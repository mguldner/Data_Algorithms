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
        double score = 0.0;
        double numberOfPoints = 0.0;
        for(Cluster<T> cluster : this.clusters){
            for(T point : cluster.getPoints()){
                score += computeSilhouetteForPoint(point, cluster, getNearestCluster(cluster));
                numberOfPoints++;
            }
        }
        return score / numberOfPoints;
    }
    
    private Cluster<T> getNearestCluster(Cluster<T> cluster) throws AlgorithmException{
        if(this.clusters.size()==1){
            throw new AlgorithmException("Try to get silhouette but only one cluster");
            return null;
        }
        Cluster<T> nearest = null;
        T centroid = cluster.getCentroid();
        double minDist = Double.MAX_VALUE;
        for(Cluster<T> tmpCluster : this.clusters){
            if(!cluster.equals(tmpCluster)){
                T tmpCentroid = tmpCluster.getCentroid();
                double tmpDist = this.genericTool.distanceBetween(centroid, tmpCentroid);
                if(tmpDist < minDist){
                    nearest = tmpCluster;
                    minDist = tmpDist;
                }
            }
        }
        return nearest;
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
