package Algorithms.BinarySearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 04 Feb 2025
 */
public class MedianOfTwoSortedArrays {
    public static void main(String[] args) {

        int[] nums1 = {1, 3};
        int[] nums2 = {2};
        System.out.println("findMedianSortedArrays(nums1, nums2) => " + findMedianSortedArrays(nums1, nums2));
        System.out.println("findMedianSortedArraysUsingKthElement(nums1, nums2) => " + findMedianSortedArraysUsingKthElement(nums1, nums2));
        System.out.println("findMedianSortedArrays2(nums1, nums2) => " + findMedianSortedArrays2(nums1, nums2));
        System.out.println("findMedianSortedArraysUsingArraySort(nums1, nums2) => " + findMedianSortedArraysUsingArraySort(nums1, nums2));
        System.out.println("findMedianSortedArraysUsingListSort(nums1, nums2) => " + findMedianSortedArraysUsingListSort(nums1, nums2));
        System.out.println("findMedianSortedArraysUsingPq(nums1, nums2) => " + findMedianSortedArraysUsingPq(nums1, nums2));
    }

    /**
     * EVEN CASE SCENARIO
     * ------------------
     * A1 = {2, 4, 9, 12}
     * A2 = {5, 6, 8, 13}
     *
     * Make then two halves like
     * A1 = l1{2, 4} r1{9, 12}
     * A2 = l2{5, 6} r2{8, 13}
     *
     * In even mixed array we need middle two elements i.e e1 and e2 as (e1 + e2)/2 is median
     * e1 = max(l1, l2)
     * e2 = min(r1, r2)
     *
     *
     * ODD CASE SCENARIO
     * ------------------
     * A1 = {2, 4, 9, 12}
     * A2 = {5, 6, 8}
     *
     * Make then two halves like
     * A1 = l1{2, 4} r1{9, 12}
     * A2 = l2{5} r2{6, 8}
     *
     * In odd mixed array we need middle one element i.e e1 and itself is median
     * e1 = min(r1, r2)
     *
     * But how to find the cut? --> using binary search of smaller array
     * l1 <= r2 && l2 <= r1 check this condition in all the cuts to get valid cut
     *
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // make num1 as min length array.
        // call same function, and change the params
        // if nums1 was greater and nums2 was smaller
        // so because we swapped these two, now nums1 is smaller and
        // nums2 is greater.
        if(nums1.length > nums2.length){
            return findMedianSortedArrays(nums2,nums1); // no recursion here, we're just swapping to make nums1 smaller
        }
        // nums1 is smaller and num2 is greater.
        // Now apply binary search in smaller array i.e nums1
        int n1 = nums1.length;
        int n2 = nums2.length;
        final int N = n1+n2;
        int start = 0;
        int end = n1;
        while (start<=end){
            // mid -> cut1
            int cut1 = start + (end - start)/2;
            // (n1 + n2)/2 - length of cut1
            int cut2 = N/2 - cut1;
            // l1,l2, r1 and r2.
            int l1 = (cut1==0)?Integer.MIN_VALUE:nums1[cut1-1];
            int l2 = (cut2==0)?Integer.MIN_VALUE:nums2[cut2-1];
            int r1 = (cut1==n1)?Integer.MAX_VALUE:nums1[cut1];
            int r2 = (cut2==n2)?Integer.MAX_VALUE:nums2[cut2];

            // check if cut is valid
            if(l1 <= r2 && l2 <= r1){
                // if N is odd, only one median
                if(N%2!=0){
                    return (double) Math.min(r1,r2);
                }
                // else if N is even, two medians, so take average
                else {
                    return ((Math.max(l1,l2) + Math.min(r1,r2))/2.0);
                }
            } else if (l1>r2) {
                end = cut1-1;
            } else{
                start = cut1+1;
            }
        }
        return 0.0; // to avoid error
    }

    public static double findMedianSortedArraysUsingKthElement(int[] nums1, int[] nums2) {
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
        int low = Math.max(0, k - nums2.length);
        int high = Math.min(k, nums1.length);
        while (low <= high) {
            int mid1 = (low + high) / 2;
            int mid2 = k - mid1;
            int l1 = mid1 == 0 ? Integer.MIN_VALUE : nums1[mid1 - 1];
            int l2 = mid2 == 0 ? Integer.MIN_VALUE : nums2[mid2 - 1];
            int r1 = mid1 == nums1.length ? Integer.MAX_VALUE : nums1[mid1];
            int r2 = mid2 == nums2.length ? Integer.MAX_VALUE : nums2[mid2];
            if (l1 <= r2 && l2 <= r1)
                return Math.max(l1, l2);
            else if (l1 > r2)
                high = mid1 - 1;
            else
                low = mid1 + 1;
        }
        return 0;
    }


    public static double findMedianSortedArrays2(int[] nums1, int[] nums2) {
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
