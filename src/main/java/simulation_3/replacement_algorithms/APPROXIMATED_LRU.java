package simulation_3.replacement_algorithms;

import simulation_3.PrintStatistics;

import java.util.LinkedList;
import java.util.List;

public class APPROXIMATED_LRU extends PagesManager{

    private List<Integer> pages;

    //int head = 0;

    public APPROXIMATED_LRU(){
        super("APPROXIMATED LRU");
        pages = new LinkedList<>();
    }

    public void hook(){
        if (!process.getPage().isPresent() && process.hasFreeFrame()){
            addPageToQueue();
        }
        if (PrintStatistics.print) printQueue();
    };

    private void addPageToQueue(){
        pages.add(process.getPage().getNum());
    }

    @Override
    protected void evictPage() {
        int i = 0;
        while (true){
            if (!process.getPages().get(pages.get(i)).getReferenceBit()){
                evictedPage = process.getPages().get(pages.remove(i));
                break;
            } else {
                process.getPages().get(pages.get(i)).setReferenceBit(false);
                pages.add(pages.remove(i));
            }
            //i = (i + 1) % pages.size();
        }

        addPageToQueue();
        if (PrintStatistics.print){
            System.out.println("[AFTER EVICTION]");
            printQueue();
        }
    }

    private void printQueue(){
        pages.forEach(x -> System.out.print(x + " "));
        System.out.println();
        pages.forEach(x -> System.out.print((process.getPages().get(x).getReferenceBit() ? 1 : 0) + printSpaces((int)Math.log10(x) + 1)));
        System.out.println();
    }

    private String printSpaces(int i){
        String str = "";
        while (i-- > 0) str += " ";
        return str;
    }

}
