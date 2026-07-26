package Algorithms.DynamicProgramming;

/**

 <pre>
------------------------------------------
How to Discover the State Machine Approach
------------------------------------------
    Going from Index-Tracking to a State Machine isn't magic—it comes down to cleaning up unnecessary variables from your recursive state.

--- Step 1: Spot the O(N^2) Bottleneck ---

    In Index-Tracking Backtracking, your helper function tracks:
    dfs(prices, day, k, boughtI)

        day:    Current price day (0 to N)
        k:      Transactions remaining (0 to 2)
        boughtI:The index where you bought the stock (-1 to N)

        boughtI = -1 ---> not bought
        boughtI = 0...N ---> bought on day i

        here instead of using separate isHold/isBought boolean var to track -> we combined it with boughtI

    The Problem👎: boughtI takes N possible values. This forces your memoization table to be O(N^2), leading to Time Limit Exceeded (TLE).

    The Question❔: Do you actually need to remember WHICH exact day you bought the stock?



--- Step 2: Pay Immediately Instead of Delaying (The Wallet Trick) ---

    In Index-Tracking, you wait until the SELL step to compute profit:
    Profit = prices[sell] - prices[boughtI]

    Because you delay the math, you MUST remember boughtI.
    The Fix: Split the transaction into real-time cash flow:

        When you BUY: Spend money instantly -> Wallet change: -prices[day]
        When you SELL: Gain money instantly -> Wallet change: +prices[day]

    Once you pay/collect in real time, you don't need to track boughtI anymore! You only need to know: Are you holding a stock or not?
    This replaces boughtI with a simple binary flag: holding (0 = No stock, 1 = Holding stock).



--- Step 3: Transition to Generalized 2D State Machine ---

    By replacing boughtI with holding, your method signature shrinks to:
    dfs(prices, day, k, holding)

        day:    0 to N
        k:      0 to 2 (3 values)
        holding:0 or 1 (2 values)

    Total states per day = [k]*[holding] = 3 * 2 = 6 states.
    You just turned an O(N^2) state space into an O(N) state machine!

        Generalized _ 2D _ StateMachine
          │           │    │
          │           │    │
          │           │    └── Model: State Machine (Hold vs. Unheld)
          │           └── Dimensions: State = Two tracking variables = (k, holding 0/1) === The state is split into two explicit variables: k (remaining transactions) and holding (binary 0/1 flag).
          └── Flexibility: Handles any 'k' transactions, not just k = 2 === Unlike the 0..4 pipeline approach (which is hardcoded for exactly 2 transactions), decomposing the state into (k, holding) allows it to handle any k transactions (even if k = 100).



--- Step 4: Flatten the Grid into a 1D Pipeline ---

    When k is small and fixed (like k = 2), you can combine k and holding into 5 sequential pipeline states:

        State 0: holding = 0, k = 2 -> Ready for 1st buy
        State 1: holding = 1, k = 2 -> Holding 1st stock
        State 2: holding = 0, k = 1 -> Sold 1st stock (ready for 2nd buy)
        State 3: holding = 1, k = 1 -> Holding 2nd stock
        State 4: holding = 0, k = 0 -> Finished both transactions

    Flow:
        State 0 --(Buy 1)--> State 1 --(Sell 1)--> State 2 --(Buy 2)--> State 3 --(Sell 2)--> State 4

    Now, instead of two variables (k and holding), you have a single variable: state (0 to 4).

    CONCEPT:
        Models the trading lifecycle as a 5-state pipeline (0 -> 1 -> 2 -> 3 -> 4)
        where each state tracks the MAXIMUM cash balance achievable in that stage:

        [0] ---BUY---> [s1] ---SELL---> [s2] ---BUY---> [s3] ---SELL---> [s4]
         |              |                |               |
        REST           REST             REST            REST


+------------------------------------+------------------------+------------------------------------+
| APPROACH                           | STATE LOOKUP INDEX     | DIMENSIONALITY                     |
+------------------------------------+------------------------+------------------------------------+
| Pipeline State Machine             | dp[day][state]         | 1D State (state is just 0..4)      |
| Generalized 2D State Machine       | dp[day][k][holding]    | 2D State (tracks both k AND hold)  |
+------------------------------------+------------------------+------------------------------------+



+------------------------------------+-------------------------------------------------------------------------+-----------------+------------------+------------------------------------------+
| CATEGORY                           | METHOD NAME                                                             | TIME COMPLEXITY | SPACE COMPLEXITY | STATUS / NOTES                           |
+------------------------------------+-------------------------------------------------------------------------+-----------------+------------------+------------------------------------------+
| Index-Tracking                     | maxProfitUsing_IndexTracking_Backtracking1_TLE                          | O(2^N)          | O(N)             | TLE - Raw recursion with boughtI         |
| Index-Tracking                     | maxProfitUsing_IndexTracking_TopDownMemoDp1_TLE                         | O(N^2)          | O(N^2)           | TLE / MLE - memo[N][2][N+1]              |
| Index-Tracking                     | maxProfitUsing_IndexTracking_BottomUpDp1_TLE                            | O(N^2)          | O(N^2)           | TLE / MLE - dp[N+1][3][N+1]              |
+------------------------------------+-------------------------------------------------------------------------+-----------------+------------------+------------------------------------------+
| Generalized 2D State Machine       | maxProfitUsing_Generalized_2D_StateMachine_Backtracking1_TLE            | O(2^N)          | O(N)             | TLE - Raw recursion with (k, holding)    |
| Generalized 2D State Machine       | maxProfitUsing_Generalized_2D_StateMachine_TopDownMemoDp1               | O(N)            | O(N)             | Accepted - memo[N][3][2]                 |
| Generalized 2D State Machine       | maxProfitUsing_Generalized_2D_StateMachine_BottomUpDp1                  | O(N)            | O(N)             | Accepted - dp[N+1][3][2]                 |
+------------------------------------+-------------------------------------------------------------------------+-----------------+------------------+------------------------------------------+
| Pipeline State Machine (Cash)      | maxProfitUsing_PipelineStateMachine_CashBalance_Backtracking1_TLE       | O(2^N)          | O(N)             | TLE - Raw recursion on 5 pipeline states |
| Pipeline State Machine (Cash)      | maxProfitUsing_PipelineStateMachine_CashBalance_TopDownMemoDp1          | O(N)            | O(N)             | Accepted - memo[N][5]                    |
| Pipeline State Machine (Cash)      | maxProfitUsing_PipelineStateMachine_CashBalance_BottomUpDp1             | O(N)            | O(N)             | Accepted - dp[N][5]                      |
| Pipeline State Machine (Cash)      | maxProfitUsing_PipelineStateMachine_CashBalance_BottomUpNoMemoryDp1     | O(N)            | O(1)             | Optimal - 4 scalar state variables       |
+------------------------------------+-------------------------------------------------------------------------+-----------------+------------------+------------------------------------------+
| Pipeline State Machine (EBP)       | maxProfitUsing_PipelineStateMachine_EffectiveBuyPrice_BottomUpDp1       | O(N)            | O(N)             | Accepted - 4 separate 1D DP arrays       |
| Pipeline State Machine (EBP)       | maxProfitUsing_PipelineStateMachine_EffectiveBuyPrice_BottomUpNoMemory1 | O(N)            | O(1)             | Optimal - ebp/profit variable framing    |
| Pipeline State Machine (EBP)       | maxProfitUsing_PipelineStateMachine_EffectiveBuyPrice_BottomUpNoMemory2 | O(N)            | O(1)             | Optimal - Cash flow variant of EBP       |
+------------------------------------+-------------------------------------------------------------------------+-----------------+------------------+------------------------------------------+



</pre>

 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 24 July 2026
 * @link 123. Best Time to Buy and Sell Stock III <a href="https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/">Leetcode link</a>
 * @topics Array, Dynamic Programming
 * @companies Meta(4), Amazon(3), Bloomberg(3), Google(20), Microsoft(13), Snap(5), TikTok(4), Tekion(4), Apple(3), Goldman Sachs(3), Citadel(2), Visa(2), PayPal(2)
 */
public class BestTimeToBuyAndSellStockIII {
    public static void main(String[] args) {
        int[] prices = {3,3,5,0,0,3,1,4};

        System.out.println("MaxProfit Using Bidirectional DP: " + maxProfitUsingBidirectionalDynamicProgramming(prices));

        System.out.println("Index tracking approaches:");
        System.out.println("maxProfit Using IndexTracking Backtracking1 TLE" + maxProfitUsing_IndexTracking_Backtracking1_TLE(prices));
        System.out.println("maxProfit Using IndexTracking TopDownMemoDp1 TLE" + maxProfitUsing_IndexTracking_TopDownMemoDp1_TLE(prices));
        System.out.println("maxProfit Using IndexTracking BottomUpTabulationDp1" + maxProfitUsing_IndexTracking_BottomUpDp1_TLE(prices));

        System.out.println("Generalized 2D StateMachine approaches:");
        System.out.println("maxProfit Using Generalized 2D State Machine Backtracking1 TLE" + maxProfitUsing_Generalized_2D_StateMachine_Backtracking1_TLE(prices));
        System.out.println("maxProfit Using Generalized 2D State Machine TopDownMemoDp1" + maxProfitUsing_Generalized_2D_StateMachine_TopDownMemoDp1(prices));
        System.out.println("maxProfit Using Generalized 2D State Machine BottomUpTabulationDp1" + maxProfitUsing_Generalized_2D_StateMachine_BottomUpDp1(prices));

        System.out.println("Pipeline StateMachine approaches - Cash Balance:");
        System.out.println("maxProfit Using Pipeline State Machine Cash Balance Backtracking1 TLE" + maxProfitUsing_PipelineStateMachine_CashBalance_Backtracking1_TLE(prices));
        System.out.println("maxProfit Using Pipeline State Machine Cash Balance TopDownMemoDp1" + maxProfitUsing_PipelineStateMachine_CashBalance_TopDownMemoDp1(prices));
        System.out.println("maxProfit Using Pipeline State Machine Cash Balance BottomUp 2D Tabulation Dp 1" + maxProfitUsing_PipelineStateMachine_CashBalance_BottomUp2DTabulationDp1(prices));
        System.out.println("maxProfit Using Pipeline State Machine Cash Balance BottomUp 1D Tabulation Dp 1" + maxProfitUsing_PipelineStateMachine_CashBalance_BottomUp1DTabulationDp1(prices));
        System.out.println("maxProfit Using Pipeline State Machine Cash Balance BottomUpNoMemoryDp1" + maxProfitUsing_PipelineStateMachine_CashBalance_BottomUpNoMemoryDp1(prices));

        System.out.println("Pipeline StateMachine approaches - Effective Buy Price EBP:");
        System.out.println("maxProfit Using Pipeline State Machine Effective Buy Price BottomUp 2D Tabulation Dp 1" + maxProfitUsing_PipelineStateMachine_EffectiveBuyPrice_BottomUp2DTabulationDp1(prices));
        System.out.println("maxProfit Using Pipeline State Machine Effective Buy Price BottomUp 1D Tabulation Dp 1" + maxProfitUsing_PipelineStateMachine_EffectiveBuyPrice_BottomUp1DTabulationDp1(prices));
        System.out.println("maxProfit Using Pipeline State Machine Effective Buy Price BottomUpNoMemoryDp1" + maxProfitUsing_PipelineStateMachine_EffectiveBuyPrice_BottomUpNoMemoryDp1(prices));
        System.out.println("maxProfit Using Pipeline State Machine Effective Buy Price BottomUpNoMemoryDp2" + maxProfitUsing_PipelineStateMachine_EffectiveBuyPrice_BottomUpNoMemoryDp2(prices));
    }


    /**

    <pre>

            PIVOT DAY (i)
                                        |
               <--- LEFT PASS ---       |       --- RIGHT PASS --->
             [ Day 0 | Day 1 | Day 2 ]  |  [ Day 3 | Day 4 | Day 5 ]
             -------------------------     -------------------------
              Track lowest leftMin          Track highest rightMax
              Max profit in [0 ... i]       Max profit in [i+1 ... N-1]
                        |                               |
                        v                               v
                 leftProfits[i]    +     rightProfits[i + 1]
             -------------------------------------------------------
                        = COMBINED TOTAL PROFIT AT DAY i




            Prices:    [ 3,  3,  5,  0,  0,  3,  1,  4 ]
                            \_____ Transaction 1 _____/   \____ Transaction 2 ____/
                             Buy @ 3, Sell @ 5 (Profit 2)   Buy @ 0, Sell @ 4 (Profit 4)
                                                         |
                                                     Pivot Cut
                                               leftProfits[2] = 2
                                              rightProfits[3] = 4
                                              ------------------
                                               Total Profit = 6


            Index i:          0       1       2       3       4
            Prices:        [  3,      3,      5,      0,      3  ]
            ------------------------------------------------------
            leftMin:          3       3       3       0       0   <-- Prefix Min
            leftProfits:      0       0       2       2       3   <-- Prefix Max Profit



    </pre>
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int maxProfitUsingBidirectionalDynamicProgramming(int[] prices) {
        int length = prices.length;
        if (length <= 1) return 0;

        int leftMin = prices[0];
        int rightMax = prices[length - 1];
        int[] leftProfits = new int[length]; // PrefixMax - just like prefix sum
        int[] rightProfits = new int[length + 1]; // SuffixMax - just like suffix sum => pad the right DP array with an additional zero for convenience.

        // construct the bidirectional DP array
        for (int l = 1; l < length; l++) {
            leftProfits[l] = Math.max(leftProfits[l-1], prices[l]-leftMin); // PrefixMax - just like prefix sum
            leftMin = Math.min(leftMin, prices[l]);

            int r = length-1-l;
            rightProfits[r] = Math.max(rightProfits[r+1], rightMax-prices[r]); // SuffixMax - just like suffix sum
            rightMax = Math.max(rightMax, prices[r]);
        }

        int maxProfit = 0;
        for (int i = 0; i < length; ++i) {
            maxProfit = Math.max(maxProfit, leftProfits[i] + rightProfits[i + 1]);
        }
        return maxProfit;
    }







    /**


                                                        [ ]
                                           ______________|______________
                                           |                           |
    i=0                                   [3]                         [ ]
                                  _________|_________         _________|_________
                                  |                 |         |                 |
    i=1                         [3,3]=0            [3]       [3]               [ ]
                          ________|________ ________|________
                          |               | |               |
    i=2                  [5]            [ ] [3,5]=2        [3]


        There is no need to sell and buy the same day same amount ---> cause it just decreases the k count

     * @TimeComplexity O(2^N)
     * @SpaceComplexity O(N)
     */
    public static int maxProfitUsing_IndexTracking_Backtracking1_TLE(int[] prices) {
        return backtrack(prices, 0, 0, -1);
    }
    private static int backtrack(int[] prices, int i, int k, int boughtI) {
        if (i == prices.length || k == 2) return 0;

        int include = 0, exclude = 0;

        if (boughtI == -1) { // BUY
            include = backtrack(prices, i+1, k, i);
        } else { // SELL
            include = backtrack(prices, i+1, k+1, -1) + Math.max(0, prices[i] - prices[boughtI]);
        }

        exclude = backtrack(prices, i+1, k, boughtI); // DO_NOTHING

        return Math.max(include, exclude);
    }





    /**

        memo_key = i,k,boughtI ---> MemoryLimitExceeded MLE error as well
        state = memo[N][k][N]

     * @TimeComplexity O(N^2)
     * @SpaceComplexity O(N^2)
     */
    public static int maxProfitUsing_IndexTracking_TopDownMemoDp1_TLE(int[] prices) {
        return dfs(prices, 0, 0, -1, new Integer[prices.length][2][prices.length+1]); // i,k,boughtI
    }
    private static int dfs(int[] prices, int i, int k, int boughtI, Integer[][][] memo) {
        if (i == prices.length || k == 2) return 0;
        else if (memo[i][k][boughtI+1] != null) return memo[i][k][boughtI+1];

        int include = 0, exclude = 0;

        if (boughtI == -1) { // BUY
            include = dfs(prices, i+1, k, i, memo);
        } else { // SELL
            include = dfs(prices, i+1, k+1, -1, memo) + Math.max(0, prices[i] - prices[boughtI]);
        }

        exclude = dfs(prices, i+1, k, boughtI, memo); // DO_NOTHING

        return memo[i][k][boughtI+1] = Math.max(include, exclude);
    }


    /**
     * @TimeComplexity O(N^2)
     * @SpaceComplexity O(N^2)
     */
    public static int maxProfitUsing_IndexTracking_BottomUpDp1_TLE(int[] prices) {

        int n = prices.length;

        // dp[day][transactionsCompleted][boughtIndex+1]
        int[][][] dp = new int[n + 1][3][n + 1];
        // dp[n][*][*] = 0

        for (int i = n - 1; i >= 0; i--) {
            for (int k = 1; k >= 0; k--) {

                for (int boughtI = -1; boughtI < n; boughtI++) {

                    int include;
                    if (boughtI == -1) { // BUY
                        include = dp[i + 1][k][i + 1];
                    } else { // SELL
                        include = Math.max(0, prices[i] - prices[boughtI]) + dp[i + 1][k + 1][0];
                    }

                    int exclude = dp[i+1][k][boughtI+1];

                    dp[i][k][boughtI+1] = Math.max(include, exclude);
                }
            }
        }

        return dp[0][0][0];
    }










    /**


                                                        [ ]
                                           ______________|______________
                                           |                           |
    i=0                                   [3]                         [ ]
                                  _________|_________         _________|_________
                                  |                 |         |                 |
    i=1                         [3,3]=0            [3]       [3]               [ ]
                          ________|________ ________|________
                          |               | |               |
    i=2                  [5]            [ ] [3,5]=2        [3]


        There is no need to sell and buy the same day same amount ---> cause it just decreases the k count


        Instead of boughtI, just use holding == 0(cash) or 1(stock) == 0/1 == T/F boolean

        Generalized _ 2D _ StateMachine
          │           │    │
          │           │    │
          │           │    └── Model: State Machine (dp[k][Hold] vs dp[k][Unheld])
          │           └── Dimensions: State = Two tracking variables = (k, holding 0/1) === The state is split into two explicit variables: k (remaining transactions) and holding (binary 0/1 flag).
          └── Flexibility: Handles any 'k' transactions, not just k = 2 === Unlike the 0..4 pipeline approach (which is hardcoded for exactly 2 transactions), decomposing the state into (k, holding) allows it to handle any k transactions (even if k = 100).


     * @TimeComplexity O(2^N)
     * @SpaceComplexity O(N)
     */
    public static int maxProfitUsing_Generalized_2D_StateMachine_Backtracking1_TLE(int[] prices) {
        return backtrack2(prices, 0, 2, false);
    }

    private static int backtrack2(int[] prices, int i, int k, boolean isHold) {
        if (i == prices.length || k == 0) return 0;

        int skip = backtrack2(prices, i+1, k, isHold); // DO_NOTHING

        int doAction = 0;
        if (isHold) { // SELL_STOCK (add price, decrement remaining transactions)
            doAction = prices[i] + backtrack2(prices, i+1, k-1, false);
        } else { // BUY_STOCK (subtract price, move to holding state)
            doAction = -prices[i] + backtrack2(prices, i+1, k, true);
        }

        return Math.max(skip, doAction);
    }


    /**
     * @TimeComplexity O(N), as k is constant otherwise it'll be O(NK)
     * @SpaceComplexity O(N)
     */
    public static int maxProfitUsing_Generalized_2D_StateMachine_TopDownMemoDp1(int[] prices) {
        int k = 2;
        Integer[][][] memo = new Integer[prices.length][k+1][2]; // i,k,holding
        return dfs2(prices, 0, k, 0, memo);
    }

    private static int dfs2(int[] prices, int i, int k, int isHold, Integer[][][] memo) {
        if (i == prices.length || k == 0) return 0;
        else if (memo[i][k][isHold] != null) return memo[i][k][isHold];

        int skip = dfs2(prices, i+1, k, isHold, memo); // DO_NOTHING

        int doAction = 0;
        if (isHold == 0) { // BUY_STOCK (subtract price, move to isHold state)
            doAction = -prices[i] + dfs2(prices, i+1, k, 1, memo);
        } else {
            // SELL_STOCK (add price, decrement remaining transactions)
            doAction = prices[i] + dfs2(prices, i+1, k-1, 0, memo);
        }

        return memo[i][k][isHold] = Math.max(skip, doAction);
    }

    /**
     * @TimeComplexity O(N), as k is constant otherwise it'll be O(NK)
     * @SpaceComplexity O(N)
     */
    public static int maxProfitUsing_Generalized_2D_StateMachine_BottomUpDp1(int[] prices) {
        int n = prices.length;
        int maxTransactions = 2;

        int[][][] dp = new int[n + 1][maxTransactions + 1][2];
        // dp[n][*][*] = 0
        // dp[*][0][*] = 0

        for (int i = n - 1; i >= 0; i--) {

            for (int k = 1; k <= maxTransactions; k++) {
                // isHold = 0
                int skip = dp[i + 1][k][0];
                int buy = -prices[i] + dp[i + 1][k][1];
                dp[i][k][0] = Math.max(skip, buy);

                // isHold = 1
                skip = dp[i + 1][k][1];
                int sell = prices[i] + dp[i + 1][k - 1][0];
                dp[i][k][1] = Math.max(skip, sell);
            }
        }

        return dp[0][maxTransactions][0];
    }






    /**

        <pre>
        1. Mental Model => Cash Balance == Net Wallet Flow
        2. Perspective => How much cash do I have in hand right now?
        3. Goal Function => Math.max(...) to maximize wallet cash
        4. DP Traversal => Bottom-Up Iterative (i = 1 ... N-1)

        State == cash in hand


        CONCEPT:
        Models the trading lifecycle as a 5-state pipeline (0 -> 1 -> 2 -> 3 -> 4)
        where each state tracks the MAXIMUM cash balance achievable in that stage:

        [0] ---BUY---> [s1] ---SELL---> [s2] ---BUY---> [s3] ---SELL---> [s4]
         |              |                |               |
        REST           REST             REST            REST

        State 0: holding = 0, k = 2 -> Ready for 1st buy
        State 1: holding = 1, k = 2 -> Holding 1st stock
        State 2: holding = 0, k = 1 -> Sold 1st stock (ready for 2nd buy)
        State 3: holding = 1, k = 1 -> Holding 2nd stock
        State 4: holding = 0, k = 0 -> Finished both transactions

       </pre>
     * @TimeComplexity O(2^N) — Exploring 2 choices (Rest/Action) at every day
     * @SpaceComplexity O(N) — Call stack depth.
    */
    public static int maxProfitUsing_PipelineStateMachine_CashBalance_Backtracking1_TLE(int[] prices) {
        return backtrack(prices, 0, 0); // start at day 0, state 0
    }

    private static int backtrack(int[] prices, int day, int state) {
        if (day == prices.length || state == 4) return 0; // Base cases: ran out of days OR reached final state (completed 2 transactions)

        // Option 1: REST / Do nothing today (stay in current state)
        int rest = backtrack(prices, day+1, state);

        // Option 2: Take action today (Buy or Sell depending on current state)
        int action = 0;
        if (state == 0) { // Action: Buy 1st stock -> transition to state 1
            action = -prices[day] + backtrack(prices, day+1, 1);                      // Buy 1st stock (-EBP1)
        } else if (state == 1) { // Action: Sell 1st stock -> transition to state 2
            action = prices[day] + backtrack(prices, day+1, 2);                       // Sell 1st stock (+Profit1)
        } else if (state == 2) { // Action: Buy 2nd stock -> transition to state 3
            action = -prices[day] + backtrack(prices, day+1, 3);                      // Buy 2nd stock (-EBP2)
        } else if (state == 3) { // Action: Sell 2nd stock -> transition to state 4
            action = prices[day] + backtrack(prices, day+1, 4);                       // Sell 2nd stock (+Profit2)
        }

        return Math.max(rest, action);
    }





    /**
     * @TimeComplexity O(N.5) = O(N) — Exploring 2 choices (Rest/Action) at every day
     * @SpaceComplexity O(N) — Call stack depth.
    */
    public static int maxProfitUsing_PipelineStateMachine_CashBalance_TopDownMemoDp1(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        // memo[day][state]
        Integer[][] memo = new Integer[prices.length][5];
        return dfs(prices, 0, 0, memo);
    }
    private static int dfs(int[] prices, int day, int state, Integer[][] memo) {
        if (day == prices.length || state == 4) return 0;
        else if (memo[day][state] != null) return memo[day][state];

        int rest = dfs(prices, day + 1, state, memo); // REST

        // ACTION
        int action = 0;
        if (state == 0) {
            action = -prices[day] + dfs(prices, day + 1, 1, memo);                      // Buy 1st stock (-EBP1)
        } else if (state == 1) {
            action = prices[day] + dfs(prices, day + 1, 2, memo);                       // Sell 1st stock (+Profit1)
        } else if (state == 2) {
            action = -prices[day] + dfs(prices, day + 1, 3, memo);                      // Buy 2nd stock (-EBP2)
        } else if (state == 3) {
            action = prices[day] + dfs(prices, day + 1, 4, memo);                       // Sell 2nd stock (+Profit2)
        }

        return memo[day][state] = Math.max(rest, action);
    }




    /**


        dp[day][state]
        States:
        0 -> Before 1st Buy (Cash: 0)
        1 -> After 1st Buy (Holding 1st stock)
        2 -> After 1st Sell (Completed 1st transaction)
        3 -> After 2nd Buy (Holding 2nd stock)
        4 -> After 2nd Sell (Completed 2nd transaction)

     * @TimeComplexity O(N)
     * @SpaceComplexity O(N)
     */
    public static int maxProfitUsing_PipelineStateMachine_CashBalance_BottomUp2DTabulationDp1(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        int n = prices.length;
        int[][] dp = new int[n][5]; // or we can use s1,s2,s3,s4 = new int[n][5]

        // Base Case for Day 0
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        dp[0][2] = Integer.MIN_VALUE / 2; // Use safe offset to prevent integer overflow
        dp[0][3] = Integer.MIN_VALUE / 2;
        dp[0][4] = Integer.MIN_VALUE / 2;

        for (int i = 1; i < n; i++) {
            // State 0: Do nothing (cash remains 0)
            dp[i][0] = dp[i - 1][0];

            // State 1: Keep holding 1st stock OR Buy 1st stock today
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);

            // State 2: Keep 1st sell balance OR Sell 1st stock today
            dp[i][2] = Math.max(dp[i - 1][2], dp[i - 1][1] + prices[i]);

            // State 3: Keep holding 2nd stock OR Buy 2nd stock today (using 1st sell profit)
            dp[i][3] = Math.max(dp[i - 1][3], dp[i - 1][2] - prices[i]);

            // State 4: Keep 2nd sell balance OR Sell 2nd stock today
            dp[i][4] = Math.max(dp[i - 1][4], dp[i - 1][3] + prices[i]);
        }

        // Maximum profit will be in state 0 (no trades), state 2 (1 trade), or state 4 (2 trades)
        return Math.max(0, Math.max(dp[n - 1][2], dp[n - 1][4]));
    }



    /**
     * @TimeComplexity O(N)
     * @SpaceComplexity O(1)
     */
    public static int maxProfitUsing_PipelineStateMachine_CashBalance_BottomUp1DTabulationDp1(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        // 1D array representing the 5 pipeline states
        int[] dp = new int[5];

        // Base Case for Day 0
        dp[0] = 0;
        dp[1] = -prices[0];
        dp[2] = Integer.MIN_VALUE / 2;
        dp[3] = Integer.MIN_VALUE / 2;
        dp[4] = Integer.MIN_VALUE / 2;

        for (int i = 1; i < prices.length; i++) {
            int price = prices[i];

            // Iterate BACKWARD (4 down to 1) so we use values from day i-1
            dp[4] = Math.max(dp[4], dp[3] + price); // State 4: Keep 2nd sell OR Sell 2nd stock
            dp[3] = Math.max(dp[3], dp[2] - price); // State 3: Keep 2nd buy  OR Buy 2nd stock
            dp[2] = Math.max(dp[2], dp[1] + price); // State 2: Keep 1st sell OR Sell 1st stock
            dp[1] = Math.max(dp[1], dp[0] - price); // State 1: Keep 1st buy  OR Buy 1st stock
            // dp[0] remains 0
        }

        return Math.max(0, Math.max(dp[2], dp[4]));
    }




    /**

        <pre>
        State Machine Dynamic Programming Solution (Max 2 Transactions)

        CONCEPT:
        State = cash in hand
        Models the trading lifecycle as a 5-state pipeline (0 -> 1 -> 2 -> 3 -> 4)
        where each state tracks the MAXIMUM cash balance achievable in that stage:

        [0] ---BUY---> [s1] ---SELL---> [s2] ---BUY---> [s3] ---SELL---> [s4]
         |              |                |              |
        REST           REST             REST           REST

        STATES EXPLAINED:
        - s1: Max balance after 1st BUY   = max(rest in s1, buy from 0)   -> max(s1, 0 - price)
        - s2: Max balance after 1st SELL  = max(rest in s2, sell from s1)  -> max(s2, s1 + price)
        - s3: Max balance after 2nd BUY   = max(rest in s3, buy from s2)   -> max(s3, s2 - price)
        - s4: Max balance after 2nd SELL  = max(rest in s4, sell from s3)  -> max(s4, s3 + price)

        Side Note: Technically, this is a dynamic programming approach and we should actually be doing s2[i] = max(s2[i-1], s1[i-1]+prices[i])

        </pre>

    * @TimeComplexity O(N)
    * @SpaceComplexity O(1)
    */
    public static int maxProfitUsing_PipelineStateMachine_CashBalance_BottomUpNoMemoryDp1(int[] prices) {
        int s1=-prices[0], s2=Integer.MIN_VALUE, s3=Integer.MIN_VALUE, s4=Integer.MIN_VALUE;

        for(int i=1; i<prices.length; i++) {
            s1 = Math.max(s1, -prices[i]);      // Min Cost 1 Max   = Negative Cash 1
            s2 = Math.max(s2, s1 + prices[i]);  // Max Gain 1 Max   = Cash after Sell 1
            s3 = Math.max(s3, s2 - prices[i]);  // Effective Cost 2 = Max Cash after Buy 2
            s4 = Math.max(s4, s3 + prices[i]);  // Final Max Gain   = Final Max Cash
        }
        return Math.max(0,s4);
    }






    /**

        maxProfitUsing_StateMachine_CashBalance_Backtracking1_TLE == maxProfitUsing_StateMachine_EffectiveBuyPrice_Backtracking1_TLE
        maxProfitUsing_StateMachine_CashBalance_TopDownMemoDp1 == maxProfitUsing_StateMachine_EffectiveBuyPrice_TopDowMemoDp1

        1. Mental Model => Effective Buy Price = Reinvestment Cost
        2. Perspective => What is the real cost of this stock after discounts?
        3. Goal Function => Math.min(...) for costs, Math.max(...) for gains
        4. DP Traversal => Bottom-Up Iterative (For-Each loop)

        State == cash in hand


        CONCEPT:
        Models the trading lifecycle as a 5-state pipeline (0 -> 1 -> 2 -> 3 -> 4)
        where each state tracks the MAXIMUM cash balance achievable in that stage:

        [0] ---BUY---> [s1] ---SELL---> [s2] ---BUY---> [s3] ---SELL---> [s4]
         |              |                |               |
        REST           REST             REST            REST

     * @TimeComplexity O(N)
     * @SpaceComplexity O(N)
     */
    public static int maxProfitUsing_PipelineStateMachine_EffectiveBuyPrice_BottomUp2DTabulationDp1(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int n = prices.length;

        int[] ebp1 = new int[n];
        int[] profit1 = new int[n];
        int[] ebp2 = new int[n];
        int[] profit2 = new int[n];

        // Day 0 Base Cases
        ebp1[0] = prices[0];
        profit1[0] = 0;
        ebp2[0] = prices[0]; // (prices[0] - profit1[0])
        profit2[0] = 0;

        for (int i = 1; i < n; i++) {
            // EBP 1 & Profit 1
            ebp1[i] = Math.min(ebp1[i - 1], prices[i]);
            profit1[i] = Math.max(profit1[i - 1], prices[i] - ebp1[i - 1]);

            // EBP 2 & Profit 2 (Reinvesting Profit 1)
            ebp2[i] = Math.min(ebp2[i - 1], prices[i] - profit1[i - 1]);
            profit2[i] = Math.max(profit2[i - 1], prices[i] - ebp2[i - 1]);
        }

        return profit2[n - 1];
    }


    /**
     * @TimeComplexity O(N)
     * @SpaceComplexity O(1)
     */
    public static int maxProfitUsing_PipelineStateMachine_EffectiveBuyPrice_BottomUp1DTabulationDp1(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        // 1D Tabulation Array representing the 4 state streams:
        // dp[0] = Effective Buy Price 1 (ebp1)
        // dp[1] = Max Profit 1          (profit1)
        // dp[2] = Effective Buy Price 2 (ebp2)
        // dp[3] = Max Profit 2          (profit2)
        int[] dp = new int[4];

        // Day 0 Base Cases
        dp[0] = prices[0]; // ebp1
        dp[1] = 0;         // profit1
        dp[2] = prices[0]; // ebp2
        dp[3] = 0;         // profit2

        for (int i = 1; i < prices.length; i++) {
            int price = prices[i];

            // Evaluate backward/capture states to safely update the 1D array in-place
            int prevEbp1 = dp[0];
            int prevProfit1 = dp[1];
            int prevEbp2 = dp[2];

            dp[0] = Math.min(prevEbp1, price);               // ebp1
            dp[1] = Math.max(dp[1], price - prevEbp1);       // profit1
            dp[2] = Math.min(prevEbp2, price - prevProfit1); // ebp2 (discounted by profit1)
            dp[3] = Math.max(dp[3], price - prevEbp2);       // profit2
        }

        // The Result is the final state in our 1D table
        return dp[3];
    }




    /**

      Effective Buy Price == Reinvestment Cost

      EBP = price - profit/sell
      profit/sell = price - EBP

     * @TimeComplexity O(N)
     * @SpaceComplexity O(1)
     */
    public static int maxProfitUsing_PipelineStateMachine_EffectiveBuyPrice_BottomUpNoMemoryDp1(int[] prices) {
        int ebp1 = Integer.MAX_VALUE, ebp2 = Integer.MAX_VALUE;
        int profit1 = 0, profit2 = 0;

        for (int price : prices) {
            ebp1 = Math.min(ebp1, price); // EBP or the maximum profit if only one transaction is allowed
            profit1 = Math.max(profit1, price - ebp1); // profit = price-EBP

            ebp2 = Math.min(ebp2, price - profit1); // EBP or reinvest the gained profit in the second transaction
            profit2 = Math.max(profit2, price - ebp2); // profit = price-EBP
        }

        return profit2;
    }







    public static int maxProfitUsing_PipelineStateMachine_EffectiveBuyPrice_BottomUpNoMemoryDp2(int[] prices) {
        int ebp1 = Integer.MIN_VALUE, profit1 = 0;
        int ebp2 = Integer.MIN_VALUE, profit2 = 0;

        for (int price : prices) {
            ebp1  = Math.max(ebp1, -price);             // Max cash left after 1st buy
            profit1 = Math.max(profit1, ebp1 + price);  // Max cash left after 1st sell
            ebp2  = Math.max(ebp2, profit1 - price);    // Max cash left after 2nd buy
            profit2 = Math.max(profit2, ebp2 + price);  // Max cash left after 2nd sell
        }

        return profit2;
    }
}
