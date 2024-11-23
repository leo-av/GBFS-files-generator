package avl.leo.almohano.generators;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static avl.leo.almohano.utils.Constants.*;
import static avl.leo.almohano.utils.FileUtils.createFile;
import static avl.leo.almohano.utils.MethodUtils.getEnumNames;
import static avl.leo.almohano.utils.RandomGenerator.getRandomCoordinates;
import static avl.leo.almohano.utils.RandomGenerator.getRandomVehicleNumber;


public class FreeBikeStatusGenerator {

    private static final String[] MOTOR_VEHICLE_TYPES = {BikeType.E_BIKE.name, ScootType.SCOOTER.name, ScootType.MOTORBIKE.name, CarType.CAR.name};



    public static List<String> generateFreeBikeStatus(String agencyName, String path, int type) {
        Random random = new Random();

        String outputFileName = "/free_bike_status.json";

        // Generate JSON data
        JSONObject jsonData = generateData(agencyName, random, type);

        // Save to file
        createFile(path, outputFileName, jsonData);

        return List.of(outputFileName);
    }

    private static JSONObject generateData(String agencyName, Random random, int type) {
        // Randomly assign the number of vehicles to generate and generate JSON data
        int vehicleNumber = getRandomVehicleNumber(random);

        JSONObject root = new JSONObject();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // Metadata
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 180);
        root.put("version", "2.3");

        // Bike data
        JSONArray bikesArray = new JSONArray();

        // Randomly assign a vehicle type
        List<String> vehicleTypes = switch (type) {
            case 1 -> getEnumNames(BikeType.class);
            case 2 -> getEnumNames(ScootType.class);
            case 3 -> getEnumNames(CarType.class);
            default -> throw new RuntimeException("Unsupported type: " + type);
        };

        // Generate bikes
        for (int i = 1; i <= vehicleNumber; i++) {
            JSONObject bike = new JSONObject();
            bike.put("bike_id", String.format("%s_MADRID%03d", agencyName, i)); // Include operator name
            bike.put("lat", getRandomCoordinates(40.3, 40.5, random)); // Latitude in Madrid
            bike.put("lon", getRandomCoordinates(-3.8, -3.6, random)); // Longitude in Madrid
            bike.put("is_reserved", random.nextInt(2)); // 0 or 1
            bike.put("is_disabled", random.nextInt(2)); // 0 or 1

            // Randomly assign a vehicle type
            String vehicleType = vehicleTypes.get(random.nextInt(vehicleTypes.size()));
            bike.put("vehicle_type_id", agencyName + "_" + vehicleType);

            if (vehicleTypeHasMotor(vehicleType)) {
                bike.put("current_range_meters", getRandomRange(vehicleType));
            }

            // Randomly assign a pricing plan
            String[] planTypes = {String.format(PLAN_1, agencyName), String.format(PLAN_2, agencyName), String.format(PLAN_3, agencyName)};
            bike.put("pricing_plan_id", planTypes[random.nextInt(planTypes.length)]);

            bikesArray.put(bike);
        }

        // Attach bikes array to root
        JSONObject data = new JSONObject();
        data.put("bikes", bikesArray);
        root.put("data", data);

        return root;
    }

    // TODO: Calcular aleatoriamente la distancia en funcion de la maxima para cada tipo
    private static int getRandomRange(String vehicleType) {
        switch (vehicleType) {
            case ELECTRIC_BIKE:
            case MOTORBIKE:
            case SCOOTER:
            case CAR:
        }
        return 0;
    }

    private static boolean vehicleTypeHasMotor(String vehicleType) {
        // Randomly assign a vehicle type
        return Arrays.asList(MOTOR_VEHICLE_TYPES).contains(vehicleType);
    }

}
