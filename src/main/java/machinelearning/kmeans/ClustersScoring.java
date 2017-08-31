package machinelearning.kmeans;

import machinelearning.general.Algorithm;
import machinelearning.general.exception.AlgorithmException;
import org.apache.log4j.Logger;

import java.util.List;

public class ClustersScoring<T>{

    private final static Logger logger = Logger.getLogger(ClustersScoring.class);
    List<Cluster<T>> clusters = null;
    GenericTool<T> genericTool = null;

    public ClustersScoring(List<Cluster<T>> clusters, GenericTool<T> genericTool){
        this.clusters = clusters;
        this.genericTool = genericTool;
    }

    public double getSilhouetteScore() throws AlgorithmException{
        double score = 0.0;
        double numberOfPoints = 0.0;
        for(Cluster<T> cluster : this.clusters){
            for(T point : cluster.getPoints()){
                try {
                    score += computeSilhouetteForPoint(point, cluster, getNearestCluster(cluster));
                    numberOfPoints++;
                } catch (AlgorithmException e){
                    if(logger.isDebugEnabled()){
                        logger.debug(e.getMessage());
                    }
                }
            }
        }
        return score / numberOfPoints;
    }
    
    private Cluster<T> getNearestCluster(Cluster<T> cluster) throws AlgorithmException{
        if(this.clusters.size()==1){
            throw new AlgorithmException("Try to get silhouette but only one cluster");
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
    
    private double computeSilhouetteForPoint(T point, Cluster<T> associatedCluster, Cluster<T> nearestCluster) throws AlgorithmException{
        double a = computeSilhouettePartClusterForPoint(point, associatedCluster, true);
        double b = computeSilhouettePartClusterForPoint(point, nearestCluster, false);
        return (b - a)/(Math.max(a,b));
    }
    
    private double computeSilhouettePartClusterForPoint(T point, Cluster<T> cluster, boolean sameCluster) throws AlgorithmException{
        double value = 0.0;
        if((sameCluster && cluster.getSize() == 1) || (!sameCluster && cluster.getSize() == 0))
            throw new AlgorithmException("Doing cluster with only one point");
        else {
            for (T otherPoint : cluster.getPoints()) {
                if (!sameCluster || !otherPoint.equals(point))
                    value += genericTool.distanceBetween(point, otherPoint);
            }
            return value /= ((double) cluster.getSize());
        }
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
