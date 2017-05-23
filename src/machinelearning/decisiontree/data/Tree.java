package machinelearning.decisiontree.data;

public abstract class Tree<T> {

    private Tree<T> parent;
    private T value;
    
    public Tree(T value, Tree<T> parent){
        this.value = value;
        this.parent = parent;
    }
    
    public Tree(T value){
        this.value = value;
    }
    
    public Tree<T> getParent(){
        return this.parent;
    }
    
    public void setParent(Tree<T> parent){
        this.parent = parent;
    }
    
    public T getValue(){
        return this.value;
    }
}
