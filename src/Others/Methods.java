
import java.util.*;
import java.util.stream.IntStream;

public class Methods {


    public static int alternate(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        HashSet<Character> set = new HashSet<>();
        for(int i = 0; i < s.length(); i++){
            Integer current = map.putIfAbsent(s.charAt(i), 1);
            if(current != null){
                map.put(s.charAt(i), current +1);
            }
            if( i < s.length() -1 && s.charAt(i) == s.charAt(i+1)){
                set.add(s.charAt(i));
            }
        }

        LinkedList<Character> linkedList = new LinkedList<>();
        char[] array = new char[map.size()];
        int cur = 0;
        HashSet<Character> done = new HashSet<>();
        array[0] = s.charAt(0);
        done.add(s.charAt(0));
        for(Character character : map.keySet()){
            if(set.contains(character) || done.contains(character)) continue;

            if(map.get(character) <= map.get(array[cur])){
                array[cur + 1] = character;
                done.add(character);
                cur ++;
            }
            else{
                array[cur +1] = array[cur];
                array[cur] = character;
                done.add(character);
                cur++;
            }
        }


        boolean valid = false;
        boolean notFound = false;
        int size = 0;
        for(int i = 0; i < array.length; i ++){
            for(int j = i+1; j < array.length; j++){
                char first = array[i];
                char second = array[j];

                for(char character : s.toCharArray()){
                    valid = character == first || character == second;

                    if(linkedList.isEmpty() && valid){
                        linkedList.addLast(character);
                        continue;
                    }

                    if(valid && linkedList.peekLast() != character){
                        linkedList.addLast(character);
                    }
                    else if (valid && linkedList.peekLast() == character){
                        notFound = true;
                        break;
                    }
                }

                if(!notFound) size = Math.max(size, linkedList.size());

            }
        }

        return size;

    }

    public static int alternateSolution(String s) {



        int[] nums=s.chars().distinct().toArray();
        int maxLength=0;
        for(int i=0; i<nums.length; i++){
            for(int j=i+1; j<nums.length; j++){
                int a=nums[i]; int b=nums[j];
                int [] removed=s.chars().filter(c->c==a||c==b).toArray();
                boolean isAlt = removed[0]!=removed[1] && IntStream.range(2,removed.length).allMatch(k->removed[k]==removed[k%2]);
                if(isAlt && removed.length>maxLength)
                    maxLength=removed.length;
            }
        }

        return maxLength;
    }



    // This version consumes too much time complexity
    public static List<String> weightedUniformStrings(String s, List<Integer> queries) {
        HashSet<Character> set = new HashSet<>();
        int counter = 0;
        int biggest = 1;
        char previous = s.charAt(0);
        for(char character : s.toCharArray()){
            set.add(character);
            if(previous == character){
                counter ++;
            }
            else{
                biggest = Math.max(biggest, counter);
                counter = 1;
            }
            previous = character;
        }

        HashSet<Integer> weights = new HashSet<>();
        for(char character : set){

            for(int i = 1; i<= biggest; i++){
                int count = 0;
                char prev = s.charAt(0);
                for(char current : s.toCharArray()){
                    if(current != character) continue;
                    if(prev == current){
                        count ++;
                    }
                    else{
                        count = 1;
                    }

                    prev = current;

                    if(count == i){
                        int value = character - 96;
                        int weight = value * i;
                        weights.add(weight);
                        break;
                    }
                }
            }
        }

        List<String> list = new ArrayList<>();
        for(int query : queries){
            if(weights.contains(query)){
                list.add("Yes");
            }
            else{
                list.add("No");
            }
        }

        return list;


    }


    // Improved version of the above method
    public static List<String> weightedUniformStringsBetter(String s, List<Integer> queries) {

        HashMap<Character, HashSet<Integer>> map = new HashMap<>();
        char previous = s.charAt(0);
        int count = 0;
        for(char character : s.toCharArray()){
            if(previous == character){
                count++;
                if(map.get(character) == null){
                    HashSet<Integer> newset = new HashSet<>();
                    newset.add(count);
                    map.put(character, newset);
                }
                else{
                    HashSet<Integer> set = map.get(character);
                    set.add(count);
                    map.put(character, set);
                }
            }
            else{
                count = 1;
                if(!map.containsKey(character)){
                    HashSet<Integer> newset = new HashSet<>();
                    newset.add(count);
                    map.put(character, newset);
                }

            }

            previous = character;
        }

        HashSet<Integer> weights = new HashSet<>();
        for(char letter : map.keySet()){
            HashSet<Integer> sizes = map.get(letter);
            int value = letter - 96;
            int weight;
            for(int size : sizes){
                weight = value * size;
                weights.add(weight);
            }
        }


        List<String> list = new ArrayList<>();
        for(int query : queries){
            if(weights.contains(query)){
                list.add("Yes");
            }
            else{
                list.add("No");
            }
        }

        return list;


    }


    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(6);
        list.add(1);
        list.add(3);
        list.add(12);
        list.add(5);
        list.add(9);
        list.add(10);

        System.out.println(weightedUniformStringsBetter("abccddde", list));
    }

}
