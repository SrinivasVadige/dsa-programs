package Algorithms.BackTracking;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 20 Feb 2025
 */
public class NQueens {
    public static void main(String[] args) {
        int n = 4;
        System.out.println("solveNQueens(n) => " + solveNQueens(n));
        System.out.println("solveNQueens2(n) => " + solveNQueens2(n));
    }

    /**
     * <pre>
        THOUGHTS:
        ---------
        1) N-Queens == n*n array
        2)


                                                                       []
                                                _______________________|_______________________
                                                |               |                |              |
                                        [Q,"","",""]         ["",Q,"",""]   ["","",Q,""]    ["","","",Q]
                                    __________|__________
                                    |                    |
                            [Q ,"","",""]         [Q ,"","",""]
                            ["","",Q ,""]         ["","","",Q ]
                        _________|_________
                        |                  |
                    [Q ,"","",""]        [Q ,"","",""]
                    ["","",Q ,""]        ["","","",Q ]
                    [Q ,"","",""]        ["",Q ,"",""]
               _________|_________
              |                   |
    [Q ,"","",""]          [Q ,"","",""]
    ["","",Q ,""]          ["","",Q ,""]
    [Q ,"","",""]          [Q ,"","",""]
    ["","",Q ,""]          ["","","",Q ]

        3) But the above approach is wrong. Because the Queen has to move one direction till the board ends
        4) Lets say, if the Queen moves down then it'll move down until it hits the board.
        5) Here if Q1 wants to move down then it'll hit Q3.
            [Q1,"","",""]
            ["","",Q2,""]
            [Q3,"","",""]
            ["","",Q4,""]
        6) Here in this approach, all Qs can move in all 4 directions till the board ends (one direction at a time)
            ["",Q1,"",""]          ["","",Q1,""]
            ["","","",Q2]          [Q2,"","",""]
            [Q3,"","",""]          ["","","",Q3]
            ["","",Q4,""]          ["",Q4,"",""]
        7) So, the correct tree representation should be
                                                          []
                    _______________________________________|______________________________________
                    |                             |                  |                           |
            [Q,"","",""]                    ["",Q,"",""]        ["","",Q,""]               ["","","",Q]
        __________|__________                     |                  |                 __________|__________
        |                    |                    |                  |                 |                    |
  [Q ,"","",""]         [Q ,"","",""]       ["",Q ,"",""]      ["","",Q ,""]     ["","","",Q ]         ["","","",Q ]
  ["","",Q ,""]         ["","","",Q ]       ["","","",Q ]      [Q ,"","",""]     [Q ,"","",""]         ["",Q ,"",""]
        |                    |                    |                  |                 |                    |
  [Q ,"","",""]         [Q ,"","",""]       ["",Q ,"",""]      ["","",Q ,""]     ["","","",Q ]         ["","","",Q ]
  ["","",Q ,""]         ["","","",Q ]       ["","","",Q ]      [Q ,"","",""]     [Q ,"","",""]         ["",Q ,"",""]
  ["","","",""]         ["",Q ,"",""]       [Q ,"","",""]      ["","","",Q ]     ["","",Q ,""]         ["","","",""]
       ❌                    |                   |                  |                 |                    ❌
                        [Q ,"","",""]       ["",Q ,"",""]      ["","",Q ,""]     ["","","",Q ]
                        ["","","",Q ]       ["","","",Q ]      [Q ,"","",""]     [Q ,"","",""]
                        ["",Q ,"",""]       [Q ,"","",""]      ["","","",Q ]     ["","",Q ,""]
                        ["","","",""]       ["","",Q ,""]      ["",Q ,"",""]     ["","","",""]
                             ❌                  ✅                 ✅               ❌

    * </pre>
   */
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> lst = new ArrayList<>();
        int[][] board = new int[n][n];
        backtrack(board, lst, 0);
        return lst;
    }

    public static void backtrack(int[][] board, List<List<String>> lst, int row) {
        if (row == board.length) lst.add(printBoard(board)); // base case
        for (int col = 0; col < board.length; col++) {
            if (isValid(board, row, col)) {
                board[row][col] = 1;
                backtrack(board, lst, row + 1);
                board[row][col] = 0; // reset the current queen for next cols in this for loop
            }
        }
    }

    public static boolean isValid(int[][] board, int row, int col) {
        // top and no need to check bottom as we move from top to bottom rows
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) return false;
        }
        // left-top diagonal
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) return false;
        }
        // right-top diagonal
        for (int i = row - 1, j = col + 1; i >= 0 && j < board.length; i--, j++) {
            if (board[i][j] == 1) return false;
        }
        return true;
    }

    public static List<String> printBoard(int[][] board) {
        List<String> lst = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < board.length; j++) {
                sb.append(board[i][j] == 1 ? "Q" : ".");
            }
            lst.add(sb.toString());
        }
        return lst;
    }




    public static List<List<String>> solveNQueens2(int n) {
        List<List<String>> lst = new ArrayList<>();
        char[][] board = new char[n][n];

        // Fill the board with dots
        for (int i = 0; i < n; i++) Arrays.fill(board[i], '.');

        backtrack(n, lst, board, 0); // Start solving from row 0
        return lst;
    }

    private static void backtrack(int n, List<List<String>> lst, char[][] board, int row) {
        // Base case: if we've placed queens in all rows, we found a valid solution
        if (row == n) {
            List<String> subLst = new ArrayList<>();
            for (char[] rowArray : board) subLst.add(new String(rowArray));
            lst.add(subLst);
            return;
        }

        // Try placing queen in each column of current row
        for (int col = 0; col < n; col++) {
            // If current position is safe
            if (isSafePlace(n, board, row, col)) {
                // Place queen
                board[row][col] = 'Q';
                // Recursively solve for next row
                backtrack(n, lst, board, row + 1);
                // Backtrack: remove queen for trying next position
                board[row][col] = '.';
            }
        }
    }

    private static boolean isSafePlace(int n, char[][] board, int row, int col) {
        // Check if there's any queen in the same column above current position
        for (int i = 0; i < n; i++) {
            if (board[i][col] == 'Q') return false;
        }

        // Check upper-left diagonal for any queen
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') return false;
        }

        // Check upper-right diagonal for any queen
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q') return false;
        }

        return true;
    }









    public static int totalNQueens(int n) {
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
}
