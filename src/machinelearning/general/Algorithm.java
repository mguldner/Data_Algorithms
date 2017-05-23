package machinelearning.general;

import java.util.List;

import machinelearning.decisiontree.Answer;
import machinelearning.decisiontree.Feature;
import machinelearning.decisiontree.data.Tree;

/*
 * V : type de retour de l'aglorithme -> Un arbre dans le cas d'un algorithme d'arbre de decision
 * T : type d'input de l'algorithme -> le type de la donnée d'entrée : un Passenger<Integer> pour le titanic
 * U : type de la valeur final predite par l'algorithme -> Integer dans le cas du titanic (mort(0) ou vivant(1)).
 */

public interface Algorithm<U, T extends DataObject<U>, V> {

    V train(List<T> data, List<Feature<?>> features);
    
    public U test(Tree<Answer<?,U>> tree, T testPoint);
}
