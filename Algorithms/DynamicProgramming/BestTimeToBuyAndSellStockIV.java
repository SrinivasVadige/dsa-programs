package Algorithms.DynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 24 July 2026
 * @link 188. Best Time to Buy and Sell Stock IV <a href="https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/">Leetcode link</a>
 * @topics Array, Dynamic Programming
 * @see Algorithms.DynamicProgramming.BestTimeToBuyAndSellStockIII
 */
public class BestTimeToBuyAndSellStockIV {
    public static void main(String[] args) {
        int[] prices = {3,2,6,5,0,3};
        int k = 2;
        System.out.println("maxProfit Using PipelineStateMachine_CashBalance_Backtracking1_TLE: " + maxProfitUsing_PipelineStateMachine_CashBalance_Backtracking1_TLE(k, prices));
        System.out.println("maxProfit Using PipelineStateMachine_CashBalance_TopDownMemoDp1: " + maxProfitUsing_PipelineStateMachine_CashBalance_TopDownMemoDp1(k, prices));
        System.out.println("maxProfit Using PipelineStateMachine_CashBalance_BottomUp1DTabulationDp1: " + maxProfitUsing_PipelineStateMachine_CashBalance_BottomUp1DTabulationDp1(k, prices));
        System.out.println("maxProfit Using PipelineStateMachine_CashBalance_BottomUp2DTabulationDp1: " + maxProfitUsing_PipelineStateMachine_CashBalance_BottomUp2DTabulationDp1(k, prices));

        System.out.println("maxProfit Using PipelineStateMachine_EffectiveBuyPrice_BottomUpNoMemoryDp1: " + maxProfitUsing_PipelineStateMachine_EffectiveBuyPrice_BottomUpNoMemoryDp1(k, prices));
        System.out.println("maxProfit Using PipelineStateMachine_EffectiveBuyPrice_BottomUpNoMemoryDp2: " + maxProfitUsing_PipelineStateMachine_EffectiveBuyPrice_BottomUpNoMemoryDp2(k, prices));

        System.out.println("maxProfit Using Generalized_2D_StateMachine_CashBalance_BottomUpDp3DTabulationDp1: " + maxProfitUsing_Generalized_2D_StateMachine_CashBalance_BottomUpDp3DTabulationDp1(k, prices));

        System.out.println("maxProfit Using Greedy_IntervalMergeAndDelete: " + maxProfitUsing_Greedy_IntervalMergeAndDelete(k, prices));
    }


    /**

        0   1   2   3   4 ... k*2 times state
        h0  h1  h0
        -p  +p  -p

        odd state = h1, +p


     */
    public static int maxProfitUsing_PipelineStateMachine_CashBalance_Backtracking1_TLE(int k, int[] prices) {
        return backtrack(k, prices, 0, 0);
    }
    private static int backtrack(int k, int[] prices, int dayI, int state) {
        if (state == 2*k || dayI == prices.length) return 0;

        int rest = backtrack(k, prices, dayI+1, state);
        int action = backtrack(k, prices, dayI+1, state+1) + (state%2==0? -prices[dayI] : prices[dayI]);

        return Math.max(rest, action);
    }




    public static int maxProfitUsing_PipelineStateMachine_CashBalance_TopDownMemoDp1(int k, int[] prices) {
        return dfs(k, prices, 0, 0, new Integer[prices.length][k*2]);
    }
    private static int dfs(int k, int[] prices, int dayI, int state, Integer[][] memo) {
        if (state == 2*k || dayI == prices.length) return 0;
        else if (memo[dayI][state] != null) return memo[dayI][state];

        int rest = dfs(k, prices, dayI+1, state, memo);
        int priceDelta = (state % 2 != 0) ? -prices[dayI] : prices[dayI];
        int action = dfs(k, prices, dayI+1, state+1, memo) + priceDelta;

        return memo[dayI][state] = Math.max(rest, action);
    }



    public static int maxProfitUsing_PipelineStateMachine_CashBalance_BottomUp2DTabulationDp1(int k, int[] prices) {
        int n = prices.length;
        int maxStates = k * 2 + 1;
        int[][] dp = new int[n][maxStates];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int c=2; c<maxStates; c++) dp[0][c] = Integer.MIN_VALUE/2;

        for (int r=1; r<prices.length; r++) {

            dp[r][0] = dp[r-1][0];

            for (int c=1; c<maxStates; c++) {
                int priceDelta = (c % 2 != 0) ? -prices[r] : prices[r];
                dp[r][c] = Math.max(dp[r-1][c] , dp[r-1][c-1] + priceDelta);
            }
        }

        int max = Integer.MIN_VALUE;
        for (int c=0; c<maxStates; c+=2) max = Math.max(max, dp[n-1][c]);

        return max;
    }



    public static int maxProfitUsing_PipelineStateMachine_CashBalance_BottomUp1DTabulationDp1(int k, int[] prices) { // or BottomUpNoMemoryDp1
        if (prices == null || prices.length == 0 || k == 0) return 0;

        int maxStates = k * 2 + 1;
        int[] dp = new int[maxStates];

        // Day 0 Base Cases
        dp[0] = 0;
        dp[1] = -prices[0];
        for (int c = 2; c < maxStates; c++) {
            dp[c] = Integer.MIN_VALUE / 2;
        }

        for (int price : prices) {
            // Iterate BACKWARD to avoid using today's updated values
            for (int c = maxStates - 1; c >= 1; c--) {
                int priceDelta = (c % 2 != 0) ? -price : price; // Odd = Buy (-), Even = Sell (+)
                dp[c] = Math.max(dp[c], dp[c - 1] + priceDelta);
            }
        }

        int max = 0;
        for (int c = 0; c < maxStates; c += 2) {
            max = Math.max(max, dp[c]);
        }

        return max;
    }







    public static int maxProfitUsing_PipelineStateMachine_EffectiveBuyPrice_BottomUpNoMemoryDp1(int k, int[] prices) { // or BottomUp1DTabulationDp1
        int[][] buySell = new int[k][2];
        for (int i=0; i<k; i++) buySell[i][0] = Integer.MIN_VALUE;

        for (int price : prices) {
            for (int i=0; i<k; i++) {
                int prevProfit = (i == 0) ? 0 : buySell[i-1][1];
                buySell[i][0] = Math.max(buySell[i][0], prevProfit-price);
                buySell[i][1] = Math.max(buySell[i][1], buySell[i][0]+price);
            }
        }

        return buySell[k-1][1];
    }


    public static int maxProfitUsing_PipelineStateMachine_EffectiveBuyPrice_BottomUpNoMemoryDp2(int k, int[] prices) { // or BottomUp1DTabulationDp2
        if (prices == null || prices.length == 0 || k == 0) return 0;

        int[] buy = new int[k];
        int[] sell = new int[k];

        // Initialize all buy states to -infinity
        Arrays.fill(buy, Integer.MIN_VALUE);

        for (int price : prices) {
            for (int i = 0; i < k; i++) {
                // Previous profit before buying: 0 for 1st transaction, sell[i-1] for subsequent
                int prevProfit = (i == 0) ? 0 : sell[i - 1];

                // Max cash left after buying the i-th stock
                buy[i] = Math.max(buy[i], prevProfit - price);

                // Max cash left after selling the i-th stock
                sell[i] = Math.max(sell[i], buy[i] + price);
            }
        }

        return sell[k - 1];
    }












    public static int maxProfitUsing_Generalized_2D_StateMachine_CashBalance_BottomUpDp3DTabulationDp1(int k, int[] prices) {
        int n = prices.length;

        if (n <= 0 || k <= 0) return 0;
        else if (k * 2 >= n) {
            int res = 0;
            for (int i = 1; i < n; i++) {
                res += Math.max(0, prices[i] - prices[i - 1]);
            }
            return res;
        }

        // dp[i][used_k][ishold] = balance ishold: 0 nothold, 1 hold
        int[][][] dp = new int[n][k + 1][2];


        for (int i = 0; i < n; i++) { // initialize the array with -inf we use -1e9 here to prevent overflow
            for (int j = 0; j <= k; j++) {
                dp[i][j][0] = -1_000_000_000;
                dp[i][j][1] = (int)-1e9;
            }
        }

        // set starting value
        dp[0][0][0] = 0;
        dp[0][1][1] = -prices[0];

        for (int i = 1; i < n; i++) { // fill the array
            for (int j = 0; j <= k; j++) {
                // transition equation
                dp[i][j][0] = Math.max( dp[i - 1][j][0], dp[i - 1][j][1] + prices[i] );
                // you can't hold stock without any transaction
                if (j > 0) {
                    dp[i][j][1] = Math.max( dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
                }
            }
        }

        int res = 0;
        for (int j = 0; j <= k; j++) {
            res = Math.max(res, dp[n - 1][j][0]);
        }

        return res;
    }





    public static int maxProfitUsing_Greedy_IntervalMergeAndDelete(int k, int[] prices) {
        int n = prices.length;

        if (n <= 0 || k <= 0) return 0;

        // find all consecutively increasing subsequence
        ArrayList<int[]> transactions = new ArrayList<>();
        int start = 0;
        int end = 0;
        for (int i = 1; i < n; i++) {
            if (prices[i] >= prices[i - 1]) {
                end = i;
            } else {
                if (end > start) {
                    int[] t = { start, end };
                    transactions.add(t);
                }
                start = i;
            }
        }
        if (end > start) {
            int[] t = { start, end };
            transactions.add(t);
        }

        while (transactions.size() > k) {
            // check delete loss
            int delete_index = 0;
            int min_delete_loss = Integer.MAX_VALUE;
            for (int i = 0; i < transactions.size(); i++) {
                int[] t = transactions.get(i);
                int profit_loss = prices[t[1]] - prices[t[0]];
                if (profit_loss < min_delete_loss) {
                    min_delete_loss = profit_loss;
                    delete_index = i;
                }
            }

            // check merge loss
            int merge_index = 0;
            int min_merge_loss = Integer.MAX_VALUE;
            for (int i = 1; i < transactions.size(); i++) {
                int[] t1 = transactions.get(i - 1);
                int[] t2 = transactions.get(i);
                int profit_loss = prices[t1[1]] - prices[t2[0]];
                if (profit_loss < min_merge_loss) {
                    min_merge_loss = profit_loss;
                    merge_index = i;
                }
            }

            // delete or merge
            if (min_delete_loss <= min_merge_loss) {
                transactions.remove(delete_index);
            } else {
                int[] t1 = transactions.get(merge_index - 1);
                int[] t2 = transactions.get(merge_index);
                t1[1] = t2[1];
                transactions.remove(merge_index);
            }
        }

        int res = 0;
        for (int[] t : transactions) {
            res += prices[t[1]] - prices[t[0]];
        }

        return res;
    }
}
