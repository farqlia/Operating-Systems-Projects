package simulation_3.replacement_algorithms;

import simulation_3.Page;
import simulation_3.Process_;
import simulation_3.Time;

public abstract class PagesManager {

    private int missCount;
    private int hitCount;
    protected Process_ process;
    protected Page evictedPage;
    private String name;

    public PagesManager(String name){
        this.name = name;
    }

    protected abstract void evictPage();

    public Page getEvictedPage(){return evictedPage;}

    // Page might have been allocated, but some algorithms need this
    // information anyway
    public void hook(){};

    public void allocatePage(){

        hook();
        if (!process.getPage().isPresent()){
            if (!process.hasFreeFrame()){
                evictPage();
                // Assign the free frame to the page
                process.getPage().setFrame(getEvictedPage().getFrame());
                getEvictedPage().setFrame(-1);
            } else {
                process.getPage().setFrame(process.getNextFreeFrame());
            }
            process.getPage().setArrivalTime(Time.get());
            missCount++;
        } else {
            evictedPage = null;
            hitCount++;
        }


    };

    public void setProcess(Process_ process) {
        this.process = process;
    }

    public int getMissCount() {
        return missCount;
    }

    public int getHitCount() {
        return hitCount;
    }

    @Override
    public String toString(){
        return name;
    }
}
