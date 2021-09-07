package Graphs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

// This class represents a directed graph using adjacency list
// representation
class AdjListGraph {
    private int V;   // No. of vertices
    private final ArrayList<LinkedList<Integer>> adj; //Adjacency Lists

    // Constructor
    AdjListGraph(int v) {
        V = v;
        adj = new ArrayList<>();
        for (int i = 0; i < v; ++i)
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