package machinelearning.general;

import java.util.List;

import machinelearning.decisiontree.Feature;

/**
 * @param <U> : Le type de la valeur finale (la donnee que l'on cherche a prevoir). 
 * Dans le cas du titanic, un Integer 0 (mort) ou 1 (vivant).
 */
public interface DataObject<U> {

    public U getLabel();
    
    public int classificationForFeature(Feature<?> feature);
    
    public int[][] getOverview(List<Feature<?>> features);
}
