package simulation_1.schedulers;

import simulation_1.processing.Process_;

import java.util.*;

public class SJF implements Scheduler{

    // Compare by completion time and maybe by arrival time then?
    Comparator<Process_> comp = Comparator.comparing(Process_::getCompTime,
            Integer::compareTo).thenComparing(Process_::getId, Integer::compareTo);
    TreeSet<Process_> processes = new TreeSet<>(comp);
    Process_ process;

    @Override
    public void addProcess(Process_ process) {
        processes.add(process);
    }

    @Override
    public Process_ nextProcess() {

        // Non-preemptive SJF, so we wait for the process to finish
        if (process == null){
            process = processes.pollFirst();
        }

        while (process != null && process.isTerminated()){
            // Retrieve new process
            process = processes.pollFirst();

        }
        return process;
    }

    @Override
    public boolean isDone() {
        return processes.isEmpty();
    }

    @Override
    public int getNumOfProcesses() {
        return processes.size();
    }

}
