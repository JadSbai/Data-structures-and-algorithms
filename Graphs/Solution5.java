package Graphs;
import java.util.*;
class Solution5 {

    static class AdjListGraph {
        private int V;   // No. of vertices
        private final ArrayList<ArrayList<Integer>> adj; //Adjacency Lists

        // Constructor
        AdjListGraph(int v) {
            V = v;
            adj = new ArrayList<>();
            for (int i = 0; i <= v; i++)
                adj.add(i, new ArrayList<Integer>());
        }

        // Function to add an edge into the graph
        void addEdge(int v, int w) {
            adj.get(v).add(w);
        }

        public int getV() {
            return V;
        }

        public ArrayList<ArrayList<Integer>> getAdj() {
            return adj;
        }
    }

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

    public static int[] findRedundantConnection(int[][] edges) {
        AdjListGraph graph = new AdjListGraph(edges.length);

        for(int[] edge : edges){
            graph.addEdge(edge[0], edge[1]);
            graph.addEdge(edge[1], edge[0]);

        }

        Stack<int[]> stack = new Stack<>();

        for(int[] edge : edges){
            Object source = edge[0];
            Object dest = edge[1];
            graph.adj.get(edge[0]).remove(dest);
            graph.adj.get(edge[1]).remove(source);
            CC connectedGraph = new CC(graph);
            if(connectedGraph.isConnected(edge[0], edge[1])){
                stack.push(edge);
            }
            graph.addEdge(edge[0], edge[1]);
            graph.addEdge(edge[1], edge[0]);
        }

        return stack.pop();

    }

    public static void main(String[] args) {
        int[] edge1 = {1,3};
        int[] edge2 = {1,2};
        int[] edge3 = {2,3};

        int[][] edges = {edge1, edge2, edge3};

        System.out.println(Arrays.toString(findRedundantConnection(edges)));

    }
}
