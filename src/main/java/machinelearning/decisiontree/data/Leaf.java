package machinelearning.decisiontree.data;

public class Leaf<T> extends Tree<T>{

    public Leaf(T value) {
        super(value);
    }

    @Override
    public String toPrintableTree() {
        return ""+this.value;
    }
}
