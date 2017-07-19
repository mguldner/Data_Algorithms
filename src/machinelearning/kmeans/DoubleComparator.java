package machinelearning.kmeans;

public class DoubleComparator implements GenericComparator<Double> {

  public double distanceBetween(Double o1, Double o2){
    return Math.abs((double)o2-o1);
  }

}
