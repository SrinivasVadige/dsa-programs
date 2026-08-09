package Algorithms.HeapAlgos;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 09 May 2025
 * @link https://leetcode.com/problems/total-cost-to-hire-k-workers/
 */
public class TotalCostToHireKWorkers {
    public static void main(String[] args) {
        int[] cost = {17,12,10,2,7,2,11,20,8};
        int k = 3;
        int candidates = 3;
        System.out.println("totalCost => " + totalCost(cost, k, candidates));
        System.out.println("totalCostMyApproach => " + totalCostMyApproach(cost, k, candidates));
        System.out.println("totalCostMyApproachOld => " + totalCostMyApproachOld(cost, k, candidates));
    }


    public static long totalCost(int[] costs, int k, int candidates) {
        int l = 0;
        int r = costs.length-1;
        long totalCost = 0;
        PriorityQueue<Integer> left = new PriorityQueue<>();
        PriorityQueue<Integer> right = new PriorityQueue<>();

        while (k-- > 0) {
            while (left.size() < candidates && l <= r) {
                left.offer(costs[l++]);
            }
            while (right.size() < candidates && l <= r) {
                right.offer(costs[r--]);
            }

            int lc = left.size() > 0 ? left.peek() : Integer.MAX_VALUE;
            int rc = right.size() > 0 ? right.peek() : Integer.MAX_VALUE;

            if (lc <= rc) {
                totalCost += lc;
                left.poll();
            } else {
                totalCost += rc;
                right.poll();
            }
        }
        return totalCost;
    }




    public static long totalCost1(int[] costs, int k, int candidates) {
        long totalCost = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)-> a==b?a[1]-b[1]: a[0]-b[0]);
        for(int i=0;i<costs.length;i++) pq.offer(new int[]{costs[i],i});

        while(k-->0) {
            int[] poll = pq.poll();
            totalCost += poll[0];
            if(poll[1] < costs.length-1) pq.offer(new int[]{costs[poll[1]+1], poll[1]+1});
        }
        return totalCost;
    }






    public static long totalCostMyApproach(int[] costs, int k, int candidates) {
        int n = costs.length, remainingCosts = n;
        PriorityQueue<Integer> left = new PriorityQueue<>();
        PriorityQueue<Integer> right = new PriorityQueue<>();

        int l = 0, r = n-1;
        while (l < candidates) { // Fill the left queue
            left.offer(costs[l++]);
            remainingCosts--;
        }
        while (remainingCosts > 0 && (n-1-r) < candidates) { // Fill the right queue
            right.offer(costs[r--]);
            remainingCosts--;
        }

        long totalCost = 0;
        while (k-- > 0) {
            int lc = left.isEmpty() ? Integer.MAX_VALUE : left.peek();
            int rc = right.isEmpty() ? Integer.MAX_VALUE : right.peek();

            if (lc <= rc) { // Choose from the left queue
                totalCost += lc;
                left.poll();
                if (l <= r) { // Add the next element to the left queue if available
                    left.offer(costs[l++]);
                    remainingCosts--;
                }
            } else { // Choose from the right queue
                totalCost += rc;
                right.poll();
                if (l <= r) { // Add the next element to the right queue if available
                    right.offer(costs[r--]);
                    remainingCosts--;
                }
            }
        }

        return totalCost;
    }






    public static long totalCostMyApproachOld(int[] costs, int k, int candidates) {
        int n = costs.length, remainingCosts = n;
        PriorityQueue<Integer> left = new PriorityQueue<>();
        PriorityQueue<Integer> right = new PriorityQueue<>();

        int l=0, r=n-1;
        while(l<candidates) {
            left.offer(costs[l++]);
            remainingCosts--;
        }
        while(remainingCosts>0 && (n-1-r)<candidates) {
            right.offer(costs[r--]);
            remainingCosts--;
        }

        long totalCost = 0;
        while(k-->0) {
            // System.out.println("k: " +k+ ", left: "+left+", right: "+right);
            int cost = 0, lc = 0, rc = 0; // or use Integer.MAX_VALUE like above
            if(!left.isEmpty()) lc = left.peek();
            if(!right.isEmpty()) rc = right.peek();

            if((lc<=rc && lc>0) || rc==0) {
                cost = lc;
                left.poll();
                if(remainingCosts > 0) {
                    left.offer(costs[l++]);
                    remainingCosts--;
                }
            } else { // but not exactly as "rc<lc && rc>0"
                cost = rc;
                right.poll();
                if(remainingCosts > 0) {
                    right.offer(costs[r--]);
                    remainingCosts--;
                }
            }
            totalCost += cost;
        }
        return totalCost;
    }






    public long totalCost2(int[] costs, int k, int candidates) {
        int n = costs.length;
        if (2 * candidates >= n) {
            Arrays.sort(costs);
            long sum = 0;
            for (int i = 0; i < k; i++) {
                sum += costs[i];
            }
            return sum;
        }
        if (candidates == 1) {
            long sum = 0;
            int left = 0, right = n - 1;
            while (k-- > 0) {
                if (right < left || costs[left] <= costs[right]) {
                    sum += costs[left++];
                } else {
                    sum += costs[right--];
                }
            }
            return sum;
        }
        int[] frontHeap = new int[candidates];
        int[] backHeap = new int[candidates];
        int frontSize = 0, backSize = 0;
        int left = 0, right = n - 1;
        for (int i = 0; i < candidates && i < n/2; i++) {
            frontHeap[frontSize++] = costs[left++];
            backHeap[backSize++] = costs[right--];
            siftUp(frontHeap, frontSize - 1);
            siftUp(backHeap, backSize - 1);
        }
        long totalCost = 0;
        while (k-- > 0) {
            if (backSize == 0 || (frontSize > 0 && frontHeap[0] <= backHeap[0])) {
                totalCost += frontHeap[0];
                if (left <= right) {
                    frontHeap[0] = costs[left++];
                    siftDown(frontHeap, 0, frontSize);
                } else {
                    frontHeap[0] = frontHeap[--frontSize];
                    if (frontSize > 0) siftDown(frontHeap, 0, frontSize);
                }
            } else {
                totalCost += backHeap[0];
                if (left <= right) {
                    backHeap[0] = costs[right--];
                    siftDown(backHeap, 0, backSize);
                } else {
                    backHeap[0] = backHeap[--backSize];
                    if (backSize > 0) siftDown(backHeap, 0, backSize);
                }
            }
       }
        return totalCost;
    }
    private void siftUp(int[] heap, int index) {
        int value = heap[index];
        while (index > 0) {
            int parent = (index - 1) >>> 1;
            if (heap[parent] <= value) break;
            heap[index] = heap[parent];
            index = parent;
        }
        heap[index] = value;
    }
    private void siftDown(int[] heap, int index, int size) {
        int value = heap[index];
        int half = size >>> 1;
        while (index < half) {
            int child = (index << 1) + 1;
            int right = child + 1;
            if (right < size && heap[right] < heap[child]) {
                child = right;
            }
            if (value <= heap[child]) break;
            heap[index] = heap[child];
            index = child;
        }
        heap[index] = value;
    }







}
