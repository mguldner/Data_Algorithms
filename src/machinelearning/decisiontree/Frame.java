package machinelearning.decisiontree;

public class Frame {

  private int min;
  private int max;

  public Frame(int min, int max){
    this.min = min;
    this.max = max;
  }
  
  public int getMin() {
    return min;
  }

  public int getMax() {
    return max;
  }

  public boolean equals(Frame frame) {
    return this.min==frame.getMin() && this.max == frame.getMax();
  }
}
