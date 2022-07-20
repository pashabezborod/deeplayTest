import java.io.*;

public class Solution {

    /**
     * Method returns minimal cost of the creature's movement from the top-left corner to the down-right one.
     */
    public static int getResult(String field, String creature) throws IOException {
        int[][] matrix;
        try (InputStream is = new FileInputStream("/app/data/data.config")) {
            matrix = makeMatrix(field, readConfig(creature, is));
        }
        return findBestPath(matrix, 0, 0) - matrix[0][0];
    }

    public static int getResult(String field, String creature, InputStream is) throws IOException {
        int[][] matrix = makeMatrix(field, readConfig(creature, is));
        return findBestPath(matrix, 0, 0) - matrix[0][0];
    }

    /**
     * Method creates matrix of a game field with every tile's move cost.
     * Throws exceptions if .config contains incorrect data.
     */
    private static int[][] makeMatrix(String field, CreatureSpeedData speedData) {
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
     * Method reads data form .config file. Throws IOException if file doesn't exist
     * or IOException if it contains incorrect data. Example for .config file:
     * HUMAN 5 2 3 1
     * SWAMPER 2 2 5 2
     * WOODMAN 3 3 2 2
     */
    private static CreatureSpeedData readConfig(String creature, InputStream is) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String[] data = null;
            while (reader.ready()) {
                String[] temp = reader.readLine().split(" ");
                if (temp[0].equalsIgnoreCase(creature)) {
                    data = temp;
                    break;
                }
            }
            if (data == null) throw new IllegalArgumentException("\nCreature " + creature + " has not been found " +
                                                                 "in the configuration file.");
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
