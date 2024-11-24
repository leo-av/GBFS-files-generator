package avl.leo.almohano.generators;

import avl.leo.almohano.utils.FileUtils;

import java.util.*;

import static avl.leo.almohano.utils.Constants.*;
import static avl.leo.almohano.utils.RandomGenerator.getRandomDistance;
import static avl.leo.almohano.utils.RandomGenerator.getRandomPriceDouble;

public class SystemPricingPlansGenerator {

    public static List<String> generateSystemPricingPlans(String agencyName, String path) {
        String outputFileName = "/system_pricing_plans.json";

        // Generate JSON data
        Map<String, Object> data = generateSystemPricingPlans(agencyName);

        // Save to file
        FileUtils.createFileFromMap(path, outputFileName, data);

        return List.of(outputFileName);
    }

    private static Map<String, Object> generateSystemPricingPlans(String agencyName) {
        Map<String, Object> root = new HashMap<>();
        long currentTimestamp = System.currentTimeMillis() / 1000;

        Random random = new Random();

        // Metadata
        root.put("last_updated", currentTimestamp);
        root.put("ttl", 180);
        root.put("version", GBFS_VERSION);

        // Plans data
        List<Object> plansArray = new ArrayList<>();

        Map<String, Object> plan1 = new HashMap<>();
        Map<String, Object> plan2 = new HashMap<>();
        Map<String, Object> plan3 = new HashMap<>();

        // Create plans
        createPlan1(agencyName, plan1, random);
        plansArray.add(plan1);

        createPlan2(agencyName, plan2, random);
        plansArray.add(plan2);

        createPlan3(agencyName, plan3, random);
        plansArray.add(plan3);

        // Attach feeds array to root
        root.put("data", Map.of("plans", plansArray));

        return root;
    }

    private static void createPlan1(String agencyName, Map<String, Object> plan1, Random random) {
        plan1.put("plan_id", String.format(PLAN_1, agencyName));
        plan1.put("name", "Simple rate");
        plan1.put("currency", EUR);
        plan1.put("is_taxable", false);
        plan1.put("price", getRandomPriceDouble(0.8, 2.5, random));
        double randomPriceDouble = getRandomPriceDouble(0.5, 2.5, random);
        plan1.put("description", String.format(EURO_PER_KILOMETER, randomPriceDouble));

        plan1.put("per_km_pricing", createSimplePerKMPricing(randomPriceDouble));
    }

    private static void createPlan2(String agencyName, Map<String, Object> plan2, Random random) {
        plan2.put("plan_id", String.format(PLAN_2, agencyName));
        plan2.put("name", "One-Way");
        plan2.put("currency", EUR);
        plan2.put("is_taxable", false);
        plan2.put("price", getRandomPriceDouble(1, 4, random));

        int randomDistance1 = getRandomDistance(random);
        plan2.put("description", String.format(INCLUDES_DISTANCE, randomDistance1, randomDistance1));

        List<Object> perKMPricing = new ArrayList<>();

        Map<String, Object> perKMPrice1 = new HashMap<>();
        double randomPriceDouble1 = getRandomPriceDouble(1.25, 2.5, random);
        perKMPrice1.put("start", randomDistance1);
        perKMPrice1.put("interval", 1);
        perKMPrice1.put("rate", randomPriceDouble1);
        int randomDistance2 = randomDistance1 + getRandomDistance(random);
        perKMPrice1.put("end", randomDistance2);
        perKMPricing.add(perKMPrice1);

        Map<String, Object> perKMPrice2 = new HashMap<>();
        double randomPriceDouble2 = getRandomPriceDouble(0.5, 1.25, random);
        perKMPrice2.put("start", randomDistance2);
        perKMPrice2.put("interval", 1);
        perKMPrice2.put("rate", randomPriceDouble2);
        int randomDistance3 = randomDistance2 + getRandomDistance(random);
        perKMPrice2.put("end", randomDistance3);
        perKMPricing.add(perKMPrice2);

        Map<String, Object> perKMPrice3 = new HashMap<>();
        double randomPriceDouble3 = getRandomPriceDouble(3, 8, random);
        perKMPrice3.put("start", randomDistance3);
        perKMPrice3.put("interval", 5);
        perKMPrice3.put("rate", randomPriceDouble3);
        int randomDistance4 = randomDistance3 + getRandomDistance(random);
        perKMPrice3.put("end", randomDistance4);
        perKMPricing.add(perKMPrice3);

        plan2.put("per_km_pricing", perKMPricing);
    }

    private static void createPlan3(String agencyName, Map<String, Object> plan3, Random random) {
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

    private static List<Object> createSimplePerKMPricing(double pricePerKM) {
        List<Object> perKMPricing = new ArrayList<>();
        Map<String, Object> perKMPrice = new HashMap<>();
        perKMPrice.put("start", 0);
        perKMPrice.put("interval", 1);
        perKMPrice.put("rate", pricePerKM);
        perKMPricing.add(perKMPrice);
        return perKMPricing;
    }

    private static List<Object> createSimplePerMinPricing(double pricePerMin) {
        List<Object> perMinPricing = new ArrayList<>();
        Map<String, Object> perMinPrice = new HashMap<>();
        perMinPrice.put("start", 0);
        perMinPrice.put("interval", 1);
        perMinPrice.put("rate", pricePerMin);
        perMinPricing.add(perMinPrice);
        return perMinPricing;
    }

}