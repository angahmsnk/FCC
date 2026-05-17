package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String DATABASE_FILE = "database.json";
    private static Map<String, String> fileDatabase = new HashMap<>();
    static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {

        loadDatabase();

        Scanner scanner = new Scanner(System.in);
        int option = 1;

        while (option != 0) {
            System.out.println("\n--- MONITOR ZMIAN W PLIKACH ---");
            System.out.println("1. Sprawdz plik");
            System.out.println("0. Wyjdz");
            System.out.print("Wybierz opcje: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Blad: podaj poprawna liczbe.");
                scanner.nextLine();
                continue;
            }

            option = scanner.nextInt();
            scanner.nextLine();

            if (option == 1) {
                System.out.print("Podaj sciezke do pliku: ");
                String inputPath = scanner.nextLine();

                if (inputPath.trim().isEmpty()) {
                    System.out.println("Blad: sciezka nie moze byc pusta.");
                    continue;
                }

                Path path = Paths.get(inputPath).toAbsolutePath().normalize();

                if (!Files.exists(path)) {
                    System.out.println("Blad: plik nie istnieje pod podana sciezka.");
                    continue;
                }

                if (Files.isDirectory(path)) {
                    System.out.println("Blad: podana sciezka prowadzi do folderu, a nie do pliku.");
                    continue;
                }

                try {
                    String currentHash = HashCalc.calculateHash(path.toString());
                    String fileKey = path.toString();

                    if (!fileDatabase.containsKey(fileKey)) {
                        fileDatabase.put(fileKey, currentHash);
                        saveDatabase();
                        System.out.println("Plik nie byl wczesniej sprawdzany przez aplikacje. Zapisano hash pliku.");
                    }
                    else {
                        String savedHash = fileDatabase.get(fileKey);

                        if (currentHash.equals(savedHash)) {
                            System.out.println("Nie wykryto zmian.");
                        }
                        else {
                            System.out.println("Wykryto zmiane pliku. Zaktualizowano hash.");
                            fileDatabase.put(fileKey, currentHash);
                            saveDatabase();
                        }
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
        scanner.close();
    }

    public static void loadDatabase() {
        File file = new File(DATABASE_FILE);
        if (!file.exists()) {
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            Type type = new TypeToken<HashMap<String, String>>() {}.getType();
            Map<String, String> loadedData = gson.fromJson(reader, type);
            if (loadedData != null) {
                fileDatabase = loadedData;
            }
        } catch (IOException e) {
            System.out.println("Blad wczytywania bazy plikow: " + e.getMessage());
        }
    }

    public static void saveDatabase() {
        try (FileWriter writer = new FileWriter(DATABASE_FILE)) {
            gson.toJson(fileDatabase, writer);
        } catch (IOException e) {
            System.out.println("Blad zapisu danych do pliku: " + e.getMessage());
        }
    }
}