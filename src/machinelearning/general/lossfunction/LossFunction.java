package machinelearning.general.lossfunction;

import java.util.List;

import machinelearning.general.exception.MissingValueException;

public interface LossFunction<T> {

  double getDiscrepancy(List<T> prediction, List<T> reality) throws MissingValueException;
}
