package avl.leo.almohano.generators;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

import static avl.leo.almohano.utils.FileUtils.createFile;
import static avl.leo.almohano.utils.RandomGenerator.getRandomCoordinate;
import static avl.leo.almohano.utils.RandomGenerator.getRandomVehicleNumber;


public class FreeBikeStatusGenerator {

    public static List<String> generateBikes(String agencyName, String path) {
        // Randomly assign the number of vehicles to generate
        Random random = new Random();
        int numberOfBikes = getRandomVehicleNumber(random);
        String outputFileName = "/free_bike_status.json";

        // Generate JSON data
        JSONObject jsonData = generateFreeBikeStatus(numberOfBikes, agencyName, random);

        // Save to file
        createFile(path, outputFileName, jsonData);

        return List.of(outputFileName);
    }

    public static List<String> generateScooters(String agencyName, String path) {
        // Randomly assign the number of vehicles to generate
        Random random = new Random();
        int numberOfBikes = getRandomVehicleNumber(random);
        String outputFileName = "/free_bike_status.json";

        // Generate JSON data
        JSONObject jsonData = generateFreeScooterStatus(numberOfBikes, agencyName, random);

        // Save to file
        createFile(path, outputFileName, jsonData);

        return List.of(outputFileName);
    }

    public static List<String> generateCars(String agencyName, String path) {
        // Randomly assign the number of vehicles to generate
        Random random = new Random();
        int numberOfBikes = getRandomVehicleNumber(random);
        String outputFileName = "/free_bike_status.json";

        // Generate JSON data
        JSONObject jsonData = generateFreeCarStatus(numberOfBikes, agencyName, random);

        // Save to file
        createFile(path, outputFileName, jsonData);

        return List.of(outputFileName);
    }

    private static JSONObject generateFreeBikeStatus(int n, String agencyName, Random random) {
        JSONObject root = new JSONObject();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // Metadata
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 180);
        root.put("version", "2.3");

        // Bike data
        JSONArray bikesArray = new JSONArray();

        // Generate bikes
        for (int i = 1; i <= n; i++) {
            JSONObject bike = new JSONObject();
            bike.put("bike_id", String.format("%s_MADRID%03d", agencyName, i)); // Include operator name
            bike.put("lat", getRandomCoordinate(40.3, 40.5, random)); // Latitude in Madrid
            bike.put("lon", getRandomCoordinate(-3.8, -3.6, random)); // Longitude in Madrid
            bike.put("is_reserved", random.nextInt(2)); // 0 or 1
            bike.put("is_disabled", random.nextInt(2)); // 0 or 1


            // Randomly assign a vehicle type
            String[] vehicleTypes = {"bike", "electric_bike", "cargo_bike"};
            String vehicleType = vehicleTypes[random.nextInt(vehicleTypes.length)];
            bike.put("vehicle_type_id", vehicleType);

            // Randomly assign a pricing plan
            String[] planTypes = {"plan1", "plan2", "plan3"};
            bike.put("pricing_plan_id", planTypes[random.nextInt(planTypes.length)]);

            bikesArray.put(bike);
        }

        // Attach bikes array to root
        JSONObject data = new JSONObject();
        data.put("bikes", bikesArray);
        root.put("data", data);

        return root;
    }

    private static JSONObject generateFreeScooterStatus(int n, String agencyName, Random random) {
        JSONObject root = new JSONObject();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // Metadata
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 180);
        root.put("version", "2.3");

        // Bike data
        JSONArray bikesArray = new JSONArray();

        // Generate bikes
        for (int i = 1; i <= n; i++) {
            JSONObject bike = new JSONObject();
            bike.put("bike_id", String.format("%s_MADRID%03d", agencyName, i)); // Include operator name
            bike.put("lat", getRandomCoordinate(40.3, 40.5, random)); // Latitude in Madrid
            bike.put("lon", getRandomCoordinate(-3.8, -3.6, random)); // Longitude in Madrid
            bike.put("is_reserved", random.nextInt(2)); // 0 or 1
            bike.put("is_disabled", random.nextInt(2)); // 0 or 1


            // Randomly assign a vehicle type
            String[] vehicleTypes = {"scooter", "motorbike"};
            String vehicleType = vehicleTypes[random.nextInt(vehicleTypes.length)];
            bike.put("vehicle_type_id", vehicleType);

            // Randomly assign a pricing plan
            String[] planTypes = {"plan1", "plan2", "plan3"};
            bike.put("pricing_plan_id", planTypes[random.nextInt(planTypes.length)]);

            bikesArray.put(bike);
        }

        // Attach bikes array to root
        JSONObject data = new JSONObject();
        data.put("bikes", bikesArray);
        root.put("data", data);

        return root;
    }

    private static JSONObject generateFreeCarStatus(int n, String agencyName, Random random) {
        JSONObject root = new JSONObject();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // Metadata
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 180);
        root.put("version", "2.3");

        // Bike data
        JSONArray bikesArray = new JSONArray();

        // Generate bikes
        for (int i = 1; i <= n; i++) {
            JSONObject bike = new JSONObject();
            bike.put("bike_id", String.format("%s_MADRID%03d", agencyName, i)); // Include operator name
            bike.put("lat", getRandomCoordinate(40.3, 40.5, random)); // Latitude in Madrid
            bike.put("lon", getRandomCoordinate(-3.8, -3.6, random)); // Longitude in Madrid
            bike.put("is_reserved", random.nextInt(2)); // 0 or 1
            bike.put("is_disabled", random.nextInt(2)); // 0 or 1


            bike.put("vehicle_type_id", "car");

            // Randomly assign a pricing plan
            String[] planTypes = {"car_plan1", "car_plan2", "car_plan3"};
            bike.put("pricing_plan_id", planTypes[random.nextInt(planTypes.length)]);

            bikesArray.put(bike);
        }

        // Attach bikes array to root
        JSONObject data = new JSONObject();
        data.put("bikes", bikesArray);
        root.put("data", data);

        return root;
    }

}
