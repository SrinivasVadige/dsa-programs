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
        System.out.println("calculate using recursion -> " + calculateUsingRecursion1(s));
        System.out.println("calculate using stack 1 -> " + calculateUsingStack1(s));
        System.out.println("calculate using stack 2 -> " + calculateUsingStack2(s));
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n) - recursion stack space
     */
    public static int calculateUsingRecursion1(String s) {
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

                1-(2+3-(4+(5-(1-(2+4-(5+6))))))


     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int calculateUsingStack1(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c != ' ') sb.append(String.valueOf(c));
        }
        s = sb.toString();

        Stack<String> stack = new Stack<>();
        int n = s.length();

        for (int i=n-1; i>=0; i--) {
            char c = s.charAt(i);
            if (c == ' ' || c == '+') {
                continue;
            } else if (c == ')') {
                stack.push(String.valueOf(c));
            } else if (Character.isDigit(c)) {
                StringBuilder num = new StringBuilder();
                while (i >= 0 && Character.isDigit(s.charAt(i)) ) {
                    num.append(s.charAt(i));
                    i--;
                }
                if (i >= 0 && s.charAt(i) == '-') num.append('-');
                stack.push(num.reverse().toString());
                i++;
            } else if (c == '(') {
                evaluate(stack);
                if (i-1 >= 0 && s.charAt(i-1) == '-') {
                    int top = -1 * Integer.parseInt(stack.pop());
                    stack.push(String.valueOf(top));
                }
            }
        }
        evaluate(stack);
        return Integer.parseInt(stack.pop());
    }
    private static void evaluate(Stack<String> stack) {
        int res = 0;
        while (!stack.isEmpty() && stack.peek().charAt(0) != ')') {
            int num = Integer.parseInt(stack.pop());
            res += num;
        }
        if (!stack.isEmpty() && stack.peek().charAt(0) == ')') stack.pop();
        stack.push(String.valueOf(res));
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int calculateUsingStack2(String s) {
        Stack<Integer> stack = new Stack<>(); //                ===> (RESULT, SIGN)

        int result = 0; // For the on-going result              ===> RESULT TILL NOW IN CLOSED BLOCK ()
        int number = 0; //                                      ===> PREV NUM
        int sign = 1; // 1 means positive, -1 means negative    ===> PREV SIGN

        for (char c : s.toCharArray()) {

            if (Character.isDigit(c)) {
                number = number * 10 + (c - '0'); // Forming operand, since it could be more than one digit
            } else if (c == '+') {
                result += sign * number;    // Evaluate the expression to the left
                number = 0;                 // Reset operand
                sign = 1;                   // Save the recently encountered '+' sign
            } else if (c == '-') {
                result += sign * number;
                number = 0;
                sign = -1;
            } else if (c == '(') {          // Push the result and sign on to the stack, for later. We push the result first, then sign
                stack.push(result);
                stack.push(sign);

                result = 0;                 // Reset operand and result, as if new evaluation begins for the new sub-expression
                sign = 1;
            } else if (c == ')') {
                result += sign * number;    // Evaluate the expression to the left
                number = 0;

                result *= stack.pop(); // previous sign
                result += stack.pop(); // previous result
            }
        }

        return result + sign * number;
    }







    public static int calculateUsingStack3(String s) { // String Reversal

        int operand = 0;
        int n = 0;
        Stack<Object> stack = new Stack<Object>();

        for (int i = s.length() - 1; i >= 0; i--) {

            char ch = s.charAt(i);

            if (Character.isDigit(ch)) {
                operand = (int) Math.pow(10, n) * (int) (ch - '0') + operand; // Forming the operand - in reverse order.
                n += 1;

            } else if (ch != ' ') {
                if (n != 0) {
                    stack.push(operand); // Save the operand on the stack. As we encounter some non-digit.
                    n = 0;
                    operand = 0;

                }
                if (ch == '(') {
                    int res = evaluateExpr(stack);
                    stack.pop();
                    stack.push(res); // Append the evaluated result to the stack. This result could be of a sub-expression within the parenthesis.

                } else {
                    stack.push(ch); // For other non-digits just push onto the stack.
                }
            }
        }

        if (n != 0) { //Push the last operand to stack, if any.
            stack.push(operand);
        }

        return evaluateExpr(stack); // Evaluate any left overs in the stack.
    }

    public static int evaluateExpr(Stack<Object> stack) {
        if (stack.empty() || !(stack.peek() instanceof Integer)) { // i.e. [1, '-', 2, '-'] becomes [1, '-', 2, '-', 0]
            stack.push(0);
        }

        int res = (int) stack.pop();
        while (!stack.empty() && !((char) stack.peek() == ')')) { // Evaluate the expression till we get corresponding ')'
            char sign = (char) stack.pop();
            if (sign == '+') {
                res += (int) stack.pop();
            } else {
                res -= (int) stack.pop();
            }
        }
        return res;
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




    /**


        1-(2+3-(4+(5-(1-(2+4-(5+6))))))


        (1+(47+5+2)-3)-(6+8)

        (1+54-3)-(6+8)

        52-(6+8)

        52-14


        1-(-2)

        1 - ( - 2

     */
    public int calculate_NOT_WORKING(String s) {
        Stack<String> stack = new Stack<>();
        int i = 0, n = s.length();
        while (i < n) {
            char c = s.charAt(i);
            if (c == ' ') {
                i++;
                continue;
            } else if (c == '(' || c == '+' || c == '-') {
                stack.push(String.valueOf(c));
            } else if (c == ')') {
                evaluate_NOT_WORKING(stack);
            }

            StringBuilder num = new StringBuilder();
            while (i<n && s.charAt(i)-'0' >= 0 && s.charAt(i)-'0' <= 9) {
                num.append(s.charAt(i));
                i++;
            }

            if (num.length() != 0) {
                stack.push(num.toString());
            }
            else i++;
        }

        evaluate_NOT_WORKING(stack);
        return Integer.parseInt(stack.pop());
    }
    /**

        1+54-3+2

        1 - ( - 2

     */
    private void evaluate_NOT_WORKING(Stack<String> stack) {
        if (stack.isEmpty() || stack.size()==1) return;
        int res = 0;

        while (!stack.isEmpty() && stack.peek().charAt(0) != '(') {
            String s = stack.pop();
            if (s.charAt(0)-'0' >= 0 && s.charAt(0)-'0' <= 9 || !stack.isEmpty() && stack.peek().charAt(0) == '-' && s.charAt(1)-'0' >= 0 && s.charAt(1)-'0' <= 9) {
                int sign = 1;
                if (!stack.isEmpty() && stack.peek().charAt(0) == '-') {
                    stack.pop();
                    sign = -1;
                }
                res += sign * Integer.parseInt(s);
            }


        }
        if (!stack.isEmpty() && stack.peek().charAt(0) == '(') {
            stack.pop();
        }
        stack.push(String.valueOf(res));
    }
}
