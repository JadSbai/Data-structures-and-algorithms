import com.sun.source.tree.Tree;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Main {
    public static int countStudents(int[] students, int[] sandwiches) {
        Stack<Integer> sa = new Stack<>();
        Queue<Integer> st = new LinkedList();
        for (int i = students.length - 1; i >= 0; i--) {
            sa.push(sandwiches[i]);
        }
        for (int student : students) {
            st.add(student);
        }
        int limit = students.length;
        int iterations = 1;
        while (iterations <= limit) {
            assert st.peek() != null;
            if (st.peek().intValue() == sa.peek().intValue()) {
                st.poll();
                sa.pop();
                limit = st.size();
                iterations = 0;
            } else {
                st.add(st.poll());
            }
            iterations += 1;

        }
        return st.size();


    }

    // Complete the triplets function below.
    static long triplets(int[] a, int[] b, int[] c) {
        Arrays.sort(a);
        Arrays.sort(b);
        Arrays.sort(c);
        a = Arrays.stream(a).distinct().toArray();
        b = Arrays.stream(b).distinct().toArray();
        c = Arrays.stream(c).distinct().toArray();
        int result = 0;
        for (int i = 0; i < b.length; i++) {
            int e = b[i];
            int f = 0;
            int s = 0;
            for (int j = 0; j < a.length; j++) {
                if (a[j] > e) {
                    f = j;
                    break;
                }
            }
            for (int k = 0; k < c.length; k++) {
                if (c[k] > e) {
                    s = k;
                    break;
                }

            }
            result += (f * s);
        }
        return result;
    }

    public static long minimumPasses(long m, long w, long p, long n) {
        long e;
        long passes = 0;
        long totalCandies = 0L;
        while (totalCandies < n) {
            totalCandies += (m * w);
            if (totalCandies >= n) {
                return passes + 1;
            }
            if (totalCandies < p) {
                passes += 1L;
                continue;
            }
            long r = totalCandies / p;
            while (r > 0) {
                if (m <= w) m += 1;
                else w += 1;
                r -= 1;
                totalCandies -= p;
            }
            passes += 1L;
        }
        return passes;
    }

    public static long largestRectangle(List<Integer> h) {
        long maxArea = 0L;
        for (int i = 0; i < h.size(); i++) {
            int a = Collections.min(h.subList(i, h.size()));
            long area = (long) a * (h.size() - i);
            System.out.println(area);
            maxArea = Math.max(maxArea, area);
        }
        return maxArea;

    }

    public static int maxSumMinProduct(int[] nums) {
        HashMap<Integer, HashMap<Integer, Boolean>> indices = new HashMap<>();
        int max = Arrays.stream(nums).max().getAsInt();
        int index = IntStream.range(0, nums.length)
                .filter(i -> max == nums[i])
                .findFirst()
                .orElse(-1);
        for (int i = 0; i < nums.length; i++) {
            indices.putIfAbsent(nums[i], new HashMap<>());
            indices.get(nums[i]).put(i, true);
        }

        Arrays.sort(nums);
        Stack<Integer> s = new Stack<>();
        int maxMinProduct = 0;
        s.push(nums[nums.length - 1]);
        indices.get(nums[nums.length - 1]).remove(index);
        int currentSum = s.peek();
        int minIndex = index;
        int maxIndex = index;
        for (int i = nums.length - 2; i >= 0; i--) {
            int next = nums[i];
            HashMap<Integer, Boolean> nextIndices = indices.get(next);
            int current = s.peek();
            if (nextIndices.containsKey(maxIndex + 1)) {
                int newM = Math.min(current, next) * (currentSum + next);
                if (newM > maxMinProduct) {
                    maxMinProduct = newM;
                    s.push(next);
                    nextIndices.remove(maxIndex + 1);
                    maxIndex += 1;
                    currentSum += next;
                    current = s.peek();
                }
            }
            if (nextIndices.containsKey(minIndex - 1)) {
                int newM = Math.min(current, next) * (currentSum + next);
                if (newM > maxMinProduct) {
                    maxMinProduct = newM;
                    s.push(next);
                    nextIndices.remove(minIndex - 1);
                    minIndex -= 1;
                    currentSum += next;
                }

            }
        }
        return maxMinProduct;
    }

    public static int minimumMoves(List<String> grid, int startX, int startY, int goalX, int goalY) {
        AbstractMap.SimpleEntry<Integer, Integer> startLocation = new AbstractMap.SimpleEntry<>(startX, startY);
        HashMap<AbstractMap.SimpleEntry<Integer, Integer>, Boolean> visited = new HashMap<>();
        HashMap<AbstractMap.SimpleEntry<Integer, Integer>, AbstractMap.SimpleEntry<Integer, Integer>> prev = new HashMap<>();
        Deque<AbstractMap.SimpleEntry<Integer, Integer>> queue = new ArrayDeque<>();
        int minSteps = Integer.MAX_VALUE;
        int xBound = grid.get(0).length() - 1;
        int yBound = grid.size() - 1;
        // Start by visiting the 'start' node and add it to the queue.
        queue.offer(startLocation);
        visited.put(startLocation, true);

        // Continue until the BFS is done.
        while (!queue.isEmpty()) {
            AbstractMap.SimpleEntry<Integer, Integer> node = queue.poll();
            int x = node.getKey();
            int y = node.getValue();
            List<AbstractMap.SimpleEntry<Integer, Integer>> neighbours = new ArrayList<>();
            neighbours.add(new AbstractMap.SimpleEntry<>(x + 1, y));
            neighbours.add(new AbstractMap.SimpleEntry<>(x, y + 1));
            neighbours.add(new AbstractMap.SimpleEntry<>(x - 1, y));
            neighbours.add(new AbstractMap.SimpleEntry<>(x, y - 1));
            neighbours = neighbours.stream().filter(e -> e.getKey() >= 0 && e.getKey() <= xBound && e.getValue() >= 0 && e.getValue() <= yBound).collect(Collectors.toList());
            // Loop through all edges attached to this node. Mark nodes as visited once they're
            // in the queue. This will prevent having duplicate nodes in the queue and speedup the BFS.
            for (AbstractMap.SimpleEntry<Integer, Integer> n : neighbours) {
                if (!visited.containsKey(n) || grid.get(n.getKey()).charAt(n.getValue()) != 'X') {
                    visited.put(n, true);
                    prev.put(n, node);
                    queue.offer(n);
                    if (n.getKey() == goalX && n.getValue() == goalY) {
                        AbstractMap.SimpleEntry<Integer, Integer> current = n;
                        int steps = 0;
                        while (current != startLocation) {
                            steps += 1;
                            current = prev.get(current);
                        }
                        minSteps = Math.min(steps, minSteps);
                    }
                }
            }

        }
        return minSteps;
    }

    static List<Integer> freqQuery(List<List<Integer>> queries) {
        ArrayList<Integer> result = new ArrayList<>();
        HashMap<Integer, Integer> frequencies = new HashMap<>();
        HashMap<Integer, Integer> containedFrequencies = new HashMap<>();
        for (List<Integer> q : queries) {
            int type = q.get(0);
            int data = q.get(1);
            if (type == 1) {
                frequencies.putIfAbsent(data, 0);
                int e = frequencies.get(data);
                frequencies.put(data, e + 1);
                if (containedFrequencies.containsKey(e)) {
                    if (containedFrequencies.get(e) > 0) {
                        if (containedFrequencies.get(e) - 1 == 0) containedFrequencies.remove(e);
                        else {
                            containedFrequencies.put(e, containedFrequencies.get(e) - 1);
                        }
                    }
                }
                containedFrequencies.putIfAbsent(e + 1, 0);
                int j = containedFrequencies.get(e + 1);
                containedFrequencies.put(e + 1, j + 1);
            } else if (type == 2) {
                if (frequencies.containsKey(data)) {
                    int e = frequencies.get(data);
                    if (containedFrequencies.containsKey(e)) {
                        if (containedFrequencies.get(e) - 1 == 0) containedFrequencies.remove(e);
                        else {
                            containedFrequencies.put(e, containedFrequencies.get(e) - 1);
                        }
                    }
                    if (e - 1 == 0) {
                        frequencies.remove(data);
                        containedFrequencies.remove(0);
                    } else {
                        frequencies.put(data, e - 1);
                        containedFrequencies.putIfAbsent(e - 1, 0);
                        int j = containedFrequencies.get(e - 1);
                        containedFrequencies.put(e - 1, j + 1);
                    }
                }
            } else {

                if (containedFrequencies.containsKey(data) && containedFrequencies.get(data) > 0) result.add(1);
                else result.add(0);

            }
        }
        return result;

    }

    // method to print the divisors
    static ArrayList<Long> printDivisors(long n) {
        ArrayList<Long> result = new ArrayList<>();
        // Note that this loop runs till square root
        for (long i = 1; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                // If divisors are equal, print only one
                if (n / i == i)
                    result.add(i);

                else // Otherwise print both
                {
                    result.add(i);
                    result.add(n / i);
                }

            }
        }
        return result;
    }

    static long countTriplets(List<Long> arr, long r) {
        HashMap<Long, Long> rightMap = new HashMap<>();
        HashMap<Long, Long> leftMap = new HashMap<>();
        for (Long e : arr) {
            rightMap.putIfAbsent(e, 0L);
            leftMap.putIfAbsent(e, 0L);
            rightMap.put(e, rightMap.get(e) + 1);
        }
        long count = 0;
        for (int i = 0; i < arr.size(); i++) {
            Long e = arr.get(i);
            rightMap.put(e, rightMap.get(e) - 1);
            if (e % r == 0L) count += (leftMap.get(e / r) * rightMap.get(e * r));
            leftMap.put(e, leftMap.get(e) + 1);
        }
        return count;
    }

    public static void checkMagazine(List<String> magazine, List<String> note) {
        HashMap<String, Integer> magazineH = new HashMap<>();
        HashMap<String, Integer> noteH = new HashMap<>();
        for (String s : magazine) {
            magazineH.putIfAbsent(s, 0);
            magazineH.put(s, magazineH.get(s) + 1);
        }
        for (String s : note) {
            noteH.putIfAbsent(s, 0);
            noteH.put(s, noteH.get(s) + 1);
        }
        for (String s : note) {
            if (!magazineH.containsKey(s)) {
                System.out.println("No");
                return;
            } else if (magazineH.get(s) < noteH.get(s)) {
                System.out.println("No");
                return;
            }
        }
        System.out.println("Yes");


    }

    // Complete the maxSubsetSum function below.
    static int maxSubsetSum(int[] arr) {
        int[] result = new int[arr.length];
        result[0] = arr[0];
        result[1] = arr[1];
        int max = 0;
        int maxCurrent = result[0];
        for(int i=2; i<arr.length; i++)
        {
            if(i!=2) maxCurrent = Math.max(maxCurrent,result[i-2]);
            result[i] = maxCurrent + arr[i];
            max = Math.max(result[i],max);

        }
        return max;

    }

    public static long candies(int n, List<Integer> arr) {
        int[] increasing = new int[arr.size()];
        int[] decreasing = new int[arr.size()];
        int[] result = new int[arr.size()];
        increasing[1] = arr.get(1) > arr.get(0) ? 1: 0;
        decreasing[1] = arr.get(1) <= arr.get(0) ? 1: 0;
        for(int i=1; i<arr.size()-1; i++)
        {
            increasing[i] = arr.get(i+1) > arr.get(i) ? increasing[i-1]+1: 0;
            decreasing[i] = arr.get(i+1) <= arr.get(i) ? decreasing[i-1]+1: 0;
        }
        System.out.println(Arrays.toString(increasing));
        System.out.println(Arrays.toString(decreasing));
        int current = 0;
        int currentCounter = -1;
        long sum = 0L;
        for(int j=0; j<increasing.length; j++)
        {
            if(increasing[j]>decreasing[j]) result[j] = current + increasing[j];
            else {
                if(currentCounter == -1)
                {
                    int k = j+1;
                    while(decreasing[k] > decreasing[k-1])
                    {
                        k+=1;
                    }
                    currentCounter = decreasing[k-1];
                }
                result[j] = current - currentCounter;
                currentCounter--;
            }
            sum+= result[j];
            current = result[j];
        }
        return sum;

    }

    public static int goUp(int current, int limit, int count)
    {
        if(current > limit) return count;
        else if(current==limit) return count+1;
        else{
            count = goUp(current+1,limit,count);
            count = goUp(current+2,limit,count);
            count = goUp(current+3,limit,count);
        }
        return count;
    }
    public static int stepPerms(int n) {
        int c = 0;
        goUp(0,n,0);
        return c;

    }

    public static long superDigit(String n) {
        long sum = 0;
        for(int i=0; i<n.length(); i++)
        {
            sum+=(Character.getNumericValue(n.charAt(i)));
        }
        return sum;

    }
    public static int superDigit(String n, int k) {
        long s = superDigit(n);
        s*=k;
        while(true)
        {
            String current = String.valueOf(s);
            if(current.length() == 1) return Integer.parseInt(current);
            s = superDigit(current);
        }
    }

    public static List<Integer> findClosestElements(int[] arr, int k, int x) {
        int index = IntStream.range(0, arr.length)
                .filter(i->arr[i]==x)
                .findFirst()
                .orElse(-1);
        int p1,p2;
        ArrayList<Integer> result = new ArrayList<>();
        if(index==-1) {
            List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());

            int c = list.stream()
                    .min(Comparator.comparingInt(i -> Math.abs(i - x))).get();
            index = IntStream.range(0, arr.length)
                    .filter(i->arr[i]==c)
                    .findFirst()
                    .orElse(-1);
            result.add(arr[index]);
        }
        else{
            result.add(x);
        }

        p1 = index-1;
        p2 = index+1;
        while(result.size()<k)
        {
            int a,b;
            if(p1>=0) a = arr[p1];
            else a = Integer.MIN_VALUE;
            if(p2<=arr.length-1) b = arr[p2];
            else b = Integer.MAX_VALUE;
            long abs1 = Math.abs((long) a-x);
            long abs2 = Math.abs((long) b-x);
            if(abs1==abs2){
                if(a<b) {
                    p1-=1;
                    result.add(a);
                }
                else {
                    p2+=1;
                    result.add(b);
                }
            }
            else{
                if(abs1<abs2) {
                    result.add(a);
                    p1-=1;
                }
                else {
                    result.add(b);
                    p2+=1;
                }
            }
        }
        Collections.sort(result);
        return result;
    }

    public static ArrayList<String> generateParenthesisHelper(int openCounter, int closedCounter, int n, String current, ArrayList<String> result) {
        if(openCounter == n && closedCounter == n)     {
            result.add(current);
            return result;
        }
        else if(closedCounter>openCounter) return result;
        else if(openCounter > n || closedCounter>n) return result;
        else{
            result = generateParenthesisHelper(openCounter+1,closedCounter,n,current+"(",result);
            result = generateParenthesisHelper(openCounter,closedCounter+1,n,current+")",result);
        }
        return result;
    }
    public static List<String> generateParenthesis(int n) {
        ArrayList<String> result = new ArrayList<>();
        result = generateParenthesisHelper(0,0,n,"",result);
        return result;
    }

//    public static int minFallingPathSum(int[][] matrix) {
//        int[][] dp = new int[matrix.length][matrix[0].length];
//        for(int i=0; i<matrix[0].length; i++)
//        {
//            dp[matrix.length-1][i] = matrix[matrix.length-1][i];
//        }
//        for(int j=matrix.length-2; j>=0; j--)
//        {
//            for(int i=0; i<matrix[0].length; i++)
//            {
//                if(i-1>=0 && i+1<matrix[0].length) dp[j][i] =  matrix[j][i] + Math.min(dp[j+1][i-1],Math.min(dp[j+1][i+1],dp[j+1][i]));
//                else if(i-1<0) dp[j][i] =  matrix[j][i] + Math.min(dp[j+1][i+1],dp[j+1][i]);
//                else dp[j][i] =  matrix[j][i] + Math.min(dp[j+1][i],dp[j+1][i-1]);
//            }
//        }
//return Arrays.stream(dp[0]).min().getAsInt();
//    }

    public static int minFallingPathSum(int[][] matrix) {
        int[][] dp = new int[matrix.length][matrix[0].length];
        for(int i=0; i<matrix[0].length; i++)
        {
            dp[matrix.length-1][i] = matrix[matrix.length-1][i];
        }
        for(int j=matrix.length-2; j>=0; j--)
        {
            for(int i=0; i<matrix[0].length; i++)
            {
                if(i-1>=0 && i+1<matrix[0].length) dp[j][i] =  matrix[j][i] + Math.min(dp[j+1][i-1],dp[j+1][i+1]);
                else if(i-1<0) dp[j][i] =  matrix[j][i] + dp[j+1][i+1];
                else dp[j][i] =  matrix[j][i] + dp[j+1][i-1];
            }
        }
        return Arrays.stream(dp[0]).min().getAsInt();
    }
    public static List<String> topKFrequent(String[] words, int k) {
        HashMap<String,Integer> frequencies = new HashMap<>();
        for(String word: words)
        {
            frequencies.putIfAbsent(word,0);
            frequencies.put(word,frequencies.get(word)+1);
        }

        Comparator<String> sortingByName = (s1, s2) -> {
            int e = frequencies.get(s1).compareTo(frequencies.get(s2));
            if(e==0) return s1.compareTo(s2);
            else return -e;
        };
        PriorityQueue pq = new PriorityQueue(sortingByName);
        pq.addAll(new HashSet<>(Arrays.asList(words)));
        List<String> result = new ArrayList<>();
        for(int i=0; i<k; i++)
        {
            result.add(pq.poll().toString());
        }
        return result;
    }

    public static class Subset {
        int parent;
        int rank;
    }
    static class UnionFind
    {
        Subset[] subsets;
        public Subset[] getSubsets() {
            return subsets;
        }

        UnionFind(int V)
        {
            subsets = new Subset[V];
            for (int v = 0; v < V; v++) {

                subsets[v] = new Subset();
                subsets[v].parent = v;
                subsets[v].rank = 0;
            }
        }


        // A utility function to find
        // set of an element i (uses
        // path compression technique)
        int find(Subset[] subsets, int i) {
            if (subsets[i].parent != i)
                subsets[i].parent
                        = find(subsets, subsets[i].parent);
            return subsets[i].parent;
        }

        // A function that does union
        // of two sets of x and y
        // (uses union by rank)
        int Union(Subset[] subsets, int x, int y) {
            int xroot = find(subsets, x);
            int yroot = find(subsets, y);
            if(xroot == yroot) return 0;
            if (subsets[xroot].rank < subsets[yroot].rank)
                subsets[xroot].parent = yroot;
            else if (subsets[yroot].rank < subsets[xroot].rank)
                subsets[yroot].parent = xroot;
            else {
                subsets[xroot].parent = yroot;
                subsets[yroot].rank++;
            }
            return 1;
        }
    }
    public static int[] findRedundantConnection(int[][] edges) {
        HashSet<Integer> vertices = new HashSet<>();
        for(int[] edge: edges)
        {

            vertices.add(edge[0]);
            vertices.add(edge[1]);
        }
        UnionFind uf = new UnionFind(vertices.size());
        for(int[] edge: edges)
        {
            int e = uf.Union(uf.subsets,edge[0]-1,edge[1]-1);
            if(e==0) return edge;

        }
        return null;
    }

    public static int climbStairs(int n) {
        if(n<=2) return n;
        int[] cache = new int[n+1];
        cache[0] = 0;
        cache[1] = 1;
        cache[2] = 2;
        int current = 3;
        for(int i=0; i<(n-2); i++)
        {
            cache[current+i] = cache[current-1+i] + cache[current-2+i];
        }
        return cache[n];
    }

    public boolean makeBricks(int small, int big, int goal) {
        return makeB(small,big,goal,0);
    }

    public boolean makeB(int small, int big, int goal, int current)
    {
        if(current == goal) return true;
        else if(current>goal) return false;
        if(small == 0) {
            if((goal-current)%5==0) return (goal - current) / 5 <= big;
        }
        else if(big == 0) return (goal-current) <= small;
        else{
            boolean a = makeB(small-1,big,goal,current+1);
            boolean b =  makeB(small,big-1,goal,current+5);
            return a || b;
        }
        return false;
    }

    public static long repeatedString(String s, long n) {
        long[] occurences = new long[s.length()];
        long count = 0;
        for(int i=0; i<s.length(); i++)
        {
            if(s.charAt(i) == 'a') occurences[i] = ++count;
            else occurences[i] = count;
        }
        long d = n / s.length();
        int r = Math.floorMod(n,s.length());
        long a = r > 0 ? occurences[r-1] : 0;
        return occurences[occurences.length-1] * d + a;
    }

    public static int[] sortArrayByParity(int[] nums) {
        LinkedHashMap<Integer,Integer> parity = new LinkedHashMap<>();
        HashMap<Integer,Integer> frequencies = new HashMap<>();
        for(Integer num: nums)
        {
            int e = num % 2 == 0 ? 1 : 0;
            parity.put(num,e);
            frequencies.putIfAbsent(num,0);
            frequencies.put(num,frequencies.get(num)+1);

        }
        LinkedHashMap<Integer,Integer> result = parity.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        int j=0;

        for(Integer k: result.keySet())
        {
            for(int i=0; i<frequencies.get(k); i++) nums[j++] = k;
        }
        return nums;
    }


    public List<AbstractMap.SimpleEntry<Integer,Integer>>  neighbours(AbstractMap.SimpleEntry<Integer,Integer> l, int[][] grid) {
        int x = l.getKey();
        int y = l.getValue();
        ArrayList<AbstractMap.SimpleEntry<Integer,Integer>> neighbours = new ArrayList<>(List.of(
                new AbstractMap.SimpleEntry<>(x-1,y),
                new AbstractMap.SimpleEntry<>(x+1,y),
                new AbstractMap.SimpleEntry<>(x,y-1),
                new AbstractMap.SimpleEntry<>(x,y+1),
                new AbstractMap.SimpleEntry<>(x-1,y-1),
                new AbstractMap.SimpleEntry<>(x-1,y+1),
                new AbstractMap.SimpleEntry<>(x+1,y-1),
                new AbstractMap.SimpleEntry<>(x+1,y+1)
        ));
        int xLength = grid.length;
        int yLength = grid[0].length;
        return neighbours.stream().filter(e -> e.getKey() >= 0 && e.getKey() < xLength && e.getValue() >= 0 && e.getValue() < yLength &&  grid[e.getKey()][e.getValue()] == 1).collect(Collectors.toList());
    }

    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        for(int i=0; i<amount+1; i++){
            dp[i] = Integer.MAX_VALUE;
        }
        dp[0] = 0;
        for(int i=1; i<amount+1; i++){
            for(Integer coin: coins)
            {
                if(coin <= i){
                    int e = dp[i-coin];
                    if(e==Integer.MAX_VALUE) continue;
                    dp[i] = Math.min(dp[i],e+1);
                }
            }
        }
        System.out.println(dp[amount]);
        return dp[amount] < Integer.MAX_VALUE ? dp[amount] : -1;
    }

    public static int sumNumbers(String str) {
        StringBuilder sb = new StringBuilder();
        int sum = 0;
        for(int i=0; i<str.length(); i++)
        {
            if(Character.isDigit(str.charAt(i)))
            {
                sb.append(str.charAt(i));
            }
            else
            {
                if(sb.length()==0) continue;
                sum+=(Integer.parseInt(sb.toString()));
                sb.setLength(0);

            }
        }
        if(sb.length()!=0) sum+=(Integer.parseInt(sb.toString()));
        return sum;
    }

    public static String collapseDuplicates(String a)
    {
        int i = 0;
        StringBuilder result = new StringBuilder();
        while (i < a.length())
        {
            char ch = a.charAt(i);
            result.append(ch);
            while (true)
            {
                if(i+1 < a.length()) {
                    if(a.charAt(i+1) == ch) i++;
                    else break;
                }
                else
                {
                    break;
                }
            }
            i++;

        }
        return result.toString();

    }

    static ArrayList<TreeNode> printLevelOrder(TreeNode root, HashMap<TreeNode,TreeNode> parent)
    {
        ArrayList<TreeNode> nodes = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);

        int i=0;
        while (!queue.isEmpty())
        {

            TreeNode tempNode = queue.poll();
            nodes.add(tempNode);
            /*Enqueue left child */
            if (tempNode.left != null) {
                parent.put(tempNode.left,tempNode);
                queue.add(tempNode.left);
            }

            /*Enqueue right child */
            if (tempNode.right != null) {
                parent.put(tempNode.right,tempNode);
                queue.add(tempNode.right);
            }
        }
        return nodes;
    }

      public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }
    public static int minCameraCover(TreeNode root) {
        HashMap<TreeNode,TreeNode> parent = new HashMap<>();
        ArrayList<TreeNode> result = printLevelOrder(root,parent);
        HashSet<TreeNode> watched = new HashSet<>();
        int cameras = 0;
        for(int j=1; j<result.size(); j++)
        {
            TreeNode node = result.get(j);
            if(watched.size() == result.size()) return cameras;
            if(watched.contains(node)) continue;
            cameras+=1;
            watched.add(parent.get(node));
            if(node!=null) {
                watched.add(node);
                if(node.left!=null) watched.add(node.left);
                if(node.right!=null) watched.add(node.right);
            }
        }
        return cameras;
    }

    public static int shortestPathLength(int[][] graph) {
        int len = graph.length;
        int expect = (1 << len) - 1; //This set all bits to 1.
        Queue<int[]> q = new LinkedList<>();    // int[] saves current node, visited states
        for(int i = 0; i < len; i++)
            q.offer(new int[]{i, 1 << i});  // We could start from any points.
        // if for current node and state, we visit it again will be dulplicate, we can continue.
        boolean[][] visited = new boolean[len][1 << len];
        int step = -1;
        while(!q.isEmpty()){
            int size = q.size();
            ++step;
            for(int i = 0; i < size; i++){
                int[] pair = q.poll();
                int node = pair[0];
                int state = pair[1];
                System.out.println(Integer.toBinaryString(state));
                // We've visited all of the nodes
                if(state == expect) return step;
                if(visited[node][state]) continue;
                visited[node][state] = true;
                for(int next : graph[node]){
                    q.offer(new int[]{next, state | (1 << next)});
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(shortestPathLength(new int[][]{{1,2,3},{0},{0},{0}}));
    }
}

