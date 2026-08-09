package Algorithms.Intervals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 26 May 2025
 * @link 452. Minimum Number of Arrows to Burst Balloons <a href="https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/">LeetCode Link</a>
 * @topics Array, Intervals, Sorting, Greedy

      NOTE:
      Arrays.sort(points, (a, b) -> a[1] - b[1]); will overflow if the difference is too large
      For example, if
      a[1] = Integer.MAX_VALUE and b[1] = Integer.MIN_VALUE then a[1] - b[1] == MAX - MIN
      or
      a[0] = -2147483646, b[0] = 2147483646:
      a[0] - b[0] = -2147483646 - 2147483646 = -4294967292

      This is why we use
      (a, b) -> Integer.compare(a[1], b[1])
      or
      Comparator.comparingInt(a -> a[1]) --- little bit more readable but less performant
      to avoid overflow issues.

 */
public class  MinimumNumberOfArrowsToBurstBalloons {
    public static void main(String[] args) {
        int[][] points = {{10,16}, {2,8}, {1,6}, {7,12}};
        System.out.println("findMinArrowShots using range -> " + findMinArrowShotsUsingRange(points)); // Output: 2
        System.out.println("findMinArrows using range 2 -> " + findMinArrowShotsUsingRange(points));
        System.out.println("findMinArrows using range 3 -> " + findMinArrowShotsUsingRange3(points));
    }




    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(1)

        [1,3],[2,8],[4,6],[5,12],[10,16]

        |--------|
            |-------------------|
                     |-----|
                         |------------------------|
                                       |------------------------|

        Just focus on the overlapping range or common range

     */
    public static int findMinArrowShotsUsingRange(int[][] points) {
        Arrays.sort(points, Comparator.comparingInt(p -> p[0]));
        int count = 1;
        int[] range = points[0];

        for (int[] p : points) {
            /* in range */ // p inside range || range inside p p inside range || range inside p
            if (range[0] <= p[0] && range[1] >= p[0] || p[0] <= range[0] && p[1] >= range[0]) {
                range[0] = Math.max(range[0], p[0]);
                range[1] = Math.min(range[1], p[1]);
            } else {
            /* out of range */
                count++;
                range = p;
            }
        }

        return count;
    }





    /**
        |-------------|
              |-------------------|
                 |-------|
                      |-------------|



        |------|
                 |------|
     */
    public static int findMinArrowShotsUsingRange2(int[][] points) {
        Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0])); // start point

        int arrows = 1; // At least one arrow is needed
        int rangeEnd = points[0][1]; // or prevEnd - The end of the first balloon

        for (int i = 1; i < points.length; i++) {
            int start = points[i][0], end = points[i][1];
            if (rangeEnd < start) { /* out of range */ // ---> If the start of the current balloon is greater than the end of the last balloon
                arrows++; // We need a new arrow
                rangeEnd = end; // Update the end to the current balloon's end
            }
            else { /* in range */ // ---> If the current balloon overlaps with the last one, update the end to the minimum of both ends
                rangeEnd = Math.min(rangeEnd, end);
            }
        }
        return arrows;
    }








    /**
        |-------------|
                  |-------|
                         |-------| ===> ❌
                    |-------------------------------|
                                    |------------------|
     */
    public static int findMinArrowShotsUsingRange3(int[][] points) {
        /* end point */
        Arrays.sort(points, Comparator.comparingInt(a -> a[1])); // or (a, b) -> Integer.compare(a[1], b[1])

        int arrows = 1; // At least one arrow is needed
        int prevEnd = points[0][1]; // or rangeEnd - The end of the first balloon

        for (int i = 1; i < points.length; i++) {
            int start = points[i][0], end = points[i][1];
            if (prevEnd < start) {
                arrows++; // We need a new arrow
                prevEnd = end; // Update the end to the current balloon's end
            }
        }

        return arrows;
    }




    public int findMinArrowShotsNotWorking(int[][] points) {

        Arrays.sort(points, Comparator.comparingInt(p -> p[0]));
        // Arrays.sort(points, (a, b)-> a[0]==b[0] ? a[1]-b[1] : a[0]-b);

        Arrays.stream(points).forEach(x -> System.out.print(Arrays.toString(x)));
        System.out.println();

        LinkedList<int[]> q = new LinkedList<>();
        int count = 0;

        for(int[] p : points) {
            if(q.isEmpty() || p[0] <= q.peek()[1]) {
                q.offer(p);
                continue;
            }

            count++;

            if(p[0] > q.peek()[1]) {
                int firstEnd = q.peek()[1];
                while (!q.isEmpty() && firstEnd >= q.peek()[0]) q.poll();
            }

            q.add(p);
        }

        q.forEach(x -> System.out.print(Arrays.toString(x)));

        if (!q.isEmpty()) count++ ;
        // if (!q.isEmpty() && (q.size() == 1 || q.peekFirst()[1] <= q.peekLast()[0]) ) count++;

        return Math.max(count, 1);
    }
}
