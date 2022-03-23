package simulation_1.schedulers;

// Global time
public class Time {

    public static int time = 0;

    public static int increment(){
        return time++;
    }

    public static int get(){
        return time;
    }

}
