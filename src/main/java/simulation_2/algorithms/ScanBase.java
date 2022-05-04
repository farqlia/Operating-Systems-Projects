package simulation_2.algorithms;

import java.util.Collection;
import java.util.Optional;

public class ScanBase extends AbstractScheduler{

    private AbstractScheduler scheduler;
    private int discSize;
    private Direction direction;

    public ScanBase(AbstractScheduler scheduler,
                    int discSize){
        this.scheduler = scheduler;
        this.discSize = discSize;
        this.direction = Direction.RIGHT;
    }

    public void reposition(){
        if (scheduler.getPosition() <= 0){
            direction = Direction.RIGHT;
        } else if (scheduler.getPosition() > discSize){
            direction = Direction.LEFT;
        }
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public Direction getDirection(){
        return direction;
    }

    public void move(){
        reposition();
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
