package machinelearning.general.lossfunction;

import java.util.List;

import machinelearning.general.exception.MissingValueException;

public static class HingeLossFunction implements LossFunction<T> {

    @Override
    public double getDiscrepancy(List<T> prediction, List<T> reality) throws MissingValueException {
        
        return 0;
    }
}
