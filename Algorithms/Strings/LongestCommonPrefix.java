package Algorithms.Strings;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 15 March 2025
 * @link 14. Longest Common Prefix <a href="https://leetcode.com/problems/longest-common-prefix/">LeetCode link</a>
 * @topics String, Trie
 * @companies Google, Meta, Amazon, Bloomberg, Apple, Microsoft, TikTok, Quora, Fractal Analytics, HSBC, Revolut, Target, Visa, SAP, CME Group, Adobe, Uber, tcs, Zoho, Accenture, Yahoo, Oracle, IBM, EPAM Systems, Turing
 */
public class LongestCommonPrefix {
    public static void main(String[] args) {
        String[] strs = {};
        System.out.println("longestCommonPrefix using Vertical Scanning => " + longestCommonPrefixUsingVerticalScanning(strs));
        System.out.println("longestCommonPrefix using Vertical Scanning and Stream => " + longestCommonPrefixUsingVerticalScanningAndStream(strs));
        System.out.println("longestCommonPrefix using Horizontal Scanning => " + longestCommonPrefixUsingHorizontalScanning(strs));
        System.out.println("longestCommonPrefix using Binary Search => " + longestCommonPrefixUsingBinarySearch(strs));
        System.out.println("longestCommonPrefix using Divide and Conquer => " + longestCommonPrefixUsingDivideAndConquer(strs));
        System.out.println("longestCommonPrefix using Trie => " + longestCommonPrefixUsingTrie(strs));
    }

    /**
     * Vertical Scanning
     *
     * @TimeComplexity O(m*n), where m = strs.length, n = minStr.length() ---- or O(N), where N = sum of all chars in all strings
     * @SpaceComplexity O(1)

        strs = ["flower","flow","flight"]

        "flower"
        "flow"
        "flight"
         i
          i
           i
     */
    public static String longestCommonPrefixUsingVerticalScanning(String[] strs) {
        StringBuilder sb = new StringBuilder();
        // int minStrLen = Arrays.stream(strs).mapToInt(String::length).min().orElse(0);
        main:
        for(int i=0; i<strs[0].length(); i++) { // or i<minStrLen; ---> trav each char i in each string
            char c = strs[0].charAt(i);
            for (String str: strs) { // checking each i's char in all strings
                if (i==str.length() || str.charAt(i) != c) {
                    break main; // or return sb.toString();
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }



    // Vertical Scanning and Streams
    public static String longestCommonPrefixUsingVerticalScanningAndStream(String[] strs) {
        int prefixLen = 0;
        int smallStrLen = Arrays.stream(strs).mapToInt(String::length).min().orElse(0);
        for(int i=0; i<smallStrLen; i++) {
            final int idx = i;
            final char c = strs[0].charAt(i);
            if(Arrays.stream(strs).skip(1).allMatch(s-> s.charAt(idx)==c)) { // we skip first string cause we get the 'c' from the first string
                prefixLen++;
            } else {
                break;
            }
        }
        return strs[0].substring(0, prefixLen);
    }


    /**
     * Horizontal Scanning
     *
     * @TimeComplexity O(m*n), where m = strs.length, n = strs[0].length() ---- or O(N), where N = sum of all chars in all strings
     * @SpaceComplexity O(1)

        strs = ["flower","flow","flight"]
        i=0;
        prefix = "flower"

        i=1
        "flow"
     --> flower
     --> flowe
     --> flow

        i=2
        "flight"
     --> flow
     --> flo
     --> fl


     */
    public static String longestCommonPrefixUsingHorizontalScanning(String[] strs) {
        if (strs.length == 0) return "";
        String prefix = strs[0]; // the whole first string
        for (int i = 1; i < strs.length; i++) { // each string
            while (!strs[i].startsWith(prefix)) { // or strs[i].indexOf(prefix) != 0
                prefix = prefix.substring(0, prefix.length() - 1); // remove the last char
                // if (prefix.isEmpty()) return "";
            }
        }
        return prefix;
    }


    /**
     * Binary search
     * @TimeComplexity O(S*logM), where S is the sum of all characters in all strings
     * @SpaceComplexity O(1)
     */
    public static String longestCommonPrefixUsingBinarySearch(String[] strs) {
        int minLen = Integer.MAX_VALUE;
        for (String str : strs) minLen = Math.min(minLen, str.length());
        int low = 1;
        int high = minLen;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (isCommonPrefix(strs, middle)) {
                low = middle + 1;
            }
            else {
                high = middle - 1;
            }
        }
        return strs[0].substring(0, (low + high) / 2);
    }
    private static boolean isCommonPrefix(String[] strs, int len) {
        String str1 = strs[0].substring(0, len);
        for (int i = 1; i < strs.length; i++) {
            if (!strs[i].startsWith(str1)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Divide and conquer
     * @TimeComplexity O(s), where s is the sum of all characters in all strings = m*n
     * @SpaceComplexity
     */
    public static String longestCommonPrefixUsingDivideAndConquer(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        return longestCommonPrefix(strs, 0, strs.length - 1);
    }

    private static String longestCommonPrefix(String[] strs, int l, int r) {
        if (l == r) {
            return strs[l];
        } else {
            int mid = (l + r) / 2;
            String lcpLeft = longestCommonPrefix(strs, l, mid);
            String lcpRight = longestCommonPrefix(strs, mid + 1, r);
            return commonPrefix(lcpLeft, lcpRight);
        }
    }
    private static String commonPrefix(String left, String right) {
        int min = Math.min(left.length(), right.length());
        for (int i = 0; i < min; i++) {
            if (left.charAt(i) != right.charAt(i)) {
                return left.substring(0, i);
            }
        }
        return left.substring(0, min);
    }







    public static String longestCommonPrefixUsingTrie(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        if (strs.length == 1) return strs[0];
        Trie trie = new Trie();
        // Insert all strings except the first into the Trie
        for (int i = 1; i < strs.length; i++) {
            trie.insert(strs[i]);
        }
        // Search for the longest prefix using the first string
        return trie.searchLongestPrefix(strs[0]);
    }

    // Using Trie
    public static String longestCommonPrefixUsingTrie2(String q, String[] strs) {
        if (strs == null || strs.length == 0) return "";
        if (strs.length == 1) return strs[0];
        Trie trie = new Trie();
        for (int i = 1; i < strs.length; i++) {
            trie.insert(strs[i]);
        }
        return trie.searchLongestPrefix(q);
    }


    static class Trie {
        private final TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        // assume methods insert, search, searchPrefix are implemented as it is described
        // in  https://leetcode.com/articles/implement-trie-prefix-tree/)
        public String searchLongestPrefix(String word) {
            TrieNode node = root;
            StringBuilder prefix = new StringBuilder();
            for (int i = 0; i < word.length(); i++) {
                char curLetter = word.charAt(i);
                if (
                        node.containsKey(curLetter) &&
                                node.getLinks() == 1 &&
                                !node.isEnd()
                ) {
                    prefix.append(curLetter);
                    node = node.get(curLetter);
                } else return prefix.toString();
            }
            return prefix.toString();
        }

        // Inserts a word into the trie.
        public void insert(String word) {
            TrieNode node = root;
            for (int i = 0; i < word.length(); i++) {
                char currentChar = word.charAt(i);
                if (!node.containsKey(currentChar)) {
                    node.put(currentChar, new TrieNode());
                }
                node = node.get(currentChar);
            }
            node.setEnd();
        }


        static class TrieNode {
            // R links to node children
            private final TrieNode[] links;

            private boolean isEnd;

            // number of children non null links
            private int size;

            public void put(char ch, TrieNode node) {
                links[ch - 'a'] = node;
                size++;
            }

            public int getLinks() {
                return size;
            }

            // assume methods containsKey, isEnd, get, put are implemented as it is described
            // in  https://leetcode.com/articles/implement-trie-prefix-tree/)
            public TrieNode() {
                links = new TrieNode[26];
            }

            public boolean containsKey(char ch) {
                return links[ch - 'a'] != null;
            }

            public TrieNode get(char ch) {
                return links[ch - 'a'];
            }

            public void setEnd() {
                isEnd = true;
            }

            public boolean isEnd() {
                return isEnd;
            }
        }
    }











    // Vertical Scanning + Stream
    public String longestCommonPrefixUsingVerticalScanningAndStream2(String[] strs) {
        StringBuilder sb = new StringBuilder();
        int smallStrLen = Arrays.stream(strs).mapToInt(String::length).min().orElse(0);

        try {
            IntStream.range(0, smallStrLen).forEach(i ->{
                final char c = strs[0].charAt(i);
                if(Arrays.stream(strs).skip(1).allMatch(s-> s.charAt(i)==c)) {
                    sb.append(c); // can't use prefix++ because prefix is a reference variable
                } else {
                    throw new RuntimeException();
                    /*
                       can't break the Stream forEach loop ---
                       So use
                       1) RuntimeException
                       2) .takeWhile() Java 9+ before forEach() in longestCommonPrefixMyApproach3()
                       3) AtomicBoolean

                     */
                }
            });
        } catch (RuntimeException _) {}
        return sb.toString();
    }




    // Vertical Scanning + Stream
    public String longestCommonPrefixUsingVerticalScanningAndStream3(String[] strs) {
        StringBuilder sb = new StringBuilder();
        int smallStrLen = Arrays.stream(strs).mapToInt(String::length).min().orElse(0);

        IntStream.range(0, smallStrLen)
                 .takeWhile(i -> {
                     char c = strs[0].charAt(i);
                     return Arrays.stream(strs).skip(1).allMatch(s -> s.charAt(i) == c);
                 })
                 .forEach(i -> sb.append(strs[0].charAt(i)));

        return sb.toString();
    }




    // Vertical Scanning + Stream
    public String longestCommonPrefixUsingVerticalScanningAndStream4(String[] strs) {
        int strLen = 0;
        int smallStrLen = Arrays.stream(strs).mapToInt(String::length).min().orElse(0);

        for(int i: IntStream.range(0, smallStrLen).toArray()) {
            final char c = strs[0].charAt(i);
            if(Arrays.stream(strs).skip(1).allMatch(s-> s.charAt(i)==c)) {
                strLen++;
            } else {
                break;
            }
        }
        return strs[0].substring(0, strLen);
    }
}
