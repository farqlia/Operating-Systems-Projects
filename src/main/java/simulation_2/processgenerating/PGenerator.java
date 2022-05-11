package simulation_2.processgenerating;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import simulation_2.algorithms.Disc;
import simulation_2.algorithms.Request;
import simulation_2.algorithms.Time;

public class PGenerator implements Generator{

    private final Disc disc;

    private final RandomGenerator ACCESS_GENERATOR = new Well19937c(1);
    private final RandomGenerator MEAN_POSITION_CHANGE_GENERATOR = new Well19937c(8);
    private final RandomGenerator MEAN_POSITION_GENERATOR = new Well19937c(9);
    private final RandomGenerator GAUSS_POSITION_GENERATOR = new Well19937c(7);

    private final RandomGenerator PRIORITY_DEADLINE_GENERATOR = new Well19937c(5);
    private final RandomGenerator PRIORITY_GENERATOR = new Well19937c(6);
    private final RandomGenerator MEAN_DEADLINE_CHANGE_GENERATOR = new Well19937c(10);
    private final RandomGenerator MEAN_DEADLINE_GENERATOR = new Well19937c(11);

    private final RandomGenerator GENERATE_REQUEST = new Well19937c(12);

    private int numOfR;
    private int numOfGeneratedR;

    private double currentChance = -0.01;
    private final double THRESHOLD = 1.5;
    private double threshold = 1.5;

    private double pRThreshold = 1.7;
    private final double THRESHOLD_PR = 1.7;
    private final double deadlineMeanThreshold = 0.4;
    private boolean generatePR;
    private int numOfPR;
    private int numOfGeneratedPR;
    private int currentMeanD;
    private double gaussDistMeansD[];
    private int minDeadline = 10;

    // Numbers between 1 - 100, each is the percentage
    // of the total disc size and points to the area
    private int[] gaussDistMeansP;
    private int currentMeanP;
    private final double sigma = Math.sqrt(0.2);
    private final double gaussMeanSwitchThreshold = 0.2;

    public PGenerator(Disc disc, int numOfR, boolean generatePR, int numOfPR, double gaussDistMeansD[], int[] gaussDistMeansP){
        this.disc = disc;
        this.numOfR = numOfR;
        this.generatePR = generatePR;
        this.numOfPR = numOfPR;
        this.gaussDistMeansD = gaussDistMeansD;
        this.gaussDistMeansP = gaussDistMeansP;
    }

    private void changeCurrentMeanP(){
        if (MEAN_POSITION_CHANGE_GENERATOR.nextFloat() < gaussMeanSwitchThreshold){
            currentMeanP = MEAN_POSITION_GENERATOR.nextInt(gaussDistMeansP.length);
            //System.out.println("[PP][CURRENT MEAN] : " + currentMean);
        }
    }

    private int calculateGaussianPosition(){
        return (int)Math.min(disc.size(), Math.abs((GAUSS_POSITION_GENERATOR.nextGaussian() * sigma * (0.1 * disc.size())
                + gaussDistMeansP[currentMeanP])));
    }

    private int getGaussRequestPosition(){
        changeCurrentMeanP();
        return calculateGaussianPosition();
    }

    private void changeCurrentMeanD(){
        if (MEAN_DEADLINE_CHANGE_GENERATOR.nextFloat() < deadlineMeanThreshold){
            currentMeanD = MEAN_DEADLINE_GENERATOR.nextInt(gaussDistMeansD.length);
            //System.out.println("[DP][CURRENT MEAN] : " + currentMean);
        }
    }

    private boolean shouldGeneratePriorityRequest(){
        pRThreshold += (1 / (double)(1 + getNumberOfGenerated() * disc.size()));
        if (pRThreshold >= 2.5) pRThreshold = THRESHOLD_PR;
        return generatePR && (((pRThreshold < PRIORITY_GENERATOR.nextGaussian())
                && (numOfGeneratedPR < numOfPR)));
    }

    private boolean shouldGenerateRequest(){
        //if (incr / disc.size() >= 2) incr = 0;
        //threshold += (1 / (double)(numOfR + numOfPR));
        //if (threshold >= 2) threshold = 1.5;
        // 100
        int div = getNumberOfGenerated() * disc.size();
        threshold += (1 / (double)(div));
        if (threshold >= 3) threshold = THRESHOLD;
        return (numOfGeneratedR < numOfR) && ((threshold
                <= (ACCESS_GENERATOR.nextGaussian()) + currentChance));
    }


    private Request generatePriorityRequest(){
        changeCurrentMeanD();
        int position = getGaussRequestPosition();
        int deadline = Math.max(minDeadline, (int)(Math.abs((PRIORITY_DEADLINE_GENERATOR.nextGaussian()) * Math.sqrt(gaussDistMeansD[currentMeanD]))
                * disc.size()));
        numOfGeneratedPR++;
        return new Request(position, Time.t, deadline);
    }

    private Request generateRequest(){
        int position = getGaussRequestPosition();
        numOfGeneratedR++;
        return new Request(position, Time.t, 0);
    }


    @Override
    public Request next() {
        currentChance *= (-1);
        if (hasNext()){
            if (shouldGenerateRequest()) return generateRequest();
            else if (shouldGeneratePriorityRequest()) return generatePriorityRequest();
            else return null;
        } else return null;
    }

    @Override
    public boolean hasNext() {
        return (generatePR && (numOfGeneratedPR < numOfPR)) || (numOfGeneratedR < numOfR);
    }

    @Override
    public int getNumberOfGenerated() {
        return numOfGeneratedPR + numOfGeneratedR;
    }
}
