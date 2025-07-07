package Algorithms.Hashing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        System.out.println("groupAnagrams2 => " + groupAnagrams2(strs));
        System.out.println("groupAnagramsUsingStreams => " + groupAnagramsUsingStreams(strs));
    }


    /**
     * Approach: Categorize by Sorted String
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str: strs) {
            char[] keyValue = str.toCharArray();
            Arrays.sort(keyValue);
            String key = new String(keyValue);

            map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }

        return map.values().stream().collect(Collectors.toList());
    }





    /**
     * Approach: Categorize by Count
     * key = combination of chars in frequencies[26] array as it is already bucket sorted
     */
    public static List<List<String>> groupAnagrams2(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for(String str: strs) {
            int[] frequencies = new int[26]; // bucket sort -> no need to sort the unique chars again
            for(char c : str.toCharArray()) {
                frequencies[c -'a']++;
            }
            // key -> we will get the unique key for an anagram as it's already a bucket sort -> no need to sort again
            StringBuilder key = new StringBuilder();
            for(int i=0; i<frequencies.length; i++) {
                if(frequencies[i] > 0) {
                    key.append((char)(i+'a')).append(frequencies[i]); // Key construction is heavier
                }
            }
            map.computeIfAbsent(key.toString(), k -> new ArrayList<>()).add(str); // or key = Arrays.toString(frequencies);
        }

        return new ArrayList<>(map.values());
    }





    /**
     * Key = Map<String, Long>
     */
    public static List<List<String>> groupAnagramsUsingStreams(String[] strs) {

        Map<Map<String, Long>, List<String>> mapWithList = new HashMap<>();

        for(String str: strs) {
            Map<String, Long> map = Arrays.stream(str.split(""))
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            mapWithList.computeIfAbsent(map, k->new ArrayList<String>()).add(str);
        }

        return mapWithList.values().stream().collect(Collectors.toList());

    }

}
