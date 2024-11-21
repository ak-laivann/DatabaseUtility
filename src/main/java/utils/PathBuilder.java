package utils;

import interfaces.Method;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathBuilder {

    /*
     * I want the authKey to be user's name and that would be the directory. entity name would be of type json.
     */
    public static String createPath(String authKey, String entityName, Method method) throws IllegalArgumentException {
        if (authKey == null || authKey.isEmpty()) {
            throw new IllegalArgumentException("Auth key can't be empty");
        }

        if (entityName == null || entityName.isEmpty()) {
            throw new IllegalArgumentException("Entity name can't be empty");
        }

        Path basePath = Paths.get("databases", authKey);

        try {
            Files.createDirectories(basePath);
        } catch (IOException e) {
            System.err.println("Could not create directory: " + e.getMessage());
        }

        Path filePath = basePath.resolve(entityName + ".json");
        try {
            if ((!Files.exists(filePath)) && (method == Method.POST)) {
                Files.createFile(filePath);
                System.out.println("File created: " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Could not create file: " + e.getMessage());
            throw new NullPointerException("No File Created or Present");
        }

        return filePath.toString();
    }
}
