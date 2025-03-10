package Algorithms.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 10 March 2025
 *
 */
public class SetMatrixZeroes {
    public static void main(String[] args) {
        int[][] matrix = {{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}};
        /*
            (0,0),(0,1),(0,2),(0,3),(0,4)
            (1,0),(1,1),(1,2),(1,3),(1,4)
            (2,0),(2,1),(2,2),(2,3),(2,4)
            (3,0),34,1)3(4,23,(4,3),(4,4)
            (4,0),(4,1),(4,2),(4,3),(4,4)
         */
        System.out.println("setZeroes: ---------------------");
        System.out.println("Before: ");
        for (int i = 0; i < matrix.length; i++) System.out.println(Arrays.toString(matrix[i]));
        setZeroes(matrix);
        System.out.println("\nAfter: ");
        for (int i = 0; i < matrix.length; i++) System.out.println(Arrays.toString(matrix[i]));
    }


    /**
     * @TimeComplexity O(m*n)
     * @SpaceComplexity O(1)


    Initially:
                 0   1   2   7
                 3   4   5   0
                 1   3   1   5

    1st Loop: Mark left and top
                 0   1   2   0
                 0   4   5   0
                 1   3   1   5

    2nd Loop: Skip 1st row & col and mark all possible positions as 0's by comparing with 1st row & col
                 0   1   2   0
                 0   0   0   0
                 1   3   1   5

    3rd Loop:
                 0   0   0   0
                 0   0   0   0
                 0   3   1   5



    Similarly-----

    Initially:
                 1   1   1   1
                 1   1   1   0
                 1   0   1   1
                 1   1   1   1

    1st Loop: Mark left and top
                 1   0   1   0
                 0   1   1   0
                 0   0   1   1
                 1   1   1   1

    2nd Loop: Skip 1st row & col and mark all possible positions as 0's by comparing with 1st row & col
                 1   0   1   0
                 0   0   0   0
                 0   0   0   0
                 1   0   1   0

    3rd Loop: -- no loop here, as in 1st loop we didn't find any 0's in 1st row and 1st col
                 1   0   1   0
                 0   0   0   0
                 0   0   0   0
                 1   0   1   0


       Instead of using extra space, we can mark the positions left and top that need to be zeroed out.
       Then we can
     */
    public static void setZeroes(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean isFirstRowHasZero = false, isFirstColHasZero = false;

        // mark the positions that need to be zeroed out
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (matrix[r][c] == 0) {
                    if (r == 0) isFirstRowHasZero = true;
                    if (c == 0) isFirstColHasZero = true;
                    matrix[r][0] = 0;
                    matrix[0][c] = 0;
                }
            }
        }

        // zero out the marked positions except the first row and column
        for (int r = 1; r < m; r++) {
            for (int c = 1; c < n; c++) {
                if (matrix[r][0] == 0 || matrix[0][c] == 0) matrix[r][c] = 0; // isTopRowZero || isLeftColZero
            }
        }

        // handle the first row and column
        if (isFirstRowHasZero) {
            for (int c = 0; c < n; c++) matrix[0][c] = 0;
        }
        if (isFirstColHasZero) {
            for (int r = 0; r < m; r++) matrix[r][0] = 0;
        }
    }

    /**
     * @TimeComplexity O(m*n)
     * @SpaceComplexity O(1)

        Same as above setZeroes() but instead use one variable for isRowZero


     */
    public static void setZeroes2(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean isRowZero = false;
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (matrix[r][c] == 0) {
                    matrix[0][c] = 0; // mark the position that needs to be zeroed out
                    if (r > 0) matrix[r][0] = 0; // skipping matrix[0][0], mark the position that needs to be zeroed out
                    else isRowZero = true; // mark the first column that needs to be zeroed out
                }
            }
        }

        for (int r = 1; r < m; r++) {
            for (int c = 1; c < n; c++) {
                if (matrix[r][c] !=0 && (matrix[r][0] == 0 || matrix[0][c] == 0)) matrix[r][c] = 0;
            }
        }

        if (matrix[0][0] == 0) {
            for (int r = 0; r < m; r++) matrix[r][0] = 0;
        }
        if (isRowZero) {
            for (int c = 0; c < n; c++) matrix[0][c] = 0;
        }

    }

    /**
     * @TimeComplexity O(m*n)
     * @SpaceComplexity O(m+n)
     *
     *         ___ [ ----- dummy row  -------  ]
     *          |  (0,0),(0,1),(0,2),(0,3),(0,4)
     *          |  (1,0),(1,1),(1,2),(1,3),(1,4)
     *   dummy col (2,0),(2,1),(2,2),(2,3),(2,4)
     *          |  (3,0),(3,1),(3,2),(3,3),(3,4)
     *         _|_ (4,0),(4,1),(4,2),(4,3),(4,4)
     *
     *
     *             ___[T   f   f   T]
     *              T  0   1   2   0
     *              f  3   4   5   2
     *             _f_ 1   3   1   5
     *
     */
    public static void setZeroesUsingDummyRowAndCol(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean[] row = new boolean[m];
        boolean[] col = new boolean[n];

        // mark the positions that need to be zeroed out
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (matrix[r][c] == 0) {
                    row[r] = true;
                    col[c] = true;
                }
            }
        }

        // zero out the marked positions
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (row[r] || col[c]) matrix[r][c] = 0;
            }
        }
    }






    // It's not working, but to understand the setZeros() method ---> cause, here we trav from i=0,j=0
    // Here we mark t & l as they'll be 0's
    // but as we start from i=0 j=0, we'll mark the 1st row & col as 0's that's why this setZeroesDummy() is not working
    public void setZeroesDummy(int[][] matrix) {
        for (int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                if (matrix[i][j]==0) {
                    // mark t & l as they already visited
                    matrix[0][j]=0; //t
                    matrix[i][0]=0; //l
                }
            }
        }
        // left col --- set matched rows to 0 --- RIGHT 0's
        for (int row=0; row<matrix.length; row++) {
            if (matrix[row][0] == 0) {
                for(int col=1; col<matrix[0].length; col++) matrix[row][col]=0;
            }
        }

        // top row --- set matched cols to 0 --- DOWN 0's
        for(int col=0; col<matrix[0].length; col++) {
            if (matrix[0][col] == 0) { // 1st row
                for (int row=1; row<matrix.length; row++) matrix[row][col]=0;
            }
        }
    }






    public static void setZeroesUsingList(int[][] matrix) {
        List<List<Integer>> lst = new ArrayList<>();
        for (int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                if (matrix[i][j]==0) lst.add(Arrays.asList(i,j));
            }
        }
        for (List<Integer> subLst: lst) setZeroes(matrix, subLst.get(0), subLst.get(1));
    }
    private static void setZeroes(int[][] matrix, int i, int j) {
        // two loops
        int a,b;
        // 1st loop ---> keep row same and move l and r
        for(a=i,b=j; a>=0; a--) matrix[a][b] = 0;
        for(a=i,b=j; a<matrix.length; a++) matrix[a][b] = 0;
        // 2nd loop ---> keep col and move t and d
        for(a=i,b=j; b>=0; b--) matrix[a][b] = 0;
        for(a=i,b=j; b<matrix[0].length; b++) matrix[a][b] = 0;
    }














    // NOT WORKING as -2^31 <= matrix[i][j] <= 2^31 - 1
    public void setZeroesIntuition(int[][] matrix) {
        for (int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                if (matrix[i][j]==0) markPosition(matrix, i, j);
            }
        }
        for (int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                if (matrix[i][j]==-1) matrix[i][j]=0; // NOT WORKING as -2^31 <= matrix[i][j] <= 2^31 - 1
            }
        }
    }
    private void markPosition(int[][] matrix, int i, int j) {
        // two loops
        // NOTE: DON'T CHANGE 0'S THAT YOU SEE HERE
        int a,b;
        // 1st loop ---> keep row same and move l and r
        for(a=i,b=j; a>=0; a--) if (matrix[a][b] != 0) matrix[a][b]=-1;
        for(a=i,b=j; a<matrix.length; a++) if (matrix[a][b] != 0) matrix[a][b]=-1;
        // 2nd loop ---> keep col and move t and d
        for(a=i,b=j; b>=0; b--) if (matrix[a][b] != 0) matrix[a][b]=-1;
        for(a=i,b=j; b<matrix[0].length; b++) if (matrix[a][b] != 0) matrix[a][b]=-1;
    }
}
