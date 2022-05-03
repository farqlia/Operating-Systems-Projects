package simulation_2.strategies;

import simulation_2.algorithms.Request;
import simulation_2.algorithms.AbstractScheduler;

import java.util.Collection;
import java.util.Optional;

public abstract class StrategyRT extends AbstractScheduler {

    // Handles the logic of processing real-time
    // requests
    // Is empty if there is no real-time
    // request in the queue
    protected AbstractScheduler scheduler;
    private int numOfRejectedRequests;

    public StrategyRT(AbstractScheduler scheduler){
        this.scheduler = scheduler;
        this.numOfRejectedRequests = 0;
    }

    //protected abstract Optional<Request> nextPriorityRequest();

    //protected abstract Request getRequest();

    protected void decrementDeadlines(){
        scheduler.getAllRequests().stream().filter(Request::isPriorityRequest)
                .forEach(Request::decrementDeadline);
    }

    public Collection<Request> getAllRequests(){
        return scheduler.getAllRequests();
    }

    @Override
    public int getPosition(){
        return scheduler.getPosition();
    }

    @Override
    public void addRequest(Request request) {
        scheduler.addRequest(request);
    }

    @Override
    public boolean hasRequests() {
        return scheduler.hasRequests();
    }

    @Override
    public int getNumOfRejectedRequests(){
        return numOfRejectedRequests;
    }

    protected void setRequestAsRejected(Request request){
        System.out.println("--------------------------------------");
        System.out.println("[HEAD]" + getPosition() + " [REJECTED] : " + request);
        System.out.println("--------------------------------------");
        numOfRejectedRequests++;
        scheduler.getAllRequests().remove(request);
    }

}
