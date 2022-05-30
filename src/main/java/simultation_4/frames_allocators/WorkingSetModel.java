package simultation_4.frames_allocators;

import simulation_1.schedulers.Time;
import simulation_3.process.Process_;
import simulation_3.process.State;

import java.util.Arrays;
import java.util.List;

public class WorkingSetModel extends FrameAllocator {

    private final int delta;
    private final int frequency;

    public WorkingSetModel(List<Process_> activeProcesses, int numOfFrames,
                           int delta) {
        super(activeProcesses, numOfFrames);
        this.delta = delta;
        this.frequency = (delta / 2);
    }

    private void printDemands(int[] demand){

        StringBuilder sB = new StringBuilder();
        for (Process_ p : activeProcesses){
            if (p.getState() == State.RUNNING){
                sB.append("Demand: ").append(p.getId()).append("= ").append(demand[p.getId()]).append("\n");
            }
        }
        System.out.println(sB);

    }

    private int getProcessWithHighestWSS(int[] frameDemand){

        int toRemove = 0;
        for (Process_ p : activeProcesses){
            if (p.getState() == State.RUNNING) {
                if (frameDemand[p.getId()] > frameDemand[toRemove]) toRemove = p.getId();
            }
        }
        return toRemove;
    }

    @Override
    public void allocateFrame(Process_ process) {

        if (Time.get() % frequency == 0){

            int wSSSUm = 0;
            int[] frameDemand = new int[activeProcesses.size()];

            for (Process_ p : activeProcesses){
                if (p.getState() == State.RUNNING){
                    frameDemand[p.getId()] = (int) p.pageRequests().stream().skip(p.nextIndex() - delta)
                            .limit(delta).distinct().count();
                    wSSSUm += frameDemand[p.getId()];
                }
            }
            printDemands(frameDemand);

            // Choose victim process to be stopped, so we can cover the needs for other processes
            if (wSSSUm > numOfFrames) stopProcess(activeProcesses.get(getProcessWithHighestWSS(frameDemand)));

            for (Process_ p : activeProcesses) {

                if (p.getState() == State.RUNNING){

                    while (frameDemand[p.getId()] > p.getFrameManager().numOfFrames())
                        p.getFrameManager().addFrame(freeFrames.pollFirst());

                    while (p.getFrameManager().numOfFrames() > frameDemand[p.getId()])
                        freeFrame(p);

                }
            }
            System.out.println("AFTER PARTITION");
            printFrames();
        }

    }
}
