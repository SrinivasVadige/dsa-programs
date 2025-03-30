package Algorithms.IntegerArray;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 30 March 2025
 *
 * We have n costs, n+1 people and now you're at nth index i.e n+1 place now check the min cost to swap for each index
 * If they are in front of you, you must pay them cost[i] to swap with them.
 * If they are behind you, they can swap with you for free.
 */
public class MinimumCostToReachEveryPosition {
    public static void main(String[] args) {
        int[] costs = {5,3,4,1,3,2}; // res -> {5,3,3,1,1,1}
        System.out.println("minCostMyApproach(costs) => " + minCostMyApproach(costs));
    }

    private static int[] minCostMyApproach(int[] costs) {
        int[] dp = new int[costs.length];
        dp[0] = costs[0];
        for (int i = 1; i < costs.length; i++) {
            dp[i] = Math.min(dp[i - 1], costs[i]);
        }
        return dp;
    }
}
