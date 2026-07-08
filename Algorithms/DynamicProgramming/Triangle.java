package Algorithms.DynamicProgramming;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 07 July 2026
 * @link 120. Triangle <a href="https://leetcode.com/problems/triangle/">Leetcode link</a>
 * @topics Array, Dynamic Programming
 * @companies Microsoft(2), Amazon(2), Bloomberg(2), Google(3), Meta(10), Oracle(3), Infosys(2), Walmart Labs(2), Goldman Sachs(2), Agoda(2), Upstart(2)
 */
public class Triangle {
    public static void main(String[] args) {
        // [2], [3,4], [6,5,7], [4,1,8,3]
        List<List<Integer>> triangle = List.of(List.of(2), List.of(3, 4), List.of(6, 5, 7), List.of(4, 1, 8, 3));
        System.out.printf("minimumTotal Using Backtracking_TLE %s\n", minimumTotalUsingBacktracking_TLE(triangle));
        System.out.printf("minimumTotal Using TopDownMemoDp %s\n", minimumTotalUsingTopDownMemoDp(triangle));
        System.out.printf("minimumTotal Using BottomUpDp %s\n", minimumTotalUsingBottomUpDp(triangle));
        System.out.printf("minimumTotal Using BottomUpDpOptimizedSpace %s\n", minimumTotalUsingBottomUpDpOptimizedSpace(triangle));
        System.out.printf("minimumTotal Using BottomUpDpInPlace %s\n", minimumTotalUsingBottomUpDpInPlace(triangle));
    }

    /**


            [-1]
            [2,3]
          [1,-1,-3]

                                                    (r,c)
                                                    (0,0)
                                    __________________|__________________
                                    |                                   |
                                  (1,0)                               (1,1)
                        ____________|____________           ____________|____________
                        |                       |           |                       |
                      (2,0)                   (2,1)       (2,1)                   (2,2)
               _________|_________     _________|_________
               |                 |     |                 |
             (3,0)             (3,1) (3,1)             (3,2)


     * @TimeComplexity O(2^n), where n = r*c
     * @SpaceComplexity O(n)
     */
    public static int minimumTotalUsingBacktracking_TLE(List<List<Integer>> triangle) {
        return backtrack(triangle, 0, 0);
    }
    private static int backtrack(List<List<Integer>> triangle, int r, int c) {
        if (r == triangle.size()) return 0;
        return triangle.get(r).get(c) + Math.min(backtrack(triangle, r+1, c), backtrack(triangle, r+1, c+1));
    }


    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n)
     */
    public static int minimumTotalUsingTopDownMemoDp(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] memo = new int[n*n];
        Arrays.fill(memo, Integer.MAX_VALUE);
        return dfs(triangle, 0, 0, memo, n);
    }
    private static int dfs(List<List<Integer>> triangle, int r, int c, int[] memo, int n) {
        int i = r*n + c;
        if (r == n) return 0;
        else if (memo[i] != Integer.MAX_VALUE) return memo[i];

        return memo[i] = triangle.get(r).get(c) + Math.min(dfs(triangle, r+1, c, memo, n), dfs(triangle, r+1, c+1, memo, n));
    }



    /**

    <pre>

        dp[r][c] depends on dp[r+1][c] & dp[r+1][c+1]

        Top-Down is asking: Who do I call?
        Bottom-Up is asking: Who must already know the answer?



        [2]             [0,0,0,0,0]
        [3,4]           [0,0,0,0,0]
        [6,5,7]         [0,0,0,0,0]
        [4,1,8,3]       [0,0,0,0,0]
                        [0,0,0,0,0]

    </pre>


     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n)
     */
    public static int minimumTotalUsingBottomUpDp(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] dp = new int[n+1][n+1];

        for (int r = n-1; r>=0; r--) {
            for(int c=0; c<triangle.get(r).size(); c++) {
                dp[r][c] = triangle.get(r).get(c) + Math.min(dp[r+1][c], dp[r+1][c+1]);
            }
        }

        return dp[0][0];
    }


    /**

        [2]
        [3,4]
        [6,5,7]
        [4,1,8,3]
                        [0,0,0,0,0]

     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(r)
     */
    public static int minimumTotalUsingBottomUpDpOptimizedSpace(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] dp = new int[n+1];

        for (int r = n-1; r>=0; r--) {
            for(int c=0; c<triangle.get(r).size(); c++) {
                dp[c] = triangle.get(r).get(c) + Math.min(dp[c], dp[c+1]);
            }
        }

        return dp[0];
    }



    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(1)
     */
    public static int minimumTotalUsingBottomUpDpInPlace(List<List<Integer>> triangle) {
        for (int row = triangle.size() - 2; row >= 0; row--) {
            for (int col = 0; col <= row; col++) {
                int bestBelow = Math.min(triangle.get(row + 1).get(col),triangle.get(row + 1).get(col + 1));
                triangle.get(row).set(col, bestBelow + triangle.get(row).get(col));
            }
        }
        return triangle.get(0).get(0);
    }








    /**

    Failing at triangle = [[-1],[2,3],[1,-1,-3]], Output = -2, Expected = -1

            [-1]
            [2,3]
          [1,-1,-3]

    because I am standing at (r,c)

    I can go

    ↓ (r+1, c)
    or
    ↘ (r+1, c+1)


     */
    public static int minimumTotal_NOT_WORKING(List<List<Integer>> triangle) {
        int sum = 0;
        for (List<Integer> row: triangle) {
            sum += Collections.min(row);
        }
        return sum;
    }
}
