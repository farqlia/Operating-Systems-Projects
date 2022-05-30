package simulation_3.replacement_algorithms;

import simulation_3.process.Page;
import simulation_3.process.Process_;
import simulation_3.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class PagesManager {

    private int missCount;
    private int hitCount;
    protected Process_ process;
    protected Page evictedPage;
    private String name;
    private List<Integer> missCountHistory;

    public PagesManager(String name){
        this.name = name;
        this.missCountHistory = new ArrayList<>();
    }

    protected abstract void evictPage();

    public Page getEvictedPage(){return evictedPage;}

    // Page might have been allocated, but some algorithms need this
    // information anyway
    public void hook(){};

    public void allocatePage(){

        hook();
        if (!process.getPage().isPresent()){
            if (!process.getFrameManager().hasFreeFrame()){
                evictPage();
                // Assign the free frame to the page
                process.getPage().setFrame(getEvictedPage().getFrame());
                getEvictedPage().setFrame(-1);
            } else {
                process.getPage().setFrame(process.getFrameManager().getNextFreeFrame());
            }
            process.getPage().setArrivalTime(Time.get());
            missCount++;
        } else {
            evictedPage = null;
            hitCount++;
        }
        missCountHistory.add(missCount);

    }

    public int getPastMissCount(int delta){
        return missCountHistory.get(missCountHistory.size() - 1 - delta);
    }

    public void setProcess(Process_ process) {
        this.process = process;
    }

    public int getMissCount() {
        return missCount;
    }

    public int getHitCount() {
        return hitCount;
    }

    public int getCount(){return hitCount + missCount;}

    @Override
    public String toString(){
        return name;
    }
}
