package simultation_4;

import simulation_3.process.Process_;
import simultation_4.frames_allocators.*;
import simultation_4.so.ProcessGenerator;
import simultation_4.so.SO;

import javax.swing.*;
import java.util.List;

public class Simulation {

    static int frames = 40;
    static int N = 10;

    public static void main(String[] args) {

        ProcessGenerator processGenerator = new ProcessGenerator();
        List<Process_> processes = processGenerator.createProcesses(N);
        execute(processes, new Equal(processes, frames), frames);

        processGenerator = new ProcessGenerator();
        processes = processGenerator.createProcesses(N);
        execute(processes, new Proportional(processes, frames), frames);

        processGenerator = new ProcessGenerator();
        processes = processGenerator.createProcesses(N);
        execute(processes, new PageFaultFrequency(processes, frames, 0.5, 0.2, 0.7, 50), frames);

        processGenerator = new ProcessGenerator();
        processes = processGenerator.createProcesses(N);
        execute(processes, new WorkingSetModel(processes, frames, 20), frames);

    }

    public static void execute(List<Process_> processes, FrameAllocator allocator, int frames){
        //ProcessGenerator processGenerator = new ProcessGenerator();
       // List<Process_> processes = processGenerator.createProcesses(N);
        SO so = new SO(processes, allocator, frames);
        so.run();
        printData(allocator.toString(), processes, so.getThrashing());
    }

    public static void printData(String algName, List<Process_> processes, int[] thrashing){
        String space = "   ";
        System.out.print(algName);

        for (int i = 0; i < (20 - algName.length()); i++) System.out.print(" ");

        for (Process_ p : processes){
            System.out.print(p + "{" + p.getPagesManager().getMissCount() + "/" + thrashing[p.getId()] + "/" + p.getPagesManager().getCount() + "}" + space);
        }
        System.out.println();
    }

}
