package Graphs;

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
public class OK {


    static class Result {


        private static int currentMax;

        public static void DFS(List<List<Integer>> grid)
        {

            currentMax = 0;
            boolean[][] visited = new boolean[grid.size()][grid.get(0).size()];
            int newMax = 0;
            int breadth = grid.get(0).size() - 1;
            for(int i = 0; i < grid.size() ; i++){
                for(int j = 0; j <= breadth; j++){
                    if(grid.get(i).get(j) == 1){
                        if(!visited[i][j]){
                            newMax ++;
                            newMax = DFSVisit(grid, i, j, visited, newMax);
                        }
                    }
                    visited[i][j] = true;
                    currentMax = Math.max(currentMax, newMax);
                    newMax = 0;
                }

            }
        }

        private static int DFSVisit(List<List<Integer>> grid, int i, int j, boolean[][] visited, int newMax)
        {
            int length = grid.size() -1;
            int breadth = grid.get(0).size() - 1;
            visited[i][j] = true;
            ArrayList<int[]> neighbours = new ArrayList<>();
            if( i - 1 >= 0){
                neighbours.add(new int[]{i - 1, j});
                if( j - 1 >= 0){
                    neighbours.add(new int[]{i - 1, j - 1});
                }
            }
            if( j - 1 >= 0){
                neighbours.add(new int[]{i, j - 1});
            }
            if(j +1 <= breadth){
                neighbours.add(new int[]{i, j + 1});
                if( i - 1 >= 0){
                    neighbours.add(new int[]{i - 1, j + 1});
                }
            }
            if(i +1 <= length){
                neighbours.add(new int[]{i + 1, j});
                if( j - 1 >= 0){
                    neighbours.add(new int[]{i + 1, j - 1});
                }

                if( j + 1 <= breadth && i + 1 <= length){
                    neighbours.add(new int[]{i + 1, j + 1});
                }
            }
            int newCurrentMax = newMax;
            for(int[] neighbour : neighbours){
                int k = neighbour[0];
                int n = neighbour[1];
                if(grid.get(k).get(n) == 1){
                    if(!visited[k][n]){
                        newCurrentMax ++;
                        newCurrentMax = DFSVisit(grid, k, n, visited, newCurrentMax);
                    }
                }
            }

            return newCurrentMax;
        }

        public static int maxRegion(List<List<Integer>> grid) {

            DFS(grid);
            return currentMax;

        }

    }

    public static class Solution {
        public static void main(String[] args) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            int n = Integer.parseInt(bufferedReader.readLine().trim());

            int m = Integer.parseInt(bufferedReader.readLine().trim());

            List<List<Integer>> grid = new ArrayList<>();

            IntStream.range(0, n).forEach(i -> {
                try {
                    grid.add(
                            Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                    .map(Integer::parseInt)
                                    .collect(toList())
                    );
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            int res = Result.maxRegion(grid);
            System.out.println(res);

            bufferedReader.close();
        }
    }


}
