package machinelearning.general;

import java.util.List;

public interface LossFunction<T> {

  double getAccuracy(List<T> prediction, List<T> reality) throws MissingValueException;
}
