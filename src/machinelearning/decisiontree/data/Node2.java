package machinelearning.decisiontree.data;

import java.util.Map;

public class Node2<T> implements Tree2<T> {

    private Map<Object,Tree2<T>> children;
    private String feature;

    public Node2(String feature, Map<Object,Tree2<T>> children) {
        this.feature = feature;
        this.children = children;
    }

    public Map<Object,Tree2<T>> getChildren(){
        return this.children;
    }

    public Tree2<T> getChild(Object featureValue){
        return this.children.get(featureValue);
    }

    public void setChildren(Map<Object,Tree2<T>> children){
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
        for(Map.Entry<Object, Tree2<T>> entry : this.children.entrySet()){
            str += entry.getValue().toPrintableTree() + ", ";
        }
        return str.substring(0, str.length()-2) + ">";
    }
}
