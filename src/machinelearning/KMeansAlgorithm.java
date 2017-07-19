package machinelearning;

import java.util.Comparator;

// TODO : The clusters are randomized but do not change -> have to !
public class KMeansAlgorithm<T> {

    private GenericComparator<T> comparator = null;
    private MeanTool<T> meanTool = null;
    
    public KMeansAlgorithm(GenericComparator<T> comparator, MeanTool<T> meanTool){
        this.comparator = comparator;
        this.meanTool = meanTool;
    }
    
    public int[] apply(T[] data, int k){
        T[] clusters = new T[k];
        int[] assignedCluster = new int[data.length];
        for(int i=0; i<k; i++){
            clusters[i] = null; //TODO Randomize
        }
        boolean hasChanged = true;
        while(hasChanged){
            List<T>[] associatedPoints = new ArrayList[k];
            for(int i=0; i<k; i++){
                associatedPoints[i] = new ArrayList<T>();
            }
            for(int i=0; i<data.length; i++){
                int closestCluster = getClosestCluster(clusters, data[i]);
                assignedCluster[i] = closestCluster;
                associatedPoints[closestCluster].add(data[i]);
            }
            for(int i=0; i<k; i++){
                clusters[k] = getMean(associatedpoints[k]);
            }
        }
    }
    
    public int getClosestCluster(T[] clusters, T dataPoint){
        double minDistance = Double.MAX_VALUE;
        int closest = -1;
        for(int i=0; i<clusters.length; i++){
            double distance = getDistance(clusters[i], dataPoint);
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
