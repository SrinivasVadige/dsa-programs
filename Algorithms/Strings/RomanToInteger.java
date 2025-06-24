package Algorithms.Strings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 21 Sept 2024
 * @link 13. Roman to Integer <a href="https://leetcode.com/problems/roman-to-integer/">LeetCode link</a>
 * @topics String, Math, Hash Table, Switch case
*/
public class RomanToInteger {

    public static void main(String[] args) {
        String romanNum = "DCXXI";
        System.out.println(romanToInt1(romanNum));
        System.out.println(romanToInt2(romanNum));
    }


    public static int romanToInt1(String s) {
        int result = 0, n = s.length();

        for(int i=0; i<n; i++) {
            int currNum = charToNum(s.charAt(i));
            int nextNum = 0;
            if(i<n-1) {
                nextNum = charToNum(s.charAt(i+1));
            }

            if(currNum < nextNum) {
                result += nextNum-currNum;
                i++;
            } else {
                result += currNum;
            }
        }
        return result;
    }
    private static int charToNum(char c) {
        switch (c) {
            case 'I':
                return 1; // as we're returning int, so we can't use break; statement
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }




    public static int romanToInt2(String s) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        int result = 0, n = s.length();
        for(int i=0; i<s.length(); i++) {
            if(i+1 < n && map.get(s.charAt(i)) < map.get(s.charAt(i+1))) {
                result -= map.get(s.charAt(i));
                // as we're subtracting the smaller value,
                // we're going add the connected bigger of it, in the next iteration
            } else {
                result += map.get(s.charAt(i));
            }
        }

        return result;
    }





    public static int romanToInt3(String s) {

        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        int result = 0;

        for(int i=0; i<s.length(); i++){
            int charValue = map.get(s.charAt(i));
            int nextCharValue = i+1<s.length()? map.get(s.charAt(i+1)): 0;
            result = result + (charValue<nextCharValue? -charValue: +charValue); // IV, IX, XL, XC.....

            // // or skip nextChar itertion
            // if (charValue < nextCharValue){
            //     charValue = nextCharValue - charValue;
            //     i++;
            // }
            // result += charValue;
        }

        return result;
    }





    // OTHER WAYS TO GET THE VALUE OF A CHAR IN JAVA USING SWITCH EXPRESSIONS --------

    // uses Java 14+ yield
    private static int charToNum2(char c) {
        return switch (c) {
            case 'I':
                yield 1;
            case 'V':
                yield 5;
            case 'X':
                yield 10;
            case 'L':
                yield 50;
            case 'C':
                yield 100;
            case 'D':
                yield 500;
            case 'M':
                yield 1000;
            default:
                yield 0;
        };
    }
    // uses Java 14+ switch expression
    private static int charToNum3(char c) {
        return switch (c) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            case 'D' -> 500;
            case 'M' -> 1000;
            default -> 0;
        };
    }
    // old java switch without return value
    private static int charToNum4(char c) {
        int num = 0;
        switch (c) {
            case 'I':
                num = 1;
                break;
            case 'V':
                num = 5;
                break;
            case 'X':
                num = 10;
                break;
            case 'L':
                num = 50;
                break;
            case 'C':
                num = 100;
                break;
            case 'D':
                num = 500;
                break;
            case 'M':
                num = 1000;
                break;
        }
        return num;
    }








    public static int romanToIntApproachOld(String s) {
        //IV
        //IX
        //XL
        //XC
        //CD
        //CM
        // M D C L X V I
        int sum = 0;
        for(int i=0; i<s.length(); i++){
            char charAtIndex = s.charAt(i);
            int charValue = getRomanValue(charAtIndex);
            if(Arrays.asList('I', 'X', 'C').contains(charAtIndex) && i<s.length()-1){
                if(shouldSkipNextChar(charAtIndex, s.charAt(i+1))){
                    charValue = getRomanValue(s.charAt(i+1)) - charValue;
                    i++;
                }
            }
            sum += charValue;
        }
        return sum;
    }

    public static boolean shouldSkipNextChar(char c, char nextChar ){
        if(c == 'I' && Arrays.asList('V','X').contains(nextChar)
        || c == 'X' && Arrays.asList('L','C').contains(nextChar)
        || c == 'C' && Arrays.asList('D','M').contains(nextChar)
        )
            return true;

        return false;
    }

    public static int getRomanValue(char c){
        int value;
        switch(c){
            case 'I': value = 1;
                break;
            case 'V': value = 5;
                break;
            case 'X': value = 10;
                break;
            case 'L': value = 50;
                break;
            case 'C': value = 100;
                break;
            case 'D': value = 500;
                break;
            case 'M': value = 1000;
                break;
            default: value = 0;
        }
        return value;
    }



}
