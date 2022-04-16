package simulation_2.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Disc {

    private final List<Integer> waitingTimes;
    private final AbstractScheduler abstractScheduler;

    private int numOfMissedDeadlines;
    private int numOfPriorityRequest;
    private int numOfRequests;
    private int numOfCylinderHeadMoves;

    private Request currRequest;

    public Disc(AbstractScheduler abstractScheduler){
        this.abstractScheduler = abstractScheduler;
        waitingTimes = new ArrayList<>();
    }

    public void process(){

        Optional<Request> request = abstractScheduler.nextRequest();

        if (request.isPresent()){
            waitingTimes.add(numOfCylinderHeadMoves - request.get().getArrTime());
            if (request.get().isPriorityRequest()){
                numOfPriorityRequest++;
            }
            numOfRequests++;
            currRequest = request.get();
        } else {
            currRequest = null;
        }
        numOfCylinderHeadMoves++;
    }

    public Request getCurrRequest() {
        return currRequest;
    }

    public int getNumOfCylinderHeadMoves() {
        return numOfCylinderHeadMoves;
    }

    public List<Integer> getWaitingTimes() {
        return waitingTimes;
    }

    public int getNumOfMissedDeadlines() {
        return abstractScheduler.getNumOfRejectedRequests();
    }

    public int getNumOfRequests() {
        return numOfRequests;
    }

    public int getCylinderHeadPosition(){
        return abstractScheduler.getPosition();
    }

    public boolean isDone(){
        return !abstractScheduler.hasRequests();
    }
}
