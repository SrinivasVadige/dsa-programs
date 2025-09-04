package Algorithms.Hashing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Given two strings ransomNote and magazine, return true if ransomNote can be constructed by using the letters from magazine and false otherwise.
 * Each character in ransomNote may only be used once in a magazine.
 * i.e we cut the letter in the magazine book and paste it in our personal Note
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 09 Nov 2024
 * @link 383. Ransom Note <a href="https://leetcode.com/problems/ransom-note/">LeetCode Link</a>
 * @topics Hash table, String, Counting
 */
public class RansomNote {
    public static void main(String[] args) {
        String ransomNote = "aa", magazine = "aab";
        System.out.println("canConstruct using HashMap => " + canConstructUsingHashMap(ransomNote, magazine));
        System.out.println("canConstruct using int[] => " + canConstructUsingIntArray(ransomNote, magazine));
    }

    /**
     * @TimeComplexity O(n+m)
     * @SpaceComplexity O(n+m)
     */
    public static boolean canConstructUsingHashMap(String ransomNote, String magazine) {
        // Map<Character, Integer> rMap = ransomNote.chars().mapToObj(i->(char)i).collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e->1)));
        // Map<Character, Integer> mMap = Arrays.stream(magazine.split("")).map(s->s.charAt(0)).collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e->1)));

        Map<Character, Integer> rMap = new HashMap<>();
        Map<Character, Integer> mMap = new HashMap<>();

        for(int i=0; i<ransomNote.length(); i++) {
            rMap.merge(ransomNote.charAt(i), 1, Integer::sum);
        }

        for(int i=0; i<magazine.length(); i++) {
            mMap.merge(magazine.charAt(i), 1, Integer::sum);
        }

        for(char c: rMap.keySet()) {
            if(!mMap.containsKey(c) || rMap.get(c) > mMap.get(c)) {
                return false;
            }
        }

        return true;
    }

    public static boolean canConstructUsingHashMap2(String ransomNote, String magazine) {
        Map<String, Integer> rMap= Arrays.stream(ransomNote.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(_->1)));
        Map<String, Integer> mMap= Arrays.stream(magazine.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(_->1)));

        for(Map.Entry<String, Integer> re: rMap.entrySet() ) {
            if(mMap.getOrDefault(re.getKey(), 0) < re.getValue())
                return false;
        }
        return true;
    }


    /**
     * @TimeComplexity O(n+m)
     * @SpaceComplexity O(1)
     */
    public static boolean canConstructUsingIntArray(String ransomNote, String magazine) {
        int[] rFreq = new int[26];
        int[] mFreq = new int[26];

        for(int i=0; i<ransomNote.length(); i++) {
            rFreq[ransomNote.charAt(i)-'a']++;
        }

        for(int i=0; i<magazine.length(); i++) {
            mFreq[magazine.charAt(i)-'a']++;
        }

        for(int i=0; i<26; i++) {
            if(rFreq[i] > mFreq[i]) {
                return false;
            }
        }

        return true;
    }


    /**
     * @TimeComplexity O(n+m)
     * @SpaceComplexity O(n+m), because we are using str.toCharArray()
     */
    public static boolean canConstructUsingIntArray2(String ransomNote, String magazine) {
        int[] count = new int[26];

        for (char c : ransomNote.toCharArray())
            count[c - 'a']++;

        for (char c : magazine.toCharArray())
            count[c - 'a']--;

        for (int i : count) {
            if (i > 0) return false;
        }

        return true;
    }



    public static boolean canConstructUsingIntArray3(String ransomNote, String magazine) {
        int[] count = new int[26];

        for (char c : magazine.toCharArray())
            count[c - 'a']++;

        for (char c : ransomNote.toCharArray())
            if(count[c-'a']-- == 0) return false; // i.e before decrement

        return true;
    }



    public static boolean canConstructUsingIntArray4(String ransomNote, String magazine) {
        int[] check = new int[26];
         for (char c : ransomNote.toCharArray()) {
             int index = magazine.indexOf(c, check[c % 26]);
             if (index < 0)
                 return false;
             check[c % 26] = index+1;
         }
         return true;
    }




    public boolean canConstructBruteForce(String ransomNote, String magazine) {
        for (int i = 0; i < ransomNote. length(); i++) {
            char r= ransomNote. charAt(i);

            int matchingIndex = magazine.indexOf(r);
            if (matchingIndex == -1)
                return false;
            magazine = magazine.substring(0, matchingIndex)
            + magazine. substring(matchingIndex + 1); // skip that char and we can have duplicate chars
        }
        return true;
    }
}
