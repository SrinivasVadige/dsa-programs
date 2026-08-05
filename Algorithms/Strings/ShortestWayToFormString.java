package Algorithms.Strings;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 04 Aug 2026
 * @link 1055. Shortest Way to Form String <a href="https://leetcode.com/problems/shortest-way-to-form-string/">LeetCode link</a>
 * @topics Two Pointers, String, Binary Search, Greedy, Dynamic Programming
 * @companies Pinterest(3), Meta(2)
 * @description Shortest Way to Build a String
 * You are given two lowercase strings: source and target.
 * A subsequence is formed by removing zero or more characters from a string while preserving the order of the characters that remain. For example, "ace" can be obtained from "abcde", but "aec" cannot.
 * Find the smallest number of subsequences taken from source whose concatenation produces target. A character from source may be used only once within a single subsequence, but each new subsequence starts again from the beginning of source.
 * Return -1 when forming target is impossible.
 * Examples
 * source = "abc", target = "abcbc"
 * answer = 2
 * Use "abc" first, then "bc":
 * "abc" + "bc" = "abcbc"
 * source = "abc", target = "acdbc"
 * answer = -1
 * target contains 'd', which does not exist in source.
 * source = "xyz", target = "xzyxz"
 * answer = 3
 * One valid construction is:
 * "xz" + "y" + "xz" = "xzyxz"
 * Constraints
 * 1 <= source.length(), target.length() <= 1000
 * source and target contain only lowercase English letters.
 */
public class ShortestWayToFormString {
    static void main() {
        String source = "abc";
        String target = "abcbc";
        System.out.println("ShortestWay using two pointers 1: " + shortestWayUsingTwoPointers1(source, target));
        System.out.println("ShortestWay using two pointers 2: " + shortestWayUsingTwoPointers2(source, target));
        System.out.println("ShortestWay using two pointers 3: " + shortestWayUsingTwoPointers3(source, target));
        System.out.println("ShortestWay using two pointers 4: " + shortestWayUsingTwoPointers4(source, target));
        System.out.println("ShortestWay using two pointers 5: " + shortestWayUsingTwoPointers5(source, target));

        System.out.println("ShortestWay using inverted index and binary search: " + shortestWayUsingTwoPointersWithInvertedIndexAndBinarySearch(source, target));
        System.out.println("ShortestWay using concatenate until subsequence: " + shortestWayUsingTwoPointersWithConcatenateUntilSubsequence(source, target));

        System.out.println("ShortestWay using backtracking: " + shortestWayUsingBacktracking1_TLE(source, target));
        System.out.println("ShortestWay using top-down memoization DP: " + shortestWayUsingTopDownMemoDp1_STACK_OVERFLOW(source, target));
        System.out.println("ShortestWay using bottom-up tabulation DP: " + shortestWayUsingBottomUpTabulationDp1(source, target));


    }




    /**

        if target has new chars or j-pointer == m then -1 or
        if source == target then 1


        xyz
           i

        xzyxz
         j


        count = 2

        aaaaa
           i

        aaaaaaaaaaaaa
                     j

     * @TimeComplexity O(mn)
     * @SpaceComplexity O(1)
    */
    public static int shortestWayUsingTwoPointers1(String source, String target) {
        int m = source.length(), n = target.length(), i = 0, j = 0, count = 0;
        boolean[] sourceChars = new boolean[26];
        for (char c : source.toCharArray()) sourceChars[c-'a'] = true;

        boolean isWordStarted = false;
        while (j < n) {
            char jc = target.charAt(j);
            if (!sourceChars[jc-'a']) return -1;
            while(i < m && jc != source.charAt(i)) i++;

            if(i < m ) { // char found
                i++;
                j++;

                if (!isWordStarted) { // new word started
                    count++;
                    isWordStarted = true;
                }
            } else if (i == m) { // prepare for new word
                i = 0;
                isWordStarted = false;
            }
        }

        return count;
    }



    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(1)
    */
    public static int shortestWayUsingTwoPointers2(String source, String target) {
        int m = source.length(), n = target.length(), i = 0, j = 0, count = 0;

        boolean isWordStarted = false;
        while (j < n) {
            while(i < m && target.charAt(j) != source.charAt(i)) i++;

            if(i < m ) { // char found in source substring - increment both pointers
                i++;
                j++;

                if (!isWordStarted) { // new word started - new subsequence
                    count++;
                    isWordStarted = true;
                }
            } else if (i == m) { // char not found in source substring - prepare for new word with same j-pointer
                if (!isWordStarted) return -1; // char not found in whole source string
                i = 0;
                isWordStarted = false;
            }
        }

        return count;
    }



    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(1)
    */
    public static int shortestWayUsingTwoPointers3(String source, String target) {
		Set<Character> set = new HashSet<>();
		for (char ch : source.toCharArray()) {
			set.add(ch);
		}
		for (char ch : target.toCharArray()) {
			if(!set.contains(ch)) return -1; //once contains any char not in source, then it is impossible otherwise must be okay
		}

		int i = 0;
        int j = 0;
		int count = 0;
		while(j < target.length()) {
			if (i == 0) count++;
            if (source.charAt(i) == target.charAt(j)) {
                j++;
            }

            i++;
            if (i == source.length()) {
                i = 0;
            }
		}

        return count;
    }





    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(1)
    */
    public static int shortestWayUsingTwoPointers4(String source, String target) {
        boolean[] sourceChars = new boolean[26];
        for (char c : source.toCharArray()) {
            sourceChars[c - 'a'] = true;
        }
        for (char c : target.toCharArray()) {
            if (!sourceChars[c - 'a']) {
                return -1;
            }
        }

        int m = source.length();
        int sourceIterator = 0;
        int count = 0;

        for (char c : target.toCharArray()) {
            if (sourceIterator == 0) {
                count++;
            }
            while (source.charAt(sourceIterator) != c) {
                sourceIterator = (sourceIterator + 1) % m;
                if (sourceIterator == 0) {
                    count++;
                }
            }
            sourceIterator = (sourceIterator + 1) % m;
        }

        return count;
    }






    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(1)
    */
    public static int shortestWayUsingTwoPointers5(String source, String target) {
        int pointer2Start = 0;
        int m = source.length();

        for (int p1 = 0; p1 < target.length(); p1++) {
            int p2 = pointer2Start;

            while (target.charAt(p1) != source.charAt(p2 % m)) {
                if (p2 == pointer2Start + m) {
                    return -1;
                }
                p2++;
            }

            pointer2Start = p2 + 1;
        }

        return (pointer2Start - 1) / m + 1;
    }





    /**
     * @TimeComplexity O(S+Tlog(S))
     * @SpaceComplexity O(S)
     */
    public static int shortestWayUsingTwoPointersWithInvertedIndexAndBinarySearch(String source, String target) {
        ArrayList<Integer>[] charToIndices = new ArrayList[26];
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            if (charToIndices[c - 'a'] == null) {
                charToIndices[c - 'a'] = new ArrayList<>();
            }
            charToIndices[c - 'a'].add(i);
        }

        int sourceIterator = 0;
        int count = 1;

        for (char c : target.toCharArray()) {

            if (charToIndices[c - 'a'] == null) {
                return -1;
            }

            ArrayList<Integer> indices = charToIndices[c - 'a'];
            int index = Collections.binarySearch(indices, sourceIterator);

            if (index < 0) {
                index = -index - 1;
            }

            if (index == indices.size()) {
                count++;
                sourceIterator = indices.get(0) + 1;
            } else {
                sourceIterator = indices.get(index) + 1;
            }
        }

        return count;
    }














    /**
     * @TimeComplexity O(T^2 * S)
     * @SpaceComplexity O(TS)
     */
    public static int shortestWayUsingTwoPointersWithConcatenateUntilSubsequence(String source, String target) {

        boolean[] sourceChars = new boolean[26]; // Boolean array to mark all characters of source
        for (char c : source.toCharArray()) {
            sourceChars[c - 'a'] = true;
        }

        // Check if all characters of the target are present in the source. If any character is not present, return -1
        for (char c : target.toCharArray()) {
            if (!sourceChars[c - 'a']) {
                return -1;
            }
        }

        // Concatenate source until the target is a subsequence of the concatenated string
        String concatenatedSource = source;
        int count = 1;
        while (!isSubsequence(target, concatenatedSource)) {
            concatenatedSource += source;
            count++;
        }

        return count;
    }
    public static boolean isSubsequence(String toCheck, String inString) {
        int i = 0, j = 0;
        while (i < toCheck.length() && j < inString.length()) {
            if (toCheck.charAt(i) == inString.charAt(j)) {
                i++;
            }
            j++;
        }

        return i == toCheck.length();
    }







    private static final int INF = 1_000_000;



    public static int shortestWayUsingBacktracking1_TLE(String source, String target) {
        if (target.isEmpty()) {
            return 0;
        }

        for (char targetChar : target.toCharArray()) {
            if (source.indexOf(targetChar) == -1) {
                return -1;
            }
        }

        int additionalPasses = backtrack(source, target, 0, 0, false);
        return additionalPasses >= INF ? -1 : 1 + additionalPasses;
    }

    private static int backtrack(String source, String target,
                        int targetIdx, int sourceIdx,
                        boolean usedCurrentPass) {
        if (targetIdx == target.length()) {
            return 0;
        }

        if (sourceIdx == source.length()) {
            // We skipped a whole source pass without consuming target characters.
            // Do not restart: that would repeat the same state forever.
            if (!usedCurrentPass) {
                return INF;
            }

            return 1 + backtrack(source, target, targetIdx, 0, false);
        }

        int skip = backtrack(
            source, target, targetIdx, sourceIdx + 1, usedCurrentPass
        );

        int use = INF;
        if (source.charAt(sourceIdx) == target.charAt(targetIdx)) {
            use = backtrack(
                source, target, targetIdx + 1, sourceIdx + 1, true
            );
        }

        return Math.min(skip, use);
    }








    public static int shortestWayUsingTopDownMemoDp1_STACK_OVERFLOW(String source, String target) {
        int m = source.length();
        int n = target.length();

        Integer[][][] memo = new Integer[n][m][2];
        int extraPasses = dfs(source, target, 0, 0, false, memo);

        return extraPasses >= INF ? -1 : 1 + extraPasses;
    }

    private static int dfs(String source, String target,
                    int targetIdx, int sourceIdx,
                    boolean usedCurrentPass,
                    Integer[][][] memo) {

        if (targetIdx == target.length()) {
            return 0;
        }

        if (sourceIdx == source.length()) {
            if (!usedCurrentPass) {
                return INF;
            }
            return 1 + dfs(source, target, targetIdx, 0, false, memo);
        }

        int used = usedCurrentPass ? 1 : 0;

        if (memo[targetIdx][sourceIdx][used] != null) {
            return memo[targetIdx][sourceIdx][used];
        }

        int skip = dfs(source, target, targetIdx,
                    sourceIdx + 1, usedCurrentPass, memo);

        int take = INF;
        if (source.charAt(sourceIdx) == target.charAt(targetIdx)) {
            take = dfs(source, target, targetIdx + 1,
                    sourceIdx + 1, true, memo);
        }

        return memo[targetIdx][sourceIdx][used] = Math.min(skip, take);
    }









    /**
     * @TimeComplexity O(S+T)
     * @SpaceComplexity O(S)
     */
    public static int shortestWayUsingBottomUpTabulationDp1(String source, String target) {

        int[][] nextOccurrence = new int[source.length()][26];

        for (int c = 0; c < 26; c++) {
            nextOccurrence[source.length() - 1][c] = -1;
        }
        nextOccurrence[source.length() - 1][source.charAt(source.length() - 1) - 'a'] = source.length() - 1;

        // Fill using recurrence relation
        for (int idx = source.length() - 2; idx >= 0; idx--) {
            for (int c = 0; c < 26; c++) {
                nextOccurrence[idx][c] = nextOccurrence[idx + 1][c];
            }
            nextOccurrence[idx][source.charAt(idx) - 'a'] = idx;
        }


        int sourceIterator = 0; // Pointer to the current index in source
        int count = 1; // Number of times we need to iterate through source

        // Find all characters of target in source
        for (char c : target.toCharArray()) {

            // If the character is not present in source
            if (nextOccurrence[0][c - 'a'] == -1) {
                return -1;
            }

            // If we have reached the end of source, or the character is not in source after source_iterator, loop back to beginning
            if (sourceIterator == source.length() || nextOccurrence[sourceIterator][c - 'a'] == -1) {
                count++;
                sourceIterator = 0;
            }

            // Next occurrence of character in source after source_iterator
            sourceIterator = nextOccurrence[sourceIterator][c - 'a'] + 1;
        }

        // Return the number of times we need to iterate through source
        return count;
    }

































    /**
     * @TimeComplexity O(T^2 * S)
     * @SpaceComplexity O(T)
     */
    public static int shortestWayUsingTopDownMemoDpIsSubsequence_TLE(String source, String target) {

        // Boolean array to mark all characters of source
        boolean[] sourceChars = new boolean[26];
        for (char c : source.toCharArray()) {
            sourceChars[c - 'a'] = true;
        }

        // Check if all characters of target are present in source
        // If any character is not present, return -1
        for (char c : target.toCharArray()) {
            if (!sourceChars[c - 'a']) {
                return -1;
            }
        }

        // Optimal Answer for a given ending index. Memoizing using an Array
        int[] memo = new int[target.length()];
        Arrays.fill(memo, Integer.MAX_VALUE / 2);

        // Want to find optimal answer for the last index.
        // Case when task is not possible is already handled
        return optimalAnswer(target.length() - 1, memo, source, target);
    }

    public static int optimalAnswer(int endingIndex, int[] memo, String source, String target) {

        // Base Case
        if (endingIndex == 0) {
            return 1;
        }

        // If already calculated, return
        if (memo[endingIndex] != Integer.MAX_VALUE / 2) {
            return memo[endingIndex];
        }

        // If subsequence, return 1
        if (isSubsequence(0, endingIndex, source, target)) {
            memo[endingIndex] = 1;
            return 1;
        }

        // If not subsequence, partition into two parts and find minimum
        int answer = Integer.MAX_VALUE / 2;

        for (int partitionIndex = 0; partitionIndex < endingIndex; partitionIndex++) {

            // Check for subsequence only if the answer is less
            // than the current answer. Using AND Short Circuiting
            if (optimalAnswer(partitionIndex, memo, source, target) + 1 < answer
                    && isSubsequence(partitionIndex + 1, endingIndex, source, target)) {
                answer = Math.min(answer, optimalAnswer(partitionIndex, memo, source, target) + 1);
            }
        }

        // Memoize and return
        memo[endingIndex] = answer;
        return answer;
    }

    // For to_check, passing indices of target, both included.
    public static boolean isSubsequence(int start, int end, String toCheck, String inString) {
        int i = start;
        int j = 0;

        while (i <= end && j < inString.length()) {
            if (toCheck.charAt(i) == inString.charAt(j)) {
                i++;
            }
            j++;
        }

        return i == end + 1;
    }








    /**
     * @TimeComplexity O(T^2 * S)
     * @SpaceComplexity O(T)
     */
    public static int shortestWayUsingBottomUpTabulationDpIsSubsequence_TLE(String source, String target) {

        // Boolean array to mark all characters of source
        boolean[] sourceChars = new boolean[26];
        for (char c : source.toCharArray()) {
            sourceChars[c - 'a'] = true;
        }

        // Check if all characters of target are present in source
        // If any character is not present, return -1
        for (char c : target.toCharArray()) {
            if (!sourceChars[c - 'a']) {
                return -1;
            }
        }

        // Optimal Answer for a given ending index. Memoizing using an Array
        int[] memo = new int[target.length()];
        Arrays.fill(memo, Integer.MAX_VALUE);
        memo[0] = 1;

        for (int endingIndex = 1; endingIndex < target.length(); endingIndex++) {
            if (isSubsequence2(0, endingIndex, source, target)) {
                memo[endingIndex] = 1;
            } else {
                for (int partitionIndex = endingIndex - 1; partitionIndex >= 0; partitionIndex--) {
                    if (memo[partitionIndex] != Integer.MAX_VALUE &&
                            isSubsequence2(partitionIndex + 1, endingIndex, source, target)) {
                        memo[endingIndex] = Math.min(memo[endingIndex], memo[partitionIndex] + 1);
                    }
                }
            }
        }

        return memo[target.length() - 1];
    }

    // For to_check, passing indices of target, both included.
    public static boolean isSubsequence2(int start, int end, String source, String target) {
        int i = start;
        int j = 0;

        while (i <= end && j < source.length()) {
            if (target.charAt(i) == source.charAt(j)) {
                i++;
            }
            j++;
        }

        return i == end + 1;
    }

}
