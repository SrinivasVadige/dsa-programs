package Algorithms.MonotonicStack;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 01 March 2025
 * @link 739. Daily Temperatures https://leetcode.com/problems/daily-temperatures/
 *
 * Monotonic Stack is of two types: Increasing and Decreasing
 * 1. Monotonic Increasing Stack
 * 2. Monotonic Decreasing Stack
 *
 * It looks like minHeap and maxHeap, but it is not a heap. And we don't maintain the all elements
 * in the stack, we only maintain the indices of the elements that are only required i.e increasing or decreasing order.
 *
 * COMPARE: currEle with the top element of the stack
 * If the current element in Monotonic Increasing Stack violates the property (increasing / decreasing), we pop the elements until the current element is not violated
 * And then push the current element's index into the stack.
 *
 * NOTE:
 * 1. It's recommended to keep the indices of given array in the stack instead of the actual values.
 * 2. Using Monotonic Stack, we can solve the problem in O(n) time complexity instead of O(n^2) time complexity Brute Force approach.
 */
public class DailyTemperatures {
    public static void main(String[] args) {
        int[] arr = {73, 74, 75, 71, 69, 72, 76, 73};
        System.out.println("dailyTemperaturesMyApproach: " + Arrays.toString(dailyTemperaturesMyApproach(arr)));
        System.out.println("dailyTemperatures: " + Arrays.toString(dailyTemperatures(arr)));
    }


    /**
     * Same like nextGreaterElement problem
     */
    public static int[] dailyTemperaturesMyApproach(int[] temperatures) {
        Stack<Integer> stack = new Stack<>();
        int n = temperatures.length;
        int[] ans = new int[n];

        for(int i=n-1; i>=0; i--) { // or stack.push(n-1); for(int i=n-2; i>=0; i--) {
            int curr = temperatures[i];
            while(!stack.isEmpty() && temperatures[stack.peek()]<=curr) { // currEle violates the Monotonic Decreasing Stack property
                stack.pop();
            }

            int diff = stack.isEmpty()? 0 : stack.peek()-i;
            ans[i]=diff;

            stack.push(i);
            // or
            // if(stack.isEmpty()) stack.push(i);
            // else if(!stack.isEmpty() && temperatures[stack.peek()]>curr) stack.push(i);
        }
        return ans;
    }


    public static int[] dailyTemperatures(int[] temperatures) {
        Stack<Integer> stack = new Stack<>();
        int[] ans = new int[temperatures.length];

        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int idx = stack.pop();
                ans[idx] = i - idx;
            }
            stack.push(i);
        }

        return ans;
    }






    public static int[] dailyTemperatures2(int[] temperatures) {
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
            dailyTemperatures2(new int[] {});
        }
    }







    public static int[] dailyTemperatures3(int[] temperatures) {
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
}
