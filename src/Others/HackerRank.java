package Others;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;

public class HackerRank
{

    public static void FizzBuzz(int n)
    {
        for(int i = 1; i <= n; i++){
            boolean isFizz = i % 3 == 0;
            boolean isBuzz = i % 5 == 0;
            boolean isNothingSpecial = (!isFizz && !isBuzz);
            boolean isFizzBuzz = (isFizz && isBuzz);

            if(isNothingSpecial){
                System.out.println(i);
            }
            else if (isFizzBuzz){
                System.out.println("FizzBuzz");
            }
            else if(isFizz){
                System.out.println("Fizz");
            }
            else {
                System.out.println("Buzz");
            }

        }
    }

    private static BigInteger a = new BigInteger("0");
    private static BigInteger b = new BigInteger("1");
    private static BigInteger[] ARRAY = {a,b};

    public static BigInteger LinearFib(int n, BigInteger[] array){
        if(n <= 1){
            return ARRAY[n];
        }
        else{
            BigInteger temp = ARRAY[1];
            ARRAY[1] = temp.add(ARRAY[0]);
            ARRAY[0] = temp;
            return LinearFib(n -1, ARRAY);
        }
    }

    private static String findUncoupled(String[] numbers){
        HashSet<String> set = new HashSet<>();
        ArrayList<String> list = new ArrayList<>();
        for(String number: numbers){
            if(set.add(number)){
                list.add(number);
            }
            else {
                list.remove(number);
            }
        }
        return list.get(0);
    }

    private static int strstr(String s, String x) {
        int index = 0;
        int index2 = 0;
        int resultIndex = 0;
        boolean onTrack = false;
        int length = x.length();

        while(index2 < s.length()){
            if(x.charAt(index) == s.charAt(index2)){
                if(index == length - 1){
                    return resultIndex;
                }
                else{
                    index++;
                }
                if(!onTrack){
                    resultIndex = index2;
                    onTrack = true;

                }
            }
            else{
                onTrack = false;
                index = 0;
            }
            if(s.charAt(index2) != x.charAt(0) || onTrack){
                index2 ++;
            }

        }
        return -1;
    }

    private static int strstrVersion2(String s, String x)
    {
        int index = 0;
        int resultIndex = -1;
        boolean found = false;
        int length = x.length();

        StringBuilder sb = new StringBuilder(s);

        while(!found){
            if(sb.length() >= length && sb.substring(0, length).equals(x)){
                found = true;
                resultIndex = index;
            }
            else{
                if(sb.length() >= length){
                    sb.delete(0, 1);
                    index ++;
                }
                else{
                    break;
                }
            }
        }
        return resultIndex;

    }

    private static void MthToLastElement(String[] array, int index){
        if(index > array.length){
            System.out.println("NIL");
            return;
        }
        LinkedList<Integer> list = new LinkedList<>();
        for(String character: array){
            int number = Integer.parseInt(character);
            list.offerLast(number);
        }
        for(int i = 1; i < index; i++ ){
            list.removeLast();
        }
        System.out.println(list.getLast());
    }

    // Contains solution for the Mth to last element method
//    public static void main(String args[] ) throws Exception {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String line = br.readLine();
//        int index = Integer.parseInt(line);
//        line = br.readLine();
//        int startIndex = 0;
//        int blankIndex = line.indexOf(" ", startIndex);
//        ArrayList<String> list = new ArrayList<>();
//        while (blankIndex != -1){
//            String number = line.substring(startIndex, blankIndex);
//            list.add(number);
//            startIndex = blankIndex + 1;
//            blankIndex = line.indexOf(" ", startIndex);
//            if(blankIndex == -1){
//                number = line.substring(startIndex);
//                list.add(number);
//            }
//        }
//        String[] array = new String[list.size()];
//        list.toArray(array);
//        MthToLastElement(array, index);
//    }


    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        while(line != null){
            int n = Integer.parseInt(line);
            System.out.println(LinearFib(n, ARRAY));
            ARRAY[0] = a;
            ARRAY[1] = b;
            line = br.readLine();
        }
    }
}
