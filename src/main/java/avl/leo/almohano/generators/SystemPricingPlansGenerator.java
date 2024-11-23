package avl.leo.almohano.generators;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

import static avl.leo.almohano.utils.Constants.*;
import static avl.leo.almohano.utils.FileUtils.createFile;
import static avl.leo.almohano.utils.RandomGenerator.getRandomDistance;
import static avl.leo.almohano.utils.RandomGenerator.getRandomPriceDouble;

public class SystemPricingPlansGenerator {

    public static List<String> generateSystemPricingPlans(String agencyName, String path) {
        String outputFileName = "/system_pricing_plans.json";

        // Generate JSON data
        JSONObject jsonData = generateSystemPricingPlans(agencyName);

        // Save to file
        createFile(path, outputFileName, jsonData);

        return List.of(outputFileName);
    }

    private static JSONObject generateSystemPricingPlans(String agencyName) {
        JSONObject root = new JSONObject();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        Random random = new Random();

        // Metadata
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 180);
        root.put("version", "2.3");

        // Plans data
        JSONArray plansArray = new JSONArray();

        JSONObject plan1 = new JSONObject();
        JSONObject plan2 = new JSONObject();
        JSONObject plan3 = new JSONObject();

        // Create plans
        createPlan1(agencyName, plan1, random);
        plansArray.put(plan1);

        createPlan2(agencyName, plan2, random);
        plansArray.put(plan2);

        createPlan3(agencyName, plan3, random);
        plansArray.put(plan3);

        // Attach feeds array to root
        root.put("data", plansArray);

        return root;
    }

    private static void createPlan1(String agencyName, JSONObject plan1, Random random) {
        plan1.put("plan_id", String.format(PLAN_1, agencyName));
        plan1.put("name", "Simple rate");
        plan1.put("currency", EUR);
        plan1.put("is_taxable", false);
        plan1.put("price", getRandomPriceDouble(0.8, 2.5, random));
        double randomPriceDouble = getRandomPriceDouble(0.5, 2.5, random);
        plan1.put("description", String.format(EURO_PER_KILOMETER, randomPriceDouble));

        plan1.put("per_km_pricing", createSimplePerKMPricing(randomPriceDouble));
    }

    private static void createPlan2(String agencyName, JSONObject plan2, Random random) {
        plan2.put("plan_id", String.format(PLAN_2, agencyName));
        plan2.put("name", "One-Way");
        plan2.put("currency", EUR);
        plan2.put("is_taxable", false);
        plan2.put("price", getRandomPriceDouble(1, 4, random));

        int randomDistance1 = getRandomDistance(random);
        plan2.put("description", String.format(INCLUDES_DISTANCE, randomDistance1, randomDistance1));

        JSONArray perKMPricing = new JSONArray();

        JSONObject perKMPrice1 = new JSONObject();
        double randomPriceDouble1 = getRandomPriceDouble(1.25, 2.5, random);
        perKMPrice1.put("start", randomDistance1);
        perKMPrice1.put("interval", 1);
        perKMPrice1.put("rate", randomPriceDouble1);
        int randomDistance2 = randomDistance1 + getRandomDistance(random);
        perKMPrice1.put("end", randomDistance2);
        perKMPricing.put(perKMPrice1);

        JSONObject perKMPrice2 = new JSONObject();
        double randomPriceDouble2 = getRandomPriceDouble(0.5, 1.25, random);
        perKMPrice2.put("start", randomDistance2);
        perKMPrice2.put("interval", 1);
        perKMPrice2.put("rate", randomPriceDouble2);
        int randomDistance3 = randomDistance2 + getRandomDistance(random);
        perKMPrice2.put("end", randomDistance3);
        perKMPricing.put(perKMPrice2);

        JSONObject perKMPrice3 = new JSONObject();
        double randomPriceDouble3 = getRandomPriceDouble(3, 8, random);
        perKMPrice3.put("start", randomDistance3);
        perKMPrice3.put("interval", 5);
        perKMPrice3.put("rate", randomPriceDouble3);
        int randomDistance4 = randomDistance3 + getRandomDistance(random);
        perKMPrice3.put("end", randomDistance4);
        perKMPricing.put(perKMPrice3);

        plan2.put("per_km_pricing", perKMPricing);
    }

    private static void createPlan3(String agencyName, JSONObject plan3, Random random) {
        plan3.put("plan_id", String.format(PLAN_3, agencyName));
        plan3.put("name", "Simple rate");
        plan3.put("currency", EUR);
        plan3.put("is_taxable", true);

        double unlockFee = getRandomPriceDouble(0.8, 4.5, random);
        plan3.put("price", unlockFee);

        double pricePerKM = getRandomPriceDouble(0.5, 1.5, random);
        double pricePerMin = getRandomPriceDouble(0.5, 1.5, random);
        plan3.put("description", String.format(EURO_PER_KILOMETER_AND_MINUTE, unlockFee, pricePerKM, pricePerMin));

        plan3.put("per_km_pricing", createSimplePerKMPricing(pricePerKM));
        plan3.put("per_min_pricing", createSimplePerMinPricing(pricePerMin));
    }

    private static JSONArray createSimplePerKMPricing(double pricePerKM) {
        JSONArray perKMPricing = new JSONArray();
        JSONObject perKMPrice = new JSONObject();
        perKMPrice.put("start", 0);
        perKMPrice.put("interval", 1);
        perKMPrice.put("rate", pricePerKM);
        perKMPricing.put(perKMPrice);
        return perKMPricing;
    }

    private static JSONArray createSimplePerMinPricing(double pricePerMin) {
        JSONArray perMinPricing = new JSONArray();
        JSONObject perMinPrice = new JSONObject();
        perMinPrice.put("start", 0);
        perMinPrice.put("interval", 1);
        perMinPrice.put("rate", pricePerMin);
        perMinPricing.put(perMinPrice);
        return perMinPricing;
    }

}