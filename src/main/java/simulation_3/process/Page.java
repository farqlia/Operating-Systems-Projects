package simulation_3.process;

public class Page {

    private final int num;
    // frame equals to -1 means that the page is not present in the
    // memory or
    private int frame = -1;
    private int referenceTime;
    private int arrivalTime;
    boolean referenceBit;

    public boolean getReferenceBit() {
        return referenceBit;
    }

    public void setReferenceBit(boolean referenceBit) {
        this.referenceBit = referenceBit;
    }

    public Page(int num) {
        this.num = num;
    }

    public boolean isPresent() {
        return frame != -1;
    }

    public int getNum() {
        return num;
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public int getReferenceTime() {
        return referenceTime;
    }

    public void setReferenceTime(int referenceTime) {
        this.referenceTime = referenceTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public String toString() {
        return "Page{" +
                "num=" + num +
                ", frame=" + frame +
                ", referenceTime=" + referenceTime +
                ", arrivalTime=" + arrivalTime +
                '}';
    }
}
