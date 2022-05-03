package simulation_2.algorithms;

import java.util.*;

public class SSTF extends AbstractScheduler {

    private final List<Request> requests;
    private Request currRequest;

    public SSTF(){
        this.requests = new ArrayList<>();
    }

    @Override
    public void refresh(){
        currRequest = getNearestRequest();
    }

    @Override
    public void addRequest(Request request) {
        requests.add(request);
    }

    @Override
    public Optional<Request> nextRequest() {
        // Finds the next request to be served
        if (currRequest == null){
            refresh();
        } else {
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
        // Element with the highest priority will be at the end
        requests.sort(comp.reversed());
        return requests.get(requests.size() - 1);
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
