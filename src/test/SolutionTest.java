package test;

import main.Solution;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

public class SolutionTest {
    @Test
    public void getResult() {
        int expected = 10;
        int actual;
        try {
            actual = Solution.getResult("STWSWTPPTPTTPWPP", "Human");
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getRandomResults() {
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
            try {
                Solution.getResult(builder.toString(), Creature.values()[random.nextInt(2)].value);
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }
}