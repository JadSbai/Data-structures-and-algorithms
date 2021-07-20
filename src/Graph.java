import java.util.ArrayList;

class Graph {
    int vertices;

    ArrayList<Edge> allEdges = new ArrayList<>();

    Graph(int vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Edge> getAllEdges() {
        return allEdges;
    }

    public void addEgde(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        allEdges.add(edge); //add to total edges
    }
}