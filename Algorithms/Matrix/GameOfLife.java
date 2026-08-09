package Algorithms.Matrix;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 03 September 2025
 * @link 289. Game of Life <a href="https://leetcode.com/problems/game-of-life/">LeetCode Link</a>
 * @topics Array, Matrix, Simulation

        1 cell dies if lives < 2
        1 cell lives if lives 2 or 3
        1 cell dies if lives > 3
        0 cell live if lives == 3

        calculate each cell's lives and change accordingly

        anything outside the board is treated as 0 (dead)
 */
public class GameOfLife {
    public static void main(String[] args) {
        int[][] board = {{0, 1, 0}, {0, 0, 1}, {1, 1, 1}, {0, 0, 0}};
        gameOfLifeUsingExtraMemory(board);

        board = new int[][] {{0, 1, 0}, {0, 0, 1}, {1, 1, 1}, {0, 0, 0}};
        gameOfLife(board);
    }

    /**
     * @TimeComplexity O(m*n)
     * @SpaceComplexity O(m*n)
     */
    public static void gameOfLifeUsingExtraMemory(int[][] board) {
        int rows = board.length, cols = board[0].length;
        int[][] lives = new int[rows][cols];

        for(int r=0; r<rows; r++) {
            for(int c=0; c<cols; c++) {
                lives[r][c] = getLiveNeighbours(board, r, c, rows, cols);
            }
        }

        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                if(board[r][c] == 0) {
                    if(lives[r][c] == 3) {
                        board[r][c] = 1;
                    }
                } else {
                    if(lives[r][c] > 3 || lives[r][c] < 2) {
                        board[r][c] = 0;
                    }
                }
            }
        }
    }
    private static int getLiveNeighbours(int[][] board, int r, int c, int rows, int cols) {
        int count = 0;
        // 8 directions: top-left, top, top-right, left, right, bottom-left, bottom, bottom-right
        int[][] dirs = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}};

        for (int[] d : dirs) {
            int nr = r + d[0], nc = c + d[1]; // neighbour r, neighbour c
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && board[nr][nc] >= 1) {
                count++;
            }
        }
        return count;

        /*
        // or
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
            }
        }
        */
    }
    private int getLiveNeighbours2(int[][] board, int r, int c, int rows, int cols) {
        int count = 0;
        if (r-1>= 0 && c-1 >=0 && board[r-1][c-1] >= 1) {
            count++;
        }
        if (r-1>=0 && board[r-1][c] >= 1) {
            count++;
        }
        if (r-1>=0 && c+1<cols && board[r-1][c+1] >= 1) {
            count++;
        }
        if (c-1>=0 && board[r][c-1] >= 1) {
            count++;
        }
        if (c+1<cols && board[r][c+1] >= 1) {
            count++;
        }
        if (r+1<rows && c-1>=0 && board[r+1][c-1] >= 1) {
            count++;
        }
        if (r+1<rows && board[r+1][c] >= 1) {
            count++;
        }
        if (r+1<rows && c+1<cols && board[r+1][c+1] >= 1) {
            count++;
        }
        return count;
    }









    /**
     * @TimeComplexity O(m*n)
     * @SpaceComplexity O(1)
     */
    public static void gameOfLife(int[][] board) {
        int rows = board.length, cols = board[0].length;

        for(int r=0; r<rows; r++) {
            for(int c=0; c<cols; c++) {
                int liveNeighbours = getLiveNeighbours(board, r, c, rows, cols); // NOTE: We might get 0 live neighbors
                if(board[r][c] == 0) {
                    board[r][c] = -liveNeighbours;
                } else {
                    board[r][c] = 1+liveNeighbours;
                }
            }
        }

        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                int liveNeighbours = Math.abs(board[r][c]);
                if(board[r][c] <= 0) {
                    board[r][c] = liveNeighbours == 3 ? 1 : 0;
                } else {
                    liveNeighbours--; // remove curr cell value 1
                    board[r][c] = (liveNeighbours > 3 || liveNeighbours < 2) ? 0 : 1;
                }
            }
        }
    }









    public static void gameOfLife2(int[][] board) {
        int rows = board.length, cols = board[0].length;

        // First pass: mark the transitions
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int liveNeighbours = countLives(board, r, c, rows, cols);

                // Rule 1 or 3: live cell dies
                if (board[r][c] == 1 && (liveNeighbours < 2 || liveNeighbours > 3)) {
                    board[r][c] = -1; // alive → dead
                }
                // Rule 4: dead cell becomes alive
                if (board[r][c] == 0 && liveNeighbours == 3) {
                    board[r][c] = 2; // dead → alive
                }
            }
        }

        // Second pass: finalize board
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c] > 0) {
                    board[r][c] = 1; // alive
                } else {
                    board[r][c] = 0; // dead
                }
            }
        }
    }
    private static int countLives(int[][] board, int r, int c, int rows, int cols) {
        int lives = 0;
        int[][] dirs = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
        };

        for (int[] d : dirs) {
            int nr = r + d[0], nc = c + d[1];
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && Math.abs(board[nr][nc]) == 1) {
                lives++;
            }
        }
        return lives;
    }






    public static void gameOfLife3(int[][] board) {
        int ROWS = board.length;
        int COLS = board[0].length;

        // First pass: compute next state
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                int nei = countNeighbors(board, r, c, ROWS, COLS);

                if (board[r][c] == 1) {
                    if (nei == 2 || nei == 3) {
                        board[r][c] = 3; // alive → stays alive
                    }
                    // else it dies (stay 1 for now, will fix later)
                } else {
                    if (nei == 3) {
                        board[r][c] = 2; // dead → alive
                    }
                }
            }
        }

        // Second pass: finalize board
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (board[r][c] == 1) {
                    board[r][c] = 0; // dead
                } else if (board[r][c] == 2 || board[r][c] == 3) {
                    board[r][c] = 1; // alive
                }
            }
        }
    }

    private static int countNeighbors(int[][] board, int r, int c, int ROWS, int COLS) {
        int nei = 0;
        for (int i = r - 1; i <= r + 1; i++) {
            for (int j = c - 1; j <= c + 1; j++) {
                if ((i == r && j == c) || i < 0 || j < 0 || i >= ROWS || j >= COLS) continue;
                if (board[i][j] == 1 || board[i][j] == 3) nei++;
            }
        }
        return nei;
    }














    public static void gameOfLife4(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int liveNeighbors = countLiveNeighbors(board, i, j);
                if (board[i][j] == 1) {
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        board[i][j] = 2;
                    }
                } else {
                    if (liveNeighbors == 3) {
                        board[i][j] = 3;
                    }
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 2) {
                    board[i][j] = 0;
                } else if (board[i][j] == 3) {
                    board[i][j] = 1;
                }
            }
        }
    }

    public static int countLiveNeighbors(int[][] board, int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board[0].length) {
                    if (board[newRow][newCol] == 1 || board[newRow][newCol] == 2) {
                        count++;
                    }
                }
            }
        }
        return count;
    }






    public void gameOfLife5(int[][] board) {
        // iterate through board
        // Mark cells that are alive -> dead as 3
        // mark cells that are dead -> alive as 4
        // iterate through board
        // make 3 -> 0
        // make 4 -> 1

        for(int i = 0; i < board.length; ++i) {
            for(int j = 0; j < board[0].length; ++j) {
                int count = getCount(board,i,j);
                if(board[i][j] == 1 && (count < 2 || count > 3)) {
                    board[i][j] = 3;
                }
                if(board[i][j] == 0 && count == 3) {
                    board[i][j] = 4;
                }

            }
        }

        for(int i = 0; i < board.length; ++i) {
            for(int j = 0; j < board[0].length; ++j) {
                if(board[i][j] == 3) {
                    board[i][j] = 0;
                }
                if(board[i][j] == 4) {
                    board[i][j] = 1;
                }
            }
        }
    }
    public int getCount(int[][] board, int row, int column) {
        return liveNeighboursCount(board,row - 1, column) +
                liveNeighboursCount(board,row - 1, column - 1) +
                liveNeighboursCount(board,row - 1, column + 1) +
                liveNeighboursCount(board,row + 1, column) +
                liveNeighboursCount(board,row + 1, column - 1) +
                liveNeighboursCount(board,row + 1, column + 1) +
                liveNeighboursCount(board,row, column + 1) +
                liveNeighboursCount(board,row, column - 1);
    }
    public int liveNeighboursCount(int[][] board, int row, int column) {
        if(row < 0 || row > board.length -1 || column < 0 || column > board[0].length - 1) return 0;
        if(board[row][column] == 1 || board[row][column] == 3) return 1;
        return 0;
    }

}
