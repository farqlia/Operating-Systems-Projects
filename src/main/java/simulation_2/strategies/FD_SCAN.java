package simulation_2.strategies;

import simulation_2.algorithms.*;

import java.util.Iterator;
import java.util.Optional;

public class FD_SCAN extends StrategyRT {

    //private Request curr
    private ScanBase scanner;
    private Request request;

    public FD_SCAN(Scheduler abstractScheduler, int discSize) {
        super(abstractScheduler);
        this.scanner = new ScanBase(abstractScheduler);
    }

    private void removeNotFeasible(){
        Request r;
        Iterator<Request> iter = scheduler.getAllRequests().iterator();
        while (iter.hasNext()){
            r = iter.next();
            if (r.isPriorityRequest() && Math.abs(r.getPosition() - getPosition()) > r.getCurrDeadline()){
                iter.remove();
                setRequestAsRejected(r);
            }
        }
    }

    private void findNextAndRemoveNotFeasible(){
        Request r = null;
        int maxDistance = 0;
        int d = 0;
        Iterator<Request> iter = scheduler.getAllRequests().iterator();
        while (iter.hasNext()){
            r = iter.next();
            if (r.isPriorityRequest()){
                if ((d = Math.abs(r.getPosition() - getPosition())) <= r.getCurrDeadline()){
                    if ((d > maxDistance)){
                        request = r;
                        maxDistance = d;
                    }
                } else {
                    // We have to remove all the requests that are not feasible
                    iter.remove();
                    setRequestAsRejected(r);
                }
            }
        }
    }


    private void setDirection(){
        scanner.setDirection(request.getPosition() > getPosition() ? Direction.RIGHT : Direction.LEFT);
    }

    @Override
    public Optional<Request> nextRequest() {

        if (request != null && !scheduler.getAllRequests().contains(request)){
            request = null;
            scheduler.refresh();
        }

        if (request == null){
            findNextAndRemoveNotFeasible();
            if (request != null && PrintStatistics.print){
                System.out.println("[SWITCHED TO FD-SCAN][HEAD] " + getPosition());
            }
        }

        decrementDeadlines();
        removeNotFeasible();

        if (request != null){

            setDirection();
            Optional<Request> optR = scanner.nextRequest();


            return optR;
        } else {
            // Call the normal algorithm
            return scheduler.nextRequest();
        }
    }
}
