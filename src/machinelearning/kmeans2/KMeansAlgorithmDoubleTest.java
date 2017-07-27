package machinelearning.kmeans2;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
        KMeansAlgorithm<Double> doubleKMAlgorithm = new KMeansAlgorithm<Double>(dataTest, 3, new DoubleTool());
        doubleKMAlgorithm.run();
        List<Cluster<Double>> clusters = doubleKMAlgorithm.getClusters();
        assertTrue(clusters.size() == 3);
        assertTrue(clusters.get(0).getSize() == 4);
        assertTrue(clusters.get(1).getSize() == 4);
        assertTrue(clusters.get(2).getSize() == 4);
        /*for (Cluster<Double> cluster : clusters) {
            System.out.println(cluster.getCentroid() + "\n");
            String pointsOfCluster = cluster.getPoints().stream()
                    .map(i -> i.toString())
                    .collect(Collectors.joining(", "));
            System.out.println(pointsOfCluster + "\n\n");
        }*/
    }
}
