package Algorithms.BinarySearch;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 12 Feb 2025
 * @link 153. Find Minimum in Rotated Sorted Array <a href="https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/">Leetcode Link</a>
 * @topics Array, Binary Search
 * @companies Bloomberg(3), Google(2), Microsoft(8), Amazon(4), Meta(23), Goldman Sachs(16), TikTok(6), Apple(4), Uber(4), IBM(2), Infosys(2), TCS(2), Oracle(2), Flipkart(2)
 */
public class FindMinInRotatedSortedArray {
    public static void main(String[] args) {
        int[] nums = {4,5,1,2,3};
        System.out.println("findMin(nums) => " + findMin1(nums));
        System.out.println("findMin My Approach Old => " + findMinMyApproachOld(nums));
    }


    public static int findMinMyApproachOld(int[] nums) {
        int l=0, r=nums.length-1, min = Integer.MAX_VALUE;

        while(l<=r) {
            int mid = (l+r)/2;
            if(nums[l]<=nums[mid]) { // if left sorted
                if (min > nums[l]) min=nums[l];
                min = Math.min(min, nums[l]);
                l = mid+1;
            } else { // if left not sorted then right is sorted
                min = Math.min(min, nums[mid]);
                r = mid-1;
            }
        }

        return min;
    }





    public static int findMin1(int[] nums) {
        int n = nums.length, l = 0, r = n-1;

        while(l<r) {
            int m = l + (r-l)/2;
            if (nums[m] > nums[r]) { // or if (nums[l] <= nums[m] && nums[m] > nums[r]) {
                // if right not sorted? then pivot point is somewhere in the right portion
                l = m+1;
            } else {
                // to shrink width and also to break the loop as while (l<r)
                r = m;
            }
        }
        return nums[l];
    }




    public static int findMin2(int[] nums) {
        int n = nums.length, l = 0, r = n-1;

        while(l<r) {
            int m = l + (r-l)/2;
            if (nums[m] < nums[r]) {
                r = m;
            } else {
                l = m+1;
            }
        }
        return nums[l];
    }



    public static int findMin3(int[] nums) {
        int res = nums[0], l = 0, r = nums.length-1;
        while (l<=r) {
            if (nums[l] < nums[r]) {
                res = Math.min(res, nums[l]);
                break;
            }

            int m = l + (r-l)/2;
            res = Math.min(res, nums[m]);
            if (nums[l] <= nums[m]) l = m+1;
            else r = m-1;
        }
        return res;
    }



    public static int findMin4(int[] nums) {
        int l = 0, r = nums.length-1;
        while (l<r) {
            int m = l + (r-l)/2;
            if (m>0 && nums[m-1] > nums[m]) return nums[m];
            else if (nums[l] <= nums[m] && nums[m] > nums[r]) l = m+1;
            else r = m-1;
        }
        return nums[l];
    }




    public static int findMin5(int[] nums) {
        if(nums[0]<nums[nums.length-1]){
            return nums[0];
        }
        int l = 0;
        int r = nums.length-1;
        while(r>l){
            int mid = (l+r)/2;
            if(nums[mid]>=nums[0]){
                l = mid+1;
            }else{
                r = mid;
            }
        }
        return nums[l];
    }



    public static int findMin6(int[] a) {
        int l = 0;
        int r = a.length - 1;

        while (a[l] > a[r]) {
            int m = (l + r) / 2;
            if (a[m] >= a[l]) l = m + 1;
            else r = m;
        }

        return a[l];
    }




    public static int findMin7(int[] nums) {
        int left = 0, right = nums.length - 1;

        if (nums[right] >= nums[0]) {
            return nums[0];
        }

        while (right >= left) {
            int mid = left + (right - left) / 2;

            if (nums[mid] > nums[mid + 1]) {
                return nums[mid + 1];
            }

            if (nums[mid - 1] > nums[mid]) {
                return nums[mid];
            }

            if (nums[mid] > nums[0]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return Integer.MAX_VALUE;
    }

}
