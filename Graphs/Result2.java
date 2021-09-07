package Graphs;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Result2 {

    public static class Graph {

        private final Set<Node> nodes = new HashSet<>();

        public void addNode(Node nodeA) {
            nodes.add(nodeA);
        }

        // getters and setters
    }

    public static class Node {

        private final long color;

        private final String name;

        private Integer distance = Integer.MAX_VALUE;

        private Map<Node, Integer> adjacentNodes = new HashMap<>();

        public void addDestination(Node destination, int distance) {
            adjacentNodes.put(destination, distance);
        }

        public Node(String name, long color) {
            this.name = name;
            this.color = color;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public Map<Node, Integer> getAdjacentNodes() {
            return adjacentNodes;
        }



        // getters and setters
    }

    //Node comparator based on the distance of each vertex (from the source vertex)
    static class NodeComparator implements Comparator<Node>{

        // Overriding compare()method of Comparator
        // for descending order of cgpa
        public int compare(Node s1, Node s2) {
            if (s1.distance < s2.distance)
                return 1;
            else if (s1.distance > s2.distance)
                return -1;
            return 0;
        }
    }



    // Main method of the Dijkstra algorithm
    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair :
                    currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    // Not necessary if you implement with a priority queue
    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    // this is the edge relaxation method
    private static void calculateMinimumDistance(Node evaluationNode,
                                                 Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + 1 < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + 1);
        }
    }

    // Find shortest path between 2 nodes of the same color inside a graph
    static int findShortest(int graphNodes, int[] graphFrom, int[] graphTo, long[] ids, int val)
    {
        Node[] nodes = new Node[graphNodes + 1];
        Graph graph = new Graph();
        String sourceName = "" + val;
        long sourceColor = ids[val - 1];
        ArrayList<Node> targetNodes = new ArrayList<>();

        int i = 1;
        int j = 0;
        while(j != graphFrom.length && i != graphNodes + 1){
            if(i <= graphNodes){
                String name = "" + i;
                long color = ids[i - 1];
                Node newNode = new Node(name, color);
                nodes[i] = newNode;
                if(!newNode.name.equals(sourceName) && newNode.color == sourceColor){
                    targetNodes.add(newNode);
                }
                graph.addNode(newNode);
                i++;
            }
            if(j < graphFrom.length){
                int currentNumber = graphFrom[j];
                Node currentNode = nodes[currentNumber];
                Node destination = nodes[graphTo[j]];
                if(destination != null && currentNode != null) {
                    currentNode.addDestination(destination, 1);
                    destination.addDestination(currentNode, 1);
                    j++;
                }

            }
        }

        Node source = nodes[val];
        calculateShortestPathFromSource(graph, source);

        int shortestPath = -1;
        for (Node node : targetNodes) {
            if(shortestPath == -1 ){
                shortestPath = node.distance;
            }
            else {
                shortestPath = Math.min(shortestPath, node.distance);
            }
        }

        return shortestPath;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        String[] graphNodesEdges = scanner.nextLine().split(" ");
        int graphNodes = Integer.parseInt(graphNodesEdges[0].trim());
        int graphEdges = Integer.parseInt(graphNodesEdges[1].trim());

        int[] graphFrom = new int[graphEdges];
        int[] graphTo = new int[graphEdges];

        for (int i = 0; i < graphEdges; i++) {
            String[] graphFromTo = scanner.nextLine().split(" ");
            graphFrom[i] = Integer.parseInt(graphFromTo[0].trim());
            graphTo[i] = Integer.parseInt(graphFromTo[1].trim());
        }

        long[] ids = new long[graphNodes];

        String[] idsItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < graphNodes; i++) {
            long idsItem = Long.parseLong(idsItems[i]);
            ids[i] = idsItem;
        }

        int val = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int ans = findShortest(graphNodes, graphFrom, graphTo, ids, val);

        System.out.println(ans);

        scanner.close();
    }
}
