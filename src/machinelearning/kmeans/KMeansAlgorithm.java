package machinelearning.kmeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO : The clusters are randomized but do not change -> have to !
public class KMeansAlgorithm<T> {

    private GenericComparator<T> comparator = null;
    private MeanTool<T> meanTool = null;

    public KMeansAlgorithm(GenericComparator<T> comparator, MeanTool<T> meanTool){
        this.comparator = comparator;
        this.meanTool = meanTool;
    }

    public Map<Integer, ArrayList<T>> apply(List<T> data, int k){
        Map<Integer, T> centroids = new HashMap<Integer, T>();
        Map<Integer, ArrayList<T>> associatedPoints = new HashMap<Integer, ArrayList<T>>();
        for(int i=0; i<k; i++){
            centroids.put(i, null); //TODO Randomize
        }
        boolean hasChanged = true;
        while(hasChanged){
            hasChanged = false;
            for(int i=0; i<k; i++){
                associatedPoints.put(i, new ArrayList<T>());
            }
            for(int i=0; i<data.size(); i++){
                int closestCluster = getClosestCluster(centroids, data.get(i));
                associatedPoints.get(closestCluster).add(data.get(i));
            }
            for(int i=0; i<k; i++){
                T mean = getMean(associatedPoints.get(i));
                if(!mean.equals(centroids.get(i))){
                    hasChanged = true;
                    centroids.put(i, mean);
                }
            }
        }
        return associatedPoints;
    }

    public int getClosestCluster(Map<Integer, T> centroids, T dataPoint){
        double minDistance = Double.MAX_VALUE;
        int closest = -1;
        for(int i=0; i<centroids.size(); i++){
            double distance = getDistance(centroids.get(i), dataPoint);
            if(distance < minDistance){
                minDistance = distance;
                closest = i;
            }
        }
        return closest;
    }

    public double getDistance(T v1, T v2){
        return this.comparator.distanceBetween(v1, v2);
    }

    public T getMean(List<T> points){
        return this.meanTool.getMean(points);
    }
}
