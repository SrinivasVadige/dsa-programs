package Algorithms.DynamicProgramming;

import java.math.BigInteger;
import java.util.Arrays;

/**

<pre>


 DYNAMIC PROGRAMMING APPROACHES PROGRESSION FLOW:

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
 * @since 13 Oct 2024
 * @see Algorithms.DynamicProgramming.Triangle
 * @see Algorithms.DynamicProgramming.MinimumPathSum
 * @link 62. Unique Paths <a href="https://leetcode.com/problems/unique-paths/">Leetcode link</a>
 * @topics Array, Dynamic Programming, Matrix
 * @companies Amazon(3), Goldman Sachs(2), SpaceX(4), Meta(3), Microsoft(3), Google(2), Infosys(2), Bloomberg(8), TikTok(5), Uber(4), Zoho(3), Nvidia(3), General Motors(3), Texas Instruments(3), Snap(2), Squarepoint Capital(2)
 */
public class UniquePaths {

    public static void main(String[] args) {

        int m = 3;
        int n = 7;
        System.out.println("uniquePaths Using Backtracking_TLE: " + uniquePathsUsingBacktracking_TLE(m, n));
        System.out.println("uniquePaths Using TopDownMemoDp: " + uniquePathsUsingTopDownMemoDp(m, n));
        System.out.println("uniquePaths Using BottomUpDp: " + uniquePathsUsingBottomUpDp(m, n));
        System.out.println("uniquePaths Using BottomUpDp OptimizedSpace: " + uniquePathsUsingBottomUpDpOptimizedSpace(m, n));
        System.out.println("uniquePaths Using Binomial Coefficients 1: " + uniquePathsUsingBinomialCoefficients1(m, n));
        System.out.println("uniquePaths Using Binomial Coefficients 2: " + uniquePathsUsingBigIntegerBinomialCoefficients2(m, n));
    }




    /**
                                                                  (r,c)
                                  down                            (0,0)                            right
                                   _________________________________|_________________________________
                                   |                                                                 |
                                 (1,0)                                                             (0,1)
                ___________________|____________________                          ___________________|____________________
                |                                      |                          |                                      |
              (2,0)                                  (1,1)                      (1,1)                                  (0,2)


     * @TimeComplexity O(2^(m+n))
     * @SpaceComplexity O(n)
     */
    public static int uniquePathsUsingBacktracking_TLE(int m, int n) {
        return backtrack(0, 0, m, n);
    }
    private static int backtrack(int r, int c, int rows, int cols) {
        if (r == rows || c == cols) return 0;
        else if (r == rows-1 && c == cols-1) return 1;

        return backtrack(r+1, c, rows, cols) + backtrack(r, c+1, rows, cols);
    }


    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(n)
     */
    public static int uniquePathsUsingTopDownMemoDp(int m, int n) {
        return dfs(0, 0, m, n, new Integer[m][n]);
    }
    private static int dfs(int r, int c, int rows, int cols, Integer[][] memo) {
        if (r == rows || c == cols) return 0;
        else if (r == rows-1 && c == cols-1) return 1;
        else if (memo[r][c] != null) return memo[r][c];

        return memo[r][c] = dfs(r+1, c, rows, cols, memo) + dfs(r, c+1, rows, cols, memo);
    }






    /**


        Top-Down is asking: Who do I call?
        Bottom-Up is asking: Who must already know the answer?

            dfs(r,c) == dp[r][c]

            dp[r][c] = dp[r+1][c] + dp[r][c+1]

            rows dir = ↑
            cols dir = ←

            return dp[0][0]

     * @TimeComplexity O(mn)
     * @SpaceComplexity O(mn)
     */
    public static int uniquePathsUsingBottomUpDp(int m, int n) {
        int[][] dp = new int[m][n];

        for (int c=n-1; c>=0; c--) dp[m-1][c] = 1;
        for (int r=m-1; r>=0; r--) dp[r][n-1] = 1;

        for (int r=m-2; r>=0; r--) {
            for (int c=n-2; c>=0; c--) {
                dp[r][c] = dp[r+1][c] + dp[r][c+1];
            }
        }

        return dp[0][0];
    }


    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(n)
     */
    public static int uniquePathsUsingBottomUpDpOptimizedSpace(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        for (int r=m-2; r>=0; r--) {
            for (int c=n-2; c>=0; c--) {
                dp[c] = dp[c] + dp[c+1];
            }
        }

        return dp[0];
    }


    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(1)
     */
    public static int uniquePathsUsingBinomialCoefficients1(int m, int n) {
        long answer = 1;
        for (int i = n; i < (m + n - 1); i++) {
            answer *= i;
            answer /= (i - n + 1);
        }
        return (int) answer;
    }




    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(1)
     */
    public static int uniquePathsUsingBigIntegerBinomialCoefficients2(int m, int n) {
        return fact(m+n-2).divide(fact(n-1).multiply(fact(m-1))).intValue();
    }
    public static BigInteger fact(int n){
        BigInteger res=BigInteger.ONE;
        for(int i=2;i<=n;i++){
            res=res.multiply(BigInteger.valueOf(i));
        }
        return res;
    }
















    /**
     * Recursive Backtracking DP
     * @TimeComplexity O(2^(m+n))
     * @SpaceComplexity O(1)
     */
    public static int uniquePathsUsingRecurseBacktracking2(int m, int n) {
        int[] dp = new int[1];
        rec(m-1, n-1, dp); // to loop from m-1, n-1 to 0,0 (or) use m,n up to base case as 1,1 instead of 0,0
        return dp[0];
    }
    static void rec(int m, int n, int[] dp){
        if(m==0 && n==0){
            dp[0]++;
            return;
        }
        if(m < 0 || n < 0) return; // bc
        rec(m-1, n, dp);
        rec(m, n-1, dp);
    }



    int c=0;
    public int uniquePathsUsingRecurseBacktracking3(int m, int n) {
        c=0;
        dfs(0, 0, m, n);
        return c;
    }
    void dfs(int i, int j, int m, int n){
        if(i==m-1 && j==n-1){
            c++;
            return;
        }
        if(i >= m || j >= n) return; // bc
        dfs(i+1, j, m, n);
        dfs(i, j+1, m, n);
    }





    /**
     * Top Down Memo DP
     * @TimeComplexity O(m*n)
     * @SpaceComplexity O(m*n)
     */
    public static int uniquePathsUsingTopDownMemoDp2(int m, int n) {
        int[][] dp = new int[m][n];
        int ways =recMemo(m-1, n-1, dp);
        return ways;
    }

    static int recMemo(int m, int n, int[][] dp) {
        if(m < 0 || n < 0) return 0; // bc
        if(m==0 && n==0){
            return 1;
        }
        if (dp[m][n] != 0) {
            return dp[m][n];
        }
        int ways = recMemo(m-1, n, dp) + recMemo(m, n-1, dp);
        return dp[m][n] = ways;
    }






    /**
     * BottomUp Tabulation DP
     * @TimeComplexity O(m*n)
     * @SpaceComplexity O(m*n)
     */
    public static int uniquePathsUsingBottomUpDp2(int m, int n) {

        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 1;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m - 1][n - 1];
    }
}
