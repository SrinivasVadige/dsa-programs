package Algorithms.Strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 29 June 2025
 * @link 3598. Longest Common Prefix Between Adjacent Strings After Removals <a href="https://leetcode.com/problems/longest-common-prefix-between-adjacent-strings-after-removals/">...</a>
 * @topics String
 */
public class LongestCommonPrefixBetweenAdjacentStringsAfterRemovals {
    public static void main(String[] args) {
        String[] words = {"flower","flow","flight"};
        System.out.printf("longestCommonPrefix           => %s\n", Arrays.toString(longestCommonPrefix(words)));
        System.out.printf("longestCommonPrefixMyApproach => %s\n", Arrays.toString(longestCommonPrefixMyApproach(words)));
    }

    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n)
     *
        words = ["apple", "application", "apricot", "banana", "band", "bat"]
        lcp = [4, 3, 1, 2, 2]   // based on adjacent pairs
                 ^  ^  ^  ^  ^
               [0] [1][2][3][4]

        prefixMax = [
           4,                      // max(lcp[0])
           max(4,3)=4,             // max(lcp[0..1])
           max(4,3,1)=4,
           max(4,3,1,2)=4,
           max(4,3,1,2,2)=4
         ]

         suffixMax = [
           max(4,3,1,2,2)=4,
           max(3,1,2,2)=3,
           max(1,2,2)=2,
           max(2,2)=2,
           2
         ]
     */
    public static int[] longestCommonPrefix(String[] words) {
        int n = words.length;
        int[] answer = new int[n];
        if (n <= 2) {
            return answer;
        }

        // Precompute LCP of all adjacent word pairs without skipping any i --> i.e [0,1], [1,2], [2,3], [3,4], ..., [n-2, n-1]
        // lcp[i] stores ith & (i+1)th words lcp.. so only n-1 lcp values. last ele in lcp is n-2
        int[] lcp = new int[n - 1];
        for (int i = 0; i + 1 < n; i++) {
            lcp[i] = getLcpLen(words[i], words[i + 1]);
        }
        /*
            words =     [0, 1, 2, 3, 4]
            prefixMax = [0, 1, 2, 3]
            lcp =       [0, 1, 2, 3]
            suffixMax = [0, 1, 2, 3]

         */
        int[] prefixMax = new int[n - 1]; // or leftMax to maintain max value of lcp[] from i==0 to i==n-2 --> left to right
        int[] suffixMax = new int[n - 1]; // or rightMax to maintain max value of lcp[] from i==n-2 to i==0 --> right to left

        // 0 to i maxValue till now
        prefixMax[0] = lcp[0]; // initially 1st lcp i.e lcp[0] is max till i=0
        for (int i = 1; i < n - 1; i++) {
            prefixMax[i] = Math.max(prefixMax[i - 1], lcp[i]);
        }

        suffixMax[n - 2] = lcp[n - 2];
        for (int i = n - 3; i >= 0; i--) {
            suffixMax[i] = Math.max(suffixMax[i + 1], lcp[i]);
        }

        for (int i = 0; i < n; i++) { // skip index
            int best = 0;
            // ➤ Case 1: Left side max (skip affects lcp[i-1])
            if (i-2 >= 0) {    // skipI=i --> calculate lcp of (i-2) and (i-1) words --> already calculated in prefixMax[i-2]
                best = Math.max(best, prefixMax[i - 2]);
            }
            // ➤ Case 2: Right side max (skip affects lcp[i])
            if (i+1 <= n-2) {    // skipI=i --> calculate lcp of (i+1) and (i+2) words --> already calculated in suffixMax[i+1]
                best = Math.max(best, suffixMax[i + 1]);
            }
            // ➤ Case 3: New adjacency (i.e., [i-1, i+1])
            if (i > 0 && i < n-1) {    // skipI=i --> calculate lcp of (i-1) and (i+1) words
                best = Math.max(best, getLcpLen(words[i - 1], words[i + 1]));
            }
            answer[i] = best;
        }

        return answer;
    }

    public static int getLcpLen(String a, String b) {
        int len = Math.min(a.length(), b.length());
        int i = 0;
        while (i < len && a.charAt(i) == b.charAt(i)) {
            i++;
        }
        return i; // length of lcp
    }











    /**
     * Working but TLE
     * @TimeComplexity O(n² * m²), where n=words.length -- number of words and m=words[i].length -- average length of strings
     */
    static HashMap<String, Integer> map = new HashMap<>();
    public static int[] longestCommonPrefixMyApproach(String[] words) {
        int n = words.length;
        int[] answer = new int[n];
        if(n<3) {
            return answer;
        }

        for(int skipI=0; skipI<n; skipI++) {
            answer[skipI] = neighbourWords(words, n, skipI);
        }

        return answer;
    }

    private static int neighbourWords(String[] words, int n, int skipI) {
        int maxLen = 0;
        for(int i=0; i<n-1; i++) {
            if (i == skipI) {
                i++;
            }

            int nextI = i+1;
            if(nextI == skipI) {
                nextI++;
            }

            if(nextI == n) {
                return maxLen;
            }

            maxLen = Math.max(maxLen, commonPrefixLen(words[i], words[nextI]));

            if (nextI == skipI) {
                i++;
            }
        }
        return maxLen;
    }

    private static int commonPrefixLen(String s1, String s2) {
        final String KEY = s1+","+s2;
        if(map.containsKey(KEY)) {
            return map.get(KEY);
        }

        if (s1.equals(s2)) {
            map.put(KEY, s1.length());
            return s1.length();
        }

        int len = 0;
        for(int i=0; i< Math.min(s1.length(), s2.length()); i++) {
            if(s1.charAt(i)==s2.charAt(i)) {
                len++;
            } else {
                break;
            }
        }

        map.put(KEY, len);
        return len;
    }











    public static int[] longestCommonPrefixUsingSparseTable(String[] words) {
        int n = words.length;
        int[] answer = new int[n];
        if (n < 3) {
            return answer;
        }

        int[] pref = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            pref[i] = commonPrefixLen2(words[i], words[i + 1]);
        }

        SparseTable st = new SparseTable(pref);

        for (int skipI = 0; skipI < n; skipI++) {
            int maxLen = 0;

            // Max prefix excluding pairs adjacent to skipI
            // ranges: [0..skipI-2] and [skipI+1..n-2]
            maxLen = Math.max(st.query(0, skipI - 2), st.query(skipI + 1, n - 2));

            // Check new adjacency if possible
            if (skipI > 0 && skipI < n - 1) {
                maxLen = Math.max(maxLen, commonPrefixLen2(words[skipI - 1], words[skipI + 1]));
            }

            answer[skipI] = maxLen;
        }

        return answer;
    }

    private static int commonPrefixLen2(String s1, String s2) {
        int minLen = Math.min(s1.length(), s2.length());
        int i = 0;
        while (i < minLen && s1.charAt(i) == s2.charAt(i)) {
            i++;
        }
        return i;
    }




    static class SparseTable {
        int[][] st;
        int[] log;

        SparseTable(int[] arr) {
            int n = arr.length;
            log = new int[n + 1];
            for (int i = 2; i <= n; i++) {
                log[i] = log[i / 2] + 1;
            }
            int k = log[n];
            st = new int[n][k + 1];
            for (int i = 0; i < n; i++) {
                st[i][0] = arr[i];
            }
            for (int j = 1; j <= k; j++) {
                for (int i = 0; i + (1 << j) <= n; i++) {
                    st[i][j] = Math.max(st[i][j - 1], st[i + (1 << (j - 1))][j - 1]);
                }
            }
        }

        int query(int l, int r) {
            if (l > r) return 0;
            int j = log[r - l + 1];
            return Math.max(st[l][j], st[r - (1 << j) + 1][j]);
        }
    }
}
