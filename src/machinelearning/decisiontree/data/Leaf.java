package machinelearning.decisiontree.data;

public class Leaf<T> extends Tree<T> {

    public Leaf(T value, Tree<T> parent) {
        super(value, parent);
    }
    
    public Leaf(T value) {
        super(value);
    }
}
