package graphtheory;

import java.util.*;

/* This code was written for a challenge of HackerRank : https://www.hackerrank.com/challenges/bfsshortreach
   Here is the associated input/output description, available on hackerrank : 
   "Input Format
    The first line contains an integer, q, denoting the number of queries. 
    The subsequent lines describe each query in the following format:

      - The first line contains two space-separated integers describing the respective values
      of n (the number of nodes) and m (the number of edges) in the graph.
      - Each line i of the m subsequent lines contains two space-separated integers, u and v, 
        describing an edge connecting node u to node v.
      - The last line contains a single integer, s, denoting the index of the starting node.
      
    Output Format : 
    For each of the q queries, print a single line of n-1 space-separated integers denoting 
    the shortest distances to each of the n-1 other nodes from starting position s. 
    These distances should be listed sequentially by node number (i.e., ), but should not include node s. 
    If some node is unreachable from s, print -1 as the distance to that node.
    "
    
    My implementation uses an adjacency matrix as input of the BFS algorithm.
*/

public class BreadFirstSearch {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int q = sc.nextInt();
        while(q>0){
            int n = sc.nextInt();
            int[][] adjacency = new int[n][n];
            int[] dist = new int[n];
            for(int i=0; i<n; i++){
                dist[i]=-1;
            }
            int m = sc.nextInt();
            for(int i=0; i<m; i++){
                int tmpS = sc.nextInt();
                int tmpD = sc.nextInt();
                adjacency[tmpS-1][tmpD-1] = 1;
                adjacency[tmpD-1][tmpS-1] = 1;
            }
            int s = sc.nextInt()-1;
            
            HashSet<Integer> S = new HashSet<Integer>();
            ArrayDeque<Integer> Q = new ArrayDeque<Integer>();
            S.add(s);
            Q.add(s);
            dist[s] = 0;
            
            while(!Q.isEmpty()){
                int tmp = Q.remove();
                for(int i=0; i<n; i++){
                    if(adjacency[tmp][i] == 1){
                        if(S.contains(i)){
                            int tmpDist = dist[i] + 6;
                            if(dist[i] != -1 && (dist[tmp] == -1 || dist[tmp] > tmpDist))
                                dist[tmp] = tmpDist;
                        }
                        else{
                            S.add(i);
                            Q.add(i);
                        }
                    }
                }
            }
            for(int i=0; i<n; i++){
                if(i!=s)
                    System.out.print(dist[i]+" ");
            }
            q--;
            System.out.println();
        }
        sc.close();
    }
}