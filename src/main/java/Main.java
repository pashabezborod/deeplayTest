import java.io.IOException;

public class Main {
    public static void main(String... args) {
        if (args.length != 2) {
            System.err.println("""
                    \nNeed two arguments:
                    1. Game field (16-letters string)
                    2. Creature type (Human, Swamper or Woodman)""");
            System.exit(1);
        }
        if (args[0].length() != 16) {
            System.err.println("\nField must be 4X4 tales (16 letters)");
            System.exit(1);
        }
        String field = args[0], creature = args[1];

        try {
            System.out.println("Calculating results for " + creature);
            System.out.println("Shortest way costs " + Solution.getResult(field, creature));
        } catch (IOException e) {
            System.err.println("Data reading error. Did you mount data.config?");
            System.exit(1);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
