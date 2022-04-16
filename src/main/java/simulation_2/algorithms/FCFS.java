package simulation_2.algorithms;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Optional;

public class FCFS extends AbstractScheduler {

    private final Deque<Request> queue = new ArrayDeque<>();

    @Override
    public void addRequest(Request request) {
        queue.addLast(request);
    }

    @Override
    public Optional<Request> nextRequest() {

        Request request = queue.peekFirst();

        if (request.getPosition() > getPosition()) {
            incPosition();
        } else if (request.getPosition() < getPosition()){
            decPosition();
        } else {
            return Optional.ofNullable(queue.pollFirst());
        }
        return Optional.empty();
    }

    @Override
    public Collection<Request> getAllRequests() {
        return queue;
    }

    @Override
    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}
