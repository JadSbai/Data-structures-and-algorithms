package Graphs;

import Trees.TreeMethods;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result3 {

    static class DisjointUnionSets {
        int[] rank, parent;
        int n;

        // Constructor
        public DisjointUnionSets(int n, HashMap<Integer, Boolean> map, List<Integer> machines) {
            rank = new int[n];
            parent = new int[n];
            this.n = n;
            makeSet(map, machines);
        }

        // Creates n sets with single item in each
        void makeSet(HashMap<Integer, Boolean> map, List<Integer> machines) {
            for(int i = 0; i< n; i++){
                parent[i] = i;
                if(i < machines.size()){
                    map.put(machines.get(i), true);
                }
                if(!map.containsKey(i)){
                    map.put(i, false);
                }




            }
        }


        // Returns representative of x's set
        int find(int x) {
            // Finds the representative of the set
            // that x is an element of
            if (parent[x] != x) {
                // if x is not the parent of itself
                // Then x is not the representative of
                // his set,
                parent[x] = find(parent[x]);

                // so we recursively call Find on its parent
                // and move i's node directly under the
                // representative of this set
            }

            return parent[x];
        }

        // Unites the set that includes x and the set
        // that includes y
        void union(int x, int y) {
            // Find representatives of two sets
            int xRoot = find(x), yRoot = find(y);

            // Elements are in the same set, no need
            // to unite anything.
            if (xRoot == yRoot)
                return;

            // If x's rank is less than y's rank
            if (rank[xRoot] < rank[yRoot])

                // Then move x under y  so that depth
                // of tree remains less
                parent[xRoot] = yRoot;

                // Else if y's rank is less than x's rank
            else if (rank[yRoot] < rank[xRoot])

                // Then move y under x so that depth of
                // tree remains less
                parent[yRoot] = xRoot;

            else // if ranks are the same
            {
                // Then move y under x (doesn't matter
                // which one goes where)
                parent[yRoot] = xRoot;

                // And increment the result tree's
                // rank by 1
                rank[xRoot] = rank[xRoot] + 1;
            }
        }
    }

        public static int minTime(List<List<Integer>> roads, List<Integer> machines) {
            HashMap<Integer, Boolean> map = new HashMap<>();
            DisjointUnionSets set = new DisjointUnionSets(roads.size() + 1, map, machines);

            int finalCost = 0;
            //roads.sort((x, y) -> Integer.compare(y.get(2), x.get(2)));


            for(List<Integer> road : roads){
                int city1 = road.get(0);
                int city2 = road.get(1);
                int cost = road.get(2);
                int set1 = set.find(city1);
                int set2 = set.find(city2);

                if(map.get(city1) && map.get(city2)){
                    finalCost += cost;
                }

                else if(map.get(set1) && map.get(set2)) {
                    finalCost += cost;
                }

                else{
                    set.union(set1, set2);
                    if(map.get(set1) || map.get(set2)){
                        map.put(set1, true);
                        map.put(set2, true);
                        map.put(city1, true);
                        map.put(city2, true);
                    }
                }
            }

            return finalCost;

        }



}

