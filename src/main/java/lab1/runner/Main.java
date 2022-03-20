package lab1.runner;

import lab1.processesgenerator.CauchyGenerator;
import lab1.processesgenerator.Generator;
import lab1.processesgenerator.ProcessesGenerator2;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean isDone = false;
        Generator generator;
        while (!isDone){
            System.out.println("Choose algorithm: 1.FCFS\n2.SJF\n3.RR\n4.All\n5.exit(e)");
            String input = scanner.nextLine();

            if ('e' == input.charAt(0)){
                isDone = true;
            } else {
                System.out.println("Show statistics: (t/f)");
                boolean showStatistics = "t".equals(scanner.nextLine().toLowerCase(Locale.ROOT).trim());
                System.out.println("Number of iterations");
                int iterations = Integer.parseInt(scanner.nextLine());

                System.out.println("Choose Parameters : (t/f)");
                String des = scanner.nextLine();

                Simulation simulation;

                if ('t' == des.toLowerCase(Locale.ROOT).charAt(0)) {

                    System.out.println("Choose quanta time : ");
                    int quanta = Integer.parseInt(scanner.nextLine());

                    System.out.println("Choose min duration time : ");
                    int minDur = Integer.parseInt(scanner.nextLine());

                    System.out.println("Choose max time difference: ");
                    int diff = Integer.parseInt(scanner.nextLine());

                    System.out.println("Choose probability : ");
                    double prob = Double.parseDouble(scanner.nextLine());

                    System.out.println("Choose intensity : ");
                    int inten = Integer.parseInt(scanner.nextLine());

                    simulation = new Simulation(iterations, showStatistics, quanta, minDur, inten, diff, prob);

                } else {
                    simulation = new Simulation(iterations, showStatistics);
                }

                input = input.toLowerCase(Locale.ROOT).trim();

                simulation.runSimulation(input);

            }

        }

    }

}
