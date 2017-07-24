package machinelearning.kmeans2;

import java.util.List;

public final class DoubleTool implements GenericTool<Double>{

    private DoubleTool() {}
    
    @Override
    public Double calculateMean(List<Double> points) {
        double sum = 0;
        for(Double d : points){
            sum += d;
        }
        return sum/points.size();
    }

    @Override
    public boolean areTheSame(Double point1, Double point2) {
        return point1 - point2 <= 0.000001;
    }

}
