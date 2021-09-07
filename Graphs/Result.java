package Graphs;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    public static class CC {

        private boolean[] marked;
        private  int[] id;
        private int count;
        private ArrayList<Integer> sizes;
        private int size;

        public CC(AdjListGraph graph) {
            sizes = new ArrayList<>();
            marked = new boolean[graph.getV() + 1];
            id = new int[graph.getV() + 1];

            for(int i = 1; i <= graph.getV(); i ++){
                size = 1;
                if(!marked[i]){
                    size = dfs(graph, i);
                    sizes.add(count, size);
                    count++;
                }
            }


        }

        private int dfs(AdjListGraph graph, int v){
            marked[v] = true;
            id[v] = count;

            for(int w : graph.getAdj().get(v)){
                if(!marked[w]){
                    size++;
                    dfs(graph, w);
                }
            }
            return size;
        }

        public boolean isConnected(int v, int w){
            return id[v] == id[w];
        }

        public int[] getId() {
            return id;
        }

        public ArrayList<Integer> getSizes() {
            return sizes;
        }


        public int getCount() {
            return count;
        }
    }

    static class AdjListGraph {
        private int V;   // No. of vertices
        private final ArrayList<LinkedList<Integer>> adj; //Adjacency Lists

        // Constructor
        AdjListGraph(int v) {
            V = v;
            adj = new ArrayList<>();
            adj.add(0, null);
            for (int i = 1; i <= v; i++)
                adj.add(i, new LinkedList<Integer>());
        }

        // Function to add an edge into the graph
        void addEdge(int v, int w) {
            adj.get(v).add(w);
        }

        public int getV() {
            return V;
        }

        public ArrayList<LinkedList<Integer>> getAdj() {
            return adj;
        }
    }

    // Main function
    public static long roadsAndLibraries(int n, int c_lib, int c_road, List<List<Integer>> cities)
    {
        // build the adjacency list graph representation
        AdjListGraph graph = new AdjListGraph(n);
        for(List<Integer> road : cities){
            graph.addEdge(road.get(0), road.get(1));
            graph.addEdge(road.get(1), road.get(0));
        }

        int totalCost = 0;

        CC connectedComponents = new CC(graph);

        int num = connectedComponents.getCount();
        int currentSize;
        for(int j = 0; j < num; j ++){
            currentSize = connectedComponents.getSizes().get(j);
            totalCost += Math.min(currentSize * c_lib, ((currentSize - 1) * c_road + c_lib));
        }
        return totalCost;

    }

}
