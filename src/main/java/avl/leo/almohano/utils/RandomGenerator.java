package avl.leo.almohano.utils;

import java.util.Random;

public class RandomGenerator {

    public static double getRandomCoordinate(double min, double max, Random random) {
        return min + (max - min) * random.nextDouble();
    }

    public static int getRandomVehicleNumber(Random random) {
        return random.nextInt(600) + 150;
    }

    public static int getRandomStationNumber(Random random) {
        return random.nextInt(190) + 15;
    }

}
