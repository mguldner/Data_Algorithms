package machinelearning.kmeans2;

import java.util.List;

public interface GenericTool<T> {

    T calculateMean(List<T> points);

    boolean areTheSame(T point1, T point2);
    
    double distanceBetween(T point1, T point2);

}
