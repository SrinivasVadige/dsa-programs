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


    /**
     * EFFECTIVE BUY PRICE:
     * --------------------
     * EFFECTIVE BUY PRICE = Amount we invested from our pocket to buy stocks
     *
     * EXAMPLE:
     * Buys and sell stocks in coins.
     *
     * Initially, we have lots of coins [c][c][c][c][c][c][c]......  in our pocket
     *
     * Eg: The stock market looks like [2, 5, 4, 6]
     *
     * Now buy 1st stock i.e price 2 == coins 2
     *
     * EXPLANATION 1:
     *
     * BUY #1
     * [c][c]       ---> here we invested two coins from our pocket, the effective buy price is 2
     *
     * SELL #1
     * [c][c][P1][P1][P1]      ---> here we made profit of 3 coins
     *
     * PROFIT #1 = [P1][P1][P1]
     *
     * BUY #2
     * [P1][P1][P1][C]         ---> we only invested one coin from our pocket --> the effective by price is 1, cause we invested the profit of 3 coins
     *
     * SELL #2
     * [P1][P1][P1][C][P2][P2]  ---> we made profit of 2 coins
     *
     * PROFIT #2 = [P2][P2]
     *
     * TOTAL PROFIT = PROFIT #1 + PROFIT #2 = [P1][P1][P1][P2][P2] = 5
     *
     *
     * EXPLANATION 2:
     *
     * [2, 5, 4, 6]
     *
     * BUY #1
     * [c][c]                   ---> B1=2 or EBP=2
     *
     * SELL #1
     * [c][c][P1][P1][P1]       ---> S1=B1+P1=5
     *
     * PROFIT #1 = [P1][P1][P1] ---> P1=3
     *
     * TOTAL PROFIT = P = S1-B1 = S1-EBP ✅
     *
     *
     *
     *
     * BUY #2
     * [P1][P1][P1][C]          ---> B2=4, B2=P1(3)+EBP(1) ---> EBP = B2-P1
     *
     * SELL #2
     * [P1][P1][P1][C][P2][P2]  ---> S2=B2+P2=6
     *
     * PROFIT #2 = [P2][P2]     ---> P2=2
     *
     * TOTAL PROFIT = P = S2-(EBP) or S2-(B2-P1) ✅
     *
     *
     * BUY #3
     *
     * Now, the total profit is P, EBP=B3-P -- i.e use TOTAL P ✅
     * So, EBP can be -ve sometimes, which is also valid
     *
     * In below example maintain min & max to check whether we need to buy or sell stocks at i
     *
     * Here EBP and i are 2 pointers
     *
     * And instead of "Total Profit P = S2-(EBP)" use "P=S2-EBP-fee"
     * same as {@link Algorithms.DynamicProgramming.BestTimeToBuyAndSellStockII#maxProfitUsingTwoPointers2(int[])}
     */
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
