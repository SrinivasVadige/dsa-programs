package Algorithms.SlidingWindow;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 27 Feb 2025
 * @link 159. Longest Substring with At Most Two Distinct Characters <a href="https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/">LeetCode link</a>
 * @topics Hash Table, String, Sliding Window
 * @companies Meta(11), TikTok(3), Google(2), Amazon(2)
 * @description Given a string, find the length of the longest continuous section that uses no more than two different characters.
 * For example, in "ccaabbb", the longest valid section is "aabbb", so the answer is 5.
 */
public class LongestSubstringWithAtMostTwoDistinctCharacters {
    public static void main(String[] args) {
        String s = "ccaabbb";
        System.out.println("lengthOfLongestSubstringTwoDistinct using Brute Force => " + lengthOfLongestSubstringTwoDistinctUsingBruteForce(s));
        System.out.println("lengthOfLongestSubstringTwoDistinct using Sliding Window 1 => " + lengthOfLongestSubstringTwoDistinctUsingSlidingWindow1(s));
        System.out.println("lengthOfLongestSubstringTwoDistinct using Sliding Window 2 => " + lengthOfLongestSubstringTwoDistinctUsingSlidingWindow2(s));
    }


    /**

        ccaabbb

        ccaabbb
        |___|

     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(1)
     */
    public static int lengthOfLongestSubstringTwoDistinctUsingBruteForce(String s) {
        int n = s.length();
        int max = 0;
        for (int i=0; i<n; i++) {
            // while (i>0 && i<n && s.charAt(i-1) == s.charAt(i)) i++; // performance improvement
            int count = 1;
            int j = i;
            Set<Character> set = new HashSet<>();
            for (; j<n; j++) {
                char jc = s.charAt(j);
                if (!set.contains(jc) && set.size() == 2) break;
                set.add(jc);
            }
            max = Math.max(max, j-i);
        }
        return max;
    }


    /**

        ccaabbb

        ccaabbb
          |___|

        find max when map.size() == 3
        increment l till map.size() == 2

     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int lengthOfLongestSubstringTwoDistinctUsingSlidingWindow1(String s) {
        int n = s.length(), l = 0, max = 0;
        Map<Character, Integer> counter = new HashMap<>();

        for(int r=0; r<n; r++) {
            char rc = s.charAt(r);
            counter.merge(rc, 1, Integer::sum);
            if (counter.size() == 3) max = Math.max(max, r-l);

            while (l < n && counter.size() == 3) {
                char lc = s.charAt(l++);
                counter.merge(lc, -1, Integer::sum);
                if (counter.get(lc) == 0) counter.remove(lc);
            }

        }
        return Math.max(max, n-l);
    }



    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int lengthOfLongestSubstringTwoDistinctUsingSlidingWindow2(String s) {
        int n = s.length();
        if (n < 3) return n;

        int l = 0;
        int r = 0;
        Map<Character, Integer> map = new HashMap<>(); // save char's rightmost position instead of counter

        int max = 2;

        while (r < n) {
            map.put(s.charAt(r), r++);
            if (map.size() == 3) {
                int del_idx = Collections.min(map.values()); // min of all values
                map.remove(s.charAt(del_idx));
                l = del_idx + 1;
            }

            max = Math.max(max, r - l);
        }
        return max;
    }
}
