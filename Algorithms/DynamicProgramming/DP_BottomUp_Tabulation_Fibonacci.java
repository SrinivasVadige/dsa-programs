package Algorithms.DynamicProgramming;

import java.util.Arrays;

/**
 *
 * <p> smaller number to bigger number
 * <p> And it can be normal loop or recursion or recursion with loop
 *
 * @Approach Bottom Up Tabulation - add all the series/node values in an array
 * @TimeComplexity O(n)
 *
 * @see Algorithms.DynamicProgramming.DP_BottomUp_NoMemory_Fibonacci
 *
 *
 *
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
public class DP_BottomUp_Tabulation_Fibonacci {

    public static void main(String[] args) {
        int n = 5;
        fib(n);
        fib2(n);
    }

    // loop from 2 but add 0,1 in series manually
    public static int fib(int n) {
        int[] dp = new int[n + 1]; // or int dp[] = new int[2]; that just stores n-1 and n-2 node values instead of storing all node values but new int[2] needs to be updated everytime using a temp var then it would become Bottom Up NoMemory Approach
        dp[0] = 0;
        dp[1] = 1;
        for(int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2]; // we already have the all previous 2 values in dp array we are just adding them
            if(i == n) System.out.println("Bottom Up DP, loop from 2: " + Arrays.toString(dp)); // jsut for printing
        }
        return dp[n]; // root node value
    }

    // loop from 0 and have 0, 1 base cases in the loop itslef add the remaining series in dp array
    public static int fib2(int n) {
        int[] dp = new int[n+1];
        for (int i = 0; i <= n; i++) {
            if (i <= 1) {  // 0,1 are base cases
                dp[i] = i;
            } else {
                dp[i] = dp[i-1] + dp[i-2];
            }
            if(i == n) System.out.println("Bottom Up DP, loop from 0: " + Arrays.toString(dp)); // jsut for printing
        }
        return dp[n]; // root node value
    }

}
