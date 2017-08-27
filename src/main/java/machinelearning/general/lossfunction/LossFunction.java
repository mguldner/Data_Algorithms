package machinelearning.general.lossfunction;

import java.util.List;

import machinelearning.general.exception.MissingValueException;

public abstract class LossFunction<T> {

    public double getDiscrepancy(List<T> prediction, List<T> reality) throws MissingValueException {
        double lossFunctionSum = 0;
        if(prediction.size() != reality.size())
            throw new MissingValueException("LossFunction [" + this.getClass() + "] : Prediction and reality do not have the same size");
        else{
            for(int i=0; i<prediction.size(); i++){
                lossFunctionSum += this.lossFunctionValue(prediction.get(i), reality.get(i));
            }
            lossFunctionSum /= (double)prediction.size();
        }
        return lossFunctionSum;
    }

    public abstract double lossFunctionValue(T predictionValue, T realityValue);
}
