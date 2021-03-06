package simulation_1.runner;

import java.util.Scanner;

public class Main {

    private static final int quantaTime = 5;
    private static final int minDuration = 20;
    private static final int difference = 20;
    private static final int intensity = 2;
    private static final int iterations = 10000;
    private static final boolean showStatistics = false;

    // ??
    private static final double phase = 0.5;
    private static final double phaseLength = 0.001;
    private static final int multiplicant = 20;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean isDone = false;

        Simulation simulation;

        while (!isDone) {
            System.out.println("Choose algorithm: \n1.FCFS\n2.SJF\n3.RR\n4.All\n5.exit(e)");
            String input = scanner.nextLine();

            if ('e' == input.charAt(0)) {
                isDone = true;
            } else {
                simulation = new Simulation(iterations, showStatistics, quantaTime, minDuration, difference, intensity,
                            phase, phaseLength, multiplicant);

                simulation.runSimulation(input);
            }
        }
    }

}
