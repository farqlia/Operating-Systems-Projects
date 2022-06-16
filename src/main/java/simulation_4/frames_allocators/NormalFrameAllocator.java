package simulation_4.frames_allocators;

import simulation_3.process.Process_;
import java.util.List;

public class NormalFrameAllocator extends FrameAllocator {

    public NormalFrameAllocator(List<Process_> activeProcesses,
                                int numOfFrames) {
        super(activeProcesses, numOfFrames);
    }

    @Override
    public void initialize() {
        for (Process_ p : activeProcesses){
            for (int i = 0; i < numOfFrames; i++){
                p.getFrameManager().addFrame(i);
            }
        }
    }

    @Override
    public void allocateFrame(Process_ process) {

    }
}
