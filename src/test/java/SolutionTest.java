import org.junit.Assert;
import org.junit.Test;
import java.io.InputStream;
import java.util.Random;

public class SolutionTest {
    @Test
    public void testExpectedResult() throws Exception {
        int expected = 10;
        int actual = Solution.getResult("STWSWTPPTPTTPWPP", "Human", getInputStream());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRandomResults() throws Exception {
        String[] creatures = {"Human", "Swamper", "Woodman"};

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
            Solution.getResult(builder.toString(), creatures[random.nextInt(2)], getInputStream());
        }
    }

    private InputStream getInputStream() {
        return getClass().getClassLoader().getResourceAsStream("data.config");
    }

}