package Algorithms.HeapAlgos;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 21 April 2026
 * @link 373. Find K Pairs with Smallest Sums <a href="https://leetcode.com/problems/find-k-pairs-with-smallest-sums/">LeetCode Link</a>
 * @topics Array, Heap(PriorityQueue)
 * @companies Google(2), Amazon(4), Meta(2), LinkedIn(2), Microsoft(4), Bloomberg(2), Flipkart(2), Walmart Labs(2), Uber(2)
 */
public class FindKPairsWithSmallestSums {
    public static void main(String[] args) {
        int[] nums1 = {1,2,2,3,4};
        int[] nums2 = {1,2,3,3};
        int k = 3;
        System.out.printf("kSmallestPairs TLE %s\n", kSmallestPairsTLE1(nums1, nums2, k));
        System.out.printf("kSmallestPairs %s\n", kSmallestPairs1(nums1, nums2, k));
    }


    /**
     * @TimeComplexity O(mnlogk) -> mn for nested for loop and logk for heap operation in that loop
     * @SpaceComplexity O(k)
     */
    public static List<List<Integer>> kSmallestPairsTLE1(int[] nums1, int[] nums2, int k) {
        PriorityQueue<List<Integer>> maxHeap = new PriorityQueue<>(Comparator.comparingInt((List<Integer> l) ->  l.get(0) + l.get(1)).reversed());
        for (int num1: nums1) {
            for (int num2: nums2) {
                List<Integer> list = Arrays.asList(num1, num2);
                maxHeap.add(list);
                if (maxHeap.size() > k) maxHeap.poll();
            }
        }
        return new ArrayList<>(maxHeap);
    }


    /**
        nums1 = [1,2,2,3,4], nums2 = [1,2,3,3]

                      i0
                   [1, x]
        [1,1]       [1,2]     [1,3]     [1,3]
          2           3         4         4


                      i1
                    [2, x]
        [2,1]       [2,2]     [2,3]     [2,3]
          3           4         5         5


                      i2
                    [2, x]
        [2,1]       [2,2]     [2,3]     [2,3]
          3           4         5         5


                      i3
                    [3, x]
        [3,1]       [3,2]     [3,3]     [3,3]
          4           5         6          6


                      i4
                    [4, x]
        [4,1]       [4,2]     [4,3]     [4,3]
          5           6         7         7


     * @TimeComplexity O(min(k, mn) * log k) or O(min(klogk,mnlog(mn))), We iterate O(min(k,mn)), logk for heap, log(mn) for?
     * @SpaceComplexity O(min(k,mn))
     */
    public static List<List<Integer>> kSmallestPairs1(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        Set<Integer> seen = new HashSet<>();
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        List<List<Integer>> res = new ArrayList<>();

        minHeap.add(new int[]{nums1[0]+nums2[0], 0, 0});
        seen.add(0);

        while (k-- > 0 && !minHeap.isEmpty()) {
            int[] curr = minHeap.poll();
            int i = curr[1];
            int j = curr[2];
            res.add(Arrays.asList(nums1[i], nums2[j]));

            int rightIdx = i * n + j+1;
            int downIdx = (i+1)*n + j;

            if (j+1<n && seen.add(rightIdx)) minHeap.add(new int[]{nums1[i]+nums2[j+1], i, j+1});
            if (i+1<m && seen.add(downIdx)) minHeap.add(new int[]{nums1[i+1]+nums2[j], i+1, j});
        }
        return res;
    }




    public List<List<Integer>> kSmallestPairs2(int[] nums1, int[] nums2, int k) {
        return new AbstractList<>() {

            private List<List<Integer>> pairs;

            @Override
            public List<Integer> get(int index) {
                init();
                return pairs.get(index);
            }

            @Override
            public int size() {
                init();
                return pairs.size();
            }

            private void init() {
                if (pairs == null) {
                    loadPairs();
                }
            }

            private void loadPairs() {
                int m = nums1.length, n = nums2.length;
                int low = nums1[0] + nums2[0];
                int high = nums1[m - 1] + nums2[n - 1];

                while (low <= high) {
                    int mid = (low + high) / 2;
                    long count = countPairsLessThanOrEqual(nums1, nums2, mid, k);

                    if (count < k) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                }

                collectPairs(nums1, nums2, low, k);
            }

            private long countPairsLessThanOrEqual(int[] nums1, int[] nums2, int threshold, int k) {
                int count = 0;
                int n = nums2.length;
                int j = n - 1;

                for (int i = 0; i < nums1.length && nums1[i] + nums2[0] <= threshold; i++) {
                    int left = 0, right = j, pos = -1;
                    while (left <= right) {
                        int mid = (left + right) / 2;
                        if (nums1[i] + nums2[mid] <= threshold) {
                            pos = mid;
                            left = mid + 1;
                        } else {
                            right = mid - 1;
                        }
                    }

                    if (pos != -1) {
                        count += (pos + 1);
                        j = pos;
                    }

                    if (count > k) break;
                }

                return count;
            }

            private void collectPairs(int[] nums1, int[] nums2, int threshold, int k) {
                pairs = new ArrayList<>();

                for (int i = 0; i < nums1.length; i++) {
                    for (int j = 0; j < nums2.length && nums1[i] + nums2[j] < threshold; j++) {
                        pairs.add(Arrays.asList(nums1[i], nums2[j]));
                    }
                }

                for (int i = 0; i < nums1.length; i++) {
                    for (int j = 0; j < nums2.length && nums1[i] + nums2[j] <= threshold && pairs.size() < k; j++) {
                        if (nums1[i] + nums2[j] == threshold) {
                            pairs.add(Arrays.asList(nums1[i], nums2[j]));
                        }
                    }
                }
            }
        };
    }
}
