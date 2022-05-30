package simulation_3.replacement_algorithms;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.random.Well44497b;
import simulation_3.process.Page;

public class RAND extends PagesManager{

    private final RandomDataGenerator EVICT_PAGE_GENERATOR = new RandomDataGenerator(new Well44497b(1));

    public RAND() {
        super("RAND");
    }


    @Override
    protected void evictPage() {
        evictedPage = process.getPages().stream().filter(Page::isPresent).skip(EVICT_PAGE_GENERATOR.nextInt(0,
                process.getFrameManager().numOfFrames() - 1)).findFirst().get();
    }
}
