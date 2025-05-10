package Algorithms.HeapAlgos;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 09 May 2025
 * @link https://leetcode.com/problems/maximum-subsequence-score/
 *
 * Here we can use backtracking but the time complexity will be O(2^n).
 */
public class MaximumSubsequenceScore {
    public static void main(String[] args) {
        int[] nums1 = {1,3,3,2}, nums2 = {2,1,3,4};
        int k = 3;
        System.out.println("maxScore(nums1, nums2, k) => " + maxScore(nums1, nums2, k));
    }

    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(n) int[n][2]
     */
    public static long maxScore(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        long maxScore = 0, sum = 0; // Use long to handle large values
        int[][] pairs = new int[n][2];
        for (int i = 0; i < n; i++) pairs[i] = new int[]{nums1[i], nums2[i]};
        Arrays.sort(pairs, (a, b) -> b[1] - a[1]); // Sort by nums2 in descending order

        /**
         * before sort
         * nums1 = [1,3,3,2],
         * nums2 = [2,1,3,4]
         *
         * after sort
         * nums1 = [3,3,2,1],
         * nums2 = [4,3,2,1]
         *
         * NOTE:
         * When currNum2=2, then the currNum1=2 --> after adding currNum1 in heap it looks like --> [2,3,3]
         * When currNum2=1, then the currNum1=1 --> after adding currNum1 in heap it looks like --> [1,2,3,3]
         * nums1MinHeap.size()>k, so pop it
         * here we pop the the smallest num1 form num1MinHeap --> although smallest num1 == currNum1 == 1, we pop it
         * But as per given problem, the indices of nums1 & nums2 must be same.
         * As this currNum2=1 case, the score is less than the previous calculated scores because currNum1 is smallest. So, don't worry about this case
         */

        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // Min-heap to maintain top k elements for nums1
        for (int[] pair : pairs) { // [currNum1 - 0, currNum2 - 1]
            sum += pair[0];
            minHeap.offer(pair[0]);
            if (minHeap.size() > k) sum -= minHeap.poll(); // Remove smallest element if size exceeds k -- currNum1 is present in sum. so, we can calculate res using currNum2
            if (minHeap.size() == k) maxScore = Math.max(maxScore, sum * pair[1]); // when nums1Size==k, then pair[1] i.e currNum2 is the smallest of all kNums2 eles
        }
        return maxScore;
    }


    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(n) - int[n]
     */
    public long maxScoreMyApproach(int[] nums1, int[] nums2, int k) {
        Integer[] indices = IntStream.range(0, nums1.length).boxed().toArray(Integer[]::new);
        Arrays.sort(indices, (i1, i2)->nums2[i2]-nums2[i1]);
        PriorityQueue<Integer> pq = new PriorityQueue<>(k);
        long res = 0, sumS = 0;
        for (int i : indices) {
            int currNum1 = nums1[i], currNum2 = nums2[i];
            sumS += currNum1;
            pq.add(currNum1);
            if (pq.size() > k) sumS -= pq.poll();
            if (pq.size() == k) res = Math.max(res, sumS*currNum2);
        }
        return res;
    }

















    // NOT WORKING
    public long maxScoreNW1(int[] nums1, int[] nums2, int k) {
		PriorityQueue<Integer> nums1MinHeap = new PriorityQueue<>((a,b)-> b.intValue() - a.intValue());
		PriorityQueue<Integer> nums2MaxHeap = new PriorityQueue<>(Comparator.reverseOrder());
		for(int i=0; i<nums1.length; i++) {
            int num1 = nums1[i], num2 = nums2[i];
            nums1MinHeap.add(num1);
            nums2MaxHeap.add(num2);
        }
        System.out.println(nums1MinHeap);
        System.out.println(nums2MaxHeap);
        int sum = 0, tempK=k;
        while(tempK-->0) {
            int val = nums1MinHeap.poll();
            sum+=val;
            System.out.println("nums1Val: " + val);
            System.out.println("sum: " + sum);
        }
        while(--k > 0) {
            System.out.println(nums2MaxHeap.poll());
        }
        int nums2Val = nums2MaxHeap.poll();
        System.out.println("nums2Val: " + nums2Val);
        return sum*nums2Val;
    }







    /**
     * NOT WORKING
     *
     * Here I sorted nums1 instead of nums2.
     */
    public long maxScoreNW2(int[] nums1, int[] nums2, int k) {
        Integer[] indices = IntStream.range(0, nums1.length).boxed().toArray(Integer[]::new);
        Arrays.sort(indices, (i1, i2)->nums1[i2]-nums1[i1]);
        System.out.println(Arrays.toString(indices));
        long res = Long.MIN_VALUE;
        int i=0;
        int sum=0, nums2Min=Integer.MAX_VALUE;

        while(i<k) {
            int idx = indices[i];
            sum += nums1[idx];
            nums2Min = Math.min(nums2Min, nums2[idx]);
            i++;
        }
        res = Math.max(res, sum*nums2Min);

        while(i<nums1.length) {
            int idx = indices[i];
            sum += (nums1[idx] - nums1[i-k]);
            nums2Min = findMin(nums2, i, k);
            res = Math.max(res, sum*nums2Min);
            i++;
        }
        return res;
    }
    private int findMin(int[] nums2, int i, int k) {
        int min = nums2[i];
        while(--k>0) {
            min = Math.min(min, nums2[--i]);
        }
        return min;
    }
}