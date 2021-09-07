package Sorting;

import java.util.*;

public class SortingMethods {

    static class Player{
        int score;
        String name;
    }


    static class Checker implements Comparator<Player> {
        // complete this method
        public int compare(Player a, Player b) {
            if(a.score> b.score){
                return -1;
            }
            else if (b.score > a.score){
                return 1;
            }

            boolean equalNames = a.name.equals(b.name);
            if(!equalNames){
                String[] names = {a.name, b.name};
                Arrays.sort(names);
                boolean order = names[0].equals(a.name);

                if(order){
                    return -1;
                }
                else {
                    return 1;
                }
            }

            else{
                return 0;
            }




        }
    }

    public static int maximumToys(List<Integer> prices, int k) {
        int[] array = new int[prices.size()];
        int i = 0;
        for(int price : prices){
            array[i] = price;
            i++;
        }

        Arrays.sort(array);
        int j =0;
        int count = 0;
        int current = k;
        while(current >= array[j]){
            current -= array[j];
            count ++;
            j++;
        }

        return count;

    }

    //Bubble sort
    public static void countSwaps(List<Integer> a) {

        int count = 0;
        int size = a.size();
        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size - 1; j++) {
                // Swap adjacent elements if they are in decreasing order
                if (a.get(j) > a.get(j+1)) {
                    int temp = a.get(j);
                    a.set(j, a.get(j+1));
                    a.set(j+1, temp);
                    count ++;
                }
            }
        }
        System.out.println("Array is sorted in " + count + " swaps.");
        System.out.println("First Element: " + a.get(0));
        System.out.println("Last Element: " + a.get(size - 1));
    }

    // Method that returns the array of the cumulative median of a list of integers
    private static double[] getMedians(List<Integer> expenses){
        // This is a max heap, inversed the standard return value of the comparator.
        PriorityQueue<Integer> lowers = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -1 * o1.compareTo(o2);
            }
        });
        // This is a classic min heap
        PriorityQueue<Integer> highers = new PriorityQueue<>();
        double[] medians = new double[expenses.size()];
        for(int i = 0; i < expenses.size(); i++){
            int number = expenses.get(i);
            addNumber(lowers, highers, number);
            rebalance(lowers, highers);
            medians[i] = getMedian(lowers, highers);
        }

        return medians;

    }
    //subroutine 0f getMedians
    private static double getMedian(PriorityQueue<Integer> lowers, PriorityQueue<Integer> highers) {
        PriorityQueue<Integer> biggerHeap = lowers.size() > highers.size() ? lowers : highers;
        PriorityQueue<Integer> smallerHeap = lowers.size() > highers.size() ? highers : lowers;

        if(biggerHeap.size() == smallerHeap.size()){
            return ((double) biggerHeap.peek() + smallerHeap.peek())/2;

        }
        else{
            return biggerHeap.peek();
        }
    }
    //subroutine 0f getMedians (rebalancing the heaps)
    private static void rebalance(PriorityQueue<Integer> lowers, PriorityQueue<Integer> highers) {
        PriorityQueue<Integer> biggerHeap = lowers.size() > highers.size() ? lowers : highers;
        PriorityQueue<Integer> smallerHeap = lowers.size() > highers.size() ? highers : lowers;

        if(biggerHeap.size() - smallerHeap.size() >= 2){
            smallerHeap.add(biggerHeap.poll()); // lower's max or higher's minimum
        }

    }

    //subroutine 0f getMedians
    private static void addNumber(PriorityQueue<Integer> lowers, PriorityQueue<Integer> highers, int number) {
        if(lowers.size() == 0 || number < lowers.peek()){
            lowers.add(number);

        }
        else{
            highers.add(number);
        }
    }

    // Method for finding the running median using heaps.
    static int activityNotifications(List<Integer> expenditure, int d) {
        int[] countData = new int[201];

        for (int i = 0; i < d; i++) {
            countData[expenditure.get(i)]++;
        }

        int answer = 0;

        for (int i = d; i < expenditure.size(); i++) {
            int today = expenditure.get(i);
            double preAvg = getAverage(countData, d);

            if ((double) today >= preAvg * (double) 2) {
                answer++;
            }

            countData[today]++;
            countData[expenditure.get(i-d)]--;
        }
        return answer;
    }

    static double getAverage(int[] countData, int d) {
        if (d % 2 == 0) {
            int targetNTh1 = d / 2;

            int cnt = 0;
            for (int i = 0; i < countData.length; i++) {
                cnt += countData[i];

                if (cnt > targetNTh1) {
                    return (double) i;
                }
                else if (cnt == targetNTh1) {
                    for (int j = i + 1; j < countData.length; j++) {
                        if (countData[j] > 0) {
                            return (double) (i + j) / (double) 2;
                        }
                    }
                }
            }
        }
        else {
            int targetNTh = d / 2 + 1;

            int cnt = 0;
            for (int i = 0; i < countData.length; i++) {
                cnt += countData[i];
                if (cnt >= targetNTh) {
                    return (double) i;
                }
            }
        }

        return -1;
    }

    // Recursively count the number of inversions for sorting an array using merge sort
    public static long countInversions(int[] a){
        int n = a.length;

        // Base Case
        if(n <= 1) {
            return 0;
        }

        // Recursive Case
        int mid = n >> 1;
        int[] left = Arrays.copyOfRange(a, 0, mid);
        int[] right = Arrays.copyOfRange(a, mid, a.length);
        long inversions = countInversions(left) + countInversions(right);

        int range = n - mid;
        int iLeft = 0;
        int iRight = 0;
        for(int i = 0; i < n; i++) {
            if(
                    iLeft < mid
                            && (
                            iRight >= range || left[iLeft] <= right[iRight]
                    )
            ) {
                a[i] = left[iLeft++];
                inversions += iRight;
            }
            else if(iRight < range) {
                a[i] = right[iRight++];
            }
        }

        return inversions;
    }

    public static List<Integer> majorityElement(int[] nums) {
        List<Integer> list = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();
        Arrays.sort(nums);
        int current = nums[0];
        int count = 0;
        int target = (int)Math.floor(nums.length/3);
        for(int num : nums){
            if(num == current){
                count++;
                if(count > target && !set.contains(current)){
                    set.add(current);
                    list.add(current);
                }
            }
            else{
                current = num;
                count = 1;
            }

        }

        return list;

    }


    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        int i = 0, j = 0, n = nums.length, k = n - 1;
        if (k < 2 || nums[k] < 0) {
            return res;
        }
        while (i < n - 2) {
            if (nums[i] > 0) {
                break;
            }
            int target = -nums[i];
            j = i + 1;
            k = n - 1;
            while (j < k) {
                if (nums[k] < 0) {
                    break;
                }
                if (nums[j] + nums[k] == target) {
                    res.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    while (j < k && nums[j] == nums[++j]) ;
                    while (j < k && nums[k] == nums[--k]) ;
                } else if (nums[j] + nums[k] > target) {
                    k--;
                } else {
                    j++;
                }
            }
            while (i < n - 2 && nums[i] == nums[++i]) ;
        }
        return res;
    }

    // Kadane's method -> O(n) -> Main idea: local_maximum at index i is the maximum of A[i] and the sum of A[i] and local_maximum at index i-1.
    public int maxSubArray(int[] nums) {
        int localMax = 0;
        int globalMax = Integer.MIN_VALUE;

        for (int num : nums) {
            localMax = Math.max(num, num + localMax);
            if (localMax > globalMax) {
                globalMax = localMax;
            }
        }
        return globalMax;

    }

    static int helper(int i,int j, int[] nums)
    {
        if(i==j) return nums[i];
        int left_cross=Integer.MIN_VALUE, right_cross=Integer.MIN_VALUE;

        int mid= (i+j)/2;
        int cur=0;
        for(int k=mid+1;k<=j;k++)
        {
            cur+= nums[k];
            right_cross= Math.max(right_cross,cur);
        }

        cur=0;
        for(int k=mid;k>=i;k--)
        {
            cur+= nums[k];
            left_cross= Math.max(left_cross,cur);
        }

        // Choose maximum between left array, right array and merging both arrays !!!
        int cross_sum=  left_cross + right_cross;
        int left_sum = helper(i,mid,nums);
        int right_sum = helper(mid+1,j,nums);

        return Math.max( cross_sum , Math.max(left_sum , right_sum) );
    }
    // Divide and conquer version of the maxSubArray method -> O(nlogn)
    public static int maxSubArray2(int[] nums) {
        return helper(0,nums.length-1,nums);
    }

    // Given an array of integers, find the subset of non-adjacent elements with the maximum sum. Calculate the sum of that subset.
    static int maxSubsetSum(int[] arr) {
        if(arr==null || arr.length==0)
            return 0;
        int n=arr.length;
        if(n==1)
            return arr[0];
        if(n==2)  //Just for clarification  not really need that
            return  Math.max(arr[0],arr[1]);
        //will hold all max till the i-th location
        int[] g=new int[n];
        int currMax = Math.max(arr[0],arr[1]);
        g[0]=arr[0];
        g[1]=currMax;
        for (int i = 2; i < arr.length; i++) {
            currMax =  Math.max(g[i-2] + arr[i], currMax);
            currMax = Math.max(arr[i], currMax);
            g[i]=currMax;

        }
        return g[n-1];
        // return IntStream.of(g).max().getAsInt();
    }

    public static long candies(int n, List<Integer> arr) {

        int previousScore = 0;
        int previousReward = 0;
        long candies = 0;

        for(int i = 0; i < n; i++){
            int score = arr.get(i);
            int nextScore = i < n-1 ? arr.get(i+1) : 0;
            if(score > previousScore){
                previousReward ++;
                candies+= previousReward;
                previousScore = score;
            }
            else{
                if(nextScore != 0 && nextScore < score){
                    previousReward = 2;
                }
                else{
                    previousReward = 1;
                }
                candies += previousReward;
                previousScore = score;
            }
        }

        return candies;
    }

    public static String abbreviation(String a, String b) {

        HashMap<Integer, Integer> map = new HashMap<>();
         HashMap<Character, Integer> map2 = new HashMap<>();
        HashMap<Character, Integer> map3 = new HashMap<>();
        HashSet<Character> set = new HashSet<>();
        ArrayList<Character> list = new ArrayList<>();

        char[] aWord = a.toCharArray();
        char[] bWord = b.toCharArray();
        int count = 1;
        for(int i = 0; i < aWord.length; i++){
            if(!Character.isLowerCase(aWord[i])){
                list.add(aWord[i]);
                map.put(count, i);
                map2.putIfAbsent(aWord[i], 1);
                map2.put(aWord[i], map2.get(aWord[i]) + 1);
                // int previous = map2.get(aWord[i]) != null ? map2.get(aWord[i]): 2000;
                // map2.put(aWord[i], Math.min(count, previous));
                count++;
            }
        }

        for(char letter : bWord){
            set.add(letter);
            map3.putIfAbsent(letter, 1);
            map3.put(letter, map3.get(letter) + 1);
        }

        for (char current : list) {
            if (!set.contains(current) || (!map2.get(current).equals(map3.get(current)))) {
                return "NO";
            }
        }

        int counter = 0;
        int[] boundaries = {0,aWord.length - 1};
        for(char letter : bWord){
            if(counter < list.size() && !(list.get(counter).equals(letter))){
                int h = boundaries[0];
                boolean found = false;
                while(h < boundaries[1] && !found){
                    if(aWord[h] == Character.toLowerCase(letter)){
                        boundaries[0] = h + 1;
                        found = true;
                    }
                    h++;
                }
                if(!found) return "NO";
            }
            else if(counter >= list.size()){
                int h = boundaries[0];
                boolean found = false;
                while(h < boundaries[1] && !found){
                    if(aWord[h] == Character.toLowerCase(letter)){
                        boundaries[0] = h + 1;
                        found = true;
                    }
                    h++;
                }
                if(!found) return "NO";
            }
            else{
                counter ++;
                if(map.containsKey(counter)) boundaries[0]= map.get(counter) + 1;
                if(map.containsKey(counter + 1)) boundaries[1] = map.get(counter + 1);
            }


        }

        return "YES";



    }

    public static void main(String[] args) {
        String a = "MBQEVZPBjcbswirgrmkkfvfvcpiukuxlnxkkenqp";
        String b = "MBQEVZP";
        System.out.println(abbreviation(a,b));
    }
}
