package machinelearning.decisiontree;

public class MutableInt {

    private int value = 1; // note that we start at 1 since we're counting
    
    public void increment(){
        value++;
    }
    
    public int getValue(){
        return value;
    }
}
