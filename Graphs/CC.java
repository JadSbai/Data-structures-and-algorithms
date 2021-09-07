package Graphs;

import java.util.*;

public class CC {

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
