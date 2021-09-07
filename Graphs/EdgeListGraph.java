package Graphs;

import java.util.HashSet;
import java.util.LinkedList;

public class EdgeListGraph {

    static class Node {
        private String name;
        private LinkedList < Edge > edgeList;

        public Node(String name) {
            this.name = name;
            edgeList = new LinkedList<>();
        }

        public String getName() {
            return name;
        }

        public LinkedList < Edge > getEdges() {
            return edgeList;
        }
    }

    static class Edge {
        private int weight;
        private Node destVertex;

        public Edge(Node dest, int w) {
            this.destVertex = dest;
            this.weight = w;
        }

        public Edge(Node dest) {
            this.destVertex = dest;
            this.weight = 1;
        }

        public int getWeight() {
            return weight;
        }

        public Node getDestVertex() {
            return destVertex;
        }
    }

    static class Graph {
        private HashSet < Node > nodes;

        public Graph() {
            nodes = new HashSet< >();
        }

        public boolean AddEdge(Node v1, Node v2, int weight) {
            return v1.getEdges().add(new Edge(v2, weight)) && v2.getEdges().add(new Edge(v1, weight));
        }

        public boolean AddVertex(Node v) {
            return nodes.add(v);
        }

        public void printGraph() {
            for (Node v: nodes) {
                System.out.print("vertex name: " + v.getName() + ":\n");
                for (Edge e: v.getEdges()) {
                    System.out.print("destVertex: " + e.getDestVertex().getName() + ", weight: " + e.getWeight() + "\n");
                }
                System.out.print("\n");
            }
        }
    }

    public static class Main {
        public static void main(String[] args) {
            Graph ourGraph = new Graph();

            // nodes
            Node v0 = new Node("0");
            Node v1 = new Node("1");
            Node v2 = new Node("2");
            Node v3 = new Node("3");

            ourGraph.AddVertex(v0);
            ourGraph.AddVertex(v1);
            ourGraph.AddVertex(v2);
            ourGraph.AddVertex(v3);

            // edges
            ourGraph.AddEdge(v0, v1, 2);
            ourGraph.AddEdge(v1, v2, 3);
            ourGraph.AddEdge(v2, v0, 1);
            ourGraph.AddEdge(v2, v3, 1);
            ourGraph.AddEdge(v3, v2, 4);

            ourGraph.printGraph();
        }
    }
}
