package machinelearning.kmeans2;

import java.util.List;

public final class DoubleTool implements GenericTool<Double>{

    public DoubleTool() {}
    
    @Override
    public Double calculateMean(List<Double> points) {
        if(points.isEmpty())
            return -1.0;
        else{
            double sum = 0;
            for(Double d : points){
                sum += d;
            }
            return sum/points.size();
        }
    }

    @Override
    public boolean areTheSame(Double point1, Double point2) {
        return distanceBetween(point1, point2) <= 0.000001;
    }

    @Override
    public double distanceBetween(Double point1, Double point2) {
        if(point1 * point2 <= 0){
            return Math.abs(point1) + Math.abs(point2);
        } else {
            return Math.abs(point1-point2);
        }
    }

}
