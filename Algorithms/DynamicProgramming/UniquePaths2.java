package Algorithms.DynamicProgramming;

import java.util.Arrays;

/**

<pre>


 DYNAMIC PROGRAMMING APPROACHES FLOW:

                        BACKTRACKING                == State (Decision Tree, Recurrence Relation / formula)
                              ↓
                        TOP-DOWN MEMO               == Same State + Memoization
                              ↓
                        BOTTOM-UP 2D                == (Dependencies + Iteration Order) == Same State => Replace dfs(...) with dp[...] & => Find dependencies - Dependencies determine iteration order
                              ↓
                        BOTTOM-UP OPTIMIZED SPACE 1D== Ask: "How many previous states do I really need?"
                              ↓
                        BOTTOM-UP IN PLACE


</pre>


 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 08 July 2026
 * @see Algorithms.DynamicProgramming.UniquePaths
 * @see Algorithms.DynamicProgramming.Triangle
 * @see Algorithms.DynamicProgramming.MinimumPathSum
 * @link 63. Unique Paths II <a href="https://leetcode.com/problems/unique-paths-ii/">Leetcode link</a>
 * @topics Array, Dynamic Programming, Matrix
 * @companies Amazon(4), Bloomberg(2), Microsoft(3), Google(2), Meta(9), Agoda(6), Nvidia(5), TikTok(4), Zepto(2)
 */
public class UniquePaths2 {
    public static void main(String[] args) {
        int[][] obstacleGrid = {{0,0,0},{0,1,0},{0,0,0}};

        System.out.printf("uniquePathsWithObstacles Using Backtracking_TLE: %d%n", uniquePathsWithObstaclesUsingBacktracking_TLE(obstacleGrid));
        System.out.printf("uniquePathsWithObstacles Using TopDownMemoDp: %d%n", uniquePathsWithObstaclesUsingTopDownMemoDp(obstacleGrid));
        System.out.printf("uniquePathsWithObstacles Using BottomUpDp: %d%n", uniquePathsWithObstaclesUsingBottomUpDp(obstacleGrid));
        System.out.printf("uniquePathsWithObstacles Using BottomUpDp OptimizedSpace: %d%n", uniquePathsWithObstaclesUsingBottomUpDpOptimizedSpace(obstacleGrid));
        System.out.printf("uniquePathsWithObstacles Using BottomUpDp InPlace: %d%n", uniquePathsWithObstaclesUsingBottomUpDpInPlace(obstacleGrid));
        obstacleGrid = new int[][]{{0,0,0},{0,1,0},{0,0,0}};
        System.out.printf("uniquePathsWithObstacles Using BottomUpDp InPlace 2: %d%n", uniquePathsWithObstaclesUsingBottomUpDpInPlace2(obstacleGrid));
    }


    /**


                                                                (r,c)
                                   down                         (0,0)                         right
                                   _______________________________|_______________________________
                                   |                                                             |
                                 (1,0)                                                         (0,1)
                   ________________|________________                             ________________|________________
                   |                               |                             |                               |
                 (2,0)                           (1,1)                         (2,1)                           (0,2)
         __________|__________
         |                   |
       (3,0)               (2,1)

     * @TimeComplexity O(2^(m+n))
     * @SpaceComplexity O(m+n)
     */
    public static int uniquePathsWithObstaclesUsingBacktracking_TLE(int[][] grid) {
        return backtrack(grid, 0, 0, grid.length, grid[0].length);
    }
    private static int backtrack(int[][] grid, int r, int c, int rows, int cols) {
        if (r == rows || c == cols) return 0;
        else if (grid[r][c] == 1) return 0; // cause sometimes grid[rows-1][cols-1] == 1
        else if (r == rows-1 && c == cols-1) return 1;

        return backtrack(grid, r+1, c, rows, cols) + backtrack(grid, r, c+1, rows, cols);
    }


    /**
     * @TimeComplexity O(m*n)
     * @SpaceComplexity O(m*n)
     */
    public static int uniquePathsWithObstaclesUsingTopDownMemoDp(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        Integer[][] memo = new Integer[rows][cols];
        return dfs(grid, 0, 0, rows, cols, memo);
    }
    private static int dfs(int[][] grid, int r, int c, int rows, int cols, Integer[][] memo) {
        if (r == rows || c == cols) return 0;
        else if (grid[r][c] == 1) return 0; // cause sometimes grid[rows-1][cols-1] == 1
        else if (r == rows-1 && c == cols-1) return 1;
        else if (memo[r][c] != null) return memo[r][c];

        return memo[r][c] = dfs(grid, r+1, c, rows, cols, memo) + dfs(grid, r, c+1, rows, cols, memo);
    }





    /**

        Top-Down is asking: Who do I call?
        Bottom-Up is asking: Who must already know the answer?

            dfs(r,c) -> dp[r][c]

            dp[r][c] = depends on dp[r+1][c] & dp[r][c+1]

            traverse = bottom to top row (↑) & right to left col (←)

            [0,0,0]         [0,0,0,0]
            [0,1,0]         [0,0,0,0]
            [0,0,0]         [0,0,0,0]
                            [0,0,0,0]
     * @TimeComplexity O(m*n)
     * @SpaceComplexity O(m*n)
     */
    public static int uniquePathsWithObstaclesUsingBottomUpDp(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        int[][] dp = new int[rows+1][cols+1];

        dp[rows-1][cols-1] = grid[rows-1][cols-1] == 1 ? 0 : 1;
        for (int r = rows-1, c=cols-2; c>=0; c--) {
            dp[r][c] = grid[r][c] == 1 ? 0 : dp[r][c+1];
        }

        for (int r = rows-2; r>=0; r--) {
            for (int c = cols-1; c>=0; c--) {
                if (grid[r][c] == 1) dp[r][c] = 0;
                else dp[r][c] = dp[r+1][c] + dp[r][c+1];
            }
        }

        return dp[0][0];
    }


    /**
     * @TimeComplexity O(m*n)
     * @SpaceComplexity O(n)
     */
    public static int uniquePathsWithObstaclesUsingBottomUpDpOptimizedSpace(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        int[] dp = new int[cols+1];

        dp[cols-1] = grid[rows-1][cols-1] == 1 ? 0 : 1;
        for (int r = rows-1, c=cols-2; c>=0; c--) {
            dp[c] = grid[r][c] == 1 ? 0 : dp[c+1];
        }

        for (int r = rows-2; r>=0; r--) {
            for (int c = cols-1; c>=0; c--) {
                if (grid[r][c] == 1) dp[c] = 0;
                else dp[c] = dp[c] + dp[c+1];
            }
        }

        return dp[0];
    }


    /**
     * @TimeComplexity O(m*n)
     * @SpaceComplexity O(1)
     */
    public static int uniquePathsWithObstaclesUsingBottomUpDpInPlace(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;

        grid[rows-1][cols-1] = grid[rows-1][cols-1] == 1 ? 0 : 1;
        for (int r = rows-1, c=cols-2; c>=0; c--) {
            grid[r][c] = grid[r][c] == 1 ? 0 : grid[r][c+1];
        }

        for (int r = rows-2; r>=0; r--) {
            for (int c = cols-1; c>=0; c--) {
                if (grid[r][c] == 1) grid[r][c] = 0;
                else if (c+1 == cols) grid[r][c] = grid[r+1][c];
                else grid[r][c] = grid[r+1][c] + grid[r][c+1];
            }
        }

        return grid[0][0];
    }



    /**
     * @TimeComplexity O(m*n)
     * @SpaceComplexity O(1)
     */
    public static int uniquePathsWithObstaclesUsingBottomUpDpInPlace2(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        if (obstacleGrid[0][0] == 1) return 0;
        obstacleGrid[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    obstacleGrid[i][j] = 0;
                } else {
                    if (i > 0 && j > 0) obstacleGrid[i][j] = obstacleGrid[i - 1][j] + obstacleGrid[i][j - 1];
                    else if (i > 0) obstacleGrid[i][j] = obstacleGrid[i - 1][j];
                    else if (j > 0) obstacleGrid[i][j] = obstacleGrid[i][j - 1];
                }
            }
        }
        return obstacleGrid[m - 1][n - 1];
    }
}
