package machinelearning.kmeans;

import java.util.List;

public class ClustersScoring<T>{

    List<Cluster<T>> clusters = null;
    GenericTool<T> genericTool = null;

    public ClustersScoring(List<Cluster<T>> clusters, GenericTool<T> genericTool){
        this.clusters = clusters;
        this.genericTool = genericTool;
    }

    public double getSilhouetteScore(){
        for(Cluster<T> cluster : this.clusters){
            double a = 0.0;
            double b= 0.0;
            for(T point : cluster.getPoints()){
                for(T otherPoint : cluster.getPoints()){
                    if(!otherPoint.equals(point))
                        a += genericTool.distanceBetween(point, otherPoint);
                }

                //a += genericTool.distanceBetween(po);
            }
        }
        return 0.0;
    }

    public double getBasicScore(){
        double score = 0.0;
        int clustersSize = clusters.size();
        for(int i=0; i<clustersSize; i++){
            Cluster<T> cluster = clusters.get(i);
            for(int j=i+1; j<clustersSize; j++){
                score += this.genericTool.distanceBetween(cluster.getCentroid(),clusters.get(j).getCentroid());
            }
            double distanceInCluster = 0.0;
            for(T point : cluster.getPoints()){
                distanceInCluster += genericTool.distanceBetween(point,cluster.getCentroid());
            }
            score += 1.0/distanceInCluster;
        }
        return score;
    }
}
