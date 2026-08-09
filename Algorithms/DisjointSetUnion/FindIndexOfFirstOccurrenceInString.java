package Algorithms.DisjointSetUnion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 21 March 2025
 * @link 28. Find the Index of the First Occurrence in a String <a href="https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/">LeetCode link</a>
 * @topics String, Two Pointers, String Matching
 */
public class FindIndexOfFirstOccurrenceInString {
    public static void main(String[] args) {
        String haystack="hello", needle="ll";
        System.out.println(strStr(haystack, needle));
    }

    /**
     * @TimeComplexity O(n*m), where n = haystack.length(), m = needle.length()
     * @SpaceComplexity O(1)
     */
    public static int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }


    /**
     * @TimeComplexity O(n+m), where n = haystack.length(), m = needle.length()
     * @SpaceComplexity O(1)
     *
     * KMP = Knuth-Morris-Pratt
     * In KMP, we pre-process the needle to get the longest prefix suffix array (lps)
     */
    public static int strStrUsingKMP(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();
        if (m > n) return -1;
        int[] lps = new int[n];

        int prevLps = 0, i=1;
        while (i < m) {
            if (needle.charAt(i) == needle.charAt(prevLps)) lps[i++] = ++prevLps;
            else if (prevLps == 0) lps[i++] = 0;
            else prevLps = lps[prevLps-1];
        }

        i=0;
        int j = 0;
        while(i < n) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            } else {
                if (j == 0) i++;
                else j = lps[j-1];
            }
            if (j == m) return i - m;
        }
        return -1;
    }


    public static int strStrUsingKMP2(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();
        if (m > n) return -1;
        int[] lps = new int[n];

        int prevLps = 0, i=1;
        while (i < m) {
            if (needle.charAt(i) == needle.charAt(prevLps)) lps[i++] = ++prevLps;
            else if (prevLps > 0) prevLps = lps[prevLps-1];
            else i++;
        }

        int j = 0;
        for (i = 0; i < n; i++) {
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) j = lps[j-1];
            if (haystack.charAt(i) == needle.charAt(j)) j++;
            if (j == m) return i - m + 1;
        }
        return -1;

    }



    /**
     * @TimeComplexity O(n*m), where n = haystack.length(), m = needle.length()
     * @SpaceComplexity O(1)
     */
    public static int strStr2(String haystack, String needle) {
        for(int i=0; i<haystack.length(); i++) { // or i<=haystack.length()-needle.length()
            if(haystack.startsWith(needle, i)) return i;
        }
        return -1;
    }



    public int strStr3(String haystack, String needle) {
        for(int i=0; i<haystack.length(); i++) {
            if (haystack.substring(i).startsWith(needle)) return i;
        }
        return -1;
    }


    public int strStr4(String haystack, String needle) {
        for(int i=0; i<haystack.length(); i++) {
            int j=0;
            for(; j<needle.length(); j++) {
                if (haystack.charAt(i+j) != needle.charAt(j)) {
                    break;
                }
            }
            if (j == needle.length()-1) return i;
        }
        return -1;
    }



    /**
     * Same as {@link #strStr4}
     */
    public int strStr5(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();

        int i = 0;
        int j = 0;
        while (i <= n - m) {
            while (haystack.charAt(i + j) == needle.charAt(j)) {
                j++;
                if (j == m) {
                    return i;
                }
            }
            i++;
            j = 0;
        }
        return -1;
    }



    public static int strStrUsingSubStringEquals(String haystack, String needle) {
        for (int i = 0; i + needle.length() <= haystack.length(); i++) {
            if (haystack.substring(i, i + needle.length()).equals(needle)) return i;
        }
        return -1;
    }


    public int strStrMyApproachOld(String haystack, String needle) {
        for(int i=0; i<haystack.length(); i++) {
            while(i<haystack.length() && haystack.charAt(i) != needle.charAt(0)) i++;
            if (haystack.substring(i).startsWith(needle)) return i;
        }
        return -1;
    }


    public static int strStr6(String haystack, String needle) {
        Pattern pattern = Pattern.compile(needle);
        Matcher matcher = pattern.matcher(haystack);
        if(matcher.find()){
            return matcher.start();
        }
        return -1;
    }
}
