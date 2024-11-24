package avl.leo.almohano.generators;

import avl.leo.almohano.utils.FileUtils;

import java.util.*;

import static avl.leo.almohano.utils.Constants.*;

public class VehicleTypesGenerator {

    public static List<String> generateVehicleTypes(String agencyName, String path, int type) {
        // Randomly assign the number of vehicles to generate
        Random random = new Random();
        String outputFileName = "/vehicle_types.json";

        // Generate JSON data
        Map<String, Object> data = generateVehicleTypes(agencyName, random, type);

        // Save to file
        FileUtils.createFileFromMap(path, outputFileName, data);

        return List.of(outputFileName);
    }

    private static Map<String, Object> generateVehicleTypes(String agencyName, Random random, int type) {
        // Create the root JSON object
        Map<String, Object> root = new HashMap<>();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // Metadata
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 86400);
        root.put("version", GBFS_VERSION);

        // Vehicle types data
        List<Object> vehicleTypes = new ArrayList<>();

        switch (type) {
            case 0 -> bikeVehicleTypes(vehicleTypes, agencyName, random);
            case 1 -> scooterVehicleTypes(vehicleTypes, agencyName, random);
            case 2 -> carVehicleTypes(vehicleTypes, agencyName, random);
            default -> throw new RuntimeException("Unsupported vehicle type: " + type);
        }

        // Attach vehicle types array to root
        Map<String, Object> data = new HashMap<>();
        data.put("vehicle_types", vehicleTypes);
        root.put("data", data);

        return root;
    }

    private static void bikeVehicleTypes(List<Object> vehicleTypes, String agencyName, Random random) {
        String vehicleTypeId = agencyName + "_" + BIKE;
        Map<String, Object> bike = createVehicleType(vehicleTypeId, agencyName, random);
        vehicleTypes.add(bike);

        vehicleTypeId = agencyName + "_" + ELECTRIC_BIKE;
        Map<String, Object> eBike = createVehicleType(vehicleTypeId, agencyName, random);
        vehicleTypes.add(eBike);
    }

    private static void scooterVehicleTypes(List<Object> vehicleTypes, String agencyName, Random random) {
        String vehicleTypeId = agencyName + "_" + MOTORBIKE;
        Map<String, Object> motorbike = createVehicleType(vehicleTypeId, agencyName, random);
        vehicleTypes.add(motorbike);

        vehicleTypeId = agencyName + "_" + SCOOTER;
        Map<String, Object> scooter = createVehicleType(vehicleTypeId, agencyName, random);
        vehicleTypes.add(scooter);
    }

    private static void carVehicleTypes(List<Object> vehicleTypes, String agencyName, Random random) {
        String vehicleTypeId = agencyName + "_" + CAR;
        Map<String, Object> car = createVehicleType(vehicleTypeId, agencyName, random);
        vehicleTypes.add(car);
    }

    private static Map<String, Object> createVehicleType(String vehicleTypeId, String agencyName, Random random) {
        Map<String, Object> vehicleType = new HashMap<>();
        vehicleType.put("vehicle_type_id", vehicleTypeId);

        String propulsionType = getPropulsionType(vehicleTypeId, random, agencyName);
        vehicleType.put("propulsion_type", propulsionType);

        vehicleType.put("form_factor", getFormFactor(vehicleTypeId, agencyName));
        int rangeMeters = getRangeMeters(vehicleTypeId, propulsionType, agencyName);
        if (rangeMeters != -1) vehicleType.put("max_range_meters", rangeMeters);

        vehicleType.put("default_pricing_plan_id", String.format(PLAN_1, agencyName));
        vehicleType.put("pricing_plans", getPricingPlans(vehicleTypeId, agencyName));

        return vehicleType;
    }

    private static String getPropulsionType(String vehicleTypeId, Random random, String agencyName) {
        String type = vehicleTypeId.replace(agencyName + "_", "");
        return switch (type) {
            case BIKE -> PropulsionType.HUMAN.name;
            case ELECTRIC_BIKE, SCOOTER -> PropulsionType.E.name;
            case MOTORBIKE, CAR -> randomPropulsion(random, PropulsionType.E.name, PropulsionType.COMBUSTION.name);
            default -> throw new RuntimeException("Unexpected value: " + type);
        };
    }

    private static String getFormFactor(String vehicleTypeId, String agencyName) {
        String type = vehicleTypeId.replace(agencyName + "_", "");
        return switch (type) {
            case BIKE, ELECTRIC_BIKE-> FormFactor.BICYCLE.name;
            case SCOOTER -> FormFactor.SCOOTER.name;
            case MOTORBIKE -> FormFactor.MOPED.name;
            case CAR -> FormFactor.CAR.name;
            default -> throw new RuntimeException("Unexpected value: " + type);
        };
    }

    private static String randomPropulsion(Random random, String... names) {
        return names[random.nextInt(names.length)];
    }

    private static int getRangeMeters(String vehicleTypeId, String propulsion, String agencyName) {
        String type = vehicleTypeId.replace(agencyName + "_", "");
        return switch (type) {
            case BIKE -> -1;
            case ELECTRIC_BIKE -> MaxRangeMeters.E_BIKE.distance;
            case SCOOTER -> MaxRangeMeters.E_SCOOTER.distance;
            case MOTORBIKE, CAR -> getDistance(vehicleTypeId, propulsion, agencyName);
            default -> throw new RuntimeException("Unexpected value: " + type);
        };
    }

    private static int getDistance(String vehicleTypeId, String propulsion, String agencyName) {
        String type = vehicleTypeId.replace(agencyName + "_", "");

        if (type.equals(MOTORBIKE) && propulsion.equals(ELECTRIC))
            return MaxRangeMeters.E_MOTORBIKE.distance;

        if (type.equals(MOTORBIKE) && (propulsion.equals(COMBUSTION)))
            return MaxRangeMeters.MOTORBIKE.distance;

        if (type.equals(CAR) && propulsion.equals(ELECTRIC))
            return MaxRangeMeters.E_CAR.distance;

        if (type.equals(CAR) && (propulsion.equals(COMBUSTION)))
            return MaxRangeMeters.CAR.distance;

        throw new RuntimeException("Unexpected value: " + type);
    }

    private static List<String> getPricingPlans(String vehicleTypeId, String agencyName) {
        String type = vehicleTypeId.replace(agencyName + "_", "");
        return switch (type) {
            case BIKE, ELECTRIC_BIKE, SCOOTER-> List.of(String.format(PLAN_1, agencyName), String.format(PLAN_3, agencyName));
            case CAR, MOTORBIKE -> List.of(String.format(PLAN_1, agencyName), String.format(PLAN_2, agencyName));
            default -> throw new RuntimeException("Unexpected value: " + type);
        };
    }
}
