package simulation_2.algorithms;

import java.util.*;

public class SCAN extends Scheduler {

    private List<Request> requests;
    private ScanBase scanner;
    int discSize;

    public SCAN(int discSize){
        this.requests = new LinkedList<>();
        this.scanner = new ScanBase(this);
        this.discSize = discSize;
    }

    @Override
    public void addRequest(Request request){
        requests.add(request);
    }

    @Override
    public Optional<Request> nextRequest() {
        if (getPosition() == 0) scanner.setDirection(Direction.RIGHT);
        else if (getPosition() == discSize) scanner.setDirection(Direction.LEFT);
        return scanner.nextRequest();
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
