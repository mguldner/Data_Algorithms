public class DoubleComparator implements GenericComparator<Double> {

  public double distanceBetween(Double o1, Double o2){
    return (double)o2-o1;
  }

}
