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

  @Override
  public boolean equals(Object frame) {
    if(frame == null || !(frame instanceof Frame))
      return false;
    else
      return this.min==((Frame)frame).getMin() && this.max == ((Frame)frame).getMax();
  }
}
