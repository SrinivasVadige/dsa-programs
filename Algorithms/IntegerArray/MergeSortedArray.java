package Algorithms.IntegerArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 28 May 2025
 * @link 88. Merge Sorted Array https://leetcode.com/problems/merge-sorted-array/
 * This is same as LinkedList merge sorted Lists -> {@link Algorithms.LinkedListAlgos.MergeTwoSortedLists}
 *
 * TOPICS: Array, Two Pointers, In-place Merge
 */
public class MergeSortedArray {

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int m = 3;
        int[] nums2 = {2, 5, 6};
        int n = 3;

        System.out.println("Before merging: " + Arrays.toString(nums1));
        merge(nums1, m, nums2, n);
        System.out.println("After merging: " + Arrays.toString(nums1));
    }


    /**
     * @TimeComplexity O(m+n)
     * @SpaceComplexity O(1) - In-place merge
     * Start from the right side of both arrays and fill nums1 from the right side ---> Biggest element first
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;

        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }

        // Copy any remaining elements from nums2. And it's sure that nums1 eles are all already in place
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }
    }


    /**
     * @TimeComplexity O(m+n)
     * @SpaceComplexity O(1) - In-place merge
     */
    public void mergeMyApproach(int[] nums1, int m, int[] nums2, int n) {
        int i=m-1, j=n-1, k=m+n-1;
        for(; k>=0 && i>=0 && j>=0; k--) {
            // if(nums1[i]==nums2[j]) {
            //     nums1[k--]=nums1[i--];
            //     nums1[k]=nums2[j--];
            // } else if (nums1[i]>nums2[j]) nums1[k]=nums1[i--];
            // else nums1[k]=nums2[j--];
            // or
            if (nums1[i]>nums2[j]) nums1[k]=nums1[i--];
            else nums1[k]=nums2[j--];
        }
        while(i>=0) nums1[k--] = nums1[i--];
        while(j>=0) nums1[k--] = nums2[j--];
    }





    public static void merge2(int[] nums1, int m, int[] nums2, int n) {
        int i = m + n - 1;

        while (m > 0 && n > 0) {
            if (nums1[m - 1] > nums2[n - 1]) {
                nums1[i--] = nums1[m - 1];
                m--;
            } else {
                nums1[i--] = nums2[n - 1];
                n--;
            }
        }
        // If there are remaining elements in nums2, copy them. And it's sure that nums1 eles are all already in place
        while (n > 0) {
            nums1[i--] = nums2[n - 1];
            n--;
        }
    }



    public static void merge3(int[] nums1, int m, int[] nums2, int n) {
        int i = m + n - 1;
        m--;
        n--; // Adjust indices to be zero-based
        while (m >= 0 && n >= 0) {
            if (nums1[m] > nums2[n]) {
                nums1[i--] = nums1[m--];
            } else {
                nums1[i--] = nums2[n--];
            }
        }
        // If there are remaining elements in nums2, copy them. And it's sure that nums1 eles are all already in place
        while (n >= 0) nums1[i--] = nums2[n--];
    }





    /**
     * @TimeComplexity O(m+n)
     * @SpaceComplexity O(1) - In-place merge
     */
    public void merge4(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;

        while (j >= 0) {
            if (i >= 0 && nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
    }




    /**
     * @TimeComplexity O(m+n)
     * @SpaceComplexity O(m+n) - Using extra space for merged array
     */
    public void mergeUsingExtraSpaceIntArray(int[] nums1, int m, int[] nums2, int n) {
        int[] merged = new int[m + n];
        int i = 0, j = 0, k = 0;

        while (i < m && j < n) {
            if (nums1[i] <= nums2[j]) {
                merged[k++] = nums1[i++];
            } else {
                merged[k++] = nums2[j++];
            }
        }
        while (i < m) merged[k++] = nums1[i++];
        while (j < n) merged[k++] = nums2[j++];

        System.arraycopy(merged, 0, nums1, 0, m + n); // ✅ Copies elements into nums1 (reference unchanged) --- JNI native method
        // nums1 = Arrays.copyOf(merged, m+n); --- loose reference
        // nums1 = Arrays.copyOfRange(merged, 0, m+n); --- loose reference
        // nums1 = merged.clone(); --- loose reference
        // nums1 = merged; --- loose reference
        // or for-loop and copy each element
    }




    /**
     * @TimeComplexity O(m+n)
     * @SpaceComplexity O(m+n) - Using extra space for merged array
     */
    public void mergeUsingExtraSpaceList(int[] nums1, int m, int[] nums2, int n) {
        List<Integer> merged = new ArrayList<>(m + n);
        int i = 0, j = 0;
        while (i < m && j < n) {
            // if(nums1[i] == nums2[j]) {
            //     lst.add(nums1[i++]);
            //     lst.add(nums2[j++]);
            // } else if (nums1[i] < nums2[j]) lst.add(nums1[i++]);
            // else lst.add(nums2[j++]);
            // or
            if (nums1[i] <= nums2[j]) merged.add(nums1[i++]);
            else merged.add(nums2[j++]);
        }
        while (i < m) merged.add(nums1[i++]);
        while (j < n) merged.add(nums2[j++]);

        for (int k = 0; k<m+n; k++) {
            nums1[k] = merged.get(k);
        }
    }





    /**
     * @TimeComplexity O(m+n)
     * @SpaceComplexity O(m+n) - Using extra space for merged array
     */
    public void mergeUsingExtraSpaceList2(int[] nums1, int m, int[] nums2, int n) {
        // if(n==0 || (m==0 && n==0)) return;
        // else if(m==0){
        //     for(int i=0; i<n; i++) nums1[i]=nums2[i];
        //     return;
        // }

        List<Integer> lst = new ArrayList<>();
        int i=0, j=0;

        while(i<m || j<n) {
            int num1 = Integer.MAX_VALUE, num2 = Integer.MAX_VALUE;
            if(i<m) num1=nums1[i];
            if(j<n) num2=nums2[j];

            if(num1 == num2) {
                lst.add(num1);
                lst.add(num2);
                i++;
                j++;
            } else if (num1 < num2) {
                lst.add(num1);
                i++;
            }
            else {
                lst.add(num2);
                j++;
            }
        }

        for(i=0; i<m+n; i++) {
            nums1[i] = lst.get(i);
        }
    }
}