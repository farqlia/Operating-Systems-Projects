package simulation_5.main;

public class Time {

    private static int time = 0;

    public static int getTime(){return time;}

    public static void incr(){time++;}

    public static void reset(){time = 0;}

}
