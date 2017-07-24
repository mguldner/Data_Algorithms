package machinelearning.kmeans;

import java.util.List;

public class DoubleMeanTool implements MeanTool<Double>{

    @Override
    public Double getMean(List<Double> points) {
        double sum = 0;
        for(Double d : points){
            sum += d;
        }
        return sum/points.size();
    }

    @Override
    public boolean isEquivalentTo(Double point) {
        // TODO Auto-generated method stub
        return false;
    }

}
