package simulation_4.frames_allocators;
import org.apache.commons.math3.random.ISAACRandom;
import simulation_3.Time;
import simulation_3.process.Page;
import simulation_3.process.Process_;
import simulation_3.process.State;
import simulation_4.PrintConsole;

import java.util.Arrays;
import java.util.List;

public class PageFaultFrequency extends FrameAllocator{

    private final double MIN;
    private final double MAX;
    private final double H;
    private final int deltaT;

    private double[] pFFs;
    private boolean[] removed;
    private int[] framesCount;

    public PageFaultFrequency(List<Process_> activeProcesses, int numOfFrames,
                              double max, double min, double h, int deltaT) {
        super(activeProcesses, numOfFrames);
        this.MAX = max;
        this.MIN = min;
        this.deltaT = deltaT;
        this.H = h;
        this.pFFs = new double[activeProcesses.size()];
        Arrays.fill(pFFs, -1);
        this.removed = new boolean[activeProcesses.size()];
        this.framesCount = new int[activeProcesses.size()];
    }

    public void initialize(){
        FrameAllocator allocator = new Proportional(activeProcesses, numOfFrames, false);
        allocator.initialize();
        freeFrames.addAll(allocator.freeFrames);
    }

    @Override
    public void allocateFrame(Process_ process) {

        double pFF = 0; //&& process.getPagesManager().getCount() % deltaT == 0
        if (process.getPagesManager().getCount() > deltaT) {
            //if (PrintConsole.print) System.out.println("[COMPUTING PFF]: " + Time.get());
            if (process.getState() == State.RUNNING) {
                pFF = ((double) (process.getPagesManager().getPastMissCount(0) -
                        process.getPagesManager().getPastMissCount(deltaT)) / deltaT);
                //if (PrintConsole.print) System.out.print(process + ": " + pFF + " ");
                // Stop the process only when its PFF goes beyond another parameter
                pFFs[process.getId()] = pFF;
                if (pFF > MAX) {
                    if (findFreeFrame()) {
                        process.getFrameManager().addFrame(freeFrames.pollFirst());
                        removed[process.getId()] = false;
                    } else if (pFF > H) stopProcess(process);
                }
                framesCount[process.getId()] = process.getFrameManager().numOfFrames();
                //restartBlockedProcess();
            }
        }
    }



    private boolean findFreeFrame(){
        int randIndex = new ISAACRandom(1).nextInt(pFFs.length);
        int index = 0;
        for (int i = 0; i < pFFs.length; i++){
            index = (i + randIndex) % pFFs.length;
            if (activeProcesses.get(index).getState() == State.RUNNING && pFFs[index] <= MIN
            && !removed[index]) {
                if (removeFrame(activeProcesses.get(index))) {
                    if (PrintConsole.print) {
                        System.out.print("[" + Time.get() + "] Frame is taken away from: " + activeProcesses.get(index));
                        System.out.println("[PFFS]: " + Arrays.toString(pFFs));
                    }
                    removed[index] = true;
                    return true;
                }
            }
        }
        return false;
    }

    private void restartBlockedProcess(){
        for (Process_ p : activeProcesses){
            if (p.getState() == State.BLOCKED && framesCount[p.getId()] <= freeFrames.size()){
                p.setState(State.RUNNING);
                if (PrintConsole.print)
                     System.out.println("[RESTARTED]: " + p);
                while (p.getFrameManager().numOfFrames() < framesCount[p.getId()])
                    p.getFrameManager().addFrame(freeFrames.pollFirst());
            }
        }
    }

    private boolean removeFrame(Process_ process){

        if (process.getFrameManager().numOfFrames() > minimalFrameNumber){
            int frame = process.getFrameManager().removeFrame();
            if (PrintConsole.print) System.out.println("-" + frame);
            freeFrames.addLast(frame);
            for (Page page : process.getPages())
                // Kicks out a page occupying a frame that is taken away from the process
                if (page.getFrame() == frame) {
                    page.setFrame(-1);
                    break;
                }
            return true;
        }
        return false;
    }

    public void printResults(){
        System.out.println("TOTAL BLOCKED: " + stoppedProcesses);
    }

    @Override
    public String toString() {
        return "PageFaultFrequency";
    }
}
