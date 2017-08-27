package machinelearning.general;

import java.util.List;

import machinelearning.general.exception.MissingValueException;

public class ScoreUtils<T> {

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

    // How many positive were detected
    public double getSensitivity(){
        return truePositiveNumber / (truePositiveNumber + falseNegativeNumber);
    }

    // How many negative were detected
    public double getSpecificity(){
        return trueNegativeNumber / (falsePositiveNumber + trueNegativeNumber);
    }

    // How many positive are really positive
    public double getPrecision(){
        return truePositiveNumber / (truePositiveNumber + falsePositiveNumber);
    }

    // How many negative are really negative
    public double getNegativePredictiveValue(){
        return trueNegativeNumber / (trueNegativeNumber + falseNegativeNumber);
    }

    // Probability that a non-relevant document is retrieved by the query
    public double getFallOut(){
        return falsePositiveNumber / (falsePositiveNumber + trueNegativeNumber);
    }

    public double getFalseDiscoveryRate(){
        return falsePositiveNumber / (falsePositiveNumber + truePositiveNumber);
    }

    public double getFalseNegativeRate(){
        return falseNegativeNumber / (falseNegativeNumber + truePositiveNumber);
    }

    public double getAccuracy() {
        return (truePositiveNumber + trueNegativeNumber) / (positiveNumber + negativeNumber);
    }
    
    public double getBalancedAccuracy(){
        return ((truePositiveNumber / positiveNumber) + (trueNegativeNumber / negativeNumber)) / 2;
    }

    public double getF1Score(){
        return (2 * truePositiveNumber) / (2 * truePositiveNumber + falsePositiveNumber + falseNegativeNumber);
    }

    public double getMatthewsCorrelationCoefficient(){
        return (truePositiveNumber * trueNegativeNumber - falsePositiveNumber * falseNegativeNumber) / 
                Math.sqrt((truePositiveNumber + falsePositiveNumber) * (truePositiveNumber + falseNegativeNumber) * 
                        (trueNegativeNumber + falsePositiveNumber) * (trueNegativeNumber + falseNegativeNumber));
    }
    
    public String toString(){
        String s = "";
        s += "Sensitivity = " + this.getSensitivity() + "\n";
        s += "Specificity = " + this.getSpecificity() + "\n";
        s += "Precision = " + this.getPrecision() + "\n";
        s += "NegativePredictiveValue = " + this.getNegativePredictiveValue() + "\n";
        s += "FallOut = " + this.getFallOut() + "\n";
        s += "FalseDiscoveryRate = " + this.getFalseDiscoveryRate() + "\n";
        s += "FalseNegativeRate = " + this.getFalseNegativeRate() + "\n";
        s += "Accuracy = " + this.getAccuracy() + "\n";
        s += "BalancedAccuracy = " + this.getBalancedAccuracy() + "\n";
        s += "F1Score = " + this.getF1Score() + "\n";
        s += "MatthewsCorrelationCoefficient = " + this.getMatthewsCorrelationCoefficient() + "\n";
        return s;
    }
}
