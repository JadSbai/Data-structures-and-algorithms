import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class DirectedGraph {
    private int V;

    // A class to store a graph edge
    static class Edge {
        int src, dest;

        Edge(int src, int dest) {
            this.src = src;
            this.dest = dest;
        }
    }

    // A list of lists to represent an adjacency list
    List<List<Integer>> adj = new ArrayList<>();

    // Constructor to construct a graph
    public DirectedGraph(List<Edge> edges) {
        // find the maximum numbered vertex
        int n = 0;
        for (Edge e : edges) {
            n = Integer.max(n, Integer.max(e.src, e.dest));
        }

        // allocate memory for the adjacency list
        for (int i = 0; i <= n; i++) {
            adj.add(i, new ArrayList<>());
        }

        // add edges to the undirected graph
        for (Edge current : edges) {
            // allocate new node in adjacency list from src to dest
            adj.get(current.src).add(current.dest);

            // uncomment line 44 for undirected graph

            // allocate new node in adjacency list from dest to src
            // adj.get(current.dest).add(current.src);
        }
    }

    // Function to print adjacency list representation of a graph
    public static void printGraph(DirectedGraph graph) {
        int src = 0;
        int n = graph.adj.size();

        while (src < n) {
            // print current vertex and all its neighboring vertices
            for (int dest : graph.adj.get(src)) {
                System.out.print("(" + src + " ——> " + dest + ")\t");
            }
            System.out.println();
            src++;
        }
    }

    // A recursive function used by topologicalSort
    void topologicalSortUtil(int v, boolean visited[], Stack<Integer> stack, ArrayList<ArrayList<Integer>> adj) {
        // Mark the current node as visited.
        visited[v] = true;
        Integer i;

        // Recur for all the vertices adjacent
        // to this vertex
        for (Integer integer : adj.get(v)) {
            i = integer;
            if (!visited[i])
                topologicalSortUtil(i, visited, stack, adj);
        }

        // Push current vertex to stack
        // which stores result
        stack.push(v);
    }

    // The function to do Topological Sort.
    // It uses recursive topologicalSortUtil()
    void topologicalSort(ArrayList<ArrayList<Integer>> adj) {
        Stack<Integer> stack = new Stack<Integer>();

        // Mark all the vertices as not visited
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;

        // Call the recursive helper
        // function to store
        // Topological Sort starting
        // from all vertices one by one
        for (int i = 0; i < V; i++)
            if (!visited[i])
                topologicalSortUtil(i, visited, stack, adj);

        // Print contents of stack
        while (!stack.empty())
            System.out.print(stack.pop() + " ");
    }

    public static void main(String[] args) {
        // Input: List of edges in a digraph (as per the above diagram)
        List<Edge> edges = Arrays.asList(new Edge(0, 1), new Edge(1, 2),
                new Edge(2, 0), new Edge(2, 1), new Edge(3, 2),
                new Edge(4, 5), new Edge(5, 4));

        // construct a graph from the given list of edges
        DirectedGraph graph = new DirectedGraph(edges);

        // print adjacency list representation of the graph
        DirectedGraph.printGraph(graph);


    }
}
