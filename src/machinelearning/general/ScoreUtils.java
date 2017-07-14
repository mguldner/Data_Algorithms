package machinelearning.general;

import java.util.ArrayList;
import java.util.List;

import machinelearning.general.exception.MissingValueException;

public class ScoreUtils<T> {

    private List<T> prediction = new ArrayList<>();
    private List<T> reality = new ArrayList<>();
    private T positiveValue = null;
    private T negativeValue = null;
    private double positiveNumber = 0;
    private double negativeNumber = 0;
    private double truePositiveNumber = 0;
    private double trueNegativeNumber = 0;
    private double falsePositiveNumber = 0;
    private double falseNegativeNumber = 0;
    

    public ScoreUtils(List<T> prediction, List<T> reality, T positiveValue, T negativeValue) throws MissingValueException {
        if(prediction.size() != reality.size())
            throw new MissingValueException("Predition and reality have not the same size.");
        else{
            this.prediction = prediction;
            this.reality = reality;
            this.positiveValue = positiveValue;
            this.negativeValue = negativeValue;
            for(int i=0; i<prediction.size(); i++){
                if(reality.get(i).equals(positiveValue)){
                    positiveNumber++;
                    if(prediction.get(i).equals(positiveValue))
                        truePositiveNumber++;
                    else
                        falseNegativeNumber++;
                }
                else{
                    negativeNumber++;
                    if(prediction.get(i).equals(negativeValue))
                        trueNegativeNumber++;
                    else
                        falsePositiveNumber++;
                }
            }
        }
    }

    public double getAccuracy() {
        return (truePositiveNumber + trueNegativeNumber)/(positiveNumber+negativeNumber);
    }

}
