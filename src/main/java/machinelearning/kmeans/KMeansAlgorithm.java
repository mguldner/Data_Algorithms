package machinelearning.kmeans;

import machinelearning.general.exception.AlgorithmException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KMeansAlgorithm<T> {

    final static Logger logger = Logger.getLogger(KMeansAlgorithm.class);
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

    public List<Cluster<T>> getClusters() {
        return clusters;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public List<T> getCentroids(){
        return this.clusters.stream().map(Cluster<T>::getCentroid).collect(Collectors.toList());
    }

    public void runMultipleTime(int n, String scoringMethod){
        List<Cluster<T>> bestClusters = new ArrayList<>();
        double score = 0.0;
        for(int i=0; i<n; i++){
            this.run();
            double clustersScore = this.getClustersScore(scoringMethod);
            if(clustersScore > score){
                bestClusters = this.getClusters();
                score = clustersScore;
            }
        }
        this.clusters = bestClusters;
    }

    private void run(){
        initWithRandom();
        compute();
    }

    public void init(){
        this.clusters = new ArrayList<>();
        int dataSize = this.data.size();
        int clustersInitSize = dataSize / this.k;
        for(int i=0; i<k; i++){
            int inf = i*clustersInitSize;
            int sup = (i+1)*clustersInitSize;
            if(i<k-1)
                this.clusters.add(new Cluster<T>(i, data.subList(inf, sup), this.genericTool));
            else
                this.clusters.add(new Cluster<T>(i, data.subList(inf, dataSize), this.genericTool));
        }
    }

    private void initWithRandom(){
        this.clusters = new ArrayList<>();
        for(int i=0; i<k; i++){
            this.clusters.add(new Cluster<T>(i, getRandomData(), this.genericTool));
        }
    }

    private void compute(){
        boolean hasChanged = true;
        int j=0;
        while(hasChanged && j < 20){
            j++;
            hasChanged = false;
            List<T> previousCentroids = this.getCentroids();
            if(logger.isDebugEnabled()){
                logger.debug("Previous centroids : " + previousCentroids.stream().map(T::toString).collect(Collectors.joining(", ")));
            }
            this.clusters.forEach(Cluster<T>::clearPoints);
            for(int i=0; i<this.data.size(); i++){
                setToClosestCluster(this.data.get(i));
            }
            for(Cluster<T> cluster : this.clusters){
                if(!cluster.assignCentroid()){
                    cluster.setCentroid(getRandomData());
                    if(logger.isDebugEnabled()){
                        logger.debug("randomization");
                    }
                }
            }
            for(int i=0; i<previousCentroids.size() && !hasChanged; i++){
                if(!this.genericTool.areTheSame(previousCentroids.get(i), this.getCentroids().get(i)))
                    hasChanged = true;
            }
        }
    }

    private void setToClosestCluster(T dataPoint){
        double minDistance = Double.MAX_VALUE;
        int closest = -1;
        for(int i=0; i<this.clusters.size(); i++){
            double distance = this.genericTool.distanceBetween(this.clusters.get(i).getCentroid(), dataPoint);
            if(distance < minDistance){
                minDistance = distance;
                closest = i;
            }
        }
        this.clusters.get(closest).addPoint(dataPoint);
    }

    private T getRandomData(){
        return this.data.get((int)(Math.random() * (this.data.size()-1)));
    }

    public double getClustersScore(String scoring){
        ClustersScoring<T> scoringTool = new ClustersScoring<>(this.clusters, this.genericTool);
        if("basic".equals(scoring)){
            return scoringTool.getBasicScore();
        }
        if("basicVariance".equals(scoring)){
            return scoringTool.getBasicVarianceScore();
        }
        if("silhouette".equals(scoring)){
            try {
                return scoringTool.getSilhouetteScore();
            } catch (AlgorithmException e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return 0.0;
    }
}
