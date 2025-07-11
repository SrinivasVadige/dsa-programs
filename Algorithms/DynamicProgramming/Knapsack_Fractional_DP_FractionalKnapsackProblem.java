package Algorithms.DynamicProgramming;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 27 May 2025
 * @link <a href="https://www.geeksforgeeks.org/problems/0-1-knapsack-problem0945/1">GeeksForGeeks problem link</a>
 * @link <a href="https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/">GeeksForGeeks article link</a>
 *
 */
public class Knapsack_Fractional_DP_FractionalKnapsackProblem {
    public static void main(String[] args) {
        int[] weights = {1, 2, 3, 2};
        int[] values = {10, 15, 40, 30};
        int capacity = 6;

        System.out.println("Maximum value in Knapsack = " + knapsack(weights, values, capacity));
    }

    public static int knapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            int itemWeight = weights[i - 1];
            int itemValue = values[i - 1];
            for (int w = 1; w <= capacity; w++) {
                // if(i==0 || w==0) dp[i][w] = 0; // Base case, already initialized to 0
                if (itemWeight <= w) {
                    // max(top, top_left+itemVal)
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - itemWeight] + itemValue);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        return dp[n][capacity];
    }


    public static int knapsackSpaceOptimized(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[] dp = new int[capacity + 1];

        for (int i = 0; i < n; i++) {
            int itemWeight = weights[i];
            int itemValue = values[i];
            for (int w = capacity; w >= itemWeight; w--) {
                dp[w] = Math.max(dp[w], dp[w - itemWeight] + itemValue);
            }
        }

        return dp[capacity];
    }




    /**
     * Working but TLE
     */
    public static int knapsackRecursive(int[] weights, int[] values, int capacity) {
        return backtrack(weights, values, capacity, 0); // no need for currSum, just decrease capacity till 0
    }

    public static int backtrack(int[] weights, int[] values, int capacity, int currentIndex) {
        if (capacity <= 0 || currentIndex >= weights.length) return 0;

        int include = 0;
        if (weights[currentIndex] <= capacity) { // currWeight <= capacity
            include = values[currentIndex] + backtrack(weights, values, capacity - weights[currentIndex], currentIndex + 1);
        }
        int exclude = backtrack(weights, values, capacity, currentIndex + 1);
        return Math.max(include, exclude);
    }
}
