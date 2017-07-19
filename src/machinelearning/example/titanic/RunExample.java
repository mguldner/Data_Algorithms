package machinelearning.example.titanic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import machinelearning.decisiontree.DecisionTreeAlgorithm;
import machinelearning.decisiontree.data.Tree;
import machinelearning.decisiontree.features.Frame;
import machinelearning.general.ScoreUtils;
import machinelearning.general.exception.AlgorithmException;
import machinelearning.general.exception.MissingValueException;
import machinelearning.general.lossfunction.HingeLossFunction;
import machinelearning.general.lossfunction.LogisticLossFunction;
import machinelearning.kmeans.DoubleComparator;
import machinelearning.kmeans.DoubleMeanTool;
import machinelearning.kmeans.KMeansAlgorithm;

public class RunExample {

    final static String folder = "/home/mathieu/Machine_Learning/Titanic";
    final static String trainingFile = folder + "/train.csv";
    final static String testFile = folder + "/test.csv";
    final static String testAnswersFile = folder + "/gender_submission.csv";

    public double runAlgorithm(double trustProbability) throws FileNotFoundException, IOException, AlgorithmException {
        ArrayList<Passenger> trainingSet = new ArrayList<Passenger>();

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
                    trainingSet.add(tmpPassenger);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        
        ArrayList<Passenger> testSet = new ArrayList<>();
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
                    testSet.add(tmpPassenger);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        int kAge = 5;
        List<Frame> ageFrames = getDoubleFrames(trainingSet.stream().map(Passenger::getAge).collect(Collectors.toList()), kAge);
        System.out.println("Age frames : \n");
        for(Frame f : ageFrames){
            System.out.println(f.getMin() + "  --  " + f.getMax() + "\n");
        }
        int kFare = 5;
        List<Frame> fareFrames = getDoubleFrames(trainingSet.stream().map(Passenger::getFare).collect(Collectors.toList()), kFare);
        System.out.println("Fare frames : \n");
        for(Frame f : fareFrames){
            System.out.println(f.getMin() + "  --  " + f.getMax() + "\n");
        }
        List<Passenger> allPassengers = new ArrayList<Passenger>(trainingSet);
        allPassengers.addAll(testSet);
        for(Passenger p : allPassengers){
            boolean ageFrameSet = false;
            for(Frame f : ageFrames){
                if(p.getAge() >= f.getMin() && p.getAge() <= f.getMax()){
                    p.setAgeFrame(f);
                    ageFrameSet = true;
                }
            }
            boolean fareFrameSet = false;
            for(Frame f : fareFrames){
                if(p.getFare() >= f.getMin() && p.getFare() <= f.getMax()){
                    p.setFareFrame(f);
                    fareFrameSet = true;
                }
            }
            if(!ageFrameSet)
                throw new AlgorithmException("[RunExample] The Passenger " + p.getPassengerId() + " has no Frame for age.");
            if(!fareFrameSet)
                throw new AlgorithmException("[RunExample] The Passenger " + p.getPassengerId() + " has no Frame for fare.");
        }
        
        
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
            System.out.println(p.getPassengerId()+","+(p.getAnswerValue()==true?1:0));
        }

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
        double accuracy = -1;
        try {
            ScoreUtils<Boolean> sU = new ScoreUtils<Boolean>(prediction, reality, true, false);
            accuracy = sU.getAccuracy();
            System.out.println(sU.toString());
            System.out.println("logisticSum = " + new LogisticLossFunction().getDiscrepancy(prediction, reality));
            System.out.println("hingeSum = " + new HingeLossFunction().getDiscrepancy(prediction, reality));
        } catch (MissingValueException e) {
            e.printStackTrace();
        }
        return accuracy;
    }
    
    public List<Frame> getDoubleFrames(List<Double> data, int k) throws AlgorithmException{
        KMeansAlgorithm<Double> kMeansAlgorithm = new KMeansAlgorithm<>(new DoubleComparator(), new DoubleMeanTool());
        Map<Integer, ArrayList<Double>> framesData = kMeansAlgorithm.apply(data, k);
        
        List<Frame> frames = new ArrayList<Frame>();
        for (Entry<Integer, ArrayList<Double>> entry : framesData.entrySet())
        {
            List<Double> entryValue = entry.getValue();
            Frame frame = new Frame(Collections.min(entryValue), Collections.max(entryValue));
            if(frames.isEmpty())
                frames.add(frame);
            else{
                boolean frameInserted = false;
                for(int i=0; i<frames.size(); i++) {
                    if(!frameInserted && frames.get(i).getMin() >= frame.getMax()){
                        frames.add(i, frame);
                    }
                }
                if(!frameInserted && frame.getMin() >= frames.get(frames.size()-1).getMax())
                    frames.add(frame);
                else
                    throw new AlgorithmException("[RunExample] The frames are not right done by the K-Means algorithm.");
            }
        }
        return frames;
    }

    public Frame getAgeFrame(double age){
        if(0 <= age){
            if(age <= 6)
                return new Frame(0,6);
            else if(age <= 11)
                return new Frame(7,11);
            else if(age <= 13)
                return new Frame(12,13);
            else if(age <= 15)
                return new Frame(14,15);
            else if(age <= 19)
                return new Frame(16,19);
            else if(age <= 21)
                return new Frame(20,21);
            else if(age <= 28)
                return new Frame(22,28);
            else if(age <= 34)
                return new Frame(29,34);
            else if(age <= 38)
                return new Frame(35,38);
            else if(age <= 42)
                return new Frame(39,42);
            else if(age <= 45)
                return new Frame(43,45);
            else if(age <= 47)
                return new Frame(46,47);
            else if(age <= 49)
                return new Frame(48,49);
            else if(age <= 63)
                return new Frame(50,63);
            else
                return new Frame(64,100);
        }
        else
            return new Frame(-1, -1);
    }
    
    public Frame getFareFrame(double fare){
        if(fare <= 6.949)
            return new Frame(0,6.949);
        else if(fare <= 7.249)
            return new Frame(6.95,7.249);
        else if(fare <= 7.329)
            return new Frame(7.25,7.329);
        else if(fare <= 8.109)
            return new Frame(7.33,8.109);
        else if(fare <= 9.349)
            return new Frame(8.11,9.349);
        else if(fare <= 9.829)
            return new Frame(9.35,9.829);
        else if(fare <= 12.9)
            return new Frame(9.83,12.9);
        else if(fare <= 15.749)
            return new Frame(13,15.749);
        else if(fare <= 20.249)
            return new Frame(15.75,20.249);
        else if(fare <= 21.0749)
            return new Frame(20.25,21.0749);
        else if(fare <= 23.249)
            return new Frame(21.075,23.249);
        else if(fare <= 25.919)
            return new Frame(23.25,25.919);
        else if(fare <= 27.89)
            return new Frame(25.92,27.89);
        else if(fare <= 29.9)
            return new Frame(27.9,29.9);
        else if(fare <= 33.49)
            return new Frame(30,33.49);
        else if(fare <= 34.9)
            return new Frame(33.5,34.9);
        else if(fare <= 45.9)
            return new Frame(35,45.9);
        else if(fare <= 56.9)
            return new Frame(46,56.9);
        else if(fare <= 65.9)
            return new Frame(57,65.9);
        else if(fare <= 70.9)
            return new Frame(66,70.9);
        else if(fare <= 78.26)
            return new Frame(71,78.26);
        else if(fare <= 84.9)
            return new Frame(78.26,84.9);
        else if(fare <= 105.9)
            return new Frame(85,105.9);
        else if(fare <= 163.9)
            return new Frame(106,163.9);
        else if(fare <= 1000)
            return new Frame(164,1000);
        else{
            System.err.println("[INFO:RunExample] Missing Frame for fare.");
            return null;
        }
    }
    
    public static void main(String[] args) throws AlgorithmException {
        RunExample runExample = new RunExample();
        try{
            /*System.out.println("0.80 : ");
            runExample.runAlgorithm(0.80);
            System.out.println("\n\n\n0.85");
            runExample.runAlgorithm(0.85);
            System.out.println("\n\n\n0.95");
            runExample.runAlgorithm(0.95);
            System.out.println("\n\n\n1");*/
            runExample.runAlgorithm(0.95);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
