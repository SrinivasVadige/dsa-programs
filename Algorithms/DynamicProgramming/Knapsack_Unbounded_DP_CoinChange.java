package Algorithms.DynamicProgramming;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * SAME AS "PERFECT SQUARES" PROBLEM
 *
 *
 *
 * PATTERNS:
 * amount = 7
 * nums = {1,3,4,5}
 *
 *                                                                                   {} sum=0 and need=7
 *                                                                              _______|_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
 *                                                                              |                                                                                                                                                       |                                                                                                |                      |
 *                                                                     {1} sum=1 and need=7-1=6                                                                                                                                 {3} sum=3 and need=7-3=4                                                                  {4} sum=1 and need=7-4=3            {5} sum=1 and need=7-5=2
 *                     _________________________________________________________|_______________________________________________________________________________                                   _____________________________________|__________________________________________________________
 *                     |                                                                       |                                       |                       |                                   |                                               |                      |                       |
 *    {1,1} sum=2 and need=6-1=5                                              {1,3} sum=4 and need=6-3=3                     {1,4} sum=5 and need=6-4=2    {1,5} sum=6 and need=6-5=1        {3,1} n=3                                           {3,3} n=1              {3,4} n=0 ✅       {3,5} n=-1 ❌
 * _________________________|__________________________                  _________________________|__________________________
 * |                |               |                  |                 |                |               |                  |
 * {1,1,1} n=4     {1,1,3} n=2   {1,1,4} n=1   {1,1,5} n=0 ✅           {1,3,1} n=2     {1,3,3} n=0✅    {1,3,4} n=-1 ❌    {1,3,5} n=-2 ❌
 *  s=3             s=5          s=6          s=7                           s=5              s=7               s=8               s=9
 *
 *
 * THOUGHTS:
 * --------
 * repeated subArrays {1,1,3} {1,5,5} ....
 * In Graph diagram - each node has child count as coins.length
 * but the ans is min depth which sums up to amount -----
 * same like perfect squares ---
 * not like LIS
 *
 *
 * so, in dp[amount+1] -- maintain the (amount matched subArr sum length) at each index
 * and replace it with another (amount matched subArr sum len) if smaller
 *
 * Brute Force will be n^n --> how to reduce it to n^2 or m^n - memo Math.min(dp[i], dp[need])?
 *
 *
 * if sort? --- nlogn even though we need to check each possibility
 * dp[amount+1]
 * temp += nums[j];
 * need = amount - temp
 * insert in dp for each needs
 * need == 0
 * Math.min(dp[i], dp[need])
 *
 *
 *
 * Note:
 * 1. In "Arrays.fill(dp, amount + 1);" if I use Integer.MAX_VALUE it'll be a problem as Integer.MAX_VALUE + 1 = Integer.MIN_VALUE
 * 2. Here we're not comparing coins[i] with coins[j] ... n^n like above graph, but instead compare each target amount (from 0, 1, 2, 3... to given amount) to all available coins
 *
 *
 * </pre>
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 04 Nov 2024
 */
public class Knapsack_Unbounded_DP_CoinChange {

    public static void main(String[] args) {
        int[] coins = {10, 1, 2};
        int amount = 11;
        System.out.println("coinChange: " + coinChange(coins, amount));
        System.out.println("coinChange2: " + coinChange2(coins, amount));
    }


    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(1)


        coins = [1,2,5], amount = 11
                 j

     */
    public static int coinChangesUsingBacktracking(int[] coins, int amount) {
        return backtrack(coins, 0, amount);
    }
    private static int backtrack(int[] coins, int i, int amount) {
        if (amount == 0) return 0;
        if (i == coins.length) return -1;
        int minCoins = Integer.MAX_VALUE;
        for (int j = 0; j * coins[i] <= amount; j++) { // j is the number of coins
            int currCoins = backtrack(coins, i + 1, amount - j * coins[i]);
            if (currCoins != -1) {
                minCoins = Math.min(minCoins, j + currCoins);
            }
        }
        return minCoins == Integer.MAX_VALUE ? -1 : minCoins;
    }





    /**
     * check needs/target amount from 0 to given amount sum --> reverse order in above graph i.e how many coins needed to reach each need amount
     * think like graph is already prepared and we check the target amounts from leaves to root.
     * And (0 target sum) or (need 'amount val') is the root node and ('amount val' target) or (need 0) are the leaves.
     */
    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1]; // or amounts[] or sums[]i.e each  target sum amount / toReach from 0 to amount
        Arrays.fill(dp, amount + 1);
        dp[0] = 0; // when target sum is 0 & assume that given sum is also 0, i.e. when need is 0 then 0 subArrays sums up to amount 0
        for (int target = 1; target <= amount; target++) { // Here we're not comparing coins[i] with coins[j] ... n^n like above graph, but instead compare each target amount (from 0, 1, 2, 3... to given amount) to all available coins
            for (int coin: coins) {
                int need = target - coin; // int need = currentAmount - targetAmount; ---> (target - coin) is diff and (coin - target) -------- so that it'll also skip to check dp[more than target] -- eg: if target = 3 i.e dp[3] then only check dp[0], dp[1] and dp[2] as we avoid -ve needs
                if(need>=0) {
                    dp[target] = Math.min(dp[target], 1 + dp[need]);
                }
                /*
                if "need" is not already calculated, then we are skipping that "need" using .min() & "1+need" and we don't update dp[need]
                And if "need" is already calculated, then we will use that and increase count(or subArray len) by 1 --- we still use min() to compare with dp[toReach]
                Note that we don't get perfect min subArray len for each amount.
                So, it is either "amount+1" (for amount not found in coins i.e 1 is not in coins[]) or perfect min subArray len. But dp[0] is always '0'
                */
            }
        }
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }

    public static int coinChange2(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount+1);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return (dp[amount] == (amount+1)) ? -1 : dp[amount];
    }

    public int coinChange3(int[] coins, int amount) {
        int[][] dp = new int[coins.length+1][amount+1];
        for(int i = 0;i<dp.length;i++)
        {
            for(int j = 0;j<dp[0].length;j++)
            {
                if(i == 0)
                    dp[i][j] = Integer.MAX_VALUE - 1;
                if(j == 0)
                    dp[i][j] = 0;
                if(i == 1 && j!=0)
                {
                    if(j%coins[0] == 0)
                        dp[i][j] = j/coins[0];
                    else
                        dp[i][j] = Integer.MAX_VALUE - 1;
                }
            }
        }
        for(int i = 2;i<dp.length;i++)
        {
            for(int j = 1;j<dp[0].length;j++)
            {
                if(coins[i-1]<=j)
                    dp[i][j] = Math.min(1+dp[i][j-coins[i-1]],dp[i-1][j]);
                else
                    dp[i][j] = dp[i-1][j];
            }
        }
        if(dp[coins.length][amount] >= Integer.MAX_VALUE - 1)
            return -1;
        return dp[coins.length][amount];
    }

    public int coinChangeDfs(int[] coins, int amount) {
        if (amount == 0) return 0;
        Map<Integer, Integer> dp = new HashMap<>();

        int result = dfs(coins, amount, dp);
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    private int dfs(int[] coins, int remaining, Map<Integer, Integer> dp) {
        if (remaining < 0) return Integer.MAX_VALUE;
        if (remaining == 0) return 0;
        if (dp.containsKey(remaining)) return dp.get(remaining);

        int minCoins = Integer.MAX_VALUE;
        for (int coin : coins) {
            int res = dfs(coins, remaining - coin, dp);
            if (res != Integer.MAX_VALUE) {
                minCoins = Math.min(minCoins, 1 + res);
            }
        }
        dp.put(remaining, minCoins);
        return minCoins;
    }

    public int coinChangeTopDown(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        Arrays.fill(dp, -2);
        return rec(coins, amount, dp);
    }

    private int rec(int[] coins, int amount, int[] dp) {
        if(amount < 0) return -1;

        if(amount == 0) return 0;

        if(dp[amount] != -2) {
            return dp[amount];
        }

        int min = Integer.MAX_VALUE;
        for(int i = 0; i < coins.length; i++) {
            int res = rec(coins, amount - coins[i], dp);
            if(res == -1) {
                continue;
            }
            min = Math.min(min, res+1);
        }
        if(min == Integer.MAX_VALUE) {
            dp[amount] = -1;
        } else {
            dp[amount] = min;
        }
        return dp[amount] ;
    }

}