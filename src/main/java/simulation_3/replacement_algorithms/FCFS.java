package simulation_3.replacement_algorithms;

import simulation_3.Page;
import simulation_3.Time;

import java.util.ArrayDeque;
import java.util.Deque;

public class FCFS extends ReplacementAlgorithm{

    private Page evictedPage;

    @Override
    protected void evictPage() {
        int maxTime = 0;
        int time;
        for (Page page : memory){
            if (process.getPages().contains(page)){
                if ((time = Time.get() - page.getArrivalTime()) > maxTime){
                    maxTime = time;
                    evictedPage = page;
                }
            }
        }

    }

    @Override
    public Page getEvictedPage() {
        return evictedPage;
    }
}
