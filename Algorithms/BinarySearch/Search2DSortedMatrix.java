package Algorithms.BinarySearch;

/**
 * @author: Srinivas Vadige, srinivas.vadige@gmail.com
 * @since: 08 Feb 2025
 */
public class Search2DSortedMatrix {

    public static void main(String[] args) {
        int[][] matrix = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        int target = 3;
        System.out.println("searchMatrix(matrix, target) => " + searchMatrix(matrix, target));
    }

    // timeComplexity : O(log m + log n)
    public static boolean searchMatrix(int[][] matrix, int target) {
        // to find target row
        int l = 0, r = matrix.length-1;
        while(l<=r) {
            int mid = (l+r)/2;
            int num = matrix[mid][matrix[mid].length-1];
            if (target == num) return true;
            else if (target < num) r = mid-1;
            else l = mid+1;
        }

        if (l>matrix.length-1) return false;

        // to find target column in above target row
        int i = l;
        l = 0;
        r = matrix[i].length-1;
        while(l<=r) {
            int mid = (l+r)/2;
            int num = matrix[i][mid];
            if (target == num) return true;
            else if (target < num) r = mid-1;
            else l = mid+1;
        }
        return false;
    }

    // timeComplexity: O(m+n)
    public static boolean searchMatrixLinear(int[][] matrix, int target) {
        int row = 0, col = matrix[0].length - 1;
        while (row < matrix.length && col >= 0) {
            if (matrix[row][col] == target) return true;
            if (matrix[row][col] > target) col--;
            else row++; // matrix[row][col] < target
        }
        return false;
    }
}
