package Algorithms.PrefixSum;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 14 April 2025
 */
public class FindTheHighestAltitude {
    public static void main(String[] args) {
        int[] gain = {-5,1,5,0,-7};
        System.out.println("largestAltitude(gain) => " + largestAltitude(gain));
        System.out.println("largestAltitudeMyApproach(gain) => " + largestAltitudeMyApproach(gain));
    }

    public static int largestAltitude(int[] gain) {
        int max = 0, sum = 0;
        for (int i = 0; i < gain.length; i++) {
            sum += gain[i];
            max = Math.max(max, sum);
        }
        return max;
    }

    public static int largestAltitudeMyApproach(int[] gain) {
        for(int i=1; i<gain.length; i++) gain[i]+=gain[i-1];
        return Math.max(0, Arrays.stream(gain).max().getAsInt());
    }
}
