package Algorithms.Matrix;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 21 July 2025
 * @link 36. Valid Sudoku <a href="https://leetcode.com/problems/valid-sudoku/">LeetCode link</a>
 * @topics Array, Hash Table, Matrix
 * @companies Google, Amazon, Apple, Zoho, Confluent, Karat, Bloomberg, Meta, Uber, Samsara, Microsoft, PayPal, Adobe, Walmart Labs, Riot Games, Geico, LinkedIn, Media.net, Snap, Yahoo, TikTok, Autodesk


    Sudoku means
    1) Each row must contain the digits 1-9 without repetition.
    2) Each column must contain the digits 1-9 without repetition.
    3) Each of the 9 "3x3 sub-boxes" of the grid must contain the digits 1-9 without repetition.


// To understand the 9 "3x3 boxes" in Sudoku board

    0,0   0,1   0,2  |   0,3   0,4   0,5   |  0,6   0,7   0,8
                     |                     |
    1,0   1,1   1,2  |   1,3   1,4   1,5   |  1,6   1,7   1,8
                     |                     |
    2,0   2,1   2,2  |   2,3   2,4   2,5   |  2,6   2,7   2,8


    3,0   3,1   3,2  |   3,3   3,4   3,5   |  3,6   3,7   3,8
                     |                     |
    4,0   4,1   4,2  |   4,3   4,4   4,5   |  4,6   4,7   4,8
                     |                     |
    5,0   5,1   5,2  |   5,3   5,4   5,5   |  5,6   5,7   5,8


    6,0   6,1   6,2  |   6,3   6,4   6,5   |  6,6   6,7   6,8
                     |                     |
    7,0   7,1   7,2  |   7,3   7,4   7,5   |  7,6   7,7   7,8
                     |                     |
    8,0   8,1   8,2  |   8,3   8,4   8,5   |  8,6   8,7   8,8


// boxStart indexes

    boxStartIndexes = [
        (0,0) (0,3) (0,6)   --- boxRow1
        (3,0) (3,3) (3,6)   --- boxRow2
        (6,0) (6,3) (6,6)   --- boxRow3
    ]    |      |     |
        bc1    bc2   bc3

    boxRow1 = 0 1 2, boxRow2 = 3 4 5, boxRow3 = 6 7 8   --- indexes from board[][] to boxIndexes
    boxCol1 = 0 1 2, boxCol2 = 3 4 5, boxCol3 = 6 7 8

    (row)/3 -> boxRow ---> bowRow1 or boxRow2 or boxRow3 --- (0 to 2)/3 = 0, (3 to 5)/3 = 1, (6 to 8)/3 = 2
    (col)/3 -> boxCol ---> boxCol1 or boxCol2 or boxCol3

    (row/3)*3 ---> row index in board[][]
    (col/3)*3 ---> col index in board[][]


 */
public class ValidSudoku {
    public static void main(String[] args) {
        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        System.out.println("isValidSudoku using HashSet => " + isValidSudokuUsingHashSet(board));
        System.out.println("isValidSudoku using Fixed Array Length => " + isValidSudokuUsingFixedArrayLength(board));
        System.out.println("isValidSudoku using Bit Masking => " + isValidSudokuUsingBitMasking(board));
        System.out.println("isValidSudoku using Searching => " + isValidSudokuUsingSearching(board));
        System.out.println("isValidSudoku using Unique Set => " + isValidSudokuUsingUniqueSet(board));
    }


    /**
     * @TimeComplexity O(N^2), where N = 9
     * @SpaceComplexity O(N^2)
     */
    public static boolean isValidSudokuUsingHashSet(char[][] board) {
        Set<String> seen = new HashSet<>();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char val = board[r][c];
                if (val == '.') continue;
                // or StringBuilder.toString()
                if (!seen.add(val + " in row " + r) ||                      // --> horizontally / row
                    !seen.add(val + " in col " + c) ||                      // --> vertically / col
                    !seen.add(val + " in box " + r/3 + "," + c/3)) {        // --> 3x3 box
                    return false;
                }
            }
        }
        return true;
    }







    /**
     * @TimeComplexity O(N^2), where N = 9
     * @SpaceComplexity O(N^2)
     */
    @SuppressWarnings("unchecked")
    public static boolean isValidSudokuUsingHashSet2(char[][] board) {
        int N = 9;
        Set<Character>[] rows = new HashSet[N]; // or List<Set<Character>>
        Set<Character>[] cols = new HashSet[N];
        Set<Character>[] boxes = new HashSet[N];
        for (int r = 0; r < N; r++) {
            rows[r] = new HashSet<>();
            cols[r] = new HashSet<>();
            boxes[r] = new HashSet<>();
        }

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                char val = board[r][c];
                if (val == '.') continue;
                if (!rows[r].add(val) || !cols[c].add(val) || !boxes[(r/3)*3 + c/3].add(val)) return false;
                /*
                    using "(rowI*cols + colI)", we get the unique value in the range of (0 to rows*cols-1)
                    and get back the rowI and colI anywhere
                    int r = value / cols;
                    int c = value % cols;

                    in 9x9 grid, if we use (r/3)*3 + c/3 ---> we get (0-8) range instead of (0-80) which is used for 9 --> 3x3 sub-grids or boxes
                */
            }
        }
        return true;
    }








    /**
     * @TimeComplexity O(N^2), where N = 9
     * @SpaceComplexity O(N^2)
     * Same as above {@link #isValidSudokuUsingHashSet2}
     */
    public static boolean isValidSudokuUsingFixedArrayLength(char[][] board) {
        int N = 9;
        boolean[][] rows = new boolean[N][N];
        boolean[][] cols = new boolean[N][N];
        boolean[][] boxes = new boolean[N][N];

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (board[r][c] == '.') continue;
                int pos = board[r][c] - '1';

                if (rows[r][pos]) return false;
                rows[r][pos] = true;

                if (cols[c][pos]) return false;
                cols[c][pos] = true;

                int idx = (r/3)*3 + c/3;
                if (boxes[idx][pos]) return false;
                boxes[idx][pos] = true;
            }
        }
        return true;
    }




    /**
     * @TimeComplexity O(N^2), where N = 9
     * @SpaceComplexity O(N)
     * a & b -- checks the similarity of bits. So, "0 & b" is always 0 --> i.e., not seen
     * a | b -- means marking or recording b in a. So, "0 | b" will mark b's binary code in 0000000
     * Similarly we can mark all the numbers in a single cell -- all numbers in each row can be marked in one bit-value
     * But why 1 << val i.e., why 2^val ??
     * cause ---> each 2^n (or 1 << n) sets a unique bit in binary, which is perfect for representing distinct states or items.
     */
    public static boolean isValidSudokuUsingBitMasking(char[][] board) {
        int N = 9;
        int[] rows = new int[N]; // using bit masking 1D Array is enough instead of 2D array
        int[] cols = new int[N];
        int[] boxes = new int[N];

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (board[r][c] == '.') continue;

                int val = board[r][c] - '0';
                int pos = 1 << (val - 1); // or 1 << val

                if ((rows[r] & pos) > 0) return false; // a & b -- checks the similarity of bits. So, "0 & b" is always 0 --> i.e., not seen
                rows[r] |= pos; // a | b -- means marking or recording b in a. So, "0 | b" will mark b's binary code in 0000000

                if ((cols[c] & pos) > 0) return false;
                cols[c] |= pos;

                int idx = (r / 3) * 3 + c / 3;
                if ((boxes[idx] & pos) > 0) return false;
                boxes[idx] |= pos;
            }
        }
        return true;
    }









    public static boolean isValidSudokuUsingBitMasking2(char[][] board) {
        int R = board.length, C = board[0].length;

        int[] cols = new int[9];
        int[] boxes = new int[9];

        for (int r = 0; r < R; r++) {
            int rowFlag = 0;

            for (int c = 0; c < C; c++) {
                char letter = board[r][c];
                if (letter == '.') continue;
                int v = letter - '0';

                if (((1 << v) & rowFlag) > 0) return false;
                rowFlag |= 1 << v;

                if (((1 << v) & cols[c]) > 0) return false;
                cols[c] |= (1 << v);

                int boxR = r / 3, boxC = c / 3;
                int boxI = boxR * 3 + boxC;
                int boxFlag = boxes[boxI];
                if ((boxFlag & (1 << v)) > 0) return false;
                boxes[boxI] |= 1 << v;
            }
        }

        return true;
    }







    /**
     * @TimeComplexity O(N^3), where N = 9 ---> extra for loop in {@link #isValidCellBySearchingCurrChar}
     * @SpaceComplexity O(1)
     */
    public static boolean isValidSudokuUsingSearching(char[][] board) {
        for(int row=0; row<9; row++) {
            for(int col=0; col<9; col++) {
                if (board[row][col]=='.') continue;
                if (!isValidCellBySearchingCurrChar(board, row, col)) return false;
            }
        }
        return true;
    }

    private static boolean isValidCellBySearchingCurrChar(char[][] board, int row, int col) {
        char val = board[row][col];

        // Check horizontally / row
        for (int c = 0; c < 9; c++) {
            if (c == col) continue;
            if (board[row][c] == val) return false;
        }

        // Check vertically / col
        for (int r = 0; r < 9; r++) {
            if (r == row) continue;
            if (board[r][col] == val) return false;
        }

        // Check 3x3 box
        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                int cr = boxRowStart + r;
                int cc = boxColStart + c;
                if (cr == row && cc == col) continue;
                if (board[cr][cc] == val) return false;
            }
        }

        return true;
    }








    /**
     * @TimeComplexity O(N^3), where N = 9 ---> extra for loop in {@link #isValidCellByUniqueSet}
     * @SpaceComplexity O(N)
     */
    public static boolean isValidSudokuUsingUniqueSet(char[][] board) {
        for(int row=0; row<9; row++) {
            for(int col=0; col<9; col++) {
                if (board[row][col]=='.') continue;
                if (!isValidCellByUniqueSet(board, row, col)) return false;
            }
        }
        return true;
    }

    private static boolean isValidCellByUniqueSet(char[][] board, int row, int col) {
        // Check horizontally / row     ---     rowSet
        Set<Character> seen = new HashSet<>(); // or boolean[] seen = new boolean[9]; and seen[val-'1'] = true;
        for(int c=0; c<9; c++) {
            if(board[row][c] == '.') continue;
            if(!seen.add(board[row][c])) {
                return false;
            }
        }

        // Check vertically / col       ---     colSet
        seen = new HashSet<>();
        for(int r=0; r<9; r++) {
            if(board[r][col] == '.') continue;
            if(!seen.add(board[r][col])) {
                return false;
            }
        }

        // Check 3x3 box                ---     boxSet
        seen = new HashSet<>();
        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if(board[boxRowStart + r][boxColStart + c] == '.') continue;
                if(!seen.add(board[boxRowStart + r][boxColStart + c])) {
                    return false;
                }
            }
        }
        return true;
    }
}
