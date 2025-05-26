package Algorithms.MonotonicStack;

import java.util.Stack;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 01 March 2025
 */
public class DailyTemperatures {
    public static void main(String[] args) {
        int[] arr = {73, 74, 75, 71, 69, 72, 76, 73};
        System.out.println("dailyTemperatures: " + dailyTemperatures(arr));
    }

    public static int[] dailyTemperatures(int[] temperatures) {
        int[] answer = new int[temperatures.length];
        int stackIndex = 0;
        int[] stack = new int[temperatures.length];
        for (int i = 0; i < temperatures.length; i++) {
            while (stackIndex > 0 && temperatures[i] > temperatures[stack[stackIndex - 1]]) {
                answer[stack[stackIndex - 1]] = i - stack[stackIndex - 1];
                stackIndex--;
            }
            stack[stackIndex++] = i;
        }
        return answer;
    }

    static {
        for (int i = 0; i < 500; i++) {
            dailyTemperatures(new int[] {});
        }
    }
    public static int[] dailyTemperatures2(int[] temperatures) {
        int n = temperatures.length;
        int[] stack = new int[n];
        int top = -1;
        int[] result = new int[n];
        for(int i=0;i<n;i++){
            while (top>-1&&temperatures[i]>temperatures[stack[top]]) {
                int index=stack[top--];
                result[index] = i-index;
            }
            stack[++top]=i;
        }
        return result;
    }

    public int[] dailyTemperatures3(int[] temperatures) {
        Stack<Integer> stack = new Stack<>();
        int[] result = new int[temperatures.length];

        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int idx = stack.pop();
                result[idx] = i - idx;
            }
            stack.push(i);
        }

        return result;
    }
}
