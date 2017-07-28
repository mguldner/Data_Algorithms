package machinelearning.kmeans2;

import java.util.ArrayList;
import java.util.List;

public class Cluster<T> {

    private int id;
    private List<T> points = new ArrayList<>();
    private GenericTool<T> genericTool = null;
    private T centroid;
    
    public Cluster(int id, List<T> points, GenericTool<T> genericTool){
        this.id = id;
        this.points = points;
        this.genericTool = genericTool;
        assignCentroid();
    }
    
    public Cluster(int id, T centroid, GenericTool<T> genericTool){
        this.id = id;
        this.centroid = centroid;
        this.genericTool = genericTool;
    }

    public List<T> getPoints() {
        return points;
    }

    public void setPoints(List<T> points) {
        this.points = points;
    }
    
    public void addPoint(T point){
        this.points.add(point);
    }

    public T getCentroid() {
        return centroid;
    }

    public void setCentroid(T centroid) {
        this.centroid = centroid;
    }

    public int getId() {
        return id;
    }
    
    public void clearPoints(){
        this.setPoints(new ArrayList<>());
    }
    
    public boolean assignCentroid(){
        this.centroid = this.genericTool.calculateMean(this.points);
        return !this.points.isEmpty();
    }
    
    public int getSize(){
        return this.points.size();
    }
}
