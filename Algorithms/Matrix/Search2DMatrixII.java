package Algorithms.Matrix;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 March 2025
 */
public class Search2DMatrixII {
    public static void main(String[] args) {
        int[][] matrix = {{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}};
        int target = 20;
        System.out.println("searchMatrix(matrix, target) => " + searchMatrix(matrix, target));
        System.out.println("searchMatrixMyApproach(matrix, target) => " + searchMatrixMyApproach(matrix, target));
    }

    /**
            19 inside ---
            [1 ,2 ,3 ,4 ,5 ]
            [6 ,7 ,8 ,9 ,10]
            [11,12,13,14,15]
            [16,17,18,19,20]
            [21,22,23,24,25]

            5 inside ---
            [1 ,4 ,7 ,11,15]
            [2 ,5 ,8 ,12,19]
            [3 ,6 ,9 ,16,22]
            [10,13,14,17,24]
            [18,21,23,26,30]

            20 inside ---
            [1 ,4 ,7 ,11,15]
            [2 ,5 ,8 ,12,19]
            [3 ,6 ,9 ,16,22]
            [10,13,14,17,24]
            [18,21,23,26,30]
     */
    public static boolean searchMatrix(int[][] matrix, int target) {
        int row = 0, col = matrix[0].length - 1;
        while (row < matrix.length && col >= 0) {
            if (matrix[row][col] == target) return true;
            if (matrix[row][col] > target) col--;
            else row++; // matrix[row][col] < target
        }
        return false;
    }




    public static boolean searchMatrix2(int[][] matrix, int target) {
        // step 1 - initialize starting position at the top-right corner
        int row = 0;
        int col = matrix[0].length-1;

        // step 2 - traverse the matrix
        while(row < matrix.length && col >= 0){
            // step 3 -  get current element
            int current = matrix[row][col];

            // step 4 - check if current element is target
            if(current == target){
                return true;
            }
            // step 5 - if current element is greater than target than, move left
            else if(current > target){
                col--;
            }
            // step 6 - if current element is smaller than target, move down
            else{
                row++;
            }
        }

        // step 7 - return false if target not found
        return false;
    }







    public static boolean searchMatrix3(int[][] matrix, int target) {
        int row=matrix.length,
            col=matrix[0].length;
        if(row>col){
            for(int i=0; i<col; i++){
            if(matrix[0][i]<=target && target<=matrix[row-1][i]){
                if(bSCol(matrix, i, target)){
                    return true;
                }
            }
            }
            return false;
        }else{

        for(int i=0; i<row; i++){
            if(matrix[i][0]<=target && target<=matrix[i][col-1]){
                if(bS(matrix[i], target)){
                    return true;
                }
            }
        }
        return false;
        }
    }
    private static boolean bS(int[] mat, int target){
        int low=0,
            high=mat.length-1,
            mid=0;
        while(low<=high){
            mid=((high-low)>>1)+low;
            if(mat[mid]==target){
                return true;
            }else if(mat[mid]<target){
                low=mid+1;
            }else{
                high=mid-1;
            }
        }
        return false;
    }
    private static boolean bSCol(int[][] mat, int i, int target){
        int low=0,
            high=mat.length-1,
            mid=0;
        while(low<=high){
            mid=((high-low)>>1)+low;
            if(mat[mid][i]==target){
                return true;
            }else if(mat[mid][i]<target){
                low=mid+1;
            }else{
                high=mid-1;
            }
        }
        return false;
    }








    public boolean searchMatrixLinearTimeComplexity(int[][] matrix, int target) {
        int m=matrix.length, n=matrix[0].length;
        int j=0;
        for (; j<n; j++){
            if (matrix[0][j] == target) return true;
            else if(matrix[0][j] > target) {
                j--;
                break;
            }
            //System.out.printf("j: %s\n", j);
        }
        if (j<0) return false;
        else if (j==n) j--;

        while(j>=0) {
            for(int i=1; i<m; i++){
                //System.out.printf("i: %s, j: %s\n", i, j);
                if(matrix[i][j]==target) return true;
            }
            j--;
        }

        return false;
    }











    /**
        FAILING FOR : 20 in [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]]

        PATTERNS:
        --------
        1) First trav through the first row ----> if (matrix[0][i] > target)
     */
    public static boolean searchMatrixMyApproach(int[][] matrix, int target) {
        // find for range
        int m=matrix.length, n=matrix[0].length;

        // scan col row & set max col i.e n
        int j=0, newN = 0;
        for (; j<n; j++){
            if(matrix[0][j] == target || matrix[m-1][j] == target) return true;
            else if (!(matrix[0][j] < target && matrix[m-1][j] > target)) {
                newN=j;
                break;
            }
        }
        // not found in above loop
        if (j==n) return false;
        else n=newN;

        // scan each row & set max row i.e m
        int i=0, newM=0;
        for(; i<m; i++) {
            if(matrix[i][0] == target || matrix[i][n-1] == target) return true;
            else if (!(matrix[i][0] < target)) {
                newM=i;
                break;
            }
        }
        // not found in above loop
        if (i==m) return false;
        else m=newM;

        for(i=0; i<m; i++) {
            for(j=0; j<n; j++) if(matrix[i][j]==target) return true;
        }

        return false;
    }

}
