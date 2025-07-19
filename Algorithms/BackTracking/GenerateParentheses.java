package Algorithms.BackTracking;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 17 Feb 2025
 * @link 22. Generate Parentheses <a href="https://leetcode.com/problems/generate-parentheses/">LeetCode link</a>
 * @topics String, Backtracking, Dynamic Programming, Divide and Conquer
 * @companies Amazon, Google, Zoho, Meta, Bloomberg, Microsoft, TikTok, Grammarly, Walmart Labs, Apple, Infosys, tcs, Flipkart, EPAM Systems, eBay, ServiceNow, Adobe, Yandex, Uber, Yahoo, Oracle, Goldman Sachs, Nvidia, Huawei, J.P. Morgan, DE Shaw
 */
@SuppressWarnings("all")
public class GenerateParentheses {
    public static void main(String[] args) {
        int n = 3;
        System.out.println("generateParenthesis using Brute Force isValid() => " + generateParenthesisUsingBruteForceIsValid(n));
        System.out.println("generateParenthesis using open & close counts => " + generateParenthesisUsingOpenAndCloseCounts(n));
        System.out.println("generateParenthesis using DivideAndConquer => " + generateParenthesisUsingDivideAndConquer(n));
        System.out.println("generateParenthesis using open & close counts 2 => " + generateParenthesisUsingOpenAndCloseCounts2(n));
        System.out.println("generateParenthesisMyApproach(n): " + generateParenthesisMyApproach(n));
    }


    /**
     * @TimeComplexity O(4ⁿ / √n)
     * @SpaceComplexity O(2n)

        This approach is same like {@link #generateParenthesisUsingBruteForceIsValid} or {@link #generateParenthesisUsingBruteForceIsValid2(int)}
        but here we use open and close counts instead of {@link #isValidUsingStack} or {@link #isValidUsingOpenCount} validation for each n size string 🔥 ---> performance boost

        we know that n=3 means
        List.get(i) --> str.length() == 2n = 2*3 = 6
        open = n = 3 -- openBrackets
        close = n = 3 -- closeBrackets
        i.e open == close == n == 3, then only a valid paranthesis is possible

        And we know that at any given point of time --- open >= close ---> i.e close never exceeds open 🔥

        so using these two below conditions we can maintain valid parenthesis instead of {@link #isValidUsingStack} or {@link #isValidUsingOpenCount}
        1) add "(" if open < n
        2) add ")" if close < open


       when n=3;
                                                       ""
                                           ____________|_____
                                           |                 |
                                          "("                ❌ close<open is false
                          _________________|_____________________________________________________________________________________________________
                          |                                                                                                                         |
                        "(("                                                                                                                       "()" c<o true
          ________________|___________________________________________                                                                 _____________|___________
          |                                                          |                                                                 |                       |
        "((("                                                      "(()" c<o true                                                     "()("                  ❌ c<o false
       ___|_____________                                   __________|___________________________________                    __________|__________
       |               |                                   |                                            |                    |                   |
  ❌ o<n false      "((()"                              "(()("                                       "(())" c<o true       "()(("              "()()" c<o true
               ________|________                 __________|_________                          __________|__________       __|______     _________|____
               |               |                 |                  |                          |                   |       |       |     |            |
             ❌ o<n false   "((())"             ❌ o<n false    "(()()" c<o true            "(())("          ❌ c<o f     ❌   "()(()" "()()("       ❌
                          _____|_____                         ______|______            ________|________                  _________|___     _|______________
                          |         |                         |           |            |                |                 |           |     |             |
                  ❌ o<n false  "((()))"             ❌ o<n false     "(()())" c<o t  ❌ o<n f      "(())()"            ❌        "()(())" ❌         "()()()"
                                   ✅                                    ✅                             ✅                           ✅                  ✅

     */
    public static List<String> generateParenthesisUsingOpenAndCloseCounts(int n) {
        List<String> lst = new ArrayList<>();
        backtrack(lst, "", 0, 0, n);
        return lst;
    }

    private static void backtrack(List<String> lst, String str, int open, int close, int n) {
        if (str.length() == 2*n) {
            lst.add(str);
            return;
        }
        if (open < n) backtrack(lst, str+"(", open+1, close, n); // or stringBuilder.append("("); && sb.deleteCharAt(sb.length()-1);
        if (close < open) backtrack(lst, str+")", open, close+1, n); // using close < open condition, we avoid not well-formed ones. So, we don't need to check sum
    }




    public static List<String> generateParenthesisUsingDivideAndConquer(int n) {
        if (n == 0) {
            return new ArrayList(Arrays.asList(""));
        }

        List<String> answer = new ArrayList();
        for (int leftCount = 0; leftCount < n; ++leftCount)
            for (String leftString : generateParenthesisUsingDivideAndConquer(leftCount))
                for (String rightString : generateParenthesisUsingDivideAndConquer(n - 1 - leftCount))
                    answer.add("(" + leftString + ")" + rightString);
        return answer;
    }








    public static List<String> generateParenthesisUsingOpenAndCloseCounts2(int n) {
        List<String> res = new ArrayList<>();
        genParenthesisHelper(res, new StringBuilder(), n, n);
        return res;
    }

    private static void genParenthesisHelper(List<String> res, StringBuilder sb, int open, int close){
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









    /**
     * @TimeComplexity O(2^2n * n)
     * @SpaceComplexity O(2^2n * n)
     */
    public static List<String> generateParenthesisUsingBruteForceIsValid(int n) {
        List<String> res = new ArrayList<>();
        backtrack(2*n, new StringBuilder("("), res); // we know that parentheses cannot be started with ")"
        return res;
    }
    private static void backtrack(int n, StringBuilder sb, List<String> res) {
        if(sb.length()==n) {
            if(isValidUsingStack(sb)) { // or #isValid
                res.add(sb.toString());
            }
            return;
        }

        backtrack(n, sb.append("("), res);
        sb.deleteCharAt(sb.length()-1);
        backtrack(n, sb.append(")"), res);
        sb.deleteCharAt(sb.length()-1);
    }
    private static boolean isValidUsingStack(StringBuilder sb) {
        Stack<Character> stack = new Stack<>();

        for(int i=0; i<sb.length(); i++) {
            if(sb.charAt(i) == '(') {
                stack.push('(');
            }
            else {
                if(stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }

        return stack.isEmpty();
    }








    public static List<String> generateParenthesisUsingBruteForceIsValid2(int n) {
        List<String> answer = new ArrayList<>();
        Queue<String> queue = new LinkedList<>(Arrays.asList("")); // same as above #backtrack() brute force method in BruteForce

        while (!queue.isEmpty()) {
            String curString = queue.poll();

            // If the length of curString is 2 * n, add it to `answer` if
            // it is valid.
            if (curString.length() == 2 * n) {
                if (isValidUsingOpenCount(curString)) {
                    answer.add(curString);
                }
                continue;
            }
            queue.offer(curString + ")");
            queue.offer(curString + "(");
        }

        return answer;
    }

    /**
     * We know that at any given point of time --- open >= close ---> i.e close never exceeds open
     */
    private static boolean isValidUsingOpenCount(String str) {
        int open = 0;
        for (char c : str.toCharArray()) {
            if (c == '(') {
                open++;
            } else {
                open--;
            }

            if (open < 0) { // or (openCount < closeCount)
                return false;
            }
        }
        return open == 0;
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
        for (int i=0; i<str.length(); i++) {
            sum += str.charAt(i)=='('?-1:1;
        }
        return sum;
    }
}
