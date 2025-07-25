package Algorithms.DynamicProgramming;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
    Given a string s and a dictionary of strings wordDict, return true if s can be segmented into a space-separated sequence of one or more dictionary words.
    n^2 allowed
    wordDict is like a set as every word is unique

    loop wordDict and divide the left and right parts of "s" as per word index =
    and again check next  word index divide left and right parts ... so on =
    and finally all "s" parts in wordDict

    looks like binary tree

    top-down memo dp? -- remove the matched word in wordDict i.e n-1 and send to child?

    t-d return? =

    note that word in wordDict can have multiple instances in "s" --> so n-1 the list? or hashmap to check if already validated?

    </pre>

 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 26 Oct 2024
 * @link 139. Word Break <a href="https://leetcode.com/problems/word-break/">LeetCode link</a>
 * @topics Array, Hash Table, String, Dynamic Programming, Memoization, Trie
 * @companies Amazon, Meta, Google, Microsoft, Walmart Labs, Apple, MongoDB, Bloomberg, Uber, Netflix, TikTok, Adobe, Salesforce, Oracle, Intuit, Coupang, Yahoo, LinkedIn, Goldman Sachs, Zoho
 */
public class WordBreak {
    public static void main(String[] args) {
        // "ccaccc", ["cc","ac"]
        // "leetcode", ["leet", "code"]
        // "catsandog". ["cats","dog","sand","and","cat"]
        String s = "ccaccc";
        List<String> wordDict = Arrays.asList("cc","ac");

        // loops "s" ---> O(n^2) time
        System.out.println("wordBreak using backtracking over s: " + wordBreakUsingBacktrackingOverS(s, wordDict));
        System.out.println("wordBreak using topDownMemoDp over s: " + wordBreakUsingTopDownMemoWithDfsDpOverS(s, wordDict));
        System.out.println("wordBreak using bottomUpTabulationDp over s: " + wordBreakUsingBottomUpTabulationDpWithBfsOverS(s, wordDict));


        // loops "wordDict" ---> O(mn) time
        System.out.println("wordBreak using backtracking over wordDict: " + wordBreakStartsUsingBacktrackingOverWordDictLoop(s, wordDict));
        System.out.println("wordBrea using topDownMemoDp over WordDict: " + wordBreakUsingTopDownMemoDpOverWordDictLoop(s, wordDict));
        System.out.println("wordBreak using bottomUpTabulationDp over WordDict: " + wordBreakUsingBottomUpTabulationDpOverWordDictLoop(s, wordDict));


        // Trie ---> O(n^2+m⋅k) time
        System.out.println("wordBreakTrieApproach: " + wordBreakTrieApproach(s, wordDict));
    }


    // LOOP OVER "s"

    /**
     * @TimeComplexity O(2^n) -- TLE
     * @SpaceComplexity O(k), where k = wordDict.size()


     s = "leetcode", wordDict = ["lee", "code", "leetc", "ode"]
     so, only "leetc + ode" works


     ================= DECISION TREE ================


                     ✅ 2 matches      l e e t c o d e
                                       l
                            ________________|________________
                            |                               |
                      l e e t c o d e                l e e t c o d e
                      l   r                          l       r
                             |                              |
      ❌ 0 matches   l e e t c o d e                l e e t c o d e    ✅ 1 match
                            l         r                        l  r
                                                             |
                                                     l e e t c o d e
                                                                     l
                                                          ✅ IndexOutOfBound
                                                         DONE

     */
    public static boolean wordBreakUsingBacktrackingOverS(String s, List<String> wordDict) {
        return backtrack(s, s.length(), 0, new StringBuilder(), new HashSet<>(wordDict));
    }

    private static boolean backtrack(String s, int n, int l, StringBuilder sb, Set<String> wordSet){
        if(l==n) {
            return true;
        }

        boolean isFound = false;
        for(int r=l; r<n; r++) {
            sb.append(s.charAt(r));
            if(wordSet.contains(sb.toString()) && !isFound) { // or if(wordSet.contains(sb.toString()) && backtrack(s, n, r+1, new StringBuilder(), wordSet, seen) {isFound = true; break;}
                isFound = backtrack(s, n, r+1, new StringBuilder(), wordSet);
            }
        }
        // sb.setLength(0); // free up reused buffer -- optional

        return isFound;
    }









    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n + k), n = s.length(), k = wordDict.size()
     */
    public static boolean wordBreakUsingTopDownMemoWithDfsDpOverS(String s, List<String> wordDict) {
        return dfs(s, s.length(), 0, new StringBuilder(), new HashSet<>(wordDict), new Boolean[s.length()]);
    }

    private static boolean dfs(String s, int n, int l, StringBuilder sb, Set<String> wordSet, Boolean[] seen){
        if(l==n) {
            return true;
        } else if (seen[l] != null) {
            return seen[l];
        }

        boolean isFound = false;
        for(int r=l; r<n; r++) {
            sb.append(s.charAt(r));
            if(wordSet.contains(sb.toString()) && !isFound) { // or if(wordSet.contains(sb.toString()) && dfs(s, n, r+1, new StringBuilder(), wordSet, seen) {isFound = true; break;}
                isFound = dfs(s, n, r+1, new StringBuilder(), wordSet, seen);
            }
        }
        // sb.setLength(0); // free up reused buffer -- optional

        return seen[l] = isFound;
    }


    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n + k), n = s.length(), k = wordDict.size()



        here we have to use combination of (seenSet and queue) to avoid -> duplication check
        or
        we can use boolean[] dp array just like {@link #wordBreakUsingBottomUpTabulationDpOverWordDictLoop}
     */
    public static boolean wordBreakUsingBottomUpTabulationDpWithBfsOverS(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        int n = s.length();
        boolean[] dp = new boolean[n];

        Queue<Integer> starts = new LinkedList<>();
        starts.add(0);
        Set<Integer> seen = new HashSet<>();

        while(!starts.isEmpty()) {
            int l = starts.poll();
            // if (l == n) return true;
            for (int r = l; r < n; r++) {
                if (wordSet.contains(s.substring(l, r+1))) {
                    dp[r] = true;
                    if (seen.add(r)) starts.offer(r+1);
                }
            }
        }

        return dp[n-1];
    }



    public static boolean wordBreakUsingBottomUpTabulationDpWithBfsOverS2(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        List<Integer> trues = new ArrayList<>(); // or use Queue
        trues.add(0); // starting point

        int i = 0;
        while (i < trues.size()) {
            int l = trues.get(i++);
            for (int r = l + 1; r <= n; r++) {
                if (wordSet.contains(s.substring(l, r)) && !dp[r]) {
                    dp[r] = true;
                    trues.add(r); // ✅ Safe, no CME: we're not using iterator (as forEach is a Iterator.hasNext() loop internally)
                }
            }
        }

        return dp[n];
    }



    public static boolean wordBreakUsingBottomUpTabulationDpWithBfsOverS3(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        List<Integer> trues = new ArrayList<>();
        trues.add(0);
        ListIterator<Integer> it = trues.listIterator();

        while (it.hasNext()) {
            int l = it.next();
            for (int r = l + 1; r <= n; r++) {
                if (wordSet.contains(s.substring(l, r)) && !dp[r]) {
                    dp[r] = true;
                    it.add(r);
                    it.previous(); // ✅ Safe, no CME: as we use ListIterator (not Iterator) and decrement it's cursor position
                }
            }
        }

        return dp[n];
    }


    /**
        s = "helloworld", wordDict = ["world", "ello", "he", "lo", "ll"]

     initial dp of wordSizes
        dp =   "",  "h",   "he", "hel","hell","hello","hellow","hellowo", "hellowor", "helloworl", "helloworld"

           ""     h      e      l      l      o       w          o          r           l           d
         [true, false, false, false, false, false,  false,     false,     false,      false,      false]

         dp[0]=true i.e we can make "" without any wordDict words

        wSize=1, trues=[0], l=0
          l=0
          ""     h      e      l      l      o      w      o      r      l      d
        [true, false, false, false, false, false, false, false, false, false, false]    l=0 --> cause we have "he". So, break;
        [true, false, true, false, false, false, false, false, false, false, false]  wSize=2, trues=[], l=1
        [true, false, true, false, false, false, false, false, false, false, false]
        [true, false, true, false, false, false, false, false, false, false, false]

     */
    public static boolean wordBreakUsingBottomUpTabulationDpWithBfsOverS4(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        int n = s.length();
        boolean[] dp = new boolean[n + 1]; // wordSizes from "" to "helloworld"
        dp[0] = true;

        List<Integer> trues = new ArrayList<>(); // --- just like queue
        trues.add(0); // l=0

        for (int wSize = 1; wSize <= n; wSize++) { // rExclusive --- number of chars or word size
            for (int l : trues) { // lInclusive
                if (wordSet.contains(s.substring(l, wSize))) {
                    dp[wSize] = true;
                    trues.add(wSize);
                    break; // ✅ because of this break, it doesn't throw CME
                }
            }
        }

        return dp[n];

        /*

        NOTE:

        List<Integer> trues = new ArrayList<>(); // --- just like queue
        trues.add(0);
        for (int l : trues) { // lInclusive
            for (int r = 1; r <= n; r++) { // rExclusive
                if (wordSet.contains(s.substring(l, r))) {
                    dp[r] = true;
                    trues.add(r); // ----> ❌Throws ConcurrentModificationException
                    break;
                }
            }
        }


         and


        Set<Integer> trues = new HashSet<>();
        trues.add(0);
        for (int l : trues) {
            for (int r = l + 1; r <= n; r++) {
                if (wordSet.contains(s.substring(l, r))) {
                    dp[r] = true;
                    trues.add(r); // ❌ CME or silent skip
                }
            }
        }


        and


        List<Integer> trues = new ArrayList<>();
        trues.add(0);
        ListIterator<Integer> it = trues.listIterator();

        while (it.hasNext()) {
            int l = it.next();
            for (int r = l + 1; r <= n; r++) {
                if (wordSet.contains(s.substring(l, r)) && !dp[r]) {
                    dp[r] = true;
                    it.add(r); // ❌ It doesn't throw CME but it silently skips it ❌ not good
                }
            }
        }


         */
    }















    /**
     * working but TLE
     * check {@link #wordBreakIndexOfApproach} for more understanding
     * note that wordBreakIndexOfApproach() only works for unique words with unique chars


     s = "leetcode", wordDict = ["lee", "code", "leetc", "ode"]
     so, only "leetc + ode" works


        ============================================ DECISION TREE ===============================================



                       check "tcode"                         check "leetcode"      check "ode"
                         new i=3                                      i=0         new i=5
                          _____________________________________________|__________________________________________
                          |                            |                            |                            |
                         lee                         code                         leetc          check ""       ode
                          ✅                          ❌                           ✅            new i=8        ❌
       ___________________|__________________                  _____________________|__________________
       |           |           |            |                  |            |            |            |
      lee         code        leetc        ode                lee          code         leetc        ode
       ❌          ❌          ❌          ❌                  ❌          ❌           ❌          ✅
                                                                                                     DONE



    */
    public static boolean wordBreakStartsUsingBacktrackingOverWordDictLoop(String s, List<String> wordDict) {
        return backtrack(s, wordDict);
    }

    private static boolean backtrack(String s, List<String> list) {
        if (s.isEmpty()) return true;

        for (String w: list) {
            if(s.startsWith(w) && backtrack(s.substring(w.length()), list)) { // or s.startsWith(word, i) && dfs(s, i + word.length(), wordDict)
                return true;
            }
        }
        return false;
    }







    /**
     * @TimeComplexity O(m*n)
     * @SpaceComplexity O(m)
     * Top-Down Memo DP but as we start from 0 index we call it as dfs i.e increase depth
     * and if matched then make the start index as "after that word"
     */
    public static boolean wordBreakUsingTopDownMemoDpOverWordDictLoop(String s, List<String> wordDict) {
        return dfs(s, 0, wordDict, new Boolean[ s.length()]);
    }

    private static boolean dfs(String s, int i, List<String> wordDict, Boolean[] dp) {
        if (i == s.length()) return true; // i.e., exactly matched "leetcode↓" ---> IndexOutOfBound base case
        if (dp[i] != null) return dp[i];

        for (String word : wordDict) {
            if (s.startsWith(word, i) && dfs(s, i + word.length(), wordDict, dp)) // move to r+1 index, just like #wordBreakUsingTopDownMemoWithDfsDpOverS()
                return dp[i] = true;
        }
        return dp[i] = false;
    }









    /**
     * <pre>
     * traverse only the custom start indices and check if any word in wordDict can be formed from that index
     * works on "future start indices" or "next validation start indices" - i.e up to index-1 substring is already calculated
     * so, after total validation, the future start index of the complete string s="leetcode" is s.length() => "leetcode↓" i.e not s.length()-1
     * Note: In this all possible future start index validations, we might have same start index
     * --> i.e in this kind of dp we do not return already calculated value but we might assign same value "true" to same index in multiple possibilities
     * so, using this we can also calculate count of all possibilities to form the s string with some extra logic
     * </pre>
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(m)
     */
    public static boolean wordBreakUsingBottomUpTabulationDpOverWordDictLoop(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1]; // +1 for "leetcode↓" ---> it maintains all possible future start indices
        dp[0] = true; // initial future start index
        for (int l = 0; l < s.length(); l++) {
            if (!dp[l]) continue; // => skip up the non-valid future indexes
            for (String word : wordDict) {
                if (l + word.length() <= s.length() && s.startsWith(word, l)) {
                    dp[l + word.length()] = true; // r+1 => future start indices
                }
            }
        }
        return dp[s.length()]; // "leetcode↓" - "next validation start index" of the complete string is true
    }





    public static boolean wordBreakUsingBottomUpTabulationDpOverWordDictLoop2(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length()]; // true for valid start indices
        for (int i = s.length() - 1; i >= 0; i--) {
            if (wordDict.contains(s.substring(i)))
                dp[i] = true;
            else {
                for (int j = i + 1; j < s.length(); j++) { // or word list for loop
                    if (dp[j] && wordDict.contains(s.substring(i, j))) { // dp[j] == true means s.substring(j) is valid
                        dp[i] = true;
                        break;
                    }
                }
            }
        }
        return dp[0]; // "[l]eetcode" i.e total s validation completed from right to left
    }












    /**
     * @TimeComplexity O(n^2+m⋅k)
     * @SpaceComplexity O(n+m⋅k)

          s = "leetcode", wordDict = ["lee", "code", "leetc", "ode"]
          so, only "leetc + ode" works


                                        ================= TRIE NODE TREE ================


                                                              root
                                                               ""
                                          ______________________|_____________________
                                          |                     |                    |
                                          c                     l                    o
                                          |                     |                    |
                                          o                     e                    d
                                          |                     |                    |
                                          d                     e true               e true
                                          |                     |
                                          e true                t
                                                                |
                                                                c true

     */
    public static boolean wordBreakTrieApproach(String s, List<String> wordDict) {
        class TrieNode {
            boolean isWord;
            final Map<Character, TrieNode> children = new HashMap<>();
        }
        TrieNode root = new TrieNode();

        // Step 1: Build Trie -- O(m⋅k), where m = s.length and k = wordDict.size
        for (String word : wordDict) {
            TrieNode curr = root;
            for (char c : word.toCharArray()) {
                curr.children.computeIfAbsent(c, k -> new TrieNode()); // or if (!curr.children.containsKey(c)) curr.children.put(c, new TrieNode());
                curr = curr.children.get(c);
            }
            curr.isWord = true;
        }

        // Step 2: Check if any word can be formed -- O(n^2)
        boolean[] dp = new boolean[s.length()];
        for (int l = 0; l < s.length(); l++) {
            if (l == 0 || dp[l - 1]) { // i.e dp[l] is valid word end
                TrieNode curr = root;
                for (int r = l; r < s.length(); r++) {
                    char c = s.charAt(r);
                    if (!curr.children.containsKey(c)) {
                        break; // No words exist
                    }

                    curr = curr.children.get(c);
                    if (curr.isWord) {
                        dp[r] = true; // mark all "r" valid pointers
                    }
                }
            }
        }
        return dp[s.length() - 1];
    }

















    // -------------- MY THOUGHTS - 26/10/2024 -------------

    @SuppressWarnings("unused")
    public static boolean wordBreakIndexOfApproach(String s, List<String> wordDict) {
        Map<String, Integer> map = wordDict.stream().collect(
            Collectors.groupingBy(i->i, Collectors.summingInt(e->0)) );
        return rec1(s, map);
    }

    // -- failing for "ccaccc" ["cc","ac"] and "aaaaaaa" ["aaaa","aaa"]
    public static boolean rec1(String s, Map<String, Integer> map ){
        //System.out.println(s);
        int i = -1;
        if(s.isEmpty()) return true;
        for (Map.Entry<String, Integer> entry: map.entrySet()){
            //System.out.println(s + " " + entry.getKey());
            if(s.equals(entry.getKey()) || map.keySet().contains(s)) return true;

            i = s.indexOf(entry.getKey());

            if(i>-1){
                map.put(entry.getKey(), entry.getValue()+1);
                return rec1( i==0? "" : s.substring(0, i), map)
                && rec1( s.substring(i+entry.getKey().length()), map );
            }
        }
        if(i==-1) return false;
        return true;
    }

    // success for "ccaccc" ["cc","ac"] and "aaaaaaa" ["aaaa","aaa"] but
    // --- failing for "catsandogcat" ["cats","dog","sand","and","cat","an"]
    // because it can divide it with "sand", "and"
    // write a logic to check with all start possibilities like cat and cats...
    // so, use startsWith() instead of indexOf??
    public boolean rec2(String s, Map<String, Integer> map ){
        //System.out.println(s);
        int i = -1;
        if(s.isEmpty()) return true;
        for (Map.Entry<String, Integer> entry: map.entrySet()){
            //System.out.println(s + " " + entry.getKey());
            if(s.equals(entry.getKey())){
                map.merge(s, 1, Integer::sum);
                return true;
            }

            i = s.indexOf(entry.getKey());

            if(i>-1){
                if(entry.getValue() > 0) continue;
                map.put(entry.getKey(), entry.getValue()+1);
                return rec2( i==0? "" : s.substring(0, i), map)
                && rec2( s.substring(i+entry.getKey().length()), map );
            }
        }
        if(i==-1) return false;
        return true;
    }











    /**
     * STILL TLE
     * same as wordBreakStartsWithRecursiveBacktracking() but use dp memo for todo index
     * "catsandogcat", ["cats","dog","sand","and","cat","an"]
     * here check "cats" scenario, "cat" scenario and here we already reached last "ogcat" case
     * as we already checked upto "catsand" i.e "cats, and" or "cat sand"
     */
    public static boolean wordBreakStartsWithTopDownMemoDp(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        rec(s, wordDict, dp, s.length()-1);
        return dp[dp.length-1]; // last index to save if we already reached up to "" in rec()
    }

    private static boolean rec(String s, List<String> list, boolean[] dp, int i) {
        System.out.println(s);
        if (s.isEmpty()){
            dp[dp.length-1]=true;
            return true;
        }
        else if(dp[dp.length-1] == true) return true;
        else if(dp[i] == true) return true;

        for (String w: list) {
            if(dp[dp.length-1] == true) return true;
            if(s.startsWith(w)) {
                dp[i] = rec(s.substring(w.length()), list, dp, dp.length-s.length()-1);
            }
        }

        return false;
    }

}
