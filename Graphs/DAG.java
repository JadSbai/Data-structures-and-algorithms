package Graphs;

import java.util.ArrayList;
import java.util.List;

public class DAG {

    static class Edge
    {
        int source, dest;

        public Edge(int source, int dest)
        {
            this.source = source;
            this.dest = dest;
        }
    }

    // A class to represent a graph object
    static class Graph {
        // A list of lists to represent an adjacency list
        List<List<Integer>> adjList = null;

        int V;

        // Constructor
        Graph(List<Solution4.Edge> edges, int N) {
            adjList = new ArrayList<>();
            V = N;

            for (int i = 0; i < N; i++) {
                adjList.add(new ArrayList<>());
            }

            // add edges to the undirected graph
            for (Solution4.Edge edge : edges) {
                adjList.get(edge.source).add(edge.dest);
            }
        }
    }



    // Perform DFS on the graph and set the departure time of all
    // vertices of the graph
    private static int DFS(Solution4.Graph graph, int v, boolean[] discovered,
                           int[] departure, int time)
    {
        // mark the current node as discovered
        discovered[v] = true;

        // do for every edge `v —> u`
        for (int u: graph.adjList.get(v))
        {
            // if `u` is not yet discovered
            if (!discovered[u]) {
                time = DFS(graph, u, discovered, departure, time);
            }
        }

        // ready to backtrack
        // set departure time of vertex `v`
        departure[v] = time++;

        return time;
    }

    // Returns true if given directed graph is DAG
    public static boolean isDAG(Solution4.Graph graph, int N)
    {
        // keep track of whether a vertex is discovered or not
        boolean[] discovered = new boolean[N];

        // keep track of the departure time of a vertex in DFS
        int[] departure = new int[N];

        int time = 0;

        // Perform DFS traversal from all undiscovered vertices
        // to visit all connected components of a graph
        for (int i = 0; i < N; i++)
        {
            if (!discovered[i]) {
                time = DFS(graph, i, discovered, departure, time);
            }
        }

        // check if the given directed graph is DAG or not
        for (int u = 0; u < N; u++)
        {
            // check if `(u, v)` forms a back-edge.
            for (int v: graph.adjList.get(u))
            {
                // If the departure time of vertex `v` is greater than equal
                // to the departure time of `u`, they form a back edge.

                // Note that departure[u] will be equal to
                // departure[v] only if `u = v`, i.e., vertex
                // contain an edge to itself
                if (departure[u] <= departure[v]) {
                    return false;
                }
            }
        }

        // no back edges
        return true;
    }

    public static void main(String[] args) {

    }
}
