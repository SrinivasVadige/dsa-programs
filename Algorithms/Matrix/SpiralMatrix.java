package Algorithms.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 09 March 2025
 * @link 54. Spiral Matrix <a href="https://leetcode.com/problems/spiral-matrix/">LeetCode link</a>
 * @topics Array, Matrix, Matrix Transpose
 */
public class SpiralMatrix {
    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        System.out.println("spiralOrder: ---------------------");
        System.out.println(spiralOrder(matrix));

        System.out.println("spiralOrder My Approach: ---------------------");
        System.out.println(spiralOrderMyApproachOld(matrix));
    }



    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        int top = 0, bottom = matrix.length - 1;
        int left = 0, right = matrix[0].length - 1;

        while(top <= bottom && left <= right) {
            for(int r=top, c=left; c<=right; c++) res.add(matrix[r][c]); // →
            top++;

            for(int r=top, c=right; r<=bottom; r++) res.add(matrix[r][c]); // ↓
            right--;

            for(int r=bottom, c=right; top <= bottom && c>=left; c--) res.add(matrix[r][c]); // ←   && skip if one row, NOTE: we already did top++
            bottom--;

            for(int r=bottom, c=left; left <= right && r>=top; r--) res.add(matrix[r][c]); // ↑   && skip if one col, NOTE: we already did right--
            left++;
        }

        return res;
    }





    public static List<Integer> spiralOrder2(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int ROWS = matrix.length, COLS = matrix[0].length;

        int top = 0, bottom = ROWS - 1; // rows
        int left = 0, right = COLS - 1; // cols

        while (top <= bottom && left <= right) {
            // left → right
            for (int c = left; c <= right; c++) {
                result.add(matrix[top][c]);
            }
            top++;

            // top → bottom
            for (int r = top; r <= bottom; r++) {
                result.add(matrix[r][right]);
            }
            right--;

            // right → left (only if still valid row)
            if (top <= bottom) {
                for (int c = right; c >= left; c--) {
                    result.add(matrix[bottom][c]);
                }
                bottom--;
            }

            // bottom → top (only if still valid col)
            if (left <= right) {
                for (int r = bottom; r >= top; r--) {
                    result.add(matrix[r][left]);
                }
                left++;
            }
        }

        return result;
    }





    public static List<Integer> spiralOrder3(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int rows = matrix.length;
        int cols = matrix[0].length;
        int top = 0, bottom = rows - 1; // rows
        int left = 0, right = cols - 1; // cols

        while (top <= bottom && left <= right) {
            // top row
            for (int c = left; c <= right; c++) {
                result.add(matrix[top][c]);
            }

            // right column
            for (int r = top + 1; r <= bottom; r++) {
                result.add(matrix[r][right]);
            }

            // bottom row (only if more than one row remains)
            if (top < bottom) {
                for (int c = right - 1; c >= left; c--) {
                    result.add(matrix[bottom][c]);
                }
            }

            // left column (only if more than one col remains)
            if (left < right) {
                for (int r = bottom - 1; r > top; r--) {
                    result.add(matrix[r][left]);
                }
            }

            top++;
            bottom--;
            left++;
            right--;
        }
        return result;
    }













    public List<Integer> spiralOrder4(int[][] matrix) {
        List<Integer> spiralNums = new ArrayList<>();
        final int ROWS = matrix.length, COLS = matrix[0].length, NEED = ROWS * COLS;

        for(int startR=0, startC=0; spiralNums.size()<NEED; startR++, startC++) {
        // or for(int startR=0, startC=0, numOfMatrices=(int)Math.ceil(COLS/2.0); numOfMatrices>0; numOfMatrices--, startR++, startC++) {
            int rows = ROWS - startR*2;
            int cols = COLS - startC*2;

            for(int r=startR, c=startC; c-startC<cols; c++) spiralNums.add(matrix[r][c]);
            for(int r=startR+1, c=startC+cols-1; r-startR<rows; r++) spiralNums.add(matrix[r][c]);
            for(int r=startR+rows-1, c=startC+cols-2; c>=startC && rows!=1; c--) spiralNums.add(matrix[r][c]);
            for(int r=startR+rows-2, c=startC; r>startR && cols!=1; r--) spiralNums.add(matrix[r][c]);
        }

        return spiralNums;
    }





    public List<Integer> spiralOrder5(int[][] matrix) {
        List<Integer> spiralNums = new ArrayList<>();
        final int ROWS = matrix.length, COLS = matrix[0].length, NEED = ROWS * COLS;

        for(int startR=0, startC=0; spiralNums.size()<NEED; startR++, startC++) { // or for(int startR=0, startC=0, numOfMatrices=(int)Math.ceil(COLS/2.0); numOfMatrices>0; numOfMatrices--, startR++, startC++) {
            int endR = ROWS - startR; // endR row - right exclusive index
            int endC = COLS - startC; // endC col - down exclusive index

            for(int r=startR, c=startC; c<endC; c++) spiralNums.add(matrix[r][c]);
            for(int r=startR+1, c=endC-1; r<endR; r++) spiralNums.add(matrix[r][c]);
            for(int r=endR-1, c=endC-2; c>=startC && spiralNums.size()<NEED; c--) spiralNums.add(matrix[r][c]);
            for(int r=endR-2, c=startC; r>startR && spiralNums.size()<NEED; r--) spiralNums.add(matrix[r][c]);
        }

        return spiralNums;
    }







    /**
        PATTERNS:
        ---------
        1) Row for loop & col for loop won't work here.
        2) Construct a 5*5 matrix.

             1  2  3  4  5       1  2  3  4  5
            16 17 18 19  6      16           6      17 18 19
            15 24 25 20  7      15           7      24    20     25
            14 23 22 21  8      14           8      23 22 21
            13 12 11 10  9      13 12 11 10  9

     -----------------------------    -----------------------------
    |(0,0) (0,1) (0,2) (0,3) (0,4)|   |(0,0) (0,1) (0,2) (0,3) (0,4)|    -----------------
    |(1,0) (1,1) (1,2) (1,3) (1,4)|   |(1,0)                   (1,4)|   |(1,1) (1,2) (1,3)|   -----
    |(2,0) (2,1) (2,2) (2,3) (2,4)|   |(2,0)                   (2,4)|   |(2,1)       (2,3)|  |(2,2)|
    |(3,0) (3,1) (3,2) (3,3) (3,4)|   |(3,0)                   (3,4)|   |(3,1) (3,2) (3,3)|   -----
    |(4,0) (4,1) (4,2) (4,3) (4,4)|   |(4,0) (4,1) (4,2) (4,3) (4,4)|    -----------------
     -----------------------------     -----------------------------

        3) Outer layer is a rectangle or square, trav that and go inside for another rectangle and so on.
        4) t=0, b=4, l=0, r=4
        5) t=1, b=3, l=1, r=3
        5) t=2, b=2, l=2, r=2
        6) Where t,b are rows == m & l,r are cols == n
     */
    public static List<Integer> spiralOrderMyApproachOld(int[][] matrix) {
        List<Integer> lst = new ArrayList<>();
        int m = matrix.length, n = matrix[0].length;

        // initially
        int t=0, b=m-1, l=0, r=n-1;

        while(t<=b) {
            // colI = col index ---> increase & decrease after ri ----> min=l & max=r
            // rowI = row index ---> after ci increase ri & wait for ci then decrease ----> min=t, max=b

            int rowI=0; // max is t+rowI==d
            int colI=0; // max is l+colI==r

            // TOP_LEFT TO TOP_RIGHT
            for (; colI+l<=r; colI++) {
                lst.add(matrix[t][l+colI]);
            }
            colI--;


            // TOP_RIGHT to BOTTOM_RIGHT
            for(rowI++; rowI+t<=b && lst.size()<m*n; rowI++) { // skip prev corner
                lst.add(matrix[t+rowI][l+colI]);
            }
            rowI--;

            // BOTTOM_RIGHT to BOTTOM_LEFT
            for(colI--; l+colI>=l && t!=b; colI--){ // skip prev corner
                lst.add(matrix[b][l+colI]);

            }
            colI++;


            // BOTTOM_LEFT to TOP_LEFT
            for(rowI--; t+rowI>t && l!=r; rowI--) {
                lst.add(matrix[t+rowI][l+colI]);

            }

            t++; b--;
            l++; r--;
        }
        return lst;
    }
}