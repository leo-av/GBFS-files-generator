package avl.leo.almohano.generators;

import avl.leo.almohano.utils.FileUtils;

import java.io.Serializable;
import java.util.*;

import static avl.leo.almohano.utils.Constants.*;
import static avl.leo.almohano.utils.Constants.ELECTRIC_BIKE;

public class StationStatusGenerator {

    public static List<String> generateStationsStatus(String agencyName, String path, int stations) {
        String outputFileName = "/station_status.json";

        // Generate JSON data
        Map<String, Object> data = generateStationStatus(stations, agencyName, new Random());

        // Save to file
        FileUtils.createFileFromMap(path, outputFileName, data);

        return List.of(outputFileName);
    }

    private static Map<String, Object> generateStationStatus(int stations, String agencyName, Random random) {
        Map<String, Object> root = new HashMap<>();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // Metadata
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 180);
        root.put("version", GBFS_VERSION);

        // Station status data
        List<Object> stationList = new ArrayList<>();

        // Generate station statuses
        for (int i = 1; i <= stations; i++) {
            Map<String, Object> station = new HashMap<>();
            station.put("station_id", String.format(STATION_ID, agencyName, i)); // Include operator name
            station.put("is_installed", 1); // Assume all stationList are installed
            station.put("is_renting", 1); // Assume all stationList are renting
            station.put("is_returning", 1); // Assume all stationList are returning

            int bikes = random.nextInt(20) + 1;
            int availableBikes = bikes - (bikes / 10);
            station.put("num_bikes_available", availableBikes); // Random number of bikes available (1-20)
            station.put("num_bikes_disabled", bikes / 10); // Random number of bikes available (1-20) / 10

            int docks = random.nextInt(30) + 1;
            int availableDocks = docks - (docks / 10);
            station.put("num_docks_available", availableDocks); // Random number of docks available (1-30)
            station.put("num_docks_disabled", docks / 10); // Random number of docks available (1-30) / 10

            // Add vehicle types supported by the station
            String typeBike = agencyName + "_" + BIKE;
            String typeEBike = agencyName + "_" + ELECTRIC_BIKE;
            Map<String, Object> dock1 = new HashMap<>();
            dock1.put("vehicle_type_ids", List.of(typeBike, typeEBike));
            dock1.put("count", (availableDocks / 3) * 2);

            Map<String, Object> dock2 = new HashMap<>();
            dock2.put("vehicle_type_ids", List.of(typeBike));
            dock2.put("count", availableDocks / 3);

            station.put("vehicle_docks_available", List.of(dock1, dock2));

            Map<String, Object> bikes1 = Map.of("vehicle_type_id", typeBike, "count", (availableBikes / 3) * 2);
            Map<String, Object> bikes2 = Map.of("vehicle_type_id", typeEBike, "count", availableBikes / 3);
            station.put("vehicle_types_available", List.of(bikes1, bikes2));

            stationList.add(station);
        }

        // Attach stationList array to root
        Map<String, Object> data = new HashMap<>();
        data.put("stations", stationList);
        root.put("data", data);

        return root;
    }
}