package graphtheory;

import java.util.*;

class VertexComparator implements Comparator<Vertex> {
    
    public int compare(Vertex o1, Vertex o2){
        if(o1.getDist() == -1)
            return 1;
        else if(o2.getDist() == -1)
            return -1;
        else{
            if(o1.getDist() < o2.getDist())
                return -1;
            else if(o1.getDist() > o2.getDist())
                return 1;
            else
                return 0;
        } 
    }
}
