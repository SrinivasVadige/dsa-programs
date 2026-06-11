package Algorithms.DynamicProgramming;


import java.util.HashMap;
import java.util.Map;

/**
    Can only move 1 step or 2 steps at a time

                                                 n=5
                                    4_____________|________________3
                         3__________|__________2         2_________|_______1
               2_________|_____1           1___|___0  1___|___0
       1_______|______0      0  -1       0  -1


    how many times we got 1?

    no memo --- as we need all cases i.e min and max

    rec backtracking ---- TLE -> memo??

    => we already know

    ways for reaching 1  =>
    3,2,1
    3,1

    so, from 3 we can see that it has two ways --> memo the ways??

    imagine 3 as a graph



 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 15 Oct 2024
 * @link 70. Climbing Stairs <a href="https://leetcode.com/problems/climbing-stairs/">LeetCode link</a>
 * @topics Math, Dynamic Programming, Memoization
 * @companies Amazon(9), Bloomberg(7), Google(5), Microsoft(4), Meta(2), Infosys(2), Deloitte(2), Zoho(3), Media.net(3), AMD(2), Accenture(13), Grammarly(11), TikTok(8), TCS(6), Apple(5), Goldman Sachs(5), IBM(4), Adobe(3), Oracle(3), Nvidia(3)
 */
public class ClimbingStairs {

    public static void main(String[] args) {
        System.out.println("climbStairsBottomUpTabulationDp: " + climbStairsUsingBottomUpTabulationDp(5));
        System.out.println("climbStairsUsingBottomUpNoMemoryDp: " + climbStairsUsingBottomUpNoMemoryDp(5));
        System.out.println("climbStairsTopDownMemoDp: " + climbStairsUsingTopDownMemoDp1(5));
        System.out.println("climbStairsUsingRecurseBacktracking: " + climbStairsUsingRecurseBacktracking1(5));
    }

    /**
        <pre>


                         8 ---> num of ways to get here -> Eg: to get to step2, we can come from step0 or step1
                      5  __
                   3  __|  |
                2  __|  |  |
             1  __|  |  |  |
          1  __|  |  |  |  |
         🚶🏻‍➡️ |__|__|__|__|__|
          0   1  2  3  4  5  ---> steps

        dp[i] -> num of ways to get to here to stepI

        If you notice dp[0]=1 & dp[1]=1
        that means there is exactly 1 way to be at step 0.
        At first, that sounds weird because you're thinking: "I didn't climb anything. How can there be 1 way?"

        In DP, we count the empty way (doing nothing) as one valid way.
        How many ways are there to reach step 0?
        Do nothing ✅ ---> That's exactly 1 way

        so, think dp[i] like "How many distinct paths end at step i"
        For step 0, there is one path: No jumps taken - That empty path counts as one valid path.

        How many steps did I climb?     0
        How many ways can I be at the start?	1

        Example:
        How many ways are there to choose nothing from a set {A, B, C} ?
        Most people initially say: 0 ways
        But in combinatorics the answer is: 1 way -> cause, the way is: {}


        or

        Instead of all these non sense, just use dp[1]=1, dp[2]=2 and continue the for loop from i=3
        and if (n <= 2) return n;
        but not uniform occurrence as dp[2] = dp[1] + dp[0]

        and it looks exactly like fibonacci series

        </pre>
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n+1) // for O(n) use if condition to return 1 in the base case
     */
    public static int climbStairsUsingBottomUpTabulationDp(int n) {
        // dp[i] -> num of ways to get here to stepI
        int[] dp = new int[n+1]; // n+1 because dp[n] will work for n and will also work for n-2 logic
        dp[0] = 1; // cause from 2 we can like 1,1 or 2,1 i.e 2 ways i.e n-1, n-1 or n-2, 0
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }




    /**
        same as above {@link #climbStairsUsingBottomUpTabulationDp} but
        as we need only previous two values then use 2 variables - prev and curr

     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int climbStairsUsingBottomUpNoMemoryDp(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        int prev = 1, curr = 1;
        for (int i = 2; i <= n; i++) {
            int temp = curr;
            curr = prev + curr;
            prev = temp;
        }
        return curr;
    }





    static Map<Integer, Integer> memo = new HashMap<>();
    /**
     * @TimeComplexity O(n) - not O(2^n) cause we are saving the results in memo
     * @SpaceComplexity O(n)
     */
    public static int climbStairsUsingTopDownMemoDp1(int n) {
        memo.put(1, 1);
        memo.put(2, 2);
        return topDownDp(n);
    }
    public static int topDownDp(int n) {
        if (memo.containsKey(n)) return memo.get(n);
        int total = topDownDp(n-1) + topDownDp(n-2);
        memo.put(n, total);
        return total;
    }



    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int climbStairsUsingTopDownMemoDp2(int n) {
        int[] dp = new int[n];
        return rec(n, dp);
    }
    public static int rec(int n, int[] dp){
        if(n < 0) return 0; //bc
        else if(n==0) return 1;
        if(dp[n-1] !=0 ) return dp[n-1]; // -1 dp length is n. So, save from n-1 to 0 or n to 1 by setting dp length ti n+1        
        return dp[n-1] = rec(n-1, dp) + rec(n-2, dp);
    }






     static int count = 0;
    /**
     * TLE
     *
     * @TimeComplexity O(2^n)
     * @SpaceComplexity O(1)
     */
    public static int climbStairsUsingRecurseBacktracking1(int n) {
        backtrack(n);
        return count;
    }
    public static void backtrack(int n) {
        if (n < 0) return;
        else if (n == 0) count++;

        backtrack(n-1);
        backtrack(n-2);
    }



    /**
     * TLE
     *
     * @TimeComplexity O(2^n)
     * @SpaceComplexity O(1)
     */
    public int climbStairsUsingRecurseBacktracking2(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        return climbStairsUsingRecurseBacktracking2(n-1) + climbStairsUsingRecurseBacktracking2(n-2);
    }
}
