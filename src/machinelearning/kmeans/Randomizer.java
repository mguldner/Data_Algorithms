package machinelearning.kmeans;

import java.util.List;

public interface Randomizer<T> {

    List<T> getNRandomObject(int n);
    
    T getRandomObject();
}
