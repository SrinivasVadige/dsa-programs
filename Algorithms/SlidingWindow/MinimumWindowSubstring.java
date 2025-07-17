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
        System.out.println("minWindow Using SlidingWindow => " + minWindowUsingSlidingWindow(s, t));
        System.out.println("minWindow Using SlidingWindow Optimized => " + minWindowUsingSlidingWindowOptimized(s, t));
        System.out.println("minWindow BruteForce => " + minWindowBruteForce(s, t));
        System.out.println("minWindow Using Resetting SlidingWindow => " + minWindowUsingResettingSlidingWindow(s, t));
        System.out.println("minWindow Using SlidingWindow2 => " + minWindowUsingSlidingWindow2(s, t));
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

            if (tMap.containsKey(c) && windowCounts.get(c).equals(tMap.get(c))) {
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
                if (tMap.containsKey(c) && windowCounts.get(c) < tMap.get(c) ) {
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
    public static String minWindowUsingResettingSlidingWindow(String s, String t) {
        Map<Character, Integer> tMap = new HashMap<>();
        for(char c: t.toCharArray()) {
            tMap.merge(c, 1, Integer::sum);
        }

        int minL = 0, minR = -1;
        int m = s.length();
        int l=0, r=0;

        Map<Character, Integer> sMap = new HashMap<>();
        while(r<m) {
            // Move r pointer to right
            while(r<m && !isEqual(tMap, sMap)) {
                sMap.merge(s.charAt(r++), 1, Integer::sum);
            }
            r--;

            // Move l pointer to right
            boolean isBreak = false;
            while(l<=r) {
                if(isEqual(tMap, sMap)) {
                    if((minR == -1 || (minR-minL) > (r-l))) {
                        minR = r;
                        minL = l;
                    }
                    sMap.merge(s.charAt(l), -1, Integer::sum);
                } else {
                    isBreak = true;
                    break;
                }
                l++;
            }

            // when l==r in above while loop then Reset sMap --> start fresh
            if(isBreak) {
                r++;
            } else {
                l=++r;
                sMap = new HashMap<>();
            }
        }
        return s.substring(minL, minR+1);
    }
    private static boolean isEqual(Map<Character, Integer> tMap, Map<Character, Integer> sMap) {
        return tMap.keySet().stream().allMatch(k-> tMap.get(k) <= sMap.getOrDefault(k,0));
        /*
            // or
            for(char c: tMap.keySet()) {
                if(tMap.get(c) > sMap.getOrDefault(c, 0)) {
                    return false;
                }
            }
            return true;
         */
    }












    /*
      "TO_FIND" counter is length of "t" and it is constant
      "found" counter is used to store the count of t characters in the current subString or window.
      So, max value of found counter is TO_FOUND t length i.e always foundCounter <= TO_FIND
      if found < TO_FIND ---> then move right pointer to find the remaining t chars. And check if we found remaining t char then increase found counter and "decrease" charsMap or ascii array int[128] specific char by 1
      main while loop with right++
      charsMap.get('right') >= 0 then found++
      if found == TO_FIND ---> then move left pointer (as we got all t chars & we want min subStr) & check if the current left index char is in t string chars, if it is then decrease found counter and "increase" charsMap or ascii array int[128] specific char by 1 as we need to find that in future
      sub while loop with left++
      charsMap.get('left') > 0 then found--
      i.e iterate main loop until found == TO_FIND then loop sub while loop and then repeat the process untill
      Remember to always decrease the right pointer charsMap, ascii array char count by 1 in the parent while loop
      NOTE
      1. we can use charsMap with char counter or ascii array new int[128] with ascii char counter to store the t chars and visited s chars. Because s and t consist of uppercase and lowercase English letters only
      2. And specific s chars are always negative i.e <= -1. And t specific char counter max value is it's repition in t string and min value of negative as per presense in s.
      3. if the t char in charsMap is > 0 then it means we need find that char in s substring that many times.
      4. while loop instead of for ---> while(rightIndex < s.length) even though we didn't loop till leftIndex in the rightIndex < s.length last loop we have the TO_FIND == found while loop to complete the left pointer
    */
      /**
      * @TimeComplexity - O(m+n)
    */
    public static String minWindowUsingSlidingWindow2(String s, String t) {
        if(t.length() > s.length()) return "";
        String subStr = "";
        final int TO_FIND_TARGET = t.length();
        int found = 0;
        int left=0, right=0;

        Map<Character, Integer> charsMap = t.chars().mapToObj(e->(char)e).collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e->1)) );

        while(right<s.length()){ // --- right pointer loop (main expression)

            charsMap.merge(s.charAt(right), -1, Integer::sum);

            if(charsMap.get(s.charAt(right)) >= 0 ){
                found++;
            }


            while(found==TO_FIND_TARGET){
                int currWindowL = right - left + 1;
                if(subStr.length() > currWindowL || subStr.isEmpty()){
                    subStr = s.substring(left, right+1);
                }

                charsMap.merge(s.charAt(left), 1, Integer::sum);

                if(charsMap.get(s.charAt(left)) > 0){
                    found--;
                }
                left++;
            }

            right++;
        }
        return subStr;
    }







    public String minWindowUsingAsciiArrSlidingWindow(String s, String t) {
        // create count array for t and char array for s
        char[] sArr = s.toCharArray();
        int[] tCount = new int[128];

        // count the occurrences of each char in t
        for (char ch : t.toCharArray()) {
            tCount[ch]++;
        }

        int required = t.length();
        int left = 0, right = 0, min = Integer.MAX_VALUE, start = 0;

        // until end of the char array of s
        while (right < sArr.length) {
            // check if char at right index is required
            if (tCount[sArr[right]] > 0) {
                required--;
            }
            tCount[sArr[right]]--;
            right++;

            // if all chars are found in current window
            while (required == 0) {
                // update the prev min if this min is minimun
                if (right - left < min) {
                    start = left;
                    min = right - left;
                }

                // slide the left pointer towards right
                if (tCount[sArr[left]] == 0) {
                    required++;

                }
                tCount[sArr[left]]++;
                left++;
            }
        }
        return min == Integer.MAX_VALUE ? "" : new String(sArr, start, min);
    }
}
