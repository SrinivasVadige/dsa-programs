package Algorithms.DynamicProgramming;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 27 July 2026
 * @link 221. Maximal Square <a href="https://leetcode.com/problems/maximal-square/">Leetcode link</a>
 * @topics Array, Dynamic Programming, Matrix
 * @companies Google(4), eBay(2), Microsoft(3), Amazon(2), Oracle(2), Wise(10), PayPal(9), ByteDance(5), Meta(4), TikTok(4), Flipkart(3), Goldman Sachs(3), Nvidia(3), PhonePe(3), Booking.com(3)
 */
public class MaximalSquare {
    public static void main(String[] args) {
        char[][] matrix = {{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}};
        System.out.println("maximalSquare Using BruteForce 1:" + maximalSquareUsingBruteForce1(matrix));
        System.out.println("maximalSquare Using BruteForce 2:" + maximalSquareUsingBruteForce2(matrix));

        System.out.println("maximalSquare Using BruteForce 1 With Memo1:" + maximalSquareUsingBruteForce1WithMemo1(matrix));
        System.out.println("maximalSquare Using BruteForce 1 With Memo2:" + maximalSquareUsingBruteForce1WithMemo2(matrix));

        System.out.println("maximalSquare Using Backtracking1_TLE" + maximalSquareUsingBacktracking1_TLE(matrix));
        System.out.println("maximalSquare Using TopDownMemoDp1" + maximalSquareUsingTopDownMemoDp1(matrix));
        System.out.println("maximalSquare Using BottomUpDp1" + maximalSquareUsingBottomUpDp1(matrix));
        System.out.println("maximalSquare Using BottomUpDp1OptimizedSpace" + maximalSquareUsingBottomUpDp1OptimizedSpace(matrix));

        System.out.println("maximalSquare Using BottomUpDp2" + maximalSquareUsingBottomUpDp2(matrix));
        System.out.println("maximalSquare Using BottomUpDp2OptimizedSpace" + maximalSquareUsingBottomUpDp2OptimizedSpace(matrix));
    }


    /**

        Here, we increase the size of the square by 1 cell at a time.

     * @TimeComplexity O(m × n × L^3) = O(m × n × min(m, n)^3) ==> where L = min(m, n)
     * @SpaceComplexity O(L^2) = O(min(m, n)^2)
     */
    public static int maximalSquareUsingBruteForce1(char[][] matrix) {
        int rows = matrix.length, cols = matrix[0].length, max = 0;
        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                if (matrix[r][c] == '0') continue;
                Set<Integer> next = new HashSet<>();
                next.add(r*cols + c);
                max = Math.max(max, backtrack(matrix, rows, cols, next));
            }
        }
        return max*max;
    }
    private static int backtrack(char[][] matrix, int rows, int cols, Set<Integer> prev) {

        Set<Integer> next = new HashSet<>();
        for (int i : prev) {
            int r = i / cols;
            int c = i % cols;
            if (r+1 == rows || c+1 == cols || matrix[r+1][c] == '0' || matrix[r][c+1] == '0' || matrix[r+1][c+1] == '0') return 0;
            next.add((r+1)*cols+c);
            next.add((r)*cols+c+1);
            next.add((r+1)*cols+c+1);
        }

        return 1+backtrack(matrix, rows, cols, next);
    }


    /**

        calculate from last cell to starting cell -> then we already calculated the required values

        right
        down
        diagonal

     * @TimeComplexity O(M * N)
     * @SpaceComplexity O(M * N)
     */
    public static int maximalSquareUsingBruteForce1WithMemo1(char[][] matrix) {
        int rows = matrix.length, cols = matrix[0].length, max = 0;
        Map<Integer, Integer> map = new HashMap<>();
        int stride = cols+1;
        for (int r=0, c=cols; r<= rows; r++) map.put(r*stride+c, 0);
        for (int r=rows, c=0; c<= cols; c++) map.put(r*stride+c, 0);


        for (int r=rows-1; r>=0; r--) {
            for (int c=cols-1; c>=0; c--) {
                int currMax = 0;
                if (matrix[r][c] == '1') {
                    Set<Integer> next = new HashSet<>();
                    next.add(r*cols + c);
                    currMax = backtrack(matrix, rows, cols, stride, r, c, next, map)+1;
                    max = Math.max(max, currMax);
                }
                map.put(r*stride+c, currMax);
            }
        }
        return max*max;
    }
    private static int backtrack(char[][] matrix, int rows, int cols, int stride, int r, int c, Set<Integer> prev, Map<Integer, Integer> map) {
        if (map.containsKey((r+1)*stride+c) && map.containsKey((r)*stride+c+1) && map.containsKey((r+1)*stride+c+1)) {
            int min = Integer.MAX_VALUE;
            min = Math.min(min, map.get((r+1)*stride+c));   // down
            min = Math.min(min, map.get((r)*stride+c+1));   // right
            min = Math.min(min, map.get((r+1)*stride+c+1)); // diagonal
            return min;
        }

        Set<Integer> next = new HashSet<>();
        for (int index : prev) {
            int nr = index / cols;
            int nc = index % cols;
            if (nr+1 >= rows || nc+1 >= cols || matrix[nr+1][nc] == '0' || matrix[nr][nc+1] == '0' || matrix[nr+1][nc+1] == '0') return 0;
            next.add((nr+1)*cols+nc);
            next.add((nr)*cols+nc+1);
            next.add((nr+1)*cols+nc+1);
        }

        return 1 + backtrack(matrix, rows, cols, stride, r+1, c+1, next, map);
    }


    /**

        Now this is the same as {@link #maximalSquareUsingBottomUpDp1}

        so, this gives me the exact idea of how exactly we can do the DP approaches progression (backtracking to TopDownMemoization to BottomUpTabDp) with
        down, right, diagonal cells

     * @TimeComplexity O(M * N)
     * @SpaceComplexity O(M * N)
     */
    public static int maximalSquareUsingBruteForce1WithMemo2(char[][] matrix) { // same as maximalSquareUsingBottomUpDp1
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        int rows = matrix.length, cols = matrix[0].length, max = 0;
        Map<Integer, Integer> map = new HashMap<>();

        // We use (cols + 1) as the multiplier so that column index `cols` doesn't overlap with column index 0 of the next row.
        int stride = cols + 1;

        for (int r = rows - 1; r >= 0; r--) {
            for (int c = cols - 1; c >= 0; c--) {
                int currMax = 0;
                if (matrix[r][c] == '1') {
                    currMax = getSquareSize(r, c, stride, map);
                    max = Math.max(max, currMax);
                }
                map.put(r * stride + c, currMax);
            }
        }
        return max * max;
    }
    private static int getSquareSize(int r, int c, int stride, Map<Integer, Integer> map) {
        int down = map.getOrDefault((r + 1) * stride + c, 0);       // down
        int right = map.getOrDefault(r * stride + (c + 1), 0);      // right
        int diag = map.getOrDefault((r + 1) * stride + (c + 1), 0); // diagonal

        return 1 + Math.min(down, Math.min(right, diag));
    }







    /**
     * @TimeComplexity O(M * N * 3^(min(M, N)))
     * @SpaceComplexity O(min(M, N))
     */
    public static int maximalSquareUsingBacktracking1_TLE(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;

        int rows = matrix.length, cols = matrix[0].length, max = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (matrix[r][c] == '1') {
                    max = Math.max(max, backtrack(matrix, r, c));
                }
            }
        }

        return max * max;
    }

    private static int backtrack(char[][] matrix, int r, int c) {
        if (r >= matrix.length || c >= matrix[0].length) return 0;
        else if (matrix[r][c] == '0') return 0;

        int down  = backtrack(matrix, r + 1, c);
        int right = backtrack(matrix, r, c + 1);
        int diag  = backtrack(matrix, r + 1, c + 1);

        return 1 + Math.min(down, Math.min(right, diag));
    }




    /**
     * @TimeComplexity O(M * N)
     * @SpaceComplexity O(M * N)
     */
    public static int maximalSquareUsingTopDownMemoDp1(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;

        int rows = matrix.length, cols = matrix[0].length, max = 0;
        Integer[][] memo = new Integer[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (matrix[r][c] == '1') {
                    max = Math.max(max, dfs(matrix, r, c, memo));
                }
            }
        }

        return max * max;
    }

    private static int dfs(char[][] matrix, int r, int c, Integer[][] memo) {
        if (r >= matrix.length || c >= matrix[0].length) return 0;
        else if (matrix[r][c] == '0') return 0;
        else if (memo[r][c] != null) return memo[r][c];

        int down  = dfs(matrix, r + 1, c, memo);
        int right = dfs(matrix, r, c + 1, memo);
        int diag  = dfs(matrix, r + 1, c + 1, memo);

        return memo[r][c] = 1 + Math.min(down, Math.min(right, diag));
    }




    /**
     * @TimeComplexity O(M * N)
     * @SpaceComplexity O(M * N)
     */
    public static int maximalSquareUsingBottomUpDp1(char[][] matrix) {
        int rows = matrix.length, cols = matrix[0].length, maxLen = 0;
        // for convenience, we add an extra all zero column and row outside of the actual dp table, to simpify the transition
        int[][] dp = new int[rows + 1][cols + 1];

        for (int r = rows - 1; r >= 0; r--) {
            for (int c = cols - 1; c >= 0; c--) {
                if (matrix[r][c] == '1') {
                    dp[r][c] = 1 + Math.min(dp[r + 1][c], Math.min(dp[r][c + 1], dp[r + 1][c + 1]));
                    maxLen = Math.max(maxLen, dp[r][c]);
                }
            }
        }

        return maxLen * maxLen;
    }



    /**
     * @TimeComplexity O(M * N)
     * @SpaceComplexity O(N)
     */
    public static int maximalSquareUsingBottomUpDp1OptimizedSpace(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;

        int rows = matrix.length, cols = matrix[0].length, maxLen = 0;
        int[] dp = new int[cols + 1];

        for (int r = rows - 1; r >= 0; r--) {
            int prevDiag = 0; // Stores dp[r+1][c+1] (the diagonal down-right value)

            for (int c = cols - 1; c >= 0; c--) {
                int temp = dp[c]; // Save old dp[r+1][c] before overwriting

                if (matrix[r][c] == '1') {
                    // dp[c] currently holds dp[r+1][c] (down) dp[c+1] holds dp[r][c+1] (right) prevDiag holds dp[r+1][c+1] (diagonal down-right)
                    dp[c] = 1 + Math.min(dp[c], Math.min(dp[c + 1], prevDiag));
                    maxLen = Math.max(maxLen, dp[c]);
                } else {
                    dp[c] = 0; // MUST reset to 0 so values from lower rows don't carry over
                }

                prevDiag = temp; // Set diagonal value for the next iteration to the left (c - 1)
            }
        }

        return maxLen * maxLen;
    }






    /**
     * @TimeComplexity O(M * N)
     * @SpaceComplexity O(M * N)
     */
    public static int maximalSquareUsingBottomUpDp2(char[][] matrix) {
        int rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;
        // for convenience, we add an extra all zero column and row outside of the actual dp table, to simpify the transition
        int[][] dp = new int[rows + 1][cols + 1];
        int maxLen = 0;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    dp[i][j] = Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]) + 1;
                    maxLen = Math.max(maxLen, dp[i][j]);
                }
            }
        }
        return maxLen * maxLen;
    }



    /**
     * @TimeComplexity O(M * N)
     * @SpaceComplexity O(1)
     */
    public static int maximalSquareUsingBottomUpDp2OptimizedSpace(char[][] matrix) {
        int rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;
        int[] dp = new int[cols + 1];
        int maxLen = 0, prev = 0;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                int temp = dp[j];
                if (matrix[i - 1][j - 1] == '1') {
                    dp[j] = Math.min(Math.min(dp[j - 1], prev), dp[j]) + 1;
                    maxLen = Math.max(maxLen, dp[j]);
                } else {
                    dp[j] = 0;
                }
                prev = temp;
            }
        }
        return maxLen * maxLen;
    }


    /**
     * @TimeComplexity O((M * N)^2) [or O(M * N * min(M, N)^2)]
     * @SpaceComplexity O(1)
     */
    public static int maximalSquareUsingBruteForce2(char[][] matrix) {
        int rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;
        int maxLen = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    int sqLen = 1;
                    boolean flag = true;
                    while (sqLen + i < rows && sqLen + j < cols && flag) {
                        for (int k = j; k <= sqLen + j; k++) {
                            if (matrix[i + sqLen][k] == '0') {
                                flag = false;
                                break;
                            }
                        }
                        for (int k = i; k <= sqLen + i; k++) {
                            if (matrix[k][j + sqLen] == '0') {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) sqLen++;
                    }
                    if (maxLen < sqLen) {
                        maxLen = sqLen;
                    }
                }
            }
        }
        return maxLen * maxLen;
    }


}
