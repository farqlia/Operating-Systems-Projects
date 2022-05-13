package simulation_3;

import simulation_3.generators.Generator;
import simulation_3.replacement_algorithms.ReplacementAlgorithm;

import java.util.*;

public class Process_ {

    Generator pageRequests;
    ListIterator<Integer> pageIter;
    List<Page> pages;
    ReplacementAlgorithm replacementAlgorithm;

    public Process_(Generator pageRequests,
                    ReplacementAlgorithm replacementAlgorithm){
        this.pageRequests = pageRequests;
        this.pageIter = pageRequests.iterator();
        this.replacementAlgorithm = replacementAlgorithm;
    }

    public void initPages(){
        pages = new ArrayList<>();
        for (int i = 0; i < pageRequests.getPageRange(); i++){
            pages.add(new Page(i));
        }
    }

    public Page nextPage(){
        return pages.get(pageIter.next());
    }

    public boolean hasNext(){
        return pageIter.hasNext();
    }

    public int nextIndex(){
        return pageIter.nextIndex();
    }

    public List<Integer> pageRequests(){
        return pageRequests.requests();
    }

    public List<Page> getPages() {
        return pages;
    }

    public ReplacementAlgorithm getReplacementAlgorithm() {
        return replacementAlgorithm;
    }
}
