package Algorithms.HeapAlgos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class TopKFrequentElements {
    public static void main(String[] args) {
        int[] nums = new int[]{1,1,1,2,2,3};
        System.out.println("topKFrequentUsingMapAndFreqArr(nums, 1) => " + Arrays.toString(topKFrequentUsingMapAndFreqArr(nums, 1)));
        System.out.println("topKFrequentUsingMapAndSet(nums, 1) => " + Arrays.toString(topKFrequentUsingMapAndSet(nums, 1)));
        System.out.println("topKFrequentUsingMapAndPq(nums, 1) => " + Arrays.toString(topKFrequentUsingMapAndPq(nums, 1)));
    }

    /**
     * @TimeComplexity O(N)
     * @SpaceComplexity O(N)
     *
     * Bucket Sort
     */
    public static int[] topKFrequentUsingMapAndFreqArr(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int n: nums) map.merge(n, 1, Integer::sum);

        // Map<Integer, Integer> map = Arrays.stream(nums).boxed().collect(Collectors.groupingBy(i->i, Collectors.summingInt(_->1)));

        @SuppressWarnings("unchecked")
        // at worst case, when each num has unique frequency, freqArr.length = nums.length+1. Otherwise, freqArr.length < nums.length
        // we set freqArr.length = n+1 "+1" cause, one element must have at least one frequency and freqArr[1] has to be possible
        // And freqArr[0] is not used and always dummy
        List<Integer>[] freqArr =  new List[nums.length + 1]; // or new ArrayList<>(Collections.nCopies(n+1, new ArrayList<>())); or new ArrayList<>() and add n+1 times
        for (int i = 0; i < freqArr.length; i++) {
            freqArr[i] = new ArrayList<>();
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int frequency = entry.getValue(); // frequency is the index of freqArr
            freqArr[frequency].add(entry.getKey()); // element is List of nums at that frequency
        }

        int[] res = new int[k];
        int idx = 0;
        fOne: for (int i = freqArr.length - 1; i >= 0; i--) {
            for (int num : freqArr[i]) {
                res[idx++] = num;
                if (idx == k) break fOne;
            }
        }

        return res;
    }



    public static int[] topKFrequentUsingMapAndSet(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] res = new int[k];

        for(int n: nums) map.merge(n, 1, Integer::sum);
        Set<Integer> set = map.values().stream().sorted(Comparator.reverseOrder()).limit(k).collect(Collectors.toSet());

        int i=0;
        for(Map.Entry<Integer, Integer> e: map.entrySet()) {
            if (set.contains(e.getValue())) res[i++]=e.getKey();
        }

        return res;
    }

    /**
     * @TimeComplexity O(NlogK)
     * @SpaceComplexity O(N)
     */
    public static int[] topKFrequentUsingMapAndPq(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // sort based on value
        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            pq.offer(entry);
        }
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = pq.poll().getKey();
        }
        return res;
    }


    public static int[] topKFrequentUsingFreqArray(int[] nums, int k) {
        int[] freq = new int[20001];
        for (int num : nums) {
            freq[num + 10000]++;
        }
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            int max = 0;
            int index = 0;
            for (int j = 0; j < freq.length; j++) {
                if (freq[j] > max) {
                    max = freq[j];
                    index = j - 10000;
                }
            }
            res[i] = index;
            freq[index + 10000] = 0;
        }
        return res;
    }






    public static int[] topKFrequentUsingMapAndPq2(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            heap.add(new int[]{key, value});
            if (heap.size() > k) {
                heap.poll();
            }
        }
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            ans[i] = heap.poll()[0];
        }
        return ans;
    }



    public int[] topKFrequentUsingMapAndPq3(int[] nums, int k) {
        Map<Integer,Integer>frequency=new HashMap<>();
        for(int element:nums){
            frequency.put(element,frequency.getOrDefault(element,0)+1);
        }
        Queue<Integer> heap=new PriorityQueue<>((n1,n2)-> frequency.get(n1)-frequency.get(n2));
        for(int element:frequency.keySet()){
            heap.add(element);
            if(heap.size()>k) heap.poll();
        }

        int result[]= new int[k];
        for(int i=k-1;i>=0;i--){
            result[i]=heap.poll();
        }
        return result;
    }








    // using heap
    public static int[] topKFrequentUsingHeap(int[] nums, int k) {
        return null;
    }
}
