package machinelearning.general.lossfunction;

public class HingeLossFunction extends BinaryLossFunction {

    @Override
    public double getValue(double value){
        return Math.max(0, 1-value);
    }

}
