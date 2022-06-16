package simulation_4.frames_allocators;

import simulation_3.process.Process_;
import simulation_3.process.State;

import java.util.List;

public class Equal extends FrameAllocator{

    public Equal(List<Process_> activeProcesses, int numOfFrames) {
        super(activeProcesses, numOfFrames);
    }

    public void initialize(){
        distributeFrames((numOfFrames / activeProcesses.size()));
    };

    private void distributeFrames(int framesPerProcess){
        for (Process_ process : activeProcesses) {
            if (process.getState() == State.RUNNING){
                for (int f = 0; f < framesPerProcess; f++){
                    process.getFrameManager().addFrame(freeFrames.pollFirst());
                }
            }
        }
        // Share all the left frames equally between processes
        // Maybe randomly add it?
        int i = 0;
        while (!freeFrames.isEmpty()){
            if (activeProcesses.get(i).getState() == State.RUNNING)
                activeProcesses.get(i % activeProcesses.size()).getFrameManager().addFrame(freeFrames.pollFirst());
            i++;
        }


    }

    @Override
    public void allocateFrame(Process_ process) {
        int framesPerProcess = freeFrames.size() / (int)activeProcesses.stream().filter(p -> p.getState() == State.RUNNING).count();
        if (framesPerProcess != 0) distributeFrames(framesPerProcess);
    }

    @Override
    public String toString() {
        return "Equal";
    }
}
