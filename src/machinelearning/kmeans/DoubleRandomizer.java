package machinelearning.kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DoubleRandomizer implements Randomizer<Double> {

    private double rangeMin;
    private double rangeMax;
    private Random r;

    public DoubleRandomizer(List<Double> data) {
        rangeMin = data.stream().reduce((i,j) -> i > j ? j : i).get();
        rangeMax = data.stream().reduce((i,j) -> i > j ? i : j).get();
        r = new Random();
    }
    
    @Override
    public List<Double> getNSemiRandomObject(int n) {
        List<Double> randomDoubles = new ArrayList<>();
        for(int i=0; i<n; i++){
            randomDoubles.add(getRandomObject());
        }
        return randomDoubles;
    }

    @Override
    public List<Double> getNRandomObject(int n) {
        List<Double> randomDoubles = new ArrayList<>();
        for(int i=0; i<n; i++){
            randomDoubles.add(getRandomObject());
        }
        return randomDoubles;
    }

    @Override
    public Double getRandomObject() {
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }

}
