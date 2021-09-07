package HashTables;

import java.util.*;

public class HTMethods {

    public static int[] twoSum(int[] nums, int target) {

        HashMap<Integer, Integer> map = new HashMap<>();

        for(int i = 0; i < nums.length; i++){
            int num = nums[i];
            map.put(num, i);
        }

        int[] result = new int[2];

        for(int i = 0; i < nums.length; i++){
            int num = nums[i];
            int rest = target - num;

            if(map.containsKey(rest) && map.get(rest) != i){
                result[0] = i;
                result[1] = map.get(rest);
                break;
            }

        }

        return result;

    }

    public static int sherlockAndAnagrams(String s) {

        HashMap<String, Integer> map = new HashMap<>();

        for(int i = 1; i < s.length(); i++){
           for(int j = 0; j < s.length(); j ++){
               if(j + i <= s.length()){
                   char[] substring = s.substring(j, j + i).toCharArray();
                   Arrays.sort(substring);
                   String finalSubstring = Arrays.toString(substring);
                   if(map.containsKey(finalSubstring)){
                       int count = map.get(finalSubstring);
                       map.put(finalSubstring, count + 1);
                   }
                   else{
                       map.put(finalSubstring, 1);
                   }
               }
           }
        }

        int result = 0;

        for(int value : map.values()){
            result += value*(value - 1)/2;
        }

        return result;
    }

    public static final int ALPHABET_CNT = 26;

    static boolean isAnagrams(String s1, String s2) {

        char[] chCnt1 = new char[ALPHABET_CNT];
        char[] chCnt2 = new char[ALPHABET_CNT];


        for (int i = 0, n = s1.length(); i < n; i++) {
            chCnt1[s1.charAt(i) - 97] += 1;
            chCnt2[s2.charAt(i) - 97] += 1;
        }

        for (int i = 0; i < ALPHABET_CNT; i++) {
            if (chCnt1[i] != chCnt2[i]) {
                return false;
            }
        }

        return true;
    }

    // Complete the sherlockAndAnagrams function below.
    // Main  idea: Compute all substrings and then check whether each pair of substring (of same length) is an anagram or not, i.e. they contain the same characters.
    static int sherlockAndAnagramsSolution(String s) {
        int cnt = 0;
        for (int i = 1, n = s.length(); i < n; i++) {
            List<String> subsetList = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (i + j <= n) {
                    subsetList.add(s.substring(j, i + j));
                }
            }

            for (int k = 0, size = subsetList.size(); k < size; k++) {
                for (int l = k + 1; l < size; l++) {
                    if (isAnagrams(subsetList.get(k), subsetList.get(l))) {
                        cnt++;
                    }
                }
            }
        }


        return cnt;
    }

    // Subroutine of the count triplets
    static int countLessThan(List<Integer> arr, int n, int key)
    {
        int l = 0, r = n - 1;
        int index = -1;

        // Modified binary search
        while (l <= r)
        {
            int m = (l + r) / 2;

            if (arr.get(m) < key)
            {
                l = m + 1;
                index = m;
            }
            else
            {
                r = m - 1;
            }
        }

        return (index + 1);
    }

    // Function to return the count
    // of elements in arr[] which are
    // greater than the given key (subroutine of the count triplets)
    static int countGreaterThan(List<Integer> arr, int n, int key)
    {
        int l = 0, r = n - 1;
        int index = -1;

        // Modified binary search
        while (l <= r)
        {
            int m = (l + r) / 2;

            if (arr.get(m) <= key)
            {
                l = m + 1;
            }
            else
            {
                r = m - 1;
                index = m;
            }
        }

        if (index == -1)
            return 0;
        return (n - index);
    }

    // Count the triplets of 3 arrays such that a[i] < b[j] < c[k] using binary search.
    static long countSubTriplets(int n, List<Integer> a, List<Integer> b, List<Integer> c)
    {
        // // Sort all three arrays
        // Arrays.sort(a) ;
        // Arrays.sort(b);
        // Arrays.sort(c);

        long count = 0;

        // Iterate for all the elements of array B
        for (int i = 0; i < n; ++i)
        {
            int current = b.get(i);


            // Count of elements in A[]
            // which are less than the
            // chosen element from B[]
            int low = countLessThan(a, a.size(), current);

            // Count of elements in C[]
            // which are greater than the
            // chosen element from B[]
            int high = countGreaterThan(c, c.size(), current);

            // Update the count
            count += ((long) low * high);
        }

        return count;
    }

    // Count the triplets that follow the geometric progression with ratio r such that i < j < k
    static long countTriplets(List<Long> arr, long r) {

        HashMap<Long, List<Integer>> map = new HashMap<>();


        for(int i = 0; i < arr.size(); i++){
            long number = arr.get(i);
            if(map.containsKey(number)){
                List<Integer> indices = map.get(number);
                indices.add(i);
                map.put(number, indices);
            }
            else{
                ArrayList<Integer> indices = new ArrayList<>();
                indices.add(i);
                map.put(number, indices);
            }
        }

        long result = 0;

        for(long number : map.keySet()){
            long number2 = number*r;
            long number3 = number*r*r;
            if(map.containsKey(number2) && map.containsKey(number3)){
                List<Integer> indices0 = map.get(number);
                List<Integer> indices1 = map.get(number2);
                List<Integer> indices2 = map.get(number3);

                result += countSubTriplets(indices1.size(), indices0, indices1, indices2);
            }
        }

        return result;


    }



    static List<Integer> freqQuery(List<List<Integer>> queries) {

        List<Integer> result = new ArrayList<>();

        // Frequency to list of integers of this frequency
        HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
        // Integer to its frequency
        HashMap<Integer, Integer> map2 = new HashMap<>();

        HashSet<Integer> initialSet = new HashSet<>();
        map.put(1, initialSet);

        for(List<Integer> query : queries){
            int command = query.get(0);
            int target = query.get(1);
            if(command == 1){
                if(map2.containsKey(target)){
                    int count = map2.get(target);
                    map2.put(target, count + 1);
                    HashSet<Integer> preSet = map.get(count);
                    preSet.remove(target);
                    if(map.containsKey(count + 1)){
                        HashSet<Integer> newSet = map.get(count +1);
                        newSet.add(target);
                    }
                    else{
                        HashSet<Integer> newSet = new HashSet<>();
                        newSet.add(target);
                        map.put(count + 1, newSet);
                    }
                }
                else{
                    map2.put(target, 1);
                    initialSet.add(target);
                }

            }

            else if(command == 2){
                if(map2.containsKey(target)){
                    int count = map2.get(target);
                    HashSet<Integer> set = map.get(count);
                    HashSet<Integer> set2 = map.get(count - 1);
                    if(count -1 == 0){
                        map2.remove(target);
                        set.remove(target);
                    }
                    else{
                        map2.put(target, count -1);
                        set.remove(target);
                        set2.add(target);
                    }
                }
            }

            else{
                if(map.containsKey(target) && !map.get(target).isEmpty()){
                    result.add(1);
                }
                else{
                    result.add(0);
                }
            }

        }

        return result;


    }


    public static List<List<Integer>> fourSum(int[] nums, int target) {

        // Amount of Sum of 2 numbers to the set of couple of indices of the numbers that add up to this sum
        HashMap<Integer, HashSet<int[]>> map2 = new HashMap<>();
        // Set of all possible couples of integers in the nums list
        HashSet<int[]> twoSet = new HashSet<>();
        // Set of current results (that thus should not be repeated)
        HashSet<String> results = new HashSet<>();

        HashSet<String> visited = new HashSet<>();

        List<List<Integer>> result = new ArrayList<>();

        for(int i = 0; i < nums.length; i++){
            int num = nums[i];
            for(int j = i + 1; j < nums.length; j++){
                int num2 = nums[j];
                int sum = num + num2;
                int[] indices = {i,j};
                HashSet<int[]> set;

                if(map2.containsKey(sum)){
                    set = map2.get(sum);
                }
                else{
                    set = new HashSet<>();
                }
                set.add(indices);
                map2.put(sum, set);
                twoSet.add(indices);
            }
        }

        for(int[] indices : twoSet){
            int a = nums[indices[0]];
            int b = nums[indices[1]];
            int sum = a + b;
            String couple1 = "" + a + b;
            String couple2 = "" + b + a;
            int rest = target - sum;
            if(!visited.contains(couple1)){
                visited.add(couple1);
                visited.add(couple2);
            }
            else{
                continue;
            }
            if(map2.containsKey(rest)){
                HashSet<int[]> set = map2.get(rest);
                for(int[] indexes : set){
                    if(indexes[0] != indices[0] && indexes[0] != indices[1] && indexes[1] != indices[0] && indexes[1] != indices[1]){
                        int c = nums[indexes[0]];
                        int d = nums[indexes[1]];
                        List<Integer> list = new ArrayList<>();
                        int[] option = {a,b,c,d};
                        Arrays.sort(option);
                        StringBuilder ok = new StringBuilder();
                        for(int number : option){
                            list.add(number);
                            ok.append(number);
                        }
                        if(!results.contains(ok.toString())){
                            result.add(list);
                            results.add(ok.toString());
                        }


                    }

                }
            }

        }

        return result;

    }

// This method is flawed !!!
    public List<List<Integer>> threeSum(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();

        if(nums.length < 3){
            return result;
        }

        Arrays.sort(nums);

        // Amount of Sum of 2 numbers to the set of couple of indices of the numbers that add up to this sum
        HashMap<Integer, HashSet<int[]>> map2 = new HashMap<>();
        // Set of all possible couples of integers in the nums list
        HashSet<int[]> twoSet = new HashSet<>();
        // Set of current results (that thus should not be repeated)
        HashSet<String> results = new HashSet<>();

        HashSet<Integer> visited = new HashSet<>();

        for(int i = 0; i < nums.length; i++){
            int num = nums[i];
            for(int j = i + 1; j < nums.length; j++){
                int num2 = nums[j];
                int sum = num + num2;
                int[] indices = {i,j};
                HashSet<int[]> set;

                if(map2.containsKey(sum)){
                    set = map2.get(sum);
                }
                else{
                    set = new HashSet<>();
                }
                set.add(indices);
                map2.put(sum, set);
                twoSet.add(indices);
            }
        }

        for(int k = 0; k< nums.length; k++){
            int current = nums[k];
            int rest = -current;
            if(current > 0 || visited.contains(rest)){
                break;
            }
            else{
                visited.add(rest);
                if(map2.containsKey(rest)){
                    HashSet<int[]> set = map2.get(rest);
                    for(int[] indices : set){
                        if(!(indices[0] == k) && !(indices[1] == k)){
                            List<Integer> list = new ArrayList<>();
                            list.add(nums[indices[0]]);
                            list.add(nums[indices[1]]);
                            list.add(nums[k]);
                            Collections.sort(list);

                            StringBuilder ok = new StringBuilder();
                            for(int number : list){
                                ok.append(number);
                            }
                            if(!results.contains(ok.toString())){
                                result.add(list);
                                results.add(ok.toString());
                            }
                        }

                    }
                }
            }
        }

        return result;

    }


    public static void main(String[] args) {
        char a = 'A';
        System.out.println(Character.getNumericValue(a));
        System.out.println(Character.getNumericValue('a'));
    }
}
