package machinelearning.kmeans2;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ClustersScoringTest {

    @Test
    public void testBasicScore() {
        DoubleTool dTool = new DoubleTool();
        List<Double> pointsC1 = new ArrayList<>();
        double d11=-15.0;
        double d12=-14.0;
        double d13=-16.0;
        double d14=-15.5;
        pointsC1.add(d11);
        pointsC1.add(d12);
        pointsC1.add(d13);
        pointsC1.add(d14);
        double centroid1 = (d11+d12+d13+d14)/4.0;
        double distanceInC1 = 0.0;
        distanceInC1 += dTool.distanceBetween(d11, d12);
        distanceInC1 += dTool.distanceBetween(d11, d13);
        distanceInC1 += dTool.distanceBetween(d11, d14);
        distanceInC1 += dTool.distanceBetween(d12, d13);
        distanceInC1 += dTool.distanceBetween(d12, d14);
        distanceInC1 += dTool.distanceBetween(d13, d14);
        List<Double> pointsC2 = new ArrayList<>();
        double d21 = 0.0;
        double d22 = -1.0;
        double d23 = 1.0;
        double d24 = 0.5;
        pointsC2.add(d21);
        pointsC2.add(d22);
        pointsC2.add(d23);
        pointsC2.add(d24);
        double centroid2 = (d21+d22+d23+d24)/4.0;
        double distanceInC2 = 0.0;
        distanceInC2 += dTool.distanceBetween(d21, d22);
        distanceInC2 += dTool.distanceBetween(d21, d23);
        distanceInC2 += dTool.distanceBetween(d21, d24);
        distanceInC2 += dTool.distanceBetween(d22, d23);
        distanceInC2 += dTool.distanceBetween(d22, d24);
        distanceInC2 += dTool.distanceBetween(d23, d24);
        List<Double> pointsC3 = new ArrayList<>();
        double d31 = 24.0;
        double d32 = 22.45;
        double d33 = 27.0;
        double d34 = 24.2;
        pointsC3.add(d31);
        pointsC3.add(d32);
        pointsC3.add(d33);
        pointsC3.add(d34);
        double centroid3 = (d31+d32+d33+d34)/4.0;
        double distanceInC3 = 0.0;
        distanceInC3 += dTool.distanceBetween(d31, d32);
        distanceInC3 += dTool.distanceBetween(d31, d33);
        distanceInC3 += dTool.distanceBetween(d31, d34);
        distanceInC3 += dTool.distanceBetween(d32, d33);
        distanceInC3 += dTool.distanceBetween(d32, d34);
        distanceInC3 += dTool.distanceBetween(d33, d34);
        
        List<Cluster<Double>> clusters = new ArrayList<>();
        Cluster<Double> c1 = new Cluster<Double>(1, pointsC1, dTool);
        Cluster<Double> c2 = new Cluster<Double>(1, pointsC2, dTool);
        Cluster<Double> c3 = new Cluster<Double>(1, pointsC3, dTool);
        clusters.add(c1);
        clusters.add(c2);
        clusters.add(c3);
        
        double score = 0.0;
        score += dTool.distanceBetween(centroid1, centroid2);
        score += dTool.distanceBetween(centroid1, centroid3);
        score += dTool.distanceBetween(centroid2, centroid3);
        score += 1.0/distanceInC1;
        score += 1.0/distanceInC2;
        score += 1.0/distanceInC3;
        
        ClustersScoring<Double> cScoring = new ClustersScoring<Double>(clusters, dTool);
        double calculatedScore = cScoring.getBasicScore();
        assertTrue(dTool.areTheSame(calculatedScore, score));
    }
    
   // @Test
    public void testSilhouetteScore() {
        
    }
}
