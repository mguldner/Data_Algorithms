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
            
        }
        return 0.0;
    }
    
    private double computeSilhouetteForPoint(T point, Cluster<T> associatedCluster, Cluster<T> nearestCluster){
        double a = computeSilhouettePartClusterForPoint(point, associatedCluster, true);
        double b = computeSilhouettePartClusterForPoint(point, nearestCluster, false);
        double silhouette = (b - a)/(Math.max(a,b));
    }
    
    private double computeSilhouettePartClusterForPoint(T point, Cluster<T> cluster,boolean sameCluster){
        double value = 0.0;
        for(T otherPoint : cluster.getPoints()){
            if(!sameCluster || !otherPoint.equals(point))
                value += genericTool.distanceBetween(point, otherPoint);
        }
        return value /= ((double)cluster.getSize());
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
