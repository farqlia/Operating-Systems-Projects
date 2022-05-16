package simulation_3.frames_managers;

import simulation_3.Process_;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public abstract class FrameManager {

    protected List<Process_> activeProcesses;
    protected int numOfFrames;

    public FrameManager(List<Process_> activeProcesses, int numOfFrames){
        this.activeProcesses = activeProcesses;
        this.numOfFrames = numOfFrames;
    }

    // hook method
    public void initialize(){};

    // allocates new frame according to the given policy
    // may even stop the process
    public abstract void allocateFrame(Process_ process);

}
