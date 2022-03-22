package lab1.processing;

import lab1.schedulers.Scheduler;

public class Processor {

    Scheduler scheduler;
    Process_ currProcess;
    String name;
    int countContextSwitch;

    public Processor(Scheduler scheduler, String name){
        this.scheduler = scheduler;
        this.name = name;
    }

    public boolean process(){

        Process_ next = scheduler.nextProcess();

        // This probably generates time gap between algorithms
        //if (next == null) return false;

        // We should perform context switch for a different process (compare references)
        // Number of context switches for RR with time quanta equal to 1 may not be
        // equal to the number of processes, because not all of them are in the queue at the same time
        if (next != currProcess){
                //System.out.println("[CONTEXT SWITCHING]");
                countContextSwitch++;
        }

        currProcess = next;

        currProcess.doJob(1);
        return true;
    }

    public Process_ getCurrProcess(){return currProcess;}

    public int getNumOfContextSwitch(){return countContextSwitch;}


}
