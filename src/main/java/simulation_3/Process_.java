package simulation_3;

import simulation_3.generators.Generator;
import simulation_3.replacement_algorithms.PagesManager;

import java.util.*;

public class Process_ {

    Generator pageRequests;
    ListIterator<Integer> pageIter;
    List<Page> pages;
    PagesManager replacementAlgorithm;

    boolean isActive;
    // frames belonging to the process
    List<Integer> frames;
    int nextFreeFrame;

    private String name;
    public static int numOfProcesses;

    public Process_(Generator pageRequests,
                    PagesManager replacementAlgorithm){
        this.pageRequests = pageRequests;
        this.pageIter = pageRequests.iterator();
        this.replacementAlgorithm = replacementAlgorithm;
        this.replacementAlgorithm.setProcess(this);
        this.isActive = true;
        this.frames = new ArrayList<>();
        this.name = "" + numOfProcesses++;
        initPages();
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

    public Page getPage(){
        return pages.get(pageIter.previous());
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

    public PagesManager getPagesManager() {
        return replacementAlgorithm;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Integer> getFrames() {
        return frames;
    }

    public int getNextFreeFrame(){
        return (nextFreeFrame >= frames.size()) ? -1 : frames.get(nextFreeFrame++);
    }

    public boolean hasFreeFrame(){
        return nextFreeFrame < frames.size();
    }

    public void setFrames(List<Integer> frames) {
        this.frames = frames;
    }

    public int numOfRequests(){return pageRequests.size();}

    @Override
    public String toString(){
        return "p" + name;
    }

}
