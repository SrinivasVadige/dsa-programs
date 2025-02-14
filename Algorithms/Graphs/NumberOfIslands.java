package Algorithms.Graphs;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 13 Feb 2025
 */
public class NumberOfIslands {
    public static void main(String[] args) {
        // char[][] grid = {{1,1,0,0,0},{1,1,0,0,0},{0,0,1,0,0},{0,0,0,1,1}};
        /*
        1 1 0 0 0
        1 1 0 0 0
        0 0 1 0 0
        0 0 0 1 1
        */
        char[][] grid = {{'1','1','1'},{'0','1','0'},{'1','1','1'}};
        System.out.println("numIslandsMyApproach(grid): " + numIslandsMyApproach(grid));

        grid = new char[][]{{'1','1','1'},{'0','1','0'},{'1','1','1'}};
        System.out.println("numIslands(grid): " + numIslands(grid));
    }

    /**
     * Only horizontal and vertical directions are allowed not diagonal
     */
    public static int numIslandsMyApproach(char[][] grid) {
        int count = 0;
        for (int m=0; m<grid.length; m++) {
            for (int n=0; n<grid[0].length; n++) {
                if (grid[m][n] == '1') {
                    count++;
                    traverseIsland(grid, m, n);
                }
            }
        }
        return count;
    }

    private static void traverseIsland(char[][] grid, int m, int n) {
        if (m < 0 || n < 0 || m>=grid.length || n>=grid[0].length || grid[m][n]=='0') return;
        grid[m][n] = '0'; // mark as visited
        traverseIsland(grid, m+1, n); // bottom
        traverseIsland(grid, m, n+1); // right
        traverseIsland(grid, m-1, n); // top
        traverseIsland(grid, m, n-1); // left
    }

    private static int rows;
    private static int cols;
    private static int island = 0;
    public static int numIslands(char[][] grid) {
        rows = grid.length;
        cols = grid[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; ++col) {
                if (grid[row][col] == '1') {
                    mark(grid, row, col);
                    island++;
                }
            }
        }
        return island;
    }

    public static void mark(char[][] grid, int row, int col) {
        grid[row][col] = '*';

        if (row > 0 && grid[row - 1][col] == '1')
            mark(grid, row - 1, col);
        if (row + 1 < rows && grid[row + 1][col] == '1')
            mark(grid, row + 1, col);
        if (col > 0 && grid[row][col - 1] == '1')
            mark(grid, row, col - 1);
        if (col + 1 < cols && grid[row][col + 1] == '1')
            mark(grid, row, col + 1);
    }



    @SuppressWarnings("unused")
    private static boolean isSameIsland(int m, int n, int[][] grid) {
        if (grid[m][n] != 1) return false;
        boolean isTrue = false;

        // left n-1
        if ((n-1)>=0 && grid[m][n-1]==1)
            isTrue = true;

        // right n+1
        if (!isTrue && (n+1) < grid[0].length && grid[m][n+1]==1)
            isTrue = true;

        // bottom m+1
        if ( !isTrue && (m+1) < grid.length && grid[m+1][n]==1)
            isTrue = true;

        // top m-1
        if (!isTrue && (m-1)>=0 && grid[m-1][n]==1)
            isTrue = true;

        // current
        if (!isTrue && grid[m][n] == 1)
            isTrue = true;

        return isTrue;
    }
}