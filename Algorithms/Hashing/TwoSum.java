package Algorithms.Hashing;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 21 Sept 2024
 * @link 1. Two Sum <a href="https://leetcode.com/problems/two-sum/">LeetCode link</a>
 * @topics Array, Hash Table
 * @companies google, amazon, facebook, microsoft, bloomberg, tcs, visa, snowflake, spotify, apple, oracle, epam, goldman, hubspot, infosys, walmart, capgemini, deloitte, tiktok, zoho, adobe, yahoo, uber, yandex, accenture, ibm, sap, doordash, nvidia, paypal
 */
public class TwoSum {

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5};
        int target = 5;
        System.out.println("TwoSum using Brute Force => " + Arrays.toString(twoSumBruteForce(arr, target)));
        System.out.println("TwoSum using One-Pass Hash Table => " + Arrays.toString(twoSumOnePassHashMap(arr, target)));
        System.out.println("TwoSum using Two-Pass Hash Table => " + Arrays.toString(twoSumTwoPassHashMap(arr, target)));
        System.out.println("TwoSum using SortedIndexes and TwoPointers => " + Arrays.toString(twoSumUsingSortedIndexesAndTwoPointers(arr, target)));
    }

    /**
     * @TimeComplexity O(N^2)
     * @SpaceComplexity O(1)
    */
    public static int[] twoSumBruteForce(int[] nums, int target) {
        int[] res = new int[2];
        for(int i=0; i<nums.length; i++){
            for(int j=i+1; j<nums.length; j++){
                if(nums[i]+nums[j] == target){
                    return res = new int[]{i,j}; // or assign res & break; or just return new int[]{i,j} & return new int[] at method end line;
                }
            }
        }
       return res;
    }

    /**
     * @TimeComplexity O(N)
     * @SpaceComplexity O(N)
     * One-pass Hash Table means only one for-loop
    */
    public static int[] twoSumOnePassHashMap(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0; i<nums.length; i++) {
            int need = target - nums[i]; // or complement or partner
            if(map.containsKey(need)) {
                return new int[]{map.get(need), i};
            }
            map.put(nums[i], i);
        }
        return new int[2];
    }



    /**
     * @TimeComplexity O(N)
     * @SpaceComplexity O(N)
     * Two-Pass Hash Table means two for-loops
    */
    public static int[] twoSumTwoPassHashMap(int[] nums, int target) {
        Map<Integer, Integer> numMap = new HashMap<>();
        int n = nums.length;

        // Build the hash table
        for (int i = 0; i < n; i++) {
            numMap.put(nums[i], i);
        }

        // Find the complement
        for (int i = 0; i < n; i++) {
            int complement = target - nums[i];
            if (numMap.containsKey(complement) && numMap.get(complement) != i) {  // or map.getOrDefault(need) > i
                return new int[]{i, numMap.get(complement)};
            }
        }

        return new int[0]; // No solution found
    }





    /**
     * @TimeComplexity O(NlogN)
     * @SpaceComplexity O(N)
    */
    public static int[] twoSumUsingSortedIndexesAndTwoPointers(int[] nums, int target) {
        int n = nums.length, l = 0, r = n-1;
        int[] sortedIndexes = IntStream.range(0,n).boxed().sorted(Comparator.comparingInt(i -> nums[i])).mapToInt(i->i).toArray();
        while(l<r) {
            int sum = nums[sortedIndexes[l]] + nums[sortedIndexes[r]]; // as we use "sum" instead of "mid" --> this is just TwoPointer not BinarySearch
            if(sum < target) {
                l++;
            } else if(sum > target) {
                r--;
            } else {
                return new int[]{sortedIndexes[l], sortedIndexes[r]};
            }
        }
        return new int[0]; // or return new int[]{-1,-1}; or null; -- but avoid returning null as it causes "ERROR: reference to toString is ambiguous"
    }
}
