package machinelearning.example.iris;

import machinelearning.kmeans.KMeansAlgorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mathieu on 9/2/17.
 */
public class IrisExample {
    private final static String folder = "/home/mathieu/Machine_Learning/Iris";
    private final static String file = folder + "/iris.data.csv";
    private final static double percent = 0.8;
    private List<Iris> trainingSet = new ArrayList<>();
    private List<Iris> testSet = new ArrayList<>();
    private IrisTool irisTool = new IrisTool();

    private void initData() {
        Random r = new Random();
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            boolean firstLine = true;
            while((line = br.readLine()) != null){
                if(firstLine){
                    firstLine = false;
                }
                else{
                    String[] values = line.split(",");
                    Iris iris = new Iris(
                            Double.parseDouble(values[0]),
                            Double.parseDouble(values[1]),
                            Double.parseDouble(values[2]),
                            Double.parseDouble(values[3]),
                            values[4]);
                    if(r.nextDouble() < percent) {
                        iris.setAnswerValue(values[4]);
                        trainingSet.add(iris);
                    } else {
                        testSet.add(iris);
                    }
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private List<Iris> determineBestK(int min, int max, String scoringMethod) {
        double bestScore = 0.0;
        int bestK = -1;
        List<Iris> bestCentroids = new ArrayList<>();
        for(int k=min; k<=max; k++){
            KMeansAlgorithm<Iris> doubleKMeansAlgorithm = new KMeansAlgorithm<>(trainingSet, k, irisTool);
            doubleKMeansAlgorithm.runMultipleTime(10, scoringMethod);
            double score = doubleKMeansAlgorithm.getClustersScore(scoringMethod);
            if(score > bestScore){
                bestK = k;
                bestScore = score;
                bestCentroids = doubleKMeansAlgorithm.getCentroids();
            }
        }
        System.out.println("Meilleur k : " + bestK);
        return bestCentroids;
    }

    private String getBestSpecies(List<Iris> centroids, Iris iris){
        double distance = Double.MAX_VALUE;
        String species = "";
        for(Iris centroid : centroids){
            double tmpDistance = irisTool.distanceBetween(iris, centroid);
            if(tmpDistance < distance){
                distance = tmpDistance;
                species = (String)centroid.getValueForFeature("species");
            }
        }
        return species;
    }

    private double runPredicitonAndEvaluate(List<Iris> centroids){
        double score = 0;
        for(Iris iris : testSet){
            iris.setAnswerValue(getBestSpecies(centroids, iris));
            if(iris.getAnswerValue().equals(iris.getValueForFeature("species"))){
                score++;
            }
        }
        return score / (double)(testSet.size());
    }

    public static void main(String[] args) {
        IrisExample irisExample = new IrisExample();
        irisExample.initData();
        String scoringMethod = "silhouette";
        List<Iris> centroids =  irisExample.determineBestK(3, 3, scoringMethod);
        for(Iris iris : centroids)
            System.out.println(iris.toString());
        double evaluation = irisExample.runPredicitonAndEvaluate(centroids);
        System.out.println("[" + scoringMethod +"] Pourcentage de bonnes réponses : " + evaluation);
    }
}
