package machinelearning.kmeans2;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DoubleToolTest {

    @Test
    public void testGetMean() {
        DoubleTool doubleTool = new DoubleTool();
        List<Double> testList = new ArrayList<>();
        testList.add(-23.8);
        testList.add(-5.9);
        testList.add(0.8);
        testList.add(2.0);
        testList.add(2.7);
        testList.add(17.3);
        assertTrue(doubleTool.areTheSame(doubleTool.calculateMean(testList), -1.15));
    }
    
    @Test
    public void testDistanceBetween() {
        DoubleTool doubleTool = new DoubleTool();
        assertTrue(doubleTool.areTheSame(doubleTool.distanceBetween(-6.1, -9.3), 3.2));
        assertTrue(doubleTool.areTheSame(doubleTool.distanceBetween(-6.1, -4.3), 1.8));
        assertTrue(doubleTool.areTheSame(doubleTool.distanceBetween(2.5, 40.27), 37.77));
        assertTrue(doubleTool.areTheSame(doubleTool.distanceBetween(-56.9, 37.89), 94.79));
        assertTrue(doubleTool.areTheSame(doubleTool.distanceBetween(-56.9, 89.89), 146.79));
    }

}
