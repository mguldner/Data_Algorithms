package machinelearning.general.lossfunction;

public class LogisticLossFunction extends BinaryLossFunction {

    @Override
    public double getValue(double value){
        return Math.log10(1 + Math.exp(-value));
    }
}
