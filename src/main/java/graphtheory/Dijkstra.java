package graphtheory;

import java.io.*;
import java.util.*;

/* This code was written for a challenge of HackerRank : https://www.hackerrank.com/challenges/dijkstrashortreach
   Here is the associated input/output description, available on hackerrank : 
   "Input Format
    The first line contains T, denoting the number of test cases. 
    First line of each test case has two integers N, denoting the number of nodes in the graph 
    and M, denoting the number of edges in the graph.
    The next M lines each consist of three space-separated integers x y r, where x and y denote 
    the two nodes between which the undirected edge exists, r denotes the length of edge between these corresponding nodes.
    The last line has an integer S, denoting the starting position.
      
    Output Format : 
    For each of the  test cases, print a single line consisting N-1 space separated integers 
    denoting the shortest distance of N-1 nodes other than S from starting position S in increasing order of their labels.
    For unreachable nodes, print -1.
    "
    
    My implementation uses an adjacency matrix as input of the BFS algorithm.
*/

public class Dijkstra {

    public static void main(String[] args) {
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int t = Integer.parseInt(br.readLine());
            for(int a0 = 0; a0 < t; a0++){
                String[] nm = br.readLine().split(" ");
                int n = Integer.parseInt(nm[0]);
                int[][] adjacency = new int[n][n];
                Vertex[] vertices = new Vertex[n];
                int m = Integer.parseInt(nm[1]);
                for(int a1 = 0; a1 < m; a1++){
                    String[] xyr = br.readLine().split(" ");
                    int x = Integer.parseInt(xyr[0]) - 1;
                    int y = Integer.parseInt(xyr[1]) - 1;
                    int r = Integer.parseInt(xyr[2]);
                    if(adjacency[x][y] != 0){
                        adjacency[x][y] = Math.min(adjacency[x][y], r);
                        adjacency[y][x] = adjacency[x][y];
                    }
                    else{
                        adjacency[x][y] = r;
                        adjacency[y][x] = r;
                    }
                }
                int s = Integer.parseInt(br.readLine()) - 1;
                PriorityQueue<Vertex> Q = new PriorityQueue<Vertex>(n, new VertexComparator());
                for(int i=0; i<n; i++){
                    if(i!=s)
                        vertices[i] = new Vertex(i, -1);
                    else
                        vertices[i] = new Vertex(i, 0);
                    Q.add(vertices[i]);
                }

                while(!Q.isEmpty()){
                    Vertex tmpV = Q.poll();
                    for(int i=0; i<n; i++){
                        if(tmpV.getDist() != -1 && adjacency[tmpV.getId()][i] != 0){
                            int newDist = tmpV.getDist() + adjacency[tmpV.getId()][i];
                            if(vertices[i].getDist() == -1 || newDist < vertices[i].getDist()){
                                changeDist(Q, vertices[i], newDist);
                            }
                        }
                    }
                }

                for(int i=0; i<n; i++){
                    if(i!=s)
                        System.out.print(vertices[i].getDist()+" ");
                }
                System.out.println();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public static void changeDist(PriorityQueue<Vertex> Q, Vertex v, int newDist){
        Q.remove(v);
        v.setDist(newDist);
        Q.add(v);
    }
}