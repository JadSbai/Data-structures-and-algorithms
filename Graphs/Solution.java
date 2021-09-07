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
    public class Solution {
        public static void main(String[] args) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            int q = Integer.parseInt(bufferedReader.readLine().trim());

            IntStream.range(0, q).forEach(qItr -> {
                try {
                    String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                    int n = Integer.parseInt(firstMultipleInput[0]);

                    int m = Integer.parseInt(firstMultipleInput[1]);

                    int c_lib = Integer.parseInt(firstMultipleInput[2]);

                    int c_road = Integer.parseInt(firstMultipleInput[3]);

                    List<List<Integer>> cities = new ArrayList<>();

                    IntStream.range(0, m).forEach(i -> {
                        try {
                            cities.add(
                                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                            .map(Integer::parseInt)
                                            .collect(toList())
                            );
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });

                    long result = Result.roadsAndLibraries(n, c_lib, c_road, cities);
                    System.out.println(result);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            bufferedReader.close();
        }
    }

