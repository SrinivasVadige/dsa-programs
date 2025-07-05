package Algorithms.GreedyAlgorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 05 July 2025
 * @link 3572. Maximize Y‑Sum by Picking a Triplet of Distinct X‑Values <a href="https://leetcode.com/problems/maximize-ysum-by-picking-a-triplet-of-distinct-xvalues">Leetcode link</a>
 * @description maximize the value of y[i] + y[j] + y[k], where i != j != k and x[i] != x[j] != x[k].
 * @topics Array, Greedy, Hash Table, Sorting, Heap(Priority Queue)
 */
public class MaximizeYSumByPickingTripletOfDistinctXValues {
    public static void main(String[] args) {
        int[] x = {1,2,1,3,2};
        int[] y = {5,3,4,6,2};
        System.out.println("maxSumDistinctTriplet => " + maxSumDistinctTriplet(x, y));
    }

    public static int maxSumDistinctTriplet(int[] x, int[] y) {
        Map<Integer, Integer> map = new HashMap<>(); // <x, y big value>
        int n = x.length;
        for(int i=0; i<n; i++) {
            int key = x[i];
            int bigValue = Math.max(map.getOrDefault(key, 0), y[i]); // greedy way to get max "y" value
            map.put(key, bigValue);
        }

        if(map.size() < 3) {
            return -1;
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // minHeap -> we poll() the smallest value in the queue
        for(int val: map.values()) {
            minHeap.offer(val);
            if(minHeap.size() > 3) {
                minHeap.poll();
            }
        }

        return minHeap.stream().reduce(0, Integer::sum);
        // or return map.values().stream().sorted(Comparator.reverseOrder()).limit(3).reduce(0, Integer::sum);
    }
}
