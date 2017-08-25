package machinelearning.kmeans2;

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
            for(int j=0; j<clustersSize-1; j++){
                int indexSecondCentroid = (i+j+1)%(clustersSize);
                score += this.genericTool.distanceBetween(clusters.get(i).getCentroid(),clusters.get(indexSecondCentroid).getCentroid());
            }
        }
        for(Cluster<T> cluster : clusters){
            double distanceInCluster = 0.0;
            for(T point : cluster.getPoints()){
                distanceInCluster += genericTool.distanceBetween(point,cluster.getCentroid());
            }
            score += 1.0/distanceInCluster;
        }
        return score;
    }
}
