package Algorithms.GreedyAlgorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 28 Feb 2025
 */
public class PartitionLabels {
    public static void main(String[] args) {
        String s = "ababcbacadefegdehijhklij";
        System.out.println("partitionLabels(s) => " + partitionLabels(s));
        System.out.println("partitionLabelsMyApproach(s) => " + partitionLabelsMyApproach(s));
    }

    public static List<Integer> partitionLabels(String s) {
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i; // last index of each character
        }
        List<Integer> lst = new ArrayList<>();
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']); // end of current pattern
            if (i == end) { // after reaching end of current pattern
                lst.add(end - start + 1);
                start = i + 1; // for next pattern
            }
        }
        return lst;
    }

    /**
        THOUGHTS:
        ---------
        1) When s="ababcbacadefegdehijhklij" then check isSamePattern() for all possible patterns like "a", "ab", "aba", "abab", "ababc", "ababcb", "ababcba", "ababcbac", "ababcbaca", "ababcbacad"
        2) If true increase current pattern count else break and repeat the process from i+1
     */
    public static List<Integer> partitionLabelsMyApproach(String s) {
        Set<Character> set = new HashSet<>(); // current pattern
        List<Integer> lst = new ArrayList<>();

        int i=0;
        while (i < s.length()) {

            // for current pattern
            set.clear();
            lst.add(1);
            for (; i<s.length(); i++) {
                set.add(s.charAt(i)); // add to current pattern
                if (isSamePattern(s, i, lst, set))
                    lst.set(lst.size()-1, lst.get(lst.size()-1)+1); // ++ the last number if isSamePattern or useSome tempVariable
                else {
                    i++; // cause in isSamePattern we check from i+1
                    break;
                }
            }
        }


        return lst;
    }
    // instead of boolean, return the index where it is true then use that index as i in above partitionLabelsMyApproach and increase the lst.set(lst.size()-1, currentVal + iDiff) accordingly. But need to take care of set with pattern
    private static boolean isSamePattern(String s, int start, List<Integer> lst, Set<Character> set) {

        for (int i=start+1; i<s.length(); i++) {
            if (set.contains(s.charAt(i))) return true;
        }

        return false;
    }
}
