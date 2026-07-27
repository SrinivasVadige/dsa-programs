package Algorithms.DynamicProgramming;

/**
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 26 July 2026
 * @link 5373. Best Time to Buy and Sell Stock V <a href="https://leetcode.com/problems/best-time-to-buy-and-sell-stock-v/">Leetcode link</a>
 * @topics Array, Dynamic Programming, Staff, Biweekly Contest 158
 * @companies Google(2), Amazon(5), Meta(3)
 * @see Algorithms.DynamicProgramming.BestTimeToBuyAndSellStockIII
 */
public class BestTimeToBuyAndSellStockV {
    public static void main(String[] args) {
        int[] prices = { 3, 2, 6, 5, 0, 3 };
        int k = 2;
        System.out.println("maximumProfit Using Backtracking1_TLE" + maximumProfitUsingBacktracking1_TLE(prices, k));
        System.out.println("maximumProfit Using TopDownMemoDp1" + maximumProfitUsingTopDownMemoDp1(prices, k));
        System.out.println("maximumProfit Using BottomUpDp1" + maximumProfitUsingBottomUpDp1(prices, k));
        System.out.println("maximumProfit Using BottomUpDp1OptimizesSpace" + maximumProfitUsingBottomUpDp1OptimizesSpace(prices, k));
        System.out.println("maximumProfit Using BottomUpDp2" + maximumProfitUsingBottomUpDp2(prices, k));
        System.out.println("maximumProfit Using BottomUpDp2Optimized" + maximumProfitUsingBottomUpDp2Optimized(prices, k));
    }




    /**

                  0 1 2 3 4
        prices = [1,7,9,8,2], k = 2

        isBuyHold = null => no hold at all
        isBuyHold = true => normal tran -> -buy+sell
        isBuyHold = false => short tran -> +sell-buy


        0   1   2   3   4
        r   bs1 bs2 sb1 sb2


            _________
            ↓       |
         -> 0   ->  1
         |  ↓
         |__2








                                                                        [ ]
                                      ___________________________________|___________________________________
                                      |                                  |                                  |
i=0                                  [ ]                                [-1]                               [1]
                              ________|________                  ________|________                  ________|________
                              |       |       |                  |               |                  |               |
i=1                          [ ]     [7]     [-7]              [-1]           [-1+7]=6[]           [1]            [1-7]=-6[]
                                                         ________|________
                                                         |               |
i=2                                                     [-1]        [-1+9]=7[]



    prices = [5]
    k = 1


    Day 0
    Flat → Sell (5) → End of array → return 5 which is wrong
    ↓
    Buy (-5)
    ↓
    End of array
    ↓
    return -5 which is wrong


     */
    public static long maximumProfitUsingBacktracking1_TLE(int[] prices, int k) {
        return backtrack(prices, k, 0, 0);
    }
    private static long backtrack(int[] prices, int k, int i, int state) {
        if (k == 0 || i == prices.length) return state == 0 ? 0 : Integer.MIN_VALUE;

        long rest = backtrack(prices, k, i+1, state);
        long action = 0;

        if (state == 0) {
            action = Math.max(
                backtrack(prices, k, i+1, 1) - prices[i],
                backtrack(prices, k, i+1, 2) + prices[i]
            );
        } else if (state == 1) {
            action = backtrack(prices, k-1, i+1, 0) + prices[i];
        } else if (state == 2) {
            action = backtrack(prices, k-1, i+1, 0) - prices[i];
        }

        return Math.max(rest, action);
    }



    public static long maximumProfitUsingTopDownMemoDp1(int[] prices, int k) {
        return dfs(prices, k, 0, 0, new Long[prices.length][k+1][3]);
    }
    private static long dfs(int[] prices, int k, int i, int state, Long[][][] memo) {
        if (k == 0 || i == prices.length) return state == 0 ? 0 : Integer.MIN_VALUE;
        else if (memo[i][k][state] != null) return memo[i][k][state];

        long rest = dfs(prices, k, i+1, state, memo);
        long action = 0;

        if (state == 0) {
            action = Math.max(
                dfs(prices, k, i+1, 1, memo) - prices[i],
                dfs(prices, k, i+1, 2, memo) + prices[i]
            );
        } else if (state == 1) {
            action = dfs(prices, k-1, i+1, 0, memo) + prices[i];
        } else if (state == 2) {
            action = dfs(prices, k-1, i+1, 0, memo) - prices[i];
        }

        return memo[i][k][state] = Math.max(rest, action);
    }


    /**
        dp[i][k][state]

        dp[i][k][0] = Math.max(dp[i+1][k][0], Math.max(dp[i+1][k][1] - prices[i], dp[i+1][k][2] + prices[i]);
        dp[i][k][1] = Math.max(dp[i+1][k][1], dp[i+1][k-1][0] + prices[i]);
        dp[i][k][2] = Math.max(dp[i+1][k][1], dp[i+1][k-1][0] - prices[i]);


                  0 1 2 3 4
        prices = [1,7,9,8,2], k = 2

        k →
         0 1 2
        [0,0,0],0   0  i
        [0,0,0],0   1  ↓
        [0,0,0],0   2
        [0,0,0],0   3
        [0,0,0],0   4
         0,0,0  0   5


     */
    public static long maximumProfitUsingBottomUpDp1(int[] prices, int k) {
        int n = prices.length;
        long[][][] dp = new long[n+1][k+1][3];

        // Base case: i == n
        for (int t = 0; t <= k; t++) {
            dp[n][t][0] = 0;
            dp[n][t][1] = Integer.MIN_VALUE;
            dp[n][t][2] = Integer.MIN_VALUE;
        }

        // Base case: k == 0
        for (int i = 0; i <= n; i++) {
            dp[i][0][0] = 0;
            dp[i][0][1] = Integer.MIN_VALUE;
            dp[i][0][2] = Integer.MIN_VALUE;
        }

        for (int i=n-1; i>=0; i--) {
            for (int t = 1; t <= k; t++) {
                dp[i][t][0] = Math.max(dp[i+1][t][0], Math.max(dp[i+1][t][1] - prices[i], dp[i+1][t][2] + prices[i]));
                dp[i][t][1] = Math.max(dp[i+1][t][1], dp[i+1][t-1][0] + prices[i]);
                dp[i][t][2] = Math.max(dp[i+1][t][2], dp[i+1][t-1][0] - prices[i]);
            }
        }

        return dp[0][k][0];
    }


    public static long maximumProfitUsingBottomUpDp1OptimizesSpace(int[] prices, int k) {
        int n = prices.length;
        long INF = (long) 1e15;

        // dp[t][state] -> 0: free, 1: holding long, 2: holding short
        long[][] next = new long[k + 1][3];
        long[][] curr = new long[k + 1][3];

        // Base case: i == n
        for (int t = 0; t <= k; t++) {
            next[t][0] = 0;
            next[t][1] = -INF;
            next[t][2] = -INF;
        }

        for (int i = n - 1; i >= 0; i--) {
            // Base case: t == 0
            curr[0][0] = 0;
            curr[0][1] = -INF;
            curr[0][2] = -INF;

            for (int t = 1; t <= k; t++) {
                curr[t][0] = Math.max(next[t][0], Math.max(next[t][1] - prices[i], next[t][2] + prices[i]));
                curr[t][1] = Math.max(next[t][1], next[t - 1][0] + prices[i]);
                curr[t][2] = Math.max(next[t][2], next[t - 1][0] - prices[i]);
            }

            // Move curr to next for the iteration of (i - 1)
            for (int t = 0; t <= k; t++) {
                next[t][0] = curr[t][0];
                next[t][1] = curr[t][1];
                next[t][2] = curr[t][2];
            }
        }

        return next[k][0];
    }








    public static long maximumProfitUsingBottomUpDp2(int[] prices, int k) {
        int n = prices.length;
        long[][][] dp = new long[n][k + 1][3];

        // initialize the state on day 0
        for (int j = 1; j <= k; j++) {
            dp[0][j][1] = -prices[0];
            dp[0][j][2] = prices[0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j][0] = Math.max(dp[i - 1][j][0], Math.max(dp[i - 1][j][1] + prices[i], dp[i - 1][j][2] - prices[i]));
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
                dp[i][j][2] = Math.max(dp[i - 1][j][2], dp[i - 1][j - 1][0] + prices[i]);
            }
        }

        return dp[n - 1][k][0];
    }


    public static long maximumProfitUsingBottomUpDp2Optimized(int[] prices, int k) {
        int n = prices.length;
        long[][] dp = new long[k + 1][3];
        // initialize the state on day 0
        for (int j = 1; j <= k; j++) {
            dp[j][1] = -prices[0];
            dp[j][2] = prices[0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = k; j > 0; j--) {
                dp[j][0] = Math.max(
                    dp[j][0],
                    Math.max(dp[j][1] + prices[i], dp[j][2] - prices[i])
                );
                dp[j][1] = Math.max(dp[j][1], dp[j - 1][0] - prices[i]);
                dp[j][2] = Math.max(dp[j][2], dp[j - 1][0] + prices[i]);
            }
        }

        return dp[k][0];
    }
}
