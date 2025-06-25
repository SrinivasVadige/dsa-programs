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

    public static String intToRoman(int num) {
        String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] value = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length; i++) {
            while (num >= value[i]) {
                sb.append(roman[i]);
                num -= value[i];
            }
        }
        return sb.toString();
    }


    public static String intToRoman2(int num) {
        Map<String, Integer> map = new LinkedHashMap<>(){{
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
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String sym = entry.getKey();
            int val = entry.getValue();
            if (num >= val) {
                int count = num / val;
                sb.append(sym.repeat(count));
                num %= val;
            }
        }
        return sb.toString();
    }
    public static String intToRoman3(int num) {
        Map<Integer, String> map = new LinkedHashMap<>(){
            {
                put(1000, "M");
                put(900, "CM");
                put(500, "D");
                put(400, "CD");
                put(100, "C");
                put(90, "XC");
                put(50, "L");
                put(40, "XL");
                put(10, "X");
                put(9, "IX");
                put(5, "V");
                put(4, "IV");
                put(1, "I");
            }
        };
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            int sym = entry.getKey();
            int val = entry.getValue().length();
            while (num >= sym) {
                sb.append(val);
                num -= sym;
            }
        }
        return sb.toString();
    }






    /**

        1       -> I
        5       -> V
        10      -> X
        50      -> L
        100     -> C
        500     -> D
        1000    -> M

        dp --> use some method to retireve the Roman Char

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
        System.out.println(sb.toString());

        while(!sb.isEmpty()) {
            int i = sb.charAt(0) - '0';
            int zeros = sb.length() - 1;
            roman.append(getRoman(i, zeros));
            sb.deleteCharAt(0);
        }
        return roman.toString();
    }


    private String getRoman(int i, int zeros) {

        String[][] roman = {
            {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"},    // ones
            {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"},    // tens
            {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"},    // hundreds
            {"", "M", "MM", "MMM"}                                           // thousands
        };
        if (zeros >= roman.length) return "";
        if (i >= roman[zeros].length) return "";
        return roman[zeros][i];
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
            return getRoman(1, zeros) + getRoman(5, zeros);
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
            return getRoman(5, zeros) + getRoman(i-5, zeros);
        } else if (i == 9) {
            return getRoman(1, zeros) + getRoman(1, zeros+1);
        }
        return s;
    }


}
