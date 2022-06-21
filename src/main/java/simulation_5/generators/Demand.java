package simulation_5.generators;

public class Demand {

    int percent;
    int lower;
    int upper;
    double probability;

    public Demand(int percent, int lower, int upper, int probability) {
        this.percent = percent;
        this.lower = lower;
        this.upper = upper;
        this.probability = ((double) probability / 100);
    }
}
