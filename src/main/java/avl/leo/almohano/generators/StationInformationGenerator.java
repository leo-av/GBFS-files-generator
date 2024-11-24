package avl.leo.almohano.generators;

import avl.leo.almohano.utils.FileUtils;

import java.util.*;

import static avl.leo.almohano.generators.StationStatusGenerator.generateStationsStatus;
import static avl.leo.almohano.utils.Constants.*;
import static avl.leo.almohano.utils.RandomGenerator.getRandomCoordinates;
import static avl.leo.almohano.utils.RandomGenerator.getRandomStationNumber;

public class StationInformationGenerator {

    public static List<String> generateStations(String agencyName, String path) {
        // Randomly assign the number of stations to generate
        Random random = new Random();
        int numberOfStations = getRandomStationNumber(random);
        String outputFileName = "/station_information.json";

        // Generate JSON data
        Map<String, Object> data = generateStationInformation(numberOfStations, agencyName, random);

        // Save to file
        FileUtils.createFileFromMap(path, outputFileName, data);

        // Generate station status according to number of stations created
        List<String> fileNames = new ArrayList<>(generateStationsStatus(agencyName, path, numberOfStations));

        fileNames.add(outputFileName);
        return fileNames;
    }

    private static Map<String, Object> generateStationInformation(int stations, String agencyName, Random random) {
        Map<String, Object> root = new HashMap<>();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // Metadata
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 180);
        root.put("version", GBFS_VERSION);

        // Station data
        List<Object> stationsList = new ArrayList<>();

        // Generate stations
        for (int i = 1; i <= stations; i++) {
            Map<String, Object> station = new HashMap<>();
            station.put("station_id", String.format(STATION_ID, agencyName, i)); // Include operator name
            station.put("name", "Station " + i);
            station.put("lat", getRandomCoordinates(40.3, 40.5, random)); // Latitude in Madrid
            station.put("lon", getRandomCoordinates(-3.8, -3.6, random)); // Longitude in Madrid
            station.put("capacity", random.nextInt(40) + 10); // Random capacity between 10 and 40

            stationsList.add(station);
        }

        // Attach stations array to root
        Map<String, Object> data = new HashMap<>();
        data.put("stations", stationsList);
        root.put("data", data);

        return root;
    }
}
