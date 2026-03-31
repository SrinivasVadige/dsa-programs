package Algorithms.BinarySearch;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 08 Feb 2025
 * @link 74. Search a 2D Matrix <a href="https://leetcode.com/problems/search-a-2d-matrix/">LeetCode link</a>
 * @topics Array, Binary Search
 * @companies Google(8), Amazon(6), Microsoft(5), Bloomberg(4), Meta(2), Oracle(7), TikTok(5), Adobe(4), Apple(4), Goldman Sachs(4), Walmart Labs(3), Nutanix(3), Arista Networks(2), Wissen Technology(2)
 * @see DataStructures.BinarySearch
 */
public class Search2DSortedMatrix {

    public static void main(String[] args) {
        int[][] matrix = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        int target = 3;
        System.out.println("searchMatrix(matrix, target) => " + searchMatrixUsingBinarySearch1(matrix, target));
    }




    /**
     * @TimeComplexity log(mn) = log(m) + log(n)
     * @SpaceComplexity O(1)
     * we know that we can use int num = r * cols + c to convert (r,c) to 1D array index
     * and using, r = num/cols, c = num%cols to convert back 1D array index to (r,c)
     */
    public static boolean searchMatrixUsingBinarySearch1(int[][] matrix, int target) {
        int rows = matrix.length, cols = matrix[0].length;
        int l = 0, r = rows*cols-1;
        while (l<=r) {
            int mid = l + (r-l)/2;
            int row = mid / cols;
            int col = mid % cols;
            int num = matrix[row][col];
            if (num == target) return true;
            else if(num < target) l = mid+1;
            else r = mid-1;
        }
        return false;
    }





    /**
     * @TimeComplexity log(mn) = log(m) + log(n)
     * @SpaceComplexity O(1)
     */
    public static boolean searchMatrixUsingBinarySearch2(int[][] matrix, int target) {
        int rows = matrix.length, cols = matrix[0].length;
        // to find target row
        int l = 0, r = matrix.length-1;
        while(l<=r) {
            int mid = (l+r)/2;
            int num = matrix[mid][cols-1]; // last col of mid row
            if (target == num) return true;
            else if (target < num) r = mid-1;
            else l = mid+1;
        }

        if (l>matrix.length-1) return false;

        // to find target column in above target row
        int i = l; // target row
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







    /**
     * @TimeComplexity O(m + n)
     * @SpaceComplexity O(1)
     */
    public static boolean searchMatrixLinear3(int[][] matrix, int target) {
        int row = 0, col = matrix[0].length - 1;
        while (row < matrix.length && col >= 0) {
            if (matrix[row][col] == target) return true;
            if (matrix[row][col] > target) col--;
            else row++; // matrix[row][col] < target
        }
        return false;
    }
}
