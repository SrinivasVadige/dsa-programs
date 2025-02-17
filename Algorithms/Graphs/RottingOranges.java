package Algorithms.Graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
    [2,1,1]
    [0,1,1]
    [1,0,1]

    THOUGHTS:
    ---------
    1) Rots horizontally & vertically but not diagonally
    2) Some oranges don't rot at all --> return -1
    3) In some cases, all the oranges are already rotten --> return 0

 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 16 Jan 2025
 */
@SuppressWarnings("unused")
public class RottingOranges {
    public static void main(String[] args) {
        int[][] grid = {{2,1,1},{1,1,0},{0,1,1}};
        System.out.println("orangesRotting(grid): " + orangesRotting(grid));
    }

    public static boolean isValid(int rowVal, int colVal, int rsize, int csize) {
        return rowVal >= 0 && rowVal < rsize && colVal >= 0 && colVal < csize;
    }

    static class Pair {
        int row;
        int col;

        Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static int orangesRotting(int[][] grid) {
        int[] rOffset = {-1, 0, 1, 0};
        int[] cOffset = {0, 1, 0, -1};
        int rsize = grid.length;
        int csize = grid[0].length;
        Queue<Pair> queue = new LinkedList<>();
        boolean isFresh = false;

        for (int i = 0; i < rsize; i++) {
            for (int j = 0; j < csize; j++) {
                if (grid[i][j] == 1) {
                    isFresh = true;
                }
            }
        }

        if (!isFresh) {
            return 0;
        }

        int time = 0;
        for (int i = 0; i < rsize; i++) {
            for (int j = 0; j < csize; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new Pair(i, j));
                }
            }
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Pair curr = queue.poll();
                for (int j = 0; j < 4; j++) {
                    int rowVal = curr.row + rOffset[j];
                    int colVal = curr.col + cOffset[j];
                    if (isValid(rowVal, colVal, rsize, csize) && grid[rowVal][colVal] == 1) {
                        grid[rowVal][colVal] = 2;
                        queue.offer(new Pair(rowVal, colVal));
                    }
                }
            }
            if (!queue.isEmpty()) {
                time++;
            }
        }

        for (int i = 0; i < rsize; i++) {
            for (int j = 0; j < csize; j++) {
                if (grid[i][j] == 1) {
                    return -1;
                }
            }
        }

        return time;
    }






    public static int orangesRotting2(int[][] grid) {
        int rows = grid.length, cols = grid[0].length, count = 0, fresh = 0, minutes = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) fresh++;
                if (grid[i][j] == 2) count++;
            }
        }
        while (count > 0 && fresh > 0) {
            minutes++;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (grid[i][j] == 2) {
                        if (i - 1 >= 0 && grid[i - 1][j] == 1) {
                            grid[i - 1][j] = 2;
                            fresh--;
                        }
                        if (i + 1 < rows && grid[i + 1][j] == 1) {
                            grid[i + 1][j] = 2;
                            fresh--;
                        }
                        if (j - 1 >= 0 && grid[i][j - 1] == 1) {
                            grid[i][j - 1] = 2;
                            fresh--;
                        }
                        if (j + 1 < cols && grid[i][j + 1] == 1) {
                            grid[i][j + 1] = 2;
                            fresh--;
                        }
                    }
                }
            }
            count = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (grid[i][j] == 1) fresh++;
                    if (grid[i][j] == 2) count++;
                }
            }
        }
        return fresh == 0 ? minutes : -1;
    }

    public int orangesRottingMyApproach(int[][] grid) {
        int mins = 0;
        for (int[] r: grid) {
            for (int c: r) {
                if (c==2) return -1; // all oranges cannot rot
            }
        }


        // at last
        for (int[] r: grid) {
            for (int c: r) {
                if (c==1) return -1; // all oranges cannot rot
            }
        }
        return mins;
    }

    private void dfs() {}

}