package machinelearning.kmeans;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class KMeansAlgorithmDoubleTest {

    public void testApply() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetClosestCluster() {
        KMeansAlgorithm<Double> doubleKMAlgorithm = new KMeansAlgorithm<>(new DoubleComparator(), new DoubleMeanTool(), new DoubleRandomizer());
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
