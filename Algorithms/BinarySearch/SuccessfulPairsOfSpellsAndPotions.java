package Algorithms.BinarySearch;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 10 May 2023
 */
public class SuccessfulPairsOfSpellsAndPotions {
    public static void main(String[] args) {
        int[] spells = {15,39,38,35,33,25,31,12,40,27,29,16,22,24,7,36,29,34,24,9,11,35,21,3,33,10,9,27,35,17,14,3,35,35,39,23,35,14,31,7};
        int[] potions = {25,19,30,37,14,30,38,22,38,38,26,33,34,23,40,28,15,29,36,39,39,37,32,38,8,17,39,20,4,39,39,7,30,35,29,23};
        long success = 317;
        System.out.println("successfulPairsMyApproach => " + Arrays.toString(successfulPairsMyApproach(spells, potions, success)));
        System.out.println("successfulPairs => " + Arrays.toString(successfulPairs(spells, potions, success)));
        System.out.println("successfulPairsSuggested => " + Arrays.toString(successfulPairsSuggested(spells, potions, success)));
    }
    /**
        OBSERVATIONS:
        -------------
        pairs[i] = num of potions where it's product with spells[i] >= success
        pairs.length = spells.length = n


        APPROACHES:
        ----------
        1. brute force ---> nm

        2. potionsMap ---> nm

        3. sorted potions[] ----> mlogm + nlogm
            a. linear search
            b. bs
     */
    public static int[] successfulPairsMyApproach(int[] spells, int[] potions, long success) {
        int n = spells.length, m = potions.length;
        Arrays.sort(potions);
        HashMap<Integer, Integer> memo = new HashMap<>();

        int[] pairs = new int[n];

        for (int i = 0; i < n; i++) {
            int spell = spells[i];
            if (memo.containsKey(spell)) {
                pairs[i] = memo.get(spell);
                continue;
            }
            // Quotient calculation
            long quotient = (success / (long) spell); // Use long to avoid overflow
            long remainder = (success % (long) spell); // Use long to avoid overflow
            if (remainder > 0) quotient++;

            int potionI = binarySearchMyApproach(potions, quotient, m); // Return m if not found
            int numOfPotions = m - potionI;
            pairs[i] = numOfPotions;
            memo.put(spell, numOfPotions);
        }
        return pairs;
    }
    private static int binarySearchMyApproach(int[] potions, long target, int n) {
        int l = 0, r = n - 1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (potions[m] == target) {
                while (m > 0 && potions[m - 1] == target) m--; // Ensure the first occurrence
                return m;
            } else if (potions[m] < target) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return l; // Return the first index where potions[index] >= target
    }







    /** */
    public static int[] successfulPairs(int[] spells, int[] potions, long success) {
        int n = spells.length, m = potions.length, pairs[] = new int[n];
        Arrays.sort(potions);
        for (int i = 0; i < n; i++) {
            int spell=spells[i], l=0, r=m-1;
            while (l<=r) {
                int mid = l+(r-l)/2;
                long product = (long) spell * potions[mid];
                if (product >= success) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            pairs[i] = m - l; // how many on the right side of target potion (including target if exists)
        }
        return pairs;
    }





    public static int[] successfulPairs2(int[] spells, int[] potions, long success) {
        int n = spells.length, m = potions.length, pairs[] = new int[n];
        Arrays.sort(potions);
        for (int i = 0; i < n; i++) {
            int spell=spells[i], l=0, r=m-1, idx=m;
            while (l<=r) {
                int mid = l+(r-l)/2;
                long product = (long) spell * potions[mid];
                if (product >= success) {
                    r = mid - 1;
                    idx = mid;
                } else {
                    l = mid + 1;
                }
            }
            pairs[i] = m - idx;
        }
        return pairs;
    }









    public static int[] successfulPairsSuggested(int[] spells, int[] potions, long success) {
        int n = spells.length, m = potions.length;
        Arrays.sort(potions);
        int[] pairs = new int[n];

        for (int i = 0; i < n; i++) {
            long spell = spells[i];
            long required = (success + spell - 1) / spell; // Ceiling of success / spell
            int potionIndex = binarySearch(potions, required, m); // Find the first potion >= required
            pairs[i] = m - potionIndex; // Count of successful pairs
        }
        return pairs;
    }

    private static int binarySearch(int[] potions, long required, int n) {
        int left = 0, right = n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (potions[mid] >= required) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left; // First index where potions[index] >= required
    }
}
