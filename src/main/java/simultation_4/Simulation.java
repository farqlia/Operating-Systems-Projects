package simultation_4;

import simulation_3.process.Process_;
import simultation_4.frames_allocators.*;
import simultation_4.so.ProcessGenerator;
import simultation_4.so.SO;

import java.util.List;

public class Simulation {

    public static void main(String[] args) {

        int frames = 30;
        ProcessGenerator processGenerator = new ProcessGenerator();
        List<Process_> processes = processGenerator.createProcesses(10);
        FrameAllocator frameAllocator = new WorkingSetModel(processes, 20, 20);
                //new PageFaultFrequency(processes, frames, 0.7, 0.3, 0.9, 20);
        SO so = new SO(processes, frameAllocator, frames);

        so.printStatistics();
        so.run();
        so.printStatistics();
    }

}
