package machinelearning.kmeans2;

import java.util.List;

public final class Scoring<T>{

  private Scoring(){}
  
  public static double getSilhouetteScore(List<Cluster<T>> clusters, GenericTool<T> genericTool){
    for(Cluster<T> cluster : clusters){
      double a = 0.0;
      double b= 0.0;
      for(T point : cluster.getPoints()){
        for(T otherPoint : cluster.getPoints()){
          if(!otherPoint.equals(point))
            a += genericTool.distanceBetween(point, otherPoint);
        }
          
        a += genericTool.distanceBetween(po);
      }
    }
  }
  
  public static double getBasicScore(List<Cluster<T>> clusters, GenericTool<T> genericTool){
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
      for(T point : cluster.getpoints()){
        distanceInCluster += genericTool.distanceBetween(point,cluster.getCentroid());
      }
      score += 1.0/distanceInCluster;
    }
    return score;
  }
}
