package simultation_4.frames_allocators;
import simulation_3.PrintStatistics;
import simulation_3.Time;
import simulation_3.process.Page;
import simulation_3.process.Process_;
import simulation_3.process.State;
import simultation_4.PrintConsole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageFaultFrequency extends FrameAllocator{

    private final double MIN;
    private final double MAX;
    private final double H;
    private final int deltaT;

    public PageFaultFrequency(List<Process_> activeProcesses, int numOfFrames,
                              double max, double min, double h, int deltaT) {
        super(activeProcesses, numOfFrames);
        this.MAX = max;
        this.MIN = min;
        this.deltaT = deltaT;
        this.H = h;
    }

    public void initialize(){
        FrameAllocator allocator = new Proportional(activeProcesses, numOfFrames);
        allocator.initialize();
        freeFrames.clear();
    }

    @Override
    public void allocateFrame(Process_ process) {

        double pFF = 0;
        if (process.getPagesManager().getCount() > deltaT && process.getPagesManager().getCount() % deltaT == 0) {
            if (PrintConsole.print) System.out.println("[COMPUTING PFF]: " + Time.get());
            if (process.getState() == State.RUNNING) {
                pFF = ((double) (process.getPagesManager().getPastMissCount(0) -
                        process.getPagesManager().getPastMissCount(deltaT)) / deltaT);
                if (PrintConsole.print) System.out.print(process + ": " + pFF + " ");
                // Stop the process only when its PFF goes beyond another parameter
                if (pFF > H) stopProcess(process);
                else if (pFF > MAX) addFrame(process);
                else if (pFF < MIN) removeFrame(process);
                System.out.println();
            }
            System.out.println("----------------------------------");
        }
    }

    private void addFrame(Process_ process){
        if (!freeFrames.isEmpty()) {
            int frame = freeFrames.pollFirst();
            process.getFrameManager().addFrame(frame);
            if (PrintConsole.print) System.out.print("+" + frame);
        }
    }

    private void removeFrame(Process_ process){

        if (process.getFrameManager().numOfFrames() > 1){
            int frame = process.getFrameManager().removeFrame();
            if (PrintConsole.print) System.out.println("-" + frame);
            freeFrames.addLast(frame);
            for (Page page : process.getPages())
                // Kicks out a page occupying a frame that is taken away from the process
                if (page.getFrame() == frame) {
                    page.setFrame(-1);
                    break;
                }
        }
    }

    @Override
    public String toString() {
        return "PageFaultFrequency";
    }
}
