package Others;

import java.util.Arrays;

public class InterviewQuestions {

    private static String reverseString(String s){
        // Use .reverse method of StringBuilder or use a for loop or a while loop
        return "";

    }

    private static String recursiveReverse(String orig) {
        //Recursive solution for reversing a String
        if (orig.length() == 1)
            return orig;
        else
            return orig.charAt(orig.length() - 1) +
                    recursiveReverse(orig.substring(0, orig.length() - 1));

    }

    private static boolean isAnagram(String a, String b){
        // two strings are anagrams if they have the same characters but in different order
        // Add the characters of the 2 strings into the same array and then use the Array.sort
        // Use a HashSet -> Not shown
        char[] array;
        String ab = a+b;
        array = ab.toCharArray();
        Arrays.sort(array);
        boolean isDifferent = false;
        int i = 0;
        int j = 1;
        while(!isDifferent && j < array.length){
            if(array[i] != array[j]){
                isDifferent = true;
            }
            i+=2;
            j+=2;
        }
        return !isDifferent;

    }

    private static boolean isAllUnique(String s){
        // Are all characters in the string unique
        // Use HashSet
        // Use the both indexOf() and lastIndexOf() methods. If they both return the same index, then the character is unique
        // Use ascii value, cf below
        return true;

    }

    private static boolean hasAllUniqueChars (String word) {
        // Most efficient method of all.
        boolean[] charMap = new boolean[26];

        for(int index=0;index < word.length(); index ++)   {
            // we are substracting char's ascii value to 64, so we get all index
            // from 0 to 25.
            int asciiCode = (int) word.toUpperCase().charAt(index) - 64;

            // If char is not present, it should have false at that index
            if(!charMap[asciiCode])
                charMap[asciiCode] = true;
            else
                return false;
        }

        return true;
    }

    private static int findDuplicates(){
        // use a HashMap<Character, count>
        // Use a HashSet with an Array or an ArrayList
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(isAnagram("Angl", "Angle"));
    }


}
