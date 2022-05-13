package simulation_3;

import simulation_3.generators.LoopingGenerator;
import simulation_3.generators.NormalGenerator;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        NormalGenerator gN = new NormalGenerator();

         LoopingGenerator lpG = new LoopingGenerator(100);

        Iterator<Integer> iter = lpG.iterator();

        System.out.println("WITH ITERATOR");

        while (iter.hasNext()){
            System.out.print(iter.next() + ", ");
        }

    }

}
