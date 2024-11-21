package avl.leo.almohano.utils;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    public static void createFile(String path, String outputFileName, JSONObject data) {
        // Save to file
        try {
            File dir = new File(path);
            if (!dir.exists()) dir.mkdirs();

            try (FileWriter file = new FileWriter(dir.getPath() + outputFileName)) {
                file.write(data.toString(4)); // Pretty print with 4 spaces
            }
            System.out.println("JSON data successfully saved to " + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
