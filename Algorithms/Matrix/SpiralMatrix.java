package Algorithms.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 09 March 2025
 */
public class SpiralMatrix {
    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println("spiralOrder My Approach: ---------------------");
        System.out.println(spiralOrderMyApproach(matrix));

        System.out.println("spiralOrder: ---------------------");
        System.out.println(spiralOrder(matrix));
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
    public static List<Integer> spiralOrderMyApproach(int[][] matrix) {
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

    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int rows = matrix.length;
        int cols = matrix[0].length;
        int top = 0;
        int bottom = rows - 1;
        int left = 0;
        int right = cols - 1;

        while (top <= bottom && left <= right) {
            for (int i = left; i <= right; i++) {
                result.add(matrix[top][i]); // top row
            }
            for (int i = top + 1; i <= bottom; i++) {
                result.add(matrix[i][right]); // right column
            }
            for (int i = right - 1; i >= left; i--) {
                result.add(matrix[bottom][i]); // bottom row
            }
            for (int i = bottom - 1; i > top; i--) {
                result.add(matrix[i][left]); // left column
            }

            top++; // top row
            bottom--; // bottom row
            left++; // left column
            right--; // right column
        }
        return result;
    }
}