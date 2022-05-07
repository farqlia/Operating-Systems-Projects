package simulation_2.algorithms;

import java.util.*;

public class SSTF extends Scheduler {

    private final List<Request> requests;
    private Request currRequest;

    public SSTF(){
        this.requests = new ArrayList<>();
    }

    @Override
    public void refresh(){
        currRequest = null;
    }

    @Override
    public void addRequest(Request request) {
        requests.add(request);
    }

    @Override
    public Optional<Request> nextRequest() {
        // Finds the next request to be served
        if (currRequest == null){
            currRequest = getNearestRequest();
        }
        if (currRequest != null){
            if (currRequest.getPosition() > getPosition()) incPosition();
            else if (currRequest.getPosition() < getPosition()) decPosition();
            else {
                requests.remove(currRequest);
                Optional<Request> optRequest = Optional.of(currRequest);
                currRequest = null;
                return optRequest;
            }
        }
        return Optional.empty();
    }

    private Request getNearestRequest(){
        Comparator<Request> comp = Comparator.comparing(r -> Math.abs(getPosition() - r.getPosition()));
        requests.sort(comp);
        return requests.isEmpty() ? null : requests.get(0);
    }


    @Override
    public Collection<Request> getAllRequests() {
        return requests;
    }

    @Override
    public boolean hasRequests() {
        return (!requests.isEmpty() || currRequest != null);
    }
}
