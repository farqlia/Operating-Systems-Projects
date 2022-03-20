package lab1.processing;

import lab1.schedulers.Scheduler;

public class Processor {

    Scheduler scheduler;
    Process_ currProcess;
    String name;
    int current;
    int countContextSwitch;

    public Processor(Scheduler scheduler, String name){
        this.scheduler = scheduler;
        this.name = name;
    }

    public boolean process(){

        Process_ next = scheduler.nextProcess();

        // This probably generates time gap between algorithms
        if (next == null) return false;

        // We should perform context switch for a different process (compare references)
        if (next != currProcess){
                //System.out.println("[CONTEXT SWITCHING]");
                countContextSwitch++;
        }

        currProcess = next;

        // Process is first time visited
        if (currProcess.getCompTime() == currProcess.getLeftTime()){
            current++;
        }

        currProcess.doJob(1);

        if (currProcess.isTerminated()) {
            current--;
        }

        return true;
    }

    public Process_ getCurrProcess(){return currProcess;}

    // Number of started, but not terminated processes
    public int getNumOfPrs(){return current;}

    public int getNumOfContextSwitch(){return countContextSwitch;}


}
