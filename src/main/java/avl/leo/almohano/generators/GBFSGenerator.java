package avl.leo.almohano.generators;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import static avl.leo.almohano.utils.Constants.BASE_GBFS_URL;
import static avl.leo.almohano.utils.FileUtils.createFile;

public class GBFSGenerator {

    public static void generateGBFS(String path, String agencyName, String locationName, List<String> fileNames, List<String> languages) {
        String outputFileName = "/gbfs.json";

        // Generate JSON data
        JSONObject jsonData = generateGBFS(agencyName, locationName, fileNames, languages);

        // Save to file
        createFile(path, outputFileName, jsonData);
    }

    private static JSONObject generateGBFS(String agencyName, String locationName, List<String> fileNames, List<String> languages) {
        JSONObject root = new JSONObject();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // Metadata
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 180);
        root.put("version", "2.3");

        // Bike data
        JSONArray feedsArray = new JSONArray();
        JSONArray languagesArray = new JSONArray();

        languages.forEach(language -> {
            JSONObject lan = new JSONObject();
            fileNames.forEach(file -> {
                JSONObject fileData = new JSONObject();
                String formatedFileName = file.replace("/", "");
                fileData.put("name", formatedFileName);
                // BASE_GBFS_URL https://leo-av.github.io/GBFS/%s/%s_gbfs/%s/%s
                // Example URL https://leo-av.github.io/GBFS/GRAPEFRUIT/madrid_gbfs/en/free_bike_status.json
                fileData.put("url", String.format(BASE_GBFS_URL, agencyName, locationName, language, formatedFileName));
                feedsArray.put(fileData);
            });
            lan.put(language, feedsArray);
            languagesArray.put(lan);
        });

        // Attach feeds array to root
        root.put("data", languagesArray);

        return root;
    }

}