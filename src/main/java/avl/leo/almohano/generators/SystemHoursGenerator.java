package avl.leo.almohano.generators;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import static avl.leo.almohano.utils.FileUtils.createFile;

public class SystemHoursGenerator {

    public static List<String> generateSystemHours(String agencyName, String path) {
        String outputFileName = "/system_hours.json";

        // Generate JSON data
        JSONObject jsonData = generateSystemHours(agencyName);

        // Save to file
        createFile(path, outputFileName, jsonData);

        return List.of(outputFileName);
    }

    public static JSONObject generateSystemHours(String agencyName) {
        // Create the root JSON object
        JSONObject root = new JSONObject();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // Metadata
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 86400);
        root.put("version", "2.3");

        // Create the rental_hours array
        JSONArray rentalHours = new JSONArray();

        // Add rental hour objects
        rentalHours.put(createRentalHour(
                new String[]{"member"},
                new String[]{"sat", "sun"},
                "00:00:00",
                "23:59:59"
        ));
        rentalHours.put(createRentalHour(
                new String[]{"nonmember"},
                new String[]{"sat", "sun"},
                "05:00:00",
                "23:59:59"
        ));
        rentalHours.put(createRentalHour(
                new String[]{"member", "nonmember"},
                new String[]{"mon", "tue", "wed", "thu", "fri"},
                "00:00:00",
                "23:59:59"
        ));

        // Add data object
        JSONObject data = new JSONObject();
        data.put("rental_hours", rentalHours);

        // Add data to the main JSON object
        return root.put("data", data);
    }

    // Helper method to create rental hour objects
    private static JSONObject createRentalHour(String[] userTypes, String[] days, String startTime, String endTime) {
        JSONObject rentalHour = new JSONObject();
        rentalHour.put("user_types", new JSONArray(userTypes));
        rentalHour.put("days", new JSONArray(days));
        rentalHour.put("start_time", startTime);
        rentalHour.put("end_time", endTime);
        return rentalHour;
    }

}