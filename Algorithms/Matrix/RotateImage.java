package Algorithms.Matrix;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 08 March 2025
 * @link 48. Rotate Image <a href="https://leetcode.com/problems/rotate-image/">LeetCode Link</a>
 * @topics Array, Math, Matrix
 */
public class RotateImage {
    public static void main(String[] args) {
         int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
         
        System.out.println("\nrotate using transpose and reverse: ---------------------");
        matrix = new int[][]{{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {11, 12, 13, 14, 15}, {16, 17, 18, 19, 20}, {21, 22, 23, 24, 25}};
        System.out.println("Before rotation: "); for (int[] ints : matrix) System.out.println(Arrays.toString(ints));
        rotateUsingTransposeAndReverse(matrix);
        System.out.println("\nAfter rotation: "); for (int[] ints : matrix) System.out.println(Arrays.toString(ints));


        System.out.println("\nrotate: ---------------------");
        matrix = new int[][]{{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {11, 12, 13, 14, 15}, {16, 17, 18, 19, 20}, {21, 22, 23, 24, 25}};
        System.out.println("Before rotation: "); for (int[] ints : matrix) System.out.println(Arrays.toString(ints));
        rotate2(matrix);
        System.out.println("\nAfter rotation: "); for (int[] ints : matrix) System.out.println(Arrays.toString(ints));


        System.out.println("\nrotateMyApproach Old: ---------------------");
        matrix = new int[][]{{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {11, 12, 13, 14, 15}, {16, 17, 18, 19, 20}, {21, 22, 23, 24, 25}};
        System.out.println("Before rotation: "); for (int[] ints : matrix) System.out.println(Arrays.toString(ints));
        rotateMyApproachOld(matrix);
        System.out.println("\nAfter rotation: "); for (int[] ints : matrix) System.out.println(Arrays.toString(ints));
    }
    
    
    
    
    /**
     * Diagonal transpose and reverse
     *          A                       B                        C
        -----------------         -----------------       -----------------
        |0,0|0,1|0,2|0,3|         |0,0|1,0|2,0|3,0|       |3,0|2,0|1,0|0,0|
        |1,0|1,1|1,2|1,3|    =>   |0,1|1,1|2,1|3,1|   =>  |3,1|2,1|1,1|0,1|
        |2,0|2,1|2,2|2,3|         |0,2|1,2|2,2|3,2|       |3,2|2,2|1,2|0,2|
        |3,0|3,1|3,2|3,3|         |0,3|1,3|2,3|3,3|       |3,3|2,3|1,3|0,3|
        -----------------         -----------------       -----------------

        PATTERNS:
        ---------
        1) When we compare matrixA and matrixC, the cols became rows and rows became cols but in reverse order
        2) MatrixB is rows & cols exchange of matrixA, just like matrixC but matrixA and matrixB are in same order i.e reverse of matrixC
        3) And in MatrixA and MatrixB, imagine a diagonal line and now (1,0) is placed in (0,1) and all eles
        4) Therefore matrixB[i][j]==matrixA[j][i] 
        5) Now just reverse the matrixB rows then we'll get matrixC.
     */
    public static void rotateUsingTransposeAndReverse(int[][] matrix) {
        int n = matrix.length;
        // transpose
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                // swap
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        // reverse
        for (int row=0; row<n; row++) {
            int l=0, r=n-1;
            while (l<r) {
                // reverse
                int temp = matrix[row][l];
                matrix[row][l] = matrix[row][r];
                matrix[row][r] = temp;
                l++; r--;
            }
        }
    }




    /**

              l           r          l           r               l   r
            -----------------      -----------------       -----------------
        t   |0,0|0,1|0,2|0,3|      |3,0|2,0|1,0|0,0|       |   |   |   |   |
            |1,0|1,1|1,2|1,3|  =>  |3,1|   |   |0,1|   =>  |   |2,1|1,1|   |   t
            |2,0|2,1|2,2|2,3|      |3,2|   |   |0,2|       |   |2,2|1,2|   |   b
        b   |3,0|3,1|3,2|3,3|      |3,3|2,3|1,3|0,3|       |   |   |   |   |
            -----------------      -----------------       -----------------

        Here l,r,t,b are fixed. And we trav the each col

        or it can also be portrayed as, where * is skip last ele as it'll be calculated by first ele

                                        l1   l2 lr3 r2  r1
            ---------------------      ---------------------      ---------------------
            |0,0|0,1|0,2|0,3|0,4|   t1 |0,0|0,1|0,2|0,3| * |      |4,0|3,0|2,0|1,0|0,0|
            |1,0|1,1|1,2|1,3|1,4|   t2 |   |1,1|1,2| * |   |      |4,1|3,1|2,1|1,1|0,1|
            |2,0|2,1|2,2|2,3|2,4|  tb3 |   |   |2,2|   |   |      |4,2|3,2|2,2|1,2|0,2|
            |3,0|3,1|3,2|3,3|3,4|   b2 |   |   |   |   |   |      |4,3|3,3|2,3|1,3|0,3|
            |4,0|4,1|4,2|4,3|4,4|   b1 |   |   |   |   |   |      |4,4|3,3|2,2|1,1|0,4|
            ---------------------      ---------------------      ---------------------

        as l==r in (2,2) while(l<r) will be exited
        use i to add or subtract l,r,t,d accordingly

             l             r
             0    1    2   3 ---> i
            ------------------
        t   |🍏| 🔵 | ⭐ |🍏|
            |⭐|          |🔵|
            |🔵|          |⭐|
        b   |🍏| ⭐ | 🔵 |🍏|
            -------------------

        If we observe carefully, we can see that 🍏 forms one square, similarly 🔵, ⭐ also forms diff squares

     */
    public static void rotate2(int[][] matrix) {
        int n = matrix.length;
        int l = 0, r = n-1; // fixed cols, change after one top square layer is rotated
        while(l<r) {
            int top = l, bottom = r; // as this is a square matrix
            // use i to trav each col position from l to r except r ele --- all i, i+1, i+2.... rotation squares
            for (int i = 0; i < r-l; i++) { // or for(int lI=l, i=0; lI<r; lI++, i++) i.e., ignore the last ele in the row because it was already rotated when i=0;
                int topLeft = matrix[top][l+i]; // TOP_LEFT
                matrix[top][l+i] = matrix[bottom-i][l]; // TOP_LEFT = BOTTOM_LEFT
                matrix[bottom-i][l] = matrix[bottom][r-i]; // BOTTOM_LEFT = BOTTOM_RIGHT
                matrix[bottom][r-i] = matrix[top+i][r]; // BOTTOM_RIGHT = TOP_RIGHT
                matrix[top+i][r] = topLeft; // TOP_RIGHT = TOP_LEFT or topLeft
            }
            l++; r--; // calculate inside box -- move inside until l>=r i.e., middle eles in the matrix
        }
    }


    public static void rotate3(int[][] matrix) {
        int n = matrix.length;
        int l = 0, r = n-1; // col
        while(l<r) {
            for (int i = 0; i < r-l; i++) {
                int temp = matrix[l][l+i];
                matrix[l][l+i] = matrix[r-i][l]; // TOP_LEFT = DOWN_LEFT
                matrix[r-i][l] = matrix[r][r-i]; // DOWN_LEFT = DOWN_RIGHT
                matrix[r][r-i] = matrix[l+i][r]; // DOWN_RIGHT = TOP_RIGHT
                matrix[l+i][r] = temp; // TOP_RIGHT = TOP_LEFT or temp
            }
            l++; r--;
        }
    }
    
    
    
    

    /**
     * Without extra space

        PATTERNS:
        ---------
        1) Traverse each matrix[i][j] and rotate it's corresponding square elements
        2) Initially store (0,0) val in temp and rotate anti-clockwise
        3) Then insert (3,0) val in (0,0)
        4) Now (3,3) val in (3,0)
        5) Now (0,3) val in (3,3)
        6) Finally temp val in (0,3)
        7) Up to now, all the edges are rotated
        8) Now, we have to rotate the inner elements by moving inwards, just like recursion

        -----------------      -----------------
        |0,0|0,1|0,2|0,3|      |3,0|2,0|1,0|0,0|
        |1,0|1,1|1,2|1,3|      |3,1|2,1|1,1|0,1|
        |2,0|2,1|2,2|2,3|      |3,2|2,2|1,2|0,2|
        |3,0|3,1|3,2|3,3|      |3,3|2,3|1,3|0,3|
        -----------------      -----------------

        temp = (0,0)
        (0,0) <- (0,3) <- (3,3) <- (3,0) <- temp

        temp = (0,1)
        (0,1) <- (2,0) <- (3,2) <- (1,3) <- temp

        temp = (0,2)
        (0,2) <- (2,0) <- (3,2) <- (1,3) <- temp

        temp = (0,3) is already rotated

        temp = (1,0)
        (1,0) <- (3,1) <- (2,3) <- (0,2) <- temp

        temp = (1,1)
        (1,1) <- (2,1) <- (2,2) <- (1,2) <- temp

        temp = (1,2) is already rotated

        temp = (1,3) is already rotated

        9) So, we don't need to trav each element as we already rotated corresponding square edge elements
        10) As per the pattern, when n = 4, if we trav these 4 elements then we get the rotated matrix. No need to trav all the elements.
            -----------------
            |0,0|0,1|   |   |
            |1,0|1,1|   |   |
            |   |   |   |   |
            |   |   |   |   |
            -----------------

            ---------------------     ---------------------      ---------------------
            |0,0|0,1|0,2|0,3|0,4|     |0,0|0,1|0,2|   |   |      |4,0|3,0|2,0|1,0|0,0|
            |1,0|1,1|1,2|1,3|1,4|     |1,0|1,1|1,2|   |   |      |4,1|3,1|2,1|1,1|0,1|
            |2,0|2,1|2,2|2,3|2,4|     |   |   |   |   |   |      |4,2|3,2|2,2|1,2|0,2|
            |3,0|3,1|3,2|3,3|3,4|     |   |   |   |   |   |      |4,3|3,3|2,3|1,3|0,3|
            |4,0|4,1|4,2|4,3|4,4|     |   |   |   |   |   |      |4,4|3,3|2,2|1,1|0,4|
            ---------------------     ---------------------      ---------------------

        10) So avg rows == half rows == <n/2 == 0-1 == 2 rows
        11) And avg cols == half cols == n%2==0? n/2-1 : n/2;
        12) When n=3, rows<n/2 or <5/2 or <2 i.e 0<=rows<=1
        13) FINALLY: We can achieve the same thing by jumping n-1-2*i times.
        14) If n=5, then we jump (0,0) by 4 times i.e to (0,4) and (1,1) by 2 times i.e to (1,3)


     */
    public static void rotateMyApproachOld(int[][] matrix) {
        int n = matrix.length;
        int jI = n%2==0? n/2-1 : n/2;
        for (int i = 0; i < n/2; i++) { // rows < n/2
            int jumpC=n-1-2*i; // jump count
            for (int j = 0; j <= jI; j++) { // cols

                int ci = i, cj = j, pi, pj; // current & previous indices
                int temp = matrix[ci][cj];
                // edgeC & posC are constant for all the current square eles in this currentSubMatrix
                int edgeC = j-i; // Edge Count
                int posC = jumpC - edgeC; // Position Count

                // TOP_LEFT = DOWN_LEFT
                pj = cj - edgeC; // left, move until edge of this currSubMatrix
                pi = ci + posC; // down
                matrix[ci][cj] = matrix[pi][pj];
                ci=pi; cj=pj;

                // DOWN_LEFT = DOWN_RIGHT
                pi= ci + edgeC;
                pj = cj + posC;
                matrix[ci][cj] = matrix[pi][pj];
                ci=pi; cj=pj;

                // DOWN_RIGHT = TOP_RIGHT
                pj= cj + edgeC;
                pi = ci - posC;
                matrix[ci][cj] = matrix[pi][pj];
                ci=pi; cj=pj;

                // TOP_RIGHT = TOP_LEFT or temp
                matrix[ci][cj] = temp;
            }
        }
    }




    /**
     * rotateMyApproachNew


        each ele is moving n-1 places

        (0,0) (0,1) (0,2)
        (1,0) (1,1) (1,2)
        (2,0) (2,1) (2,2)

        (2,0) (1,0) (0,0)
        (2,1) (1,1) (0,1)
        (2,2) (1,2) (0,2)


        i*cols+j ---> unique position
     */
    public void rotateUsingLayerByLayerCycleRotation(int[][] matrix) {
        final int N = matrix.length;
        int top = 0, bottom = N-1;
        int left = 0, right = N-1;

        while(top <= bottom && left <= right) { // outer-box

            final int CURR_N = (right-left+1);
            int calculatedCols = 0;

            while(calculatedCols < (right-left)) { // top-row
                int startLocation = top*N + (left + calculatedCols);
                int prevNum = Integer.MIN_VALUE;
                int currLocation = top*N + (left + calculatedCols);

                while(true) { // each-col
                    int r = currLocation/N, c = currLocation % N;
                    int num = matrix[r][c];
                    matrix[r][c] = prevNum;

                    if (prevNum != Integer.MIN_VALUE && currLocation == startLocation) break;

                    // nextLocation
                    int nextR = -1, nextC = -1;
                    final int JUMP = CURR_N - 1;
                    int jumped = 0;

                    if(r == top) {
                        jumped = right-c;
                        nextC = c + jumped;
                        nextR = r + (JUMP - jumped);
                    } else if (c == right) {
                        jumped = bottom - r;
                        nextR = r + jumped;
                        nextC = c - (JUMP - jumped);
                    } else if (r == bottom) {
                        jumped = c-left;
                        nextC = c - jumped;
                        nextR = r - (JUMP - jumped);
                    } else {
                        jumped = r - top;
                        nextR = r - jumped;
                        nextC = c + (JUMP - jumped);
                    }

                    prevNum = num;
                    currLocation = nextR*N + nextC;
                }

                calculatedCols++;
            }

            top++;
            bottom--;
            left++;
            right--;
        }
    }







    public static void rotate4(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n/2; i++) { // rows < n/2
            for (int j = i; j < n-1-i; j++) { // cols < n-1-i, but not (n%2==0? n/2 : (n/2)+1)
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n-1-j][i]; // TOP_LEFT = DOWN_LEFT
                matrix[n-1-j][i] = matrix[n-1-i][n-1-j]; // DOWN_LEFT = DOWN_RIGHT
                matrix[n-1-i][n-1-j] = matrix[j][n-1-i]; // DOWN_RIGHT = TOP_RIGHT
                matrix[j][n-1-i] = temp; // TOP_RIGHT = TOP_LEFT or temp
            }
        }
    }





    /**
     * Using extra space

        PATTERNS:
        ---------
        1) Edge elements are staying in edges & Side elements in side.
        2) Before the sort, in each row --> i is constant(ROW I) and j is increasing(0 TO LEN-1)
        3) After the sort, in each row --> i is decreasing(LEN-1 TO 0), j is constant(LEN-1-ROW I)

        -------------          -------------
        |0,0|0,1|0,2|          |2,0|1,0|0,0|
        |1,0|1,1|1,2|          |2,1|1,1|0,1|
        |2,0|2,1|2,2|          |2,2|1,2|0,2|
        -------------          -------------

        -----------------      -----------------
        |0,0|0,1|0,2|0,3|      |3,0|2,0|1,0|0,0|
        |1,0|1,1|1,2|1,3|      |3,1|2,1|1,1|0,1|
        |2,0|2,1|2,2|2,3|      |3,2|2,2|1,2|0,2|
        |3,0|3,1|3,2|3,3|      |3,3|2,3|1,3|0,3|
        -----------------      -----------------

     */
    public void rotateUsingArrCopy(int[][] matrix) {
        int n=matrix.length;
        int[][] copy = new int[n][n]; // matrix.clone(); or Arrays.copyOf(matrix, n); won't work for 2D Array
        for(int i=0; i<n; i++) {for (int j=0; j<n; j++) copy[i][j]=matrix[i][j];} // or copy[i] = matrix[i].clone(); or Arrays.copyOf(matrix[i], n);
        for(int i=0, rj=0; i<n; i++, rj++) {
            for (int j=0, ri=n-1; j<n; j++, ri--) {
                // matrix[j][n-1-i] = copy[i][j]; --- if you don't want to use ri & rj extra vars
                matrix[i][j] = copy[ri][rj]; // or copy[i][j] = matrix[i][j];matrix[i][j] = copy[ri][rj]!=0?copy[ri][rj]:matrix[ri][rj];
            }
        }
    }






    // same as rotateUsingArrCopy
    public void rotateUsingMap(int[][] matrix) {
        int n=matrix.length;
        Map<String, Integer> map = new HashMap<>();

        for(int i=0, rj=0; i<n; i++, rj++) {
            for (int j=0, ri=n-1; j<n; j++, ri--) {
                // matrix[i][j] == curr
                // matrix[ri][rj] == after rotation
                map.put(i+","+j, matrix[i][j]);
                matrix[i][j] = map.getOrDefault(ri+","+rj, matrix[ri][rj]); // rotated val
                map.remove(ri+","+rj);

            }
        }
    }






    public static void rotateUsingRotatedArray(int[][] matrix) {
        int n = matrix.length;
        int[][] rotated = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotated[j][n-1-i] = matrix[i][j];
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = rotated[i][j];
            }
        }
    }
}
