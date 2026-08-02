package Algorithms.IntegerArray;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 01 August 2026
 * @link 1427. Perform String Shifts <a href="https://leetcode.com/problems/perform-string-shifts/">LeetCode Link</a>
 * @topics Array, Math, String
 * @companies Google(6), Bloomberg(3), Microsoft(2)
 * @description Perform String Shifts
 * Given a lowercase string s and a list of shift operations, apply every operation in order and return the resulting string.
 * Each operation has two values: [direction, amount].
 * direction = 0: rotate the string left by amount positions.
 * direction = 1: rotate the string right by amount positions.
 * A left rotation moves characters from the beginning to the end. A right rotation moves characters from the end to the beginning.
 * Example
 * Input: s = "abc", shift = [[0,1],[1,2]]Output: "cab"
 * Explanation: rotate left once: "abc" → "bca"; then rotate right twice: "bca" → "cab".
 * Constraints
 * 1 <= s.length <= 100
 * s contains only lowercase English letters.
 * 1 <= shift.length <= 100
 * Each operation contains exactly two integers.
 * direction is either 0 or 1.
 * 0 <= amount <= 100
 */
public class PerformStringShifts {
    public static void main() {
        String s = "abc";
        int[][] shift = {{0,1},{1,2}};
        System.out.println("stringShift Using EachShift: " + stringShiftUsingEachShift(s, shift));
        System.out.println("stringShift Using OverallShifts: " + stringShiftUsingOverallShifts(s, shift));
        System.out.println("stringShift Using OverallShifts Improved: " + stringShiftUsingOverallShiftsImproved(s, shift));
    }


    /**
     * @TimeComplexity O(N*L) where N = shift.length and L = s.length()
     * @SpaceComplexity O(L)
     */
    public static String stringShiftUsingEachShift(String s, int[][] shift) {
        int n = s.length();
        for (int[] move: shift) {
            int dir = move[0];
            int amount = move[1] % n;
            if (amount == 0) continue;

            if (dir == 0) { //left
                String leftS = s.substring(0, amount);
                String rightS = s.substring(amount);
                s = rightS + leftS;
            } else { // right
                String leftS = s.substring(0, n-amount);
                String rightS = s.substring(n-amount);
                s = rightS + leftS;
            }
        }
        return s;
    }






    /**
     * @TimeComplexity O(N+L) where N = shift.length and L = s.length()
     * @SpaceComplexity O(L)
     */
    public static String stringShiftUsingOverallShifts(String string, int[][] shift) {
        int[] overallShifts = new int[2];
        for (int[] move : shift) {
            overallShifts[move[0]] += move[1];
        }
        int leftShifts = overallShifts[0];
        int rightShifts = overallShifts[1];

        int n = string.length();
        if (leftShifts > rightShifts) {
            leftShifts = (leftShifts - rightShifts) % n;
            string = string.substring(leftShifts) + string.substring(0, leftShifts);
        } else if (rightShifts > leftShifts) {
            rightShifts = (rightShifts - leftShifts) % n;
            string = string.substring(n - rightShifts) + string.substring(0, n - rightShifts);
        }

        return string;
    }





    public static String stringShiftUsingOverallShiftsImproved(String s, int[][] shift) {
        int leftShifts = 0; // Count the number of left shifts. A right shift is a negative left shift.
        for (int[] move : shift) {
            if (move[0] == 1) {
                move[1] = -move[1];
            }
            leftShifts += move[1];
        }

        leftShifts = Math.floorMod(leftShifts, s.length()); // Convert back to a positive, do left shifts, and return.
        s = s.substring(leftShifts) + s.substring(0, leftShifts);
        return s;
    }
}
