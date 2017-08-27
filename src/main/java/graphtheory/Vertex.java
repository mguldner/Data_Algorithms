package graphtheory;

class Vertex {
    private int id;
    private int dist;
    
    public Vertex(int id, int dist){
        this.id = id;
        this.dist = dist;
    }
    
    public int getId(){
        return this.id;
    }
    
    public int getDist(){
        return this.dist;
    }
    
    public void setDist(int dist){
        this.dist = dist;
    }
}
