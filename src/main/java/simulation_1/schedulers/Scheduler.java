package simulation_1.schedulers;

import simulation_1.processing.Process_;

public interface Scheduler {

    // Add new process
    void addProcess(Process_ process);

    // Returns or retrieves next process
    Process_ nextProcess();

    boolean isDone();

    int getNumOfProcesses();
}
