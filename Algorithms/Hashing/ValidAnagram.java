package Algorithms.Hashing;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 06 Sept 2025
 * @link 242. Valid Anagram <a href="https://leetcode.com/problems/valid-anagram/">Leetcode link</a>
 * @topics Hash Table, String, Sorting
 */
public class ValidAnagram {
    public static void main(String[] args) {
        String s = "anagram";
        String t = "nagaram";
        System.out.println(isAnagram(s, t));
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static boolean isAnagram(String s, String t) {
        if(s.length() != t.length()) return false;

        int[] freq = new int[26];

        for(int i=0; i<s.length(); i++) {
            freq[s.charAt(i)-'a']++;
            freq[t.charAt(i)-'a']--;
        }

        // for(int f: freq) {
        //     if(f != 0) return false;
        // }
        // return true;
        return Arrays.stream(freq).allMatch(i->i==0);
    }




    public static boolean isAnagram2(String s, String t) {
        if(s.length()!=t.length()) {
            return false;
        }

        int[] freq = new int[26];

        for(int i = 0 ;i<s.length();i++) {
            freq[s.charAt(i)-'a']++;
        }

        for(int i = 0 ;i<t.length();i++){
            if(--freq[t.charAt(i)-'a']<0){
                return false;
            }
        }

        return true;
    }




    /**
     * @TimeComplexity O(n) + O(nlogn) = O(nlogn)
     * @SpaceComplexity O(n)
     */
    public static boolean isAnagram3(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();

        Arrays.sort(sArr);
        Arrays.sort(tArr);

        return Arrays.equals(sArr, tArr); // arrays are objects, but they inherit the default equals() but dont override equals() ---> always compare arrays using Arrays.equals()

        /*
        // or
        String sStr = Arrays.toString(sArr);
        String tStr = Arrays.toString(tArr);
        return sStr.equals(tStr);
        */
    }






    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static boolean isAnagram4(String s, String t) {
        return Arrays.stream(s.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .equals(Arrays.stream(t.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
    }
}
