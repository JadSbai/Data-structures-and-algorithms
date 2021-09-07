package Graphs;

import java.util.*;

public class GraphMethods {

    // Adjacency list representation of an undirected non-weighted graph
    static class Graph {
        private final int V;   // No. of vertices
        private final ArrayList<LinkedList<Integer>> adj; //Adjacency Lists

        // Constructor
        Graph(int v) {
            V = v;
            adj = new ArrayList<>();
            for (int i = 0; i < v; ++i)
                adj.add(i, new LinkedList<Integer>());
        }

        // Function to add an edge into the graph
        void addEdge(int v, int w)
        {
            adj.get(v).add(w);
        }

    }

    // BFS traversal of the given graph from the given source vertex
    public static void BFS(int s, int V, ArrayList<LinkedList<Integer>> adj) {
        // Mark all the vertices as not visited(By default
        // set as false)
        boolean[] visited = new boolean[V];

        // Create a queue for BFS
        ArrayDeque<Integer> queue = new ArrayDeque<>();

        // Mark the current node as visited and enqueue it
        visited[s] = true;
        queue.add(s);

        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            s = queue.poll();
            System.out.print(s + " ");
            for (int n : adj.get(s)) {
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
    }

    public static void DFS(int V, ArrayList<LinkedList<Integer>> adj){

        HashMap<Integer, Integer> parent = new HashMap<>();
        for(int i = 0; i < V ; i++){
            if(!parent.containsKey(i)){
                parent.put(i, null);
                System.out.print(i + " ");
                DFSVisit(adj, i, parent);
            }
        }
    }

    private static void DFSVisit(ArrayList<LinkedList<Integer>> adj, int S, HashMap<Integer, Integer> parent){
        parent.put(S, null);
        for(Integer vertex : adj.get(S)){
            if(!parent.containsKey(vertex)){
                parent.put(vertex, S);
                System.out.print(vertex + " ");
                DFSVisit(adj, vertex, parent);
            }
        }
    }

    // Method for the findJudge problem
    public int findJudge(int n, int[][] trust) {

        if(n == 1){
            return 1;
        }

        if(trust.length < n-1){
            return -1;

        }


        HashMap<Integer, Integer> map = new HashMap<>();
        HashMap<Integer, Boolean> map2 = new HashMap<>();
        HashSet<Integer> set = new HashSet<>();

        int[] curr = trust[0];
        set.add(curr[1]);
        map2.put(curr[0], true);
        map.put(curr[1], 1);

        for(int i = 1; i < trust.length; i ++){

            curr = trust[i];
            map2.put(curr[0], true);
            if(set.contains(curr[1])){
                map.put(curr[1], map.get(curr[1]) + 1);
            }
            else if(!map2.containsKey(curr[1])){
                set.add(curr[1]);
                map.put(curr[1], 1);
            }

            if(map.containsKey(curr[0])){
                set.remove(curr[0]);
            }

        }

        Iterator<Integer> it = set.iterator();
        int currJudge;
        while(it.hasNext()){
            currJudge = it.next();
            if(map.get(currJudge) == n -1){
                return currJudge;
            }
        }

        return -1;
    }

    // Better solution for the findJudge problem
    public int findJudge2(int n, int[][] trust) {
        int[] count = new int[n +1];

        for(int[] arr : trust){
            count[arr[0]] --;
            count[arr[1]] ++;

        }

        for(int i = 1; i <= n; i ++){
            if(count[i] == n -1){
                return i;
            }

        }
        return -1;
    }

    static ArrayList<LinkedList<Integer>> convert(int[][] a)
    {
        // no of vertices
        int l = a[0].length;
        ArrayList<LinkedList<Integer>> adjListArray = new ArrayList<>(l);

        // Create a new list for each
        // vertex such that adjacent
        // nodes can be stored
        for (int i = 0; i < l; i++) {
            adjListArray.add(new LinkedList<Integer>());
        }

        int i, j;
        for (i = 0; i < a[0].length; i++) {
            for (j = 0; j < a.length; j++) {
                if (a[i][j] == 1) {
                    adjListArray.get(i).add(j);
                }
            }
        }

        return adjListArray;
    }

    public static int minTime(List<List<Integer>> roads, List<Integer> machines)
    {
        AdjListGraph graph = new AdjListGraph(roads.size() + 1);
        HashMap<String, Integer> map = new HashMap<>();
        int finalCost = 0;

        if(machines.size() == roads.size() + 1){
            for(List<Integer> road : roads){
                finalCost += road.get(2);

            }
            return finalCost;
        }

        for(List<Integer> road : roads){
            graph.addEdge(road.get(0), road.get(1));
            graph.addEdge(road.get(1), road.get(0));
            String key1 = "" + road.get(0) + road.get(1);
            map.put(key1, road.get(2));
        }

        //CC connectedComponent = new CC(graph); // we don't need to compute the connected components as we arre given that for n vertices there will always be n-1 undirected edges, meaning there will always be 1 and only connected component containing all vertices of the graph !!!

        ArrayList<LinkedList<Integer>> adj = graph.getAdj();
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        for(int machine : machines){
            int total = 0;
            for(int neighbour : adj.get(machine)){
                String key = "" + machine + neighbour;
                if(map.get(key) == null){
                    key = "" + neighbour + machine;
//                    if(map.get(key) == null){
//                        continue;
//                    }
                }
                total += map.get(key);
                //map.remove(key);
            }
            if(total != 0){
                queue.offer(total);
            }

        }

        while(queue.size() > 1){
            finalCost += queue.poll();
        }


        return finalCost;
    }

    // Method used by isCyclic
    private boolean isCyclicUtil(int i, boolean[] visited,
                                 boolean[] recStack,ArrayList<ArrayList<Integer>> adj)
    {

        // Mark the current node as visited and
        // part of recursion stack
        if (recStack[i])
            return true;

        if (visited[i])
            return false;

        visited[i] = true;

        recStack[i] = true;
        List<Integer> children = adj.get(i);

        for (Integer c: children)
            if (isCyclicUtil(c, visited, recStack,adj))
                return true;

        recStack[i] = false;

        return false;
    }

    // Method to know whether a graph contains a cycle or not.
    private boolean isCyclic(int V,ArrayList<ArrayList<Integer>> adj)
    {

        // Mark all the vertices as not visited and
        // not part of recursion stack
        boolean[] visited = new boolean[V];
        boolean[] recStack = new boolean[V];


        // Call the recursive helper function to
        // detect cycle in different DFS trees
        for (int i = 0; i < V; i++)
            if (isCyclicUtil(i, visited, recStack,adj))
                return true;

        return false;
    }




    public static void main(String[] args) {
        Graph graph = new Graph(5);
        graph.addEdge(0,1);
        graph.addEdge(1,2);
        graph.addEdge(3,4);
        graph.addEdge(4,1);

        DFS(graph.V, graph.adj);
        System.out.println();
        BFS(0,graph.V, graph.adj );
    }


}
