package Algorithms.Hashing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 15 April 2025
 */
public class UniqueNumberOfOccurrences {
    public static void main(String[] args) {
        int[] arr = {1,2,2,1,1,3};
        System.out.println("uniqueOccurrences(arr) => " + uniqueOccurrences(arr));
        System.out.println("uniqueOccurrencesMyApproach(arr) => " + uniqueOccurrencesMyApproach(arr));
    }

    public static boolean uniqueOccurrences(int[] arr) {
        int[] count = new int[2001]; // -1000 <= arr[i] <= 1000

        for (int num: arr) count[num+1000]++;

        Set<Integer> countSet = new HashSet<>();
        for (int num: count) {
            if (num == 0) continue;
            if (countSet.contains(num)) return false;
            countSet.add(num);
        }
        return true;
    }




    public static boolean uniqueOccurrencesMyApproach(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int x: arr) map.merge(x, 1, Integer::sum);

        Set<Integer> vals = new HashSet<>();

        for(int v: map.values()) {
            if(!vals.add(v)) return false;
        }

        return true;
    }
}
