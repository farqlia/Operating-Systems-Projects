package lab1.runner;

import lab1.processesgenerator.Generator;

import java.util.Locale;
import java.util.Scanner;

public class Main2 {

    private static final int quantaTime = 1;
    private static final int minDuration = 50;
    private static final int maxDifference = 50;
    private static final int intensity = 2;
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
                System.out.println("Show statistics: (t/f)");
                boolean showStatistics = "t".equals(scanner.nextLine().toLowerCase(Locale.ROOT).trim());
                System.out.println("Number of numOfProcesses");
                int iterations = Integer.parseInt(scanner.nextLine());

                System.out.println("Simulation : (1/2)");
                String sim = scanner.nextLine();

                if ('1' == sim.toLowerCase(Locale.ROOT).charAt(0)){
                    // phase : 0 - 8
                    // phaseLength : 0.5 - 2  (0.5 : short but burst phase)
                    simulation = new Simulation(iterations, showStatistics, quantaTime, minDuration, maxDifference, intensity);
                } else {
                    double phase = 0.1;
                    double phaseLength = 0.1;
                    simulation = new Simulation2(iterations, showStatistics, quantaTime, minDuration, maxDifference, intensity,
                            phase, phaseLength, multiplicant);
                }

                simulation.runSimulation(input);

            }
        }
    }

}
