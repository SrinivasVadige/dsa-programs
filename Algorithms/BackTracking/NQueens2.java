package Algorithms.BackTracking;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 10 March 2026
 * @link 52. N-Queens II <a href="https://leetcode.com/problems/n-queens-ii/">LeetCode link</a>
 * @topics Backtracking
 * @companies Microsoft(3), Meta(2), Bloomberg(2), Amazon(12), Google(9), Liftoff(5), Snowflake(3), Walmart Labs(2), Deutsche Bank(2), Zenefits(2)
 * @see Algorithms.BackTracking.NQueens
 */
public class NQueens2 {
    public static void main(String[] args) {
        int n = 6;
        System.out.println("totalNQueens using Backtracking 1 => " + totalNQueensUsingBacktracking1(n));
        System.out.println("totalNQueens using Backtracking 2 => " + totalNQueensUsingBacktracking2(n));
        System.out.println("totalNQueens using Backtracking 3 🔥 => " + totalNQueensUsingBacktracking3(n));
        System.out.println("totalNQueens using Backtracking 4 => " + totalNQueensUsingBacktracking4(n));
    }



    /**
     * @TimeComplexity O(n!)
     * Initially we thought it is O(n^3) as O(n^2) to traverse each cell and O(n) for isValid()
     * But isValid() will be false sometimes & we do not backtrack() for next cell
     * So, it's O(n!)
     * Cause in 1st row we have n choices, in 2nd row we have n-2 choices, in 3rd row we have n-4 choices and so on... just like n * n-1 * n-2 ...
     * @SpaceComplexity O(n^2)
     */
    static int count = 0;
    static boolean[][] chessBoard;
    public static int totalNQueensUsingBacktracking1(int n) {
        chessBoard = new boolean[n][n];
        backtrack(chessBoard, n, 0, 0); // traverse row by row
        return count;
    }
    private static void backtrack(boolean[][] chessBoard, int n, int r, int queens) {
        if (queens == n) {
            count++;
            return;
        }
        else if (r == n) return;

        for (int c = 0; c<n; c++) { // in each row, traverse col by col
            if (isHorizontallyAndVerticallyValid(n, r, c) && isDiagonallyValid(n, r, c)) {
                chessBoard[r][c] = true;
                backtrack(chessBoard, n, r+1, queens+1);
                chessBoard[r][c] = false;
            }
        }
    }
    private static boolean isHorizontallyAndVerticallyValid(int n, int ri, int ci) {
        for (int i=0; i<ri; i++) {
            if (chessBoard[ri][i] || chessBoard[i][ci]) return false;
        }

        // for (int r=ri, c=0; c<n; c++) { // Horizontal
        //     if (chessBoard[r][c]) return false;
        // }
        // for (int r=0, c=ci; r<n; r++) { // Vertical
        //     if (chessBoard[r][c]) return false;
        // }
        return true;
    }
    private static boolean isDiagonallyValid(int n, int ri, int ci) {
        int[][] dirs = {{-1,-1}, {-1, 1}}; // left-top and right-top
        for (int[] dir: dirs) {
            for (int r = ri, c=ci; r>=0 && c>=0 && r<=ri && c<n; r+=dir[0], c+=dir[1]) {
                if (chessBoard[r][c]) return false;
            }
        }

        // for (int r = ri, c=ci; r>=0 && c>=0; r--, c--) if (chessBoard[r][c]) return false; // left-top
        // for (int r = ri, c=ci; r<n && c<n; r++, c++) if (chessBoard[r][c]) return false; // right-bottom
        // for (int r = ri, c=ci; r>=0 && c<n; r--, c++) if (chessBoard[r][c]) return false; // right-top
        // for (int r = ri, c=ci; r<n && c>=0; r++, c--) if (chessBoard[r][c]) return false; // left-bottom
        return true;
    }





    public static int totalNQueensUsingBacktracking2(int n) {
        int[][] board = new int[n][n];
        return backtrack(board, 0);
    }
    public static int backtrack(int[][] board, int row) {
        if (row == board.length) return 1;
        int count = 0;
        for (int col = 0; col < board.length; col++) {
            if (isValid(board, row, col)) {
                board[row][col] = 1;
                count += backtrack(board, row + 1);
                board[row][col] = 0;
            }
        }
        return count;
    }
    public static boolean isValid(int[][] board, int rowI, int colI) {

        for (int r = 0; r < rowI; r++) { // top and no need to check bottom as we move from top to bottom rows
            if (board[r][colI] == 1) return false;
        }

        for (int r = rowI-1, c = colI-1; r >= 0 && c >= 0; r--, c--) { // left-top diagonal
            if (board[r][c] == 1) return false;
        }

        for (int r = rowI-1, c = colI+1; r >= 0 && c < board.length; r--, c++) { // right-top diagonal
            if (board[r][c] == 1) return false;
        }
        return true;
    }








    /**
     * 🔥🔥🔥
                     0   1   2   3   4  ---> cols
                   ---------------------
               0 |  [ ] [ ] [ ] [ ] [ ]
               1 |  [ ] [ ] [ ] [x] [ ]
               2 |  [ ] [x] [ ] [ ] [ ]
               3 |  [ ] [ ] [ ] [ ] [ ]
               4 |  [ ] [ ] [ ] [ ] [ ]

               |
               |
               v
              rows

            if we look at this board,
            DIAGONAL SET (top-left) diagonal is always unique as r-c is always unique
            ANTI-DIAGONAL SET (top-right) diagonal is always unique as r+c is always unique


            That's why we can use diagonals and antiDiagonals sets instead of checking all the top-left and top-right diagonals in n*n matrix
            And if add a queen to a column, then we don't add another queen to the same column so we can use columns set to keep track of columns
     */
    public static int totalNQueensUsingBacktracking3(int n) {
        return backtrack(n, 0, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    private static int backtrack(int n, int row, Set<Integer> diagonals, Set<Integer> antiDiagonals, Set<Integer> cols ) {
        if (row == n) {
            return 1;
        }

        int solutions = 0;
        for (int c = 0; c < n; c++) {
            int currDiagonal = row - c; // r - c is always unique for each diagonal ---> top-left
            int currAntiDiagonal = row + c; // r + c is always unique for each anti-diagonal ---> top-right

            if (cols.contains(c) || diagonals.contains(currDiagonal) || antiDiagonals.contains(currAntiDiagonal)) {
                continue;
            }

            cols.add(c);
            diagonals.add(currDiagonal);
            antiDiagonals.add(currAntiDiagonal);

            solutions += backtrack(n, row + 1, diagonals, antiDiagonals, cols);

            cols.remove(c);
            diagonals.remove(currDiagonal);
            antiDiagonals.remove(currAntiDiagonal);
        }

        return solutions;
    }













    static Set<Integer> rSeen = new HashSet<>();
    static Set<Integer> cSeen = new HashSet<>();
    public static int totalNQueensUsingBacktracking4(int n) {
        chessBoard = new boolean[n][n];
        backtrack(n, 0, 0);
        return count;
    }
    private static void backtrack(int n, int i, int queens) { // i = r * cols + c
        if (queens == n) {
            count++;
            return;
        }
        else if (i == n*n) return;

        int r = i/n, c = i%n;
        if (!rSeen.contains(r) && !cSeen.contains(c) && isDiagonallyValid(n, r, c)) {
            rSeen.add(r);
            cSeen.add(c);
            chessBoard[r][c] = true;
            backtrack(n, i+1, queens+1);
            rSeen.remove(r);
            cSeen.remove(c);
            chessBoard[r][c] = false;
        }
        backtrack(n, i+1, queens);
    }
}
