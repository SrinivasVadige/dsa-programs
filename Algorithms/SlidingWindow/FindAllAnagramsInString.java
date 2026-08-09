package Algorithms.SlidingWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 * We already know, how brute force works. Here just focus on O(n) algorithm
 *
 * CONSTRAINTS:
 * 1. s and p chars are lowercase english letters
 *
 * </pre>
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 29 Sept 2024
*/
public class FindAllAnagramsInString {
    public static void main(String[] args) {
        String s = "cbaebabacd", p = "abc";
        // String s = "abab", p = "ab";
        System.out.println("findAnagrams : " + findAnagrams(s, p));
        System.out.println("findAnagramsDoResearch : " + findAnagramsDoResearch(s, p) + "  ---  wrong ans" );
    }

    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> list = new ArrayList<>();
        int[] markArr = new int[26];
        int left=0, right=0, found=0;

        for(char c: p.toCharArray())
            markArr[c-'a']++;

        while (right<s.length()) {
            if(markArr[s.charAt(right++)-'a']-- >= 1) found++;
            if(found==p.length()) list.add(left);
            if(right - left == p.length() && markArr[s.charAt(left++)-'a']++ >= 0) found--;
        }
        return list;
    }

    /**
     * This is exactly same findAnagrams() logic but explained the each step in detailed
     *
     * Note that mark or visit means decreasing the char count in markArr
     * and similarly to unmark or revist means increasing back the char count in the markArr
    */
    public static List<Integer> findAnagramsExplained(String s, String p) {
        List<Integer> list = new ArrayList<>();
        int[] markArr = new int[26]; // for both p&s chars as the both chars are lowercase english letters or we can use Map<Character, Integer> instead
        int left=0, right=0, found=0;

        // PREPARE CHAR ARRAY WITH P CHARS AS DEFAULT
        for(char c: p.toCharArray())
            markArr[c-'a']++;  // 'a' is char i.e ascii value and c-'a' means char-65 Eg: 'a'-'a' => 0 as 65-65=0

        while (right<s.length()) {
            System.out.println(s.substring(left, right+1));
            // IS RIGHT S CHAR IS IN P? --- check before marking the right char in markArr and before incresing the right pointer
            if(markArr[s.charAt(right) - 'a'] >= 1)  // p char in right pointer s chars is always >=1 as we unmark every char in WINDOW LENGTH condition 
                found++;

            markArr[s.charAt(right) - 'a']--; // mark right index visited char
            right++;
            // if(markArr[s.charAt(right++)-'a']-- >= 1) found++; //// i.e instead of these 4 lines, simply use if(markArr[s.charAt(right++)-'a']-- >= 1) found++; --- i.e 1st array num-- and then right++

            // FOUND ALL P CHARS IN S
            if(found==p.length()) list.add(left);

            // WINDOW LENGTH --- i.e left pointer waits till the right reaches the 1st window length and this window length will continue through out the loop as we check each and every char in s
            if(right - left == p.length()){
                // IS CURRENT LEFT S CHAR IS IN P? --- check before unmarking the left char in markArr and before increasing the left pointer
                if(markArr[s.charAt(left) - 'a'] >=0 ) //  p char in left pointer s chars is always >=0 as we unmark every char below
                    found--;

                markArr[s.charAt(left) - 'a']++; // unmark left index visited char, because we already marked the char with right index
                left++;
            }
            // if(right - left == p.length() && markArr[s.charAt(left++)-'a']++ >= 0) found--;  //// i.e instead of these 6 lines, simply use if(right - left == p.length() && markArr[s.charAt(right++)-'a']-- >= 0) found--; i.e 1st arr num++ and then right++
        }
        return list;
    }





    /**
     * ---- my thoughts --- need to research more
     * @Intuition - p is window length and we can use left and right pointers with sliding
     *
     * <p> but gettting TLE
    */
    @SuppressWarnings("unused")
    public static List<Integer> findAnagramsDoResearch(String s, String p) {
        List<Integer> list = new ArrayList<>();
        if(s.length() < p.length()) return list;

        int left=0, right=0;
        Map<Character, Integer> pMap = p.chars().mapToObj(i->(char)i).collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e->1)));
        Map<Character, Integer> temMap = new HashMap<>(pMap);
        int TARGET_WINDOW = p.length();
        int found = 0;
        while(right<s.length()) {

            if(temMap.getOrDefault(s.charAt(right), -1) > 0 && found <= TARGET_WINDOW ){
                found++;
                temMap.merge(s.charAt(right), -1, Integer::sum);
            }

            if(found == TARGET_WINDOW){
                left = right+1-TARGET_WINDOW;
                list.add(left);
                //System.out.println(s.substring(left, right+1));
            }

            if(temMap.getOrDefault(s.charAt(right), -1) < 0 || found > TARGET_WINDOW ){
                found = 0;
                temMap = new HashMap<>(pMap);
            }

            // if(temMap.getOrDefault(s.charAt(right), -1) == 0 && found == TARGET_WINDOW){
            //     found++;
            //     temMap.merge(s.charAt(right), -1, Integer::sum);
            // }

            right++;
        }

        return list;
    }
}
