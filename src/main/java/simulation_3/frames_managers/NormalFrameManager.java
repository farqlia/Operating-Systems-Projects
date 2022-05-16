package simulation_3.frames_managers;

import simulation_3.Process_;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NormalFrameManager extends FrameManager{

    public NormalFrameManager(List<Process_> activeProcesses,
                              int numOfFrames) {
        super(activeProcesses, numOfFrames);
    }

    @Override
    public void initialize() {
        for (Process_ p : activeProcesses){
            p.setFrames(IntStream.range(0, numOfFrames).boxed().collect(Collectors.toList()));
        }
    }

    @Override
    public void allocateFrame(Process_ process) {

    }
}
