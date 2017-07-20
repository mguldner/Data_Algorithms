package machinelearning.kmeans;

public class DoubleComparator implements GenericComparator<Double> {

    public double distanceBetween(Double o1, Double o2){
        if(o1 <= 0){
            if(o2 <= 0)
                return Math.abs(o1-o2);
            else
                return Math.abs(o1+o2);
        }
        else
            return Math.abs(o1-o2);
    }

}
