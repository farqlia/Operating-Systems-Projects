import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.junit.jupiter.api.Test;

public class StandardDeviationTest {

    @Test
    void shouldTest(){
        StandardDeviation sD = new StandardDeviation();
        System.out.println(sD.evaluate(new double[]{5, 6, 8, 9}));
    }

}
