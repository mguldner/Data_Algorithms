package machinelearning.decisiontree.features;


public class Frame {

    // Frame : min <= 
    private double min;
    // frame : < max
    private double max;
    
    public Frame(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
    
    @Override
    public String toString(){
        return min + " <= x < " + max;
    }
}
