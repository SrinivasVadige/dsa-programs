package Algorithms.BackTracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 17 Feb 2025
 */
@SuppressWarnings("all")
public class GenerateParentheses {
    public static void main(String[] args) {
        int n = 3;
        System.out.println("generateParenthesisMyApproach(n): " + generateParenthesisMyApproach(n));
        System.out.println("generateParenthesis(n): " + generateParenthesis(n));
    }

    /**
     * THOUGHTS
     * --------
       1) Open parentheses === Closed Parentheses == n
       2) May be consider "(" as -1 and ")" as 1 then final sum == 0
       3) Stop backtrack if you have +ve count in total i.e ")" --> cause it cannot be a well-formed one
       4) We need an "need" extra var to count number of "(" and str.length == 2*n;
       5) So, it's like a binary tree recursion. No need for dp memo, so just backtrack
       6) when n==0 then just add ")" until the sum <=0

                                                       ""
                                    ____________________|_____________________
                                   |                                          |
                                   "("                                       ")"
                         __________|__________                                ❌
                        |                     |
                      "()"                    "(("
                 _______|_______     __________|_________
                 |             |     |                  |
                "()("        "())"  "((("              "(()"
          _______|_______     ❌  ____|____        _____|_____
          |             |         |        |       |          |
        "()(("         "()()"   "(((("    "((()"  "(()("    "(())"

     */
    public static List<String> generateParenthesisMyApproach(int n) {
        List<String> lst = new ArrayList<>();
        backtrack(n, n-1, -1, lst, "("); // we already know that ")" is not well-formed
        return lst;
    }

    private static void backtrack(int n, int openNeed, int sum, List<String> lst, String str) {
        if (sum > 0) return; // stopping not well-formed ones
        if (sum == 0 && openNeed == 0) {
            lst.add(str);
            return;
        }
        if (openNeed > 0) backtrack(n, openNeed-1, sum-1, lst, str+"(");
        backtrack(n, openNeed, sum+1, lst, str+")");
    }

    // for easy understanding of above sum
    private static int sum(String str) {
        int sum = 0;
        for (int i=0; i<str.length(); i++) sum += str.charAt(i)=='('?-1:1;
        return sum;
    }





    public static List<String> generateParenthesis(int n) {
        List<String> lst = new ArrayList<>();
        backtrack(lst, "", 0, 0, n);
        return lst;
    }

    private static void backtrack(List<String> lst, String str, int open, int close, int n) {
        if (str.length() == 2*n) {
            lst.add(str);
            return;
        }
        if (open < n) backtrack(lst, str+"(", open+1, close, n);
        if (close < open) backtrack(lst, str+")", open, close+1, n); // using close < open condition, we avoid not well-formed ones. So, we don't need to check sum
    }



    public List<String> generateParenthesis2(int n) {
        List<String> res = new ArrayList<>();
        genParenthesisHelper(res, new StringBuilder(), n, n);
        return res;
    }

    private void genParenthesisHelper(List<String> res, StringBuilder sb, int open, int close){
        if(close < open) return; // i.e always closeNeed >= openNeed
        if(close == 0 && open == 0) res.add(sb.toString());

        if(open > 0){
        sb.append("(");
        genParenthesisHelper(res, sb, open-1, close);
        sb.deleteCharAt(sb.length()-1);
        }
        if(close > 0){
            sb.append(")");
            genParenthesisHelper(res, sb, open, close-1);
            sb.deleteCharAt(sb.length()-1);
        }
    }
}
