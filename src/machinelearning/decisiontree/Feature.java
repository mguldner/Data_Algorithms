package machinelearning.decisiontree;

import java.util.List;

/*
 * Name : le nom de la caracteristique concernee. 
 *      Exemple : 
 *          "Port de depart" -> T : String
 *          "Age" -> T : T : Encadrement
 * Value : les valeurs possibles pour la caracteristique concernee. 
 *      Exemple : 
 *          "C" ou "S" ou "Q"
 *          x<=18 ou 18<x<50 
 */

public class Feature<T> {
    
    private String name;
    private List<T> possibleValues;
    
    public Feature(String name, List<T> possibleValues){
        this.name = name;
        this.possibleValues = possibleValues;
    }

    public String getName() {
        return name;
    }

    public List<T> getPossibleValues() {
        return possibleValues;
    }
}
