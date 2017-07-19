package machinelearning.kmeans;
public interface GenericComparator<T> {

  double distanceBetween(T o1, T o2);

}
