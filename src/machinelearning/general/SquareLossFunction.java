package machinelearning.general;

import java.util.List;

public class SquareLossFunction<T> implements LossFunction<T> {

  @Override
  public double getAccuracy(List<T> prediction, List<T> reality) throws MissingValueException {
    if(prediction.size() != reality.size())
      throw new MissingValueException("Predition and reality have not the same size.");
    else{
      double sum = 0;
      for(int i=0; i<prediction.size(); i++){
        if(prediction.get(i) == reality.get(i))
          sum+=1;
      }
      return sum/(double)prediction.size();
    }
  }

}
