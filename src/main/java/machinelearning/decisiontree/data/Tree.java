package machinelearning.decisiontree.data;

public abstract class Tree<T> {
    
    protected T value;
    
    public Tree(T value){
        this.value = value;
    }
    
    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
    
    public abstract String toPrintableTree();
}
