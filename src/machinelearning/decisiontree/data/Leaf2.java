package machinelearning.decisiontree.data;

public class Leaf2<T> implements Tree2<T>{

    private T value;

    public Leaf2(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toPrintableTree() {
        return ""+this.value;
    }
}
