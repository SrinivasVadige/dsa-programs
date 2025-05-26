package Algorithms.Intervals;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 26 May 2025
 * @link 452. Minimum Number of Arrows to Burst Balloons https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/
 */
public class MinimumNumberOfArrowsToBurstBalloons {
    public static void main(String[] args) {
        int[][] points = {{10,16}, {2,8}, {1,6}, {7,12}};
        System.out.println(findMinArrowShots(points)); // Output: 2
    }



    /**
        |-------------|
                  |-------|
              |-------------------|
                      |-------------|
                          |-------| ===> ❌



        |------|
                 |------|
     */
    public static int findMinArrowShots(int[][] points) {
        Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1])); // end point
        /**
         * NOTE:
         * Arrays.sort(points, (a, b) -> a[1] - b[1]); will overflow if the difference is too large
         * For example, if a[1] = Integer.MAX_VALUE and b[1] = Integer.MIN_VALUE then a[1] - b[1] == Integer.MAX_VALUE - Integer.MIN_VALUE
         * Or a[0] = -2147483646, b[0] = 2147483646:
         * a[0] - b[0] = -2147483646 - 2147483646 = -4294967292
         *
         * This is why we use
         * (a, b) -> Integer.compare(a[1], b[1])
         * or
         * Comparator.comparingInt(a -> a[1]) --- little bit more readable but less performant
         * to avoid overflow issues.
         *
         */

        int arrows = 1; // At least one arrow is needed
        int prevEnd = points[0][1]; // The end of the first balloon

        for (int i = 1; i < points.length; i++) {
            int start = points[i][0], end = points[i][1];
            // If the start of the current balloon is greater than the end of the last balloon
            if (start > prevEnd) {
                arrows++; // We need a new arrow
                prevEnd = end; // Update the end to the current balloon's end
            }
        }

        return arrows;
    }


    /**
        |-------------|
              |-------------------|
                 |-------|
                      |-------------|



        |------|
                 |------|
     */
    public static int findMinArrowShots2(int[][] points) {
        Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0])); // start point

        int arrows = 1; // At least one arrow is needed
        int prevEnd = points[0][1]; // The end of the first balloon

        for (int i = 1; i < points.length; i++) {
            int start = points[i][0], end = points[i][1];
            // If the start of the current balloon is greater than the end of the last balloon
            if (start > prevEnd) {
                arrows++; // We need a new arrow
                prevEnd = end; // Update the end to the current balloon's end
            }
            else {
                // If the current balloon overlaps with the last one, update the end to the minimum of both ends
                prevEnd = Math.min(prevEnd, end);
            }
        }
        return arrows;
    }
}
