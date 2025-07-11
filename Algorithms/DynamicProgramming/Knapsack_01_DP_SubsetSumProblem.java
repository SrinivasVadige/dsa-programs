package Algorithms.DynamicProgramming;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 10 July 2025
 * @link <a href="https://www.geeksforgeeks.org/problems/subset-sum-problem-1611555638/1">GeeksForGeeks problem link</a>
 * @link <a href="https://www.geeksforgeeks.org/dsa/subset-sum-problem-dp-25/">GeeksForGeeks article link</a>
 * @description Return T/F if the given array numbers can form the given target sum
 * @topics Array, Dynamic Programming
 * @companies Amazon, Microsoft

    Subset means it is a combination of elements in the given array --> not necessarily contiguous
    SubsetSum can be solved by using:
        1. Backtracking
        2. Dynamic Programming - (BottomUp, TopDown Memo)

    It's a variation of Knapsack 0-1 problem
    0-1 means either include or exclude the element -> like T or F
    we never know which element to include or exclude
 */
public class Knapsack_01_DP_SubsetSumProblem {
    public static void main(String[] args) {
        int[] arr = {2, 3, 7, 8, 10};
        int sum = 11;
        System.out.println("isSubsetSum Using Backtracking => " + isSubsetSumUsingBacktracking(arr, sum));
        System.out.println("isSubsetSum Using BottomUp DP => " + isSubsetSumUsingBottomUpDP(arr, sum));
        System.out.println("isSubsetSum Using BottomUp DP Optimized Space 🔥 => " + isSubsetSumUsingBottomUpDPOptimizedSpace(arr, sum));
        System.out.println("isSubsetSum Using TopDown Memo DP => " + isSubsetSumUsingTopDownMemoDP(arr, sum));
    }



        /**
     * @TimeComplexity O(2^n)
     * @SpaceComplexity O(n)
                      0  1  2  3  4
        Given array: {2, 3, 7, 8, 10}, sum: 11

                                   _____________[]__________________               INITIAL
                                  /                                 \
                         ________[2]______                     _____[]______        i=0
                        /                \                    /             \
                  ___[2,3]__           __[2]__               [3]            []      i=1
                 /          \         /       \            /    \          /   \
             [2,3,7]        [2,3]  [2,7]     [3,7]      [3,7]   [3]       [7]   []  i=2
           /        \
     [2,3,7,8]      [2,3,7]                                                         i=3



     */
    public static boolean isSubsetSumUsingBacktracking(int[] arr, int sum) {
        return backtrack(arr, sum, 0);
    }
    private static  boolean backtrack(int[] arr, int sum, int index) {
        if (sum == 0) {
            return true;
        }
        if (sum < 0 || index >= arr.length) {
            return false;
        }
        return backtrack(arr, sum-arr[index], index+1) || backtrack(arr, sum, index+1); // return (include_num || exclude_num);
    }




    /**
     * Approach -> Bottom-Up DP (Tabulation)
     * @TimeComplexity O(n*sum)
     * @SpaceComplexity O(n*sum)

        Maintain a 2D boolean grid of size (n+1)*(sum+1)
        if arr = {2, 3, 7, 8, 10}, sum = 11

                            exclude             ||         include
        dp[i][j] =         dp[i-1][j]           ||   dp[i-1][j - arr[i-1]]
                     dp[top_row][current_sum]   ||   dp[top_row][current_sum - numberInCurrentRow]
                        {top_cell nums}              {top_cell nums + current_num}

        when curr_num=7, curr_sum=9
        dp[3,9] =        dp[2,9]                ||   dp[2,2]
                           {2,3}                     {2,3} + {7} = {2,3,7}

                          sum / j →
                        _________________________________________________________________________
                        |     |   0    1    2    3    4     5    6    7    8     9   10    11   |
                    i ↓ |_____|_________________________________________________________________|
        {}           0  |  -  |  ✅   ❌   ❌   ❌   ❌   ❌   ❌   ❌   ❌   ❌   ❌   ❌   |
        {2}          1  |  2  |  ✅   ❌  "✅"  ❌   ❌   ❌   ❌   ❌   ❌   ❌   ❌   ❌   |
        {2,3}        2  |  3  |  ✅   ❌  *✅* /✅/  ❌  "✅"  ❌   ❌   ❌   ❌   ❌   ❌   | ← 5=3+2... i.e in {2,3} -> sum 5 is T as 5-3=2 and we check if we have any subset{} sum = 2,
        {2,3,7}      3  |  7  |  ✅   ❌   ✅   ✅   ❌   ✅   ❌   ✅   ❌  *✅* /✅/  ❌   | ← 9=7+2, 10=7+3 in "dp[3,9]= dp[2,9] || dp[2,2]" -- as dp[i,j] = dp[i-1][j] || dp[i-1][j - arr[i-1]]
        {2,3,7,8}    4  |  8  |  ✅   ❌   ✅   ✅   ❌   ✅   ❌   ✅   ✅   ✅   ✅   ✅   | ← 11=8+3 in dp[4,11]
        {2,3,7,8,10} 5  |  10 |  ✅   ❌   ✅   ✅   ❌   ✅   ❌   ✅   ✅   ✅   ✅   ✅   |
                        |_______________________________________________________________________|


        OBSERVATIONS:
        -------------
        1) all dp[i][0] are always true cause --> an empty set {} is always a subset of any set {....}
        i.e sum==0 can be formed by any subset - cause it has {} empty set

        2) If we have T ✅ cell at the top-row of current cell, then we can assign T ✅ to our current cell
        cause if sum=2 can be formed by {2}, then sum=2 can also be formed by {2,3} cause {2} is also a subset of {2,3}
        So, similarly -> {2}, {2,3}, {2,3,7}, {2,3,7,8}, {2,3,7,8,10} all these subsets have the subset of {2}

        3) If the top-row-number is less then current-row-number --> cause arr is not sorted in general we can also have {3,5,9,4,8} arr
        and
        if current-sum is less then then current-row-number
        i.e when calculating T/F grid for "7" i.e {2,3,7} we have to calculate
        all the sums right? like 0 to 11. So, in current-row all the current-sum upto <7 just copy paste the top cell values -> 0,1,2,3,4,5,6
        cause we can't form any new sums which are less than 7 i.e {2,3,7} subset --> we already know {2,3} subset sums.

        4) Looks like {2,3} subset can form sum=5 T it's because as 5-3=2 and we check if current main set {2,3} have any subset{} in which has sum=2
        i.e {2} subset can form sum=2
        => "top - numberInCurrentRow"

        That’s the key insight in 0/1 Knapsack / Subset Sum DP:
        dp[i][j] = dp[i-1][j] || dp[i-1][j - arr[i-1]]
        ---> dp[i-1][j] ---> means dp[top_row][current_sum] → Either exclude current element (copy from top)
        ---> dp[i-1][j - arr[i-1]] ---> means dp[top_row][current_sum - numberInCurrentRow] → Or include it (look back j - arr[i-1])




        Example for why it's "top_row" and not the current_row: --------
        while calculating dp[3,9] i.e current_sum=9 and current_row=3 and numberInCurrentRow=7 i.e {2,3,7} subset
        in dp[i-1][j - arr[i-1]] we need top row value dp[2][2] even though we copy the top value dp[2][2] in current dp[3,2] as current_sum=9 is < sum=2
        for more understanding this situation try this arr=[6, 6, 6, 3, 8]; sum=16;

        arr=[6, 6, 6, 3, 8]; sum=16;

        dp[i][j] = dp[i-1][j] || dp[i-1][j - arr[i-1]]    ❔
        dp[i][j] =   dp[top_row][current_sum]   ||   dp[top_row][current_sum - numberInCurrentRow]
                          top_cell

                          sum / j →
                        _____________________________________________________________________________________________
                        |     |   0    1    2    3    4     5    6    7    8     9   10    11   12  13  14  15   16 |
                    i ↓ |_____|_____________________________________________________________________________________|
        {}           0  |  -  |  ✅   ❌   ❌   ❌   ❌   ❌   ❌   ❌   ❌   ❌   ❌   ❌   ❌  ❌  ❌  ❌  ❌ |
        {6}          1  |  6  |  ✅   ❌   ❌   ❌   ❌   ❌   ✅   ❌   ❌   ❌   ❌   ❌   ❌  ❌  ❌  ❌  ❌ |
        {6,6}        2  |  6  |  ✅   ❌   ❌   ❌   ❌   ❌   ✅   ❌   ❌   ❌   ❌   ❌   ✅  ❌  ❌  ❌  ❌ |
        {6,6,6}      3  |  6  |  ✅   ❌   ❌   ❌   ❌   ❌   ✅   ❌   ❌   ❌   ❌   ❌   ✅  ❌  ❌  ❌  ❌ |
        {6,6,6,3}    4  |  3  |  ✅   ❌   ❌   ✅   ❌   ❌   ✅   ❌   ❌   ✅   ❌   ❌   ✅  ❌  ❌  ✅  ❌ |
        {6,6,6,3,8}  5  |  8  |  ✅   ❌   ❌   ✅   ❌   ❌   ✅   ❌   ✅   ✅   ❌   ✅   ✅  ❌  ✅  ✅  ❌ |
                        |___________________________________________________________________________________________|

        now see this dp[5,16] = dp[4,16] || dp[4,8] = F||F = F
        ---> if we use current_row instead of top_row then dp[5,16] = dp[4,16] || dp[5,8] = F||T = T ---> wrong
        so, we use current_row it'll count the same number again
       ---> this condition dp[top_row][current_sum - numberInCurrentRow] means we already included the numberInCurrentRow. So, don't include it again

        ❌ You can't look into the same row (i.e., dp[i][...]) when including an element
                --> You’ll count the same item multiple times
                --> You're violating the "0 or 1 usage" constraint
                --> You basically switch to unbounded knapsack, unintentionally
        ✅ You must look at dp[i-1][...] — i.e., "Can I make sum j - arr[i-1] without using this element?"

     */
    public static boolean isSubsetSumUsingBottomUpDP(int[] arr, int sum) {
        int n = arr.length;

        boolean[][] dp = new boolean[n+1][sum+1];
        for (int r = 0; r < n+1; r++) {
            dp[r][0] = true;
        }

        for (int r = 1; r < n+1; r++) { // row_number i.e., arr[r-1] is the current element --> cause arr index if diff
            for (int j = 1; j < sum+1; j++) { // sum
                if (arr[r-1] > j) {
                    // Exclude the current element
                    dp[r][j] = dp[r-1][j];
                }
                else {
                    // (Include || exclude)
                    dp[r][j] = dp[r-1][j] || dp[r-1][j-arr[r-1]]; // --> if currSum_j==currNum dp[r-1][j-arr[r-1]] becomes dp[r-1][0] i.e always true
                }
            }
        }
        return dp[n][sum];
    }







    /**
     * Approach: BottomUp DP Optimized Space 🔥
     * @TimeComplexity O(n*sum)
     * @SpaceComplexity O(sum)

        Maintain a 1D boolean dp of size (sum+1) instead of a 2D boolean dp of size (n+1)*(sum+1) --> optimized space
        if arr = {2, 3, 7, 8, 10}, sum = 11
        Here for each number
            1) we add the number to the existing set of numbers
            2) loop over all the sums and check if the current sum is possible by adding the current number

        dp[j] = dp[j] || dp[j - num];
              exclude || include


        INITIAL STATE - {}
               0     1     2     3     4     5     6     7     8     9    10     11    sum / j --→
            _________________________________________________________________________
            |  T  |  F  |  F  |  F  |  F  |  F  |  F  |  F  |  F  |  F  |  F  |  F  |
            |_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|
               ✅

        after num = 2 loop - {2}
               0     1     2     3     4     5     6     7     8     9    10     11
            _________________________________________________________________________
            |  T  |  F  |  T  |  F  |  F  |  F  |  F  |  F  |  F  |  F  |  F  |  F  |
            |_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|
               ✅         ✅

        after num = 3 loop - {2, 3}
               0     1     2     3     4     5     6     7     8     9    10     11
            _________________________________________________________________________
            |  T  |  F  |  T  |  T  |  F  |  T  |  F  |  F  |  F  |  F  |  F  |  F  |
            |_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|
               ✅         ✅    ✅          ✅

        after num = 7 loop - {2, 3, 7}
               0     1     2     3     4     5     6     7     8     9    10     11
            _________________________________________________________________________
            |  T  |  F  |  T  |  T  |  F  |  T  |  F  |  F  |  T  |  T  |  T  |  F  |
            |_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|
               ✅         ✅    ✅          ✅                ✅    ✅   ✅

        after num = 8 loop - {2, 3, 7, 8}
               0     1     2     3     4     5     6     7     8     9    10     11
            _________________________________________________________________________
            |  T  |  F  |  T  |  T  |  F  |  T  |  F  |  F  |  T  |  T  |  T  |  T  |
            |_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|
               ✅         ✅    ✅          ✅                ✅    ✅   ✅    ✅

        after num = 10 loop - {2, 3, 7, 8, 10}
               0     1     2     3     4     5     6     7     8     9    10     11
            _________________________________________________________________________
            |  T  |  F  |  T  |  T  |  F  |  T  |  F  |  F  |  T  |  T  |  T  |  T  |
            |_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|
               ✅         ✅    ✅          ✅                ✅    ✅   ✅    ✅
     */
    public static boolean isSubsetSumUsingBottomUpDPOptimizedSpace(int[] arr, int targetSum) {
        boolean[] dp = new boolean[targetSum + 1];
        dp[0] = true;

        for (int num : arr) {
            for (int sum = targetSum; sum >= num; sum--) {
                dp[sum] = dp[sum] || dp[sum - num];
                /*
                // or
                if (dp[sum] || !dp[sum - num]) {
                    continue;
                } else {
                    dp[sum] = true;
                }
                */
            }
        }

        return dp[targetSum];
    }


    /**
     * It's very simple to convert the 0-1 knapsack to unbounded knapsack problem. Just flip the sum traversal
     * It's because, if you move from left to right by updating the dp array.. then in the right side if you use "dp[sum - num];" will include the extra element - unbounded
     * check {@link Algorithms.DynamicProgramming.Knapsack_Unbounded_DP_CoinChange#coinChangeUsingBottomUpTabulationDpOptimizedSpace} for easy understanding
     */
    public static boolean isUnboundedSubsetSum(int[] arr, int targetSum) {
        boolean[] dp = new boolean[targetSum + 1];
        dp[0] = true;

        for (int num : arr) {
            for (int sum = num; sum <= targetSum; sum++) { // instead of "for(int sum = targetSum; sum >= num; sum--)"
                dp[sum] = dp[sum] || dp[sum - num];
            }
        }

        return dp[targetSum];
    }


    /**
     * Same as above {@link #isUnboundedSubsetSum}
     * instead of "for(int sum = num; sum <= targetSum; sum++)" we can do
     * for (sum loop...) {
     *     for (num loop...)
     * }
     */
    public static boolean isUnboundedSubsetSum2(int[] arr, int targetSum) {
        boolean[] dp = new boolean[targetSum + 1];
        dp[0] = true; // base case: sum 0 is always achievable

        for (int sum = 1; sum <= targetSum; sum++) {
            for (int num : arr) {
                if (sum >= num && dp[sum - num]) {
                    dp[sum] = true;
                    break; // we found a valid way to reach this sum
                }
            }
        }

        return dp[targetSum];
    }










    public static boolean isSubsetSumUsingBottomUpDPOptimizedSpace2(int[] arr, int targetSum) {
        boolean[] prev = new boolean[targetSum + 1];
        boolean[] curr = new boolean[targetSum + 1];
        prev[0] = true;

        for (int num : arr) {
            for (int sum = 0; sum <= targetSum; sum++) {
                // Exclude
                curr[sum] = prev[sum];
                // Include (only if valid)
                if (sum >= num) {
                    curr[sum] = curr[sum] || prev[sum - num];
                }
            }

            // Move current to previous for next item
            prev = curr.clone(); // or System.arraycopy
        }

        return prev[targetSum];
    }






    /**
     * @TimeComplexity O(n*sum)
     * @SpaceComplexity O(sum)
     */
    public static boolean isSubsetSumUsingBottomUpDPOptimizedSpace3(int[] arr, int sum) {
        int n = arr.length;
        boolean[] prev = new boolean[sum + 1];
        boolean[] curr = new boolean[sum + 1];

        // Mark prev[0] = true as it is true to
        // make sum = 0 using 0 elements
        prev[0] = true;

        // Fill the subset table in bottom-up manner
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= sum; j++) {
                if (arr[i-1] > j) {
                    curr[j] = prev[j];
                }
                else {
                    curr[j] = prev[j] || prev[j - arr[i - 1]];
                }
            }

            // Update prev to be the current row
            prev = curr.clone(); // or System.arraycopy(curr, 0, prev, 0, sum + 1);
        }
        return prev[sum];
    }










    // Function to initiate the subset sum check
    public static boolean isSubsetSumUsingTopDownMemoDP(int[] arr, int sum) {
        int n = arr.length;
        int[][] memo = new int[n + 1][sum + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return isSubsetSumRec(arr, n, sum, memo);
    }
    private static boolean isSubsetSumRec(int[] arr, int n, int sum,
                                  int[][] memo) {

        // If the sum is zero, we found a subset
        if (sum == 0) {
            return true;
        }

        // If no elements are left
        if (n <= 0) {
            return false;
        }

        // If the value is already computed, return it
        if (memo[n][sum] != -1) {
            return memo[n][sum] == 1;
        }

        // If the last element is greater than the sum,
        // ignore it
        if (arr[n - 1] > sum) {
            memo[n][sum] = isSubsetSumRec(arr, n - 1, sum, memo)
              		 	? 1 : 0;
        }
        else {

            // Include or exclude the last element directly
            memo[n][sum] = (isSubsetSumRec(arr, n - 1, sum, memo)
					|| isSubsetSumRec(arr, n - 1, sum - arr[n - 1], memo))
                      ? 1 : 0;
        }

        return memo[n][sum] == 1;
    }

}
