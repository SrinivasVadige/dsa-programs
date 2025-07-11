package Algorithms.DynamicProgramming;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 10 July 2025
 * @description same like {@link Algorithms.DynamicProgramming.Knapsack_01_DP_SubsetSumProblem} but we can repeat the same element in given count times
 * @topics Array, Dynamic Programming
 */
public class Knapsack_Bounded_DP_BoundedSubsetSumProblem {
    public static void main(String[] args) {
        int[] arr = {2, 3, 7, 8, 10};
        int[] count = {1, 2, 3, 1, 2};
        int sum = 37;
        System.out.println("isBoundedSubsetSum Using Backtracking => " + isBoundedSubsetSumUsingBacktracking(arr, count, sum));
        System.out.println("isBoundedSubsetSum Using BottomUp DP => " + isBoundedSubsetSumUsingBottomUpDP(arr, count, sum));
    }


    public static boolean isBoundedSubsetSumUsingBacktracking(int[] arr, int[] count, int sum) {
        return backtrack(arr, count, sum, 0, 0);
    }

    private static boolean backtrack(int[] arr, int[] count, int sum, int index, int countUsed) {
        if (sum == 0) {
            return true;
        }
        if (sum < 0 || index >= arr.length || countUsed >= count[index]) {
            return false;
        }
        return backtrack(arr, count, sum - arr[index], index + 1, countUsed + 1) || backtrack(arr, count, sum, index + 1, countUsed); // return (include_num || exclude_num);
    }





    public static boolean isBoundedSubsetSumUsingBottomUpDP(int[] arr, int[] count, int targetSum) {
        int n = arr.length;
        boolean[][] dp = new boolean[n + 1][targetSum + 1];

        // Base case: sum = 0 is always possible with 0 items
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }

        // Fill the table
        for (int i = 1; i <= n; i++) {
            int num = arr[i - 1];
            int maxCount = count[i - 1];

            for (int sum = 0; sum <= targetSum; sum++) {
                // Case 1: don't take the current item
                dp[i][sum] = dp[i - 1][sum];

                // Case 2: try using current item k times (1 ≤ k ≤ maxCount)
                for (int k = 1; k <= maxCount; k++) {
                    int prevSum = sum - k * num;
                    if (prevSum < 0) break;

                    if (dp[i - 1][prevSum]) {
                        dp[i][sum] = true;
                        break; // no need to try more `k` values
                    }
                }
            }
        }

        return dp[n][targetSum];
    }







    public static boolean isBoundedSubsetSumUsingBottomUpDP2(int[] arr, int[] count, int targetSum) {
        int n = arr.length;
        boolean[] dp = new boolean[targetSum + 1];
        dp[0] = true; // sum 0 is always possible

        for (int i = 0; i < n; i++) {
            int val = arr[i];
            int maxUse = count[i];

            // We use a temporary array to avoid overwriting states in the same round
            int[] used = new int[targetSum + 1];

            for (int sum = 0; sum <= targetSum; sum++) {
                if (dp[sum]) {
                    used[sum] = 0; // if already reachable, no item used
                } else if (sum >= val && dp[sum - val] && used[sum - val] < maxUse) {
                    dp[sum] = true;
                    used[sum] = used[sum - val] + 1;
                }
            }
        }

        return dp[targetSum];
    }
}
