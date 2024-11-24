package avl.leo.almohano.generators;

import avl.leo.almohano.utils.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static avl.leo.almohano.utils.Constants.*;

public class GBFSGenerator {

    public static void generateGBFS(String path, String agencyName, String locationName, List<String> fileNames, List<String> languages) {
        String outputFileName = "/gbfs.json";

        // Generate JSON data
        Map<String, Object> data = generateGBFS(agencyName, locationName, fileNames, languages);

        // Save to file
        FileUtils.createFileFromMap(path, outputFileName, data);
    }

    public static List<String> generateGBFSVersions(String agency, String locationName, String path) {
        String outputFileName = "/gbfs_versions.json";

        // Generate JSON data
        Map<String, Object> data = generateGBFSVersions(agency, locationName);

        // Save to file
        FileUtils.createFileFromMap(path, outputFileName, data);

        return List.of(outputFileName);
    }

    private static Map<String, Object> generateGBFS(String agencyName, String locationName, List<String> fileNames, List<String> languages) {
        Map<String, Object> root = new HashMap<>();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // Metadata
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 180);
        root.put("version", GBFS_VERSION);

        // Languages feeds data
        Map<String, Object> lan = new HashMap<>();
        languages.forEach(language -> {
            // Feed data
            Map<String, Object> feeds = new HashMap<>();
            List<Object> feedsArray = new ArrayList<>();
            fileNames.forEach(file -> {
                Map<String, Object> fileData = new HashMap<>();
                String formatedFileName = file.replace("/", "").replace(".json", "");
                fileData.put("name", formatedFileName);
                // BASE_GBFS_URL https://leo-av.github.io/GBFS/%s/%s_gbfs/%s.json
                if (formatedFileName.equals("gbfs_versions"))
                    fileData.put("url", String.format(BASE_GBFS_URL, agencyName, locationName, formatedFileName));

                // BASE_GBFS_FILES_URL https://leo-av.github.io/GBFS/%s/%s_gbfs/%s/%s.json
                else fileData.put("url", String.format(BASE_GBFS_FILES_URL, agencyName, locationName, language, formatedFileName));

                feedsArray.add(fileData);
            });
            feeds.put("feeds", feedsArray);
            lan.put(language, feeds);
        });

        // Attach languages feeds to root
        root.put("data", lan);

        return root;
    }

    private static Map<String, Object> generateGBFSVersions(String agency, String locationName) {
        Map<String, Object> root = new HashMap<>();
        long currentTimestamp = System.currentTimeMillis() / 1000;
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 180);
        root.put("version", GBFS_VERSION);

        List<Object> versions = new ArrayList<>();
        versions.add(Map.of("version", GBFS_VERSION, "url", String.format(BASE_GBFS_URL, agency, locationName, "gbfs")));

        root.put("data", Map.of("versions", versions));
        return root;
    }

}