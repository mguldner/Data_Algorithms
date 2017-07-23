package machinelearning.kmeans;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DoubleRandomizerTest {

    @Test
    public void testGetNRandomObject() {
        List<Double> testData = new ArrayList<>();
        testData.add(43.5678);
        testData.add(43.5678);
        testData.add(67.9);
        testData.add(-23.9);
        testData.add(54.0);
        DoubleRandomizer doubleRandomizer = new DoubleRandomizer(testData);
        List<Double> randomizedData = doubleRandomizer.getNRandomObject(7);
        assertTrue(randomizedData.size() == 7);
        for(Double rData : randomizedData){
            assertTrue(rData >= -23.9 && rData <= 67.9);
        }
    }

}
