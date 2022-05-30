package simultation_4.frames_allocators;

import simulation_3.process.Process_;
import simulation_3.process.State;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Proportional extends FrameAllocator {


    public Proportional(List<Process_> activeProcesses, int numOfFrames) {
        super(activeProcesses, numOfFrames);
    }

    public void initialize(){
        distributeFrames();
    }

    private void distributeFrames(){
        int sum = activeProcesses.stream()
                .filter(p -> p.getState() == State.RUNNING).mapToInt(p -> p.getPages().size()).reduce(Integer::sum).getAsInt();
        int framesToAdd;
        for (Process_ p : activeProcesses){
            if (p.getState() == State.RUNNING){
                framesToAdd = ((p.getPages().size() * numOfFrames) / sum) - p.getFrameManager().numOfFrames();
                for (int f= 0; f < framesToAdd; f++){
                    p.getFrameManager().addFrame(freeFrames.pollFirst());
                }
            }
        }


        if (!freeFrames.isEmpty()){

            List<Process_> sorted = activeProcesses.stream().filter(p -> p.getState() == State.RUNNING).sorted(Comparator.comparingInt(p -> p.getPages().size())).collect(Collectors.toList());
            int i = sorted.size() - 1;
            while (!freeFrames.isEmpty()) {
                framesToAdd = freeFrames.size() / 2;
                while (framesToAdd-- >= 0) sorted.get(i).getFrameManager().addFrame(freeFrames.pollFirst());
                i = (i - 1 + sorted.size()) % sorted.size();
            }
        }

    }

    @Override
    public void allocateFrame(Process_ process) {
        distributeFrames();
    }

    @Override
    public String toString() {
        return "Proportional";
    }
}
