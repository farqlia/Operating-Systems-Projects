package simulation_2.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Disc {

    // This is probably unnecessary
    private final List<Integer> waitingTimes;
    private final AbstractScheduler abstractScheduler;
    private final int size;

    private int numOfPriorityRequest;
    private int numOfRequests;
    private int numOfCylinderHeadMoves;

    private Request currRequest;

    public Disc(AbstractScheduler abstractScheduler, int size){
        this.abstractScheduler = abstractScheduler;
        this.size = size;
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
            currRequest.setServiceTime(numOfCylinderHeadMoves);
        } else {
            currRequest = null;
        }
        numOfCylinderHeadMoves++;
    }

    public Request getCurrRequest() {
        return currRequest;
    }

    public int size(){return size;};

    public int getNumOfHeadMoves() {
        return numOfCylinderHeadMoves;
    }

    public List<Integer> getWaitingTimes() {
        return waitingTimes;
    }


    public int getNumOfRealizedRequests() {
        return numOfRequests;
    }

    public int getNumOfRequests() {
        return numOfRequests + abstractScheduler.getNumOfRejectedRequests();
    }

    public int getNumOfProcessedPR() {
        return numOfPriorityRequest;
    }

    public int getHeadPosition(){
        return abstractScheduler.getPosition();
    }

    public boolean isDone(){
        return !abstractScheduler.hasRequests();
    }
}
