package simultation_4;



import simulation_3.process.Process_;

import java.util.List;

public class Thrashing {

   int[] frequency;
   int delta;
   List<Process_> processes;

   public Thrashing(List<Process_> processes, int deltaT){
       frequency = new int[processes.size()];
       this.delta = deltaT;
       this.processes = processes;
   }

   public void collectData(Process_ process){

       int bound = delta / 2;
       if (process.getPagesManager().getCount() > delta && process.getPagesManager().getPastMissCount(0)
               - process.getPagesManager().getPastMissCount(delta) > bound) {
           frequency[process.getId()]++;
       }
   }

   public int[] getData(){
       return frequency;
   }

}
