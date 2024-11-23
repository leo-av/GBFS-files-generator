package avl.leo.almohano.utils;

public final class Constants {

    private Constants() {}

    public static final String BASE_GBFS_URL = "https://leo-av.github.io/GBFS/%s/%s_gbfs/%s/%s";

    public static final String PLAN_1 = "%s_plan_1";
    public static final String PLAN_2 = "%s_plan_2";
    public static final String PLAN_3 = "%s_plan_3";

    public static final String EUR = "EUR";
    public static final String EURO_PER_KILOMETER = "%.2f € per kilometer";
    public static final String EURO_PER_KILOMETER_AND_MINUTE = "%.2f € unlock fee, %.2f € per kilometer and %.2f € per minute.";

    public static final String INCLUDES_DISTANCE = "Includes %dkm, overage fees apply after %dkm";
    public static final String BIKE = "bike";
    public static final String BICYCLE = "bicycle";
    public static final String ELECTRIC_BIKE = "electric_bike";
    public static final String CARGO_BIKE = "cargo_bike";
    public static final String CARGO_BICYCLE = "cargo_bicycle";
    public static final String SCOOTER = "scooter";
    public static final String SCOOTER_STANDING = "scooter_standing";
    public static final String MOTORBIKE = "motorbike";
    public static final String MOPED = "moped";
    public static final String CAR = "car";
    public static final String HUMAN = "human";
    public static final String ELECTRIC_ASSIST = "electric_assist";
    public static final String ELECTRIC = "electric";
    public static final String COMBUSTION = "combustion";
    public static final String COMBUSTION_DIESEL = "combustion_diesel";
    public static final String HYBRID = "hybrid";


    public enum BikeType {
        BIKE(Constants.BIKE),
        E_BIKE(ELECTRIC_BIKE),
        CARGO_BIKE(Constants.CARGO_BIKE);

        public final String name;

        BikeType(String name) {
            this.name = name;
        }
    }

    public enum ScootType {
        SCOOTER(Constants.SCOOTER),
        MOTORBIKE(Constants.MOTORBIKE);

        public final String name;

        ScootType(String name) {
            this.name = name;
        }
    }

    public enum CarType {
        CAR(Constants.CAR);

        public final String name;

        CarType(String name) {
            this.name = name;
        }
    }

    public enum PropulsionType {
        HUMAN(Constants.HUMAN),
        E_ASSIST(ELECTRIC_ASSIST),
        E(ELECTRIC),
        COMBUSTION(Constants.COMBUSTION),
        COMBUSTION_D(COMBUSTION_DIESEL),
        HYBRID(Constants.HYBRID);

        public final String name;

        PropulsionType(String name) {
            this.name = name;
        }
    }

    public enum FormFactor {
        BICYCLE(Constants.BICYCLE),
        CARGO_BICYCLE(Constants.CARGO_BICYCLE),
        CAR(Constants.CAR),
        SCOOTER_STANDING(Constants.SCOOTER_STANDING),
        MOPED(Constants.MOPED);

        public final String name;

        FormFactor(String name) {
            this.name = name;
        }
    }

    public enum MaxRangeMeters {
        E_BIKE(75000),
        E_SCOOTER(20000),
        E_MOTORBIKE(200000),
        E_CAR(290000),
        MOTORBIKE(250000),
        CAR(500000);

        public final int distance;

        MaxRangeMeters(int distance) {
            this.distance = distance;
        }
    }
}