package simulation_3.replacement_algorithms;

import simulation_3.Page;
import simulation_3.Process_;

public abstract class ReplacementAlgorithm {

    private int missCount;
    private int hitCount;
    protected Page[] memory;
    protected Process_ process;

    protected abstract void evictPage();

    public abstract Page getEvictedPage();

    public void allocatePage(Page page){

        evictPage();
        // Allocate new page in place of the deleted page
        memory[getEvictedPage().getFrame()] = page;
        page.setFrame(getEvictedPage().getFrame());
        getEvictedPage().setFrame(-1);
        missCount++;

    };

    public void incMissCount(){missCount++;}

    public void incHitCount(){hitCount++;}

    public int getMissCount() {
        return missCount;
    }

    public int getHitCount() {
        return hitCount;
    }
}
