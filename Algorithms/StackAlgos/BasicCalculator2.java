package Algorithms.StackAlgos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.IntBinaryOperator;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 22 Oct 2025
 * @link 227. Basic Calculator II <a href="https://leetcode.com/problems/basic-calculator-ii/">LeetCode link</a>
 * @topics String, Stack, Math

        6 + 5 / 4 * 3 - 2 + 1

        num = 3, sign = -
        [6, +], [5, /], [4, *]

        num = 12, sign = -
        [6, +], [5, /]

        num = 0, sign = -
        [6, +],

        num = 6, sign = -
        ...

        num = 0, sign = +
        [6, -]

        num = 2, sign = +
        [6, -]

        num = 0, sign = +
        [4, +]

        add the last num too
 */
public class BasicCalculator2 {
    public static void main(String[] args) {
        String s = "3+5 / 2 ";
        System.out.println("calculate using getNextNum -> " + calculateUsingGetNextNum(s));
        System.out.println("calculate using stack ->" + calculateUsingStack(s));
    }


    static Map<Character, IntBinaryOperator> operations = Map.of(
        '+', Integer::sum,
        '-', (a, b) -> a - b,
        '*', (a, b) -> a * b,
        '/', (a, b) -> a / b
    );

    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int calculateUsingGetNextNum(String s) {
        // List<Integer> numsList = new ArrayList<>();
        int num = 0;
        int numSign = 1; // + is 1 and - is -1 ---> initial num & numSign is +0
        int totalSum = 0;

        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                continue;
            } else if (c == '+' || c == '-') {
                // numsList.add(num * numSign);
                totalSum += num * numSign;
                num = 0;
                numSign = c == '+' ? 1 : -1;
            } else if (c == '*' || c == '/') {
                int[] nextNum = getNextNum(s, i);
                num = operations.get(c).applyAsInt(num, nextNum[0]);
                i = nextNum[1];
            } else {
                num = num*10 + c - '0';
            }
        }
        // return numsList.stream().reduce(0, Integer::sum) + num * numSign;
        return totalSum + num * numSign;
    }
    private static int[] getNextNum(String s, int i) {
        int nextNum = 0;
        i++;
        for (; i<s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') continue;
            else if (operations.containsKey(c)) {
                i--;
                break;
            } else {
                nextNum = nextNum*10 + c - '0';
            }
        }
        return new int[]{nextNum, i};
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int calculateUsingStack(String s) { // calculateUsingStack
        int n = s.length();
        Stack<Integer> stack = new Stack<>();
        int num = 0;
        char prevOperation = '+'; // numSign
        for (int i=0; i<n; i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = (num * 10) + (c - '0');
            }
            // if (!Character.isDigit(c) && !Character.isWhitespace(c) || i == n-1) { // or
            if (operations.containsKey(c) || i == n-1) { // because (n-1) char can be digit too and prevOperation can be +, -, *, /
                if (prevOperation == '+') {
                    stack.push(num);
                }
                else if (prevOperation == '-') {
                    stack.push(-num);
                }
                else if (prevOperation == '*') {
                    stack.push(stack.pop() * num);
                }
                else if (prevOperation == '/') {
                    stack.push(stack.pop() / num);
                }
                prevOperation = c; // NOTE: c is currOperation and prevOperation is numSign
                num = 0;
            }
        }
        int result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int calculate2(String s) {
        int n = s.length();
        int currNum = 0, lastNum = 0, totalSum = 0; // lastNum is like a stack.pop()
        char operation = '+'; // or prevOperation or numSign
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                currNum = (currNum * 10) + (c - '0');
            }
            // if (!Character.isDigit(c) && !Character.isWhitespace(c) || i == n-1) { // or
            if (operations.containsKey(c) || i == n-1) {
                if (operation == '+' ) {
                    totalSum += lastNum;
                    lastNum = currNum;
                } else if (operation == '-') {
                    totalSum += lastNum;
                    lastNum = -currNum;
                } else if (operation == '*') {
                    lastNum = lastNum * currNum;
                } else if (operation == '/') {
                    lastNum = lastNum / currNum;
                }
                operation = c;
                currNum = 0;
            }
        }
        totalSum += lastNum;
        return totalSum;
    }








    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int calculateUsingStack2(String s) { // calculateUsingStack2
        Stack<Integer> numStack = new Stack<>();
        Stack<Character> signStack = new Stack<>();
        int num = 0;

        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                continue;
            } else if (c == '+' || c == '-') {
                num = eval(numStack, signStack, num);
                numStack.push(num);
                signStack.push(c);
                num = 0;
            } else if (c == '*' || c == '/') {
                int nextNum[] = getNextNum(s, i);
                num = operations.get(c).applyAsInt(num, nextNum[0]);
                i = nextNum[1];
            } else {
                num = num*10 + c - '0';
            }
        }

        return eval(numStack, signStack, num);
    }


    private static int eval (Stack<Integer> numStack, Stack<Character> signStack, int b) {
        while (!numStack.isEmpty()) {
            int a = numStack.pop();
            char sign = signStack.pop();
            b = operations.get(sign).applyAsInt(a,b);
        }
        return b;
    }
}
