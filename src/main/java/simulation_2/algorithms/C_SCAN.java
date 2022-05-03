package simulation_2.algorithms;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class C_SCAN extends AbstractScheduler{

    private List<Request> requests;
    private ScanBase scanner;
    private int discSize;

    public C_SCAN(int discSize){
        this.requests = new LinkedList<>();
        this.discSize = discSize;
        this.scanner = new ScanBase(this, discSize);
    }

    @Override
    public void addRequest(Request request){
        requests.add(request);
    }

    private void resetPositionIfNeeded(){
        if (getPosition() == discSize){
            scanner.setDirection(Direction.JUMP_TO_0);
        }
    }

    @Override
    public Optional<Request> nextRequest() {
        Optional<Request> optR = scanner.nextRequest();
        resetPositionIfNeeded();
        return optR;
    }

    @Override
    public Collection<Request> getAllRequests() {
        return requests;
    }

    @Override
    public boolean hasRequests() {
        return !requests.isEmpty();
    }

}
