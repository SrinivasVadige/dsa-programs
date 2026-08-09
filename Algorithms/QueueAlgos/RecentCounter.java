package Algorithms.QueueAlgos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 20 April 2025
 */
public class RecentCounter {
    public static void main(String[] args) {
        RecentCounter recentCounter = new RecentCounter();
        System.out.println(recentCounter.ping(1)); // 1
        System.out.println(recentCounter.ping(100)); // 2
        System.out.println(recentCounter.ping(3001)); // 3
        System.out.println(recentCounter.ping(3002)); // 3
    }

    private final Queue<Integer> queue;

    public RecentCounter() {
        queue = new LinkedList<>();
    }

    public int ping(int t) {
        queue.offer(t);
        while (queue.peek() < t - 3000) {
            queue.poll();
        }
        return queue.size();
    }






    List<Integer> lst = new ArrayList<>();
    public int pingUsingList(int t) {
        lst.add(t);
        int c=1;
        int r1 = t-3000;
        for(int i=lst.size()-2; i>=0; i--) {
            if(lst.get(i)>=r1) c++;
        }
        return c;
    }









    private static final int[] records = new int[10000]; //
    private int start=0;
    private int end=0;
    public int pingUsingArray(int t) {
        while (start < end && (t - records[start] > 3000)) {
            start++; // if the difference in time is greater than 3000ms,
            // than increase the value of start until it's equal or less than 3000ms.
        }
        records[end++] = t; // Inserting the current time at the end
        return end - start; // Returning the answer including the element added just now.
    }







    TreeMap<Integer, Integer> treeMap = new TreeMap<>();
    int count = 0;
    public int pingUsingTreeMap(int t) {
        ++count;
        treeMap.put(t, count);
        Map.Entry<Integer, Integer> lower = treeMap.lowerEntry(t - 3000);
        // if (lower == null) return count;
        return count - (lower==null ? 0 : lower.getValue());
    }
}
