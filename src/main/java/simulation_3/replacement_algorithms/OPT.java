package simulation_3.replacement_algorithms;

import simulation_3.Page;
import simulation_3.PrintStatistics;

import java.util.LinkedHashSet;

public class OPT extends PagesManager{

    public OPT() {
        super("OPT");
    }

    @Override
    protected void evictPage() {
        LinkedHashSet<Integer> pagesToBeReferenced = new LinkedHashSet<>();
        int index = process.nextIndex();
        int lastAdded = 0;
        //index < process.numOfRequests() &&
        while (pagesToBeReferenced.size() != process.getFrames().size()){
            lastAdded = process.pageRequests().get(index % process.pageRequests().size());
            // Page is a candidate for eviction only if it is present in the memory
            if (process.getPages().get(lastAdded).isPresent()){
                pagesToBeReferenced.add(lastAdded);
            }
            index = (index + 1) % process.pageRequests().size();
        }

        //process.pageRequests().stream().skip(process.nextIndex()).limit(50).forEach(x -> System.out.print(x + " "));
        if (PrintStatistics.print){
            pagesToBeReferenced.forEach(x -> System.out.print(x + " "));
            System.out.println();
        }

        for (int i : pagesToBeReferenced) lastAdded = i;
        evictedPage = process.getPages().get(lastAdded);
    }
}
