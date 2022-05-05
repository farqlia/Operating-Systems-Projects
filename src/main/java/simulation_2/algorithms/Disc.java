package simulation_2.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Disc {

    private final List<Integer> waitingTimes;
    private final Scheduler scheduler;
    private final int size;

    private int numOfPriorityRequest;
    private int numOfRequests;
    private int numOfHeadMoves;

    private Request currRequest;
    private int prevPosition;

    public Disc(Scheduler scheduler, int size){
        this.scheduler = scheduler;
        this.size = size;
        waitingTimes = new ArrayList<>();
    }

    public void process(){

        Optional<Request> request = scheduler.nextRequest();

        if (request.isPresent()){

            waitingTimes.add(numOfHeadMoves - request.get().getArrTime());
            if (request.get().isPriorityRequest()){
                numOfPriorityRequest++;
            }
            numOfRequests++;
            currRequest = request.get();
            currRequest.setServiceTime(numOfHeadMoves);

            if (PrintStatistics.print){
                scheduler.getAllRequests().forEach(x -> System.out.print(x.getPosition() + " "));
                System.out.println();
            }
        } else {
            currRequest = null;
        }

        numOfHeadMoves += (prevPosition == scheduler.getPosition() ? 0 : 1);
        prevPosition = scheduler.getPosition();

    }

    public Request getCurrRequest() {
        return currRequest;
    }

    public int size(){return size;};

    public int getNumOfHeadMoves() {
        return numOfHeadMoves;
    }

    public List<Integer> getWaitingTimes() {
        return waitingTimes;
    }

    public int getNumOfRealizedRequests() {
        return numOfRequests;
    }

    public int getNumOfRequests() {
        return numOfRequests + scheduler.getNumOfRejectedRequests();
    }

    public int getNumOfProcessedPR() {
        return numOfPriorityRequest;
    }

    public int getHeadPosition(){
        return scheduler.getPosition();
    }
}
