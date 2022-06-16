package simulation_5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Processor {

    List<Process> processes;
    int id;
    boolean isMigrating;

    public Processor(int id) {
        this.id = id;
        this.processes = new LinkedList<>();
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

    public boolean isMigrating(){return isMigrating;}

    public void setMigrating(boolean migrating) {
        isMigrating = migrating;
    }

    public void executeAll(){
        Iterator<Process> iter = processes.iterator();
        Process p= null;
        while (iter.hasNext()) {
            p = iter.next();
            p.execute();
            if (p.isDone()) iter.remove();;
        }
    }

    public Process giveAwayProcess(){
        return processes.remove(processes.size() - 1);
    }

}
