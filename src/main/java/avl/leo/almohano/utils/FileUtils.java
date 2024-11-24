package avl.leo.almohano.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileUtils {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void createFileFromMap(String path, String outputFileName, Map<String, Object> data) {
        // Save to file
        try {
            File dir = new File(path);
            if (!dir.exists()) dir.mkdirs();

            try (FileWriter file = new FileWriter(dir.getPath() + outputFileName)) {
                file.write(gson.toJson(data)); // Pretty print with 4 spaces
            }
            System.out.println("JSON data successfully saved to " + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
