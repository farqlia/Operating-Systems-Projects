package lab1.runner;

import lab1.processesgenerator.AbstractGenerator;
import lab1.processesgenerator.Generator;
import lab1.processesgenerator.ProcessesGenerator2;
import lab1.schedulers.Scheduler;

public class Simulation extends AbstractSimulation{

    AbstractGenerator generator2;

    public Simulation(int iterations, boolean showStatistics, int quantaTime, int minDuration,
                      int maxDifference, int intensity, double threshold, double chance){
        super(iterations, showStatistics, quantaTime, minDuration, intensity, maxDifference, threshold);
        this.generator2 = new ProcessesGenerator2(minDuration, maxDifference, intensity,
                threshold, chance, iterations);
    }

    @Override
    protected Generator getGenerator(Scheduler scheduler) {
        return new ProcessesGenerator2(generator2, scheduler);
    }

}
