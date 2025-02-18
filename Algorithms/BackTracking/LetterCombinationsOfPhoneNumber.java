package Algorithms.BackTracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**

THOUGHTS
---------
1) HashMap to map number with chars like {2:"abc"}
2) No duplicate numbers in input
3) we can have "ad" but "da" is not needed
4) take first letter in each entryVal and calculate all possibilities
and remove that letter in the entryVal
5) digits = "23" --> ["ad","ae","af","bd","be","bf","cd","ce","cf"]
and digits = "234" ---> ["adg", "adh", "adi", "aeg", "aeh", "aei", "afg", "afh", "afi", "bdg", "bdh", "bdi", "beg", "beh", "bei", "bfg", "bfh", "bfi", "cdg", "cdh", "cdi", "ceg", "ceh", "cei", "cfg", "cfh", "cfi"]
this means digits.length == List.get(i).length


    2 = "abc"
    3 = "def"
    4 = "ghi"


                                                                     ""
                                                _____________________|_____________________
                                                |                     |                     |
                                                2                     3                     4
                                    ____________|____________     ____|____             ____|____
                                    |           |            |    |   |   |             |   |   |
                                    a           b            c    d   e   f             g   h   i
                        __________|___   _____|_____  _____|_____
                        |             |   |          | |         |
                        3             4   3          4 3         4
            ____________|____________    |||          |||
            |            |            |
            ad            ae           af
            |            |            |
            4            4            4
        ____|____    ____|____    ____|____
        |   |    |   |   |   |    |   |    |
    adg  adh  adi aeg aeh aei  afg afh  afi

 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 16 Feb 2025
 */
@SuppressWarnings("all")
public class LetterCombinationsOfPhoneNumber {
    public static void main(String[] args) {
        String digits = "234";
        System.out.println("letterCombinations(digits) => " + letterCombinations(digits));
    }

    public static List<String> letterCombinations(String digits) {
        if (digits.isEmpty()) return new ArrayList<>();
        String[] map = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        return backtrack(digits, 0, map, new StringBuilder());
    }

    public static List<String> backtrack(String digits, int digitsIndex, String[] map, StringBuilder sb) {
        if (digitsIndex == digits.length()) return List.of(sb.toString());

        List<String> lst = new ArrayList<>();
        String letters = map[digits.charAt(digitsIndex) - '0']; // or Integer.parseInt(digits.charAt(index) + "");
        for (String c : letters.split("")) {
            sb.append(c);
            lst.addAll(backtrack(digits, digitsIndex + 1, map, sb));
            sb.deleteCharAt(sb.length() - 1); // after adding "adg" to lst, remove "g" from sb as we need to calculate "adh"
        }
        return lst;
    }

    private static String[][] letters = new String[][]{
        {},                     //0
        {},                     //1
        {"a", "b", "c"},        //2
        {"d", "e", "f"},        //3
        {"g", "h", "i"},        //4
        {"j", "k", "l"},        //5
        {"m", "n", "o"},        //6
        {"p", "q", "r", "s"},   //7
        {"t", "u", "v"},        //8
        {"w", "x", "y", "z"},   //9
    };

    public static List<String> letterCombinations2(String digits) {
        if (digits.length() == 0) return new ArrayList<>();
        List<String> res = new ArrayList<>();
        backtrack(digits, 0, new StringBuilder(), res);
        return res;
    }

    private static void backtrack(String digits, int index, StringBuilder curr, List<String> res) {
        if (index == digits.length() && digits.length() > 0) {
            res.add(curr.toString());
            return;
        }

        for (String c : letters[Integer.valueOf(digits.substring(index, index + 1))]) {
            curr.append(c);
            backtrack(digits, index + 1, curr, res);
            curr.deleteCharAt(curr.length() - 1);
        }
    }


    static Map<Character,String> map = new HashMap<>();
    static {
        map.put('2',"abc");
        map.put('3',"def");
        map.put('4',"ghi");
        map.put('5',"jkl");
        map.put('6',"mno");
        map.put('7',"pqrs");
        map.put('8',"tuv");
        map.put('9',"wxyz");
    }
    public static List<String> letterCombinations3(String digits) {
        List<String> ans = new ArrayList<>();
        if(digits.length()==0)return ans;
        solve(0,digits,ans,new StringBuilder());
        System.out.println(ans);
        return ans;
    }
    static void solve(int i,String digits,List<String>ans,StringBuilder t){
        if(i==digits.length()){
            ans.add(t.toString());
            return;
        }
        for(char c:map.get(digits.charAt(i)).toCharArray()){
            t.append(c);
            solve(i+1,digits,ans,t);
            t.deleteCharAt(t.length()-1);
        }
    }




    // Need to research more
    public List<String> letterCombinationsMyApproach(String digits) {
        if (digits.isEmpty()) return new ArrayList<>();

        Map<String, String> keyboard = new HashMap<>();
        keyboard.put("2", "abc");
        keyboard.put("3", "def");
        keyboard.put("4", "ghi");
        keyboard.put("5", "jkl");
        keyboard.put("6", "mno");
        keyboard.put("7", "pqrs");
        keyboard.put("8", "tuv");
        keyboard.put("9", "wxyz");

        String[] arr = new String[10];

        for(String digit: digits.split("")) {
            arr[Integer.parseInt(digit)] = keyboard.get(digit);
        }

        List<String> lst = new ArrayList<>();

        // 1st digit chars
        int i = Integer.parseInt(digits.charAt(0)+"");
        for (String c: arr[i].split("") ) {
            travChar(c, arr, lst, digits.length(), i, 0);
        }

        return lst;
    }
    //
    private String travChar(String res, String[] arr, List<String> lst, int targetLen, int arrIndex, int charIndexInDigitStr) {
        // res += arr[arrIndex].charAt(charIndexInDigitStr);

        // add next index char
        for (int i = arrIndex+1; arrIndex+1 < arr.length; i++) {
            // target only digits str indices
            if (!arr[arrIndex+1].isEmpty()) {
                for (int ci=0; ci < arr[arrIndex+1].length(); ci++){
                    travChar(res, arr, lst, targetLen, arrIndex+1, ci);
                }
            }
        }

        return res;
    }


        private String helper(String res, String[] arr, List<String> lst, int targetLen, int i, int ci) {

            return res += arr[i].charAt(ci);
        }

}
