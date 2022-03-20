package lab1.schedulers;

import lab1.processing.Process_;

public interface Scheduler {

    // Add new process
    void addProcess(Process_ process);

    // Returns or retrieves next process
    Process_ nextProcess();

    boolean isDone();
}
