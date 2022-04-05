package simulation_2.strategies;

import simulation_2.algorithms.Request;
import simulation_2.algorithms.Scheduler;

import java.util.Collection;

public abstract class StrategyRT extends Scheduler {

    // Handles the logic of processing real-time
    // requests
    // Is empty if there is no real-time
    // request in the queue
    protected Scheduler scheduler;

    public Collection<Request> getAllRequests(){
        return scheduler.getAllRequests();
    }
}
