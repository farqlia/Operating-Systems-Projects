package simultation_4.frames_allocators;

import simulation_3.PrintStatistics;
import simulation_3.process.Page;
import simulation_3.process.Process_;
import simulation_3.process.State;
import simultation_4.PrintConsole;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public abstract class FrameAllocator {

    protected List<Process_> activeProcesses;
    protected Deque<Integer> freeFrames;
    protected int numOfFrames;
    protected int minimalFrameNumber;
    protected int stoppedProcesses;

    public FrameAllocator(List<Process_> activeProcesses, int numOfFrames){
        this.activeProcesses = activeProcesses;
        this.numOfFrames = numOfFrames;
        this.freeFrames = new ArrayDeque<>();
        this.minimalFrameNumber = 2;
        initFrames();
    }

    // hook method
    public void initialize(){};

    public void printResults(){}

    // allocates new frame according to the given policy
    // may even stop the process
    public abstract void allocateFrame(Process_ process);

    public void freeFrame(Process_ process){
        int removed = process.getFrameManager().removeFrame();
        process.getPages().stream().filter(p -> p.getFrame() == removed)
                .findFirst().ifPresent(p -> p.setFrame(-1));
        freeFrames.addLast(removed);
    }

    // When the process is terminated, it gives back its frames
    public void freeFrames(Process_ process){

        for (Page p : process.getPages()) {
            p.setFrame(-1);
        }
        while (process.getFrameManager().numOfFrames() > 0){
            freeFrames.addLast(process.getFrameManager().removeFrame());
        }

    }


    protected void stopProcess(Process_ process){
        // Stop the process
        process.setState(State.BLOCKED);
        // When we delete a process, we have to free its frames
        freeFrames(process);
        stoppedProcesses++;
        if (PrintConsole.print) System.out.println("[BLOCKED] = " + process);
    }

    public boolean hasFreeFrames(){return !freeFrames.isEmpty();}

    // Initialize list of free frames
    private void initFrames(){
        for (int f = 0; f < numOfFrames; f++){
            freeFrames.addLast(f);
        }
    }

    public void printFrames(){
        System.out.println("--------------------------------------");
        activeProcesses.stream().filter(p -> p.getState() == State.RUNNING).forEach(p -> System.out.println(p + ", f = " + p.getFrameManager().getFrames() + ", NoF = " + p.getFrameManager().numOfFrames()));
        System.out.println("--------------------------------------");

    }
}
