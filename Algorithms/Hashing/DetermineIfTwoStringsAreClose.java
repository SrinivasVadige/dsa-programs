package Algorithms.Hashing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 15 April 2025
 */
public class DetermineIfTwoStringsAreClose {
    public static void main(String[] args) {
        String word1 = "abc", word2 = "bca";
        System.out.println("closeStrings(word1, word2) => " + closeStrings(word1, word2));
        System.out.println("closeStringsMyApproach(word1, word2) => " + closeStringsMyApproach(word1, word2));
        System.out.println("closeStringsMyApproach2(word1, word2) => " + closeStringsMyApproach2(word1, word2));

    }

    public static boolean closeStrings(String word1, String word2) {
        if (word1.length() != word2.length()) return false; // OPTIONAL FOR EFFICIENCY

        int[] freq1 = new int[26], freq2 = new int[26];
        for (char c : word1.toCharArray()) freq1[c - 'a']++;
        for (char c : word2.toCharArray()) freq2[c - 'a']++;

        // all chars in word1 in word2 ---> or we can use HashSet1.equals(HashSet2)
        for (int i = 0; i < 26; i++) {
            if (freq1[i] == 0 && freq2[i] == 0) continue;
            if (freq1[i] == 0 || freq2[i] == 0 || freq1[i] != freq2[i]) return false;
        }

        Arrays.sort(freq1);
        Arrays.sort(freq2);
        return true;
    }






    public static boolean closeStringsMyApproach(String word1, String word2) {
        Map<Character, Integer> map1 = new HashMap<>(), map2 = new HashMap<>();
        for(char c: word1.toCharArray()) map1.merge(c, 1, Integer::sum);
        for(char c: word2.toCharArray()) map2.merge(c, 1, Integer::sum);

        Map<Integer, Integer> vals1 = new HashMap<>(), vals2 = new HashMap<>();
        for(int v: map1.values()) vals1.merge(v, 1, Integer::sum);
        for(int v: map2.values()) vals2.merge(v, 1, Integer::sum);

        return map1.keySet().equals(map2.keySet()) && vals1.equals(vals2);
    }




    public static boolean closeStringsMyApproach2(String word1, String word2) {
        Map<Character, Integer> map1 = new HashMap<>(), map2 = new HashMap<>();
        for(char c: word1.toCharArray()) map1.merge(c, 1, Integer::sum);
        for(char c: word2.toCharArray()) map2.merge(c, 1, Integer::sum);

        List<Integer> vals1 = new ArrayList<>(map1.values()), vals2 = new ArrayList<>(map2.values());
        Collections.sort(vals1);
        Collections.sort(vals2);

        return map1.keySet().equals(map2.keySet()) && vals1.equals(vals2);
    }

}
