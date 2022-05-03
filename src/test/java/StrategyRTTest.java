import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import simulation_2.algorithms.AbstractScheduler;
import simulation_2.algorithms.Disc;
import simulation_2.algorithms.Request;
import simulation_2.algorithms.SCAN;
import simulation_2.processgenerating.Generator;
import simulation_2.strategies.EDF;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StrategyRTTest {

    List<Request> requests = new ArrayList<>();
    Request r1 = new Request(0, 0, 0);
    Request r2 = new Request(150, 50, 0);
    Request r3 = new Request(75, 90, 10);
    Request r4 = new Request(120, 90, 40);
    Request r5 = new Request(100, 100, 0);

    AbstractScheduler scheduler = new EDF(new SCAN(200));
    Disc disc = new Disc(scheduler, 200);
    Generator generator;

    void setUp(){
        requests.add(r1);
        requests.add(r2);
        requests.add(r3);
        requests.add(r4);
        requests.add(r5);
        generator = new TestGenerator(new ArrayList<>(requests), disc);
    }

    private void process(){

        do {
            if (generator.hasNext()){
                Request r = generator.next();
                if (r != null) scheduler.addRequest(r);
            }
            disc.process();
        } while (scheduler.hasRequests());
    }

    @Test
    void test(){
        process();
        requests.forEach(x -> System.out.println(x.getServiceTime()));
    }

    //@ParameterizedTest
    //@MethodSource
    Stream<Arguments> getExpectedTimes(){
        return Stream.of(
                Arguments.of(0),
                Arguments.of()
        );
    }

}
