package Algorithms.Tries;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 06 Jan 2026
 * @link 211. Design Add and Search Words Data Structure <a href="https://leetcode.com/problems/design-add-and-search-words-data-structure/">LeetCode Link</a>
 * @topics HashTable, String, Design, Trie, DFS, BFS
 * @companies Datadog(5), Amazon(3), Apple(2), Google(5), Bloomberg(3), Microsoft(2), TikTok(2), Snowflake(2), Meta(4), Rubrik(4), DoorDash(3), LinkedIn(2), Atlassian(2), DocuSign(2), Snap(2)
 * @see Algorithms.Tries.ImplementTriePrefixTree
 */
public class DesignAddAndSearchWordsDataStructure {
    public static void main(String[] args) {
        System.out.println("DesignAddAndSearchWordsDataStructure");
        /*
        Input:
            ["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
            [     [],          ["bad"],  ["dad"],  ["mad"],  ["pad"], ["bad"], [".ad"], ["b.."]]
        Output:
            [    null,           null,    null,     null,     false,    true,    true,    true]
         */
        System.out.println("WordDictionary using TrieNode 1");
        WordDictionary wd = new WordDictionaryUsingTrieNode1();
        wd.addWord("bad");
        wd.addWord("dad");
        wd.addWord("mad");
        System.out.println("inserted words: \nbad, \ndad, \nmad");
        System.out.println("search: ");
        System.out.println("\"pad\" -> " +  wd.search("pad"));
        System.out.println("\"bad\" -> " +  wd.search("bad"));
        System.out.println("\".ad\" -> " +  wd.search(".ad"));
        System.out.println("\"b..\" -> " +  wd.search("b.."));


    }

    interface WordDictionary {
        void addWord(String word);
        boolean search(String word);
    }


    static class WordDictionaryUsingTrieNode1 implements WordDictionary {
        static class TrieNode {
            boolean isEnd = false;
            TrieNode[] neighbors = new TrieNode[26];
        }

        TrieNode root = new TrieNode();

        public void addWord(String word) {
            TrieNode curr = root;
            for (char c: word.toCharArray()) {
                int i = c-'a';
                if (curr.neighbors[i]==null) curr.neighbors[i] = new TrieNode();
                curr = curr.neighbors[i];
            }
            curr.isEnd = true;
        }

        public boolean search(String word) {
            // return searchUsingBfs(word);
            return searchUsingDfs(word);
        }

        public boolean searchUsingBfs(String word) { // bfs
            Queue<TrieNode> q = new LinkedList<>();
            q.add(root);
            for (char c: word.toCharArray()) {
                int i = c-'a';
                int size = q.size();
                while (size-- > 0) {
                    TrieNode curr = q.poll();
                    if (c == '.') {
                        for (TrieNode nei: curr.neighbors) {
                            if (nei != null) q.add(nei);
                        }
                    } else {
                        if (curr.neighbors[i] != null) q.add(curr.neighbors[i]);
                    }
                }

            }

            while (!q.isEmpty()) {
                if (q.poll().isEnd) return true;
            }
            return false;
        }

        public boolean searchUsingDfs(String word) { // dfs
            // return dfs1(word, root, 0);
            return dfs2(word, root, 0);
        }
        private boolean dfs1(String word, TrieNode node, int i){ // i = word index
            for (; i<word.length(); i++) {
                char c = word.charAt(i);
                if (c == '.') {
                    for (TrieNode nei: node.neighbors) {
                        if (nei != null && dfs1(word, nei, i+1)) return true;
                    }
                    return false;
                } else {
                    if (node.neighbors[c-'a'] == null) return false;
                    node = node.neighbors[c-'a'];
                }
            }
            return node.isEnd;
        }
        private boolean dfs2(String word, TrieNode node, int i){
            if (i == word.length()) return node.isEnd;

            char c = word.charAt(i);
            if (c == '.'){
                for(TrieNode nei : node.neighbors) {
                    if (nei != null && dfs2(word, nei, i+1)) return true;
                }
                return false;
            } else {
                if (node.neighbors[c - 'a'] == null) return false;
                return dfs2(word, node.neighbors[c - 'a'], i+1);
            }
        }
    }








    static class WordDictionaryUsingTrieNode2 implements WordDictionary {
        static class TrieNode {
            boolean isEnd = false;
            Map<Character, TrieNode> neighbors = new HashMap<>();
        }
        TrieNode root = new TrieNode();
        public void addWord(String word) {
            TrieNode curr = root;
            for (char c: word.toCharArray()) {
                if(!curr.neighbors.containsKey(c)) curr.neighbors.put(c, new TrieNode());
                curr = curr.neighbors.get(c);
            }
            curr.isEnd = true;

        }

        public boolean search(String word) {
            // return searchUsingBfs(word);
            return searchUsingDfs(word, root);
        }

        public boolean searchUsingBfs(String word) { // bfs
            Queue<TrieNode> q = new LinkedList<>();
            q.add(root);
            for (char c: word.toCharArray()) {
                int size = q.size();
                while (size-- > 0) {
                    TrieNode curr = q.poll();
                    if (c == '.') {
                        for (TrieNode nei: curr.neighbors.values()) q.add(nei);
                    } else {
                        if (curr.neighbors.get(c) != null) q.add(curr.neighbors.get(c));
                    }
                }

            }

            while (!q.isEmpty()) {
                if (q.poll().isEnd) return true;
            }
            return false;
        }

        public boolean searchUsingDfs(String word, TrieNode node) { // dfs
            for (int i = 0; i < word.length(); ++i) {
                char ch = word.charAt(i);
                if (!node.neighbors.containsKey(ch)) {
                    if (ch == '.') {
                        for (char x : node.neighbors.keySet()) {
                            TrieNode nei = node.neighbors.get(x);
                            if (searchUsingDfs(word.substring(i + 1), nei)) return true;
                        }
                    }
                    return false;
                } else {
                    node = node.neighbors.get(ch);
                }
            }
            return node.isEnd;
        }
    }








    static class WordDictionaryUsingMapBruteForceTLE implements WordDictionary {
        Map<Integer, Set<String>> dic = new HashMap<>(); // dictionary

        public void addWord(String word) {
            int m = word.length();
            dic.computeIfAbsent(m, k-> new HashSet<>()).add(word);;
        }

        public boolean search(String word) {
            int len = word.length();
            if (dic.containsKey(len)) {
                for (String w : dic.get(len)) {
                    int i = 0;
                    while (i<len && (word.charAt(i) == '.' || w.charAt(i) == word.charAt(i))) {
                        i++;
                    }
                    if (i == len) return true;
                }
            }
            return false;
        }
    }
}
