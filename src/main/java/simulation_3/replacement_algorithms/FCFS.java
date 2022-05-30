package simulation_3.replacement_algorithms;

import simulation_3.process.Page;
import simulation_3.Time;

public class FCFS extends PagesManager {

    public FCFS() {
        super("FCFS");
    }

    @Override
    protected void evictPage() {
        int maxTime = 0;
        int time;
        for (Page page : process.getPages()){
            if (page.isPresent() && (time = Time.get() - page.getArrivalTime()) > maxTime){
                maxTime = time;
                evictedPage = page;
            }

        }

    }

}
