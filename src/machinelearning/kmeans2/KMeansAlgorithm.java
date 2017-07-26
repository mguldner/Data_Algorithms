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
    
    public void run(){
        init();
        compute();
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
        boolean hasChanged = true;
        while(hasChanged){
            hasChanged = false;
            List<T> previousCentroids = this.getCentroids();
            this.clusters.stream().forEach(Cluster<T>::clearPoints);
            for(int i=0; i<this.data.size(); i++){
                setToClosestCluster(this.data.get(i));
            }
            this.clusters.stream().forEach(Cluster<T>::assignCentroid);
            for(T centroid : previousCentroids){
                if(!this.genericTool.areTheSame(previousCentroids.get(i), this.getCentroids().get(i)){
                    hasChanged = true;
            }
        }
    }

    public List<T> getCentroids(){
        return this.clusters.stream().map(Cluster<T>::getCentroid).collect(Collectors.toList());
    }
    
    public void setToClosestCluster(T dataPoint){
        double minDistance = Double.MAX_VALUE;
        int closest = -1;
        for(int i=0; i<this.clusters.size(); i++){
            double distance = this.genericToool.distanceBetween(this.clusters.get(i).getCentroid(), dataPoint);
            if(distance < minDistance){
                minDistance = distance;
                closest = i;
            }
        }
        this.clusters.get(i).addPoint(dataPoint);
    }

}

