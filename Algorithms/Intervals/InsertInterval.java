package Algorithms.Intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 29 September 2025
 * @link 57. Insert Interval <a href="https://leetcode.com/problems/insert-interval/">LeetCode Link</a>
 * @topics Array, Intervals, Sorting
 */
public class InsertInterval {
    public static void main(String[] args) {
        int[][] intervals = {{1, 3}, {6, 9}};
        int[] newInterval = {2, 5};
        System.out.println("insertUsingInsertThenMerge => ") ;
        for (int[] i : insertUsingInsertThenMerge(intervals, newInterval)) System.out.print(Arrays.toString(i));

        intervals = new int[][] {{1, 3}, {6, 9}};
        newInterval = new int[] {2, 5};
        System.out.println("\ninsertUsingExpandingNewInterval => ") ;
        for (int[] i : insertUsingExpandingNewInterval(intervals, newInterval)) System.out.print(Arrays.toString(i));

        intervals = new int[][] {{1, 3}, {6, 9}};
        newInterval = new int[] {2, 5};
        System.out.println("\ninsertUsingExpandingNewInterval 2 => ") ;
        for (int[] i : insertUsingExpandingNewInterval2(intervals, newInterval)) System.out.print(Arrays.toString(i));
    }



    public static int[][] insertUsingInsertThenMerge(int[][] intervals, int[] newInterval) {

        // STEP 1: Insert newInterval in given intervals without merge
        List<int[]> updatedIntervals = new ArrayList<>();
        boolean inserted = false;

        for (int[] curr : intervals) {
            if (!inserted && newInterval[0] <= curr[0]) {
                updatedIntervals.add(newInterval);
                inserted = true;
            }
            updatedIntervals.add(curr);
        }
        if (!inserted) updatedIntervals.add(newInterval);


        // STEP 2: MergeIntervals
        List<int[]> res = new ArrayList<>();
        res.add(updatedIntervals.get(0));

        for (int i=1; i<updatedIntervals.size(); i++) {

            int[] prev = res.get(res.size()-1);
            int[] curr = updatedIntervals.get(i);

            if (prev[1] < curr[0]) {
                res.add(curr);
            } else {
                prev[1] = Math.max(prev[1], curr[1]);
            }
        }

        return res.toArray(int[][]::new);
    }









    public static int[][] insertUsingExpandingNewInterval(int[][] intervals, int[] newInterval) {

        List<int[]> res = new ArrayList<>();
        boolean inserted = false;

        for (int[] curr : intervals) {
            if (inserted) {
                res.add(curr);
            } else if (newInterval[1] < curr[0]) {
                /*
                1. Intervals after newInterval

                    |----nI----|

                                    |---c---|

                    as curr[] is always in sorted order ---> add newInterval first and then keep on adding remaining intervals
                */
                res.add(newInterval);
                res.add(curr);
                inserted = true;
            } else if (newInterval[0] > curr[1]) {
                /*
                2. Intervals before newInterval

                                |----nI----|

                    |---c---|
                */
                res.add(curr);
            } else {
                /*
                3. Overlapping intervals

                if newInterval[1] >= curr[0] || newInterval[0] <= curr[1] ---> OVER-LAPPING
                Here, we don't add curr[] to res, as we are merging curr[] inside newInterval
                */
                newInterval[0] = Math.min(newInterval[0], curr[0]);
                newInterval[1] = Math.max(newInterval[1], curr[1]);
            }
        }

        if(!inserted) {
            res.add(newInterval);
        }

        return res.toArray(int[][]::new);
    }









    public static int[][] insertUsingExpandingNewInterval2(int[][] intervals, int[] newInterval) {
        List<int[]> res = new ArrayList<>();
        int i = 0;
        int n = intervals.length;
        int start = newInterval[0];
        int end = newInterval[1];

        /*
        1. Add intervals before newInterval

                        |----nI----|

            |---c---|

            as curr[] is always in sorted order
        */
        for (; i<n && start > intervals[i][1]; i++) {
            res.add(intervals[i]);
        }

        /*
        2. Merge overlapping intervals ---> EXPANDING newInterval

        now start <= intervals[i][1]

            |----nI----|

                            |---c---|

            &&

            |----nI----|

                    |---c---|


        */
        for (; i<n && end >= intervals[i][0]; i++) {
            start = Math.min(start, intervals[i][0]);
            end = Math.max(end, intervals[i][1]);
        }
        res.add(new int[]{start, end});

        /*
        3. Add remaining intervals
         */
        for(; i<n; i++) {
            res.add(intervals[i]);
        }

        return res.toArray(int[][]::new);
    }
}
