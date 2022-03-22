package lab1.runner;

import lab1.processesgenerator.AbstractGenerator;
import lab1.processesgenerator.Generator;
import lab1.processesgenerator.ProcessesGenerator;
import lab1.schedulers.Scheduler;

public class Simulation extends AbstractSimulation{

    AbstractGenerator generator2;

    public Simulation(int iterations, boolean showStatistics, int quantaTime, int minDuration,
                      int maxDifference, int intensity){
        super(iterations, showStatistics, quantaTime);
        this.generator2 = new ProcessesGenerator(minDuration, maxDifference, intensity, iterations);
    }

    @Override
    protected Generator getGenerator(Scheduler scheduler) {
        return new ProcessesGenerator(generator2, scheduler);
    }

}
