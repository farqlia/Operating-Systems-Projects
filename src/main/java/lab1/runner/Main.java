package lab1.runner;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static final int quantaTime = 1;
    private static final int minDuration = 50;
    private static final int difference = 50;
    private static final int intensity = 2;
    private static final int iterations = 100000;
    private static final boolean showStatistics = false;

    // For simulation 2
    private static final double phase = 0.1;
    private static final double phaseLength = 0.1;
    private static final int multiplicant = 10;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean isDone = false;

        AbstractSimulation simulation;

        while (!isDone) {
            System.out.println("Choose algorithm: 1.FCFS\n2.SJF\n3.RR\n4.All\n5.exit(e)");
            String input = scanner.nextLine();

            if ('e' == input.charAt(0)) {
                isDone = true;
            } else {
                System.out.println("Simulation : (1/2)");
                String sim = scanner.nextLine();

                if ('1' == sim.toLowerCase(Locale.ROOT).charAt(0)){
                    simulation = new Simulation(iterations, showStatistics, quantaTime, minDuration, difference, intensity);
                } else {
                    simulation = new Simulation2(iterations, showStatistics, quantaTime, minDuration, difference, intensity,
                            phase, phaseLength, multiplicant);
                }

                simulation.runSimulation(input);

            }
        }
    }

}
