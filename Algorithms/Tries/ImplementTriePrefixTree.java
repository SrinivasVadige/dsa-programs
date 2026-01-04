package Algorithms.Tries;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 23 Feb 2025
 * @link 208. Implement Trie (Prefix Tree) <a href="https://leetcode.com/problems/implement-trie-prefix-tree/">LeetCode Link</a>
 * @topics HashTable, String, Design, Trie
 * @companies Oracle(4), MongoDB(4), Amazon(3), Bloomberg(2), Microsoft(5), Google(4), Meta(3), General Motors(3), Apple(2), DoorDash(10), Roblox(7), DocuSign(5), Citadel(3), Grammarly(3), Adobe(2), Walmart Labs(2), TikTok(2), Goldman Sachs(2), Uber(2)
 * <pre>
 *
    THOUGHTS:
    ---------
    1) Max num of children for root node is 26 alphabets.
    2) when we inserted "apple", "app", "ape", "boy" in Trie, it looks like:

                          ""
                   ________|___________________
                   |                           |
                   a                           b
                   |                           |
                   p                           o
         __________|________                   |
        |                  |                   |
        p isEnd=true      e isEnd=true       y isEnd=true
        |
        l
        |
        e isEnd=true
 *
 * </pre>
 */
@SuppressWarnings("unused")
public class ImplementTriePrefixTree {
    public static void main(String[] args) {

        System.out.println("Implement Trie Prefix Tree --- Original");
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println("trie.search(\"apple\") => " + trie.search("apple"));
        System.out.println("trie.search(\"app\") => " + trie.search("app"));
        System.out.println("trie.startsWith(\"app\") => " + trie.startsWith("app"));
        trie.insert("app");
        System.out.println("trie.search(\"app\") => " + trie.search("app"));




        System.out.println("\nImplement Trie Prefix Tree --- MyApproach");
        TrieMyApproach trieMyApproach = new TrieMyApproach();
        trieMyApproach.insert("apple");
        System.out.println("trieMyApproach.search(\"apple\") => " + trieMyApproach.search("apple"));
        System.out.println("trieMyApproach.search(\"app\") => " + trieMyApproach.search("app"));
        System.out.println("trieMyApproach.startsWith(\"app\") => " + trieMyApproach.startsWith("app"));
        trieMyApproach.insert("app");
        System.out.println("trieMyApproach.search(\"app\") => " + trieMyApproach.search("app"));
    }

    static class Trie {
        static class TrieNode {
            TrieNode[] children = new TrieNode[26]; // or HashMap
            boolean isEnd = false;
        }

        TrieNode root = new TrieNode();

        public void insert(String word) {
            TrieNode curr = root;
            for (char c : word.toCharArray()) {
                if (curr.children[c - 'a'] == null) {
                    curr.children[c - 'a'] = new TrieNode();
                }
                curr = curr.children[c - 'a'];
            }
            curr.isEnd = true;
        }

        public boolean search(String word) {
            TrieNode curr = root;
            for (char c : word.toCharArray()) {
                if (curr.children[c - 'a'] == null) {
                    return false;
                }
                curr = curr.children[c - 'a'];
            }
            return curr.isEnd;
        }

        public boolean startsWith(String prefix) {
            TrieNode curr = root;
            for (char c : prefix.toCharArray()) {
                if (curr.children[c - 'a'] == null) {
                    return false;
                }
                curr = curr.children[c - 'a'];
            }
            return true;
        }
    }








    static class Trie2 {

        static class TrieNode {
            private final int R = 26;
            private final TrieNode[] links = new TrieNode[R];
            private boolean isEnd;

            public boolean containsKey(char ch) {return links[ch -'a'] != null;}
            public TrieNode get(char ch) {return links[ch -'a'];}
            public void put(char ch, TrieNode node) {links[ch -'a'] = node;}
            public void setEnd() {isEnd = true;}
            public boolean isEnd() {return isEnd;}
        }

        private final TrieNode root = new TrieNode();

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

        public boolean search(String word) {
           TrieNode node = searchPrefix(word);
           return node != null && node.isEnd();
        }

        public boolean startsWith(String prefix) {
            TrieNode node = searchPrefix(prefix);
            return node != null;
        }

        private TrieNode searchPrefix(String word) {
            TrieNode node = root;
            for (int i = 0; i < word.length(); i++) {
               char c = word.charAt(i);
               if (node.containsKey(c)) {
                   node = node.get(c);
               } else {
                   return null;
               }
            }
            return node;
        }
    }









    static class TrieMyApproach {
        TrieMyApproach[] arr = new TrieMyApproach[26];
        char c ='\0'; // optional
        boolean isEnd = false;

        public void insert(String word) {
            TrieMyApproach trav = this;
            for(char ch: word.toCharArray()) {
                if(trav.arr[ch-'a'] == null) trav.arr[ch-'a'] = new TrieMyApproach();
                trav = trav.arr[ch-'a'];
                trav.c=ch; // optional
            }
            trav.isEnd=true;
        }

        public boolean search(String word) {
            TrieMyApproach trav = this;
            for(char ch: word.toCharArray()) {
                trav = trav.arr[ch-'a'];
                if(trav==null) return false;
                trav.c=ch; // optional
            }
            return trav.isEnd;
        }

        public boolean startsWith(String prefix) {
            TrieMyApproach trav = this;
            for(char ch: prefix.toCharArray()) {
                trav = trav.arr[ch-'a'];
                if(trav==null) return false;
                trav.c=ch; // optional
            }
            return true;
        }
    }













    static class TrieMyApproach2 {
        class Tree {
            char c;
            boolean isEnd;
            List<Tree> lst = new ArrayList<>(); // or HashMap
            Tree() {}
            Tree(char c) {this.c = c;}
        }

        private Tree root = new Tree();

        public void insert(String word) {
            insert(word, 0, root);
        }

        // complete word find i.e word 1st char is child of root node && word last char contains isEnd=true;
        public boolean search(String word) {
            if (root.lst.size()==0) return false;
            return search(word, 0, root);
        }

        // prefix 1st char is child of root node and prefix last char node not
        public boolean startsWith(String prefix) {
            if (root.lst.size()==0) return false;
            return startsWith(prefix, 0, root);
        }

        private void insert(String word, int i, Tree node) {
            Tree child = node.lst.stream().filter(x->x.c==word.charAt(i)).findFirst().orElse(null);
            if (child==null) { // child not found
                child = new Tree(word.charAt(i));
                node.lst.add(child);
            }

            if (i==word.length()-1) {
                child.isEnd = true;
                return;
            }

            insert(word, i+1, child);
        }

        private boolean search(String word, int i, Tree node) {
            Tree child = node.lst.stream().filter(x->x.c==word.charAt(i)).findFirst().orElse(null);
            if (child==null) return false;
            if (i==word.length()-1) return child.isEnd;
            return search(word, i+1, child);
        }

        private boolean startsWith(String word, int i, Tree node) {
            if (i==word.length()) return true;
            Tree child = node.lst.stream().filter(x->x.c==word.charAt(i)).findFirst().orElse(null);
            if (child==null) return false;
            return startsWith(word, i+1, child);
        }
    }






    static class Trie3 {
        Node root;

        public Trie3() {
            root = new Node();
        }

        public void insert(String word) {
            root.insert(word, 0);
        }

        public boolean search(String word) {
            return root.search(word, 0, false);
        }

        public boolean startsWith(String prefix) {
            return root.search(prefix, 0, true);
        }

        static class Node {
            Node[] nodes;
            boolean isEnd;

            Node() {
                nodes = new Node[26];
            }

            private void insert(String word, int idx) {
                if (idx >= word.length()) return;
                int i = word.charAt(idx) - 'a';
                if (nodes[i] == null) {
                    nodes[i] = new Node();
                }

                if (idx == word.length()-1) nodes[i].isEnd = true;
                nodes[i].insert(word, idx+1);
            }

            private boolean search(String word, int idx, boolean checkPrefix) {
                if (idx >= word.length()) return false;
                Node node = nodes[word.charAt(idx) - 'a'];
                if (node == null) return false;
                if (idx == word.length() - 1 && (checkPrefix || node.isEnd)) return true;

                return node.search(word, idx+1, checkPrefix);

            }

            private boolean startsWith(String prefix, int idx) {
                if (idx >= prefix.length()) return false;
                Node node = nodes[prefix.charAt(idx) - 'a'];
                if (node == null) return false;
                if (idx == prefix.length() - 1) return true;

                return node.startsWith(prefix, idx+1);
            }
        }
    }
}
