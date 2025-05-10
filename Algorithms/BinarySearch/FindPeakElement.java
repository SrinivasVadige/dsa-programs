package Algorithms.BinarySearch;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 10 May 2025
 * @link https://leetcode.com/problems/find-peak-element/
 *
 * We can have a peak if the element is greater than its neighbors, so we can have multiple peaks and it's not necessary to the maxNum
 *
 *
 *
 *         peak --------->    *       *     <------- peak
 *                          *   *   *   *
 *                        *       *      *
 *                     -∞                  *
 *                                         *   *   <------- peak
 *                                           *    -∞
 *
 * Given that nums[-1] and nums[n] are -∞
 *
 *
 */
public class FindPeakElement {
    public static void main(String[] args) {
        int[] nums = {1,2,3,1};
        System.out.println("findPeakElement(nums) => " + findPeakElement(nums));
    }

    /**
     * @TimeComplexity O(log n)
     * @SpaceComplexity O(1)
     *
     * Focus on "Strictly monotonically increasing" or "Strictly monotonically decreasing"
     *
     *                                         *                    *
     *                                     *      -∞     or     -∞      *
     *                                 *                                     *
     *                             *                                            *
     *                         -∞                                                   -∞
     *
     * "-∞" is for nums[-1] and nums[n]
     *
     * If there is a "Strictly monotonically increasing" then we might face a smaller number on the right i.e a peak or the nums ends with -∞ i.e a peak
     * Similarly if there is a "Strictly monotonically decreasing", then we know that nums[-1] is -∞ and nums[0] is peak
     *
     * Note that we need any peak, not necessarily the max peak
     *
    */
    public static int findPeakElement(int[] nums) {
        int n=nums.length, l=0, r=n-1, mid;
        while (l<=r) {
            mid = l + (r-l)/2;
            // which neighbor is bigger?
            if (mid > 0 && nums[mid] < nums[mid-1]) r=mid-1; // leftNeighbor is bigger
            else if( mid < n-1 && nums[mid] < nums[mid+1]) l=mid+1; // rightNeighbor is bigger
            else return mid; // so, no neighbor is bigger
        }
        return -1;
    }


    /**
     * Note that we need any peak, not necessarily the max peak
     */
    public static int findPeakElement2(int[] nums) {
        int start = 0, end = nums.length - 1;
        while (start < end) {
            // * | *       --->  where '|' is mid, left '*' is leftNeighbor, right '*' is rightNeighbor
            int mid = start + (end - start) / 2;
            if (nums[mid] < nums[mid + 1]) { // rightNeighbor is bigger
                start = mid + 1;
            } else { // rightNeighbor is smaller or rightNeighbor is same or leftNeighbor is bigger
                end = mid;
            }
        }
        return start;
    }



    public static int findPeakElement3(int[] arr) {
        int n = arr.length;
        if(n==1) return 0;
        if(arr[0]>arr[1]) return 0;
        if(arr[n-1]>arr[n-2]) return n-1;

        int l=1, r=n-2, mid;
        while(l<=r) {
            mid = l + (r-l)/2;
            if(arr[mid]>arr[mid-1] && arr[mid]>arr[mid+1]) return mid;
            else if(arr[mid]>arr[mid-1]) l=mid+1;
            else r=mid-1;
        }
        return -1;
    }










    public int findPeakElementUsingSortFindMax(int[] nums) {
        return IntStream.range(0, nums.length).boxed().sorted((i,j)->nums[j]-nums[i]).findFirst().get();
    }

    public int findPeakElementLinearSearchFindMax(int[] nums) {
        int maxI=0, max=Integer.MIN_VALUE;
        for(int i=0; i<nums.length; i++) {
            if(max<nums[i]) {
                max = nums[i];
                maxI=i;
            }
        }
        return maxI;
    }
    public int findPeakElementLinearToGetAnyPeak(int[] nums) {
        int n=nums.length;
        for(int i=0; i<n; i++) {
            boolean l = i==0? true: nums[i-1]<nums[i];
            boolean r = i==n-1?true: nums[i+1]<nums[i];
            if(l && r) return i;
        }
        return 0;
    }
    public int findPeakElementUsingRandomToGetAnyPeak(int[] nums) {
        int n=nums.length;
        boolean[] visited = new boolean[n];
        while(true) {
            int i= new Random().nextInt(n);
            if(visited[i]) continue;
            boolean l = i==0? true: nums[i-1]<nums[i];
            boolean r = i==n-1?true: nums[i+1]<nums[i];
            if(l && r) return i;
        }
    }
}
