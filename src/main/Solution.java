package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class Solution {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Need two arguments:\n1.Game Filed\n2. Creature type (Human, Swamper or Woodman");
            System.exit(1);
        }
        try {
            System.out.println(getResult(args[0], args[1]));
        } catch (Exception e) {
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
     * Method makes a path to data.config depends on OS.
     */
    private static String createPath() throws URISyntaxException {
        return Path.of(Solution.class
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI()
                        .getPath())
                .toString();
    }

    /**
     * Method creates matrix of a game field with every tile's move cost.
     * Throws exceptions if .config contains incorrect data.
     */
    private static int[][] makeMatrix(String field, String creature) throws IOException, URISyntaxException {
        readConfig(creature);
        int[][] matrix = new int[4][4];
        int x = 0, y = 0, count = 0;
        for (char temp : field.toCharArray()) {
            matrix[y][x] = switch (temp) {
                case 'S' -> CreatureSpeed.SWAMP.getValue();
                case 'W' -> CreatureSpeed.WATER.getValue();
                case 'T' -> CreatureSpeed.THICKET.getValue();
                case 'P' -> CreatureSpeed.PLAIN.getValue();
                default -> throw new IOException("Illegal tale type!");
            };
            y = ++count / 4;
            x = count % 4;
        }
        return matrix;
    }

    /**
     * Method reads data form .config file. Throws exceptions if file doesn't exist
     * or contains incorrect data. Example for .config file:
     *
     * HUMAN 5 2 3 1
     * SWAMPER 2 2 5 2
     * WOODMAN 3 3 2 2
     */
    private static void readConfig(String creature) throws IOException, URISyntaxException {
        try (BufferedReader reader = new BufferedReader(new FileReader(
                createPath() + File.separator + "data" + File.separator + "data.config"))) {
            String[] temp = null;
            while (reader.ready()) {
                temp = reader.readLine().split(" ");
                if (temp[0].equalsIgnoreCase(creature)) break;
            }
            if (temp == null || temp.length != 5) throw new IOException("config file error!");
            for (int i = 0; i < 4; i++) CreatureSpeed.values()[i].setValue(Integer.parseInt(temp[i + 1]));
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
     * Enum with all types of tiles
     */

    private enum CreatureSpeed {
        SWAMP,
        WATER,
        THICKET,
        PLAIN;
        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
