public SilhouetteScoring<T>{

  private SilhouetteScoring(){}
  
  public double getSilhouetteScore(List<Cluster<T>> clusters, GenericTool<T> genericTool){
    for(Cluster<T> cluster : clusters){
      double a = 0.0;
      double b= 0.0;
      for(T point : cluster.getPoints()){
        a += genericTool.distanceBetween(po);
      }
    }
  }

}
