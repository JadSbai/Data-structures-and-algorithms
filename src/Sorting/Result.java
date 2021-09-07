package Sorting;

import java.util.*;

class Result {

    public static String abbreviation(String a, String b) {

        HashMap<Integer, Integer> map = new HashMap<>();
        // HashMap<Character, Integer> map2 = new HashMap<>();
        HashSet<Character> set = new HashSet<>();
        ArrayList<Character> list = new ArrayList<>();

        char[] aWord = a.toCharArray();
        char[] bWord = b.toCharArray();
        int count = 1;
        for(int i = 0; i < aWord.length; i++){
            if(!Character.isLowerCase(aWord[i])){
                list.add(aWord[i]);
                map.put(count, i);
                // int previous = map2.get(aWord[i]) != null ? map2.get(aWord[i]): 2000;
                // map2.put(aWord[i], Math.min(count, previous));
                count++;
            }
        }

        for(char letter : bWord){
            set.add(letter);
        }

        for (char current : list) {
            if (!set.contains(current)) {
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

}

