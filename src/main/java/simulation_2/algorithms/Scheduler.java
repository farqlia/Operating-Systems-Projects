package simulation_2.algorithms;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public abstract class Scheduler {

    // Current position of the cylinder head
    private int position = -1;

    public abstract void addRequest(Request request);

    public abstract Optional<Request> nextRequest();

    public abstract Collection<Request> getAllRequests();

    public void incPosition(){position++;}

    public void decPosition(){position--;}

    public int getPosition(){return position;}

}
