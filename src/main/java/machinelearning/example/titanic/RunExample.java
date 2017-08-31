package machinelearning.example.titanic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import machinelearning.decisiontree.DecisionTreeAlgorithm;
import machinelearning.decisiontree.data.Tree;
import machinelearning.decisiontree.features.Frame;
import machinelearning.general.ScoreUtils;
import machinelearning.general.exception.AlgorithmException;
import machinelearning.general.exception.MissingValueException;
import machinelearning.general.lossfunction.HingeLossFunction;
import machinelearning.general.lossfunction.LogisticLossFunction;
import machinelearning.kmeans.Cluster;
import machinelearning.kmeans.DoubleTool;
import machinelearning.kmeans.KMeansAlgorithm;
import org.apache.log4j.Logger;

public class RunExample {

    private final static Logger logger = Logger.getLogger(RunExample.class);
    private final static String folder = "/home/mathieu/Machine_Learning/Titanic";
    private final static String trainingFile = folder + "/train.csv";
    private final static String testFile = folder + "/test.csv";
    private final static String testAnswersFile = folder + "/gender_submission.csv";
    
    private List<Passenger> trainingSet = new ArrayList<>();
    private List<Passenger> testSet = new ArrayList<>();
    private List<Passenger> allPassengers = null;

    private void initData() throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(trainingFile))){
            String line;
            boolean firstLine = true;
            while((line = br.readLine()) != null){
                if(firstLine){
                    firstLine = false;
                }
                else{
                    String[] values = line.split(",");
                    if("".equals(values[4]))
                        values[4] = "-1";
                    Passenger tmpPassenger = new Passenger(
                            Integer.parseInt(values[0]),
                            Integer.parseInt(values[2]),
                            "male".equals(values[3]),
                            Double.parseDouble(values[4]),
                            Integer.parseInt(values[5]),
                            Integer.parseInt(values[6]),
                            Double.parseDouble(values[8]),
                            values[10]);
                    tmpPassenger.setAnswerValue(Integer.parseInt(values[1]) == 1);
                    if("-1".equals(values[4]))
                        tmpPassenger.setAgeFrame(new Frame(-1, -1));
                    trainingSet.add(tmpPassenger);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        
        try(BufferedReader br = new BufferedReader(new FileReader(testFile))){
            String line;
            boolean firstLine = true;
            while((line = br.readLine()) != null){
                if(firstLine)
                    firstLine=false;
                else{
                    String[] values = line.split(",");
                    if("".equals(values[3]))
                        values[3] = "-1";
                    Passenger tmpPassenger = new Passenger(
                            Integer.parseInt(values[0]),
                            Integer.parseInt(values[1]),
                            "male".equals(values[2]),
                            Double.parseDouble(values[3]),
                            Integer.parseInt(values[4]),
                            Integer.parseInt(values[5]),
                            Double.parseDouble(values[7]),
                            values[9]);
                    if("-1".equals(values[3]))
                        tmpPassenger.setAgeFrame(new Frame(-1, -1));
                    testSet.add(tmpPassenger);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        allPassengers = new ArrayList<>(trainingSet);
        allPassengers.addAll(testSet);
        if(logger.isDebugEnabled()){
            logger.debug("data ok");
        }
    }
    
    private int determineBestK(String attribute, int min, int max, String scoringMethod) {
        List<Double> data = null;
        if("age".equals(attribute)){
            data = allPassengers.stream().filter(p -> !p.isAgeFrameSet()).map(Passenger::getAge).collect(Collectors.toList());
        } else if("fare".equals(attribute)){
            data = allPassengers.stream().map(Passenger::getFare).collect(Collectors.toList());
        }
        int bestK = -1;
        double bestScore = 0.0;
        for(int k=min; k<=max; k++){
            KMeansAlgorithm<Double> doubleKMeansAlgorithm = new KMeansAlgorithm<>(data, k, new DoubleTool());
            doubleKMeansAlgorithm.runMultipleTime(10, scoringMethod);
            double score = doubleKMeansAlgorithm.getClustersScore(scoringMethod);
            System.out.println("Pour " + attribute + " avec k = " + k + ", score de " + score + " >? " + bestScore);
            if(score > bestScore){
                bestScore = score;
                bestK = k;
            }
        }
        return bestK;
    }
    
    private void runPredictionAlgorithm(double trustProbability, int kAge, int kFare, String clusterScoringMethod) throws IOException, AlgorithmException {
        List<Frame> ageFrames = getDoubleFrames(allPassengers.stream().filter(p -> !p.isAgeFrameSet()).map(Passenger::getAge).collect(Collectors.toList()), kAge, clusterScoringMethod);
        ageFrames.add(new Frame(-1, -1));
        
        List<Frame> fareFrames = getDoubleFrames(allPassengers.stream().map(Passenger::getFare).collect(Collectors.toList()), kFare, clusterScoringMethod);
        
        if(logger.isInfoEnabled()){
            logger.info("Age Frames : " + ageFrames.size());
            for(Frame f : ageFrames){
                System.out.println(f.toString());
            }
            
            logger.info("\n\n\nFare Frames : " + fareFrames.size());
            for(Frame f : fareFrames){
                System.out.println(f.toString());
            }
        }
        
        allPassengers.stream().forEach(passenger -> {
            try {
                setAgeFrame(passenger, ageFrames);
                setFareFrame(passenger, fareFrames);
            } catch (AlgorithmException e) {
                e.printStackTrace();
            }
        });
        
        List<String> features = new ArrayList<>();
        features.add("pClass");
        features.add("isMale");
        features.add("ageFrame");
        features.add("sibSpNb");
        features.add("parChNb");
        features.add("fareFrame");
        features.add("embarked");

        DecisionTreeAlgorithm<Boolean, Passenger> algorithm = new DecisionTreeAlgorithm<>(trustProbability);
        Tree<Boolean> trainedTree = algorithm.train(trainingSet, features);

        for(Passenger p : testSet){
            p.setAnswerValue(algorithm.test(trainedTree, p));
            if(logger.isInfoEnabled()){
                System.out.println(p.getPassengerId()+","+(p.getAnswerValue()?1:0));
            }
        }
    }
    
    private void evaluateSolution(){
        List<Boolean> reality = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(testAnswersFile))){
            String line;
            boolean firstLine = true;
            while((line = br.readLine()) != null){
                if(firstLine)
                    firstLine = false;
                else{
                    String[] values = line.split(",");
                    reality.add(Integer.parseInt(values[1])==1);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        List<Boolean> prediction = testSet.stream().map(Passenger::getAnswerValue).collect(Collectors.toList());

        if(logger.isInfoEnabled()) {
            try {
                ScoreUtils<Boolean> sU = new ScoreUtils<>(prediction, reality, true, false);
                logger.info(sU.toString());
                logger.info("logisticSum = " + new LogisticLossFunction().getDiscrepancy(prediction, reality));
                logger.info("hingeSum = " + new HingeLossFunction().getDiscrepancy(prediction, reality));
            } catch (MissingValueException e) {
                e.printStackTrace();
            }
        }
    }
    
    private List<Frame> getDoubleFrames(List<Double> data, int k, String clusterScoringMethod) throws AlgorithmException{
        KMeansAlgorithm<Double> doubleKMeansAlgorithm = new KMeansAlgorithm<>(data, k, new DoubleTool());
        doubleKMeansAlgorithm.runMultipleTime(10, clusterScoringMethod);
        if(logger.isDebugEnabled()){
            logger.debug("K-Means has run");
        }
        
        List<Frame> frames = new ArrayList<>();
        for (Cluster<Double> cluster : doubleKMeansAlgorithm.getClusters())
        {
            if(cluster.getSize() != 0) {
                List<Double> clusterPoints = cluster.getPoints();
                Frame frame = new Frame(Collections.min(clusterPoints), Collections.max(clusterPoints));
                if (frames.isEmpty())
                    frames.add(frame);
                else {
                    boolean frameInserted = false;
                    for (int i = 0; i < frames.size(); i++) {
                        if (!frameInserted && frames.get(i).getMin() >= frame.getMax()) {
                            frames.add(i, frame);
                            frameInserted = true;
                        }
                    }
                    if (!frameInserted && frame.getMin() >= frames.get(frames.size() - 1).getMax()) {
                        frames.add(frame);
                        frameInserted = true;
                    }
                    if (!frameInserted) {
                        logger.error("Actual frames : \n" + frames.stream().map(f -> f.toString()).collect(Collectors.joining("\n")));
                        logger.error("New frame : " + frame.toString());
                        throw new AlgorithmException("[RunExample] The frames done by the Double-K-Means algorithm are not right : they share same elements");
                    }
                }
            }
        }
        return frames;
    }
    
    private void setAgeFrame(Passenger p, List<Frame> ageFrames) throws AlgorithmException{
        boolean ageFrameSet = false;
        for(Frame f : ageFrames){
            if(p.getAge() >= f.getMin() && p.getAge() <= f.getMax()){
                p.setAgeFrame(f);
                ageFrameSet = true;
            }
        }
        if(!ageFrameSet)
            throw new AlgorithmException("[RunExample] The Passenger " + p.getPassengerId() + " has no Frame for age.");
    }
    
    private void setFareFrame(Passenger p, List<Frame> fareFrames) throws AlgorithmException{
        boolean fareFrameSet = false;
        for(Frame f : fareFrames){
            if(p.getFare() >= f.getMin() && p.getFare() <= f.getMax()){
                p.setFareFrame(f);
                fareFrameSet = true;
            }
        }
        if(!fareFrameSet)
            throw new AlgorithmException("[RunExample] The Passenger " + p.getPassengerId() + " has no Frame for fare.");
    }

    public static void main(String[] args) throws AlgorithmException {
        RunExample runExample = new RunExample();
        String clusterScoringMethod = "basic";
        try{
            runExample.initData();
            int kAge = runExample.determineBestK("age", 2, 50, "basic");
            int kFare= runExample.determineBestK("fare", 2, 50, clusterScoringMethod);
            System.out.println("");
            runExample.runPredictionAlgorithm(0.95, kAge, kFare, clusterScoringMethod);
            runExample.evaluateSolution();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
