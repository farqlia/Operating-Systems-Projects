package lab1.runner;

import lab1.processesgenerator.Generator;

import java.util.Locale;
import java.util.Scanner;

public class Main2 {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean isDone = false;
        Generator generator;

        AbstractSimulation simulation = null;

        while (!isDone) {
            System.out.println("Choose algorithm: 1.FCFS\n2.SJF\n3.RR\n4.All\n5.exit(e)");
            String input = scanner.nextLine();

            if ('e' == input.charAt(0)) {
                isDone = true;
            } else {
                System.out.println("Show statistics: (t/f)");
                boolean showStatistics = "t".equals(scanner.nextLine().toLowerCase(Locale.ROOT).trim());
                System.out.println("Number of iterations");
                int iterations = Integer.parseInt(scanner.nextLine());

                System.out.println("Simulation : (1/2)");
                String sim = scanner.nextLine();

                int quantaTime = 1;
                int minDuration = 20;
                int maxDifference = 40;
                int intensity = 1;
                double threshold = 0.3;
                double chance = 0.1;
                double phase = 0.2;
                double phaseLength = 0.1;
                int multiplicant = 5;

                if ('1' == sim.toLowerCase(Locale.ROOT).charAt(0)){
                    simulation = new Simulation(iterations, showStatistics, quantaTime, minDuration, maxDifference, intensity, threshold, chance);
                } else {
                    simulation = new Simulation2(iterations, showStatistics, quantaTime, minDuration, maxDifference, intensity, threshold, chance,
                            phase, phaseLength, multiplicant);
                }

                simulation.runSimulation(input);

            }
        }
    }

}
