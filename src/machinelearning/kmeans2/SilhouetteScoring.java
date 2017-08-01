package machinelearning.kmeans2;

import java.util.List;

public class SilhouetteScoring<T>{

  private SilhouetteScoring(){}
  
  public double getScore(List<Cluster<T>> clusters, GenericTool<T> genericTool){
    for(Cluster<T> cluster : clusters){
      double a = 0.0;
      double b= 0.0;
      for(T point : cluster.getPoints()){
          
        a += genericTool.distanceBetween(po);
      }
    }
  }

}
