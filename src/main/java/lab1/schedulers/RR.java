package lab1.schedulers;

import lab1.processing.Process_;

import java.util.ArrayDeque;
import java.util.Deque;

public class RR implements Scheduler {

    final int quanta;
    int interval;
    private Process_ process;
    int longstWaitingTime;

    private final Deque<Process_> processes;

    public RR(int quanta){
        this.quanta = quanta;
        interval = quanta;
        processes = new ArrayDeque<>();
    }

    public RR(){
        this(2);
    }

    @Override
    public void addProcess(Process_ process) {
        processes.offer(process);
    }

    @Override
    public Process_ nextProcess() {

        if (process == null){
            process = processes.pollFirst();
            interval = quanta;
        }

        while (process != null && process.isTerminated()){
            process = processes.pollFirst();
            interval = quanta;
        }

        if (interval == 0){
            // Add current process to the end of the queue
            processes.offer(process);
            // Retrieve next process
            process = processes.pollFirst();
            interval = quanta;
        }
        interval--;

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
