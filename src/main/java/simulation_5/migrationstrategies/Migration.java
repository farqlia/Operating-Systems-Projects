package simulation_5.migrationstrategies;

import simulation_5.objects.Process;
import simulation_5.objects.Processor;

public class Migration {

    Processor processor;
    Process process;
    int currProbes;

    public Migration(Processor processor, Process process) {
        this.processor = processor;
        this.process = process;
        this.processor.incrOutGoingMigrations();
    }

    public void incrProbes(){
        currProbes++;
        this.processor.incrCommunications();
    }

    @Override
    public String toString() {
        return "Migration{" +
                "processorId=" + processor.id +
                ", process=" + process +
                ", currProbes=" + currProbes +
                '}';
    }
}
