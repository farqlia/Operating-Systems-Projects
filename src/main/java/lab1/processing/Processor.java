package lab1.processing;

import lab1.schedulers.Scheduler;

public class Processor {

    private final Scheduler scheduler;
    Process_ currProcess;
    int countContextSwitch;

    public Processor(Scheduler scheduler){
        this.scheduler = scheduler;
    }

    public boolean process(){

        Process_ next = scheduler.nextProcess();

        // We should perform context switch for a different process (compare references)
        if (next != currProcess){
                countContextSwitch++;
        }

        currProcess = next;

        currProcess.doJob();
        return true;
    }

    public Process_ getCurrProcess(){return currProcess;}

    public int getNumOfContextSwitch(){return countContextSwitch;}


}
