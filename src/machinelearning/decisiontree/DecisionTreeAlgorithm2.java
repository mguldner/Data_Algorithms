package machinelearning.decisiontree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import machinelearning.decisiontree.data.Leaf2;
import machinelearning.decisiontree.data.Node2;
import machinelearning.decisiontree.data.Tree2;
import machinelearning.general.DataObject2;

/**
 * 
 * @author mathieu
 *
 * @param <T> le type de retour : Integer si mort ou vivant, String pour nom du fruit/légume
 */
public class DecisionTreeAlgorithm2<T, U extends DataObject2<T>> {

    private double trustProbability;

    public DecisionTreeAlgorithm2(double trustProbability){
        this.trustProbability = trustProbability;
    }

    public void setTrustProbability(double trustProbability){
        this.trustProbability = trustProbability;
    }

    public double getTrustProbability(){
        return this.trustProbability;
    }

    public Tree2<T> train(List<U> data, List<String> remainingFeatures){
        Entry<T, Long> guess = mostFrequentAnswerJava8(data);
        if((double)guess.getValue() > (double)data.size() * trustProbability 
                || remainingFeatures.isEmpty())
            return new Leaf2<>(guess.getKey());
        else{
            Map<Object, List<U>> finalSubsets = new HashMap<>();
            int finalScore = -1;
            String finalFeature="";
            for(String feature : remainingFeatures){
                Map<Object, List<U>> subsets = new HashMap<>();
                List<Object> featureValues = valuesOf(feature, data);
                int score = 0;
                for(Object featureValue : featureValues){
                    List<U> subset = data
                            .stream()
                            .filter(e -> e.getValueForFeature(feature).equals(featureValue))
                            .collect(Collectors.toList());
                    subsets.put(featureValue, subset);
                    score += mostFrequentAnswerJava8(subset)
                            .getValue()
                            .intValue();
                }
                if(score > finalScore){
                    finalScore = score;
                    finalSubsets = subsets;
                    finalFeature = feature;
                }
            }
            remainingFeatures.remove(finalFeature);
            Map<Object,Tree2<T>> children = new HashMap<>();
            for(Entry<Object, List<U>> entry : finalSubsets.entrySet()){
                children.put(entry.getKey(), train(entry.getValue(), remainingFeatures));
            }
            return new Node2<>(finalFeature, children);
        }
    }

    public T test(Tree2<T> tree, U testPoint){
        if(tree instanceof Leaf2)
            return ((Leaf2<T>)tree).getValue();
        else if(tree instanceof Node2){
            Node2<T> treeAsNode = (Node2<T>)tree;
            Object featureValue = testPoint.getValueForFeature(treeAsNode.getFeature());
            return test(treeAsNode.getChild(featureValue), testPoint);
        }
        else
            return null;
    }

    public List<Object> valuesOf(String feature, List<U> data){
        return data
                .stream()
                .map(e -> e.getValueForFeature(feature))
                .distinct()
                .collect(Collectors.toList());
    }

    public Entry<T, Long> mostFrequentAnswerJava8(List<U> data){
        //System.out.println("data : "); 
//        for(U e : data)
//            System.out.println(e.getAnswerValue().toString());
        Map<T, Long> classification = data.stream()
                .map(U::getAnswerValue)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//        System.out.println(classification.toString());
        Entry<T, Long> bestAnswer = classification
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        return bestAnswer;
    }
}
