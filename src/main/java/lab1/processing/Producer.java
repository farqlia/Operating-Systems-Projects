package lab1.processing;

import lab1.processesgenerator.Generator;
import lab1.schedulers.Scheduler;
import lab1.schedulers.Time;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

// Takes in a processes generator and in an arbitrary moment adds new process
// to all the schedulers
public class Producer {

    // Add some way to visualize 'traffic'
    final double MAGIC_NUMBER = 0;
    RandomGenerator generator = new JDKRandomGenerator(1);
    Generator processGenerator;
    Scheduler[] schedulers;
    int howMany;

    public Producer(int howMany, Generator processGenerator,Scheduler ... schedulers){
        this.processGenerator = processGenerator;
        this.schedulers = schedulers;
        this.howMany = howMany;
    }

    public void produce(){

        if (howMany > 0){
            if (generator.nextGaussian() > MAGIC_NUMBER){

                Process_ process = processGenerator.next();
                howMany--;

                for (Scheduler scheduler : schedulers){
                    scheduler.addProcess(new SimpleProcess(process.getId(), process.getArrTime(), process.getCompTime()));
                }

                System.out.println("[ADDED] process : " + process.getId());

            } else {
                System.out.println("A process is waiting to be added");
            }
        }
    }



}
