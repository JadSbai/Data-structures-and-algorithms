package Trees;

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

public class Solution2 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int queriesRows = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<String>> queries = new ArrayList<>();

        IntStream.range(0, queriesRows).forEach(i -> {
            try {
                queries.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> result = Result2.contacts(queries);

        System.out.println(result);

        bufferedReader.close();
    }
}
