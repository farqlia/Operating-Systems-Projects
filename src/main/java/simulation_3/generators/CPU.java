package simulation_3.generators;

import simulation_3.Page;
import simulation_3.Process_;

import java.util.LinkedList;

public class CPU {

    private final Page[] memory;
    // frames that are not occupied by any process
    private LinkedList<Integer> freeFrames;

    public CPU(int numOfFrames){
        this.memory = new Page[numOfFrames];
    }

    public void service(Process_ process){
        Page page = process.nextPage();
        // No page was evicted so casually allocate new frame
        if (!page.isPresent()){
            if (!freeFrames.isEmpty()){
                page.setFrame(freeFrames.remove());
                memory[page.getFrame()] = page;
            } else {
                process.getReplacementAlgorithm().allocatePage(page);
            }
            process.getReplacementAlgorithm().incMissCount();
        }
    }

}
