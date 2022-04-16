package simulation_2.strategies;

import simulation_2.algorithms.AbstractScheduler;
import simulation_2.algorithms.Request;

import java.util.Comparator;
import java.util.Optional;

// In this strategy we choose the request with the earliest deadline
// and move the head towards it, even if the deadline can't be met
public class EDF extends StrategyRT{

    private Request request;

    public EDF(AbstractScheduler abstractScheduler) {
        super(abstractScheduler);
    }

    @Override
    public void addRequest(Request request) {
        abstractScheduler.addRequest(request);
    }

    @Override
    public int getPosition(){
        return abstractScheduler.getPosition();
    }

    private Optional<Request> realizeRequest(){
        abstractScheduler.getAllRequests().remove(request);
        Optional<Request> result = Optional.of(request);
        request = null;
        return result;
    }

    @Override
    public Optional<Request> nextRequest() {

        if (request == null){
            Optional<Request> optRequest = abstractScheduler.getAllRequests()
                    .stream().filter(Request::isPriorityRequest)
                    .min(Comparator.comparingInt(Request::getDeadline));

            optRequest.ifPresent(value -> request = value);
        }

        if (request != null){

            request.decrementDeadline();

            if (request.getPosition() > abstractScheduler.getPosition()) {
                abstractScheduler.incPosition();
            } else if (request.getPosition() < abstractScheduler.getPosition()){
                abstractScheduler.decPosition();
            } else {
                return realizeRequest();
            }

            if (request.getDeadline() == 0){
                // Set as missed deadline and remove from the list
                setRequestAsRejected(request);
                request = null;
            }

        } else {
            return abstractScheduler.nextRequest();
        }

        return Optional.empty();
    }

    @Override
    public boolean hasRequests(){
        return abstractScheduler.hasRequests();
    }

}
