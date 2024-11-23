package avl.leo.almohano.generators;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

import static avl.leo.almohano.utils.Constants.*;
import static avl.leo.almohano.utils.FileUtils.createFile;

public class VehicleTypesGenerator {

    public static List<String> generateVehicleTypes(String agencyName, String path, int type) {
        // Randomly assign the number of vehicles to generate
        Random random = new Random();
        String outputFileName = "/vehicle_types.json";

        // Generate JSON data
        JSONObject jsonData = generateVehicleTypes(agencyName, random, type);

        // Save to file
        createFile(path, outputFileName, jsonData);

        return List.of(outputFileName);
    }

    private static JSONObject generateVehicleTypes(String agencyName, Random random, int type) {
        // Create the root JSON object
        JSONObject root = new JSONObject();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // Metadata
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 86400);
        root.put("version", "2.3");

        // Vehicle types data
        JSONArray vehicleTypes = new JSONArray();

        switch (type) {
            case 1 -> bikeVehicleTypes(vehicleTypes, agencyName, random);
            case 2 -> scooterVehicleTypes(vehicleTypes, agencyName, random);
            case 3 -> carVehicleTypes(vehicleTypes, agencyName, random);
            default -> throw new RuntimeException("Unsupported vehicle type: " + type);
        }

        // Attach vehicle types array to root
        JSONObject data = new JSONObject();
        data.put("vehicle_types", vehicleTypes);
        root.put("data", data);

        return root;
    }

    private static void bikeVehicleTypes(JSONArray vehicleTypes, String agencyName, Random random) {
        String vehicleTypeId = agencyName + "_" + BIKE;
        JSONObject bike = createVehicleType(vehicleTypeId, agencyName, random);
        vehicleTypes.put(bike);

        vehicleTypeId = agencyName + "_" + ELECTRIC_BIKE;
        JSONObject eBike = createVehicleType(vehicleTypeId, agencyName, random);
        vehicleTypes.put(eBike);

        vehicleTypeId = agencyName + "_" + CARGO_BIKE;
        JSONObject cargoBike = createVehicleType(vehicleTypeId, agencyName, random);
        vehicleTypes.put(cargoBike);
    }

    private static void scooterVehicleTypes(JSONArray vehicleTypes, String agencyName, Random random) {
        String vehicleTypeId = agencyName + "_" + MOTORBIKE;
        JSONObject motorbike = createVehicleType(vehicleTypeId, agencyName, random);
        vehicleTypes.put(motorbike);

        vehicleTypeId = agencyName + "_" + SCOOTER;
        JSONObject scooter = createVehicleType(vehicleTypeId, agencyName, random);
        vehicleTypes.put(scooter);
    }

    private static void carVehicleTypes(JSONArray vehicleTypes, String agencyName, Random random) {
        String vehicleTypeId = agencyName + "_" + CAR;
        JSONObject car = createVehicleType(vehicleTypeId, agencyName, random);
        vehicleTypes.put(car);
    }

    private static JSONObject createVehicleType(String vehicleTypeId, String agencyName, Random random) {
        JSONObject vehicleType = new JSONObject();
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
            case BIKE, CARGO_BIKE -> PropulsionType.HUMAN.name;
            case ELECTRIC_BIKE, SCOOTER -> PropulsionType.E.name;
            case MOTORBIKE, CAR -> randomPropulsion(random, PropulsionType.E.name, PropulsionType.COMBUSTION.name, PropulsionType.COMBUSTION_D.name);
            default -> throw new RuntimeException("Unexpected value: " + type);
        };
    }

    private static String getFormFactor(String vehicleTypeId, String agencyName) {
        String type = vehicleTypeId.replace(agencyName + "_", "");
        return switch (type) {
            case BIKE, ELECTRIC_BIKE-> FormFactor.BICYCLE.name;
            case CARGO_BIKE-> FormFactor.CARGO_BICYCLE.name;
            case SCOOTER -> FormFactor.SCOOTER_STANDING.name;
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
            case BIKE, CARGO_BIKE -> -1;
            case ELECTRIC_BIKE -> MaxRangeMeters.E_BIKE.distance;
            case SCOOTER -> MaxRangeMeters.E_SCOOTER.distance;
            case MOTORBIKE, CAR -> randomDistance(vehicleTypeId, propulsion, agencyName);
            default -> throw new RuntimeException("Unexpected value: " + type);
        };
    }

    private static int randomDistance(String vehicleTypeId, String propulsion, String agencyName) {
        String type = vehicleTypeId.replace(agencyName + "_", "");

        if (type.equals(MOTORBIKE) && propulsion.equals(ELECTRIC))
            return MaxRangeMeters.E_MOTORBIKE.distance;

        if (type.equals(MOTORBIKE) && (propulsion.equals(COMBUSTION) || propulsion.equals(COMBUSTION_DIESEL)))
            return MaxRangeMeters.MOTORBIKE.distance;

        if (type.equals(CAR) && propulsion.equals(ELECTRIC))
            return MaxRangeMeters.E_CAR.distance;

        if (type.equals(CAR) && (propulsion.equals(COMBUSTION) || propulsion.equals(COMBUSTION_DIESEL)))
            return MaxRangeMeters.CAR.distance;

        throw new RuntimeException("Unexpected value: " + type);
    }

    private static List<String> getPricingPlans(String vehicleTypeId, String agencyName) {
        String type = vehicleTypeId.replace(agencyName + "_", "");
        return switch (type) {
            case BIKE, CARGO_BIKE, ELECTRIC_BIKE, SCOOTER-> List.of(String.format(PLAN_1, agencyName), String.format(PLAN_3, agencyName));
            case CAR, MOTORBIKE -> List.of(String.format(PLAN_1, agencyName), String.format(PLAN_2, agencyName));
            default -> throw new RuntimeException("Unexpected value: " + type);
        };
    }
}
