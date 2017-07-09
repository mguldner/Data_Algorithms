package machinelearning.general;

import java.util.List;

import machinelearning.decisiontree.data.Tree;

/*
 * T : type de la valeur finale predite par l'algorithme -> Integer dans le cas du titanic (mort(0) ou vivant(1)).
 * U : type d'input de l'algorithme -> le type de la donnée d'entrée : un Passenger<Integer> pour le titanic
 */

public interface Algorithm<T, U extends DataObject<T>> {

    Tree<T> train(List<U> data, List<String> remainingFeatures);
    
    T test(Tree<T> tree, U testPoint);
}
