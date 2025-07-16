package Algorithms.SlidingWindow;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 26 Sept 2024
 * @link 3. Longest Substring Without Repeating Characters <a href="https://leetcode.com/problems/longest-substring-without-repeating-characters/">LeetCode link</a>
 * @topics String, Hash Table, Sliding Window / Dynamic Sliding Window
 * @companies Amazon, Google, Microsoft, Bloomberg, Meta, TikTok, Visa, Oracle, Yandex, Walmart Labs, Goldman Sachs, Turing, Apple, Zoho, Cisco, Infosys, Netflix, EPAM, Nvidia, HCL, Adobe, Yahoo, Tinkoff, Tesla, IBM, Uber, Flipkart, Accenture, Agoda, Salesforce
*/
public class LongestSubstringWithoutRepeatingCharacters {

    public static void main(String[] args) {
        String s = "dvdfd";
        System.out.println("lengthOfLongestSubstring using TwoPointers & HashSet: "  + lengthOfLongestSubstringUsingTwoPointersAndHashSet(s));
        System.out.println("lengthOfLongestSubstring using LinkedHashSet: "  + lengthOfLongestSubstringUsingLinkedHashSet(s));
        System.out.println("lengthOfLongestSubstring using HashMap: "  + lengthOfLongestSubstringUsingHashMap(s));
        System.out.println("lengthOfLongestSubstring using HashMap Optimized: " + lengthOfLongestSubstringUsingHashMapOptimized(s));
        System.out.println("lengthOfLongestSubstring using BruteForce: " + lengthOfLongestSubstringUsingBruteForce(s));
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)

        Example 1:
        Input: s = "abcabcbb"
        Output: 3
        Explanation: The answer is "abc" or "bca" or "cba", with the length of 3.

        move r till no duplicates

        a b c a b c
        lr

        a b c a b c
        l r

        a b c a b c
        l   r

        a b c a b c ---> found duplicate, now move l
        l     r

        a b c a b c ---> no duplicate, now move r
          l   r

        a b c a b c ---> found duplicate, now move l
          l     r

        a b c a b c ---> no duplicate, now move r
            l   r

        a b c a b c ---> found duplicate, now move l
            l     r

        a b c a b c
              l   r
     */
    public static int lengthOfLongestSubstringUsingTwoPointersAndHashSet(String s) {
        int l=0;
        int n=s.length();
        int maxLen = 0;
        Set<Character> set = new HashSet<>(); // to check duplicates

        for(int r=0; r<n; r++) {
            if(!set.add(s.charAt(r))) {
                while(l<n) {
                    char c = s.charAt(l++);
                    if(c == s.charAt(r)) {
                        break;
                    }
                    set.remove(c);
                }
            }
            maxLen = Math.max(maxLen, set.size()); // or maxLen = Math.max(maxLen, r-l+1);
        }
        return maxLen;
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int lengthOfLongestSubstringUsingLinkedHashSet(String s) {
        Set<Character> set = new LinkedHashSet<>();
        int maxLen = 0;

        for(char c : s.toCharArray()) {
            if(!set.add(c)) {
                Iterator<Character> it = set.iterator();
                while(it.hasNext() && it.next() != c) {
                    it.remove();
                }
                set.remove(c);
                set.add(c);
            }
            maxLen = Math.max(maxLen, set.size());
        }
        return maxLen;
    }







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int lengthOfLongestSubstringUsingHashMap(String s) {
        Map<Character, Integer> counter = new HashMap<>();
        int l = 0;
        int r = 0;
        int maxLen = 0;

        while (r < s.length()) {
            char rChar = s.charAt(r);
            counter.merge(rChar, 1, Integer::sum);

            while (counter.get(rChar) > 1) {
                char lChar = s.charAt(l);
                counter.merge(lChar, -1, Integer::sum);
                l++;
            }

            maxLen = Math.max(maxLen, r-l+1);
            r++;
        }
        return maxLen;
    }







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int lengthOfLongestSubstringUsingHashMapOptimized(String s) {
        int n = s.length(), maxLen = 0;
        Map<Character, Integer> map = new HashMap<>(); // --> map - index after current character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            maxLen = Math.max(maxLen, j-i+1);
            map.put(s.charAt(j), j + 1);
        }
        return maxLen;
    }









    /**
     * @TimeComplexity O(n^3)
     * @SpaceComplexity O(1)
     */
    public static int lengthOfLongestSubstringUsingBruteForce(String s) {
        int n = s.length();

        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (checkRepetition(s, i, j)) {
                    res = Math.max(res, j - i + 1);
                }
            }
        }

        return res;
    }

    private static boolean checkRepetition(String s, int start, int end) {
        Set<Character> chars = new HashSet<>();

        for (int i = start; i <= end; i++) {
            char c = s.charAt(i);
            if (chars.contains(c)) {
                return false;
            }
            chars.add(c);
        }

        return true;
    }






    public static int lengthOfLongestSubstringUsingTwoPointersAndHashSet2(String s) {
        int n = s.length();
        int maxLength = 0;
        Set<Character> set = new HashSet<>();
        int l = 0;

        for (int r = 0; r < n; r++) {
            if (!set.contains(s.charAt(r))) {
                set.add(s.charAt(r));
                maxLength = Math.max(maxLength, r-l+1);
            } else {
                while (set.contains(s.charAt(r))) {
                    set.remove(s.charAt(l));
                    l++;
                }
                set.add(s.charAt(r));
            }
        }

        return maxLength;
    }







    /*
     * No nedd to use two pointer approach like MinimumWindowSubString problem. Because we're focussing only one dup char
    */
    public static int lengthOfLongestSubstringNoPointers(String s) {
        int n = s.length();
        if(n <= 1) {
            return n;
        }

        String subStr = "";
        int maxL = 0;

        String[] chars = s.split("");

        for(int i=0; i<n; i++){
            int repStrIndex = subStr.indexOf(chars[i]); // as we check the index of subStr not s. It's working fine
            if(repStrIndex > -1){
                subStr = subStr.substring(repStrIndex+1) + chars[i]; // --- or HashSet
            } else subStr += chars[i];

            System.out.println(subStr);
            maxL = Math.max(maxL, subStr.length());
        }

        return maxL;

     }







     // won't work for Eg: dvdf -- here max sub string length is 3
    public static int lengthOfLongestSubstringMyApproachOldNotWorking(String s) {


        Map<String, Integer> map = new HashMap<>();
        String[] chars = s.split("");

        int maxL = 0;
        int tempStart=0;
        for(int i=0; i<s.length(); i++){
            if(map.containsKey(chars[i])){
                maxL = Math.max(maxL, i-tempStart);
                tempStart = i;
                map.clear();
                map.put(chars[i], 1);
            }
            else
                map.put(chars[i], 1);
            System.out.println(maxL);
            System.out.println(map);
        }

        return map.size();
    }
}
