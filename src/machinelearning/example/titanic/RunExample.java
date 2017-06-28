package machinelearning.example.titanic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import machinelearning.decisiontree.Answer;
import machinelearning.decisiontree.DecisionTreeAlgorithm;
import machinelearning.decisiontree.Feature;
import machinelearning.decisiontree.data.Tree;
import machinelearning.decisiontree.features.Frame;
import machinelearning.general.LossFunction;

public class RunExample {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ArrayList<Passenger> trainingSet = new ArrayList<Passenger>();
        HashMap<Integer, Boolean> trainingSetSurvivor = new HashMap<Integer, Boolean>();
        
        String trainingFile = "";
        try(BufferedReader br = new BufferedReader(new FileReader(trainingFile))){
            String line;
            while((line = br.readLine()) != null){
                String[] values = line.split(";");
                if("".equals(values[4]))
                    values[4] = "-1";
                trainingSet.add(new Passenger(
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
                trainingSetSurvivor.put(Integer.parseInt(values[0]), "1".equals(values[1]));
            }
        }
        
        ArrayList<Passenger> testSet = new ArrayList<Passenger>();
        HashMap<Integer, Boolean> testSetSurvivor = new HashMap<Integer, Boolean>();
        String testFile = "";
        try(BufferedReader br = new BufferedReader(new FileReader(testFile))){
            String line;
            while((line = br.readLine()) != null){
                String[] values = line.split(";");
                if("".equals(values[4]))
                    values[4] = "-1";
                testSet.add(new Passenger(
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
        }
        // Listes a definir auparavant et a completer au fur et a mesure de la lecture des donnees d'entree
        ArrayList<Integer> possiblePClasses = new ArrayList<Integer>();
        possiblePClasses.add(1);
        possiblePClasses.add(2);
        possiblePClasses.add(3);
        Feature<Integer> passengerClass = new Feature<Integer>("pClass", possiblePClasses);
        
        ArrayList<Boolean> possibleSex = new ArrayList<Boolean>();
        possibleSex.add(true);
        possibleSex.add(false);
        Feature<Boolean> sex = new Feature<Boolean>("isMale", possibleSex);
        
        ArrayList<Frame> possibleAges = new ArrayList<Frame>();
        possibleAges.add(new Frame(0,18));
        possibleAges.add(new Frame(18,50));
        possibleAges.add(new Frame(50,100));
        Feature<Frame> age = new Feature<Frame>("age", possibleAges);
        
        ArrayList<Integer> possibleSibSpNb = new ArrayList<Integer>();
        possibleSibSpNb.add(0);
        possibleSibSpNb.add(1);
        possibleSibSpNb.add(2);
        possibleSibSpNb.add(3);
        possibleSibSpNb.add(4);
        possibleSibSpNb.add(5);
        possibleSibSpNb.add(8);
        Feature<Integer> sibSpNb = new Feature<Integer>("sibSpNb", possibleSibSpNb);
        
        ArrayList<Integer> possibleParChNb = new ArrayList<Integer>();
        possibleParChNb.add(0);
        possibleParChNb.add(1);
        possibleParChNb.add(2);
        possibleParChNb.add(3);
        possibleParChNb.add(4);
        possibleParChNb.add(5);
        possibleParChNb.add(6);
        Feature<Integer> parChNb = new Feature<Integer>("parChNb", possibleParChNb);
        
        ArrayList<Frame> possibleFare = new ArrayList<Frame>();
        possibleFare.add(new Frame(0,10));
        possibleFare.add(new Frame(10,100));
        possibleFare.add(new Frame(100,1000));
        Feature<Frame> fare = new Feature<Frame>("fare", possibleFare);
        
        ArrayList<String> possibleEmbarcations = new ArrayList<String>();
        possibleEmbarcations.add("C");
        possibleEmbarcations.add("Q");
        possibleEmbarcations.add("S");
        Feature<String> embarcation = new Feature<String>("embarked", possibleEmbarcations);
        
        List<Feature<?>> features = new ArrayList<Feature<?>>();
        features.add(passengerClass);
        features.add(sex);
        features.add(age);
        features.add(sibSpNb);
        features.add(parChNb);
        features.add(fare);
        features.add(embarcation);
        DecisionTreeAlgorithm<Integer, Passenger> decisionTreeAlgorithm = new DecisionTreeAlgorithm<Integer, Passenger>(0.8);
        Tree<Answer<?,Integer>> treeTrained = decisionTreeAlgorithm.train(trainingSet, features);
        for(Passenger p : testSet){
            p.setLabel(decisionTreeAlgorithm.test(treeTrained, p));
        }
        
//        LossFunction lossFunction = new LossFunction();
//        reality = ;
//        lossFunction.getError(reality, prediction);
    }
}
