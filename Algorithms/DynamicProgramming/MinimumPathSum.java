package Algorithms.DynamicProgramming;

import java.util.Arrays;

/**
<pre>
 0 & +ve nums

 top left to bottom right

    |°            |
    |    ↓        |
    |      -→     |
    |            °|

 dpad => down or right

                    ----- int[][] grid -----

                            [0][0]
                    [1][0]           [0][1]
                [2][0] [1][1]    [1][1]   [0][2]


 Patterns:
 1. weighted binary graph --> memo or rb ???
 2. [1][1] -- repeated ele => memo will work even weights / distance => because we want min. So, memo min distance
 3. top-down memo dp -> yes keep the small and break the rec of big -- or bottom-up tabulation

[1,3,1]
[1,5,1]
[4,2,1]

</pre>



 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 14 Oct 2024
 * @see Algorithms.DynamicProgramming.Triangle
 * @see Algorithms.DynamicProgramming.UniquePaths
 * @link 64. Minimum Path Sum <a href="https://leetcode.com/problems/minimum-path-sum/">Leetcode link</a>
 * @topics Array, Dynamic Programming, Matrix
 * @companies Amazon(3), Goldman Sachs(2), SpaceX(4), Meta(3), Microsoft(3), Google(2), Infosys(2), Bloomberg(8), TikTok(5), Uber(4), Zoho(3), Nvidia(3), General Motors(3), Texas Instruments(3), Snap(2), Squarepoint Capital(2)
 */
public class MinimumPathSum {

    public static void main(String[] args) {
        int[][] grid = {{1,3,1},{1,5,1},{4,2,1}};
        System.out.println("minPathSum Using Backtracking_TLE: " + minPathSumUsingBacktracking_TLE(grid));
        System.out.println("minPathSum Using TopDownMemoDp: " + minPathSumUsingTopDownMemoDp(grid));
        System.out.println("minPathSum Using BottomUpDp: " + minPathSumUsingBottomUpDp(grid));
        System.out.println("minPathSum Using BottomUpDp OptimizedSpace: " + minPathSumUsingBottomUpDpOptimizedSpace(grid));
        System.out.println("minPathSum Using BottomUpDp InPlace: " + minPathSumUsingBottomUpDpInPlace(grid));
    }



    /**
                                                                (r,c)
                                                                (0,0)
                                          ________________________|________________________
                                          |                                               |
                                        (0,1)                                           (1,0)
                             _____________|_____________                     _____________|_____________
                             |                         |                     |                         |
                           (0,2)                     (1,1)                 (1,1)                     (2,0)



     * @TimeComplexity O(2^(m+n))
     * @SpaceComplexity O(m+n)
     */
    public static int minPathSumUsingBacktracking_TLE(int[][] grid) {
        return backtrack(grid, 0, 0, grid.length, grid[0].length);
    }
    private static int backtrack(int[][] grid, int r, int c, int rows, int cols) {
        if (r == rows-1 && c == cols-1) return grid[r][c];
//        else if (r == rows || c == cols) return Integer.MAX_VALUE;

        int down = Integer.MAX_VALUE, right = Integer.MAX_VALUE;
        if (r < rows-1) down = backtrack(grid, r+1, c, rows, cols);
        if (c < cols-1) right = backtrack(grid, r, c+1, rows, cols);

        return grid[r][c] + Math.min(down, right);
    }


    
    
    
    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(mn)
     */
    public static int minPathSumUsingTopDownMemoDp(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        int[][] memo = new int[rows][cols];
        for (int[] row: memo) {
            Arrays.fill(row, -1);
        }
        return dfs(grid, 0, 0, grid.length, grid[0].length, memo);
    }
    private static int dfs(int[][] grid, int r, int c, int rows, int cols, int[][] memo) {
        if (r == rows-1 && c == cols-1) return grid[r][c];
        else if (memo[r][c] >= 0) return memo[r][c];
//        else if (r == rows || c == cols) return Integer.MAX_VALUE;

        int down = Integer.MAX_VALUE, right = Integer.MAX_VALUE;
        if (r < rows-1) down = dfs(grid, r+1, c, rows, cols, memo);
        if (c < cols-1) right = dfs(grid, r, c+1, rows, cols, memo);

        return memo[r][c] = grid[r][c] + Math.min(down, right);
    }




    
    
    /**


        dfs(r,c) -> dp[r][c]

        dp[r][c] depends on dp[r+1][c] & dp[r][c+1] -> calculate bottom rows first & right to left (<-) col dir

        [1,3,1]     [0,0,0,0]
        [1,5,1]     [0,0,0,0]
        [4,2,1]     [0,0,0,0]
                    [0,0,0,0]

     * @TimeComplexity O(mn)
     * @SpaceComplexity O(mn)
     */
    public static int minPathSumUsingBottomUpDp(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        int[][] dp = new int[rows][cols];

        dp[rows-1][cols-1] = grid[rows-1][cols-1];
        for (int r=rows-1, c=cols-2; c>=0; c--) {
            dp[r][c] = grid[r][c] + dp[r][c+1];
        }

        for (int r = rows-2; r>=0; r--) {
            for (int c=cols-1; c>=0; c--) {
                int down = Integer.MAX_VALUE, right = Integer.MAX_VALUE;
                if (r+1 < rows) down = dp[r+1][c];
                if (c+1 < cols) right = dp[r][c+1];

                dp[r][c] = grid[r][c] + Math.min(down, right);

                /*
                    // or
                    if (r == rows-1) dp[r][c] = grid[r][c] + dp[r][c+1];
                    else if (c == cols-1) dp[r][c] = grid[r][c] + dp[r+1][c];
                    else dp[r][c] = grid[r][c] + Math.min(dp[r+1][c], dp[r][c+1]);
                 */
            }
        }

        return dp[0][0];
    }



    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(mn)
     */
    public int minPathSumUsingBottomUpDp2(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        int[][] dp = new int[rows][cols];
        for (int r = rows-1; r >= 0; r--) {
            for (int c = cols-1; c >= 0; c--) {
                if (r == rows-1 && c != cols-1) dp[r][c] = grid[r][c] + dp[r][c + 1];
                else if (c == cols-1 && r != rows-1) dp[r][c] = grid[r][c] + dp[r + 1][c];
                else if (c != cols-1 && r != rows-1) dp[r][c] = grid[r][c] + Math.min(dp[r + 1][c], dp[r][c + 1]);
                else dp[r][c] = grid[r][c];
            }
        }
        return dp[0][0];
    }


    
    
    
    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(n)
     */
    public static int minPathSumUsingBottomUpDpOptimizedSpace(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        int[] dp = new int[cols+1];

        for (int r=rows-1, c=cols-1; c>=0; c--) {
            dp[c] = grid[r][c] + dp[c+1];
        }
        dp[cols] = Integer.MAX_VALUE;

        for (int r = rows-2; r>=0; r--) {
            for (int c=cols-1; c>=0; c--) {
                dp[c] = grid[r][c] + Math.min(dp[c], dp[c+1]);
            }
        }

        return dp[0];
    }

    
    
    
    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(1)
     */
    public static int minPathSumUsingBottomUpDpInPlace(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        for (int i = rows-1; i >= 0; i--) {
            for (int j = cols-1; j >= 0; j--) {
                if (i == rows-1 && j != cols - 1) grid[i][j] = grid[i][j] + grid[i][j + 1];
                else if (j == cols - 1 && i != rows-1) grid[i][j] = grid[i][j] + grid[i + 1][j];
                else if (j != cols - 1 && i != rows-1) grid[i][j] = grid[i][j] + Math.min(grid[i + 1][j], grid[i][j + 1]);
            }
        }
        return grid[0][0];
    }














    /*
        ---------------------------------------------------
                        OLD METHODS
        ---------------------------------------------------

     */




    /**
     * @TimeComplexity O(m) + O(n) + O(mn) = O(mn)
     * @SpaceComplexity O(mn)
     */
    public static int minPathSumBottomUpTabulation(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n]; // or we can just modify the existing grid
        dp[0][0] = grid[0][0]; // don't use clone() as we are adding lefts and tops below
        for(int i = 1; i < m; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }
        for(int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j-1] + grid[0][j];
        }
        for(int i = 1; i < m; i++) {
            for(int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j];
            }
        }
        return dp[m-1][n-1];
    }

    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(mn)
     */
    public static int minPathSumBottomUpTabulationMyApproach(int[][] grid) {
        int[][] dp = new int[grid.length][grid[0].length];

        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                 int left = j>0? ( dp[i][j-1] ): 0;
                 int top = i>0? ( dp[i-1][j] ) : 0;

                 if(j==0)
                    dp[i][j] += ( top + grid[i][j]  );
                 else if(i==0)
                    dp[i][j] += (  left + grid[i][j]  );
                 else
                    dp[i][j] += (  Math.min(top, left) + grid[i][j]  );
            }
        }

        return dp[grid.length-1][grid[0].length-1];
    }

    public static int minPathSumBottomUpTabulationSimilar(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        int[][] dp = new int[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                dp[i][j] = Integer.MAX_VALUE;
                if(i+j == 0){
                    dp[i][j] = grid[i][j];
                }
                if(i>0){
                    dp[i][j] = dp[i-1][j] + grid[i][j];
                }
                if(j>0){
                    dp[i][j] = Math.min(dp[i][j],dp[i][j-1] + grid[i][j]);
                }
            }
        }
        return dp[n-1][m-1];
    }


    /**
     * [1,3,1]         [1,4,5]  - - -
     * [1,5,1]         [2,7,6]      |     => is the min path
     * [4,2,1]         [6,8,7]      |
     *
     *                                                         rec(3,3)
     *                                             ________________|________________________________________
     *                                            /                                                         \
     *                                         rec(2,3)                                                  rec(3,2)
     *                                ____________|___________________________                                ____________|______________
     *                               /                                        \                              /                           \
     *                            rec(1,3)                                rec(2,2)                         rec(2,2)                   rec(3,1)
     *                  ____________|_____________                        _____|_____
     *                 /                          \                    (1,2)       (2,1)
     *               rec(0,3)                   rec(1,2)            (0,2)  (1,1)      |
     *       _________|_________             ______|_______        (0,1)  (0,1)(1,0)
     *      /                   \           /              \
     *     --                 rec(0,2)    rec(0,2)        rec(1,1)
     *                           \                       ___|___
     *                        rec(0,1)                  /       \
     *                           \                  rec(0,1)    rec(1,0)
     *                        rec(0,0)                 \         /
     *                                             rec(0,0)    rec(0,0)
     *
     */
    public static int minPathSumTopDownMemoMyApproach(int[][] grid) {
        int[][] dp = new int[grid.length][grid[0].length];
        dp[0][0] = grid[0][0];
        recMyApproach(grid, dp, grid.length-1, grid[0].length-1);
        return dp[grid.length-1][grid[0].length-1];
    }
    // without sending m,n to child node/recursion we can't do grid[m-1][n] top, grid[m][n-1] left
    public static int recMyApproach(int[][] grid, int[][] dp, int m, int n) {
        if(m == 0 && n == 0) return dp[m][n] = grid[m][n];

        if(dp[m][n] != 0)
            return dp[m][n];

        // row=0 means left
        if(m == 0) return dp[m][n] = grid[m][n] + recMyApproach(grid, dp, m, n-1);
        // col=0 means top
        if(n == 0) return dp[m][n] = grid[m][n] + recMyApproach(grid, dp, m-1, n);

        int left = recMyApproach(grid, dp, m, n-1);
        int top = recMyApproach(grid, dp, m-1, n);
        return dp[m][n] = grid[m][n] + Math.min(left, top);
    }

    public static int minPathSumTopDownMemo(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int dp[][] = new int[m][n];
        return find(grid, m - 1, n - 1, dp);
    }
    public static int find(int[][] grid, int m, int n, int dp[][]) {
        if (m == 0 && n == 0)    return grid[m][n];
        else if (m < 0 || n < 0)    return Integer.MAX_VALUE;
        else if (dp[m][n] != 0)    return dp[m][n];
        return dp[m][n] = grid[m][n] + Math.min(find(grid, m - 1, n, dp), find(grid, m, n - 1, dp));
    }



    public static int minPathSumTopDownMemo2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        for(int i=0;i<m;i++){  // not required
            for(int j=0;j<n;j++){
                dp[i][j] = -1;
            }
        }
        return rec2(grid,dp,0,0);
    }

    private static int rec2(int[][] grid, int[][] dp, int i, int j) { // or pass m, n as params
        int m = grid.length;
        int n = grid[0].length;

        if (i == m-1 && j == n-1) return grid[i][j];
        if (dp[i][j] != -1) return dp[i][j]; // compare == 0
        int min = Integer.MAX_VALUE;
        if (i+1 < m) min = Math.min(min, rec2(grid, dp, i+1, j));
        if (j+1 < n) min = Math.min(min, rec2(grid, dp, i, j+1));
        dp[i][j] = min + grid[i][j];
        return dp[i][j];
    }





    /**
     *
     * It's not working --------
     *
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(mn)
     */
    public static int minPathSumTopDownMemo3(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        rec3(grid, dp, 0);
        return dp[m-1][n-1];
    }

    private static void rec3(int[][] grid, int[][] dp, int i) { // or pass m, n as params
        int m = grid.length;
        int n = grid[0].length;
        if (i == m-1) {
            dp[i%2][n-1] = grid[i][n-1];
            for (int j = n-2; j >= 0 ; j--) dp[i%2][j] = dp[i%2][j+1]+grid[i][j];
            return;
        }
        rec3(grid,dp,(i+1));
        dp[i%2][n-1] = dp[(i+1)%2][n-1] + grid[i][n-1];
        for (int j = n-2; j >= 0; j--)
        dp[i%2][j] = Math.min(dp[(i+1)%2][j],dp[i%2][j+1]) + grid[i][j];
    }

}
