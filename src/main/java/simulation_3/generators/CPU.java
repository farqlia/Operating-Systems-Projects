package simulation_3.generators;

import simulation_3.process.Page;
import simulation_3.PrintStatistics;
import simulation_3.process.Process_;
import simulation_3.Time;
import simulation_3.process.State;
import simultation_4.frames_allocators.FrameAllocator;
import simultation_4.frames_allocators.NormalFrameAllocator;

import java.util.List;

public class CPU {

    private final Page[] memory;
    private List<Process_> activeProcesses;
    private FrameAllocator frameAllocator;

    public CPU(int numOfFrames, List<Process_> activeProcesses){
        this(numOfFrames, new NormalFrameAllocator(activeProcesses, numOfFrames), activeProcesses);
    }

    public CPU(int numOfFrames, FrameAllocator frameAllocator, List<Process_> activeProcesses){
        this.memory = new Page[numOfFrames];
        this.activeProcesses = activeProcesses;
        this.frameAllocator = frameAllocator;
        this.frameAllocator.initialize();
    }

    public void service(Process_ process){
        Page page = process.nextPage();
        //if (PrintStatistics.print) System.out.println("[REQUESTED PAGE]: " + page.getNum());
        page.setReferenceTime(Time.get());
        frameAllocator.allocateFrame(process);
        // Frame allocator may decide to stop the process
        if (process.getState() == State.RUNNING){
            page.setReferenceBit(true);
            process.getPagesManager().allocatePage();
            memory[page.getFrame()] = page;
            if (PrintStatistics.print) printMemory();
        }
        //else {
         //   activeProcesses.remove(process);
        //}
    }
    private void printMemory(){
        System.out.println("--------- MEMORY --------------");
        System.out.println("[TIME]: " + Time.get());
        for (int i = 0; i < memory.length; i++){
            System.out.print(i + ": ");
            if (memory[i] != null){
                System.out.print(memory[i].getNum());
                for (Process_ p : activeProcesses){
                    if (p.getFrameManager().getFrames().contains(i)) System.out.print(" " + p);
                }
                if (memory[i].getReferenceTime() == Time.get())
                    System.out.print("             <----");
                System.out.println();
            } else {
                System.out.println();
            }
        }
        System.out.println("--------- ------- --------------");
    }
}
