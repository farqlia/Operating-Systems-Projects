package simulation_5.objects;

import simulation_5.objects.Process;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Processor {

    List<Process> processes;
    public final int id;
    int outGoingMigrations;
    int inComingMigrations;
    int communications;

    public Processor(int id) {
        this.id = id;
        this.processes = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public int numOfProcesses(){return processes.size();}

    public int currentLoad(){return processes.stream().mapToInt(Process::getDemand)
            .reduce(Integer::sum).orElse(0);}

    public void addProcess(Process process){
        processes.add(process);
    }

    public boolean isOccupied(){
        return !processes.isEmpty();
    }

    public int getCommunications() {
        return communications;
    }

    public void incrCommunications() {
        this.communications++;
    }

    public void resetCommunications() {
        this.communications = 0;
    }

    public int getOutGoingMigrations() {
        return outGoingMigrations;
    }

    public void resetOutGoingMigrations() {
        this.outGoingMigrations = 0;
    }

    public void resetInComingMigrations() {
        this.inComingMigrations = 0;
    }

    public void incrOutGoingMigrations() {
        this.outGoingMigrations++;
    }

    public int getInComingMigrations() {
        return inComingMigrations;
    }

    public void incrInComingMigrations() {
        this.inComingMigrations++;
    }

    public void executeAll(){
        Iterator<Process> iter = processes.iterator();
        Process p= null;
        while (iter.hasNext()) {
            p = iter.next();
            p.execute();
            if (p.isDone()) iter.remove();
        }
    }

    public Process giveAwayProcess(){
        return processes.remove(processes.size() - 1);
    }

}
