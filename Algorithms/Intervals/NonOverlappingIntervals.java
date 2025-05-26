package Algorithms.Intervals;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since  26 May 2025
 * @link 435. Non-overlapping Intervals https://leetcode.com/problems/non-overlapping-intervals/
 *
 * Example 1:
 * intervals = [(1,2), (2,3), (3,4), (1,3)]
 *          1         2
 *          +---------+
 *                     2         3
 *                     +---------+
 *                               3         4
 *                               +---------+
 *          1                    3
 *          +--------------------+
 *
 * Here, I can remove (1,2) && (2,3) or only (1,3) to make the intervals non-overlapping.
 *
 *
 *
 * Example 2:
 * intervals = [(1,8), (2,3), (4,5), (6,7)]
 *
 * |-------------------------------------------------------|
 *        |--------|
 *                         |--------|
 *                                         |--------|
 *
 * Example 3:
 *
 *         |--------|
 *   |--------||--------|
 *
 * Example 4:
 *
 * |-----|  |----------|     |------------|  |--------|
 *    |--------|    |------------|    |--------|
 *    |--------|                      |--------|
 *    |--------|                      |--------|
 */
public class NonOverlappingIntervals {
    public static void main(String[] args) {
        int[][] intervals = {
            {1, 2},
            {2, 3},
            {3, 4},
            {1, 3}
        };

        System.out.println("eraseOverlapIntervals 1 => " + eraseOverlapIntervals(intervals));
        System.out.println("eraseOverlapIntervals 2 => " + eraseOverlapIntervals2(intervals));
    }


    /**
     *
     * intervals = [(0,5), (3,4), (1,2), (5,9), (5,7), (7,9)]
     *
     * Now sort in ascending order of end time:
     * [(1,2), (3,4), (0,5), (5,7), (5,9), (7,9)]
     *
     *         1         2
     *         +---------+
     *                            3         4
     *                            +---------+
     *   0                                            5
     *   +--------------------------------------------+ ❌ - we cannot perform this meeting
     *                                                5                      7
     *                                                +----------------------+
     *                                                5                                         9
     *                                                +-----------------------------------------+ ❌ - we cannot perform this meeting
     *                                                                        7                 9
     *                                                                        +-----------------+
     *
     */
    public static int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1])); // Sort intervals by their end time

        int count = 0;
        int prevEnd = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            int start = intervals[i][0], end = intervals[i][1];
            if (start < prevEnd) count++;
            else prevEnd = end;
        }
        return count;
    }




    /**
     * intervals = [(0,5), (3,4), (1,2), (5,9), (5,7), (7,9)]
     *
     * Now sort in ascending order of start time:
     * [(0,5), (1,2), (3,4), (5,7), (5,9), (7,9)]
     *
     *       0                                         5
     *       +-----------------------------------------+
     *              1       2
     *              +-------+
     *                             3         4
     *                             +---------+
     *                                                  5                  7
     *                                                  +------------------+
     *                                                  5                              9
     *                                                  +------------------------------+
     *                                                                      7          9
     *                                                                      +----------+
     */
    public static int eraseOverlapIntervals2(int[][] intervals) {
        Arrays.sort(intervals, (a,b)->a[0]-b[0]); // Sort intervals by their start time

        int prevEnd = intervals[0][1];
        int count=0;
        for(int i=1; i<intervals.length; i++) {
            int start=intervals[i][0], end=intervals[i][1];
            if(start >= prevEnd) prevEnd=end;
            else {
                count++;
                prevEnd=Math.min(prevEnd, end);
            }
        }
        return count;
    }







    /**
     * NOT WORKING
     */
    public int eraseOverlapIntervalsMyApproach(int[][] intervals) {
        Arrays.sort(intervals, (a,b)->a[0]==b[0]?a[1]-b[1]:a[0]-b[0]);
        int end = intervals[0][0], prevLen=0, count=0;
        for(int[] interval: intervals) {
            System.out.println(Arrays.toString(interval));
            if(end>interval[0]) {
                if (prevLen>interval[1]-interval[0]) { // skip prev
                    end = interval[1];
                    prevLen = interval[1]-interval[0];
                }
                count++;
            } else {
                end = interval[1];
            }
        }
        return count;
    }
}
