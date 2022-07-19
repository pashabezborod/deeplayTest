package test;

import main.Solution;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.Random;

public class SolutionTest {
    String path = Path.of(Solution.class.getResource("").getPath())
                          .getParent()
                          .getParent()
                          .getParent()
                          .getParent()
                          .toString() + File.separator + "data" + File.separator + "data.config";
    @Test
    public void testExpectedResult() throws Exception {
        int expected = 10;
        int actual = Solution.getResult("STWSWTPPTPTTPWPP", "Human", path);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRandomResults() throws Exception {
        enum Creature {
            HUMAN("Human"), SWAMPER("Swamper"), WOODMAN("Woodman");
            public final String value;

            Creature(String value) {
                this.value = value;
            }
        }

        for (int j = 0; j < 100000; j++) {
            StringBuilder builder = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < 16; i++) {
                int temp = random.nextInt(3);
                builder.append(switch (temp) {
                    case 0 -> 'S';
                    case 1 -> 'W';
                    case 2 -> 'T';
                    case 3 -> 'P';
                    default -> throw new RuntimeException("Random failed lol");
                });
            }
            Solution.getResult(builder.toString(), Creature.values()[random.nextInt(2)].value, path);
        }
    }
}