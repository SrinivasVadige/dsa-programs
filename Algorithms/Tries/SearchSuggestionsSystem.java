package Algorithms.Tries;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 19 May 2025
 */
public class SearchSuggestionsSystem {
    public static void main(String[] args) {
        String[] products = {"mobile","mouse","moneypot","monitor","mousepad"};
        String searchWord = "mouse";
        System.out.println("suggestedProducts => " + suggestedProducts(products, searchWord));
        System.out.println("suggestedProductsUsingTwoPointers => " + suggestedProductsUsingTwoPointers(products, searchWord));
        System.out.println("suggestedProductsMyApproach => " + suggestedProductsMyApproach(products, searchWord));
        System.out.println("suggestedProductsUsingTrie => " + suggestedProductsUsingTrie(products, searchWord));
        System.out.println("suggestedProductsUsingTrieAndSuggestionsArray => " + suggestedProductsUsingTrieAndSuggestionsArray(products, searchWord));
    }


    public static List<List<String>> suggestedProducts(String[] products, String searchWord) {
        List<List<String>> ans = new ArrayList<>();
        if (products == null || products.length == 0 || searchWord == null || searchWord.length() == 0) return ans;
        Arrays.sort(products);
        for (int i = 0; i < searchWord.length(); i++) {
            List<String> curr = new ArrayList<>();
            for (int j = 0; j < products.length; j++) {
                if (products[j].startsWith(searchWord.substring(0, i + 1))) curr.add(products[j]);
                if (curr.size() == 3) break;
            }
            ans.add(curr);
        }
        return ans;
    }




    /**
     * @TimeComplexity nlogn + n*w + m, where w is length of each word
     */
    public static List<List<String>> suggestedProductsUsingTwoPointers(String[] products, String searchWord) {
        List<List<String>> res = new ArrayList<>();
        Arrays.sort(products);
        int n = products.length, l = 0, r = n - 1;
        for (int i = 0; i < searchWord.length(); i++) {
            char c = searchWord.charAt(i);
            while (l <= r && (products[l].length() <= i || products[l].charAt(i) < c)) l++;
            while (l <= r && (products[r].length() <= i || products[r].charAt(i) > c)) r--;

            // if (l > r) {
            //     res.add(new ArrayList<>());
            //     continue;
            // }

            List<String> curr = new ArrayList<>();
            for (int j = l; j <= r && curr.size() < 3; j++) {
                curr.add(products[j]);
            }
            res.add(curr);
        }
        return res;
    }











    public static List<List<String>> suggestedProductsUsingTrie(String[] products, String searchWord) {
        List<List<String>> res = new ArrayList<>();
        if (products == null || products.length == 0 || searchWord == null || searchWord.length() == 0) return res;
        Trie trie = new Trie();
        for (String product : products) trie.insert(product);
        for (int i = 0; i < searchWord.length(); i++) {
            List<String> curr = new ArrayList<>();
            List<String> suggestions = trie.search(searchWord.substring(0, i + 1));
            Collections.sort(suggestions);
            for (String suggestion : suggestions) {
                if (curr.size() < 3 && suggestion.startsWith(searchWord.substring(0, i + 1))) curr.add(suggestion);
                else if (curr.size() >= 3) break;
            }
            res.add(curr);
        }
        return res;
    }
    static class Trie {
        class TrieNode {
            TrieNode[] children;
            boolean isEnd;
            public TrieNode() {
                children = new TrieNode[26];
                isEnd = false;
            }
        }
        TrieNode root;
        public Trie() {
            root = new TrieNode();
        }
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
        public List<String> search(String word) {
            List<String> res = new ArrayList<>();
            TrieNode curr = root;
            for (char c : word.toCharArray()) {
                if (curr.children[c - 'a'] == null) return res;
                curr = curr.children[c - 'a'];
            }
            dfs(curr, word, res);
            return res;
        }
        private void dfs(TrieNode node, String prefix, List<String> res) {
            if (node.isEnd) res.add(prefix);
            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null) {
                    dfs(node.children[i], prefix + (char)('a' + i), res);
                }
            }
        }
    }










    static class TrieNode{
        TrieNode[] children = new TrieNode[26];
        List<String> suggestions = new ArrayList<>();
    }
    private static TrieNode root = new TrieNode();


    public static List<List<String>> suggestedProductsUsingTrieAndSuggestionsArray(String[] products, String searchWord) {
        Arrays.sort(products);

        // insert
        for(String st : products) insert(st);

        // search
        List<List<String>> result = new ArrayList<>();
        TrieNode node = root;
        for(char c : searchWord.toCharArray()){
            int id = c - 'a';
            if(node != null){
                node = node.children[id];
            }
            result.add(node == null ? new ArrayList<>() : node.suggestions);
        }
        return result;
    }

    public static void insert(String word){
        TrieNode node = root;
        for(char c : word.toCharArray()){
            int idx = c - 'a';
            if(node.children[idx] == null) node.children[idx] = new TrieNode();

            node = node.children[idx];

            if(node.suggestions.size() < 3) node.suggestions.add(word);
        }
    }











    class Trie3 {
        Trie3[] children = new Trie3[26];
        List<Integer> v = new ArrayList<>();

        public void insert(String w, int i) {
            Trie3 node = this;
            for (int j = 0; j < w.length(); ++j) {
                int idx = w.charAt(j) - 'a';
                if (node.children[idx] == null) {
                    node.children[idx] = new Trie3();
                }
                node = node.children[idx];
                if (node.v.size() < 3) {
                    node.v.add(i);
                }
            }
        }

        @SuppressWarnings("all")
        public List<Integer>[] search(String w) {
            Trie3 node = this;
            int n = w.length();
            List<Integer>[] ans = new List[n];
            Arrays.setAll(ans, k -> new ArrayList<>());
            for (int i = 0; i < n; ++i) {
                int idx = w.charAt(i) - 'a';
                if (node.children[idx] == null) {
                    break;
                }
                node = node.children[idx];
                ans[i] = node.v;
            }
            return ans;
        }
    }

    public List<List<String>> suggestedProducts3(String[] products, String searchWord) {
        Arrays.sort(products);
        Trie3 trie = new Trie3();
        for (int i = 0; i < products.length; ++i) {
            trie.insert(products[i], i);
        }
        List<List<String>> ans = new ArrayList<>();
        for (var v : trie.search(searchWord)) {
            List<String> t = new ArrayList<>();
            for (int i : v) {
                t.add(products[i]);
            }
            ans.add(t);
        }
        return ans;
    }














/**

        MY APPROACH

 */
    static class Node {
        Node[] arr;
        boolean isEnd;
        Node(){
            arr = new Node[26];
            isEnd = false;
        }
    }
    static Node rootNode = new Node();
    static List<List<String>> res = new ArrayList<>();

    public static List<List<String>> suggestedProductsMyApproach(String[] products, String searchWord) {
        res = new ArrayList<>();
        // prepare trie
        prepareTrie(products);

        // enter each char
        // Node trav = rootNode;
        for(int i=0; i<searchWord.length(); i++) {
            prefix(searchWord.substring(0, i+1));

            // char c = searchWord.charAt(i);
            // List<String> lst = new ArrayList<>();
            // if(trav.arr[c-'a']!=null) {
            //     trav=trav.arr[c-'a'];
            //     findChildren(
            //         new StringBuilder(searchWord.substring(0,i+1)),
            //         trav, lst
            //     );
            // }
            // res.add(lst);
        }
        return res;
    }

    private static void prepareTrie(String[] products) {
        for(String product: products) {
            Node trav = rootNode;
            for(char c: product.toCharArray()) {
                if(trav.arr[c-'a'] == null) trav.arr[c-'a'] = new Node();
                trav = trav.arr[c-'a'];
            }
            trav.isEnd=true;
        }
    }

    private static void prefix(String str) {
        List<String> lst = new ArrayList<>();
        Node trav = rootNode;
        for(char c : str.toCharArray()) {
            if(trav.arr[c-'a']==null) {
                res.add(lst);
                return;
            }
            trav = trav.arr[c-'a'];
        }

        // find 3 children with isEnd=true;
        findChildren(new StringBuilder(str), trav, lst); // dfs
        res.add(lst);
    }

    private static void findChildren(StringBuilder sb, Node trav, List<String> lst) { // dfs
        if(lst.size()==3) return;
        if(trav.isEnd) lst.add(sb.toString());
        // for loop
        for(int i=0; i<26; i++) {
            if(trav.arr[i]!=null) {
                findChildren(sb.append((char)('a'+i)), trav.arr[i], lst);
                sb.deleteCharAt(sb.length() - 1); // or sb.delete(sb.length() - 1, sb.length());
                // to revert the change
            }
        }
    }
}
