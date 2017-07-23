package machinelearning.kmeans;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

public class KMeansAlgorithmDoubleTest {

    @Test
    public void testApply() {
        List<Double> dataTest = new ArrayList<Double>();
        dataTest.add(0.15);
        dataTest.add(1.23);
        dataTest.add(2.4);
        dataTest.add(-1.5);
        dataTest.add(-43.9);
        dataTest.add(-27.5);
        dataTest.add(-30.5);
        dataTest.add(-40.5);
        dataTest.add(45.9);
        dataTest.add(33.3);
        dataTest.add(44.6);
        dataTest.add(25.3);
        KMeansAlgorithm<Double> doubleKMAlgorithm = new KMeansAlgorithm<>(new DoubleComparator(), new DoubleMeanTool(), new DoubleRandomizer(dataTest));
        Map<Integer, ArrayList<Double>> results = doubleKMAlgorithm.apply(dataTest, 3);
        /*assertTrue(results.size() == 3);
        assertTrue(results.get(0).size() == 4);
        assertTrue(results.get(1).size() == 4);
        assertTrue(results.get(2).size() == 4);*/
        for (Map.Entry<Integer, ArrayList<Double>> entry : results.entrySet()) {
            System.out.println(entry.getKey() + "\n");
            String commaSeparatedValues = entry.getValue().stream()
                    .map(i -> i.toString())
                    .collect(Collectors.joining(", "));
            System.out.println(commaSeparatedValues + "\n\n");
        }
    }

    @Test
    public void testGetClosestCluster() {
        KMeansAlgorithm<Double> doubleKMAlgorithm = new KMeansAlgorithm<>(new DoubleComparator(), new DoubleMeanTool(), new DoubleRandomizer(new ArrayList<Double>()));
        Map<Integer, Double> centroids = new HashMap<>();
        centroids.put(0, -27.8);
        centroids.put(1, -1.8);
        centroids.put(2, 0.0);
        centroids.put(3, 32.6);
        assertTrue(doubleKMAlgorithm.getClosestCluster(centroids, 0.0) == 2);
        assertTrue(doubleKMAlgorithm.getClosestCluster(centroids, -0.0) == 2);
        assertTrue(doubleKMAlgorithm.getClosestCluster(centroids, -65.1) == 0);
        assertTrue(doubleKMAlgorithm.getClosestCluster(centroids, 38.2) == 3);
        assertTrue(doubleKMAlgorithm.getClosestCluster(centroids, -1.6) == 1);
    }
}
