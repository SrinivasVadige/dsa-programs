package Algorithms.DynamicProgramming;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 May 2025
 *
 * Given   [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
 * Output: [6, 105, 5, 5, 4, 102, 3, 2, 101, 1]  ---> we just added cost[i] = cost[i] + Math.min(cost[i+1], cost[i+2])]
 */
public class MinCostClimbingStairs {
    public static void main(String[] args) {
        int[] cost = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        System.out.println("minCostClimbingStairs BottomUpTabulation DP 1: " + minCostClimbingStairsBottomUpTabulationDp(cost));
        System.out.println("minCostClimbingStairs BottomUpTabulation DP 2: " + minCostClimbingStairsBottomUpTabulationDp2(cost));
        System.out.println("minCostClimbingStairs BottomUpNoMemory DP: " + minCostClimbingStairsBottomUpNoMemoryDp(cost));
        System.out.println("minCostClimbingStairs TopDownMemo DP: " + minCostClimbingStairsTopDownMemoDp(cost));
    }


    public static int minCostClimbingStairsBottomUpTabulationDp(int[] cost) {
        int n = cost.length;
        int[] minCostDp = new int[n+1];
        for(int i=0; i<n; i++) minCostDp[i]=cost[i];
        for(int i=n-2; i>=0; i--) {
            minCostDp[i]+=Math.min(minCostDp[i+1], minCostDp[i+2]);
        }
        return Math.min(minCostDp[0], minCostDp[1]);
    }


    public static int minCostClimbingStairsBottomUpTabulationDp2(int[] cost) {
        int n = cost.length;
        for(int i=n-2; i>=0; i--) {
            cost[i]+=Math.min(cost[i+1], i+2==n?0:cost[i+2]);
        }
        return Math.min(cost[0], cost[1]);
    }


    public static int minCostClimbingStairsBottomUpNoMemoryDp(int[] cost) {
        int n = cost.length;
        int step1 = cost[n-1], step2 = 0;
        for(int i=n-2; i>=0; i--) {
            int curr = cost[i] + Math.min(step1, step2);
            step2=step1;
            step1=curr;
        }
        return Math.min(step1, step2);
    }

    public int minCostClimbingStairsBottomUpNoMemoryDp2(int[] cost) {
        if(cost.length == 1) return cost[0];
        if(cost.length == 2) return Math.min(cost[0], cost[1]);

        int first = cost[0];
        int second = cost[1];

        for(int i = 2; i < cost.length; i++) {
            int current = Math.min(first, second) + cost[i];
            first = second;
            second = current;
        }
        return Math.min(first, second);
    }


    public static int minCostClimbingStairsTopDownMemoDp(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE); // Use a sentinel value Eg: when cost=[0,0,0,0,0,0,0,0]
        return Math.min(helper(cost, n-1, dp), helper(cost, n-2, dp));
    }
    private static int helper(int[] cost, int i, int[] dp) {
        if(i < 0) return 0;
        if(dp[i] != Integer.MAX_VALUE) return dp[i];
        dp[i] = cost[i] + Math.min(helper(cost, i-1, dp), helper(cost, i-2, dp));
        return dp[i];
    }

}
