package Algorithms.Intervals;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 20 April 2025
 *
 * timeSeries = [1, 4], duration = 2
 * Intervals: [1,3) and [4,6)
 * No overlap → total poisoned = 2 + 2 = 4
 *
 * timeSeries = [1, 2], duration = 2
 * Intervals:[1,3) and [2,4)
 * Overlap → merged interval = [1,4) → total poisoned = 3
 *
 * Similarly, when timeSeries = [1, 4, 6, 7] and duration = 2
 *
 *
 *
 *              8  |
 *              7  |
 *              6  |
 *              5  |
 *              4  |                   _______
 *              3  |                ______
 *       ↑      2  |          ______
 *       i      1  | ______
 *                 |______________________________
 *                0  1  2  3  4  5  6  7  8  9 10
 *
 *                    timeSeries[i]  ->
 *
 * Here, timeSeries[i] - timeSeries[i-1] means --- 4-1 = 3 which is greater than duration:2 --- No overlap
 * If no overlap, add given duration to the result as the previous interval can only take the duration of 2.
 * If there is an overlap, add the difference between the two intervals to the result
 * Similarly, for the next intervals
 *
 */
public class TeemoAttacking {
    public static void main(String[] args) {
        int[] timeSeries = {1, 4, 6, 7};
        int duration = 2;
        System.out.println(findPoisonedDuration(timeSeries, duration)); // Output: 7
    }


    public static int findPoisonedDuration(int[] timeSeries, int duration) {
        if (timeSeries.length == 1) return duration;

        int ans = duration;
        for (int i=1; i<timeSeries.length; i++) {
            int intervalDiff = timeSeries[i] - timeSeries[i-1];
            if (intervalDiff < duration) { // Overlapped
                ans += intervalDiff;
            } else { // No overlap
                ans += duration;
            }
        }
        return ans;
    }




    public static int findPoisonedDuration2(int[] timeSeries, int duration) {
        int result = 0;
        for (int i = 0; i < timeSeries.length - 1; i++) {
            result += Math.min(timeSeries[i + 1] - timeSeries[i], duration);
        }
        return result + duration;
    }
}
