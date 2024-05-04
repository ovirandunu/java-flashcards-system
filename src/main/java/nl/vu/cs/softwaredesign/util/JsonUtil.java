package nl.vu.cs.softwaredesign.util;

import nl.vu.cs.softwaredesign.Level;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtil {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Level loadLevelFromJSON(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Level.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveLevelToJSON(Level level, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(level, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteLevelJSON(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
