package simulation_1.donotlookhere;

import simulation_1.processing.Process_;
import simulation_1.schedulers.Scheduler;

import java.util.LinkedList;
import java.util.List;

public class TrapScheduler implements Scheduler {

    List<Process_> p = new LinkedList<>();

    @Override
    public void addProcess(Process_ process) {
        p.add(process);
    }

    @Override
    public Process_ nextProcess() {
        return p.isEmpty() ? null : p.remove(0);
    }

    @Override
    public boolean isDone() {
        return p.isEmpty();
    }

    @Override
    public int getNumOfProcesses() {
        return 0;
    }
}
