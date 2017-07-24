package machinelearning.kmeans;

import java.util.List;

public interface MeanTool<T> {

  T getMean(List<T> points);
  
  boolean isEquivalentTo(T point);

}
