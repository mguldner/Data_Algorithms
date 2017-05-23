package machinelearning.decisiontree.data;

import java.util.ArrayList;
import java.util.List;

/*
 * La valeur associee a un Node permet de dire quel est la reponse la plus probable 
 * pour ce Node, même si une plus precise peut se trouver en continuant dans ses enfants.
 */

public class Node<T> extends Tree<T> {

    private List<Tree<T>> children;
    
    public Node(T value, Tree<T> parent, List<Tree<T>> children) {
        super(value, parent);
        this.children = children;
    }
    
    public Node(T value, Tree<T> parent) {
        super(value, parent);
        this.children = new ArrayList<>();
    }
    
    public Node(T value, List<Tree<T>> children) {
        super(value);
        this.children = children;
    }
    
    public Node(T value) {
        super(value);
    }
    
    public List<Tree<T>> getChildren(){
        return this.children;
    }
    
    public Tree<T> getChild(int index){
        return this.children.get(index);
    }

    public void addChild(Tree<T> child){
        this.children.add(child);
    }
    
    public void setChildren(List<Tree<T>> children){
        this.children = children;
    }
}
