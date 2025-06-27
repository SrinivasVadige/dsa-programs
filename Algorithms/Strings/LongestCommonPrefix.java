package Algorithms.Strings;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 15 March 2025
 * @link 14. Longest Common Prefix <a href="https://leetcode.com/problems/longest-common-prefix/">LeetCode link</a>
 * @topics String, Trie
 */
public class LongestCommonPrefix {
    public static void main(String[] args) {
        String[] strs = {"flower","flow","flight"};
        System.out.printf("longestCommonPrefix => %s\n", longestCommonPrefix(strs));
    }

    // Approach: Horizontal scanning 1
    public static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        String prefix = strs[0]; // the whole first string
        for (int i = 1; i < strs.length; i++) { // skipping the first string
            while (!strs[i].startsWith(prefix)) { // or strs[i].indexOf(prefix) != 0
                prefix = prefix.substring(0, prefix.length() - 1); // remove the last char
            }
        }
        return prefix;
    }



    public String longestCommonPrefixMyApproach(String[] strs) {
        int prefix = 0;
        int smallStrLen = Arrays.stream(strs).mapToInt(String::length).min().orElse(0);
        for(int i=0; i<smallStrLen; i++) {
            final int idx = i;
            final char c = strs[0].charAt(i);
            if(Arrays.stream(strs).allMatch(s-> s.charAt(idx)==c)) {
                prefix++;
            } else {
                break;
            }
        }
        return strs[0].substring(0, prefix);
    }



    // Approach: Brute force
    public static String longestCommonPrefixBruteForce(String[] strs) {
        String s = "";
        main:
        for (int i=0; i<strs[0].length(); i++) { // string index
            char c = strs[0].charAt(i);
            // loop each string
            for (int j=1; j<strs.length; j++) { // skip 1st str by starting from j=1 instead (String str: strs)
                if (i>=strs[j].length() || c!=strs[j].charAt(i)) break main;
            }
            s+=c;
        }
        return s;
    }




    // Approach: Binary search
    public static String longestCommonPrefix2(String[] strs) {
        int minLen = Integer.MAX_VALUE;
        for (String str : strs) minLen = Math.min(minLen, str.length());
        int low = 1;
        int high = minLen;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (isCommonPrefix(strs, middle)) low = middle + 1;
            else high = middle - 1;
        }
        return strs[0].substring(0, (low + high) / 2);
    }
    private static boolean isCommonPrefix(String[] strs, int len) {
        String str1 = strs[0].substring(0, len);
        for (int i = 1; i < strs.length; i++) if (!strs[i].startsWith(str1)) return false;
        return true;
    }


    // Approach: Horizontal scanning 2
    public String longestCommonPrefix3(String[] strs) {
        if (strs.length == 0) return "";
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        }
        return prefix;
    }

    // Approach: Vertical scanning
    public String longestCommonPrefix4(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if ( i == strs[j].length() || strs[j].charAt(i) != c)
                    return strs[0].substring(0, i);
            }
        }
        return strs[0];
    }


    // Divide and conquer
    public String longestCommonPrefix5(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        return longestCommonPrefix(strs, 0, strs.length - 1);
    }

    private String longestCommonPrefix(String[] strs, int l, int r) {
        if (l == r) {
            return strs[l];
        } else {
            int mid = (l + r) / 2;
            String lcpLeft = longestCommonPrefix(strs, l, mid);
            String lcpRight = longestCommonPrefix(strs, mid + 1, r);
            return commonPrefix(lcpLeft, lcpRight);
        }
    }
    private String commonPrefix(String left, String right) {
        int min = Math.min(left.length(), right.length());
        for (int i = 0; i < min; i++) {
            if (left.charAt(i) != right.charAt(i)) return left.substring(0, i);
        }
        return left.substring(0, min);
    }



    public String longestCommonPrefixUsingTrie(String[] strs) {
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
    public String longestCommonPrefixUsingTrie2(String q, String[] strs) {
        if (strs == null || strs.length == 0) return "";
        if (strs.length == 1) return strs[0];
        Trie trie = new Trie();
        for (int i = 1; i < strs.length; i++) {
            trie.insert(strs[i]);
        }
        return trie.searchLongestPrefix(q);
    }
}






class Trie {
    private TrieNode root;

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
        private TrieNode[] links;

        private final int R = 26;

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
            links = new TrieNode[R];
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
