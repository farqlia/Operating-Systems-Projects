package simulation_2.algorithms;

import java.util.function.Consumer;

public enum Direction {

    LEFT(AbstractScheduler::decPosition),
    RIGHT(AbstractScheduler::incPosition),
    JUMP_TO_0(s -> s.moveTo(0));

    private final Consumer<AbstractScheduler> move;
    Direction(Consumer<AbstractScheduler> move) {
        this.move = move;
    }

    public void move(AbstractScheduler scheduler){
        move.accept(scheduler);
    }

}
