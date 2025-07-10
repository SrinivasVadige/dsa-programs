package Algorithms.Intervals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 09 July 2025
 * @link 253. Meeting Rooms II <a href="https://leetcode.com/problems/meeting-rooms-ii/">LeetCode Link</a>
 * @description Find the minimum number of conference rooms required to hold all the meetings.
 * @topics Array, Two Pointers, Greedy, Sorting, Heap(Priority Queue), prefix sum
 * @companies Amazon, Google, Atlassian, TikTok, Meta, Waymo, Microsoft, Bloomberg, Uber, Docusign, WorldQuant, Apple, Oracle, Cisco, Netflix, Hubspot, Lime, IBM, Snap, Salesforce, Turo, Splunk, eBay, Pinterest, Yandex, ServiceNow, Lyft
 */
public class MeetingRooms2 {
    public static void main(String[] args) {
        int[][] intervals = {{0, 10}, {2, 5}, {7, 8}}; // => 2
        System.out.println("minMeetingRooms using Priority Queue => " + minMeetingRoomsUsingPriorityQueue(intervals));
        System.out.println("minMeetingRooms using Chronological Ordering => " + minMeetingRoomsUsingChronologicalOrdering(intervals));
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
        0   1   2   3   4   5   6   7   8   9   10

       using pq / minHeap --> track the meetings that are going to finish soon
     */
    public int minMeetingRoomsMyApproach(int[][] intervals) {
        int rooms = 0;
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for(int[] interval: intervals) {
            if(minHeap.isEmpty() || minHeap.peek() > interval[0]) {
                rooms++;
            } else { // minHeap.peek() <= interval[0]
                minHeap.poll();
            }
            minHeap.offer(interval[1]);
        }
        return rooms; // or minHeap.size();
    }


    public static int minMeetingRoomsUsingPriorityQueue(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int[] interval : intervals) {
            if (!minHeap.isEmpty() && minHeap.peek() <= interval[0]) {
                minHeap.poll();
            }
            minHeap.offer(interval[1]);
        }
        return minHeap.size();
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
    public static int minMeetingRoomsUsingChronologicalOrdering(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;
        int n = intervals.length; // intervals.length == start.length == end.length
        int[] start = new int[n];
        int[] end = new int[n];
        for (int i = 0; i < n; i++) {
            start[i] = intervals[i][0];
            end[i] = intervals[i][1];
        }
        Arrays.sort(start);
        Arrays.sort(end);
        int rooms = 0, endI = 0; // or endPointer
        for (int i = 0; i < n; i++) { // i -> is startI or startPointer
            if (start[i] < end[endI]) { // is meetingStart < meetingEnd
                rooms++;
            } else {
                endI++; // we can reuse the room
            }
        }
        /*
        // or
        int rooms = 0, startPointer = 0, endPointer = 0;
        while (startPointer < intervals.length) {
          if (start[startPointer] >= end[endPointer]) {
            rooms--;
            endPointer++;
          }
          rooms++;
          startPointer++;
        }
        */
        return rooms;
    }








    public int minMeetingRoomsNotWorking(int[][] intervals) {
        Arrays.sort(intervals, (a,b)-> a[1]==b[1]? b[0]-a[0] : a[1]-b[1]);
        // Arrays.stream(intervals).forEach(x->System.out.print(Arrays.toString(x)));
        int rooms = 0;
        int prevEnd = intervals[0][1];
        for (int[] meet: intervals) {
            int currStart = meet[0];
            int currEnd = meet[1];
            if(prevEnd > currStart) {
                rooms++;
            }
            prevEnd = Math.max(prevEnd, currEnd);
        }
        return rooms;
    }
}
