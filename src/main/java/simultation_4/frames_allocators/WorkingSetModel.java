package simultation_4.frames_allocators;
import simulation_3.Time;
import simulation_3.process.Process_;
import simulation_3.process.State;
import simultation_4.PrintConsole;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class WorkingSetModel extends FrameAllocator {

    private final int delta;
    private final int frequency;
    private FrameAllocator propAllocator;

    public WorkingSetModel(List<Process_> activeProcesses, int numOfFrames,
                           int delta) {
        super(activeProcesses, numOfFrames);
        this.delta = delta;
        this.frequency = (delta / 2);
        this.propAllocator = new Proportional(activeProcesses, numOfFrames);
    }

    public void initialize(){
        propAllocator.initialize();
        freeFrames.clear();
    };

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

        int toRemove = -1;
        for (Process_ p : activeProcesses){
            if (p.getState() == State.RUNNING) {
                if (toRemove == -1 || frameDemand[p.getId()] > frameDemand[toRemove]) toRemove = p.getId();
            }
        }
        return toRemove;
    }

    protected void restartBlockedProcess(int[] frameDemands){
        for (Process_ p : activeProcesses){
            if (p.getState() == State.BLOCKED
                    && frameDemands[p.getId()] > 0 && frameDemands[p.getId()] <= freeFrames.size()){
                p.setState(State.RUNNING);
                if (PrintConsole.print)
                    if (PrintConsole.print) System.out.println("[RESTARTED]: " + p);
                while (p.getFrameManager().numOfFrames() < frameDemands[p.getId()])
                    p.getFrameManager().addFrame(freeFrames.pollFirst());
            }
        }
    }

    @Override
    public void allocateFrame(Process_ process) {

        if (Time.get() > frequency && Time.get() % frequency == 0){

            if (PrintConsole.print) System.out.println("[TIME] = " + Time.get());

            int[] frameDemand = new int[activeProcesses.size()];
            int wSSSUm = computeWSS(frameDemand);
            //printDemands(frameDemand);

            // Choose victim process to be stopped, so we can cover the needs for other processes
            if (wSSSUm > numOfFrames) {
                shareFrames(activeProcesses.get(getProcessWithHighestWSS(frameDemand)));
            }

            Deque<Process_> queue = new ArrayDeque<>();

            for (Process_ p : activeProcesses) if (p.getState() == State.RUNNING) queue.addLast(p);

            Process_ p;
            while (!queue.isEmpty()){

                p = queue.pollFirst();

                if ((freeFrames.size() >= (frameDemand[p.getId()] - p.getFrameManager().numOfFrames()))){

                    while (frameDemand[p.getId()] > p.getFrameManager().numOfFrames())
                        p.getFrameManager().addFrame(freeFrames.pollFirst());

                    while (p.getFrameManager().numOfFrames() > frameDemand[p.getId()])
                        freeFrame(p);
                } else {
                    queue.addLast(p);
                }

            }

            if (freeFrames.size() > 0) restartBlockedProcess(frameDemand);
            if (PrintConsole.print) {
                System.out.println("AFTER PARTITION");
                printFrames();
            }


        }

    }
    private int computeWSS(int[] frameDemand){
        int wSSSUm = 0;
        int start = 0;

        for (Process_ p : activeProcesses){
            start = Math.max(0, p.nextIndex() - delta);
            frameDemand[p.getId()] = (int) p.pageRequests().stream().skip(start)
                    .limit(delta).distinct().count();

            if (p.getState() == State.RUNNING) wSSSUm += frameDemand[p.getId()];
        }
        return wSSSUm;
    }

    private void shareFrames(Process_ process){
        while (!freeFrames.isEmpty()) propAllocator.freeFrames.addLast(freeFrames.pollFirst());
        propAllocator.stopProcess(process);
        propAllocator.allocateFrame(null);
    }

    public void printResults(){

        System.out.println("TOTAL BLOCKED: " + stoppedProcesses);
    }

    @Override
    public String toString() {
        return "WorkingSetModel";
    }
}
