package Algorithms.Hashing;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 23 Sept 2024
 * @link 49. Group Anagrams <a href="https://leetcode.com/problems/group-anagrams/">Leetcode link</a>
 * @description words with same char frequencies are anagrams
 * @topics Array, Hash Table, String, Sorting
 * @companies Amazon, Google, Meta, Microsoft, Oracle, Bloomberg, Yandex, Goldman, Zoho, Adobe, J, Apple, Visa, PayPal, IBM, Nvidia, Nutanix, Morgan, TikTok, Uber, Salesforce, ServiceNow, Affirm, EPAM, Walmart, Atlassian, Anduril, Yahoo, Citadel, eBay
*/
public class GroupAnagrams {

    public static void main(String[] args) {
        String[] strs = new String[]{"eat","tea","tan","ate","nat","bat"};
        System.out.println("groupAnagrams => " + groupAnagrams(strs));
        System.out.println("groupAnagrams 2 => " + groupAnagrams2(strs));
        System.out.println("groupAnagrams 3 => " + groupAnagrams3(strs));
        System.out.println("groupAnagrams 4 => " + groupAnagrams4(strs));
        System.out.println("groupAnagrams 5 => " + groupAnagrams5(strs));
    }


    /**
     * Approach: Categorize by Sorted char[] String
     * Key = String.valueOf(sorted char[]) or new String(sorted char[])
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str: strs) {
            char[] keyValue = str.toCharArray();
            Arrays.sort(keyValue);
            String key = new String(keyValue);

            map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }

        return new ArrayList<>(map.values()); // or map.values().stream().collect(Collectors.toList());
    }



    /**
     * Key = Arrays.toString(int[])
     */
    public static List<List<String>> groupAnagrams2(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for(String str: strs) {
            int[] freq = new int[26];
            for(int i=0; i<str.length(); i++) {
                freq[str.charAt(i)-'a']++;
            }

            map.computeIfAbsent(Arrays.toString(freq), k -> new ArrayList<>()).add(str);
        }

        return new ArrayList<>(map.values());
    }




    /**
     * Approach: Categorize by Count
     * key = StringBuilder.toString() using int[]
     * key = combination of chars in frequencies[26] array as it is already bucket sorted
     * key -> we will get the unique key for an anagram as it's already a bucket sort -> no need to sort again
     * as we convert int[] to StringBuilder ---> Key construction is heavier
     * new int[26]; // bucket sort -> no need to sort the unique chars again
     */
    public static List<List<String>> groupAnagrams3(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for(String str: strs) {
            int[] freq = new int[26];
            for(char c : str.toCharArray()) {
                freq[c -'a']++;
            }

            StringBuilder key = new StringBuilder();
            for(int i=0; i<freq.length; i++) {
                if(freq[i] > 0) key.append((char)(i+'a')).append(freq[i]);
            }
            map.computeIfAbsent(key.toString(), k -> new ArrayList<>()).add(str); // or key = Arrays.toString(freq);
        }

        return new ArrayList<>(map.values());
    }




    /**
     * Key = TreeMap<Character, Integer>.toString()
     */
    public static List<List<String>> groupAnagrams4(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for(String str: strs) {
            // use TreeMap if key is String because HashMap won't work as we need key order, Eg: ["bur", "rub"]
            Map<Character, Integer> freq = new TreeMap<>();
            for(int i=0; i<str.length(); i++) {
                freq.merge(str.charAt(i), 1, Integer::sum);
            }
            map.computeIfAbsent(freq.toString(), k -> new ArrayList<>()).add(str);
        }

        return new ArrayList<>(map.values());
    }




    /**
     * Key = HashMap<Character, Integer>
     */
    public static List<List<String>> groupAnagrams5(String[] strs) {

        Map<Map<Character, Integer>, List<String>> map = new HashMap<>();

        for(String str: strs) {
            Map<Character, Integer> freq = str.chars().mapToObj(i->(char)i).collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e->1)));
            map.computeIfAbsent(freq, k->new ArrayList<String>()).add(str);
        }

        return new ArrayList<>(map.values());
    }

}
