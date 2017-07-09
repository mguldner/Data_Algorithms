package machinelearning.decisiontree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import machinelearning.decisiontree.data.Leaf;
import machinelearning.decisiontree.data.Node;
import machinelearning.decisiontree.data.Tree;
import machinelearning.general.DataObject;

/**
 * 
 * @author mathieu
 *
 * @param <T> le type de retour : 
 * - Integer si mort ou vivant dans le cas du Titanic, 
 * - String pour nom du fruit lors d'une classification de fruit (exemple bidon).
 * @param <U> le type d'objet en question : 
 * - un Passenger dans le cas du Titanic, 
 * - un Fruit dans le cas d'une classification de fruit.
 */
public class DecisionTreeAlgorithm<T, U extends DataObject<T>> {

    private double trustProbability;

    public DecisionTreeAlgorithm(double trustProbability){
        this.trustProbability = trustProbability;
    }

    public void setTrustProbability(double trustProbability){
        this.trustProbability = trustProbability;
    }

    public double getTrustProbability(){
        return this.trustProbability;
    }

    public Tree<T> train(List<U> data, List<String> remainingFeatures){
        Entry<T, Long> guess = mostFrequentAnswerJava8(data);
        if((double)guess.getValue() > (double)data.size() * trustProbability 
                || remainingFeatures.isEmpty())
            return new Leaf<>(guess.getKey());
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
            Map<Object,Tree<T>> children = new HashMap<>();
            for(Entry<Object, List<U>> entry : finalSubsets.entrySet()){
                children.put(entry.getKey(), train(entry.getValue(), remainingFeatures));
            }
            return new Node<>(finalFeature, children, guess.getKey());
        }
    }

    public T test(Tree<T> tree, U testPoint){
        if(tree instanceof Leaf)
            return ((Leaf<T>)tree).getValue();
        else if(tree instanceof Node){
            Node<T> treeAsNode = (Node<T>)tree;
            Object featureValue = testPoint.getValueForFeature(treeAsNode.getFeature());
            Tree<T> childForFeature = treeAsNode.getChild(featureValue);
            if(childForFeature == null)
                return treeAsNode.getValue();
            else
                return test(childForFeature, testPoint);
        }
        else{
            return null;
        }
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
