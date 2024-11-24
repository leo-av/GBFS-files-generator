package avl.leo.almohano.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class RandomGenerator {

    public static double getRandomCoordinates(double min, double max, Random random) {
        return min + (max - min) * random.nextDouble();
    }

    public static double getRandomPriceDouble(double min, double max, Random random) {
        double randomDouble = min + (max - min) * random.nextDouble();
        // Round the value to ensure the last decimal is either .X0 or .X5
        double value = (Math.round(randomDouble * 100) / 100.0);
        if ((value * 100) % 10 != 0 && (value * 100) % 5 != 0)
            value = (value + Math.round(5 - ((value * 100) % 5)) / 100.0);

        // Rounding to 2 decimal places
        BigDecimal bd = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static int getRandomVehicleNumber(Random random) {
        return random.nextInt(600) + 150;
    }

    public static int getRandomStationNumber(Random random) {
        return random.nextInt(190) + 15;
    }

    public static int getRandomDistance(Random random) {
        return random.nextInt(5) + 25;
    }

    public static int getRandomDistance(int min, int max, Random random) {
        return random.nextInt(max) + min;
    }

}