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
        System.out.println("maxProfitUsingTwoPointersEffectiveBuyPrice2 => " + maxProfitUsingTwoPointersEffectiveBuyPrice2(prices));
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


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     *
     *
     * EFFECTIVE BUY PRICE:
     * --------------------
     * EFFECTIVE BUY PRICE = Amount we invested from our pocket to buy stocks
     *
     * 🔥 To calculate profit while using EBP approach
     * we just do TotalProfit = (CurrSellAmount-EBP) ✅
     * This TotalProfit is profit till now.
     * So, no need to sum up profit in every sell, like we usually do i.e totalProfit+=profit ❌
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
     * EXAMPLE WITH EXPLANATION 1
     * [2, 5, 4, 6]
     * ---------- "At Starting point, BUY == EFFECTIVE BUY PRICE" ----------
     *
     * EBP = 🟠🟠
     * BUY #1
     * [c][c]       ---> here we invested two coins from our pocket, the effective buy price is 2
     * 🟠🟠
     *
     * SELL #1
     * [c][c][P1][P1][P1]      ---> here we made profit of 3 coins
     * 🟠🟠 🟢  🟢  🟢
     *
     * PROFIT #1 = [P1][P1][P1]
     *              🟢🟢🟢
     *
     *
     *
     * EBP = 🟠
     * BUY #2
     * [P1][P1][P1][C]         ---> we only invested one coin from our pocket --> the effective by price is 1, cause we invested the profit of 3 coins
     * 🟢  🟢  🟢 🟠
     *
     * SELL #2
     * [P1][P1][P1][C][P2][P2]  ---> we made profit of 2 coins
     * 🟢  🟢  🟢 🟠 🟢 🟢
     *
     * PROFIT #2 = [P2][P2]
     *
     * TOTAL PROFIT = PROFIT #1 + PROFIT #2 = [P1][P1][P1][P2][P2] = 5
     *                                        🟢  🟢  🟢 🟢  🟢
     *
     *
     *
     * SAME EXAMPLE WITH DIFFERENT EXPLANATION :
     * [2, 5, 4, 6]
     * ---------- "At Starting point, BUY == EFFECTIVE BUY PRICE" ----------
     *
     * EBP = 🟠🟠
     * BUY #1
     * [c][c]                   ---> B1=2 or EBP=2
     * 🟠 🟠
     *
     * SELL #1
     * [c][c][P1][P1][P1]       ---> S1=B1+P1=5
     * 🟠 🟠 🟢 🟢 🟢
     *
     * PROFIT #1 = [P1][P1][P1] ---> P1=3
     *
     * TOTAL PROFIT = P = [P1][P1][P1] = S1-B1 = S1-EBP ✅
     *                     🟢 🟢 🟢
     *
     *
     *
     * EBP = 🟠
     * BUY #2
     * [P1][P1][P1][C]          ---> B2=4, B2=P1(3)+EBP(1) ---> EBP = B2-P1
     *  🟢 🟢 🟢  🟠
     *
     * SELL #2
     * [P1][P1][P1][C][P2][P2]  ---> S2=B2+P2=6
     * 🟢  🟢  🟢 🟠 🟢 🟢
     *
     * PROFIT #2 = [P2][P2]     ---> P2=2
     *
     * TOTAL PROFIT = P = [P1][P1][P1][P2][P2] = S2-(EBP) or S2-(B2-P1) ✅
     *                     🟢 🟢 🟢 🟢  🟢
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
     * [2, 5, 4, 6]
     */
    public static int maxProfitUsingTwoPointersEffectiveBuyPrice(int[] prices) {
        int n = prices.length;
        int profit = 0, effectiveBuyPrice = prices[0];
        for (int i = 1; i < n; i++) {
             // if sold curr stock, then "TotalProfit = currSellAmount-EBP"
             // TP = S2-(EBP) or S2-(B2-P1)
            profit = Math.max(profit, prices[i] - effectiveBuyPrice);
            // if bought curr stock, then "EBP = currBuyPrice - TotalProfit"
            // EBP = B2-P
            effectiveBuyPrice = Math.min(effectiveBuyPrice, prices[i] - profit);
        }
        return profit;
    }


    /**
     * While calculating EBP, price-TP --> what if price is smaller, we get -ve EBP right ?
     * Yes, sometimes EBP can be -ve
     * that means, TP is bigger & we didn't spend money from our pocket to buy that stock
     *
     * [7, 1, 5, 3, 6, 4]
     *           i
     * here initially,
     * price = 3,
     * TP = 4,
     * EBP = 1;
     *
     * Now, if we calculate
     * EBP = price-TP = 3-4 = -1
     * TP = price-EBP = 3-(-1) = 4
     *
     * So, finally this -ve EBP will balance back the TP
     */
    public static int maxProfitUsingTwoPointersEffectiveBuyPrice2(int[] prices) {
        int TP=0; // TotalProfit
        int EBP=prices[0]; // EffectiveBuyPrice
        for(int price: prices) {
            TP = Math.max(TP, price-EBP);
            EBP = Math.min(EBP, price-TP);
        }
        return TP;
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
