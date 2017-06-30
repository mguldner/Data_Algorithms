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
import machinelearning.general.LossFunction;
import machinelearning.general.SquareLossFunction;
import machinelearning.general.exception.MissingValueException;

public class RunExample2 {

    final static String folder = "/home/mathieu/Machine_Learning/Titanic";
    final static String trainingFile = folder + "/train.csv";
    final static String testFile = folder + "/test.csv";
    final static String testAnswersFile = folder + "/gender_submission.csv";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ArrayList<Passenger2> trainingSet = new ArrayList<Passenger2>();

        try(BufferedReader br = new BufferedReader(new FileReader(trainingFile))){
            String line;
            boolean firstLine = true;
            while((line = br.readLine()) != null){
                if(firstLine){
                    firstLine = false;
                }
                else{
                    //System.out.println(line);
                    String[] values = line.split(",");
                    if("".equals(values[4]))
                        values[4] = "-1";
                    Passenger2 tmpPassenger = new Passenger2(
                            Integer.parseInt(values[0]),
                            Integer.parseInt(values[2]),
                            "male".equals(values[3]),
                            Double.parseDouble(values[4]),
                            Integer.parseInt(values[5]),
                            Integer.parseInt(values[6]),
                            values[7],
                            Double.parseDouble(values[8]),
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
                            Double.parseDouble(values[3]),
                            Integer.parseInt(values[4]),
                            Integer.parseInt(values[5]),
                            values[6],
                            Double.parseDouble(values[7]),
                            values[9]);
                    testSet.add(tmpPassenger);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        List<String> features = new ArrayList<>();
        features.add("passengerId");
        features.add("pClass");
        features.add("isMale");
        features.add("age");
        features.add("sibSpNb");
        features.add("parChNb");
        features.add("ticket");
        features.add("fare");
        features.add("embarked");

        DecisionTreeAlgorithm2<Boolean, Passenger2> algorithm = new DecisionTreeAlgorithm2<>(0.8);
//        System.out.println("pass : ");
//        for(Passenger2 p : trainingSet)
//            System.out.println(p.getEmbarked());
        Tree2<Boolean> trainedTree = algorithm.train(trainingSet, features);

        for(Passenger2 p : testSet){
            p.setAnswerValue(algorithm.test(trainedTree, p));
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
        for(Boolean p : prediction){
            System.out.println(p==true?1:0);
        }
        try {
            double accuracy = lossFunction.getAccuracy(reality, prediction);
            System.out.println("Accuracy : " + accuracy);
        } catch (MissingValueException e) {
            e.printStackTrace();
        }
    }
    
    public static Frame getFrame(int age){
    if(0 <= age){
      if(age <= 10)
        return new Frame(0,10);
      else if(age <= 20)
        return new Frame(11,20);
      else if(age <= 35)
        return new Frame(21,35);
      else if(age <= 60)
        return new Frame(36,60);
      else if(age <= 90)
        return new Frame(61,90);
      else
        return new Frame(91,150);
    }
    else
      return null;
  }
}
