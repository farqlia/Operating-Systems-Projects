package lab1.runner;

import lab1.processesgenerator.Generator;

import java.util.Locale;
import java.util.Scanner;


public class Main {
/*
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean isDone = false;
        Generator generator;

        AbstractSimulation simulation = null;

        while (!isDone){
            System.out.println("Choose algorithm: 1.FCFS\n2.SJF\n3.RR\n4.All\n5.exit(e)");
            String input = scanner.nextLine();

            if ('e' == input.charAt(0)){
                isDone = true;
            } else {
                System.out.println("Show statistics: (t/f)");
                boolean showStatistics = "t".equals(scanner.nextLine().toLowerCase(Locale.ROOT).trim());
                System.out.println("Number of numOfProcesses");
                int numOfProcesses = Integer.parseInt(scanner.nextLine());

                System.out.println("Choose Parameters : (t/f)");
                String des = scanner.nextLine();

                if ('t' == des.toLowerCase(Locale.ROOT).charAt(0)) {

                    System.out.println("Choose quanta time : ");
                    int quanta = Integer.parseInt(scanner.nextLine());

                    System.out.println("Choose min duration time : ");
                    int minDur = Integer.parseInt(scanner.nextLine());

                    System.out.println("Choose max time difference: ");
                    int diff = Integer.parseInt(scanner.nextLine());

                    System.out.println("Choose threshold : ");
                    double prob = Double.parseDouble(scanner.nextLine());

                    System.out.println("Choose intensity : ");
                    int inten = Integer.parseInt(scanner.nextLine());

                    System.out.println("Choose other parameters : (t/f)");
                    des = scanner.nextLine();

                    if ('t' == des.toLowerCase(Locale.ROOT).charAt(0)){
                        System.out.println("Choose phase : ");
                        double phase = Double.parseDouble(scanner.nextLine());

                        System.out.println("Choose phase length: ");
                        double phaselength = Double.parseDouble(scanner.nextLine());

                        System.out.println("Choose multiplicant: ");
                        int multiplicant = Integer.parseInt(scanner.nextLine());

                        simulation = new Simulation2(numOfProcesses, showStatistics, quanta, minDur, inten, diff, prob,
                                phase, phaselength, 0.1, multiplicant);

                    } else {
                        simulation = new Simulation(numOfProcesses, showStatistics, quanta, minDur, inten, diff, prob, 0.1);
                    }
                }

                input = input.toLowerCase(Locale.ROOT).trim();

                simulation.runSimulation(input);

            }

        }

    }

 */
}

