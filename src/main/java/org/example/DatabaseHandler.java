package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHandler {
    private static final String DATABASE_FILE = "database.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Map<String, String> loadDatabase() {
        File file = new File(DATABASE_FILE);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (FileReader reader = new FileReader(file)) {
            Type type = new TypeToken<HashMap<String, String>>() {}.getType();
            Map<String, String> loadedData = gson.fromJson(reader, type);
            if (loadedData != null) {
                return loadedData;
            }
        } catch (IOException e) {
            System.out.println("Blad wczytywania bazy plikow: " + e.getMessage());
        }
        return new HashMap<>();
    }

    public static void saveDatabase(Map<String, String> fileDatabase) {
        try (FileWriter writer = new FileWriter(DATABASE_FILE)) {
            gson.toJson(fileDatabase, writer);
        } catch (IOException e) {
            System.out.println("Blad zapisu danych do pliku: " + e.getMessage());
        }
    }
}