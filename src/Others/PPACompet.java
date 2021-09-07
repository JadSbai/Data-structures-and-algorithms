package Others;

import java.lang.*;
import java.lang.reflect.Array;
import java.util.*;
import java.lang.Math;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.*;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PPACompet {

    /*
     * Complete the 'alterCase' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING word as parameter.
     */

    public static String alterCase(String word) {
        String actualWord = word.trim();
        char firstCharacter = Character.toUpperCase(actualWord.charAt(0));
        StringBuilder newWord = new StringBuilder(Character.toString(firstCharacter));
        boolean isNextCharacterLowerCase = true;
        boolean isEmpty = false;
        for (int index = 1; index < actualWord.length(); index++) {
            Character currentChar = actualWord.charAt(index);
            if (currentChar.toString().equals(" ")) {
                isEmpty = true;
                newWord.append(currentChar);
            }
            if (!isNextCharacterLowerCase && !isEmpty) {
                newWord.append(Character.toUpperCase(currentChar));
                isNextCharacterLowerCase = true;

            } else if (isNextCharacterLowerCase && !isEmpty) {
                newWord.append(Character.toLowerCase(currentChar));
                isNextCharacterLowerCase = false;
            }
            isEmpty = false;


        }
        return newWord.toString();
    }


    public static boolean isCancelled(List<Integer> arr, int k) {
        int numberOfStudentsOnTime = 0;
        for (int arrivalTime : arr) {
            if (arrivalTime <= 0) {
                numberOfStudentsOnTime++;
            }
        }
        return numberOfStudentsOnTime < k;
    }


    public static String encryption(String s) {

        s = s.trim();
        s = s.replaceAll("\\s", "");
        int length = s.length();

        double squareRootLength = Math.sqrt(length);
        double min = Math.floor(squareRootLength);
        double max = Math.ceil(squareRootLength);

        int rows = (int) min;
        int columns = (int) min;

        int product = rows * columns;
        while (product < length) {
            columns++;
            if (columns > max) {
                columns--;
                product = rows * columns;
                break;
            }
            product = rows * columns;

        }
        while (product < length && rows <= columns) {
            rows++;
            product = rows * columns;
        }


        char[][] grid = new char[rows][columns];

        int index = 0;
        int i = 0;
        while (i < rows && index < length) {
            int j = 0;
            while (j < columns && index < length) {
                grid[i][j] = s.charAt(index);
                j++;
                index++;
            }
            i++;
        }


        StringBuilder encryptedString = new StringBuilder();

        for (int j = 0; j < columns; j++) {
            encryptedString.append(" ");
            for (int k = 0; k < rows; k++) {
                if (Character.isLetter(grid[k][j])) {
                    encryptedString.append(grid[k][j]);
                }
            }
        }
        return encryptedString.toString().trim();
    }

    public static int romanToInt(String roman) {
        HashMap<String, Integer> romanMap = new HashMap<>();
        romanMap.put("I", 1);
        romanMap.put("V", 5);
        romanMap.put("X", 10);
        romanMap.put("L", 50);
        romanMap.put("C", 100);
        romanMap.put("D", 500);
        romanMap.put("M", 1000);
        romanMap.put(" ", 0);
        String trueRoman = roman.replaceAll("\\s", "");
        int length = trueRoman.length();
        int currentSum = 0;
        if (length % 2 != 0) {
            trueRoman += " ";
            length = trueRoman.length();
        }
        String temp;
        int j = 0;
        for (int i = 0; i < length / 2; i ++) {
            temp = trueRoman.substring(j, j + 2);
            switch (temp) {
                case "IV":
                    currentSum += 4;
                    break;
                case "IX":
                    currentSum += 9;
                    break;
                case "XL":
                    currentSum += 40;
                    break;
                case "XC":
                    currentSum += 90;
                    break;
                case "CD":
                    currentSum += 400;
                    break;
                case "CM":
                    currentSum += 900;
                    break;
                default:
                    currentSum += romanMap.get(Character.toString(temp.charAt(0))) + romanMap.get(Character.toString(temp.charAt(1)));
                    break;
            }
            j += 2;
        }
        return currentSum;
    }


    public static int numberOnes(int n) {
        String currentNumber;
        int numberOfOnes = 0;
        char one = '1';
        int count;
        for(int z = 1; z <= n; z++){
            currentNumber = String.valueOf(z);
            count = 0;
            for(int i=0; i < currentNumber.length(); i++)
            {    if(currentNumber.charAt(i) == one)
                count++;
            }
            numberOfOnes += count;

        }
        return numberOfOnes;
    }


    public static String progression(List<Integer> arr) {

        int size = arr.size();
        boolean isArithmetic = true;
        boolean isGeometric = true;

        int[] commonDifferences = new int[size -1];
        int commonDifference;
        int index = 0;
        int number1;
        int number2;
        while(isArithmetic && index + 1 < size){
            number1 = arr.get(index);
            number2 = arr.get(index + 1);
            commonDifference = number1 - number2;
            commonDifferences[index] = commonDifference;
            if(index > 0 && (commonDifferences[index - 1] != commonDifferences[index])){
                isArithmetic = false;
            }
            index ++;
        }

        double[] commonRatios = new double[size -1];
        double commonRatio;
        int indexBis = 0;
        int number3;
        int number4;
        while(!isArithmetic && isGeometric && indexBis + 1 < size){
            number3 = arr.get(indexBis);
            number4 = arr.get(indexBis + 1);
            commonRatio = number4/number3;
            commonRatios[indexBis] = commonRatio;
            if(indexBis > 0 && (commonRatios[indexBis - 1] != commonRatios[indexBis])){
                isGeometric = false;
            }
            indexBis ++;
        }

        if(isArithmetic){
            return "arithmetic";
        }
        else if(isGeometric){
            return "geometric";
        }
        else{
            return "neither";
        }
    }

    public static int thirdLargest(List<Integer> arr) {
        int size = arr.size();

        if(size == 1){
            return arr.get(0);
        }

        if(size == 2){
            if (arr.get(0) > arr.get(1))
                return arr.get(0);
            else return arr.get(1);
        }

        int currentMax = 0;
        int currentSecondMax = 0;
        int currentThirdMax = 0;
        int temp;
        int temp2;
        boolean greaterThanMax;
        boolean greaterThanSecondMax;
        boolean greaterThanThirdMax;
        ArrayList<Integer> distinctValues = new ArrayList<>();
        for(int currentNumber : arr) {
            if (!distinctValues.contains(currentNumber)) {
                distinctValues.add(currentNumber);
                greaterThanMax = currentNumber > currentMax;
                if (greaterThanMax) {
                    temp = currentMax;
                    temp2 = currentSecondMax;
                    currentMax = currentNumber;
                    currentSecondMax = temp;
                    currentThirdMax = temp2;

                }
                greaterThanSecondMax = currentNumber > currentSecondMax;
                if (currentNumber < currentMax && greaterThanSecondMax) {
                    temp = currentSecondMax;
                    currentSecondMax = currentNumber;
                    currentThirdMax = temp;
                }
                greaterThanThirdMax = currentNumber > currentThirdMax;
                if (!greaterThanMax && !greaterThanSecondMax && currentNumber != currentSecondMax && greaterThanThirdMax){
                    currentThirdMax = currentNumber;
                }
            }
        }

        if(distinctValues.size() >= 3 && currentThirdMax != currentSecondMax){
            return currentThirdMax;
        }
        else{
            return currentMax;
        }
    }

    public static boolean isPattern(String pattern, String word) {
        ArrayList<Character> listOfUniqueCharactersInPattern = new ArrayList<>();
        HashMap<Character, Integer> numberOfOccurrences = new HashMap<>();

        char currentChar;
        for(int i = 0; i < pattern.length(); i++) {
            currentChar = pattern.charAt(i);
            if (!listOfUniqueCharactersInPattern.contains(currentChar)) {
                listOfUniqueCharactersInPattern.add(currentChar);
            }
        }

        for(char character : listOfUniqueCharactersInPattern){
            numberOfOccurrences.put(character, 0);
        }

        int currentKeyValue;
        for(int j = 0; j < pattern.length(); j++){
            currentChar = pattern.charAt(j);
            currentKeyValue = numberOfOccurrences.get(currentChar);
            numberOfOccurrences.put(currentChar,currentKeyValue + 1);
        }
        return true;


    }

    private static boolean matchInt(String pattern, String str) {
        Map<Character, String> map;
        map = new HashMap<>();
        if (pattern.length() == 0) {
            return str.length() == 0;
        }
        char pch = pattern.charAt(0);
        for (int i = 0; i < str.length(); ++i) {
            if (!map.containsKey(pch)) {
                String val = str.substring(0, i + 1);
                map.put(pch, val);
                if (matchInt(pattern.substring(1), str.substring(val.length()))) {
                    return true;
                } else {
                    map.remove(pch);
                }
            } else {
                String val = map.get(pch);
                if (!str.startsWith(val)) {
                    return false;
                }
                return matchInt(pattern.substring(1), str.substring(val.length()));
            }
        }
        return false;
    }

    public static List<Integer> gradingStudents(List<Integer> grades) {
        List<Integer> finalGrades = new ArrayList<>();
        int multipleOfFive;
        int multiplicator;
        for(int grade : grades){
            multipleOfFive = 5;
            multiplicator = 2;
            if(grade >= 38){
                while(multipleOfFive < grade){
                    multipleOfFive = 5*multiplicator;
                    multiplicator++;
                }
                if(multipleOfFive - grade < 3){
                    grade = multipleOfFive;
                }
            }
            finalGrades.add(grade);
        }
        return finalGrades;
    }

    public static int distance(String i, String t) {
        int lengthOfT = t.length();
        int lengthOfI = i.length();
        int counter = 0;

        StringBuilder stringBuilder = new StringBuilder();
        for(int j = 0; j < lengthOfI; j++){
            stringBuilder.append(i.charAt(j));
        }

        if(lengthOfT == 0){
            return lengthOfI;
        }

        if(lengthOfI == 0){
            return lengthOfT;
        }

        char currentTCharacter;
        if(!i.equals(t)){
            for(int j = 0; j < lengthOfT; j++){
                currentTCharacter = t.charAt(j);
                if(j < lengthOfI && !(stringBuilder.charAt(j) == currentTCharacter)){
                    if( j+1 < lengthOfT && (stringBuilder.charAt(j) == t.charAt(j+1))) {
                        stringBuilder.insert(j, currentTCharacter);
                        counter++;
                    }
                    else{
                        stringBuilder.setCharAt(j, currentTCharacter);
                        counter++;
                        System.out.println(stringBuilder.length());
                    }
                }
                else if (j > lengthOfI){
                    stringBuilder.append(currentTCharacter);
                    counter++;
                    System.out.println(stringBuilder.length());
                }
            }
            System.out.println(stringBuilder.toString());
            System.out.println(t);
            if(lengthOfI > lengthOfT && stringBuilder.toString().length() != lengthOfT){
                stringBuilder.delete(lengthOfT, lengthOfI);
                counter += lengthOfI - lengthOfT;
            }
        }
        return counter;
    }

    public static List<Integer> russianRoulette2(List<Integer> arr) {
        int size = arr.size();
        int minimum = 0;
        int maximum = 0;
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < size; i++){
            if( i != size -1 && arr.get(i) == 1 && arr.get(i+1) == 1){
                arr.set(i,0);
                arr.set(i+1,0);
                minimum++;
                maximum += 2;
            }
            else if(i != size -1 && arr.get(i) == 1 && arr.get(i+1) == 0){
                arr.set(i,0);
                minimum++;
                maximum++;
            }
            else if(i == size -1 && arr.get(i) == 1){
                minimum++;
                maximum++;

            }
        }
        result.add(minimum);
        result.add(maximum);
        return result;
    }


    public static int michaelsCode(String message) {
        String trueMessage = message.toLowerCase();
        int max = 1;
        int length = trueMessage.length();
        if(length == 0){
            return 0;
        }
        int counter = 1;
        ArrayList<Integer> maxValues = new ArrayList<>();
        ArrayList<Integer> valueList = new ArrayList<>();
        valueList.add((int)trueMessage.charAt(0));
        for(int i = 1; i < length; i++){
            char currentCharacter = trueMessage.charAt(i);
            int currentCharacterValue = (int) currentCharacter;
            boolean validValue = true;
            for(int value : valueList){
                validValue = (currentCharacterValue - value) <= 2 && (currentCharacterValue - value) >= -2;
                if(!validValue){
                    break;
                }
            }
            if(validValue){
                valueList.add(currentCharacterValue);
                counter++;
            }
            else{
                maxValues.add(counter);
                valueList.clear();
                valueList.add(currentCharacterValue);
                counter = 1;
            }
            if(i == length -1){
                maxValues.add(counter);
            }
        }
        for(int number : maxValues){
            if(number > max){
                max = number;
            }
        }
        return max;
    }

    public static List<Integer> minSteps(String containers) {
        ArrayList<Integer> result = new ArrayList<>();
        int[] containerArray = Arrays.stream(containers.split("")).mapToInt(Integer::parseInt).toArray();
        long numberOfMugs = IntStream.of(containerArray)
                .filter(i -> i == 1)
                .count();

        int counter = 0;
        for (int j = 0; j < containerArray.length; j++) {
            int index = 0;
            int desired = containerArray[j];
            int current;

            while (desired != numberOfMugs && index < containerArray.length) {

                current = containerArray[index];
                if (index != j && current == 1 && Math.abs(index - j) == 1) {
                    desired += current;
                    counter++;
                }
                else if (index != j && current == 1) {
                    int temp = desired;
                    int tempIndex = index;
                    int tempo = 0;
                    int tempo2 = 0;
                    if (j < tempIndex) {
                        tempIndex = containerArray.length - 1;
                        for (int k = containerArray.length - 1; k > j; k--) {
                            if (tempIndex - 1 == j) {
                                desired += tempo;
                                counter += tempo;
                                tempo = 0;
                                index = containerArray.length - 1;

                            } else if (containerArray[tempIndex] != 0) {
                                tempo++;
                                tempIndex--;
                                counter += tempo;
                            } else {
                                tempIndex--;
                                counter += tempo;
                            }
                        }
                    }
                    else {
                        tempIndex = 0;
                        for (int h = 0; h < j; h++) {
                            if (tempIndex + 1 == j) {
                                desired += tempo2;
                                counter += tempo2;
                                tempo2 = 0;
                                index = j - 2;
                            } else if (containerArray[tempIndex] != 0) {
                                tempo2++;
                                tempIndex++;
                                counter += tempo2;
                            } else {
                                tempIndex++;
                                counter += tempo2;
                            }
                        }
                    }
                }
                if (index != containerArray.length - 1) {
                    index++;
                }
            }
            result.add(counter);
            counter = 0;
        }
        return result;
    }

    public static String aliensplat(String english, String alien, int a, int b) {
        if(english.length() != alien.length()){
            return "aliensplat";
        }
        if(alien.equals(english)){
            return "0";
        }

        int total = 0;

        int val1;
        int val2;
        int diff;
        int startingIndex = 0;
        int endIndex = startingIndex;
        char c1 = english.charAt(startingIndex);
        char c2 = alien.charAt(startingIndex);
        char currentECharacter;
        char currentACharacter;
        for(int j = 1; j < english.length(); j++){
            currentECharacter = english.charAt(j);
            currentACharacter = alien.charAt(j);
            if(c1 == currentECharacter){
                if(c2 == currentACharacter){
                    endIndex = j;
                }
                else {
                    if(endIndex - startingIndex == 0){
                        total += Math.abs((int)(c1 - c2)) * b;
                    }
                    else{
                        total += Math.abs((int)(c1 - c2)) * a;
                    }
                    startingIndex = j;
                    endIndex=startingIndex;
                    c1 = english.charAt(startingIndex);
                    c2 = alien.charAt(startingIndex);
                    if(j == english.length() - 1){
                        total += Math.abs((int)(c1 - c2)) * b;
                    }
                }
            }
            else {
                if(endIndex - startingIndex == 1){
                    total += Math.abs((int)(c1 - c2)) * b;
                }
                else{
                    total += Math.abs((int)(c1 - c2)) * a;
                }
                startingIndex = j;
                endIndex=startingIndex;
                c1 = english.charAt(startingIndex);
                c2 = alien.charAt(startingIndex);
                if(j == english.length() - 1){
                    total += Math.abs((int)(c1 - c2)) * b;
                }
            }
        }
        String result = Integer.toString(total);
        return result;
    }

    public static boolean isWeaved(String first, String second, String third) {

        if(first.length() + second.length() != third.length()){
            return false;
        }

        StringBuilder firstB = new StringBuilder(first);
        StringBuilder secondB = new StringBuilder(second);


        ArrayList<String> listFirst = new ArrayList<>();
        HashMap<String, ArrayList<Integer>> map1 = new HashMap<>();
        HashMap<String, ArrayList<Integer>> map2 = new HashMap<>();
        ArrayList<String> listSecond = new ArrayList<>();


        for (int i = 0; i < first.length(); i++) {
            for (int j = i + 1; j <= first.length(); j++) {
                ArrayList<Integer> tempList = new ArrayList<>();
                listFirst.add(first.substring(i, j));
                tempList.add(i);
                tempList.add(j);
                map1.put(first.substring(i,j), tempList);
            }
        }

        for (int i = 0; i < second.length(); i++) {
            for (int j = i + 1; j <= second.length(); j++) {
                ArrayList<Integer> tempList2 = new ArrayList<>();
                listSecond.add(second.substring(i, j));
                tempList2.add(i);
                tempList2.add(j);
                map2.put(second.substring(i,j), tempList2);
            }
        }

        StringBuilder result = new StringBuilder();
        boolean found;
        boolean found2;
        boolean firstTurn = true;
        int startIndex = 0;
        while(!result.toString().equals(third)) {
            found = false;
            found2 = false;

            for (String substring : listFirst) {
                for (int i = startIndex; i < third.length(); i++) {
                    String currentSubstring = third.substring(startIndex, i + 1);
                    if (substring.equals(currentSubstring)) {
                        result.append(substring);
                        firstB.delete(map1.get(substring).get(0), map1.get(substring).get(1));
                        listFirst.clear();
                        map1.clear();
                        for (int k = 0; k < firstB.toString().length(); k++) {
                            for (int j = k + 1; j <= firstB.toString().length(); j++) {
                                ArrayList<Integer> tempList = new ArrayList<>();
                                listFirst.add(firstB.substring(k, j));
                                tempList.add(k);
                                tempList.add(j);
                                map1.put(firstB.substring(k,j), tempList);
                            }
                        }
                        startIndex = i+1;
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
                firstTurn = false;
            }

            if (!firstTurn) {
                for (String substring : listSecond) {
                    for (int i = startIndex; i < third.length(); i++) {
                        String currentSubstring = third.substring(startIndex, i + 1);
                        if (substring.equals(currentSubstring)) {
                            result.append(substring);
                            secondB.delete(map2.get(substring).get(0), map2.get(substring).get(1));
                            listSecond.clear();
                            map2.clear();
                            for (int k = 0; k < secondB.toString().length(); k++) {
                                for (int j = k + 1; j <= secondB.toString().length(); j++) {
                                    ArrayList<Integer> tempList2 = new ArrayList<>();
                                    listSecond.add(secondB.substring(k, j));
                                    tempList2.add(k);
                                    tempList2.add(j);
                                    map2.put(secondB.substring(k,j), tempList2);
                                }
                            }
                            startIndex = i+1;
                            found2 = true;
                            break;
                        }
                    }
                    if (found2) {
                        break;
                    }
                    firstTurn = true;
                }

            }
            if(!found && !found2){
                return false;
            }
        }
        return true;
    }

    // count even numbers in a sequence of integers
    public static int countEven(List<Integer> seq)
    {
        int c = 0;

        for (int i = 0; i < seq.size(); i++) {
            if (seq.get(i) % 2 == 0) {
                c++;
            }
        }
        return c;
    }




    public static void main(String[] args) {
        System.out.println(isWeaved("aabcc","dbbca","aadbbbaccc"));
    }
}

