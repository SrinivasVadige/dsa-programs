package Algorithms.BinarySearch;

import java.util.Comparator;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 10 May 2025
 * @link 162. Find Peak Element <a href="https://leetcode.com/problems/find-peak-element/">LeetCode Link</a>
 * @topics Binary Search, Array
 * @companies Meta(15), Amazon(9), Google(7), Uber(4), Microsoft(3), Bloomberg(3), TikTok(2), IXL(2), Samsung(5), Accenture(5), Infosys(4), Oracle(4), Goldman Sachs(4), Apple(3), Flipkart(3), PayPal(3), eBay(3), Zepto(3)
 * @see DataStructures.BinarySearch

<pre>

 We can have a peak if the element is greater than its neighbors, so we can have multiple peaks and it's not necessary to the maxNum



         peak --------->    *       *     <------- peak
                          *   *   *   *
                        *       *      *
                     -∞                  *
                                         *   *   <------- peak
                                           *    -∞

 Given that nums[-1] and nums[n] are -∞

</pre>
 */
public class FindPeakElement {
    public static void main(String[] args) {
        int[] nums = {1,2,3,1};
        System.out.println("findPeakElement(nums) => " + findPeakElementUsingBinarySearch1(nums));
    }

    /**
     * @TimeComplexity O(log n)
     * @SpaceComplexity O(1)

      Focus on "Strictly monotonically increasing" or "Strictly monotonically decreasing"

                                              *                    *
                                          *      -∞     or     -∞      *
                                      *                                     *
                                  *                                            *
                              -∞                                                   -∞

      "-∞" is for nums[-1] and nums[n]

      If there is a "Strictly monotonically increasing" then we might face a smaller number on the right i.e a peak or the nums ends with -∞ i.e a peak
      Similarly if there is a "Strictly monotonically decreasing", then we know that nums[-1] is -∞ and nums[0] is peak

      Note that we need any peak, not necessarily the max peak

    */
    public static int findPeakElementUsingBinarySearch1(int[] nums) {
        int n=nums.length, l=0, r=n-1, m;
        while (l<=r) {
            m = l + (r-l)/2;
            // which neighbor is bigger?
            if (m > 0 && nums[m-1] > nums[m]) r=m-1; // leftNeighbor is bigger
            else if( m < n-1 && nums[m] < nums[m+1]) l=m+1; // rightNeighbor is bigger
            else return m; // so, no neighbor is bigger
        }
        return -1;
    }



    public static int findPeakElementUsingBinarySearch2(int[] nums) {
        int n = nums.length, l = 0, r = n-1;

        while(l <= r) {
            int m = l + (r-l)/2;
            if      ((m-1 == -1 || nums[m-1] < nums[m]) && (m+1 == n || nums[m] > nums[m+1])) return m;
            else if (nums[m] < nums[m+1]) l = m+1; // ---> there will be a peak
            else    r = m-1;
        }

        return l;
    }



    public int findPeakElementUsingBinarySearch3(int[] nums) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int mid = (l + r) / 2;
            if (nums[mid] > nums[mid + 1]) r = mid;
            else l = mid + 1;
        }
        return l;
    }



    public int findPeakElementUsingBinarySearch4(int[] nums) {
        return search(nums, 0, nums.length - 1);
    }

    public int search(int[] nums, int l, int r) {
        if (l == r) return l;
        int mid = (l + r) / 2;
        if (nums[mid] > nums[mid + 1]) return search(nums, l, mid);
        return search(nums, mid + 1, r);
    }


    /**
     * Note that we need any peak, not necessarily the max peak
     */
    public static int findPeakElementUsingBinarySearch5(int[] nums) {
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



    public static int findPeakElementUsingBinarySearch6(int[] arr) {
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

    public int findPeakElementUsingMaxComparator(int[] nums) {
        return IntStream.range(0, nums.length).boxed().max(Comparator.comparingInt(i->nums[i])).orElse(-1); // or .min((i, j) -> nums[j] - nums[i]).get()
    }

    public int findPeakElementUsingReduceComparator(int[] nums) {
        return IntStream.range(0, nums.length).reduce((i,j)->nums[i]>nums[j]?i:j).orElse(0);
    }

    public int findPeakElementLinearSearchFindMax1(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n-1; i++) {
            if (nums[i] > nums[i+1]) return i;
        }
        return n-1;
    }

    public int findPeakElementLinearSearchFindMax2(int[] nums) {
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
            boolean l = i == 0 || nums[i - 1] < nums[i];
            boolean r = i == n - 1 || nums[i + 1] < nums[i];
            if(l && r) return i;
        }
        return 0;
    }
    public int findPeakElementUsingRandomToGetAnyPeak(int[] nums) {
        int n=nums.length;
        while(true) {
            int i= new Random().nextInt(n);
            boolean l = i == 0 || nums[i - 1] < nums[i];
            boolean r = i == n - 1 || nums[i + 1] < nums[i];
            if(l && r) return i;
        }
    }
}
