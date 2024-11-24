package avl.leo.almohano.generators;

import avl.leo.almohano.utils.FileUtils;

import java.util.*;

import static avl.leo.almohano.utils.Constants.*;
import static avl.leo.almohano.utils.MethodUtils.getEnumNames;
import static avl.leo.almohano.utils.RandomGenerator.*;


public class FreeBikeStatusGenerator {

    private static final String[] MOTOR_VEHICLE_TYPES = {BikeType.E_BIKE.name, ScootType.SCOOTER.name, ScootType.MOTORBIKE.name, CarType.CAR.name};

    public static List<String> generateFreeBikeStatus(String agencyName, String path, int type) {
        Random random = new Random();

        String outputFileName = "/free_bike_status.json";

        // Generate JSON data
        Map<String, Object> data = generateData(agencyName, random, type);

        // Save to file
        FileUtils.createFileFromMap(path, outputFileName, data);

        return List.of(outputFileName);
    }

    private static Map<String, Object> generateData(String agencyName, Random random, int type) {
        // Randomly assign the number of vehicles to generate and generate JSON data
        int vehicleNumber = getRandomVehicleNumber(random);

        Map<String, Object> root = new HashMap<>();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // Metadata
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 180);
        root.put("version", GBFS_VERSION);

        // Bike data
        List<Object> vehicleList = new ArrayList<>();

        // Randomly assign a vehicle type
        List<String> vehicleTypes = switch (type) {
            case 0 -> getEnumNames(BikeType.class);
            case 1 -> getEnumNames(ScootType.class);
            case 2 -> getEnumNames(CarType.class);
            default -> throw new RuntimeException("Unsupported type: " + type);
        };

        // Generate bikes
        for (int i = 1; i <= vehicleNumber; i++) {
            Map<String, Object> vehicle = new HashMap<>();
            vehicle.put("bike_id", String.format(VEHICLE_ID, agencyName, i)); // Include operator name
            vehicle.put("lat", getRandomCoordinates(40.3, 40.5, random)); // Latitude in Madrid
            vehicle.put("lon", getRandomCoordinates(-3.8, -3.6, random)); // Longitude in Madrid
            vehicle.put("is_reserved", random.nextBoolean()); // 0 or 1
            vehicle.put("is_disabled", random.nextBoolean()); // 0 or 1

            // Randomly assign a vehicle type
            String vehicleType = vehicleTypes.get(random.nextInt(vehicleTypes.size()));
            vehicle.put("vehicle_type_id", agencyName + "_" + vehicleType);

            if (vehicleTypeHasMotor(vehicleType))
                vehicle.put("current_range_meters", getRandomRange(vehicleType, agencyName, random));

            // Randomly assign car-type vehicles a child seat.
            if (type == 2 && random.nextInt(2) == 1)
                vehicle.put("vehicle_equipment", List.of("child_seat_a"));

            // Randomly assign a pricing plan
            String[] planTypes = {String.format(PLAN_1, agencyName), String.format(PLAN_2, agencyName), String.format(PLAN_3, agencyName)};
            vehicle.put("pricing_plan_id", planTypes[random.nextInt(planTypes.length)]);

            vehicleList.add(vehicle);
        }

        // Attach bikes array to root
        Map<String, Object> data = new HashMap<>();
        data.put("bikes", vehicleList);
        root.put("data", data);

        return root;
    }

    private static boolean vehicleTypeHasMotor(String vehicleType) {
        // Randomly assign a vehicle type
        return Arrays.asList(MOTOR_VEHICLE_TYPES).contains(vehicleType);
    }


    // TODO: Revisar si se puede mejorar el calculo de moto y coche
    private static int getRandomRange(String vehicleTypeId, String agencyName, Random random) {
        String type = vehicleTypeId.replace(agencyName + "_", "");
        return switch (type) {
            case ELECTRIC_BIKE -> getRandomDistance((int) (MaxRangeMeters.E_BIKE.distance * 0.05), MaxRangeMeters.E_BIKE.distance, random);
            case SCOOTER -> getRandomDistance((int) (MaxRangeMeters.E_SCOOTER.distance * 0.05), MaxRangeMeters.E_SCOOTER.distance, random);
            case MOTORBIKE -> getRandomDistance((int) (MaxRangeMeters.E_MOTORBIKE.distance * 0.05), MaxRangeMeters.E_MOTORBIKE.distance, random);
            case CAR -> getRandomDistance((int) (MaxRangeMeters.E_CAR.distance * 0.05), MaxRangeMeters.E_CAR.distance, random);
            default -> -1;
        };
    }

}