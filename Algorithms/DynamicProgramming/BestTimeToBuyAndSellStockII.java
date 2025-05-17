package Algorithms.DynamicProgramming;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 16 May 2025
 */
public class BestTimeToBuyAndSellStockII {
    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println("maxProfitUsingTwoPointersMyApproach => " + maxProfitUsingTwoPointersMyApproach(prices));
        System.out.println("maxProfitUsingBottomUpTabulationDp => " + maxProfitUsingBottomUpTabulationDp(prices));
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     *
     * This approach is same as {@link Algorithms.DynamicProgramming.BestTimeToBuyAndSellStock#maxProfitUsingTwoPointers(int[])}
     * But here add profit instead of getting maxProfit
     */
    public static int maxProfitUsingTwoPointersMyApproach(int[] prices) {
        int profit=0, min=prices[0]; // l pointer

        for(int i=1; i<prices.length; i++) { // r pointer
            if(prices[i]<min) min=prices[i];
            else {
                profit += prices[i]-min;
                min=prices[i];
            }
        }
        return profit;
    }




    public int maxProfit(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            // If today's price is higher than yesterday's, we take the profit
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
        return profit;
    }



    public static int maxProfitUsingTwoPointers2(int[] prices) {
        int n = prices.length;
        int profit = 0, effectiveBuyPrice = prices[0];
        for (int i = 1; i < n; i++) {
            profit = Math.max(profit, prices[i] - effectiveBuyPrice);
            effectiveBuyPrice = Math.min(effectiveBuyPrice, prices[i] - profit);
        }
        return profit;
    }





    public static int maxProfitUsingBottomUpTabulationDp(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][0] = -prices[0];
        dp[0][1] = 0;
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + prices[i]);
        }
        return dp[n - 1][1];
    }




    public int maxProfitUsingBacktracking(int[] prices) {
        int n = prices.length;
        Integer[][] dp = new Integer[n][2];
        return backtrack(prices,0,1,dp);
    }
    public int backtrack(int[] prices,int ind,int buy,Integer[][] dp) {
        if(ind==prices.length)
        return 0;
        if(dp[ind][buy]!=null)
        return dp[ind][buy];
        int profit = 0;
        if(buy==1) profit = Math.max(-prices[ind] + backtrack(prices,ind+1,0,dp),backtrack(prices,ind+1,1,dp));
        else profit = Math.max(prices[ind] + backtrack(prices,ind+1,1,dp),backtrack(prices,ind+1,0,dp));

        return dp[ind][buy] = profit;
    }
}
