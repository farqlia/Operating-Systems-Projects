package simulation_2.strategies;

import simulation_2.algorithms.Request;

import java.util.Comparator;
import java.util.Optional;

public class EDF extends StrategyRT{

    private Request request;

    @Override
    public void addRequest(Request request) {
        scheduler.addRequest(request);
    }

    @Override
    public Optional<Request> nextRequest() {

        if (request == null){
            Optional<Request> optRequest = scheduler.getAllRequests()
                    .stream().filter(Request::isPriorityRequest)
                    .min(Comparator.comparingInt(Request::getDeadline));

            // If its deadline can't be met
            if (optRequest.isPresent() &&
                    Math.abs(scheduler.getPosition() - optRequest.get().getPosition()) > optRequest.get().getDeadline()){
                request = optRequest.get();
                request.setMissedDeadline(true);
            }
        }

        if (request != null){

            if (request.getPosition() > scheduler.getPosition()) {
                scheduler.incPosition();
            } else if (request.getPosition() < scheduler.getPosition()){
                scheduler.decPosition();
            } else {
                scheduler.getAllRequests().remove(request);
                Optional<Request> result = Optional.of(request);
                request = null;
                return result;
            }
        } else {
            return scheduler.nextRequest();
        }

        return Optional.empty();
    }
}
