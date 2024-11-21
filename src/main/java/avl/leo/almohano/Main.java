package avl.leo.almohano;

import avl.leo.almohano.generators.FreeBikeStatusGenerator;
import avl.leo.almohano.generators.GBFSGenerator;
import avl.leo.almohano.generators.StationInformationGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static Random random = new Random();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        do {
            // Ask the user for the operator name
            System.out.print("Enter the agency name: ");
            String agency = scanner.nextLine().toUpperCase();

            // Ask for Location Name
            System.out.print("Enter the agency name (examples: Madrid, Valencia, Bilbao, New York): ");
            String locationName = scanner.nextLine().toLowerCase();

            // Ask for locationName bound coordinates

            generateData(agency, locationName);

        } while (continueExecution(scanner));
    }

    private static boolean continueExecution(Scanner scanner) {
        // Ask the user if he wants to continue
        System.out.print("Repeat process? (y/n) (yes/no) (si/no): ");
        String response = scanner.nextLine().toLowerCase();

        if (response.equals("y") || response.equals("yes") || response.equals("si"))  return true;
        else if (response.equals("n") || response.equals("no")) return false;
        else {
            System.out.println("Invalid response, finishing execution.");
            return false;
        }
    }

    private static void generateData(String agency, String location) {
        String outputPath = "/Users/leo_almohano/UNI/TFG/datos/GBFS/" + agency + "/" + location + "_gbfs/";
        String outputFilesPath = outputPath + "en/";

        String agencyName = agency.toLowerCase().replaceAll("\\s+", "_"); // Normalize name

        int type = random.nextInt(3) + 1;

        List<String> filenames = new ArrayList<>();

        switch (type) {
            case 1: {
                filenames.addAll(FreeBikeStatusGenerator.generateBikes(agencyName, outputFilesPath));
                filenames.addAll(StationInformationGenerator.generateStations(agencyName, outputFilesPath));
                break;
            }
            case 2: {
                // Los coches tienen estaciones asociadas?
                filenames.addAll(FreeBikeStatusGenerator.generateCars(agencyName, outputFilesPath));
                break;
            }
            case 3: {
                // Motos y patines tienen estaciones asociadas?
                filenames.addAll(FreeBikeStatusGenerator.generateScooters(agencyName, outputFilesPath));
                break;
            }
        }

        GBFSGenerator.generateGBFS(outputPath, agencyName, location, filenames, List.of("en"));
    }
}