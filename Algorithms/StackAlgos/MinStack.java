package Algorithms.StackAlgos;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 02 March 2025
 * @link 155. Min Stack <a href = "https://leetcode.com/problems/min-stack"> LeetCode Link </a>
 * @topics Array, Stack, Design
 */
public class MinStack {
    public static void main(String[] args) {
        MinStackUsingIntArray minStack = new MinStackUsingIntArray();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.top());
        System.out.println(minStack.getMin());
    }


    static class MinStackUsingIntArray {

        Stack<int[]> stack;

        public MinStackUsingIntArray() {
            stack = new Stack<>();
        }

        public void push(int val) {
            stack.push(new int[]{val, stack.isEmpty()? val : Math.min(stack.peek()[1], val)}); // or  Math.min((stack.isEmpty()? val : stack.peek()[1]), val)
        }

        public void pop() {
            stack.pop();
        }

        public int top() {
            return stack.peek()[0];
        }

        public int getMin() {
            return stack.peek()[1];
        }
    }




    /**
     * Maintain the min values of the original stack at that specific point of push() method in a separate stack -- minStack
     */
    static class MinStackUsingExtraMinStack {
        Stack<Integer> stack = new Stack<>(); // k=number, v=minAtThatPoint
        Stack<Integer> minStack = new Stack<>(); // k=number, v=minAtThatPoint

        public void push(int val) {
            stack.push(val);
            minStack.push(Math.min(minStack.isEmpty()? val: minStack.peek(), val));
        }

        public void pop() {
            stack.pop();
            minStack.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }






    static class MinStackUsingAbstractMap {
        Stack<Map.Entry<Integer,Integer>> stack = new Stack<>(); // k=number, v=minAtThatPoint

        public void push(int val) {
            int minVal = stack.isEmpty()? val: stack.peek().getValue();
            minVal = Math.min(minVal, val); //check if curr val is min or not
            stack.push(new AbstractMap.SimpleEntry<>(val, minVal));
        }

        public void pop() {
            stack.pop();
        }

        public int top() {
            return stack.peek().getKey();
        }

        public int getMin() {
            return stack.peek().getValue();
        }

    }





    static class MinStackUsingTreeMap {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        Stack<Integer> stack = new Stack<>();

        public void push(int val) {
            stack.push(val);
            map.merge(val, 1, Integer::sum);
        }

        public void pop() {
            int num = stack.pop();
            if (map.get(num)==1) map.remove(num);
            else map.put(num, map.get(num)-1);
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return map.firstKey();
        }
    }




    static class MinStackUsingMinHeap {
        PriorityQueue<Integer> pq=new PriorityQueue<>();
        Stack<Integer> stk=new Stack<>();

        public void push(int val) {
            pq.add(val);
            stk.push(val);
        }

        public void pop() {
            pq.remove(stk.peek());
            stk.pop();
        }

        public int top() {
            return stk.peek();
        }

        public int getMin() {
            return pq.isEmpty()? Integer.MAX_VALUE : pq.peek();
        }
    }
}

