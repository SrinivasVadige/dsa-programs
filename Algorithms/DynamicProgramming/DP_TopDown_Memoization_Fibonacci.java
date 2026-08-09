package Algorithms.DynamicProgramming;

import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * <p> Bigger nodes to smaller (but inside recursion it'll return from smaller node value to bigger)
 * <p> It's exactly same as Recursive Backtracking DP, but here with memoization to skip already calculated nodes sub-problems
 *
 * @Approach Top Down DP with Memoization
 * @TimeComplexity O(n)
 * @SpaceComplexity O(n)
 *
 * @see <a href="/DataStructures/BinaryTree.java"> /DataStructures/BinaryTree.java </a>
 * @see <a href="./Fibonacci_DP_Recursive_Backtracking.java">./Fibonacci_DP_Recursive_Backtracking.java</a>
 *
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
public class DP_TopDown_Memoization_Fibonacci {

    public static void main(String[] args) {
        int n = 5; // root node

        // memoization with array
        int[] dp = new int[n+1]; // tree length
        fib(n, dp);
        dp[1] = 1; //-- just for printing. By default dp[0] = 0 i.e no need to initialize 0 leaf node value again
        System.out.println("Top Down DP with array: " + Arrays.toString(dp));

        // memoization with hashmap
        HashMap<Integer, Integer> dpMap = new HashMap<>();
        fib(n, dpMap);
        dpMap.put(0, 0); // just for printing as we don't initialize 0, 1 leaf node values in fib()
        dpMap.put(1, 1); // just for printing
        System.out.println("Top Down DP with hashmap: " + dpMap.values());
    }

    public static int fib(int n, int[] dp) {
        if(n == 0 || n == 1) return n; // base case for leaf nodes or dp[0] = 0, dp[1] = 1; -- here we don't initialize 0, 1 leaf node values in dp[0] & dp[1] 
        if(dp[n] != 0) return dp[n]; // return if already calculated where n is node & dp[n] is sum i.e node value
        dp[n] = fib(n - 1, dp) + fib(n - 2, dp); // min recursion for fib(n) is fib(2)
        return dp[n]; // return node value not the memo array
    }

    public static int fib(int n, HashMap<Integer, Integer> dp) {
        if(n == 0 || n == 1) return n; // base case for leaf nodes
        if(dp.containsKey(n)) return dp.get(n); // return if already calculated where n is node & dp.get(n) is sum i.e node value
        int result = fib(n - 1, dp) + fib(n - 2, dp);
        dp.put(n, result);
        return result; // return node value not the memo hashmap
    }






    // Codeium AI coding assistant solutions
    public static int fib(int n, int a, int b) {
        if(n == 0 || n == 1) return n;
        int c = a + b;
        if(n == 2) return c;
        return fib(n - 1, b, c) + fib(n - 2, a, b);
    }

    public static int fib(int n, int a, int b, int[] dp) {
        if(n == 0 || n == 1) return n;
        if(dp[n] != 0) return dp[n];
        dp[n] = fib(n - 1, b, a + b, dp) + fib(n - 2, a, b, dp);
        return dp[n];
    }



}
