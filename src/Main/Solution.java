package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class Solution {
    /**
    Method returns minimal cost of the creature's movement from the top-left corner to the down-right one.
     */
    public static int getResult(String field, String creature) throws IOException, URISyntaxException {
        int[][] matrix = makeMatrix(field, creature);
        return findBestPath(matrix, 0, 0) - matrix[0][0];

    }

    /**
    Method makes a path to data.config depends on OS.
    */
    private static String createConfigPath() throws URISyntaxException, IOException {
        String os = System.getProperty("os.name").toLowerCase();
        String path = Path.of(Solution.class
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI()
                        .getPath())
                .getParent()
                .toString();
        path += switch (os) {
            case "windows" -> "\\data.config";
            case "mac", "linux" -> "/data.config";
            default -> throw new IOException();
        };
        return path;
    }

    /**
    Method creates matrix of a game field with every tile's move cost.
    Throws exceptions if .config contains incorrect data.
     */
    private static int[][] makeMatrix(String field, String creature) throws IOException, URISyntaxException {
        int[] config = readConfig(creature);
        int[][] matrix = new int[4][4];
        int x = 0, y = 0, count = 0;
        for (char temp : field.toCharArray()) {
            matrix[y][x] = switch (temp) {
                case 'S' -> config[0];
                case 'W' -> config[1];
                case 'T' -> config[2];
                case 'P' -> config[3];
                default -> throw new IOException("Illegal tale type!");
            };
            y = ++count / 4;
            x = count % 4;
        }
        return matrix;
    }

    /**
    Method reads data form .config file. Throws exceptions if file doesn't exist
    or contains incorrect data. Example for .config file:

    HUMAN 5 2 3 1
    SWAMPER 2 2 5 2
    WOODMAN 3 3 2 2
     */
    private static int[] readConfig(String creature) throws IOException, URISyntaxException {
        try (BufferedReader reader = new BufferedReader(new FileReader(createConfigPath()))) {
            String[] temp = null;
            int[] result = new int[4];
            while (reader.ready()) {
                temp = reader.readLine().split(" ");
                if (temp[0].equalsIgnoreCase(creature)) break;
            }
            assert temp != null && temp.length == 5 : "config error!";
            for (int i = 0; i < 4; i++) result[i] = Integer.parseInt(temp[i + 1]);
            return result;
        }
    }

    /**
    Method recursively calculates and returns the shortest way to the last tile of the matrix.
     */
    private static int findBestPath(int[][] matrix, int x, int y) {
        if (x == 3 && y == 3) return matrix[y][x];
        int goRight = 0, goDown = 0;
        if (x < 3) goRight = findBestPath(matrix, x + 1, y) + matrix[y][x];
        if (y < 3) goDown = findBestPath(matrix, x, y + 1) + matrix[y][x];
        if (goDown != 0 && goRight != 0) return Math.min(goDown, goRight);
        else return Math.max(goDown, goRight);
    }
}
