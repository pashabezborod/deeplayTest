package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class Solution {
    public static void main(String... args) {
        if (args.length != 2)
            throw new IllegalArgumentException("""
                    \nNeed two arguments:
                    1. Game field
                    2. Creature type (Human, Swamper or Woodman)""");
        if (args[0].length() != 16)
            throw new IllegalArgumentException("\nField must be 4X4 tales (16 letters)");

        try {
            System.out.println("Calculating results for " + args[1]);
            System.out.println("Shortest way costs " + getResult(args[0], args[1]));
        } catch (IOException e) {
            System.err.println("Data reading error. Did you mount data.config?");
            e.printStackTrace();
        } catch (URISyntaxException e) {
            System.err.println("Path to data.config casting error.");
            e.printStackTrace();
        }
    }

    /**
     * Method returns minimal cost of the creature's movement from the top-left corner to the down-right one.
     */
    public static int getResult(String field, String creature) throws IOException, URISyntaxException {
        int[][] matrix = makeMatrix(field, creature);
        return findBestPath(matrix, 0, 0) - matrix[0][0];
    }

    /**
     * Method makes a path to data.config
     */
    private static String createPath() throws URISyntaxException {
        Path path = Path.of(Solution.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath());
        if (path.toString().endsWith(".jar")) path = path.getParent();
        return path + File.separator + "data" + File.separator + "data.config";
    }

    /**
     * Method creates matrix of a game field with every tile's move cost.
     * Throws exceptions if .config contains incorrect data.
     */
    private static int[][] makeMatrix(String field, String creature) throws IOException, URISyntaxException {
        CreatureSpeedData speedData = readConfig(creature);
        int[][] matrix = new int[4][4];
        int x = 0, y = 0, count = 0;
        for (char temp : field.toCharArray()) {
            matrix[y][x] = speedData.getSpeed(temp);
            y = ++count / 4;
            x = count % 4;
        }
        return matrix;
    }

    /**
     * Method reads data form .config file. Throws exceptions if file doesn't exist
     * or contains incorrect data. Example for .config file:
     * <p>
     * HUMAN 5 2 3 1
     * SWAMPER 2 2 5 2
     * WOODMAN 3 3 2 2
     */
    private static CreatureSpeedData readConfig(String creature) throws IOException, URISyntaxException {
        try (BufferedReader reader = new BufferedReader(new FileReader(createPath()))) {
            String[] data = null;
            while (reader.ready()) {
                String[] temp = reader.readLine().split(" ");
                if (temp[0].equalsIgnoreCase(creature)) {
                    data = temp;
                    break;
                }
            }
            if (data == null) throw new IllegalArgumentException("\nNo such a creature in the game.");
            if (data.length != 5) throw new IllegalArgumentException("\nIncorrect data. Check data.config");
            return new CreatureSpeedData(Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]));
        }
    }

    /**
     * Method recursively calculates and returns the shortest way to the last tile of the matrix.
     */
    private static int findBestPath(int[][] matrix, int x, int y) {
        if (x == 3 && y == 3) return matrix[y][x];
        int goRight = 0, goDown = 0;
        if (x < 3) goRight = findBestPath(matrix, x + 1, y) + matrix[y][x];
        if (y < 3) goDown = findBestPath(matrix, x, y + 1) + matrix[y][x];
        if (goDown != 0 && goRight != 0) return Math.min(goDown, goRight);
        else return Math.max(goDown, goRight);
    }

    /**
     * A special class to keep data from .config
     */

    private record CreatureSpeedData(int swamp, int water, int thicket, int plain) {
        int getSpeed(char fieldName) throws IllegalArgumentException {
            return switch (fieldName) {
                case 'S' -> swamp;
                case 'W' -> water;
                case 'T' -> thicket;
                case 'P' -> plain;
                default -> throw new IllegalArgumentException("""
                        \nWrong tale type!
                        Valid tiles:
                        "S" - Swamp
                        "W" - Water
                        "T" - Thicket
                        "P" - Plain""");
            };
        }
    }
}
