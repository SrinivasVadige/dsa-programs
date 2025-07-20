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
        System.out.println("minMeetingRooms using min heap => " + minMeetingRoomsUsingMinHeap(intervals));
        System.out.println("minMeetingRooms using Chronological Ordering => " + minMeetingRoomsUsingChronologicalOrdering(intervals));
    }



    /**
     * @TimeComplexity O(NlogN)
     * @SpaceComplexity O(N)

        APPROACH: MinHeap / PriorityQueue to track the recent meeting that is going to end
        ---------

        NOTE:
        If we sort by endTime, we lose the chronological sequence of when meetings start, which is crucial to simulate real-time allocation.

        because we can't greedily pick the earliest-ending meeting (to replace the current meeting) when we haven't processed all earlier-starting meetings.
        It leads to incorrect reuse and potentially more rooms.

        if you sort by endTime instead of startTime, then we will have this below error ❌ ----> 🔥
        example --> when intervals = [1,4],[2,5],[6,8],[4,9]

        [1,4],[2,5],[6,8],[4,9]
          o     o     |
                      |
                      ↓
        how to know if this [6,8] meeting has to replaced [1,4] or [2,5] ?? --> cause the next [4,9] meeting can only be placed in [1,4]

            ------------                                                                 <--| this [1,4] ?
                -------------                                                            <--| or this [2,5] ?
                                ---------       -> * ___ this [6,8] meeting replacement by ??
                        --------------------
        _____________________________________
        0   1   2   3   4   5   6   7   8   9

        the correct solution is fill [6,8] room in [2,5] instead of [1,4]. So, we can fill [4,9] room in [1,4] without any issues and no extra room needed



        So, sort with startTime to get this result
            ------------
                -------------
                        --------------------
                                ---------
        _____________________________________
        0   1   2   3   4   5   6   7   8   9
     */
    public static int minMeetingRoomsUsingMinHeap(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(i->i[0]));
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for(int[] interval: intervals) {
            if(minHeap.isEmpty()) { // --> increase roomCount ++
                minHeap.add(interval[1]);
            } else if (minHeap.peek() > interval[0]) { // --> increase roomCount ++
                minHeap.add(interval[1]);
            } else { // minHeap.peek() <= interval[0] --> here the roomCount stays the same -- and ++ i.e., we replace the ending meeting roon with the current one
                minHeap.poll();
                minHeap.add(interval[1]);
            }
        }
        return minHeap.size();
    }



    /**
     * @TimeComplexity O(NlogN) -- but this "Chronological Ordering" is faster than above "Priority Queue" approach
     * @SpaceComplexity O(N)


         APPROACH: Separate start and end arrays and use Chronological Ordering
         ---------

        intervals = [[0,10], [2,5], [7,8]]
        start = [0, 2, 7]
        end = [5, 8, 10]

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
        for (int startI = 0; startI < n; startI++) { // or startPointer
            if (start[startI] < end[endI]) { // is meetingStart < meetingEnd
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






    /**
     * same as above {@link #minMeetingRoomsUsingMinHeap} method but move this "minHeap.add(interval[1]);" operation common for all the iterations
     */
    public static int minMeetingRoomsUsingPriorityQueue2(int[][] intervals) {
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





    public int minMeetingRoomsUsingPriorityQueue3(int[][] intervals) {
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
