package Algorithms.Strings;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 24 June 2025
 * @link 12. Integer to Roman <a href="https://leetcode.com/problems/integer-to-roman/">LeetCode link</a>
 * @topics String, Math, Hash Table, Switch case
*/
public class IntegerToRoman {
    public static void main(String[] args) {
        int num = 58;
        System.out.println(intToRoman(num));
    }

    /**
        Here 3 = 3 * 1
        and  8 = 5 + 3
        So, just take care of 1, 4, 5, and 9
     */
    public static String intToRoman(int num) {
        String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] value = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        // or Object[][] = { {"M", 1000}, {"CM", 900}...} or Map<String, Integer>
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length; i++) {
            int val = value[i];
            String sym = roman[i]; // Symbol

            while (num >= val) {
                sb.append(sym);
                num -= val; // 8 = 5 + 3 --> so, 8 - 5 = 3 is the new num and if 3 we do 3-1 = 2 and then 2-1 = 1 and then 1-1 = 0
            }
        }
        return sb.toString();
    }


    /**
     * Same like above {@link #intToRoman} but we decrease the num itself like 8000 = 5000 + 3000 then the new num is 3000
     */
    public static String intToRoman2(int num) {
        String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] value = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < roman.length; i++) {
            String sym = roman[i]; // Symbol
            int val = value[i];

            int count = num / val;
            if (count > 0) {
                sb.append(sym.repeat(count));  // Efficient Roman symbol repetition
                num = num % val;               // 8 % 5 = 3 remainder -- Reduce the number
            }

            /*
            or
            if (num >= val) {
                int count = num / val;
                sb.append(sym.repeat(count));
                num %= val; // 8 % 5 = 3
            }
            */
        }
        return sb.toString();

        /*
        instead of String[] romans and int[] values, we can use HashMap like this --- NOTE: it has has to be in order that's why LinkedHashMap
        Map<String, Integer> map = new LinkedHashMap<>(){{ // Double braces initialization creates subclasses and Breaks Serializable, Equals, and HashCode
            put("M", 1000);
            put("CM", 900);
            put("D", 500);
            put("CD", 400);
            put("C", 100);
            put("XC", 90);
            put("L", 50);
            put("XL", 40);
            put("X", 10);
            put("IX", 9);
            put("V", 5);
            put("IV", 4);
            put("I", 1);
        }};
         */
    }



    /**

        1       -> I
        5       -> V
        10      -> X
        50      -> L
        100     -> C
        500     -> D
        1000    -> M

        dp --> use some method to retrieve the Roman Char

        StringBuilder(num)

        while (!sb.isEmpty()) {

        }
        use .length() to know 10s, 100, 1000,
        use .charAt(0) -> left most char
        use

     */
    public String intToRomanMyApproach(int num) {
        StringBuilder roman = new StringBuilder();
        StringBuilder sb = new StringBuilder(""+num);

        while(!sb.isEmpty()) {
            int i = sb.charAt(0) - '0';
            int zeros = sb.length() - 1;
            roman.append(getRoman(i, zeros));
            sb.deleteCharAt(0);
        }
        return roman.toString();
    }

    public String intToRomanMyApproach2(int num) {
        StringBuilder roman = new StringBuilder();

        while(num>0) {
            int zeros = (int) Math.log10(num);
            int tenPowerZeros = (int) Math.pow(10, zeros);
            int digit = num/tenPowerZeros; // return the left most digit
            roman.append(getRoman(digit, zeros));
            num -= digit * tenPowerZeros; // remove the left most digit
        }
        return roman.toString();
    }


    private String getRoman(int digit, int zeros) {

        String[][] roman = {
            {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"},    // ones --> zeros = 0
            {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"},    // tens --> zeros = 1
            {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"},    // hundreds --> zeros = 2
            {"", "M", "MM", "MMM"}                                           // thousands --> zeros = 3
        };
        if (zeros >= roman.length) return "";
        if (digit >= roman[zeros].length) return "";
        return roman[zeros][digit];
    }

    private String getRoman2(int i, int zeros) {
        String s = "";
        if (i <= 3) {
            if(zeros == 0) {
                s = "I";
            } else if (zeros == 1) {
                s = "X";
            } else if (zeros == 2) {
                s = "C";
            } else if (zeros == 3) {
                s = "M";
            }
            return s.repeat(i);
        } else if (i == 4) {
            return getRoman2(1, zeros) + getRoman2(5, zeros);
        } else if (i == 5) {
            if(zeros == 0) {
                s = "V";
            } else if (zeros == 1) {
                s = "L";
            } else if (zeros == 2) {
                s = "D";
            }
            return s;
        } else if (i <= 8) {
            return getRoman2(5, zeros) + getRoman2(i-5, zeros);
        } else if (i == 9) {
            return getRoman2(1, zeros) + getRoman2(1, zeros+1);
        }
        return s;
    }







    public String intToRomanMyApproachOld(int num) {

        String roman = "";
        int subNum = num;

        if(subNum >= 1000 && subNum < 4000){
            roman += "M".repeat(subNum/1000);
            if(subNum % 1000 == 0)
                return roman;
            subNum = removeLeftDigit(subNum);
        }

        if(subNum >= 900 && subNum < 1000){
            roman += "CM";
            if(subNum == 900)
                return roman;
            subNum = removeLeftDigit(subNum);
        }

        if(subNum >= 500 && subNum < 900){
            roman += "D";
            if(subNum >= 600)
                roman += "C".repeat(subNum/100-5);
            if(subNum % 100 == 0)
                return roman;
            subNum = removeLeftDigit(subNum);
        }

        if(subNum >= 400 && subNum < 500){
            roman += "CD";
            if(subNum == 400)
                return roman;
            subNum = removeLeftDigit(subNum);
        }

        if(subNum >= 100 && subNum < 400){
            roman += "C".repeat(subNum/100);
            if(subNum % 100 == 0)
                return roman;
            subNum = removeLeftDigit(subNum);
        }

        if(subNum >= 90 && subNum < 100){
            roman += "XC";
            if(subNum == 90)
                return roman;
            subNum = removeLeftDigit(subNum);
        }

        if(subNum >= 50 && subNum < 90){
            roman += "L";
            if(subNum >= 60)
                roman += "X".repeat(subNum/10-5);
            if(subNum % 10 == 0)
                return roman;
            subNum = removeLeftDigit(subNum);
        }

        if(subNum >= 40 && subNum < 50){
            roman += "XL";
            if(subNum == 40)
                return roman;
            subNum = removeLeftDigit(subNum);
        }

        if(subNum >= 10 && subNum < 40){
            roman += "X".repeat(subNum/10);
            if(subNum % 10 == 0)
                return roman;
            subNum = removeLeftDigit(subNum);
        }

        if(subNum >= 9 && subNum < 10){
            roman += "IX";
            if(subNum == 9)
                return roman;
        }

        if(subNum >= 5 && subNum < 9){
            roman += "V";
            if(subNum >= 6)
                roman += "I".repeat(subNum-5);
        }

        if(subNum >= 4 && subNum < 5){
            roman += "IV";
            if(subNum == 4)
                return roman;
        }

        if(subNum >= 1 && subNum < 4){
            roman += "I".repeat(subNum);
        }


        return roman;

    }

    public int removeLeftDigit(int subNum){
        return Integer.parseInt(Integer.toString(subNum).substring(1));
    }


}
