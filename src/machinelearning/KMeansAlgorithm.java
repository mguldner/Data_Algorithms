package machinelearning;

import java.util.Comparator;

public class KMeansAlgorithm<T> {

    
    
    public int[] apply(T[] data, int k){
        T[] u = new T[k];
        int[] z = new int[data.length];
        for(int i=0; i<k; i++){
            u[i] = null; //TODO Randomize
        }
        boolean hasChanged = true;
        while(hasChanged){
            for(int i=0; i<data.length; i++){
                z[i] = getClosestCluster(u, data[i]);
            }
        }
    }
    
    public int getClosestCluster(T[] u, T x){
        double min = Double.MAX_VALUE;
        int closest = -1;
        for(int i=0; i<u.length; i++){
            double distance = getDistance(u[i], x);
            if(distance < min){
                min = distance;
                closest = i;
            }
        }
        return closest;
    }
    
    public double getDistance(T v1, T v2, Comparator<T> comparator){
        return comparator.compare(v1, v2); //TODO : Need a kind of comparator to have distance betweeen two objects of type T. Compatrator not good -> return int...
    }
}
