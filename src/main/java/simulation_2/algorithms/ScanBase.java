package simulation_2.algorithms;

import java.util.Collection;
import java.util.Optional;

public class ScanBase extends Scheduler {

    private Scheduler scheduler;
    private Direction direction;

    public ScanBase(Scheduler scheduler){
        this.scheduler = scheduler;
        this.direction = Direction.RIGHT;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public void move(){
        direction.move(scheduler);
    }

    private Optional<Request> retrieveRequest(){
        return scheduler.getAllRequests()
                .stream().filter(r -> r.getPosition() == scheduler.getPosition())
                .findFirst();
    }

    @Override
    public void addRequest(Request request) {
        scheduler.addRequest(request);
    }

    @Override
    public Optional<Request> nextRequest() {
        Optional<Request> request = retrieveRequest();
        if (!request.isPresent()){
            move();
        } else {
            scheduler.getAllRequests().remove(request.get());
        }
        return request;

    }

    @Override
    public Collection<Request> getAllRequests() {
        return scheduler.getAllRequests();
    }

    @Override
    public boolean hasRequests() {
        return scheduler.hasRequests();
    }
}
