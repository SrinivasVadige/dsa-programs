package Algorithms.TwoPointers;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 01 Oct 2024
 * @link 15. 3Sum <a href="https://leetcode.com/problems/3sum/">LeetCode link</a>
 * @topics Array, Two Pointers, Sorting, Hash Table
 * @companies Google, Amazon, Microsoft, Meta, Bloomberg, Visa, TikTok, IBM, Apple, Oracle, Walmart Labs, Cloudflare, Agoda, Meesho, Adobe, Yahoo, Uber, tcs, Salesforce, Zoho, Accenture, Intuit, Samsung, Infosys
 */
public class ThreeSum {

    public static void main(String[] args) {
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
        System.out.println("threeSum using BruteForce - N^3 => " + threeSumBruteForceTLE(nums));
        System.out.println("threeSum using TwoSum TwoPass HashMap - N^2 => " + threeSumUsingTwoSumTwoPassHashMap(nums));
        System.out.println("threeSum using TwoSum OnePass HashSet - N^2 => " + threeSumUsingTwoSumOnePassHashSet(nums));
        System.out.println("threeSum using Sort & TwoPointers - N^2 optimized 🔥 => " + threeSumUsingSortAndTwoPointers(nums));
    }



    /**
     * @TimeComplexity O(n^3)
     * @SpaceComplexity O(1)
     */
    public static List<List<Integer>> threeSumBruteForceTLE(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        list.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    }
                }
            }
        }
        return list;
    }










    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n)
     * TwoSum TwoPass HashTable like {@link Algorithms.Hashing.TwoSum#twoSumTwoPassHashMap}
     * or
     * Hash with Triplet [nums[i], nums[j], nums[k]] Sorting for Duplicate Elimination
     * <p>
     * even though both {@link #threeSumUsingSortAndTwoPointers} and {@link #threeSumUsingTwoSumTwoPassHashMap} are O(n^2)
     * {@link #threeSumUsingSortAndTwoPointers} is much more optimized as it avoids extra overhead from hash maps
     * and skips the duplicate checks as the nums is already sorted and the memory is O(1)
     */
    public static List<List<Integer>> threeSumUsingTwoSumTwoPassHashMap(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = nums.length;
        for(int i=0; i<n; i++) {
            map.put(nums[i], i); // maintain the last occurrence of the "num"
        }

        Set<List<Integer>> res = new HashSet<>();
        Set<Integer> visitedINums = new HashSet<>();
        for(int i=0; i<n; i++) {
            if(!visitedINums.add(nums[i])) { // skip duplicate "i"s
                continue;
            }
            // TwoSum Two-Pass Hash Table Approach
            for(int j=i+1; j<n; j++) {
                int need = -nums[i]-nums[j];
                if(map.getOrDefault(need, -1) > j) { // or map.containsKey(need) && map.get(need)!=i && map.get(need)!=j
                    List<Integer> list = new ArrayList<>(Arrays.asList(nums[i], nums[j], need));
                    Collections.sort(list);
                    res.add(list);
                }
            }
        }
        return new ArrayList<>(res);
    }





    public static List<List<Integer>> threeSumUsingTwoSumTwoPassHashMap2(int[] nums) { // threeSumUsingTwoSumApproachNew
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        Set<List<Integer>> set = new HashSet<>();
        Arrays.sort(nums);

        for(int i=0; i<n; i++) {
            map.put(nums[i], i);
        }

        for(int i=0; i<n; i++) {
            if(i!=0 && nums[i-1]==nums[i]) { // or  if(!visitedINums.add(nums[i])) continue;
                continue;
            }

            for(int j=i+1; j<n; j++) {
                int sum = nums[i] + nums[j];
                int need = -sum;
                int needI = map.getOrDefault(need, -1);
                if(needI>-1 && needI>j) {
                    set.add(Arrays.asList(nums[i], nums[j], need));
                }

                while(j+1<n && nums[j]==nums[j+1]) j++;  // skip duplicate "j"s
            }
        }

        return new ArrayList<>(set);
    }







    /**
     * @TimeComplexity O(N^2)
     * @SpaceComplexity O(N)
     * same as above {@link #threeSumUsingTwoSumTwoPassHashMap}
     */
    public static List<List<Integer>> threeSumUsingTwoSumOnePassHashSet(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length && nums[i] <= 0; i++) {
            if (i==0 || nums[i - 1] != nums[i]) { // or if(!visitedINums.add(nums[i])) continue; -- skip duplicate "i"s
                twoSumOnePassHashSet(nums, i, res);
            }
        }
        return res;
    }
    private static void twoSumOnePassHashSet(int[] nums, int i, List<List<Integer>> res) {
        var seen = new HashSet<Integer>();
        for (int j = i+1; j < nums.length; j++) {
            int complement = -nums[i]-nums[j];
            if (seen.contains(complement)) {
                res.add(Arrays.asList(nums[i], nums[j], complement));
                while (j+1<nums.length && nums[j]==nums[j + 1]) j++; // skip duplicate "j"s --- as nums is sorted, no need for Collections.sort(list);
            }
            seen.add(nums[j]);
        }
    }








    /**
     * @TimeComplexity O(N^2 + NlogN) = O(N^2)
     * @SpaceComplexity O(1)
     * after the sort, [-1,0,1,2,-1,-4] will be converted to [-4,-1,-1,0,1,2]
     * this {@link #twoSumUsingTwoPointers} is same as {@link Algorithms.TwoPointers.TwoSum2InputArrayIsSorted#twoSum}
     * and {@link Algorithms.Hashing.TwoSum#twoSumUsingSortedIndexesAndTwoPointers}
     */
    public static List<List<Integer>> threeSumUsingSortAndTwoPointers(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;
        for(int i=0; i<n-2; i++) {
            if(i != 0 && nums[i-1] == nums[i]) { // to avoid calculating the duplicate "i"s
                continue;
            }
            twoSumUsingTwoPointers(nums, i, res);
        }
        return res;
    }
    private static void twoSumUsingTwoPointers(int[] nums, int i, List<List<Integer>> res) {
        int l = i+1;
        int r = nums.length-1;
        while (l < r) {
            int sum = nums[i] + nums[l] + nums[r]; // as we use "sum" instead of "mid" --> this is just TwoPointers not BinarySearch approach
            if(sum<0) {
                l++;
            } else if (sum>0) {
                r--;
            } else {
                res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                do l++;
                while (l < r && nums[l - 1] == nums[l]);
                /*
                // or
                r--;
                while(l<r && nums[r]==nums[r+1]) r--;
                 */
            }
        }
    }
}
