package Algorithms.BackTracking;

import java.util.*;

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



    EXAMPLE 1:
    -----------
    digits = "234"
    n = digits.length = "234".length() = 3

    2 : "abc" -- length = 3
    3 : "def" -- length = 3
    4 : "ghi" -- length = 3

    Here in this problem with any example, we calculate 3^n not 2^n like we usually do in backtracking
    Here 3^n cause 3 is length of digits -- so new 3 possibilities in each iteration

                                                                   ""
                                                                   |
                                                                   2
                         __________________________________________|__________________________________________
                         |                                         |                                         |
                         a                                         b                                         c
                         |                                         |                                         |
                         3                                         3                                         3
             ____________|_____________                ____________|_____________                ____________|_____________
             |           |            |                |           |            |                |           |            |
             ad          ae           af               bd          be           bf               cd          ce           cf
             |           |            |                |           |            |                |           |            |
             4           4            4                4           4            4                4           4            4
         ____|____   ____|____    ____|____        ____|____   ____|____    ____|____        ____|____   ____|____    ____|____
         |   |    |  |   |   |    |   |    |       |   |    |  |   |   |    |   |    |       |   |    |  |   |   |    |   |    |
       adg  adh  adi aeg aeh aei  afg afh  afi   bdg  bdh  bdi beg beh bei  bfg bfh  bfi   cdg  cdh  cdi ceg ceh cei  cfg cfh  cfi


    Here the total number of combinations is 3^n = 3^3 = 27



    EXAMPLE 2:
    -----------
    digits = "23"
    n = digits.length = "23".length() = 2

    2 : "abc" -- length = 3
    3 : "def" -- length = 3

    Total number of combinations = 3^n = 3^2 = 9



    NOTE:
    -----
    when we have 9 : "wxyz" -- length = 4
    then the time complexity will be 4^n not 3^n --- worst case time complexity
    So, overall time complexity will be O(4^n)



 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 16 Feb 2025
 * @link 17. Letter Combinations of a Phone Number <a href="https://leetcode.com/problems/letter-combinations-of-a-phone-number/">LeetCode link</a>
 * @topics Hash Table, String, Backtracking
 * @companies Amazon(11), LinkedIn(8), Microsoft(7), Meta(6), Google(5), Bloomberg(2), TCS(2), Epic Systems(15), DE Shaw(10), Uber(8), Accenture(8), Apple(6), Oracle(6), IBM(5), Zoho(5), Tesla(5), Flexport(5)
 */
@SuppressWarnings("all")
public class LetterCombinationsOfPhoneNumber {
    public static void main(String[] args) {
        String digits = "234";
        System.out.println("letterCombinations 1 => " + letterCombinations1(digits));
        System.out.println("letterCombinations 2 => " + letterCombinations2(digits));
    }


    /**
     * @TimeComplexity O(4^n * n) -- worst case when 9 is in digits, where n is the length of digits and 4 is the max number of characters for a digit i.e "wxyz"
     * @SpaceComplexity O(n) -- recursion stack space
     */
    static String[] keypad = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    static List<String> result = new ArrayList<>();
    public static List<String> letterCombinations1(String digits) {
        backtrack(new StringBuilder(), digits, 0);
        return result;
    }

    private static void backtrack(StringBuilder word, String digits, int digitIndex){
        if (word.length() == digits.length()) {
            result.add(word.toString());
            return;
        }

        String letters = keypad[digits.charAt(digitIndex)-'0'];
        for (char c : letters.toCharArray()) {
            word.append(c);
            backtrack(word, digits, digitIndex+1);
            word.deleteCharAt(word.length()-1);
        }

    }







    public static List<String> letterCombinations2(String digits) {
        if (digits.isEmpty()) return new ArrayList<>();
        String[] map = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        return backtrack(digits, 0, map, new StringBuilder());
    }

    public static List<String> backtrack(String digits, int digitsIndex, String[] map, StringBuilder sb) {
        if (digitsIndex == digits.length()) return List.of(sb.toString()); // or new ArrayList<>(Arrays.asList(sb.toString().split("")));

        List<String> lst = new ArrayList<>();
        String letters = map[digits.charAt(digitsIndex) - '0']; // or Integer.parseInt(digits.charAt(index) + "");
        for (String c : letters.split("")) {
            sb.append(c);
            lst.addAll(backtrack(digits, digitsIndex + 1, map, sb));
            sb.deleteCharAt(sb.length() - 1); // after adding "adg" to lst, remove "g" from sb as we need to calculate "adh"
        }
        return lst;
    }






    public static List<String> letterCombinations3(String digits) {
        List<String> lst = new ArrayList<>();
        int n = digits.length();
        String[] keypad = new String[]{"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        if(n>0) dfs(digits, n, 0, keypad, lst, new String());
        return lst;
    }
    public static void dfs(String digits, int n, int i, String[] keypad, List<String> lst, String currStr) {
        if(i==n) { // or currStr.length() == n
            lst.add(currStr);
            return;
        }
        char digit = digits.charAt(i);
        String str = keypad[digit-'2'];
        for(String s: str.split("")) {
            dfs(digits, n, i+1, keypad, lst, currStr+s);
        }
    }




    public static List<String> letterCombinations4(String digits) {
        String[] keypad = new String[]{"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        Queue<String> q = new LinkedList<>();

        String letters = keypad[digits.charAt(0) - '2'];
        for (String c : letters.split("")) {
            q.offer(c);
        }

        for (int i = 1; i < digits.length(); i++) {
            int size = q.size();

            while (size-- > 0) {
                String out = q.poll();

                String currLetters = keypad[digits.charAt(i) - '2'];
                for (char c : currLetters.toCharArray()) {
                    q.offer(out + c);
                }
            }
        }

        return new ArrayList<>(q);
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

    public static List<String> letterCombinations5(String digits) {
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
    public static List<String> letterCombinations6(String digits) {
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
    public List<String> letterCombinationsMyApproachOldest(String digits) {
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
