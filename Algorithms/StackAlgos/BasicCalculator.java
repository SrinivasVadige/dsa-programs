package Algorithms.StackAlgos;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 21 Oct 2025
 * @link 224. Basic Calculator <a href="https://leetcode.com/problems/basic-calculator/">LeetCode link</a>
 * @topics String, Stack, Recursion, Math

        (3-(5-(8)-(2+(9-(0-(8-(2))))-(4))-(4)))


        (3-(5-(8)-(2+(9-(0-(8-(2))))-(4))-(4)))
        (3-(5-(8)-(2+(9-(0-(6)))-(4))-(4)))
        (3-(5-(8)-(2+(9-(-6))-(4))-(4)))
        (3-(5-(8)-(2+(9+6)-(4))-(4)))
        (3-(5-(8)-(2+(15)-(4))-(4)))
        (3-(5-(8)-(17-(4))-(4)))
        (3-(5-(8)-(13)-(4)))
        (3-(5-8-13-4))
        (3-(5-8-13-4))
        (3-(-20))
        (3+20)
        23
 */
public class BasicCalculator {
    public static void main(String[] args) {
        String s = "(11+(4+5+2)-3-(1+2))+(6+8)";
        System.out.println("calculate using recursion -> " + calculateUsingRecursion(s));
        System.out.println("calculate using stack -> " + calculateUsingStack(s));
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int calculateUsingRecursion(String s) {
        return calculateSingleParentheses(s, 0)[1];
    }
    private static int[] calculateSingleParentheses(String s, int i) { // Only calculate one small parentheses part () at a time
        int sum = 0;
        int num = 0;
        int sign = 1; // sign for next Operation

        for (; i<s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                continue;
            } else if (c == '+' || c == '-') {
                sum += sign * num;
                num = 0;
                sign = c == '-' ? -1 : 1;
            } else if (c == '(') {
                int[] arr = calculateSingleParentheses(s, i+1);
                i = arr[0];
                sum += sign * arr[1];
            } else if (c == ')' ) {
                break;
            } else {
                num = num*10 + c - '0';
            }
        }

        sum += sign * num;

        return new int[]{i, sum};
    }










    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int calculateUsingStack(String s) {

        Stack<Integer> stack = new Stack<>();
        int sum = 0, num = 0, sign = 1;

        for(char c: s.toCharArray()) {
            // System.out.println(stack);

            if(c == '+') {
                sum += sign * num;
                num = 0;
                sign = 1;
            } else if(c == '-') {
                sum += sign * num;
                num = 0;
                sign = -1;
            } else if(c == '(') {
                stack.add(sum);
                stack.add(sign);
                sum = 0;
                sign = 1;
            } else if(c == ')') {
                sum += sign * num;
                num = 0;
                sum *= stack.pop(); // sign
                sum += stack.pop(); // sum
            } else if(c != ' ') { // c >= '0' && c <= '9'
                num = num * 10 + (c - '0');
            }
        }


        return sum += sign * num;
    }







    static int idx = 0;
    public static int calculateUsingRecursion2(String s) {
        return calc(s);
    }
    private static int calc(String s){
        int sum = 0, num = 0, sign = 1;
        while(idx<s.length()){
            char c = s.charAt(idx++);
            if(c >= '0' && c <= '9'){
                num = num * 10 + c - '0';
            } else if(c == '('){
                num = calc(s);
            } else if(c == ')'){
                return sum += num*sign;
            } else if(c =='+' || c == '-') {
                sum += num*sign;
                num = 0;
                sign = c == '+' ? 1 : -1;
            }
        }
        sum += num*sign;
        return sum;
    }






    /**

        Here we just need to focus only on the -(a+b) cause +(a+b) is same as a+b

     */
    public static int calculateUsingSelfRecursionTLE(String s) {
        StringBuilder newS = new StringBuilder();
        for(char c : s.toCharArray()) {
            if (c != ' ') newS.append(c);
        }
        s = newS.toString();
        // System.out.println(s);
        int n = s.length();
        int sum = 0;
        StringBuilder num = new StringBuilder();
        for (int i=0; i<n; i++) {
            // System.out.printf("i:%s, c:%s, num:%s, sum:%s\n", i, s.charAt(i), num, sum);
             if (i+1 < n && s.charAt(i) == '-' && s.charAt(i+1) == '(') {
                i += 2;
                int open = 1;
                int close = 0;
                StringBuilder sb = new StringBuilder();
                for (; i<n; i++) {
                    if (s.charAt(i) == '(') open++;
                    else if (s.charAt(i) == ')') close++;
                    if (open == close) break;
                    sb.append(s.charAt(i));
                }
                sum -= calculateUsingSelfRecursionTLE(sb.toString());
            } else if (Arrays.asList('+', '-', '(', ')', ' ').contains(s.charAt(i))) {
                if (num.length() !=0 ) {
                    sum += Integer.parseInt(num.toString());
                    num = new StringBuilder();
                }
                if (s.charAt(i) == '-') num.append('-');
            } else {
                num.append(s.charAt(i));
            }
        }

        if (num.length() !=0 ) {
            sum += Integer.parseInt(num.toString());
        }

        return sum;
    }
}
