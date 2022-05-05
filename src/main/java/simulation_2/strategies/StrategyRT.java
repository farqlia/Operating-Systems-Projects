package simulation_2.strategies;

import simulation_2.algorithms.PrintStatistics;
import simulation_2.algorithms.Request;
import simulation_2.algorithms.Scheduler;

import java.util.Collection;

public abstract class StrategyRT extends Scheduler {

    // Handles the logic of processing real-time
    // requests
    // Is empty if there is no real-time
    // request in the queue
    protected Scheduler scheduler;
    private int numOfRejectedRequests;

    public StrategyRT(Scheduler scheduler){
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
        if (PrintStatistics.print){
            System.out.println("--------------------------------------");
            System.out.println("[HEAD]" + getPosition() + " [REJECTED] : " + request);
            System.out.println("--------------------------------------");
        }
        numOfRejectedRequests++;
        scheduler.getAllRequests().remove(request);
    }

}
