package Graphs;

//  Strongly connected components in an undirected graph
public class SCC {

        private boolean[] marked;
        private  int[] id;
        private int count;

        public SCC(AdjListGraph graph) {
            marked = new boolean[graph.getV()];
            id = new int[graph.getV()];

            for(int i = 0; i < graph.getV(); i ++){

                if(!marked[i]){

                    count++;

                }

            }
        }

        private void dfs(AdjListGraph graph, int v){
            marked[v] = true;
            id[v] = count;

            for(int w : graph.getAdj().get(v)){
                if(!marked[w]){
                    dfs(graph, w);
                }

            }

        }

        public boolean isStronglyConnected(int v, int w){
            return id[v] == id[w];
        }

        public int getId(int v) {
            return id[v];
        }

        public int getCount() {
            return count;
        }
    }

