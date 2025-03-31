package Algorithms.DisjointSetUnion;

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
 */
public class LongestConsecutiveSequence {

    public static void main(String[] args) {
        int[] nums = {100,4,200,1,3,2};
        System.out.println("longestConsecutiveUsingSort: " + longestConsecutiveUsingSort(nums));
        System.out.println( "longestConsecutiveUsingHashSet MyApproach: " + longestConsecutiveUsingHashSetMyApproach(nums));
        System.out.println( "longestConsecutiveUsingHashSet: " + longestConsecutiveUsingHashSet(nums));
        System.out.println("longestConsecutiveUsingUnionFind: " + longestConsecutiveUsingUnionFind(nums));
    }

    // Time Complexity = O(n log n) cause we are sorting the array
    public static int longestConsecutiveUsingSort(int[] nums) {
        if(nums.length == 0) return 0;

        Arrays.sort(nums);

        int tempMax = 1; // at least one item in nums array
        int max = 1;
        int prev = nums[0];
        for (int i = 1; i < nums.length; i++){
			if (prev == nums[i]) continue; // duplicates
            else if(prev+1 == nums[i]){ // sequence
				tempMax++;
                max = Math.max(max, tempMax);
            }
            else tempMax = 1; // sequence braked

            // update prev pointer
			prev = nums[i];
        }
        return max;
    }












    /**
        PATTERNS:
        ---------
        1) Instead of sort, we need to solve this in O(n) time complexity
        2) HashSet and now for(nums) loop to check lWidth and rWidth of i.
        3) Duplicates are not part of sequence
     */
    public static int longestConsecutiveUsingHashSetMyApproach(int[] nums) {
        int n = nums.length;
        Set<Integer> set = new HashSet<>();
        Set<Integer> visited = new HashSet<>(); // for performance & to avoid TLE
        for(int x: nums) set.add(x);
        int max = 0;

        for (int i=0; i<n; i++) { // trav set instead of array to make more faster
            int x = nums[i];
            if(visited.contains(x)) continue;
            int lc = 0;
            int rc = 0;
            visited.add(x);

            // left
            while(set.contains(x-1)) {
                x=x-1;
                lc++;
                visited.add(x);
            }

            // right
            x = nums[i];
            while(set.contains(x+1)) {
                x=x+1;
                rc++;
                visited.add(x);
            }

            max = Math.max(max, lc+rc+1);
        }
        return max;
    }





    /**
     * @TimeComplexity: O(n)
     * @SpaceComplexity: O(n)
     *
     * The reason why it's O(n) is
     * 1. Insert all nums in set (we skip the duplicates as well)
     * 2. Iterate through set
     * 3. In best case when no sequence --> then O(n)
     * 4. In worst case when few of then are consecutive --> then O(n) + O(n) + O(n)... few
     * 5. Which is less than O(n^2)
     */
    public static int longestConsecutiveUsingHashSet(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) numSet.add(num);

        int longestStreak = 0;

        // Iterate through the set instead of arr and find the "start" of each sequence
        for (int num : numSet) {
            // Only start counting if "num" is the start of a sequence, cause we'll trav it later
            if (!numSet.contains(num - 1)) { // or if(numSet.contains(num-1)) continue;
                int currentNum = num;
                int currentStreak = 1;
                while (numSet.contains(currentNum + 1)) { // sequence
                    currentNum++;
                    currentStreak++;
                }
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
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
            }        }

        public int find(int i) {
            if (parent[i] == i) return i;
            return parent[i] = find(parent[i]);
        }
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
                while (set.contains(num + length))
                    length++;
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



