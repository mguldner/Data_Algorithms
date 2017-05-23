package machinelearning.decisiontree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import machinelearning.decisiontree.data.Leaf;
import decisiontree.data.Node;
import decisiontree.data.Tree;
import general.Algorithm;
import general.DataObject;

/*
 * ? :  Le type de chacune des Features -> on ne le connait pas d'avance ! 
 *      Dans le cas du titanic, une String pour l'endroit d'embarcation, un plage de valeurs pour l'age, etc.
 * T :  Le type de la structure de donnee d'entree choisie
 * U :  Le type de la valeur finale (la donnee que l'on cherche a prevoir). 
 *      Dans le cas du titanic, un Integer 0 (mort) ou 1 (vivant).
 *
 * On obtient un arbre du type
 * 
 *                         -----------------0---------------
 *                         |                               |
 *                       Male                           Female
 *                ---------1----------            ---------0----------
 *                |        |         |            |        |         |
 *              x<18    18<x<50     50<x         <16    16<x<60     60<x
 *                0        1         0            1        0         1
 */

public final class DecisionTreeAlgorithm<U, T extends DataObject<U>> implements Algorithm<U, T, Tree<Answer<?, U>>> {
    
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
    
    
    
    @Override
    public Tree<Answer<?,U>> train(List<T> data, List<Feature<?>> features){
        Answer<?,U> bestLabel = this.mostFrequent(data);
        if(bestLabel != null || features.isEmpty()){
            // No need to split further || cannot split further
            return new Leaf<>(bestLabel);
        } else {
            int[][] scoreFeature = new int[features.size()][];
            for(int i=0; i<features.size(); i++){
                scoreFeature[i] = new int[features.get(i).getPossibleValues().size()];
            }
            // Mieux vaut boucler sur les donnees et sous-boucler sur les features que l'inverse !
            for(T tmpData : data){
                for(int i=0; i<features.size(); i++){
                    int featureClass = tmpData.classificationForFeature(features.get(i));
                    if(featureClass != -1)
                        scoreFeature[i][featureClass]++;
                }
            }
            int maxScore = -1;
            int winnerFeature = -1;
            for(int i=0; i<features.size(); i++){
                int tmpScore = -1;
                for(int j=0; j<scoreFeature[i].length; j++){
                    tmpScore = Math.max(tmpScore, scoreFeature[i][j]);
                }
                if(maxScore < tmpScore){
                    maxScore = tmpScore;
                    winnerFeature = i;
                }
            }
            
            Feature<?> winningFeature = features.remove(winnerFeature);
            List<ArrayList<T>> listClassifiedData = new ArrayList<ArrayList<T>>();
            for(int i=0; i<winningFeature.getPossibleValues().size(); i++){
                ArrayList<T> classifiedData = new ArrayList<T>();
                listClassifiedData.add(classifiedData);
            }
            for(T tmpData : data){
                int featureClass = tmpData.classificationForFeature(winningFeature);
                listClassifiedData.get(featureClass).add(tmpData);
            }
            List<Tree<Answer<?,U>>> children = new ArrayList<Tree<Answer<?,U>>>();
            for(int i=0; i<winningFeature.getPossibleValues().size(); i++){
                children.add(train(listClassifiedData.get(i), features));
            }
            return new Node<>(bestLabel, children);
        }
    }
    
    @Override
    public U test(Tree<Answer<?,U>> tree, T testPoint){
        if(tree instanceof Leaf<?>){
            return tree.getValue().getLabel();
        } else if (tree instanceof Node<?>){
            
        }
        return null;
    }
    
    private Answer<?,U> mostFrequent(List<T> data){
        HashMap<U, MutableInt> numLabel = new HashMap<U, MutableInt>();
        U winningLabel = null;
        int maxScore = 1;
        for(T element: data){
            U tmpLabel = element.getLabel();
            MutableInt count = numLabel.get(tmpLabel);
            if(count == null){
                numLabel.put(tmpLabel, new MutableInt());
            } else {
                count.increment();
                int tmpScore = count.getValue();
                if(tmpScore > maxScore){
                    maxScore = tmpScore;
                    winningLabel = tmpLabel;
                }
            }
        }
        int numberOfElements = data.size();
        if(maxScore > numberOfElements * trustProbability)
            return new Answer<>(winningLabel);
        else
            return null;
    }
    
    /*private Answer<?,U> mostFrequent(List<T> data){
        HashMap<String, Integer> frequency = new HashMap<>();
        int bestFrequency = -1;
        String bestLabel = "";
        for(T element : data){
            String tmpLabel = element.getLabel();
            int tmpFreq = 1;
            if(frequency.containsKey(tmpLabel)){
                tmpFreq = frequency.get(tmpLabel)+1;
                frequency.replace(tmpLabel, tmpFreq);
            }
            else
                frequency.put(tmpLabel, 1);
            if(tmpFreq > bestFrequency){
                bestFrequency = tmpFreq;
                bestLabel = tmpLabel;
            }
        }
        int numberOfElements = data.size();
        if(bestFrequency > numberOfElements * trustProbability)
            return bestLabel;
        else
            return null;
    }
    */
}
