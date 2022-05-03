package simulation_2.algorithms;

import java.util.*;

public class SCAN extends AbstractScheduler{

    private List<Request> requests;
    private ScanBase scanner;

    public SCAN(int discSize){
        this.requests = new LinkedList<>();
        this.scanner = new ScanBase(this, discSize);
    }

    @Override
    public void addRequest(Request request){
        requests.add(request);
    }

    @Override
    public Optional<Request> nextRequest() {
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
