package Algorithms.MonotonicStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 26 May 2025
 * @link 901. Online Stock Span https://leetcode.com/problems/online-stock-span/
 */
public class OnlineStockSpan {
    public static void main(String[] args) {
        StockSpanner stockSpanner = new StockSpanner();
        System.out.println(stockSpanner.next(100)); // returns 1
        System.out.println(stockSpanner.next(80));  // returns 1
        System.out.println(stockSpanner.next(60));  // returns 1
        System.out.println(stockSpanner.next(70));  // returns 2
        System.out.println(stockSpanner.next(60));  // returns 1
        System.out.println(stockSpanner.next(75));  // returns 4
        System.out.println(stockSpanner.next(85));  // returns 6
    }

    // Using Monotonically Decreasing Stack --> stack.peek() i.e to one is the smallest ele
    static class StockSpanner {
        private Stack<int[]> st; // [price, span]

        public StockSpanner() {
            st = new Stack<>();
        }

        public int next(int price) {
            int span = 1; // Span starts at 1 (current day)

            while (!st.isEmpty() && price >= st.peek()[0]){ // Pop all prices from stack that are less than or equal to current price
                span += st.pop()[1]; // Add their span to current span
            }

            st.push(new int[]{price, span}); // curr (price, span) is smaller than "stack.peek()"

            return span;
        }
    }






    class StockSpanner2 {
        Stack<int[]> stack; // [day, price]
        int day;
        public StockSpanner2() {
            stack=new Stack<>();
            day=0;
        }

        public int next(int price) {
            while (!stack.isEmpty() && stack.peek()[1]<=price) {
                stack.pop();
            }
            int span=day+1;//If stack is empty!!
            if(!stack.isEmpty()) {
                span=day-stack.peek()[0];
            }
            stack.push(new int[]{day,price});
            day++;
            return span;
        }
    }







    class StockSpannerUsingNode {

        private static class Node {
            Node next;
            int height;
            int count;
            static Node LAST = new Node();
            static {
                LAST.height = 0x7FFF_FFFF; // or Integer.MAX_VALUE
                LAST.count = 0;
            }
        }
        private Node head = Node.LAST;

        public int next(int price) {
            insert(price);
            return head.count;
        }

        private void insert(int price) { // height == price
            if (price == head.height)
            head.count += 1;
            else {
                Node node = new Node();
                node.height = price;
                node.count = 1;
                while (node.height >= head.height) {
                    node.count += head.count;
                    head = head.next;
                }
                node.next = head;
                head = node;
            }
        }
    }











    class StockSpannerUsingIntArray {
        int[] arr = new int[10000];
        int idx = 0;
        public int next(int price) {
            arr[idx] = price;
            int count = 1;
            for(int i=idx-1; i>=0; i--) {
                if(arr[i] <= price) count++;
                else break;
            }
            idx++;
            return count;
        }
    }







    class StockSpannerUsingList {
        List<Integer> lst = new ArrayList<>();
        public int next(int price) {
            lst.add(price);
            int count = 0;
            for(int i=lst.size()-1;i>=0; i--) {
                if(lst.get(i)<=price) count++;
                else break;
            }
            return count;
        }
    }
}
