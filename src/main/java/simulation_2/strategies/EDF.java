package simulation_2.strategies;

import simulation_2.algorithms.AbstractScheduler;
import simulation_2.algorithms.Request;
import java.util.Iterator;
import java.util.Optional;

// In this strategy we choose the request with the earliest deadline
// and move the head towards it, even if the deadline can't be met
public class EDF extends StrategyRT{

    private Request request;

    public EDF(AbstractScheduler abstractScheduler) {
        super(abstractScheduler);
    }

    private Optional<Request> realizeRequest(){
        scheduler.getAllRequests().remove(request);
        return Optional.of(request);
    }

    private void findNextAndRemoveNotFeasible(){
        Request r = null;
        int minDeadline = 0;
        Iterator<Request> iter = scheduler.getAllRequests().iterator();
        while (iter.hasNext()){
            r = iter.next();
            if (r.isPriorityRequest()){
                if (r.getCurrDeadline() < minDeadline){
                   request = r;
                } else if (r.getCurrDeadline() <= 0){
                    // We have to remove all the requests which deadlines are missed
                    iter.remove();
                    setRequestAsRejected(r);
                }
            }
        }

    }


    @Override
    public Optional<Request> nextRequest() {

        // This must be called in a very specific moment, that is, when the priority request
        // has just been realized or rejected in the previous call
        if (request != null && !scheduler.getAllRequests().contains(request)){
            request = null;
            scheduler.refresh();
        }

        if (request == null){
            findNextAndRemoveNotFeasible();
            if (request != null) System.out.println("[SWITCHED TO EDF] : " + getPosition());
        }

        if (request != null){

            if (request.getPosition() > scheduler.getPosition()) {
                scheduler.incPosition();
            } else if (request.getPosition() < scheduler.getPosition()){
                scheduler.decPosition();
            } else {
                return realizeRequest();
            }

            if (request.getCurrDeadline() == 0){
                setRequestAsRejected(request);
            }
            decrementDeadlines();

        } else {
            // Call the normal algorithm
            return scheduler.nextRequest();
        }

        return Optional.empty();
    }

    @Override
    public boolean hasRequests(){
        return scheduler.hasRequests();
    }

}
