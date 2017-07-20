package machinelearning.kmeans;

import static org.junit.Assert.*;

import org.junit.Test;

public class DoubleComparatorTest {

    @Test
    public void testDistanceBetween() {
        DoubleComparator doubleComparator = new DoubleComparator();
        assertTrue(doubleComparator.distanceBetween(-6.1, -9.3) - 3.2 <= 0.000001);
        assertTrue(doubleComparator.distanceBetween(-6.1, -4.3) - 1.8 <= 0.000001);
        assertTrue(doubleComparator.distanceBetween(2.5, 40.27) - 37.77 <= 0.000001);
        assertTrue(doubleComparator.distanceBetween(-56.9, 37.89) - 94.79 <= 0.000001);
        assertTrue(doubleComparator.distanceBetween(-56.9, 89.89) - 146.79 <= 0.000001);
    }

}
