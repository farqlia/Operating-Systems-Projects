package simulation_5;

public class Migration {

    Processor processor;
    Process process;
    int currProbes;

    public Migration(Processor processor, Process process) {
        this.processor = processor;
        this.process = process;
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
