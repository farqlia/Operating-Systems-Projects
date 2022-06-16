package simulation_4.so;
import simulation_3.Time;
import simulation_3.generators.CPU;
import simulation_3.process.Process_;
import simulation_3.process.State;
import simulation_4.PrintConsole;
import simulation_4.Thrashing;
import simulation_4.frames_allocators.FrameAllocator;

import java.util.Arrays;
import java.util.List;

public class SO {

    private final PageRequestGenerator pageRequestGenerator;

    List<Process_> processes;
    FrameAllocator frameAllocator;
    Thrashing thrashing;
    CPU cpu;

    public SO(List<Process_> processes, FrameAllocator frameAllocator, int numOfFrames) {
        this.processes = processes;
        this.frameAllocator = frameAllocator;
        this.pageRequestGenerator = new PageRequestGenerator(processes);
        this.cpu = new CPU(numOfFrames, frameAllocator, processes);
        this.thrashing = new Thrashing(processes, 10);
    }

    public int[] getThrashing(){return thrashing.getData();};

    public void run(){
        Process_ process;
        Time.reset();
        boolean wasRemoved = false;
        //int iter = 10000;
        while (pageRequestGenerator.hasNext()){
            process = pageRequestGenerator.next();
            cpu.service(process);
            thrashing.collectData(process);
            Time.inc();
            if (wasRemoved && PrintConsole.print) printFramesPerProcess();

            if (!process.hasNext()) {

                if (PrintConsole.print)  System.out.println("[TIME] = " + Time.get() + ", finished: " + process);
                wasRemoved = true;
                process.setState(State.TERMINATED);
                //System.out.println(process.printInfo());

                frameAllocator.freeFrames(process);
            } else {
                wasRemoved = false;
            }
        }
    }

    public void printFramesPerProcess(){
        System.out.println("--------------------------------------");
        processes.forEach(p -> System.out.println(p + ", f = " + p.getFrameManager().getFrames() + ", NoF = " + p.getFrameManager().numOfFrames()));
        System.out.println("--------------------------------------");
    }

    public void printResults(){
        System.out.println(frameAllocator);
        for (Process_ process : processes){
            System.out.println(process.getId() + " ");
        }
    }

    public void printStatistics(){
        System.out.println(frameAllocator);
        for (Process_ process : processes){
            System.out.println(process.printInfo());
        }
        System.out.println("[RUNNING] = " + processes.stream().filter(x -> x.getState() == State.RUNNING).count());
        System.out.println("[WAITING] = " + processes.stream().filter(x -> x.getState() == State.BLOCKED).count());
        System.out.println("[TERMINATED] = " + processes.stream().filter(x -> x.getState() == State.TERMINATED).count());
        System.out.println(Arrays.toString(thrashing.getData()));
    }

}
