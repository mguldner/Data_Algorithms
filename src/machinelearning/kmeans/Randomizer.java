package machinelearning.kmeans;

import java.util.List;

public interface Randomizer<T> {

    List<T> getNSemiRandomObject(int n);
    
    List<T> getNRandomObject(int n);
    
    T getRandomObject();
}
