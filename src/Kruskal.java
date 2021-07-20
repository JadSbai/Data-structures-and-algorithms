import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Kruskal {

    public static void kruskalMST(Graph graph) {
        ArrayList<Edge> edges = graph.allEdges;
        int numberOfVertices = graph.vertices;
        PriorityQueue<Edge> pq = new PriorityQueue<>(edges.size(), Comparator.comparingInt(o -> o.weight));

        //add all the edges to priority queue, //sort the edges on weights
        pq.addAll(edges);

        //create a parent []
        int[] parent = new int[numberOfVertices];

         UnionFind uf = new UnionFind(numberOfVertices);
        Subset[] subsets =  uf.getSubsets();

        ArrayList<Edge> mst = new ArrayList<>();

        //process vertices - 1 edges
        int index = 0;
        while (index < numberOfVertices - 1) {
            Edge edge = pq.remove();
            //check if adding this edge creates a cycle
            int x_set = uf.find(subsets,edge.source);
            int y_set = uf.find(subsets,edge.destination);

            if (x_set == y_set) {
                //ignore, will create cycle
            } else {
                //add it to our final result
                mst.add(edge);
                index++;
                uf.Union(subsets,x_set, y_set);
            }
        }
        //print MST
        System.out.println("Minimum Spanning Tree: ");
        printGraph(mst);
    }

    public static void printGraph(ArrayList<Edge> edgeList) {
        for (int i = 0; i < edgeList.size(); i++) {
            Edge edge = edgeList.get(i);
            System.out.println("Edge-" + i + " source: " + edge.source +
                    " destination: " + edge.destination +
                    " weight: " + edge.weight);
        }
    }


    public static void main(String[] args) {
        int vertices = 6;
        Graph graph = new Graph(vertices);
        graph.addEgde(0, 1, 4);
        graph.addEgde(0, 2, 3);
        graph.addEgde(1, 2, 1);
        graph.addEgde(1, 3, 2);
        graph.addEgde(2, 3, 4);
        graph.addEgde(3, 4, 2);
        graph.addEgde(4, 5, 6);
        kruskalMST(graph);
    }
}