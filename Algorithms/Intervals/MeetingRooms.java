package Algorithms.Intervals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 09 July 2025
 * @link 252. Meeting Rooms <a href="https://leetcode.com/problems/meeting-rooms/">LeetCode link</a>
 * @description Given meetings with intervals[][], intervals[i] = [starti, endi], determine if a person could attend all meetings.
 * @topics Array, Sorting, Greedy
 * @companies Amazon, Meta, TikTok, Bloomberg, Oracle, Apple, Palo, Turo, Adobe, Uber, eBay
 */
public class MeetingRooms {
    public static void main(String[] args) {
        int[][] intervals = {{0, 30}, {5, 10}, {15, 20}}; // => false
        System.out.println("canAttendMeetings => " + canAttendMeetings(intervals));
    }

    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(1)
     */
    public static boolean canAttendMeetings(int[][] intervals) {
        if(intervals == null || intervals.length == 0) {
            return true;
        }
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        int prevEnd = intervals[0][0];
        for(int[] interval: intervals) {
            if(prevEnd > interval[0]) {
                return false;
            }
            prevEnd = interval[1];
        }
        return true;
    }



    public static boolean canAttendMeetings2(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        for (int i = 0; i < intervals.length - 1; i++) {
            if (intervals[i][1] > intervals[i + 1][0]) {
                return false;
            }
        }
        return true;
    }

    /**
     * @TimeComplexity O(NlogN) -- but this "Chronological Ordering" is faster than above "Priority Queue" approach
     * @SpaceComplexity O(N)


         APPROACH: Separate start and end arrays and use Chronological Ordering
         ---------

        intervals = [[0,10], [2,5], [7,8]]

        🟢      🟢          🔚      🟢  🔚      🔚
        __________________________________________
        0   1   2   3   4   5   6   7   8   9   10

     */
    public static boolean canAttendMeetingsUsingChronologicalOrdering(int[][] intervals) {
        int[] start = new int[intervals.length];
        int[] end = new int[intervals.length];
        for(int i=0; i<intervals.length; i++) {
            start[i] = intervals[i][0];
            end[i] = intervals[i][1];
        }
        Arrays.sort(start);
        Arrays.sort(end);

        // we know that start[0] occupies the first room. so, check from next start and first end
        for(int i=0; i<intervals.length-1; i++) {
            if(start[i+1] < end[i]) {
                return false;
            }
        }
        return true;
    }


    /**
     * @TimeComplexity O(NlogN)
     * @SpaceComplexity O(N)

         APPROACH: PriorityQueue to track the recent meeting mend
         ---------

        intervals = [[0,10], [2,5], [7,8]]


        ----------------------------------------
                ------------
                                    ----
        __________________________________________
       0    1   2   3   4   5   6   7   8   9   10

       using pq / minHeap --> track the meetings that are going to finish soon
     */
    public boolean canAttendMeetingsUsingMinHeap(int[][] intervals) {
        if(intervals == null || intervals.length == 0) {
            return true;
        }
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for(int[] interval : intervals) {
            if(!minHeap.isEmpty() && minHeap.peek()<=interval[0]){
                minHeap.poll();
            }
            minHeap.offer(interval[1]);
        }
        System.out.println(minHeap);
        return minHeap.size()==1;

    }





    public boolean canAttendMeetingsUsingBruteForce(int[][] intervals) {
        for (int i = 0; i < intervals.length; i++) {
            for (int j = i + 1; j < intervals.length; j++) {
                if (overlap(intervals[i], intervals[j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean overlap(int[] interval1, int[] interval2) {
        return (interval1[0] >= interval2[0] && interval1[0] < interval2[1])
            || (interval2[0] >= interval1[0] && interval2[0] < interval1[1]);
    }
}
