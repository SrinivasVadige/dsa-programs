package Algorithms.DynamicProgramming;

/**


Generalized 2-State Machine:
 Minimal ownership state (HOLD / NOT_HOLD); special rules are encoded in the transitions (like i+2).
Pipeline State Machine:
 Every phase of the process is an explicit state (HOLD → NOT_HOLD → COOL_DOWN), and all transitions advance one day (i+1).


 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 26 July 2026
 * @link 309. Best Time to Buy and Sell Stock with Cooldown <a href="https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/">LeetCode link</a>
 * @topics Array, Dynamic Programming
 * @see Algorithms.DynamicProgramming.BestTimeToBuyAndSellStock
 * @see Algorithms.DynamicProgramming.BestTimeToBuyAndSellStockII
 * @see Algorithms.DynamicProgramming.BestTimeToBuyAndSellStockIII
 * @see Algorithms.DynamicProgramming.BestTimeToBuyAndSellStockWithTransactionFee
 */
public class BestTimeToBuyAndSellStockWithCooldown {
    public static void main(String[] args) {
        int[] prices = {1, 2, 3, 0, 2};

        System.out.println("maxProfit Using Generalized_2D_StateMachine_Backtracking1_TLE => " + maxProfitUsing_Generalized_2D_StateMachine_Backtracking1_TLE(prices));
        System.out.println("maxProfit Using Generalized_2D_StateMachine_TopDownMemoDp1 => " + maxProfitUsing_Generalized_2D_StateMachine_TopDownMemoDp1(prices));
        System.out.println("maxProfit Using Generalized_2D_StateMachine_BottomUpDp1 => " + maxProfitUsing_Generalized_2D_StateMachine_BottomUpDp1(prices));
        System.out.println("maxProfit Using Generalized_2D_StateMachine_BottomUpNoMemoryDp1 => " + maxProfitUsing_Generalized_2D_StateMachine_BottomUpNoMemoryDp1(prices));

        System.out.println("maxProfit Using Pipeline_StateMachine_Backtracking1_TLE => " + maxProfitUsing_PipelineStateMachine_Backtracking1_TLE(prices));
        System.out.println("maxProfit Using Pipeline_StateMachine_TopDownMemoDp1 => " + maxProfitUsing_PipelineStateMachine_TopDownMemoDp1(prices));
        System.out.println("maxProfit Using Pipeline_StateMachine_BottomUpDp1 => " + maxProfitUsing_PipelineStateMachine_BottomUpDp1(prices));
        System.out.println("maxProfit Using Pipeline_StateMachine_BottomUpNoMemoryDp1 => " + maxProfitUsing_PipelineStateMachine_BottomUpNoMemoryDp1(prices));
        System.out.println("maxProfit Using Pipeline_StateMachine_BottomUpNoMemoryDp2 => " + maxProfitUsing_PipelineStateMachine_BottomUpNoMemoryDp2(prices));

        System.out.println("maxProfit Using 1D_IntervalDecision_BottomUpDp1 => " + maxProfitUsing_1D_IntervalDecision_BottomUpDp1(prices));
    }

    /**

         0 1 2 3 4
        [1,2,3,0,2]

i                                                                   [ ]
                                          ___________________________|___________________________
                                          |                                                     |
i=0                                      [ ]                                                   [-1]
                         _________________|_________________               _____________________|_____________________
                         |                                 |               |                                         |
i=1                     [ ]                               [-2]            [-1]                                 [-1+2] = []
               __________|__________        _______________|______    ______|____________                 ___________|___________
               |                   |        |                    |    |                 |                 |                     |
i=2           [ ]                 [-3]     [-2]          [-2+3]=[ ]  [-1]         [-1+3]=2[]         [ ] nextI=3              [-0] nextI=3



        2 states = 0,1

        0   1   0   1   0   1   .......
        H   NH  H   NH


     * @TimeComplexity O(2^N)
     * @SpaceComplexity O(N)
    */
    public static int maxProfitUsing_Generalized_2D_StateMachine_Backtracking1_TLE(int[] prices) {
        return backtrack(prices, 0, false);
    }
    private static int backtrack(int[] prices, int i, boolean isHold) {
        if (i >= prices.length) return 0;

        int action = 0, skip = 0;
        if (isHold) {
            action = backtrack(prices, i+2, false) + prices[i]; // sell
        } else {
            action = backtrack(prices, i+1, true) - prices[i]; // buy
        }

        skip = backtrack(prices, i+1, isHold);

        return Math.max(action, skip);
    }




    /**
     * @TimeComplexity O(2N) as [prices.length][2] = O(N)
     * @SpaceComplexity O(N)
    */
    public static int maxProfitUsing_Generalized_2D_StateMachine_TopDownMemoDp1(int[] prices) {
        return dfs(prices, 0, 0, new Integer[prices.length][2]);
    }
    private static int dfs(int[] prices, int i, int isHold, Integer[][] memo) {
        if (i >= prices.length) return 0;
        else if (memo[i][isHold] != null) return memo[i][isHold];

        int action = 0, skip = 0;
        if (isHold==1) {
            action = dfs(prices, i+2, 0, memo) + prices[i]; // sell
        } else {
            action = dfs(prices, i+1, 1, memo) - prices[i]; // buy
        }

        skip = dfs(prices, i+1, isHold, memo);

        return memo[i][isHold] = Math.max(action, skip);
    }



    /**


         0 1 2 3 4
        [1,2,3,0,2]

        dp[i][h] = dp[i+1][h] && dp[i+1][1]-prices[i] || dp[i+2][0]+prices[i] or dp[i][h] = dp[i-1][h] && dp[i-1][1]-prices[i] || dp[i-2][0]+prices[i]

        instead of dp[i][h] just easily divide it into dp[i][0] and dp[i][1]

        dp[i][0] = Math.max(dp[i+1][0], dp[i+1][1] - prices[i])
        dp[i][1] = Math.max(dp[i+1][1], dp[i+2][0] + prices[i])

         h0 h1
         f  t
        [0, 0]  0 i
        [0, 0]  1 ↓
        [0, 0]  2
        [0, 0]  3
        [0, 0]  4
        [0, 0]  5
        [0, 0]  6


     * @TimeComplexity O(N)
     * @SpaceComplexity O(N)
    */
    public static int maxProfitUsing_Generalized_2D_StateMachine_BottomUpDp1(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n+2][2];

        for (int i=n-1; i>=0; i--) {
            dp[i][0] = Math.max(dp[i+1][0], dp[i+1][1] - prices[i]); // Option 1: Not holding stock
            dp[i][1] = Math.max(dp[i+1][1], dp[i+2][0] + prices[i]); // Option 2: Holding stock
        }

        return dp[0][0];
    }

    /**
     * @TimeComplexity O(N)
     * @SpaceComplexity O(1)
    */
    public static int maxProfitUsing_Generalized_2D_StateMachine_BottomUpNoMemoryDp1(int[] prices) {
        int n = prices.length;
        int[] hold = new int[2];
        int[] notHold = new int[3];

        for (int i=n-1; i>=0; i--) {
            notHold[0] = Math.max(notHold[1], hold[1] - prices[i]); // Option 1: Not holding stock
            hold[0] = Math.max(hold[1], notHold[2] + prices[i]); // Option 2: Holding stock

            hold[1] = hold[0];
            notHold[2] = notHold[1];
            notHold[1] = notHold[0];
        }

        return notHold[0];
    }








    /**

         0 1 2 3 4
        [1,2,3,0,2]

        3 states = 0,1,2

        0   1   2       0   1   2       ......
        H   NH  COOL


     */
    public enum STATE {
        HOLD {
            public STATE next() { return NOT_HOLD; }
        },
        NOT_HOLD {
            public STATE next() { return COOL_DOWN; }
        },
        COOL_DOWN {
            public STATE next() { return HOLD; }
        };

        public abstract STATE next();
    }
    /**
     * @TimeComplexity O(2^N)
     * @SpaceComplexity O(N)
    */
    public static int maxProfitUsing_PipelineStateMachine_Backtracking1_TLE(int[] prices) {
        return backtrack(prices, 0, STATE.HOLD);
    }
    private static int backtrack(int[] prices, int i, STATE state) {
        if (i >= prices.length) return 0;

        int rest = backtrack(prices, i+1, state);
        int action = 0;

        if (state == STATE.HOLD) {
            action = backtrack(prices, i+1, state.next()) - prices[i];
        } else if (state == STATE.NOT_HOLD) {
            action = backtrack(prices, i+1, state.next()) + prices[i];
        } else if (state == STATE.COOL_DOWN) {
            action = backtrack(prices, i+1, state.next());
        }

        return Math.max(rest, action);
    }



    /**
     * @TimeComplexity O(2N) as [prices.length][2] = O(N)
     * @SpaceComplexity O(N)
    */
    public static int maxProfitUsing_PipelineStateMachine_TopDownMemoDp1(int[] prices) {
        return dfs(prices, new Integer[prices.length][3], 0, 0);
    }
    private static int dfs(int[] prices, Integer[][] memo, int i, int state) {
        if (i >= prices.length) return 0;
        else if (memo[i][state] != null) return memo[i][state];

        int rest = dfs(prices, memo, i+1, state);
        int action = 0;

        if (state == 0) {
            action = dfs(prices, memo, i+1, 1) - prices[i];
        } else if (state == 1) {
            action = dfs(prices, memo, i+1, 2) + prices[i];
        } else if (state == 2) {
            action = dfs(prices, memo, i+1, 0);
        }

        return memo[i][state] = Math.max(rest, action);
    }


    /**

        dp[i][s] = Math.max(dp[i+1][s], dp[i+1][(s+1) % 3]

     * @TimeComplexity O(N)
     * @SpaceComplexity O(N)
    */
    public static int maxProfitUsing_PipelineStateMachine_BottomUpDp1(int[] prices) {
        int n = prices.length;
        int states = 3;
        int[][] dp = new int[n+1][states];

        for (int i=n-1; i>=0; i--) {
            dp[i][0] = Math.max(dp[i+1][0], dp[i+1][1] - prices[i]);
            dp[i][1] = Math.max(dp[i+1][1], dp[i+1][2] + prices[i]);
            dp[i][2] = Math.max(dp[i+1][2], dp[i+1][0]);
        }

        return dp[0][0];
    }

    /**
     * @TimeComplexity O(N)
     * @SpaceComplexity O(1)
    */
    public static int maxProfitUsing_PipelineStateMachine_BottomUpNoMemoryDp1(int[] prices) {
        int n = prices.length;
        int buy = 0, sell = 0, cool = 0;

        for (int i=n-1; i>=0; i--) {

            int preBuy = buy, prevSell = sell, prevCool = cool;

            buy = Math.max(preBuy, prevSell - prices[i]);
            sell = Math.max(prevSell, prevCool + prices[i]);
            cool = Math.max(prevCool, preBuy);
        }

        return buy;
    }

    /**
     * @TimeComplexity O(N)
     * @SpaceComplexity O(1)
    */
    public static int maxProfitUsing_PipelineStateMachine_BottomUpNoMemoryDp2(int[] prices) {
        int sell = Integer.MIN_VALUE, buy = Integer.MIN_VALUE, cool = 0;

        for (int price : prices) {
            int preSell = sell;

            sell = buy + price;
            buy = Math.max(buy, cool - price);
            cool = Math.max(cool, preSell);
        }

        return Math.max(sell, cool);
    }





    /**
     * @TimeComplexity O(N^2)
     * @SpaceComplexity O(N)
    */
    public static int maxProfitUsing_1D_IntervalDecision_BottomUpDp1(int[] prices) {
        int n = prices.length;
        int[] MP = new int[n + 2];
        for (int i = n-1; i >= 0; i--) {
            int C1 = 0;
            // Case 1). buy and sell the stock
            for (int sell = i+1; sell < n; sell++) {
                int profit = (prices[sell] - prices[i]) + MP[sell + 2];
                C1 = Math.max(profit, C1);
            }

            // Case 2). do no transaction with the stock p[i]
            int C2 = MP[i + 1];

            MP[i] = Math.max(C1, C2);
        }
        return MP[0];
    }
}
