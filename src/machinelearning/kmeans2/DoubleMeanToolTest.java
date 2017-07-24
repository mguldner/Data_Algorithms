package machinelearning.kmeans2;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DoubleMeanToolTest {

    @Test
    public void testGetMean() {
        DoubleMeanTool doubleMeanTool = new DoubleMeanTool();
        List<Double> testList = new ArrayList<>();
        testList.add(-23.8);
        testList.add(-5.9);
        testList.add(0.8);
        testList.add(2.0);
        testList.add(2.7);
        testList.add(17.3);
        assertTrue(doubleMeanTool.getMean(testList) - (-1.15) <= 0.000001);
    }

}
