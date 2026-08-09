package Algorithms.Hashing;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
 You must write an algorithm that runs in O(n) time.
 *
 *
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 24 Sept 2024
 * @link 128. Longest Consecutive Sequence <a href="https://leetcode.com/problems/longest-consecutive-sequence/">Leetcode link</a>
 * @topics Array, Hash Table, Sorting, Union Find
 */
public class LongestConsecutiveSequence {

    public static void main(String[] args) {
        int[] nums = {100,4,200,1,3,2};
        System.out.println( "longestConsecutiveUsingHashSet: " + longestConsecutiveUsingHashSet(nums));
        System.out.println("longestConsecutiveUsingSort: " + longestConsecutiveUsingSort(nums));
        System.out.println("longestConsecutiveUsingUnionFind: " + longestConsecutiveUsingUnionFind(nums));
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     *
     * The reason why it's O(n) is
     * 1. Insert all nums in set (we skip the duplicates as well)
     * 2. Iterate through set
     * 3. In best case when no sequence --> then O(n)
     * 4. In worst case when few of then are consecutive --> then O(n) + O(n) + O(n)... few
     * 5. Which is less than O(n^2)
     */
    public static int longestConsecutiveUsingHashSet(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for(int num: nums) {
            seen.add(num);
        }


        int max = 0;
        for (int num: seen) {

            if (seen.contains(num-1)) continue; // i.e not head

            int len = 0;
            while(seen.contains(num)) {
                len++;
                num++;
            }

            max = Math.max(max, len);
        }

        return max;
    }







    public static int longestConsecutiveUsingHashSet2(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int num: nums) {
            set.add(num);
        }


        int max = 0;
        for (int num: set) {
            if (!set.contains(num-1)) { // i.e not head
                int len = 0;
                while(set.contains(num+len)) len++;
                max = Math.max(max, len);
            }
        }

        return max;
    }




    public static int longestConsecutiveUsingHashSet3(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) numSet.add(num);

        int longestStreak = 0;

        // Iterate through the set instead of arr and find the "start" of each sequence
        for (int num : numSet) {
            // Only start counting if "num" is the start of a sequence, cause we'll trav it later
            if (!numSet.contains(num - 1)) { // or if(numSet.contains(num-1)) continue;
                int currentStreak = 1;
                while (numSet.contains(num + 1)) { // sequence
                    num++;
                    currentStreak++;
                }
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }







    public static int longestConsecutiveUsingDoubleLinkedListAndHashMap(int[] nums) {
        class Node {
            final int val;
            Node left;
            Node right;
            Node(int val) {this.val = val;}
        }

        Map<Integer, Node> map = new HashMap<>();

        // Prepare DoubleLinkedLists
        for(int num: nums) {

            if (map.containsKey(num)) {
                continue;
            }

            Node curr = new Node(num);
            map.put(num, curr);

            Node left = map.get(num-1);
            Node right = map.get(num+1);

            if (left != null) {
                curr.left = left;
                left.right = curr;
            }

            if (right != null) {
                curr.right = right;
                right.left = curr;
            }
        }

        // trav only the head nodes
        int maxLen = 0;
        for (Node node : map.values()) {
            if (node.left != null)  continue; // i.e not head
            int len = 0;
            for (Node cur = node; cur != null; cur = cur.right) len++;
            maxLen = Math.max(maxLen, len);
        }


        return maxLen;
    }







    public static int longestConsecutiveUsingSort(int[] nums) {
        int[] sortedNonDupNums = Arrays.stream(nums).distinct().sorted().toArray(); // or re-assign it to given "nums" param
        int maxLen = 0;
        int tempLen = 0;
        if (nums.length > 0) {
            tempLen = 1;
            maxLen = 1;
        }

        for(int i=1; i<sortedNonDupNums.length; i++) {
            if(sortedNonDupNums[i-1]+1 == sortedNonDupNums[i]) {
                tempLen++;
                maxLen = Math.max(maxLen, tempLen);
            } else {
                tempLen = 1;
            }
        }

        return maxLen;
    }








    public static int longestConsecutiveUsingSort2(int[] nums) {
        Arrays.sort(nums);

        int maxLen = 0;
        int tempLen = 0;
        if (nums.length > 0) {
            tempLen = 1;
            maxLen = 1;
        }

        for(int i=1; i<nums.length; i++) {
            if(nums[i-1]+1 == nums[i]) {
                tempLen++;
                maxLen = Math.max(maxLen, tempLen);
            } else if(nums[i-1] == nums[i]) { // skip dups
                continue;
            } else {
                tempLen = 1;
            }
        }

        return maxLen;
    }





    public static int longestConsecutiveUsingSort3(int[] nums) {
        if(nums.length == 0) return 0;

        Arrays.sort(nums);

        int tempMax = 1; // at least one item in nums array
        int max = 1;
        int prev = nums[0];
        for (int i = 1; i < nums.length; i++){
			if (prev == nums[i]) continue; // dups
            else if(prev+1 == nums[i]){ // sequence
				tempMax++;
                max = Math.max(max, tempMax);
            }
            else tempMax = 1; // sequence broken

            // update prev pointer
			prev = nums[i];
        }
        return max;
    }







    /**
     * It's same as {@link #longestConsecutiveUsingHashSet(int[])}
     */
    public static int longestConsecutiveUsingUnionFind(int[] nums) {
            if (nums == null || nums.length == 0) return 0;

            Set<Integer> numSet = new HashSet<>();
            for (int num : nums) numSet.add(num);

            UF uf = new UF(nums);

            for (int num : numSet) {
                if (numSet.contains(num+1)) {
                    uf.union(num, num+1);
                }
            }

            Map<Integer, Integer> rootCount = new HashMap<>();
            for (int num: numSet) rootCount.merge(uf.find(num), 1, Integer::sum);

            return Collections.max(rootCount.values());
    }
    static class UF {
        Map<Integer, Integer> map = new HashMap<>(); // num -> parent

        public UF(int[] nums) {
            for (int num : nums) map.put(num, num);
        }

        public int find(int num) {
            while (map.get(num) != num) num = map.get(num);
            return num;
        }

        public void union(int num1, int num2) {
            int parent1 = find(num1);
            int parent2 = find(num2);
            if (parent1 != parent2) map.put(parent2, parent1);
        }
    }





    public static int longestConsecutiveUsingUnionFind2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) numSet.add(num);

        UnionFind uf = new UnionFind(numSet.size());
        Map<Integer, Integer> numToIndex = new HashMap<>();

        int index = 0;
        for (int num : numSet) numToIndex.put(num, index++);

        for (int num : numSet) {
            if (numSet.contains(num - 1)) {
                uf.union(numToIndex.get(num), numToIndex.get(num - 1));
            }
        }

        int max = 0;
        for (int num : numSet) {
            max = Math.max(max, uf.rank[uf.find(numToIndex.get(num))]);
        }

        return max;
    }
    static class UnionFind {
        int[] parent;
        int[] rank;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }

        public void union(int i, int j) {
            int pi = find(i);
            int pj = find(j);
            if (pi == pj) return;
            if (rank[pi] < rank[pj]) {
                parent[pi] = pj;
                rank[pj] += rank[pi];
            } else {
                parent[pj] = pi;
                rank[pi] += rank[pj];
            }
        }

        public int find(int i) {
            if (parent[i] == i) return i;
            return parent[i] = find(parent[i]);
        }
    }











    /**
        PATTERNS:
        ---------
        1) Instead of sort, we need to solve this in O(n) time complexity
        2) HashSet and now for(nums) loop to check lWidth and rWidth of i.
        3) Dups are not part of sequence

     */
    public static int longestConsecutiveHashSetMyApproachOld(int[] nums) {
        int n = nums.length;
        Set<Integer> set = new HashSet<>();
        Set<Integer> visited = new HashSet<>();
        for(int x: nums) set.add(x);
        int max = 0;

        for (int num : nums) {
            int x = num;
            if (visited.contains(x)) continue;
            int lc = 0;
            int rc = 0;
            visited.add(x);

            // left
            while (set.contains(x - 1)) {
                x = x - 1;
                lc++;
                visited.add(x);
            }

            // right
            x = num;
            while (set.contains(x + 1)) {
                x = x + 1;
                rc++;
                visited.add(x);
            }

            max = Math.max(max, lc + rc + 1);
        }
        return max;
    }







    public int longestConsecutiveNotWorkingMemoryLimitExceeded(int[] nums) {
        boolean[] negatives = new boolean[(int)1e9+1];
        boolean[] positives = new boolean[(int)1e9+1];

        for(int num: nums) {
            if(num < 0) {
                negatives[Math.abs(num)] = true;
            } else {
                positives[num] = true;
            }
        }

        int maxLen = 0;
        int tempLen = 0;

        for(int i=1; i<=1e9; i++) {
            if(negatives[i]) {
                tempLen++;
                maxLen = Math.max(maxLen, tempLen);
            } else {
                tempLen = 0;
            }
        }

        tempLen = 0;
        // last -ve count i.e -1 to till valid -ve consecutive number
        for(int i=1; i<=1e9; i++) {
            if(negatives[i]) {
                tempLen++;
                maxLen = Math.max(maxLen, tempLen);
            } else {
                break;
            }
        }

        for(int i=0; i<=1e9; i++) {
            if(positives[i]) {
                tempLen++;
                maxLen = Math.max(maxLen, tempLen);
            } else {
                tempLen = 0;
            }
        }


        return maxLen;
    }



















    public static int longestConsecutiveUsingHashSetTLE(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int longest = 0;

        for (int num : nums) {
            if (!set.contains(num - 1)) {
                int length = 1;
                while (set.contains(num + length)) length++;
                longest = Math.max(longest, length);
            }
        }

        return longest;
    }


    /**
     * TLE
     */
    public static int longestConsecutiveTLE(int[] nums) {
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        int longest = 0;

        for (int num : nums) {
            if (!numSet.contains(num - 1)) {
                int length = 1;
                while (numSet.contains(num + length))
                    length++;
                longest = Math.max(longest, length);
            }
        }

        return longest;
    }
}



