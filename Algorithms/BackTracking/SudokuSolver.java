package Algorithms.BackTracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 21 July 2025
 * @link 37. Sudoku Solver <a href="https://leetcode.com/problems/sudoku-solver/">LeetCode link</a>
 * @topics Array, Backtracking, Matrix
 * @companies Amazon, Google, Confluent, Microsoft, Bloomberg, DoorDash, Meta, Intuit, Adobe, Citadel, Riot Games, Apple, Oracle, Goldman Sachs, Uber, Yahoo
 * see {@link Algorithms.Matrix.ValidSudoku} for better understanding of Sudoku
 */
public class SudokuSolver {

    static final int N = 9;
    private static char[][] getChars() {
        return new char[][]{
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'},
        };
    }
    private static void printBoard(char[][] board) {
        for (char[] row : board) {
            for (char c : row) System.out.print(c + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("\nsolveSudoku using BackTracking by MissingNums => ");
        char[][] board = getChars();
        solveSudokuUsingBackTrackingByMissingNums(board);
        printBoard(board);


        System.out.println("solveSudoku using BackTracking by all nums i.e all 1-9 => ");
        board = getChars();
        solveSudokuUsingBacktrackingByAllNums(board);
        printBoard(board);


        System.out.println("\nsolveSudoku using BackTracking 2 => ");
        board = getChars();
        solveSudoku2(board);
        printBoard(board);
    }





    /**
     * @TimeComplexity O(N^E), where E = N^2
     * @SpaceComplexity O(N^2)
     */
    public static void solveSudokuUsingBackTrackingByMissingNums(char[][] board) {
        @SuppressWarnings("unchecked")
        List<Character>[][] missingNums = new ArrayList[N][N]; // 3D Array or HashMap<List<Integer>> cause HashMap<int[]> won't work properly
        List<int[]> missingLocations = new ArrayList<>();

        // Step 1: Prepare missingLocations & missingNums
        for(int row=0; row<N; row++) {
            for(int col=0; col<N; col++) {
                if (Character.isDigit(board[row][col])) continue;
                missingLocations.add(new int[]{row, col});
                missingNums[row][col] = getMissingNums(board, row, col);
            }
        }

        // Step 2: Backtrack
        backtrack(board, missingLocations, 0, missingNums);
    }

    private static List<Character> getMissingNums(char[][] board, int row, int col) {
        Set<Character> seen = new HashSet<>();
        for (int i = 0; i < N; i++) {
            seen.add(board[row][i]); // horizontally
            seen.add(board[i][col]); // vertically
        }

        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;
        for (int r = 0; r < 3; r++) {
            for (int c2 = 0; c2 < 3; c2++) {
                seen.add(board[boxRowStart + r][boxColStart + c2]);
            }
        }

        char[] nums = {'1','2','3','4','5','6','7','8','9'};
        List<Character> unSeen = new ArrayList<>();
        for(char ch : nums) {
            if(!seen.contains(ch)) {
                unSeen.add(ch);
            }
        }

        return unSeen;
    }

    /*
        missingLocations = [
            location1 : ['1', '2', '3'],
            location2 : ['4', '5', '6'],
            location3 : ['7', '8', '9'],
            ........
        ]
        i.e., ---> each missingLocations[i] have a list of missingNums


        now for each location do the backtracking ---

        missingLocations i=0 && 0th missingNum
        for location1 ['1']
        location2 : ['4'] ---> location3 : ['7;], location3 : ['8'], location3 : ['9']
        location2 : ['5'] ---> location3 : ['7;], location3 : ['8'], location3 : ['9']
        location2 : ['6'] ---> location3 : ['7;], location3 : ['8'], location3 : ['9']

        missingLocations i=0 && 1st missingNum
        for location1 ['2']
        location2 : ['4'] ---> location3 : ['7;], location3 : ['8'], location3 : ['9']
        location2 : ['5'] ---> location3 : ['7;], location3 : ['8'], location3 : ['9']
        location2 : ['6'] ---> location3 : ['7;], location3 : ['8'], location3 : ['9']

        missingLocations i=0 && 2nd missingNum
        for location1 ['3'] - index2
        location2 : ['4'] ---> location3 : ['7;], location3 : ['8'], location3 : ['9']
        location2 : ['5'] ---> location3 : ['7;], location3 : ['8'], location3 : ['9']
        location2 : ['6'] ---> location3 : ['7;], location3 : ['8'], location3 : ['9']

        so, we choose 0th missingLocations each missingNum and do the backtracking for the rest of the missingLocations with their missingNums

     */
    private static boolean backtrack(char[][] board, List<int[]> missingLocations, int idx, List<Character>[][] missingNums) {
        if (idx == missingLocations.size()) {
            return true; // all positions filled
        }

        int row = missingLocations.get(idx)[0];
        int col = missingLocations.get(idx)[1];

        for (char candidate : missingNums[row][col]) {
            if (isSafe(board, row, col, candidate)) { // is it ok to use curr MissingLocation's curr MissingNum ??
                board[row][col] = candidate;
                if (backtrack(board, missingLocations, idx + 1, missingNums)) {
                    return true;
                }
                board[row][col] = '.'; // undo backtrack
            }
        }
        return false;
    }

    private static boolean isSafe(char[][] board, int row, int col, char c) {
        // HORIZONTAL & VERTICAL
        for (int i = 0; i < N; i++) {
            if (board[row][i] == c || board[i][col] == c) return false;
        }
        // 9 "3x3" boxes
        int boxRowStart = (row/3)*3;
        int boxColStart = (col/3)*3;
        for (int ri = 0; ri < 3; ri++) {
            for (int ci = 0; ci < 3; ci++) {
                if (board[boxRowStart + ri][boxColStart + ci] == c) return false;
            }
        }
        return true;
        /*
        // or --- box loop alternate
        int[][][] boxStarts = {
                    {{0,0}, {0,3}, {0,6}},
                    {{3,0}, {3,3}, {3,6}},
                    {{6,0}, {6,3}, {6,6}}
        };
        int r = boxStarts[row/3][col/3][0];
        int c = boxStarts[row/3][col/3][1];

        int[][] boxLoop = {{r,c}, {r,c+1}, {r,c+2}, {r+1,c}, {r+1,c+1}, {r+1, c+2}, {r+2,c}, {r+2,c+1}, {r+2, c+2}};
        for (int[] box : boxLoop) {
            if (board[box[0]][box[1]] == c) return false;
        }
        */
    }
    /*
    // or ---- #isSafe alternate
    private static boolean isSafe(char[][] board, int row, int col, char c) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == c) return false;
            if (board[i][col] == c) return false;
            if (board[(row/3)*3 + i/3][(col/3)*3 + i%3] == c) return false;
        }
        return true;
    }
    */











    /**
     * @TimeComplexity O(N^E), where E = N^2
     * @SpaceComplexity O(1)
     * as this approach do backtracking isValid for all numbers like 1 to 9, it's slower than above {@link #solveSudokuUsingBackTrackingByMissingNums} approach
     */
    public static void solveSudokuUsingBacktrackingByAllNums(char[][] board) {
        backtrack(board, 0, 0);
    }

    public static boolean backtrack(char[][] board, int row, int col) {
        if (row == N) return true;
        if (col == N) return backtrack(board, row + 1, 0);
        if (board[row][col] != '.') return backtrack(board, row, col + 1);

        for (char c = '1'; c <= '9'; c++) {
            if (isValid(board, row, col, c)) {
                board[row][col] = c;
                if (backtrack(board, row, col + 1)) {
                    return true;
                }
                board[row][col] = '.'; // undo changes
            }
        }
        return false;
    }

    /**
     * same as above {@link #isSafe} method 🔥
     */
    public static boolean isValid(char[][] board, int row, int col, char c) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == c) return false; // Check column
            if (board[i][col] == c) return false; // Check row
            if (board[3*(row/3) + i/3][3*(col/3) + i%3] == c) return false; // Check 3x3 box
        }
        return true;
    }







    public static void solveSudokuUsingBacktrackingByAllNums2(char[][] board) {
        solve(board); // backtrack
    }

    private static boolean solve(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {  // Empty cell
                    continue;
                }
                for (char c = '1'; c <= '9'; c++) {
                    if (isValid(board, i, j, c)) {
                        board[i][j] = c;
                        if (solve(board)) return true;  // Backtrack
                        else board[i][j] = '.';  // Undo or reset backtrack
                    }
                }
                return false; // No valid digit
            }
        }
        return true; // Solved
    }









    static int n = 3; // box size
    static int[][] rows = new int[N][N+1]; // N+1 for 1-indexing i.e ignore 0
    static int[][] columns = new int[N][N+1];
    static int[][] boxes = new int[N][N+1];
    static char[][] board = new char[N][N];
    static boolean sudokuSolved = false;
    static {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = '.';
            }
        }
    }
    public static void solveSudoku2(char[][] board) {
        SudokuSolver.board = board;

        // Step 1: init rows, columns, and boxes
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                char num = board[i][j];
                if (num != '.') {
                    int d = Character.getNumericValue(num); // or num - '0'
                    placeNumber(d, i, j);
                }
            }
        }

        // Step 2: start backtracking
        backtrack(0, 0);
    }

    public static void placeNumber(int d, int row, int col) { // Place a number d in (row, col) cell
        rows[row][d]++;
        columns[col][d]++;
        board[row][col] = (char) (d + '0'); // not needed for the "Step 1: init rows, columns, and boxes" invocation

        int idx = (row/n)*n + col/n; // unique index value with range from 0 to 8
        boxes[idx][d]++;
    }

    public static void backtrack(int row, int col) {
        if (board[row][col] == '.') {
            for (int d = 1; d < 10; d++) {
                if (couldPlace(d, row, col)) { // isValid ?
                    placeNumber(d, row, col);
                    placeNextNumbers(row, col);

                    if (!sudokuSolved) removeNumber(d, row, col); // If sudoku is solved, there is no need to backtrack, since the single unique solution is promised
                }
            }
        } else placeNextNumbers(row, col);
    }

    public static boolean couldPlace(int d, int row, int col) { // Check if one could place a number d in (row, col) cell
        int idx = (row/n)*n + col/n;
        return rows[row][d] + columns[col][d] + boxes[idx][d] == 0;
    }

    public static void removeNumber(int d, int row, int col) { // Reset backtrack() -- Remove a number that didn't lead to a solution
        int idx = (row/n)*n + col/n;
        rows[row][d]--;
        columns[col][d]--;
        boxes[idx][d]--;
        board[row][col] = '.';
    }

    public static void placeNextNumbers(int row, int col) { // Call backtrack function in recursion to continue to place numbers till the moment we have a solution
        if ((col == N - 1) && (row == N - 1)) {
            sudokuSolved = true;
        }
        else {
            if (col == N - 1) backtrack(row + 1, 0); //If we're at the end of the row, go to the next row
            else backtrack(row, col + 1); // go to the next column
        }
    }

}
