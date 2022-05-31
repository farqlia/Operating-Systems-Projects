package simulation_3.process;

import java.util.LinkedList;
import java.util.List;

public class FrameManager {

    private final List<Integer> frames = new LinkedList<>();
    private int next = 0;

    public void addFrame(int frame){
        frames.add(frame);
    }

    public void removeFrame(int frame){
        frames.remove(frame);
        // next--; ???
    }

    public int removeFrame(){
        next = Math.max(next - 1, 0);
        return frames.remove(0);
    }

    public int getNextFreeFrame(){return hasFreeFrame() ? frames.get(next++) : -1;}

    public boolean hasFreeFrame(){return next < frames.size();}

    public int numOfFrames(){return frames.size();}

    public List<Integer> getFrames() {return frames;}
}
