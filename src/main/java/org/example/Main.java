package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Map<String, String> fileDatabase = DatabaseHandler.loadDatabase();

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
                        DatabaseHandler.saveDatabase(fileDatabase);
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
                            DatabaseHandler.saveDatabase(fileDatabase);
                        }
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
        scanner.close();
    }
}