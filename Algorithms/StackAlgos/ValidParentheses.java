package Algorithms.StackAlgos;

import java.util.HashMap;
import java.util.Stack;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 09 Jan 2025
 * @link 20. Valid Parentheses <a href="https://leetcode.com/problems/valid-parentheses/">LeetCode link</a>
 * @topics String, Stack
 * @companies Amazon, Google, Meta, Bloomberg, Microsoft, Intuit, Apple, Oracle, DE Shaw, Yandex, LinkedIn, Roblox, AT&T, Walmart Labs, Visa, TikTok, Deloitte, EPAM Systems, Goldman Sachs, Wix, Turing, Adobe, BlackRock, Zoho, tcs, ServiceNow, Infosys, Uber, J.P. Morgan, Epic Systems
 */
public class ValidParentheses {
    public static void main(String[] args) {
        String s = "()[]{}";
        System.out.println("isValid using Stack: " + isValidUsingStack(s));
        System.out.println("isValid using Stack & HashMap: " + isValidUsingStackAndHashMap(s));
        System.out.println("isValid using char array: " + isValidUsingCharArray(s));

    }

    public static boolean isValidUsingStack(String s) {
        if (s.length() % 2 != 0) return false;
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') stack.push(c);
            else if (stack.isEmpty()) return false;
            else if (c == ')' && stack.peek() == '(') stack.pop();
            else if (c == '}' && stack.peek() == '{') stack.pop();
            else if (c == ']' && stack.peek() == '[') stack.pop();
            else return false;
        }
        return stack.isEmpty();
    }




    public boolean isValidUsingStack2(String s) {
        if (s.length() % 2 != 0) return false;
        Stack<Character> stack = new Stack<>();
        for (int i=0; i<s.length(); i++) {
            char c1 = s.charAt(i);
            if (c1=='(' || c1=='{' || c1=='[') stack.push(c1);
            else {
                if (stack.empty()) return false;
                char c2 = stack.pop();
                if ( (c2 == '(' && c1 != ')') || (c2 == '{' && c1 != '}') || (c2 == '[' && c1 != ']') ) return false;
            }
        }
        return stack.empty();
    }







    static HashMap<Character, Character> mappings = new HashMap<>() {{
        put(')', '(');
        put('}', '{');
        put(']', '[');
    }};
    public static boolean isValidUsingStackAndHashMap(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (mappings.containsKey(c)) {
                if(stack.empty() || stack.pop()!=mappings.get(c)) {
                    return false;
                }
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }






    public static boolean isValidUsingCharArray(String s) {
        if (s.length() % 2 != 0) return false;

        char[] stack = new char[s.length()];
        int head = 0;

        for (char c : s.toCharArray()) {
            switch (c) {
                case '(':
                case '{':
                case '[':
                    stack[head++] = c;
                    break;
                case ')':
                    if (head == 0 || stack[--head] != '(') return false;
                    break;
                case '}':
                    if (head == 0 || stack[--head] != '{') return false;
                    break;
                case ']':
                    if (head == 0 || stack[--head] != '[') return false;
                    break;
            }
        }
        return head == 0;
    }







    /**
     * NOT WORKING ❌ ---> This open & close count approach will only work with one single type of parenthesis
     * test "([)]"
     * here parenthesisOpen == parenthesisClose and squareOpen == squareClose but that's not the valid one
     *
     * @see #isValidUsingOpenCountOfSingleType for more understanding ---> this is the intuition but it only works with one type of parenthesis
     * close never exceeds open in valid parenthesis
     */
    public static boolean isValidUsingOpenAndCloseCountsNotWorking(String s) {
        int parenthesisOpen = 0;
        int parenthesisClose = 0;
        int curlyOpen = 0;
        int curlyClose = 0;
        int squareOpen = 0;
        int squareClose = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                parenthesisOpen++;
            } else if (c == ')') {
                parenthesisClose++;
            } else if (c == '{') {
                curlyOpen++;
            } else if (c == '}') {
                curlyClose++;
            } else if (c == '[') {
                squareOpen++;
            } else if (c == ']') {
                squareClose++;
            }

            if (parenthesisOpen < parenthesisClose || curlyOpen < curlyClose || squareOpen < squareClose) {
                return false;
            }
        }
        return parenthesisOpen == parenthesisClose && curlyOpen == curlyClose && squareOpen == squareClose;
    }



    /**
     * We know that at any given point of time --- open >= close ---> i.e close never exceeds open
     */
    private static boolean isValidUsingOpenCountOfSingleType(String str) {
        int open = 0;
        int close = 0;
        for (char c : str.toCharArray()) {
            if (c == '(') {
                open++;
            } else {
                close++;
            }

            if (open < close) { // or if(c==')') open--; and open < 0
                return false;
            }
        }
        return open == close; // or if(c==')') open--; and open == 0
    }
}
