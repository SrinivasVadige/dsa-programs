package Algorithms.BinarySearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 04 Feb 2025
 * @link 4. Median of Two Sorted Arrays <a href="https://leetcode.com/problems/median-of-two-sorted-arrays/">LeetCode Link</a>
 * @topics Mid Level, Array, Binary Search, Divide and Conquer
 * @companies Google(32), Amazon(19), Meta(9), Bloomberg(8), Microsoft(6), Goldman Sachs(6), Rippling(3), Apple(2), Udemy(10), TCS(4), Oracle(2), Capgemini(2), Wix(10), Yandex(8), Accenture(6), Adobe(5), Walmart Labs(5), Flipkart(3), TikTok(3), Zoho(3), Autodesk(3), Cognizant(3)
 * @see Algorithms.BinarySearch.KthElementOfTwoSortedArrays
 */
public class MedianOfTwoSortedArrays {
    public static void main(String[] args) {

        int[] nums1 = {1, 3};
        int[] nums2 = {2};
        System.out.println("findMedianSortedArrays => " + findMedianSortedArrays1(nums1, nums2));
        System.out.println("findMedianSortedArrays Using KthElement => " + findMedianSortedArraysUsingKthElement1(nums1, nums2));
        System.out.println("findMedianSortedArrays using linear => " + findMedianSortedArraysUsingLinear1(nums1, nums2));
        System.out.println("findMedianSortedArrays using sort => " + findMedianSortedArraysUsingArraySort(nums1, nums2));
    }

    /**
     * @TimeComplexity O(log(min(m,n)))
     * @SpaceComplexity O(1)


      EVEN CASE SCENARIO
      ------------------
      A1 = {2, 4, 9, 12}
      A2 = {5, 6, 8, 13}

      Make them two halves like
      A1 = l1{2, 4} r1{9, 12}
      A2 = l2{5, 6} r2{8, 13}

      In even mixed array we need middle two elements i.e e1 and e2 as (e1 + e2)/2 is median
      e1 = max(l1, l2)
      e2 = min(r1, r2)


      ODD CASE SCENARIO
      ------------------
      A1 = {2, 4, 9, 12}
      A2 = {5, 6, 8}

      Make them two halves like
      A1 = l1{2, 4} r1{9, 12}
      A2 = l2{5} r2{6, 8}

      In odd mixed array we need middle one element i.e e1 and itself is median
      e1 = min(r1, r2)

      But how to find the cut? ---> using binary search of smaller array
      l1 <= r2 && l2 <= r1 check this condition in all the cuts to get valid cut
      cause we already know that l1<=r1 and l2<=r2, so we have to check l1 <= r2 && l2 <= r1

      Apply binary search in the smaller array i.e., nums1 ---> TimeComplexity = O(log(min(m,n)))
        ---> cause smaller array never contains more than half the number of total eles
        ---> so binary search don't accidentally analyze too many eles



        nums1 = [2, 3, 6, 15], nums2 = [1, 3, 4, 7, 10, 12]

                  |
        1 2 3 3 4 | 6 7 10 12 15 ----> sorted array with 10 eles
                  |

        which is same as

           l1    |  r1
        2, 3     |  6, 15
        1, 3, 4  |  7, 10, 12
              l2 |  r2

        or

            l1    r1
               |
        [2, 3, |  6, 15]
               |

               l2    r2
                  |
        [1, 3, 4, |  7, 10, 12]
                  |


     So, there will be definitely one symmetry - to cut

     and that's why we get max(l1, l2) and min(r1, r2)

     👉 low and high represent how many elements you can pick from nums1, not indices.


     */
    public static double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        if(nums1.length > nums2.length) { // make num1 as min length array.
            return findMedianSortedArrays1(nums2,nums1); // no recursion here, we're just swapping to make nums1 smaller
        }
        // nums1 is smaller and num2 is greater. Now apply binary search in the smaller array i.e., nums1
        int n1 = nums1.length, n2 = nums2.length;
        final int N = n1+n2;
        int low = 0; // start
        int high = n1; // end

        while (low<=high){

            int cut1 = low + (high - low)/2; // in nums1 the cut is between cut1-1 (l1) and cut1 (r1) index
            int cut2 = N/2 - cut1; // cut1 + cut2 = N/2, so cut2 = remaining length for nums2 cut (in left side)

            int l1 = cut1==0 ? Integer.MIN_VALUE : nums1[cut1-1]; // left half lastI in nums1
            int r1 = cut1==n1 ? Integer.MAX_VALUE : nums1[cut1]; // right half firstI in nums1
            int l2 = cut2==0 ? Integer.MIN_VALUE : nums2[cut2-1]; // left half lastI in nums2
            int r2 = cut2==n2 ? Integer.MAX_VALUE : nums2[cut2]; // right half firstI in nums2

            // check if cut is valid - cross X validation
            if(l1 <= r2 && l2 <= r1){
                if(N%2 != 0) return Math.min(r1,r2); // if we use "cut2 = (N+1)/2 - cut1" then we can return max(l1,l2)
                else return (Math.max(l1,l2) + Math.min(r1,r2))/2.0;
            } else if (l1>r2) {
                high = cut1-1;
            } else{
                low = cut1+1;
            }
        }
        return 0.0; // to avoid error
    }






    /**
     * @TimeComplexity O(log(min(m,n)))
     * @SpaceComplexity O(1)
     */
    public static double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int[] A = nums1.length <= nums2.length ? nums1 : nums2; // A must be smaller
        int[] B = nums1.length <= nums2.length ? nums2 : nums1;

        int na = A.length, nb = B.length, total = na+nb, half = total/2;
        int l = -1, r = na-1;

        while(true){
            int i = (l+r)/2; // A cut
            int j = half - i - 2; // B cut

            int Aleft = i>=0 ? A[i] : Integer.MIN_VALUE;
            int Aright = i+1<na ? A[i+1] : Integer.MAX_VALUE;
            int Bleft = j>=0 ? B[j] : Integer.MIN_VALUE;
            int Bright = j+1<nb ? B[j+1] : Integer.MAX_VALUE;

            if (Aleft <= Bright && Bleft <= Aright) {
                if (total % 2 == 1) return Math.min(Aright, Bright);
                return (Math.max(Aleft, Bleft) + Math.min(Aright, Bright)) / 2.0;
            } else if (Aleft > Bright) {
                r = i - 1;
            } else {
                l = i + 1;
            }
        }
    }






    /**
     * @TimeComplexity O(log(min(m,n)))
     * @SpaceComplexity O(1)

     mid1 = elements from nums1

     mid2 = k - mid1 ---> from nums2

     Now we must ensure:
     mid2 <= nums2.length

     ⚡ That directly gives:
     k - mid1 <= nums2.length
     => mid1 >= k - nums2.length

     So, low = Math.max(0, k - nums2.length); ---> we know that nums2 > nums1 ---> if k-n2 is -ve then we know that mid1 must be 0

     Why NOT nums1 in calculating low?
     Because:
     low is the minimum valid value of mid1
     That minimum depends on how many elements nums2 can supply
     👉 If nums2 is small, nums1 must compensate more → mid1 must be larger


     */
    public static double findMedianSortedArraysUsingKthElement1(int[] nums1, int[] nums2) {
        int totalLength = nums1.length + nums2.length;
        if (totalLength % 2 == 1) {
            return findKthElement(nums1, nums2, totalLength / 2 + 1);
        } else {
            return (findKthElement(nums1, nums2, totalLength / 2) + findKthElement(nums1, nums2, totalLength / 2 + 1)) / 2.0;
        }
    }

    public static int findKthElement(int[] nums1, int[] nums2, int k) {
        if (nums1.length > nums2.length) {
            return findKthElement(nums2, nums1, k);
        }
        int low = Math.max(0, k - nums2.length); //  ---> Eg: low = k - n2 = 3 - 2 = 1
        int high = Math.min(k, nums1.length); // you can pick max k eles or all eles from nums1
        while (low <= high) {
            int cut1 = (low + high) / 2; // elements from nums1
            int cut2 = k - cut1; // cut1 + cut2 = k
            int l1 = cut1 == 0 ? Integer.MIN_VALUE : nums1[cut1 - 1];
            int r1 = cut1 == nums1.length ? Integer.MAX_VALUE : nums1[cut1];
            int l2 = cut2 == 0 ? Integer.MIN_VALUE : nums2[cut2 - 1];
            int r2 = cut2 == nums2.length ? Integer.MAX_VALUE : nums2[cut2];
            if (l1 <= r2 && l2 <= r1)
                return Math.max(l1, l2);
            else if (l1 > r2)
                high = cut1 - 1;
            else
                low = cut1 + 1;
        }
        return 0;
    }







    /**
     * @TimeComplexity O(log(min(m,n)))
     * @SpaceComplexity O(1)
     */
    public double findMedianSortedArraysUsingKthElement2(int[] A, int[] B) {
        int na = A.length, nb = B.length;
        int n = na + nb;
        if ((na + nb) % 2 == 1) {
            return solve(A, B, n/2, 0, na-1, 0, nb-1);
        } else {
            return (double) (solve(A, B, n/2, 0, na-1, 0, nb-1) + solve(A, B, n/2-1, 0, na-1, 0, nb-1)) / 2;
        }
    }

    public int solve(int[] A, int[] B, int k, int aStart, int aEnd, int bStart, int bEnd) {
        // If the segment of on array is empty, it means we have passed all
        // its element, just return the corresponding element in the other array.
        if (aEnd < aStart) {
            return B[k - aStart];
        }
        if (bEnd < bStart) {
            return A[k - bStart];
        }

        // Get the middle indexes and middle values of A and B.
        int aIndex = (aStart + aEnd) / 2, bIndex = (bStart + bEnd) / 2;
        int aValue = A[aIndex], bValue = B[bIndex];

        // If k is in the right half of A + B, remove the smaller left half.
        if (aIndex + bIndex < k) {
            if (aValue > bValue) {
                return solve(A, B, k, aStart, aEnd, bIndex + 1, bEnd);
            } else {
                return solve(A, B, k, aIndex + 1, aEnd, bStart, bEnd);
            }
        }
        // Otherwise, remove the larger right half.
        else {
            if (aValue > bValue) {
                return solve(A, B, k, aStart, aIndex - 1, bStart, bEnd);
            } else {
                return solve(A, B, k, aStart, aEnd, bStart, bIndex - 1);
            }
        }
    }






    /**
     * @TimeComplexity O(m+n)
     * @SpaceComplexity O(m+n)
     * MergeSort approach
     */
    public static double findMedianSortedArraysUsingLinear1(int[] nums1, int[] nums2) {
        List<Integer> list = new ArrayList<>();
        int m = nums1.length, n = nums2.length, i = 0, j = 0, midI = (m+n-1)/2;

        while (i<m && j<n) {
            if (nums1[i] < nums2[j]) list.add(nums1[i++]);
            else list.add(nums2[j++]);
        }
        while (i < m) list.add(nums1[i++]);
        while (j < n) list.add(nums2[j++]);

        if (list.size() % 2 == 1) return list.get(midI);
        return (list.get(midI) + list.get(midI+1))/2.00;
    }




    /**
     * @TimeComplexity O(m+n)
     * @SpaceComplexity O(m+n)
     * MergeSort approach
     */
    public static double findMedianSortedArraysUsingLinear2(int[] nums1, int[] nums2) {
        int n1=nums1.length,n2=nums2.length,i=0,j=0,count=0,mid=(n1+n2)/2;
        int[] nums=new int[n1+n2];
        while(count<=mid){
            if(i==n1 && j<n2) nums[count++]=nums2[j++];
            else if(j==n2 && i<n1) nums[count++]=nums1[i++];
            else if(nums1[i]<nums2[j]){
                nums[count++]=nums1[i++];
            }else nums[count++]=nums2[j++];
        }
        if((n1+n2)%2!=0) return nums[mid];
        return (nums[mid]+nums[mid-1])/2.0;
    }







    /**
     * @TimeComplexity O(m+n)
     * @SpaceComplexity O(1)
     * MergeSort approach
     * same as {@link #findMedianSortedArraysUsingLinear4}
     */
    public static double findMedianSortedArraysUsingLinear3(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length, i = 0, j = 0, midI = (m+n-1)/2;

        int idx = 0;
        while((i<m || j<n) && idx < midI) {
            if (i<m && j<n && nums1[i] < nums2[j]) i++;
            else if (i<m && j<n && nums1[i] > nums2[j]) j++;
            else if (i < m) i++;
            else j++;
            idx++;
        }

        Integer one = null, two = null;
        while (i < m || j<n) {
            if (one != null && two != null || one != null && (m+n)%2==1) break;

            int num;
            if (i<m && j <n) num = nums1[i] < nums2[j] ? nums1[i++] : nums2[j++];
            else if (i < m) num = nums1[i++];
            else num = nums2[j++];
            if (one == null) one = num; else two = num;
        }

        if (one != null && two != null) return (one+two)/2.0;
        return one;
    }








    private static int p1 = 0, p2 = 0; // or i, j pointers
    /**
     * @TimeComplexity O(m+n)
     * @SpaceComplexity O(1)
     * MergeSort approach
     * same as {@link #findMedianSortedArraysUsingLinear3}
     */
    public static double findMedianSortedArraysUsingLinear4(int[] nums1, int[] nums2) {
        p1=p2=0;
        int m = nums1.length, n = nums2.length;
        boolean isEven = (m + n) % 2 == 0;
        for (int i = 0; i < (m+n)/2 - (isEven?1:0); i++) { // increment p1, p2 pointers
            getMin(nums1, nums2);
        }
        if (isEven) {
            return (double) (getMin(nums1, nums2) + getMin(nums1, nums2)) / 2;
        } else {
            return getMin(nums1, nums2);
        }
    }
    private static int getMin(int[] nums1, int[] nums2) {
        if (p1 < nums1.length && p2 < nums2.length) {
            return nums1[p1] < nums2[p2] ? nums1[p1++] : nums2[p2++];
        } else if (p1 < nums1.length) {
            return nums1[p1++];
        } else if (p2 < nums2.length) {
            return nums2[p2++];
        }
        return -1;
    }











    /**
     * @TimeComplexity O(t log t), where t = (m+n)
     * @SpaceComplexity O(t)
     */
    public static double findMedianSortedArraysUsingArraySort(int[] nums1, int[] nums2) {
        int[] nums = new int[nums1.length+nums2.length];
        double median;
        for(int i=0; i<nums1.length; i++) nums[i] = nums1[i];
        for(int i=nums1.length, j=0; i<nums.length; i++, j++) nums[i] = nums2[j];
        Arrays.sort(nums);
        int len = nums.length;
        if(len%2==0) median = (double) (nums[(len/2)-1] + nums[(len/2)])/2; // or /2.0
        else median = nums[len/2];
        return median;
    }

    /**
     * @TimeComplexity O(t log t), where t = (m+n)
     * @SpaceComplexity O(t)
     */
    public static double findMedianSortedArraysUsingListSort(int[] nums1, int[] nums2) {
        double d = 0.0;
        List<Integer> lst = new ArrayList<>();
        for (int i: nums1) lst.add(i);
        for (int i: nums2) lst.add(i);
        Collections.sort(lst);
        if (lst.size()%2==1) d = lst.get(lst.size()/2);
        else d = (lst.get((lst.size()/2) -1) + lst.get(lst.size()/2))/2;
        return d;
    }

    /**
     * @TimeComplexity O(t log t), where t = (m+n)
     * @SpaceComplexity O(t)
     */
    public static double findMedianSortedArraysUsingPq(int[] nums1, int[] nums2) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i: nums1) pq.add(i);
        for (int i: nums2) pq.add(i);
        int mid = pq.size()/2;
        boolean isEven = pq.size()%2==0;
        for (int i=0; i< (isEven? (mid-1):mid); i++) pq.poll();
        return isEven? (pq.poll() + pq.poll())/2.0 : pq.poll();
    }
}
