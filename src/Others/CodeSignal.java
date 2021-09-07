package Others;

import java.util.*;
import java.util.stream.IntStream;

public class CodeSignal {

    public static  int firstDuplicate(int[] a) {
        HashSet<Integer> set = new HashSet<>();
        for(int i = 0; i < a.length; i++){
            if(!set.add(a[i])){
                return a[i];
            }
        }
        return -1;
    }

    public static char firstNotRepeatingCharacter(String s) {
        HashSet<Character> set = new HashSet<>();
        ArrayList<Character> list = new ArrayList<>();
        for(char c: s.toCharArray()){
            if(!set.add(c)){
                list.remove((Character)c);
            }
            else{
                list.add(c);
            }
        }
        if(!list.isEmpty()){
            return list.get(0);

        }
        else{
            return '_';
        }
    }

    private static int[][] rotateClockWise(int[][] matrix) {
        int size = matrix.length;
        int[][] ret = new int[size][size];

        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j)
                ret[i][j] = matrix[size - j - 1][i]; //***

        return ret;
    }

    public static void areDelimitersMatched(String input1) {
        String input = input1.replaceAll("\\s", "");
        int length = input.length();
        char[] array = input.toCharArray();
        Character[] arrayOfOpeningSymbols = {'{', '(', '['};
        Character[] arrayOfClosingSymbols = {'}', ')', ']'};
        List<Character> list1 = Arrays.asList(arrayOfOpeningSymbols);
        List<Character> list2 = Arrays.asList(arrayOfClosingSymbols);
        HashMap<Character, String> typeOfParenthesis = new HashMap<>();
        typeOfParenthesis.put('{', "curly");
        typeOfParenthesis.put('}', "curly");
        typeOfParenthesis.put('(', "round");
        typeOfParenthesis.put(')', "round");
        typeOfParenthesis.put('[', "square");
        typeOfParenthesis.put(']', "square");


        Stack<Character> S = new Stack<>();

        for (int i = 0; i < length; i++) {
            if (list1.contains(array[i])) {
                S.push(array[i]);
            } else if (list2.contains(array[i])) {
                if (S.isEmpty()) {
                    System.out.println("False");
                    return;
                }

                if (!typeOfParenthesis.get(S.pop()).equals(typeOfParenthesis.get(array[i]))) {
                    System.out.println("False");
                    return;
                }
            }
        }
        if (S.isEmpty()) {
            System.out.println("True");
        } else {
            System.out.println("False");
        }
    }



    private static char[][] get2DSubArray(char[][] origArray, int fromRow, int toRow, int fromColumn, int toColumn) {
        char[][] subArray = new char[toColumn - fromColumn][];

        for (int i = fromColumn; i < toColumn; i++) {
            subArray[i - fromColumn] = Arrays.copyOfRange(origArray[i], fromRow, toRow);
        }

        return subArray;
    }

    private static boolean sudoku2(char[][] grid) {
        HashSet<Character> set = new HashSet<>();

        // Check all lines
        for (char[] row : grid) {
            set.clear();
            for (char c : row) {
                if (c != '.' && !set.add(c)) {
                    return false;
                }
            }
        }

        set.clear();

        // Check all columns
        for(int i = 0; i < grid.length; i++){
            int[] range = IntStream.rangeClosed(0, grid.length - 1).toArray();
            char[] column = new char[grid.length];
            for(int number: range){
                column[number] = grid[number][i];
            }
            set.clear();
            for (char c : column) {
                if (c != '.' && !set.add(c)) {
                    return false;
                }
            }
        }

        set.clear();

        // Check all subarrays
        for(int i = 0; i < grid.length; i+= 3){
            for(int k = 0; k < grid.length; k+=3){
                char[][] subArray = get2DSubArray(grid, i, i+2, k, k+2);
                set.clear();
                for (char[] chars : subArray) {
                    for (int l = 0; l < subArray.length; l++) {
                        if (chars[l] != '.' && !set.add(chars[l])) {
                            return false;
                        }
                    }

                }

            }

        }

        return true;
    }

    String amendTheSentence(String s) {
        StringBuilder sb = new StringBuilder();
        for(char c: s.toCharArray()){
            if(Character.isUpperCase(c)){
                sb.append(' ');
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString().trim();

    }
//    Classify strings in python:
//
//    def classifyStrings(s):
//            if re.findall('(([aeiou]{3})|[^aeiou?]{5})',s):
//            return 'bad'
//            if '?' in s:
//    a = classifyStrings(s.replace('?','a',1))
//    b = classifyStrings(s.replace('?','n',1))
//            return a if a == b else 'mixed'
//            return 'good'



    public static void main(String[] args) {
        char[][] grid = {
                {'.','4','5','.','.','.','.','.','.'},
                {'.','.','.','.','.','.','.','.','.'},
                {'.','.','.','1','.','.','7','.','.'},
                {'.','.','.','.','.','.','.','.','.'},
                {'.','.','.','.','.','.','.','6','.'},
                {'.','.','.','.','6','.','.','9','.'},
                {'.','.','.','3','.','.','.','.','6'},
                {'.','.','.','.','3','.','2','.','.'},
                {'.','.','.','8','.','.','.','.','.'}
        };
        System.out.println(sudoku2(grid));
    }
}

