package simulation_2.algorithms;

import java.util.function.Consumer;

public enum Direction {

    LEFT(Scheduler::decPosition),
    RIGHT(Scheduler::incPosition),
    JUMP_TO_0(s -> s.moveTo(0));

    private final Consumer<Scheduler> move;
    Direction(Consumer<Scheduler> move) {
        this.move = move;
    }

    public void move(Scheduler scheduler){
        move.accept(scheduler);
    }

}
