package simulation_3.process;

import simulation_3.generators.Generator;
import simulation_3.process.Page;
import simulation_3.replacement_algorithms.PagesManager;

import java.util.*;
import java.util.stream.Collectors;

public class Process_ {

    Generator pageRequests;
    ListIterator<Integer> pageIter;
    List<Page> pages;
    PagesManager replacementAlgorithm;
    FrameManager frameManager;

    private State state;

    private final int id;

    public Process_(int id, Generator pageRequests,
                    PagesManager replacementAlgorithm){
        this.pageRequests = pageRequests;
        this.pageIter = pageRequests.iterator();
        this.replacementAlgorithm = replacementAlgorithm;
        this.replacementAlgorithm.setProcess(this);
        this.state = State.RUNNING;
        this.frameManager = new FrameManager();
        this.id = id;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public FrameManager getFrameManager() {
        return frameManager;
    }

    public int numOfRequests(){return pageRequests.size();}

    public int getId() {
        return id;
    }

    @Override
    public String toString(){
        return "p" + id;
    }

    public String printInfo(){
        return this + ": " + "[State] = " + state.name() + ", " +
                //"[Pages] = " + pages.stream().map(x -> "p: " + x.getNum() + ", f: " + x.getFrame()).collect(Collectors.toList()) + ", " +
                "[NofF] = " + frameManager.numOfFrames() + ", " +
                "[Frames] = " + frameManager.getFrames() +  ", " +
                "[NoP] = " + pages.size() + ", " +
                "[NofR] = " + pageRequests.size() + ", " +
                "[Page Faults] = " + replacementAlgorithm.getMissCount() + "\n";
    }

}
