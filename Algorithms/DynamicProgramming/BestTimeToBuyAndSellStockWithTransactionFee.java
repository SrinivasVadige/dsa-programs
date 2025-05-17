package Algorithms.DynamicProgramming;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 16 May 2025
 */
public class BestTimeToBuyAndSellStockWithTransactionFee {
    public static void main(String[] args) {
        int[] prices = {1, 3, 2, 8, 4, 9};
        int fee = 2;
        System.out.println("maxProfitUsingBottomUpTabulationDp => " + maxProfitUsingBottomUpTabulationDp(prices, fee));
        System.out.println("maxProfitUsingTwoPointers => " + maxProfitUsingTwoPointers(prices, fee));

    }

    public static int maxProfitUsingBottomUpTabulationDp(int[] prices, int fee) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][0] = -prices[0];
        dp[0][1] = 0;
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + prices[i] - fee);
        }
        return dp[n - 1][1];
    }



    public static int maxProfitUsingTwoPointers(int[] prices, int fee){
        int n=prices.length, profit=0, effectiveBuyPrice=prices[0];
        for(int i=1; i<n; i++) {
            profit=Math.max(profit, prices[i]-effectiveBuyPrice-fee);
            effectiveBuyPrice=Math.min(effectiveBuyPrice, prices[i]-profit);
        }
        return profit;
    }



    public static int maxProfit3(int[] prices, int fee) {
        int n = prices.length;
        int buy = -prices[0]; // amount we spend
        int sell = 0;
        for (int i = 1; i < n; i++) {
            sell = Math.max(sell, buy + prices[i] - fee);
            buy = Math.max(buy, sell - prices[i]);
            // or buy and then sell
        }
        return sell;
    }



    public int maxProfit4(int[] prices, int fee) {
        int n = prices.length;
        int buy = prices[0]; // amount we spend
        int sell = 0;
        for (int i = 1; i < n; i++) {
            sell = Math.max(sell, prices[i] - buy - fee);
            buy = Math.min(buy, prices[i]-sell);
        }
        return sell;
    }



    public static int maxProfit5(int[] prices, int fee) {
        int buy = Integer.MIN_VALUE;
        int sell = 0;

        for (int price : prices) {
            buy = Math.max(buy, sell - price);
            sell = Math.max(sell, buy + price - fee);
        }

        return sell;
    }
}
