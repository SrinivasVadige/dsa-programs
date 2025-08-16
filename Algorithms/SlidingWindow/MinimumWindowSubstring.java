package Algorithms.SlidingWindow;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 28 Sept 2023
 * @link 76. Minimum Window Substring <a href="https://leetcode.com/problems/minimum-window-substring/">LeetCode link</a>
 * @description Given two strings s and t of lengths m and n respectively, return the minimum window substring of s such that every character in t (including duplicates) is included in the window. If there is no such substring, return the empty string "".
 * @topics String, Hash Table, Sliding Window / Dynamic Sliding Window
 * @companies Amazon, Google, Microsoft, Lyft, Bloomberg, LinkedIn, TikTok, SoFi, Snowflake, Snap, Zeta, Adobe, Walmart Labs, Uber, Airbnb, Yandex, Apple, Zopsmart, Salesforce, Apollo, Agoda, MakeMyTrip, thoughtspot, Nagarro

    NOTE:
    1. Here "s" should have all the chars in "t" ---> that means "s" can have more "t" chars i.e if it has {A=1, B=2} then "s" can have {A=11, B=3}
 * This sum is similar to {@link Algorithms.SlidingWindow.LongestSubstringWithoutRepeatingCharacters}
*/
public class MinimumWindowSubstring {
    public static void main(String[] args) {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        System.out.println("minWindow Using Resetting SlidingWindow (isMatch validation) => " + minWindowUsingResettingSlidingWindow(s, t));
        System.out.println("minWindow using ASCII Array SlidingWindow => " + minWindowUsingAsciiArrSlidingWindow(s, t));
        System.out.println("minWindow Using SlidingWindow => " + minWindowUsingSlidingWindow(s, t));
        System.out.println("minWindow Using SlidingWindow Optimized => " + minWindowUsingSlidingWindowOptimized(s, t));
        System.out.println("minWindow BruteForce => " + minWindowBruteForce(s, t));
    }



        /**
     * WORKING but slower than above
     * @TimeComplexity O(m*o), where m=s.length, n=t.length, o=tMap.size
     * @SpaceComplexity O(m+n)
     *

        s="aaaaaaaaaaaabbbbbcddefg"
        t="abcdd"

        tMap - {a=1, b=1, c=1, d=2}

        a a a a a a a a a a a a b b b b b c d d e f g
                                l               r
     */
    public static String minWindowUsingResettingSlidingWindow(String s, String t) { // UsingSlidingWindowBruteForceIsMatchValidation
        int l = 0;
        int m = s.length();
        String res = "";

        Map<Character, Integer> sMap = new HashMap<>();
        Map<Character, Integer> tMap = new HashMap<>();
        for(char c : t.toCharArray()) {
            tMap.merge(c, 1, Integer::sum);
        }

        for(int r=0; r<m; r++) {
            char c = s.charAt(r);
            sMap.merge(c, 1, Integer::sum);
            while(l<=r && isMatch(sMap, tMap)) {
                c = s.charAt(l);
                sMap.merge(c, -1, Integer::sum);

                if(res.isEmpty() || res.length() > r-l+1) {
                    res = s.substring(l, r+1);
                }

                l++;
            }
        }

        return res;

    }

    private static boolean isMatch(Map<Character, Integer> sMap, Map<Character, Integer> tMap) {
        // return tMap.keySet().stream().allMatch(c-> tMap.get(c) <= sMap.getOrDefault(c,0));
        for(char c : tMap.keySet()) {
            if(sMap.getOrDefault(c, 0) < tMap.get(c)) {
                return false;
            }
        }
        return true;
    }


    /**
     * @TimeComplexity O(m+n)
     * @SpaceComplexity O(m+n)
     *
     * Same like {@link #minWindowUsingResettingSlidingWindow} but use "NEED" and "have" instead of isMatch()
     */
    public static String minWindowUsingAsciiArrSlidingWindow(String s, String t) { // UsingAsciiArrSlidingWindow
        int[] res = {0, -1};

        int[] tCounter = new int['z'-'A'+1]; // 'A'=65, 'Z'=90, 'a'=97, 'z'=122 ---> 122-65+1 = 122-64 = 58
        int[] sCounter = new int['z'-'A'+1];

        for(char c: t.toCharArray()) {
            tCounter[c-'A']++;
        }

        final int NEED = (int) Arrays.stream(tCounter).filter(x->x>0).count();
        int have = 0; // do have++ only when c frequency in sMap.get(c) == tMap.get(c), and have-- when c in sMap.get(c) < tMap.get(c)
        int l = 0;

        for(int r=0; r<s.length(); r++) {
            char c = s.charAt(r);

            sCounter[c-'A']++;

            if(tCounter[c-'A'] == sCounter[c-'A']) {
                have++;
            }

            while(have == NEED) {
                if(res[1]==-1 || res[1]-res[0]>r-l) {
                    res[0] = l;
                    res[1] = r;
                }
                c = s.charAt(l);
                sCounter[c-'A']--;

                if(tCounter[c-'A'] > sCounter[c-'A']) {
                    have--;
                }
                l++;
            }
        }

        return s.substring(res[0], res[1]+1);
    }









    /**
     * @TimeComplexity O(n+m)
     * @SpaceComplexity O(m)
     * same like {@link #minWindowUsingResettingSlidingWindow} but use "formed" and "REQUIRED" instead of isEqual()
     * and instead of r-while-loop and then l-while-loop, use l-while-loop inside r-while-loop i.e., increment r by 1 and do l-while-loop
     */
    public static String minWindowUsingSlidingWindow(String s, String t) {
        if (s.isEmpty() || t.isEmpty() || s.length() < t.length()) {
            return "";
        }

        Map<Character, Integer> tMap = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            tMap.merge(t.charAt(i), 1, Integer::sum);
        }

        final int NEED = tMap.size();
        int l = 0, r = 0;
        int have = 0; // have++ only when c in sMap == tMap, and have-- when c in sMap < tMap

        Map<Character, Integer> windowCounts = new HashMap<>(); // sMap
        int[] ans = { -1, 0, 0 }; // [size, l, r]

        while (r < s.length()) {
            char c = s.charAt(r);
            windowCounts.merge(c, 1, Integer::sum);

            if (tMap.containsKey(c) && windowCounts.get(c).equals(tMap.get(c))) { // or tMap.getOrDefault(c, 0).equals(sMap.get(c))
                have++;
            }

            while (l <= r && have == NEED) {
                c = s.charAt(l);
                if (ans[0]==-1 || r-l+1 < ans[0]) {
                    ans[0] = r-l+1;
                    ans[1] = l;
                    ans[2] = r;
                }

                windowCounts.merge(c, -1, Integer::sum);
                if (tMap.containsKey(c) && windowCounts.get(c) < tMap.get(c) ) { // or tMap.getOrDefault(c, 0) > sMap.get(c)
                    have--;
                }
                l++;
            }

            r++;
        }

        return ans[0] == -1 ? "" : s.substring(ans[1], ans[2] + 1);
    }










    public static String minWindowUsingSlidingWindowOptimized(String s, String t) {
        if (s.isEmpty() || t.isEmpty()) {
            return "";
        }

        Map<Character, Integer> tMap = new HashMap<>();

        for (int i = 0; i < t.length(); i++) {
            tMap.merge(t.charAt(i), 1, Integer::sum);
        }

        int required = tMap.size();

        List<Map.Entry<Integer, Character>> filteredS = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (tMap.containsKey(c)) {
                filteredS.add(new AbstractMap.SimpleEntry<>(i, c));
            }
        }

        int l = 0, r = 0, formed = 0;
        Map<Character, Integer> windowCounts = new HashMap<>();
        int[] ans = { -1, 0, 0 };

        while (r < filteredS.size()) {
            char c = filteredS.get(r).getValue();
            windowCounts.merge(c, 1, Integer::sum);

            if (tMap.containsKey(c) && windowCounts.get(c).intValue() == tMap.get(c).intValue()) {
                formed++;
            }

            while (l <= r && formed == required) {
                c = filteredS.get(l).getValue();

                int end = filteredS.get(r).getKey();
                int start = filteredS.get(l).getKey();
                if (ans[0] == -1 || end - start + 1 < ans[0]) {
                    ans[0] = end - start + 1;
                    ans[1] = start;
                    ans[2] = end;
                }

                windowCounts.put(c, windowCounts.get(c) - 1);
                if ( tMap.containsKey(c) && windowCounts.get(c) < tMap.get(c) ) {
                    formed--;
                }
                l++;
            }
            r++;
        }
        return ans[0] == -1 ? "" : s.substring(ans[1], ans[2] + 1);
    }











    /**
     * @TimeComplexity - O(n^2)
     * @Intution - the chars in t strings <= chars in subStr. So we can use 2 pointer sliding window technique
     * @Approach - Check each and every subString and it's length >= t length
    */
    public static String minWindowBruteForce(String s, String t) {
        if(t.length() > s.length()) return "";
        String subStr = "";
        Map<String, Integer> tMap = Arrays.stream(t.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e->1)) );

        for(int i=0; i<s.length(); i++){
            for(int j=i+t.length(); j<s.length()+1; j++){ // non exclusive
                String tempSub = s.substring(i, j);
                Map<String, Integer> subStrMap = Arrays.stream(tempSub.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e->1)) );
                // check t in tempSub
                if(validate(tMap, subStrMap)){
                    if(subStr.length() > tempSub.length() || subStr.isEmpty())
                        subStr = tempSub;
                }
            }
        }
        return subStr;
    }
    static Boolean validate(Map<String, Integer> tMap, Map<String, Integer> subStrMap){
        for(Map.Entry<String, Integer> te: tMap.entrySet()){
            if(subStrMap.getOrDefault(te.getKey(), 0) < te.getValue())
                return false;
        }
        tMap = new HashMap<>();
        System.out.println(tMap);
        return true;
    }

}
