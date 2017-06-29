package machinelearning.general;

import java.util.List;

import machinelearning.general.exception.MissingValueException;

public interface LossFunction<T> {

  double getAccuracy(List<T> prediction, List<T> reality) throws MissingValueException;
}
