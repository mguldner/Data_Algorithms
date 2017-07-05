package machinelearning.example.titanic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import machinelearning.decisiontree.DecisionTreeAlgorithm2;
import machinelearning.decisiontree.data.Tree2;
import machinelearning.decisiontree.features.Frame;
import machinelearning.general.LossFunction;
import machinelearning.general.SquareLossFunction;
import machinelearning.general.exception.MissingValueException;

public class RunExample2 {

    final static String folder = "/home/mathieu/Machine_Learning/Titanic";
    final static String trainingFile = folder + "/train.csv";
    final static String testFile = folder + "/test.csv";
    final static String testAnswersFile = folder + "/gender_submission.csv";

    public double runAlgorithm(double trustProbability) throws FileNotFoundException, IOException {
        ArrayList<Passenger2> trainingSet = new ArrayList<Passenger2>();

        try(BufferedReader br = new BufferedReader(new FileReader(trainingFile))){
            String line;
            boolean firstLine = true;
            while((line = br.readLine()) != null){
                if(firstLine){
                    firstLine = false;
                }
                else{
//                    System.out.println(line);
                    String[] values = line.split(",");
//                    System.out.println(values[0]);
//                    System.out.println(values[2]);
//                    System.out.println(values[3]);
//                    System.out.println(values[4]);
//                    System.out.println(values[5]);
//                    System.out.println(values[6]);
//                    System.out.println(values[8]);
//                    System.out.println(values[10]);
                    if("".equals(values[4]))
                        values[4] = "-1";
                    Passenger2 tmpPassenger = new Passenger2(
                            Integer.parseInt(values[0]),
                            Integer.parseInt(values[2]),
                            "male".equals(values[3]),
                            getAgeFrame(Double.parseDouble(values[4])),
                            Integer.parseInt(values[5]),
                            Integer.parseInt(values[6]),
                            getFareFrame(Double.parseDouble(values[8])),
                            values[10]);
                    tmpPassenger.setAnswerValue(Integer.parseInt(values[1]) == 1);
                    trainingSet.add(tmpPassenger);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        ArrayList<Passenger2> testSet = new ArrayList<>();
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
                    //                    System.out.println(line);
                    Passenger2 tmpPassenger = new Passenger2(
                            Integer.parseInt(values[0]),
                            Integer.parseInt(values[1]),
                            "male".equals(values[2]),
                            getAgeFrame(Double.parseDouble(values[3])),
                            Integer.parseInt(values[4]),
                            Integer.parseInt(values[5]),
                            getFareFrame(Double.parseDouble(values[7])),
                            values[9]);
                    testSet.add(tmpPassenger);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        List<String> features = new ArrayList<>();
        features.add("pClass");
        features.add("isMale");
        features.add("age");
        features.add("sibSpNb");
        features.add("parChNb");
        features.add("fare");
        features.add("embarked");

        DecisionTreeAlgorithm2<Boolean, Passenger2> algorithm = new DecisionTreeAlgorithm2<>(trustProbability);
        //        System.out.println("pass : ");
        //        for(Passenger2 p : trainingSet)
        //            System.out.println(p.getEmbarked());
        //        for(Passenger2 p : trainingSet){
        //            System.out.println(p.getAnswerValue());
        //        }
        Tree2<Boolean> trainedTree = algorithm.train(trainingSet, features);

//        System.out.println("Trained Tree : \n " + trainedTree.toPrintableTree());
        for(Passenger2 p : testSet){
            p.setAnswerValue(algorithm.test(trainedTree, p));
            System.out.println(p.getPassengerId()+","+(p.getAnswerValue()==true?1:0));
        }

        LossFunction<Boolean> lossFunction = new SquareLossFunction<>();
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
        List<Boolean> prediction = testSet.stream().map(Passenger2::getAnswerValue).collect(Collectors.toList());
        double accuracy = -1;
        try {
            accuracy = lossFunction.getAccuracy(reality, prediction);
//            System.out.println("Accuracy : " + accuracy);
        } catch (MissingValueException e) {
            e.printStackTrace();
        }
        return accuracy;
    }

    public Frame getAgeFrame(double age){
        if(0 <= age){
            if(age <= 16)
                return new Frame(0,16);
            else if(age <= 21)
                return new Frame(17,21);
            else if(age <= 25)
                return new Frame(22,25);
            else if(age <= 31)
                return new Frame(26,31);
            else if(age <= 37)
                return new Frame(32,37);
            else if(age <= 46)
                return new Frame(38,46);
            else
                return new Frame(47,100);
        }
        else
            return new Frame(-1, -1);
    }
    
    public Frame getFareFrame(double fare){
        if(fare <= 7.73)
            return new Frame(0,7.73);
        else if(fare <= 7.889)
            return new Frame(7.74,7.889);
        else if(fare <= 8.655)
            return new Frame(7.89,8.655);
        else if(fare <= 12.9)
            return new Frame(8.66,12.9);
        else if(fare <= 16.2)
            return new Frame(13,16.2);
        else if(fare <= 26.1)
            return new Frame(16.3,26.1);
        else if(fare <= 34.7)
            return new Frame(26.2,34.7);
        else if(fare <= 8.72)
            return new Frame(34.8,72);
        else if(fare <= 1000)
            return new Frame(72.1,1000);
        else{
            System.err.println("[INFO:RunExample2] Missing Frame for fare.");
            return null;
        }
    }
    
    public static void main(String[] args) {
        /*for(double tP = 0.8; tP < 1; tP += 0.01){
            RunExample2 runExample = new RunExample2();
            try {
                System.out.println(tP + ";" + runExample.runAlgorithm(tP));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        RunExample2 runExample = new RunExample2();
        try{
            System.out.println("\n\nAccuracy : " + runExample.runAlgorithm(0.95));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
