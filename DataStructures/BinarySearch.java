package DataStructures;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 10 May 2023
 *
 * Binary Search has a time complexity of O(log n) but only works on sorted arrays
 *
 * But Binary Search has 2 aspects:
 * 1. A sorted array
 * 2. Splitting the array into two equal halves
 *
 * So, if we don't have a sorted array, then try to split the array into two equal halves if we have some patterns like num[i-1] < num[i] < num[i+1]
 *
 * 1. A sorted array
 * -----------------
 * If you have a sorted array, then you can use Binary Search to find an element in O(log n) time
 *
 * 2. Splitting the array into two equal halves
 * ---------------------------------------------
 * Observe some patterns like num[i-1] < num[i] < num[i+1]
 * You can split the array into two equal halves like
 * A1 = l1{2, 4} r1{9, 12}
 * Then
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 6};
        int target = 7;
        System.out.println("binarySearch(nums, target) => " + binarySearch(nums, target));
    }



    public static int binarySearch(int[] nums, int target) {
        int l = 0, r = nums.length - 1, mid;
        while (l <= r) {
            mid = l + (r - l) / 2; // to avoid overflow or use (start + end) / 2
            if (nums[mid] == target) return mid;
            else if (nums[mid] < target) l = mid + 1;
            else r = mid - 1;
        }
        return l; // or return -1; -- return the index where it would be if it were inserted in order
        /**
         * [1, 3, 5, 6] & target = 7
         * if target>all nums eles and not found then it'll return nums.length i.e "n" as 'r' is initialized to 'n-1' and after l & r completion in while(l<=r) it'll be r+1
         * and 'r' will return 'n-1' as it is initialized to 'n-1'
         *
         * [1, 3, 5, 6] & target = -1
         * similarly if target<all nums eles and not found then 'l' return 0 not -1, so 'l' is initialized to 0
         * and 'r' will return -1
         *
         * so, check and return -1; instead of l
         */
    }



    public int binarySearchWithDuplicates(int[] nums, int target) {
        int l = 0, r = nums.length - 1, mid;
        while (l <= r) {
            mid = l + (r - l) / 2; // to avoid overflow or use (start + end) / 2
            if (nums[mid] == target) {
                while(mid>0 && nums[mid-1]==target) mid--; // this will return the starting index of the target (as we have duplicate targets)
                return mid;
            }
            else if (nums[mid] < target) l = mid + 1;
            else r = mid - 1;
        }
        return -1;
    }


    public int binarySearchWithDuplicates2(int[] nums, int target) {
        int l = 0, r = nums.length - 1, mid;
        while (l<=r) {
            mid = l+(r-l)/2;
            if (nums[mid] >= target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return l;
        /**
         * same as above binarySearch() explanation
         *
         * [1, 3, 3, 5, 6] & target = 3
         *  l     m     r
         *
         * [1, 3, 3, 5, 6] & target = 3
         *  l  r
         *
         * [1, 3, 3, 5, 6] & target = 3
         * lr
         */
    }

}
