package simulation_2.algorithms;


import java.util.*;

public class SSTF extends AbstractScheduler {

    private List<Request> requests;
    Request currRequest;

    @Override
    public void addRequest(Request request) {
        requests.add(request);
    }

    @Override
    public Optional<Request> nextRequest() {
        if (currRequest == null){

        }
    }

    @Override
    public Collection<Request> getAllRequests() {
        return null;
    }

    @Override
    public boolean hasRequests() {
        return false;
    }
}
