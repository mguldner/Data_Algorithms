package machinelearning.kmeans2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// TODO : The clusters are randomized but do not change -> have to !
public class KMeansAlgorithm<T> {

    private List<Cluster<T>> clusters = null;
    private int k;
    private List<T> data = null;
    private GenericTool<T> genericTool = null;

    public KMeansAlgorithm(List<T> data, int k, GenericTool<T> genericTool){
        this.clusters = new ArrayList<>();
        this.k = k;
        this.data = data;
        this.genericTool = genericTool;
    }

    public void init(){
        int dataSize = this.data.size();
        int clustersInitSize = dataSize / 3;
        for(int i=0; i<k; i++){
            int inf = i*clustersInitSize;
            int sup = (i+1)*clustersInitSize;
            if(i<k-1)
                this.clusters.add(new Cluster<T>(i, data.subList(inf, sup), this.genericTool));
            else
                this.clusters.add(new Cluster<T>(i, data.subList(inf, dataSize), this.genericTool));
        }
    }

    public void compute(){
        List<T> previousCentroids = this.getCentroids();
        boolean hasChanged = true;
        while(hasChanged){
            hasChanged = false;
            this.clusters.stream().forEach(Cluster<T>::clearPoints);
        }
    }

    public void run(){
        init();
        compute();
    }

    public List<T> getCentroids(){
        return this.clusters.stream().map(Cluster<T>::getCentroid).collect(Collectors.toList());
    }

    public Map<Integer, ArrayList<T>> apply(List<T> data, int k){
        Map<Integer, T> centroids = new HashMap<Integer, T>();
        Map<Integer, ArrayList<T>> associatedPoints = new HashMap<Integer, ArrayList<T>>();
        List<T> randomizedData = randomizer.getNRandomObject(k);
        for(int i=0; i<k; i++){
            centroids.put(i, randomizedData.get(i));
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
                if(associatedPoints.get(i).isEmpty()){
                    centroids.put(i, randomizer.getRandomObject());
                }
                else{
                    hasChanged = meanTool.
                            T mean = this.meanTool.getMean(associatedPoints.get(i));
                    if(!mean.isEquivalentTo(centroids.get(i))){
                        hasChanged = true;
                        centroids.put(i, mean);
                    }
                }
            }
        }
        for(Map.Entry<Integer, T> entry : centroids.entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue() + "\n");
        }
        return associatedPoints;
    }

    public int getClosestCluster(Map<Integer, T> centroids, T dataPoint){
        double minDistance = Double.MAX_VALUE;
        int closest = -1;
        for(int i=0; i<centroids.size(); i++){
            double distance = this.comparator.distanceBetween(centroids.get(i), dataPoint);
            if(distance < minDistance){
                minDistance = distance;
                closest = i;
            }
        }
        return closest;
    }

}

