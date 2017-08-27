package machinelearning.decisiontree.features;

public class Frame {

  private double min;
  private double max;

  public Frame(double min, double max){
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
  public boolean equals(Object frame) {
    if(frame == null || !(frame instanceof Frame))
      return false;
    else
      return this.min==((Frame)frame).getMin() && this.max == ((Frame)frame).getMax();
  }
  
  @Override
  public int hashCode() {
    return (int)(this.min + this.max);
  }
  
  @Override
    public String toString() {
        return this.min + " <= x <= " + this.max;
    }
}
