package machinelearning.example.titanic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import machinelearning.decisiontree.DecisionTreeAlgorithm2;

public class RunExample2 {

  public static void main(String[] args) throws FileNotFoundException, IOException {
    ArrayList<Passenger2> trainingSet = new ArrayList<Passenger2>();

    String trainingFile = "";
    try(BufferedReader br = new BufferedReader(new FileReader(trainingFile))){
      String line;
      while((line = br.readLine()) != null){
        String[] values = line.split(";");
        if("".equals(values[4]))
          values[4] = "-1";
        Passenger2 tmpPassenger = new Passenger2(
            Integer.parseInt(values[0]),
            Integer.parseInt(values[2]),
            "M".equals(values[3]),
            Integer.parseInt(values[4]),
            Integer.parseInt(values[5]),
            Integer.parseInt(values[6]),
            values[7],
            Double.parseDouble(values[8]),
            values[9],
            values[10]);
        tmpPassenger.setAnswerValue(true);
        trainingSet.add(tmpPassenger);
      }
    } catch(Exception e){
      e.printStackTrace();
    }

    ArrayList<Passenger2> testSet = new ArrayList<>();
    String testFile = "";
    try(BufferedReader br = new BufferedReader(new FileReader(testFile))){
      String line;
      while((line = br.readLine()) != null){
        String[] values = line.split(";");
        if("".equals(values[4]))
          values[4] = "-1";
        testSet.add(new Passenger2(
            Integer.parseInt(values[0]),
            Integer.parseInt(values[2]),
            "M".equals(values[3]),
            Integer.parseInt(values[4]),
            Integer.parseInt(values[5]),
            Integer.parseInt(values[6]),
            values[7],
            Double.parseDouble(values[8]),
            values[9],
            values[10]));
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
    features.add("cabin");
    features.add("embarked");
    
    DecisionTreeAlgorithm2<Boolean, Passenger2> algorithm = new DecisionTreeAlgorithm2<>(0.8);
    algorithm.train(trainingSet, features);
    
    for(Passenger2 p : testSet){
      p.setAnswerValue(algorithm.test(algorithm.getTrainedTree(), p));
    }
    
    
  }
}
