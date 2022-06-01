import java.util.HashSet;
import java.util.Arrays;
import java.util.Random;

public class Solution {
    static int[][] matrix;
    static HashSet<MyStupidGraph> graphs;

    //Я знаю, что эта задача решается через графы. Знаю, что я плохо разбираюсь в графах. Но я стараюсь.
    private static class MyStupidGraph {
        int x, y, sum;
        MyStupidGraph unvisitedDown, unvisitedRight;

        public MyStupidGraph(int x, int y, int parentSum) {
            this.x = x;
            this.y = y;
            sum = parentSum + matrix[y][x];
            if (x < 3) unvisitedRight = new MyStupidGraph(x + 1, y, sum);
            if (y < 3) unvisitedDown = new MyStupidGraph(x, y + 1, sum);
            if (unvisitedDown == null && unvisitedRight == null) {
                graphs.add(this);
            }

        }
    }

    //Создаю числовую матрицу с затратами на каждый ход, просчитываю возможные варианты
    //(на поле 4Х4 нелогичны варианты идти вврех или влево)
    //и выбираю наименее затратный.
    public static int getResult(String string, String creature) throws IllegalArgumentException {
        byte sPrice, wPrice, tPrice, pPrice;
        switch (creature.charAt(0)) {
            case 'H' -> {
                sPrice = 5;
                wPrice = 2;
                tPrice = 3;
                pPrice = 1;
            }
            case 'S' -> {
                sPrice = wPrice = pPrice = 2;
                tPrice = 5;
            }
            case 'W' -> {
                sPrice = wPrice = 3;
                tPrice = pPrice = 2;
            }
            default -> throw new IllegalArgumentException("Illegal creature type!");
        }
        int x = 0, y = 0, count = 0;
        matrix = new int[4][4];
        for (char temp : string.toCharArray()) {
            matrix[y][x] = switch (temp) {
                case 'S' -> sPrice;
                case 'W' -> wPrice;
                case 'T' -> tPrice;
                case 'P' -> pPrice;
                default -> throw new IllegalArgumentException("Illegal tale type!");
            };
            y = ++count / 4;
            x = count % 4;
        }
        graphs = new HashSet<>();
        new MyStupidGraph(0, 0, 0);
        int result = Integer.MAX_VALUE;
        for (MyStupidGraph graph : graphs) result = Math.min(graph.sum, result);
        return result - matrix[0][0];

    }

    //Метод Main использовал для тестирования. На всякий случай сохранил.
    //Я пока мало что знаю о создании UNIT-тестов, поэтому адекватного тестирования предоставить не могу.
    //В ближайшем будущем планирую заняться этой темой.

    /*
    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            Random random = new Random();
            int t = random.nextInt(3);
            char c = switch (t) {
                case 0 -> 'S';
                case 1 -> 'T';
                case 2 -> 'W';
                case 3 -> 'P';
                default -> throw new IllegalArgumentException();
            };
            builder.append(c);
        }
        System.out.println(getResult(builder.toString(), "Human"));
        for(int[] temp : matrix) System.out.println(Arrays.toString(temp));

        getResult("STWSWTPPTPTTPWPP", "Human");
    }
     */
}
