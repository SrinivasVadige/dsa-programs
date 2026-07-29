package Algorithms.IntegerArray;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 28 July 2026
 * @link 624. Maximum Distance in Arrays <a href="https://leetcode.com/problems/maximum-distance-in-arrays/">LeetCode link</a>
 * @topics Senior, Array, Greedy
 * @companies Google(6), Bloomberg(3), Microsoft(2)
 */
public class MaximumDistanceInArrays {
    public static void main(String[] args) {
        List<List<Integer>> arrays = Arrays.asList(Arrays.asList(4, 9), Arrays.asList(1, 3), Arrays.asList(2, 5), Arrays.asList(10, 14));

        System.out.println("maxDistance Using Sort: " + maxDistanceUsingSort(arrays));
        System.out.println("maxDistance Using Heap: " + maxDistanceUsingHeap(arrays));
        System.out.println("maxDistance Using Only MinMax Vars: " + maxDistanceUsingOnlyMinMaxVars(arrays));
        System.out.println("maxDistance Using Brute Force 1: " + maxDistanceUsingBruteForce1_TLE(arrays));
        System.out.println("maxDistance Using Brute Force 2: " + maxDistanceUsingBruteForce2_TLE(arrays));
    }

    /**
    
        [[1,3,5],[4,5],[2,3,4]]
    
        [1,3,5] - m,m
        [4,5]   - m,m
        [2,3,4] - m,m
    
     * @TimeComplexity O(mlogm)
     * @SpaceComplexity O(m)
     */
    public static int maxDistanceUsingSort(List<List<Integer>> arrays) {
        int m = arrays.size();
        List<int[]> minsAndMax = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            List<Integer> subList = arrays.get(i);
            minsAndMax.add(new int[] { subList.get(0), i });
            minsAndMax.add(new int[] { subList.get(subList.size() - 1), i });
        }

        minsAndMax.sort(Comparator.comparingInt(a -> a[0]));

        int l = 0;
        int r = minsAndMax.size() - 1;

        if (minsAndMax.get(l)[1] == minsAndMax.get(r)[1]) {
            return Math.max(minsAndMax.get(r)[0] - minsAndMax.get(l + 1)[0], minsAndMax.get(r - 1)[0] - minsAndMax.get(l)[0]);
        }

        return minsAndMax.get(r)[0] - minsAndMax.get(l)[0];
    }


    /**
     * @TimeComplexity O(m)
     * @SpaceComplexity O(1)
     */
    public static int maxDistanceUsingHeap(List<List<Integer>> arrays) {
        int m = arrays.size();
        PriorityQueue<int[]> maxHeapForMins = new PriorityQueue<>(Comparator.comparingInt(a -> -a[0])); // or Comparator.comparingInt((int[] a) -> a[0]).reversed() or Comparator.<int[]>comparingInt(a -> a[0]).reversed()
        PriorityQueue<int[]> minHeapForMaxs = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        for (int i = 0; i < m; i++) {
            int min = arrays.get(i).get(0);
            int max = arrays.get(i).get(arrays.get(i).size() - 1);
            maxHeapForMins.offer(new int[] { min, i });
            minHeapForMaxs.offer(new int[] { max, i });

            if (i >= 2) {
                maxHeapForMins.poll();
                minHeapForMaxs.poll();
            }
        }

        int[] min1 = maxHeapForMins.poll();
        int[] min2 = maxHeapForMins.poll();
        int[] max1 = minHeapForMaxs.poll();
        int[] max2 = minHeapForMaxs.poll();

        int max = 0;
        if (min1[1] != max1[1]) {
            max = Math.max(max, max1[0] - min1[0]);
        }
        if (min1[1] != max2[1]) {
            max = Math.max(max, max2[0] - min1[0]);
        }
        if (min2[1] != max1[1]) {
            max = Math.max(max, max1[0] - min2[0]);
        }
        if (min2[1] != max2[1]) {
            max = Math.max(max, max2[0] - min2[0]);
        }

        return max;
    }


    /**
     * @TimeComplexity O(m)
     * @SpaceComplexity O(1)
     */
    public static int maxDistanceUsingOnlyMinMaxVars(List<List<Integer>> arrays) {
        int res = 0;
        int n = arrays.get(0).size();
        int min_val = arrays.get(0).get(0);
        int max_val = arrays.get(0).get(n - 1);
        
        for (int i = 1; i < arrays.size(); i++) {
            n = arrays.get(i).size();
            int currMin = arrays.get(i).get(0);
            int currMax = arrays.get(i).get(n - 1);

            res = Math.max(res, Math.max(Math.abs(currMax - min_val), Math.abs(max_val - currMin)));
            
            min_val = Math.min(min_val, currMin);
            max_val = Math.max(max_val, currMax);
        }
        return res;
    }




    /**
     * @TimeComplexity O(m^2)
     * @SpaceComplexity O(1)
     */
    public static int maxDistanceUsingBruteForce1_TLE(List<List<Integer>> arrays) {
        int res = 0;
        int n = arrays.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < arrays.get(i).size(); j++) {
                for (int k = i + 1; k < n; k++) {
                    for (int l = 0; l < arrays.get(k).size(); l++) {
                        res = Math.max(res, Math.abs(arrays.get(i).get(j) - arrays.get(k).get(l)));
                    }
                }
            }
        }
        return res;
    }

    /**
     * @TimeComplexity O(m^2)
     * @SpaceComplexity O(1)
     */
    public static int maxDistanceUsingBruteForce2_TLE(List<List<Integer>> arrays) {
        List<Integer> array1, array2;
        int res = 0;
        int n = arrays.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                array1 = arrays.get(i);
                array2 = arrays.get(j);
                res = Math.max(res, Math.abs(array1.get(0) - array2.get(array2.size() - 1)));
                res = Math.max(res, Math.abs(array2.get(0) - array1.get(array1.size() - 1)));
            }
        }
        return res;
    }
}
