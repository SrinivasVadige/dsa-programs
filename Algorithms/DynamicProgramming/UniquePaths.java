package Algorithms.DynamicProgramming;

import java.math.BigInteger;

/**
<pre>

    m*n allowed -> so RB? or TD dp?

    a[0][0] -> a[1][0] or a[0][1] only one sub-array will change

    now continue choices from a[1][0] - again 2
    and choices for a[0][1] - again 2

    binary tree

                          a[0][0]
                       ______|______
                      /             \
                  a[1][0]         a[0][1]
              ______|______     ______|______
             /             \   /             \
           2,0            1,1  1,1           0,2
         3,0  2,1


Here 1,1 is already calculated --- Top-Down memo DP with hashmap or dp array
will 1,1 before possibilities will change ?? no it would not change ****** as either move right or move down. It's constant

left node = parentVal + [1,0] i.e left increment

right node = parentVal + [0,1] i.e right increment

and we do not need left nodes as we want to move right side ?? --- we need as we can move only rights once we came down

start from m not 0



@author Srinvas Vadige, srinivas.vadige@gmail.com
@since 13 Oct 2024
</pre>


*/
public class UniquePaths {

    public static void main(String[] args) {

        int m = 3;
        int n = 7;
        System.out.println("uniquePathsUsingTopDownMemoDp: " + uniquePathsUsingTopDownMemoDp(m, n));
        System.out.println("uniquePathsUsingRecurseBacktracking: " + uniquePathsUsingRecurseBacktracking(m, n));
        System.out.println("uniquePaths: " + uniquePaths(m, n));
    }

    /**
     * Recursive Backtracking DP
     * @Time Complexity: O(2^(m+n))
     * @Space Complexity: O(1)
     */
    public static int uniquePathsUsingRecurseBacktracking(int m, int n) {
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
    public int uniquePathsUsingRecurseBacktracking2(int m, int n) {
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
     * @Time Complexity: O(m*n)
     * @Space Complexity: O(m*n)
     */
    public static int uniquePathsUsingTopDownMemoDp(int m, int n) {
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
     * Bottom Up Tabulation DP
     * @Time Complexity: O(m*n)
     * @Space Complexity: O(m*n)
     */
    public static int uniquePaths(int m, int n) {

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





    public int uniquePaths2(int m, int n) {
        return fact(m+n-2).divide(fact(n-1).multiply(fact(m-1))).intValue();
    }
    public BigInteger fact(int n){
        BigInteger res=BigInteger.ONE;
        for(int i=2;i<=n;i++){
            res=res.multiply(BigInteger.valueOf(i));
        }
        return res;
    }
}
