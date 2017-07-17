package machinelearning.general.lossfunction;

public abstract class BinaryLossFunction extends LossFunction<Boolean> {

    @Override
    public double lossFunctionValue(Boolean predictionValue, Boolean realityValue) {
        if(!predictionValue.equals(realityValue))
            return getValue(0);
        else
            return getValue(1);
    }
    
    public abstract double getValue(double value);
}
