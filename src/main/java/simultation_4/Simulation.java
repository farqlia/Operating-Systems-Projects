package simultation_4;

import simulation_3.process.Process_;
import simultation_4.frames_allocators.*;
import simultation_4.so.ProcessGenerator;
import simultation_4.so.SO;

import javax.swing.*;
import java.util.List;

public class Simulation {

    public static void main(String[] args) {

        int frames = 40;
        ProcessGenerator processGenerator = new ProcessGenerator();
        List<Process_> processes = processGenerator.createProcesses(10);
        FrameAllocator frameAllocator =
                new WorkingSetModel(processes, frames, 20);
                 //new Proportional(processes, frames, false);
                //new PageFaultFrequency(processes, frames, 0.5, 0.2, 0.7, 50);
        SO so = new SO(processes, frameAllocator, frames);

        so.printStatistics();
        so.run();
        so.printStatistics();

        //Visualize v = new Visualize();

        //SwingUtilities.invokeLater(() -> {v.visualize(processes);});

    }

}
