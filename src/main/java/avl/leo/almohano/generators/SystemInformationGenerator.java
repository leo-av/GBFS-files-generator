package avl.leo.almohano.generators;

import avl.leo.almohano.utils.FileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static avl.leo.almohano.utils.Constants.GBFS_VERSION;

public class SystemInformationGenerator {

    public static List<String> generateSystemInformation(String agencyName, String agency, String path) {
        String outputFileName = "/system_information.json";

        // Generate JSON data
        Map<String, Object> data = generateSystemInformation(agencyName, agency);

        // Save to file
        FileUtils.createFileFromMap(path, outputFileName, data);

        return List.of(outputFileName);
    }

    private static Map<String, Object> generateSystemInformation(String agencyName, String agency) {
        Map<String, Object> root = new HashMap<>();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // Metadata
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 180);
        root.put("version", GBFS_VERSION);

        Map<String, Object> data = new HashMap<>();

        data.put("system_id", agencyName);
        data.put("language", "en");
        data.put("name", agency);
        data.put("short_name", agency);
        data.put("operator", agency);
        data.put("url", "http://www.exampleride.org");
        data.put("start_date", "2010-06-10");
        data.put("phone_number", "666777888");
        data.put("email", "customerservice@" + agencyName + ".es");
        data.put("feed_contact_email", "datafeed@" + agencyName + ".es");
        data.put("timezone", "Europe/Paris");
        data.put("license_url", "https://exampleride.org/data-license.html");

        root.put("data", data);
        return root;
    }

}