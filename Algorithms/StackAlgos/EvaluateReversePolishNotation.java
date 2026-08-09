package Algorithms.StackAlgos;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.BinaryOperator;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 20 Oct 2025
 * @link 150. Evaluate Reverse Polish Notation <a href="https://leetcode.com/problems/evaluate-reverse-polish-notation/">LeetCode link</a>
 * @topics Array, Stack, Math

         Infix notation (normal operations), Postfix notations (Reverse Polish Notation)

        1) In RPN or postfix - we don't need any parenthesis ()
        2) No order of operators like * is preceding than +, / than * and so on
        3) Evaluate from left to right l -> r
        4) Evaluate only recent 2 numbers
 */
public class EvaluateReversePolishNotation {
    public static void main(String[] args) {
        String[] tokens = {"10","6","9","3","+","-11","*","/","*","17","+","5","+"};
        System.out.println("evalRPN -> " + evalRPN(tokens));
        System.out.println("evalRPN using BinaryOperator -> " + evalRPN_UsingBinaryOperator(tokens));
    }


    public static int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for(String token : tokens) {
            if ("+-*/".contains(token)) { // or token.length() == 1 && !Character.isDigit(token.charAt(0))
                int a = stack.pop();
                int b = stack.pop();
                int c = 0;
                if (token.equals("+")) {
                    c = b + a;
                } else if (token.equals("-")) {
                    c = b - a;
                } else if (token.equals("*")) {
                    c = b * a;
                } else {
                    c = b / a;
                }
                stack.push(c);
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }


    /**
     * We can use
     * @see java.util.function.BinaryOperator or
     * @see java.util.function.BiFunction
     */
    public static int evalRPN_UsingBinaryOperator(String[] tokens) {
        Map<String, BinaryOperator<Integer>> op = new HashMap<>(Map.of( // Map.of is unmodifiable map, so wrap it in new HashMap for mutability
            "+", (a, b) -> b + a,
            "-", (a, b) -> b - a,
            "*", (a, b) -> b * a,
            "/", (a, b) -> b / a
        ));

        Stack<Integer> stack = new Stack<>();

        for (String token : tokens) {
            if (op.containsKey(token)) {
                stack.push(op.get(token).apply(stack.pop(), stack.pop()));
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }





    public static int evalRPN2(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String token : tokens) {
            if (token.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if (token.equals("-")) {
                stack.push(-stack.pop() + stack.pop());
            } else if (token.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else if (token.equals("/")) {
                stack.push((int)(1.0 / stack.pop() * stack.pop()));
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }




    public static int evalRPN3(String[] tokens) {
        Stack<Integer> stack = new Stack<>();

        for (String c : tokens) {
            if (c.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if (c.equals("-")) {
                int second = stack.pop();
                int first = stack.pop();
                stack.push(first - second);
            } else if (c.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else if (c.equals("/")) {
                int second = stack.pop();
                int first = stack.pop();
                stack.push(first / second);
            } else {
                stack.push(Integer.parseInt(c));
            }
        }

        return stack.peek();
    }




    public static int evalRPN_UsingTryCatch(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for(String token : tokens) {
            try {
                stack.push(Integer.parseInt(token));
            } catch (Exception e) {
                int a = stack.pop();
                int b = stack.pop();
                int c = 0;
                if (token.equals("+")) {
                    c = b + a;
                } else if (token.equals("-")) {
                    c = b - a;
                } else if (token.equals("*")) {
                    c = b * a;
                } else {
                    c = b / a;
                }
                /*
                // or
                int c = switch (token) {
                    case "+" -> b + a;
                    case "-" -> b - a;
                    case "*" -> b * a;
                    default -> b / a;
                };
                 */
                stack.push(c);
            }
        }

        return stack.pop();
    }
}
