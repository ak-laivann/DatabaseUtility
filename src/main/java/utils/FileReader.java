package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class FileReader<T> {
    public static ArrayList<Object> readJsonArrayFromFile(String filePath) {
        ArrayList<Object> jsonArray = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File(filePath);

        if (file.length() == 0) {
            System.out.println("Empty File -> is this method post? Anyhow, returning an empty list.");
            return jsonArray;
        }

        try {
            jsonArray = objectMapper.readValue(file, ArrayList.class);
        } catch (IOException e) {
            System.out.println("Ha Ha! you fucked up. Check the stack trace and get back to work again.");
            e.printStackTrace();
        }

        return jsonArray;
    }
}
