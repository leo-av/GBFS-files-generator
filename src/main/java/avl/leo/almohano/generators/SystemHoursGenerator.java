package avl.leo.almohano.generators;

import avl.leo.almohano.utils.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static avl.leo.almohano.utils.Constants.GBFS_VERSION;

public class SystemHoursGenerator {

    public static List<String> generateSystemHours(String path) {
        String outputFileName = "/system_hours.json";

        // Generate JSON data
        Map<String, Object> data = generateSystemHours();

        // Save to file
        FileUtils.createFileFromMap(path, outputFileName, data);

        return List.of(outputFileName);
    }

    public static Map<String, Object> generateSystemHours() {
        // Create the root JSON object
        Map<String, Object> root = new HashMap<>();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // Metadata
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 86400);
        root.put("version", GBFS_VERSION);

        // Create the rental_hours array
        List<Object> rentalHours = new ArrayList<>();

        // Add rental hour objects
        rentalHours.add(createRentalHour(
                new String[]{"member"},
                new String[]{"sat", "sun"},
                "00:00:00",
                "23:59:59"
        ));
        rentalHours.add(createRentalHour(
                new String[]{"nonmember"},
                new String[]{"sat", "sun"},
                "05:00:00",
                "23:59:59"
        ));
        rentalHours.add(createRentalHour(
                new String[]{"member", "nonmember"},
                new String[]{"mon", "tue", "wed", "thu", "fri"},
                "00:00:00",
                "23:59:59"
        ));

        // Add data object
        Map<String, Object> data = new HashMap<>();
        data.put("rental_hours", rentalHours);

        // Add data to the main JSON object
        root.put("data", data);

        return root;
    }

    // Helper method to create rental hour objects
    private static Map<String, Object> createRentalHour(String[] userTypes, String[] days, String startTime, String endTime) {
        Map<String, Object> rentalHour = new HashMap<>();
        rentalHour.put("user_types", List.of(userTypes));
        rentalHour.put("days", List.of(days));
        rentalHour.put("start_time", startTime);
        rentalHour.put("end_time", endTime);
        return rentalHour;
    }

}