package machinelearning.decisiontree;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import machinelearning.decisiontree.data.Tree;
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



    public Tree<Answer2<T>> train(List<U> data, List<String> remainingFeatures){
        T guess = mostFrequentAnswerJava8(data);
        return null;
    }

    public T mostFrequentAnswer(List<U> data){
        HashMap<T, MutableInt> numberOfAnswers = new HashMap<T, MutableInt>();
        T winningAnswer = data.get(0).getAnswerValue();
        int maxScore = 1;
        for(U element: data){
            T tmpAnswer = element.getAnswerValue();
            MutableInt count = numberOfAnswers.get(tmpAnswer);
            if(count == null){
                numberOfAnswers.put(tmpAnswer, new MutableInt());
            } else {
                count.increment();
                int tmpScore = count.getValue();
                if(tmpScore > maxScore){
                    maxScore = tmpScore;
                    winningAnswer = tmpAnswer;
                }
            }
        }
        int numberOfElements = data.size();
        if(maxScore > numberOfElements * trustProbability)
            return winningAnswer;
        else
            return null;
    }
    
    public T mostFrequentAnswerJava8(List<U> data){
        Map<T, Long> classification = data.stream()
            .map(U::getAnswerValue)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Entry<T, Long> bestAnswer = classification
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .get();
        if((double)bestAnswer.getValue() > (double)data.size() * trustProbability)
            return bestAnswer.getKey();
        else
            return null;
    }
}
