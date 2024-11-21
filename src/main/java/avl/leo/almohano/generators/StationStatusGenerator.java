package avl.leo.almohano.generators;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

import static avl.leo.almohano.utils.FileUtils.createFile;

public class StationStatusGenerator {

    public static List<String> generateStationsStatus(String agencyName, String path, int stations) {
        String outputFileName = "/station_status.json";

        // Generate JSON data
        JSONObject jsonData = generateStationStatus(stations, agencyName, new Random());

        // Save to file
        createFile(path, outputFileName, jsonData);

        return List.of(outputFileName);
    }

    private static JSONObject generateStationStatus(int n, String agencyName, Random random) {
        JSONObject root = new JSONObject();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // Metadata
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 180);
        root.put("version", "2.3");

        // Station status data
        JSONArray stationsArray = new JSONArray();

        // Generate station statuses
        for (int i = 1; i <= n; i++) {
            JSONObject station = new JSONObject();
            station.put("station_id", String.format("%s_MADRID%03d", agencyName, i)); // Include operator name
            station.put("num_bikes_available", random.nextInt(20) + 1); // Random number of bikes available (1-20)
            station.put("num_ebikes_available", random.nextInt(10)); // Random number of e-bikes available (0-5)
            station.put("num_docks_available", random.nextInt(30) + 1); // Random number of docks available (1-20)
            station.put("is_installed", 1); // Assume all stations are installed
            station.put("is_renting", 1); // Assume all stations are renting
            station.put("is_returning", 1); // Assume all stations are returning

            stationsArray.put(station);
        }

        // Attach stations array to root
        JSONObject data = new JSONObject();
        data.put("stations", stationsArray);
        root.put("data", data);

        return root;
    }
}