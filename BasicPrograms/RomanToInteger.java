package BasicPrograms;
import java.util.Arrays;
import java.util.HashMap;

/**
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
public class RomanToInteger {

    public static void main(String[] args) {
        String romanNum = "DCXXI";
        System.out.println(romanToIntApproach1(romanNum));
        System.out.println(romanToIntApproach2(romanNum));
    }

    // @SuppressWarnings("unchecked")
    public static int romanToIntApproach1(String s) {

        // @SuppressWarnings("rawtypes")
        HashMap<Character, Integer> map = new HashMap<>();
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


    public static int romanToIntApproach2(String s) {
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
