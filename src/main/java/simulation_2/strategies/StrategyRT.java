package simulation_2.strategies;

import org.apache.commons.math3.analysis.function.Abs;
import simulation_2.algorithms.Request;
import simulation_2.algorithms.AbstractScheduler;

import java.util.Collection;

public abstract class StrategyRT extends AbstractScheduler {

    // Handles the logic of processing real-time
    // requests
    // Is empty if there is no real-time
    // request in the queue
    protected AbstractScheduler abstractScheduler;
    private int numOfRejectedRequests;

    public StrategyRT(AbstractScheduler abstractScheduler){
        this.abstractScheduler = abstractScheduler;
        this.numOfRejectedRequests = 0;
    }

    public Collection<Request> getAllRequests(){
        return abstractScheduler.getAllRequests();
    }

    @Override
    public boolean hasRequests() {
        return abstractScheduler.hasRequests();
    }

    @Override
    public int getNumOfRejectedRequests(){
        return numOfRejectedRequests;
    }

    protected void setRequestAsRejected(Request request){
        numOfRejectedRequests++;
        request.setMissedDeadline(true);
        abstractScheduler.getAllRequests().remove(request);
    }

}
