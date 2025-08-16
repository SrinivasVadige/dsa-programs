package Algorithms.SlidingWindow;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 16 August 2025
 * @link 30. Substring with Concatenation of All Words <a href="https://leetcode.com/problems/substring-with-concatenation-of-all-words/">LeetCode link</a>
 * @topics String, Hash Table, Sliding Window / Dynamic Sliding Window
 * @see Algorithms.SlidingWindow.MinimumWindowSubstring
 * NOTE:
 * Here we need the "concatenated string" --- i.e., all the words in words[] array concatenated in any permutation order
 */
public class SubstringWithConcatenationOfAllWords {
    public static void main(String[] args) {
        String s = "barfoofoobarthefoobarman";
        String[] words = {"bar","foo","the"};
        System.out.println("findSubstring Working But TLE => " +  findSubstringWorkingButTLE(s, words));
        System.out.println("findSubstring => " + findSubstring(s, words));
        System.out.println("findSubstring using AbstractList => " + findSubstringUsingAbstractList(s, words));
    }


    /**
     * @TimeComplexity O(m * n^2)
     * @SpaceComplexity O(n)
     */
    public static List<Integer> findSubstringWorkingButTLE(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if(s==null || words==null || s.isEmpty() || words.length==0) return result;
        Map<String, Integer> wordFreq = Arrays.stream(words).collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e->1)) );

        int m = s.length();
        int n = words.length;
        int wordLen = words[0].length();
        int windowLen = wordLen*n;

        for(int l=0; l<m-windowLen+1; l++) { // s.length() - last_concatenated_string_length // --- O(m) iterations
            Map<String, Integer> seen = new HashMap<>(); // or window --- reset the seen map for each window
            for(int r=l; r-l<windowLen; r+=wordLen) { // O(n) iterations
                String word = s.substring(r, r+wordLen); // O(wordLen) copy
                seen.merge(word, 1, Integer::sum);
                if(seen.equals(wordFreq)) { // O(n) comparison
                    result.add(l);
                }
            }
        }
        return result;
    }








    /**
     * @TimeComplexity O(m * wordLen)
     * @SpaceComplexity O(n * wordLen)
     */
    public static List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (s == null || words == null || s.isEmpty() || words.length == 0) return result;

        Map<String, Integer> wordFreq = new HashMap<>();
        for (String word : words) {
            wordFreq.merge(word, 1, Integer::sum);
        }

        int m = s.length();
        int n = words.length;
        int wordLen = words[0].length();

        // Try all starting points modulo / single wordLen
        for (int i = 0; i<wordLen; i++) {
            Map<String, Integer> seen = new HashMap<>();

            for (int l=i, r=i, wCount=0; r+wordLen<=m; r+=wordLen) {
                String word = s.substring(r, r + wordLen);

                if (wordFreq.containsKey(word)) {
                    seen.merge(word, 1, Integer::sum); // --- increase the sliding window
                    wCount++;

                    // Too many of this word → shrink from l
                    while (seen.get(word) > wordFreq.get(word)) {
                        String leftWord = s.substring(l, l + wordLen); // --- decrease the sliding window
                        seen.merge(leftWord, -1, Integer::sum);
                        wCount--;
                        l += wordLen;
                    }

                    // Window is exactly n words → valid index, add l-index and shrink one word from l
                    if (wCount == n) {
                        result.add(l);
                        // Move l by one word to the right side to continue
                        String leftWord = s.substring(l, l + wordLen); // --- decrease the sliding window
                        seen.merge(leftWord, -1, Integer::sum);
                        wCount--;
                        l += wordLen;
                    }

                } else {
                    seen = new HashMap<>(); // or seen.clear(); --- reset window
                    wCount = 0;
                    l = r+wordLen; // cause in next iteration, r+=wordLen --- i.e., making l=r
                }
            }
        }
        return result;
    }









    public static List<Integer> findSubstringUsingAbstractList(String s, String[] words) {
        return new AbstractList<Integer>() {
            List<Integer> list;

            @Override
            public Integer get(int index) {
                if(list == null) list = getResults(s, words);
                return list.get(index);
            }

            @Override
            public int size() {
                if(list == null) list = getResults(s, words);
                return list.size();
            }

        };
    }
    private static List<Integer> getResults(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (s == null || words == null || words.length == 0) return result;
        int wordLen = words[0].length();
        int wordCount = words.length;
        int totalLen = words.length * wordLen;

        if (s.length() < totalLen) return result;
        Map<String, Integer> wordMap = new HashMap<>();

        for(String word: words) {
            wordMap.merge(word, 1, Integer::sum);
        }

        for (int i = 0; i < wordLen; i++) {
            int left = i, right = i;
            int count = 0;
            Map<String,Integer> windowMap = new HashMap<>();

            while (right + wordLen <= s.length()) {
                String part = s.substring(right, right + wordLen);
                right += wordLen;
                if(!wordMap.containsKey(part)) {
                    windowMap = new HashMap<>(); // or windowMap.clear();
                    count = 0;
                    left = right;
                    continue;
                }
                windowMap.merge(part, 1, Integer::sum);
                count++;

                while (windowMap.get(part) > wordMap.get(part)) {
                    String leftWord = s.substring(left, left + wordLen);
                    windowMap.merge(leftWord, -1, Integer::sum);
                    count--;
                    left += wordLen;
                }
                if (count == words.length) result.add(left);
            }

        }
        return result;
    }











    public static List<Integer> findSubstring2(String s, String[] words) {
        int m=s.length();
        int wordLen=words[0].length();
        int n=words.length;
        int windowLen = wordLen*n;
        List<Integer> ans=new ArrayList<>();

        Map<String,Integer> wordFreq=new HashMap<>();
        for(String word:words){
            wordFreq.merge(word, 1, Integer::sum);
        }


        for(int idx=0; idx<wordLen; idx++) {

            Map<String,Integer> seen = new HashMap<>();

            StringBuilder sb = new StringBuilder();

            for(int l=idx,r=idx; r<m; r++) {
                sb.append(s.charAt(r));

                if(sb.length()==wordLen){
                    String word=sb.toString();
                    seen.merge(word, 1, Integer::sum);
                    sb.setLength(0);
                }

                if(r-l+1 == windowLen) {
                    if(wordFreq.equals(seen)) {
                        ans.add(l);
                    }
                    String remove=s.substring(l, l+wordLen);

                    if(seen.get(remove)>1) {
                        seen.merge(remove, -1, Integer::sum);
                    }
                    else {
                        seen.remove(remove);
                    }

                    l+=wordLen;
                }
            }
        }
        return ans;
    }










    public static List<Integer> findSubstringWorkingButTLE2(String s, String[] words) {
        List<Integer> result = new java.util.ArrayList<>();
        if(s==null || words==null || s.isEmpty() || words.length==0) return result;

        Map<String, Integer> wordFreq = new HashMap<>();
        for(String word : words) {
            wordFreq.merge(word, 1, Integer::sum);
        }

        int m = s.length();
        int n = words.length;
        int wordLen = words[0].length();
        int windowLen = wordLen*n;

        for(int i=0; i<m-windowLen+1; i++) {
            Map<String, Integer> seen = new HashMap<>(); // reset the seen map for each window

            for(int j=i; j<i+windowLen; j+=wordLen) {

                String word = s.substring(j, j+wordLen);

                if (!wordFreq.containsKey(word)) {
                    break;
                }

                seen.merge(word, 1, Integer::sum);

                if(seen.get(word) > wordFreq.get(word)) {
                    break;
                }

                if(j+wordLen == i+windowLen) {
                    result.add(i);
                }
            }
        }
        return result;
    }

}
