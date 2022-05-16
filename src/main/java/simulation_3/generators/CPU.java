package simulation_3.generators;

import simulation_3.Page;
import simulation_3.PrintStatistics;
import simulation_3.Process_;
import simulation_3.Time;
import simulation_3.frames_managers.FrameManager;
import simulation_3.frames_managers.NormalFrameManager;

import java.util.List;

public class CPU {

    private final Page[] memory;
    private List<Process_> activeProcesses;
    private FrameManager frameManager;

    public CPU(int numOfFrames, List<Process_> activeProcesses){
        this.memory = new Page[numOfFrames];
        this.activeProcesses = activeProcesses;
        this.frameManager = new NormalFrameManager(activeProcesses,
                numOfFrames);
        this.frameManager.initialize();
    }

    public void service(Process_ process){
        Page page = process.nextPage();
        if (PrintStatistics.print) System.out.println("[REQUESTED PAGE]: " + page.getNum());
        page.setReferenceTime(Time.get());
        frameManager.allocateFrame(process);
        // Frame allocator may decide to stop the process
        if (process.isActive()){
            process.getPagesManager().allocatePage();
            memory[page.getFrame()] = page;
            page.setReferenceBit(true);
            if (PrintStatistics.print) printMemory();
        } else {
            activeProcesses.remove(process);
        }
    }
    private void printMemory(){
        System.out.println("--------- MEMORY --------------");
        System.out.println("[TIME]: " + Time.get());
        for (int i = 0; i < memory.length; i++){
            System.out.print(i + ": ");
            if (memory[i] != null){
                System.out.print(memory[i].getNum());
                //for (Process_ p : activeProcesses){
                 //   if (p.getFrames().contains(i)) System.out.print(" " + p);
                //}
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
