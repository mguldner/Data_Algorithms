package machinelearning.decisiontree.data;

import java.util.Map;

public class Node<T> extends Tree<T> {

    private Map<Object,Tree<T>> children;
    private String feature;

    public Node(String feature, Map<Object,Tree<T>> children, T value) {
        super(value);
        this.feature = feature;
        this.children = children;
    }

    public Map<Object,Tree<T>> getChildren(){
        return this.children;
    }

    public Tree<T> getChild(Object featureValue){
        return this.children.get(featureValue);
    }

    public void setChildren(Map<Object,Tree<T>> children){
        this.children = children;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    @Override
    public String toPrintableTree() {
        String str = "<" + this.getFeature() + " : ";
        for(Map.Entry<Object, Tree<T>> entry : this.children.entrySet()){
            str += entry.getKey() + "=" + entry.getValue().toPrintableTree() + ", ";
        }
        return str.substring(0, str.length()-2) + ">";
    }
}
