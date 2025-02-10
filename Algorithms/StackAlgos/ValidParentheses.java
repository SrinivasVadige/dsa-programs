package Algorithms.StackAlgos;

import java.util.Stack;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 09 Jan 2025
 */
public class ValidParentheses {
    public static void main(String[] args) {
        String s = "()[]{}";
        System.out.println("isValid: " + isValid(s));
    }

    public static boolean isValid(String s) {
        if (s.length() % 2 != 0) return false;
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') stack.push(c);
            else if (c == ')' && stack.peek() == '(') stack.pop();
            else if (c == '}' && stack.peek() == '{') stack.pop();
            else if (c == ']' && stack.peek() == '[') stack.pop();
            else return false;
        }
        return stack.isEmpty();
    }

    // or use hashMap with open and close chars as keys and values
    public boolean isValidMyApproach(String s) {
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

    public boolean isValidUsingArray(String s) {
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
}
