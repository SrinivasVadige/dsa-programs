package Algorithms.MonotonicStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 14 October 2026
 * @link 496. Next Greater Element I <a href="https://leetcode.com/problems/next-greater-element-i/">LeetCode link</a>
 * @topics Mid Level, Array, Hash Table, Stack, Monotonic Stack
 * @description The next greater element of some element x in an array is the first greater element that is to the right of x in the same array.
 * You are given two distinct 0-indexed integer arrays nums1 and nums2, where nums1 is a subset of nums2.
 * For each 0 <= i < nums1.length, find the index j such that nums1[i] == nums2[j] and determine the next greater element of nums2[j] in nums2. If there is no next greater element, then the answer for this query is -1.
 * Return an array ans of length nums1.length such that ans[i] is the next greater element as described above.

Example 1:
Input: nums1 = [4,1,2], nums2 = [1,3,4,2]
Output: [-1,3,-1]
Explanation: The next greater element for each value of nums1 is as follows:
- 4 is underlined in nums2 = [1,3,4,2]. There is no next greater element, so the answer is -1.
- 1 is underlined in nums2 = [1,3,4,2]. The next greater element is 3.
- 2 is underlined in nums2 = [1,3,4,2]. There is no next greater element, so the answer is -1.


Example 2:
Input: nums1 = [2,4], nums2 = [1,2,3,4]
Output: [3,-1]
Explanation: The next greater element for each value of nums1 is as follows:
- 2 is underlined in nums2 = [1,2,3,4]. The next greater element is 3.
- 4 is underlined in nums2 = [1,2,3,4]. There is no next greater element, so the answer is -1.

 */
public class NextGreaterElement1 {
    public static void main(String[] args) {
        int[] nums1 = {4,1,2}, nums2 = {1,3,4,2};

        System.out.printf("nextGreaterElement Using BruteForce: %s%n", Arrays.toString(nextGreaterElementUsingBruteForce(nums1, nums2)));
        System.out.printf("nextGreaterElement Using BetterBruteForce: %s%n", Arrays.toString(nextGreaterElementUsingBetterBruteForce(nums1, nums2)));
        System.out.printf("nextLargerElement Using MonotonicallyDecreasingStack RTL ByValues: %s%n", Arrays.toString(nextLargerElementUsingMonotonicallyDecreasingStack_RTL_ByValues(nums1, nums2)));
        System.out.printf("nextLargerElement Using MonotonicallyDecreasingStack RTL ByIndexes: %s%n", Arrays.toString(nextLargerElementUsingMonotonicallyDecreasingStack_RTL_ByIndexes(nums1, nums2)));
        System.out.printf("nextLargerElement Using MonotonicallyDecreasingStack LTR ByIndexes: %s%n", Arrays.toString(nextLargerElementUsingMonotonicallyDecreasingStack_LTR_ByIndexes(nums1, nums2)));
    }


    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(1)
     */
    public static int[] nextGreaterElementUsingBruteForce(int[] nums1, int[] nums2) {
        int[] res = new int[nums1.length];
        int j;

        for (int i = 0; i < nums1.length; i++) {
            boolean found = false;
            for (j = 0; j < nums2.length; j++) {
                if (found && nums2[j] > nums1[i]) {
                    res[i] = nums2[j];
                    break;
                }

                if (nums2[j] == nums1[i]) {
                    found = true;
                }
            }
            if (j == nums2.length) {
                res[i] = -1;
            }
        }

        return res;
    }



    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(n)
     */
    public static int[] nextGreaterElementUsingBetterBruteForce(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> hash = new HashMap<>();
        for (int i = 0; i < nums2.length; i++) {
            hash.put(nums2[i], i);
        }

        int[] res = new int[nums1.length];
        int j;

        for (int i = 0; i < nums1.length; i++) {
            for (j = hash.get(nums1[i]) + 1; j < nums2.length; j++) {
                if (nums1[i] < nums2[j]) {
                    res[i] = nums2[j];
                    break;
                }
            }
            if (j == nums2.length) {
                res[i] = -1;
            }
        }

        return res;
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int[] nextLargerElementUsingMonotonicallyDecreasingStack_RTL_ByValues(int[] nums1, int[] nums2) {
        int n2 = nums2.length;
        Map<Integer, Integer> numToIndex = new HashMap<>();
        for(int i=0; i<n2; i++) numToIndex.put(nums2[i], i);

        int[] nge = new int[n2];
        Stack<Integer> stack = new Stack<>();

        for (int i=n2-1; i>=0; i--) {
            while(!stack.isEmpty() && stack.peek() <= nums2[i]) stack.pop();
            if (stack.isEmpty()) nge[i] = -1;
            else nge[i] = stack.peek();
            stack.push(nums2[i]);
        }

        int n1 = nums1.length;
        int[] res = new int[n1];
        for(int i=0; i<n1; i++) {
            res[i] = nge[numToIndex.get(nums1[i])];
        }
        return res;
    }



    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int[] nextLargerElementUsingMonotonicallyDecreasingStack_RTL_ByIndexes(int[] nums1, int[] nums2) {
        int n2 = nums2.length;
        Map<Integer, Integer> numToIndex = new HashMap<>();
        for(int i=0; i<n2; i++) numToIndex.put(nums2[i], i);

        int[] nge = new int[n2];
        Stack<Integer> indexStack = new Stack<>();

        for (int i=n2-1; i>=0; i--) {
            while(!indexStack.isEmpty() && nums2[indexStack.peek()] <= nums2[i]) indexStack.pop();
            if (indexStack.isEmpty()) nge[i] = -1;
            else nge[i] = nums2[indexStack.peek()];
            indexStack.push(i);
        }

        int n1 = nums1.length;
        int[] res = new int[n1];
        for(int i=0; i<n1; i++) {
            res[i] = nge[numToIndex.get(nums1[i])];
        }
        return res;
    }



    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int[] nextLargerElementUsingMonotonicallyDecreasingStack_LTR_ByIndexes(int[] nums1, int[] nums2) {
        int n2 = nums2.length;
        Map<Integer, Integer> numToIndex = new HashMap<>();
        for(int i=0; i<n2; i++) numToIndex.put(nums2[i], i);

        int[] nge = new int[n2];
        Arrays.fill(nge, -1);
        Stack<Integer> indexStack = new Stack<>();

        for (int i=0; i<n2; i++) {
            while(!indexStack.isEmpty() && nums2[indexStack.peek()] < nums2[i]) {
                int prevI = indexStack.pop();
                nge[prevI] = nums2[i];
            }
            indexStack.push(i);
        }

        int n1 = nums1.length;
        int[] res = new int[n1];
        for(int i=0; i<n1; i++) {
            res[i] = nge[numToIndex.get(nums1[i])];
        }
        return res;
    }

}
