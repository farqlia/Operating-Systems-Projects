import simulation_1.processing.Process_;
import simulation_1.processing.SimpleProcess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.TreeSet;

public class SortedSetTest {

    @Test
    void test(){
        TreeSet<Process_> treeSet = new TreeSet<>(Comparator.comparing(Process_::getCompTime,
                Integer::compareTo).thenComparing(Process_::getId, Integer::compareTo));

        treeSet.add(new SimpleProcess(0, 0, 10));
        treeSet.add(new SimpleProcess(1, 0, 20));
        Assertions.assertTrue(treeSet.add(new SimpleProcess(2, 0, 10)));

        Process_ exp = treeSet.pollFirst();

        Assertions.assertEquals(10, exp.getCompTime());
        Assertions.assertEquals(0, exp.getId());

        exp = treeSet.pollFirst();

        Assertions.assertEquals(10, exp.getCompTime());
        Assertions.assertEquals(2, exp.getId());






    }

}
