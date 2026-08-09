package Algorithms.BackTracking;

import java.util.List;
import java.util.ArrayList;

public class PalindromePartitioning {
    public static void main(String[] args) {
        String s = "aaab"; // => [["a","a","a","b"],["a","aa","b"],["aa","a","b"],["a","a","aa"],["aa","b"],["a","aaa"]]
        System.out.println("partitionMyApproach(s) => " + partitionMyApproach(s));
        System.out.println("partition(s) => " + partition(s));
    }

    /**
        THOUGHTS:
        ---------
        1) Only go until the s.length() and don't need to re-arrange the chars
        2) In "aab" string. No need to fine "aba"
        3) When s="aaab"

                                   []
                     ______________|______________
                    |                  |          |
                   [a]                [aa]       [aaa]
            ________|_______           |          |
            |              |           |          |
         [a, a]         [a, aa]     [aa, a]    [aaa, b]
            |              |           |
            |              |           |
         [a, a, a]     [a, aa, b]  [aa, a, b]
            |
            |
       [a, a, a, b]

        4) So, the children will be how many palindromes can be formed
        5) i.e break until you found any palindrome anymore a, aa, aaa

     */
    public static List<List<String>> partitionMyApproach(String s) {
        List<List<String>> lst = new ArrayList<>();
        backtrack(s, lst, new ArrayList<>(), 0);
        return lst;
    }
    private static void backtrack(String s, List<List<String>> lst, List<String> subLst, int i) {
        if (i==s.length()) {
            lst.add(new ArrayList<>(subLst));
            return;
        }
        // num of nodes == all possible palindromes from startIndex
        String subS = "";
        int start = i;
        for (; i<s.length(); i++) {
            subS = s.substring(start, i+1);
            if (isPalindrome(subS)) {
                subLst.add(subS);
                backtrack(s, lst, subLst, i+1);
                subLst.remove(subLst.size()-1); // reset
            }
        }
    }
    private static boolean isPalindrome(String s) {
        if (s.length()==1) return true;
        return new StringBuilder(s).reverse().toString().equals(s);
    }



    public static List<List<String>> partition(String s) {
        List<List<String>> lst = new ArrayList<>(); // final result list to return
        List<String> path = new ArrayList<>(); // current path to be added to result list
        dfs(s, lst, path, 0); // start dfs from index 0 of string s
        return lst;
    }
    public static void dfs(String s, List<List<String>> lst, List<String> path, int start) {
        if (start == s.length()) { // base case when we reach end of string s
            lst.add(new ArrayList<>(path)); // add current path to result list
            return;
        }
        for (int i = start; i < s.length(); i++) { // iterate from start to end of string s
            if (isPalindrome2(s.substring(start, i + 1))) { // if substring from start to i+1 is a palindrome
                path.add(s.substring(start, i + 1)); // add substring to current path
                dfs(s, lst, path, i + 1); // recurse with start index i+1
                path.remove(path.size() - 1); // remove substring from current path
            }
        }
    }
    public static boolean isPalindrome2(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) return false;
            i++; j--;
        }
        return true;
    }
}
