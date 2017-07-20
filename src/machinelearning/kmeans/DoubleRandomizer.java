package machinelearning.kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DoubleRandomizer implements Randomizer<Double> {

    @Override
    public List<Double> getNRandomObject(int n, List<Double> data) {
        List<Double> randomDoubles = new ArrayList<>();
        double rangeMin = data.stream().reduce((i,j) -> i > j ? j : i).get();
        double rangeMax = data.stream().reduce((i,j) -> i > j ? i : j).get();
        for(int i=0; i<n; i++){
            Random r = new Random();
            randomDoubles.add(rangeMin + (rangeMax - rangeMin) * r.nextDouble());
        }
        return randomDoubles;
    }

}
