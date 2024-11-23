package avl.leo.almohano.generators;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

import static avl.leo.almohano.generators.StationStatusGenerator.generateStationsStatus;
import static avl.leo.almohano.utils.FileUtils.createFile;
import static avl.leo.almohano.utils.RandomGenerator.getRandomCoordinates;
import static avl.leo.almohano.utils.RandomGenerator.getRandomStationNumber;

public class StationInformationGenerator {

    public static List<String> generateStations(String agencyName, String path) {
        // Randomly assign the number of stations to generate
        Random random = new Random();
        int numberOfStations = getRandomStationNumber(random);
        String outputFileName = "/station_information.json";

        // Generate JSON data
        JSONObject jsonData = generateStationInformation(numberOfStations, agencyName, random);

        // Save to file
        createFile(path, outputFileName, jsonData);

        // Generate station status according to number of stations created
        List<String> fileNames = new java.util.ArrayList<>(generateStationsStatus(agencyName, path, numberOfStations));

        fileNames.add(outputFileName);
        return fileNames;
    }

    private static JSONObject generateStationInformation(int n, String agencyName, Random random) {
        JSONObject root = new JSONObject();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // Metadata
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 180);
        root.put("version", "2.3");

        // Station data
        JSONArray stationsArray = new JSONArray();

        // Generate stations
        for (int i = 1; i <= n; i++) {
            JSONObject station = new JSONObject();
            station.put("station_id", String.format("%s_MADRID%03d", agencyName, i)); // Include operator name
            station.put("name", "Station " + i);
            station.put("lat", getRandomCoordinates(40.3, 40.5, random)); // Latitude in Madrid
            station.put("lon", getRandomCoordinates(-3.8, -3.6, random)); // Longitude in Madrid
            station.put("capacity", random.nextInt(40) + 10); // Random capacity between 10 and 40

            // Add vehicle types supported by the station
            JSONArray vehicleTypes = new JSONArray();
            vehicleTypes.put("bike");
            if (random.nextBoolean())
                vehicleTypes.put("electric_bike");

            if (random.nextBoolean())
                vehicleTypes.put("cargo_bike");

            station.put("vehicle_type_ids", vehicleTypes);

            stationsArray.put(station);
        }

        // Attach stations array to root
        JSONObject data = new JSONObject();
        data.put("stations", stationsArray);
        root.put("data", data);

        return root;
    }
}
