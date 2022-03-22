package lab1.runner;

import lab1.processesgenerator.Generator;
import lab1.processesgenerator.SJFKillGenerator;
import lab1.schedulers.Scheduler;

public class Simulation2 extends AbstractSimulation{

    SJFKillGenerator generator;

    public Simulation2(int iterations, boolean showStatistics, int quantaTime, int minDuration, int maxDifference, int intensity, double phase, double phaseLength, int multiplicant) {
        super(iterations, showStatistics, quantaTime);
        generator = new SJFKillGenerator(minDuration, maxDifference, intensity, iterations, phase, phaseLength, multiplicant);
    }

    @Override
    protected Generator getGenerator(Scheduler scheduler) {
        return new SJFKillGenerator(generator, scheduler);
    }
}
