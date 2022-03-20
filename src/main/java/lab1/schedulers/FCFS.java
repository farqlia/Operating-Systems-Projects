package lab1.schedulers;

import lab1.processing.Process_;

import java.util.ArrayDeque;
import java.util.Deque;

public class FCFS implements Scheduler{

    private final Deque<Process_> processQueue =
            new ArrayDeque<>();
    Process_ process;


    @Override
    public void addProcess(Process_ process) {
        processQueue.offer(process);
    }

    @Override
    public Process_ nextProcess() {
        process = processQueue.peek();

        while (process != null && process.isTerminated()){
            processQueue.poll();
            // Retrieve new process
            process = processQueue.peek();
        }

        return process;
    }

    @Override
    public boolean isDone() {
        return processQueue.isEmpty();
    }

    public int getMaxStarvingTime() {
        return 0;
    }

}
