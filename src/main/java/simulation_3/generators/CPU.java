package simulation_3.generators;

import simulation_3.process.Page;
import simulation_3.process.Process_;
import simulation_3.Time;
import simulation_3.process.State;
import simulation_4.frames_allocators.FrameAllocator;
import simulation_4.frames_allocators.NormalFrameAllocator;

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
        Page page = process.peekPage();
        //if (PrintConsole.print) System.out.println("[REQUESTED PAGE]: " + page.getNum());
        frameAllocator.allocateFrame(process);
        // Frame allocator may decide to stop the process
        if (process.getState() == State.RUNNING){
            page = process.nextPage();
            page.setReferenceTime(Time.get());
            page.setReferenceBit(true);
            process.getPagesManager().allocatePage();
            memory[page.getFrame()] = page;
            //if (PrintConsole.print) printMemory();
        }
    }
    private void printMemory(){
        System.out.println("--------- MEMORY --------------");
        System.out.println("[TIME]: " + Time.get());
        for (int i = 0; i < memory.length; i++){
            System.out.print(i + ": ");
            if (memory[i] != null && memory[i].isPresent()){
                System.out.print(memory[i].getNum());
                for (Process_ p : activeProcesses){
                    if (p.getFrameManager().getFrames().contains(i)) System.out.print(" " + p);
                }
                if (memory[i].getFrame() == i && memory[i].getReferenceTime() == Time.get())
                    System.out.print("             <----");
                System.out.println();
            } else {
                System.out.println();
            }
        }
        System.out.println("--------- ------- --------------");
    }
}
