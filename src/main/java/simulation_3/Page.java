package simulation_3;

public class Page {

    private boolean isPresent;
    private final int num;

    public Page(int num) {
        this.num = num;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public int getNum() {
        return num;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }
}
