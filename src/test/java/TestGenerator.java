
import simulation_2.algorithms.Disc;
import simulation_2.algorithms.Request;
import simulation_2.processgenerating.Generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TestGenerator implements Generator {

    List<Request> requests;
    private Disc disc;

    TestGenerator(List<Request> requests, Disc disc){
        this.requests = requests;
        this.disc = disc;
    }

    @Override
    public Request next() {
        Optional<Request> optRequest = requests.stream()
                .filter(r -> (r.getArrTime() == disc.getNumOfHeadMoves())).findFirst();
        optRequest.ifPresent(value -> requests.remove(value));
        return optRequest.orElse(null);
    }

    @Override
    public boolean hasNext() {
        return !requests.isEmpty();
    }
}
