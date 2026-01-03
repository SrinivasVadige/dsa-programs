package Algorithms.Graphs;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 03 Jan 2026
 * @link 127. Word Ladder <a href="https://leetcode.com/problems/word-ladder/">LeetCode Link</a>
 * @topics Graph, Hash Table, DFS, BFS
 * @companies CleverTap(8), Google(5), Meta(4), Amazon(4), Microsoft(3), Bloomberg(3), Oracle(3), ByteDance(2), Citadel(2), Okta(2), LinkedIn(5), Apple(3), TikTok(2), ServiceNow(2), Expedia(2), Reddit(18), Snap(8), Uber(6), eBay(6), Adobe(4), Flipkart(3), Visa(3), Salesforce(3), Box(3), The Trade Desk(3)
 */
public class WordLadder {
    public static void main(String[] args) {
        String beginWord = "hit", endWord = "cog";
        List<String> wordList = new ArrayList<>(Arrays.asList("hot","dot","dog","lot","log","cog"));
        System.out.println("ladderLength Using BFS 1 -> " + ladderLengthUsingBfs1(beginWord, endWord, wordList));
        System.out.println("ladderLength Using BFS 2 -> " + ladderLengthUsingBfs2(beginWord, endWord, wordList));
        System.out.println("ladderLength Using BFS 3 -> " + ladderLengthUsingBfs3(beginWord, endWord, wordList));
        System.out.println("ladderLength Using BFS 4 -> " + ladderLengthUsingBfs4(beginWord, endWord, wordList));
    }


    /**
     * @TimeComplexity O(M^2 * N), where M is the length of each word and N wordList size
     * @SpaceComplexity O(N)
     */
    public static int ladderLengthUsingBfs1(String beginWord, String endWord, List<String> wordList) {
        int k = beginWord.length();
        Set<String> wordSet = new HashSet<>(wordList);
        if (wordSet.add(endWord)) return 0;

        Queue<String> q = new LinkedList<>();
        Set<String> seen = new HashSet<>();
        seen.add(beginWord);
        q.add(beginWord);

        int transformations = 1;
        while(!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {

                String currWord = q.poll();
                if (currWord.equals(endWord)) return transformations;

                char[] currWordArr = currWord.toCharArray();
                for (int i=0; i<k; i++) {
                    char oldChar = currWordArr[i];

                    for(char c='a'; c<='z'; c++) {
                        if (c==oldChar) continue;
                        currWordArr[i] = c;
                        String nextWord = new String(currWordArr);
                        if (!seen.contains(nextWord) && wordSet.contains(nextWord)) {
                            seen.add(nextWord);
                            q.add(nextWord);
                        }
                    }
                    currWordArr[i] = oldChar;
                }
            }
            transformations++;
        }

        return 0;
    }










    /**
     * @TimeComplexity O(M^2 * N), where M is the length of each word and N wordList size
     * @SpaceComplexity O(N)
     */
    public static int ladderLengthUsingBfs2(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) return 0;

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int steps = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                String word = queue.poll();
                if (word.equals(endWord)) return steps;

                for (int i = 0; i < word.length(); i++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        char[] chars = word.toCharArray();
                        chars[i] = c;
                        String pattern = new String(chars);

                        if (wordSet.contains(pattern)) {
                            queue.offer(pattern);
                            wordSet.remove(pattern);
                        }
                    }
                }
            }
            steps++;
        }
        return 0;
    }









    /**
     * @TimeComplexity O(M^2 * N), where M is the length of each word and N wordList size
     * @SpaceComplexity O(MN)
     */
    public static int ladderLengthUsingBfs3(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) return 0;

        // pattern -> list of words
        Map<String, List<String>> neighbors = new HashMap<>();
        wordList.add(beginWord);

        for (String word : wordList) {
            for (int i = 0; i < word.length(); i++) {
                String pattern = word.substring(0, i) + "*" + word.substring(i + 1);
                neighbors.computeIfAbsent(pattern, k -> new ArrayList<>()).add(word);
            }
        }

        Set<String> visit = new HashSet<>();
        Queue<String> q = new ArrayDeque<>();
        visit.add(beginWord);
        q.offer(beginWord);

        int transformations = 1;
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                String word = q.poll();
                if (word.equals(endWord)) return transformations;

                for (int i = 0; i < word.length(); i++) {
                    String pattern = word.substring(0, i) + "*" + word.substring(i + 1);
                    for (String neiWord : neighbors.getOrDefault(pattern, Collections.emptyList())) {
                        if (!visit.contains(neiWord)) {
                            visit.add(neiWord);
                            q.offer(neiWord);
                        }
                    }
                }
            }
            transformations++;
        }

        return 0;
    }









    /**
     * @TimeComplexity O(M^2 * N), where M is the length of each word and N wordList size
     * @SpaceComplexity O(MN)
     * but much faster than {@link #ladderLengthUsingBfs3}
     */
    static class Pair extends AbstractMap.SimpleEntry<String, Integer>{Pair(String k, Integer v){super(k,v);}}
    private static int L = 0;
    private static Map<String, List<String>> allComboDict  = new HashMap<>();
    public static int ladderLengthUsingBfs4(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) return 0;

        L = beginWord.length();
        allComboDict  = new HashMap<>();

        wordList.forEach(word -> {
            for (int i = 0; i < L; i++) {
                String pattern = word.substring(0, i) + '*' + word.substring(i + 1, L);
                List<String> transformations = allComboDict.getOrDefault(pattern, new ArrayList<>());
                transformations.add(word);
                allComboDict.put(pattern, transformations);
            }
        });

        Queue<Pair> Q_begin = new LinkedList<>();
        Queue<Pair> Q_end = new LinkedList<>();
        Q_begin.add(new Pair(beginWord, 1));
        Q_end.add(new Pair(endWord, 1));

        Map<String, Integer> visitedBegin = new HashMap<>();
        Map<String, Integer> visitedEnd = new HashMap<>();
        visitedBegin.put(beginWord, 1);
        visitedEnd.put(endWord, 1);
        int ans = -1;

        while (!Q_begin.isEmpty() && !Q_end.isEmpty()) {
            if (Q_begin.size() <= Q_end.size()) {
                ans = visitWordNode(Q_begin, visitedBegin, visitedEnd);
            } else {
                ans = visitWordNode(Q_end, visitedEnd, visitedBegin);
            }

            if (ans > -1) {
                return ans;
            }
        }

        return 0;
    }
    private static int visitWordNode(Queue<Pair> Q, Map<String, Integer> visited, Map<String, Integer> othersVisited) {
        for (int j = Q.size(); j > 0; j--) {
            Pair node = Q.remove();
            String word = node.getKey();
            int level = node.getValue();

            for (int i = 0; i < L; i++) {
                String pattern = word.substring(0, i) + '*' + word.substring(i + 1, L);

                for (String adjacentWord : allComboDict.getOrDefault(pattern,new ArrayList<>())) {
                    if (othersVisited.containsKey(adjacentWord)) {
                        return level + othersVisited.get(adjacentWord);
                    }

                    if (!visited.containsKey(adjacentWord)) {
                        visited.put(adjacentWord, level + 1);
                        Q.add(new Pair(adjacentWord, level + 1));
                    }
                }
            }
        }
        return -1;
    }
}
