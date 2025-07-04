package Algorithms.Hashing;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
        System.out.println(Arrays.toString(twoSumBruteForce(arr, target)));
        System.out.println(Arrays.toString(twoSumOnePassHashMap(arr, target)));
        System.out.println(Arrays.toString(twoSumTwoPassHashMap(arr, target)));
    }

    /**
     * @TimeComplexity O(n^2)
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
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
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
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
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
            if (numMap.containsKey(complement) && numMap.get(complement) != i) {
                return new int[]{i, numMap.get(complement)};
            }
        }

        return new int[]{}; // No solution found
    }





    public static int[] twoSumOnePassHashMap2(int[] nums, int target) {
        HashMap<Integer, Integer> hm = new HashMap<>();
        int [] ans = new int[2];
        int n = nums.length;
        for(int i=0; i<n; i++) {
            int partner = target - nums[i]; // or complement
            if(hm.containsKey(partner)) {
                ans[0] = hm.get(partner); // new int[]{numMap.get(complement), i};
                ans[1] = i;
                break;
            }
            hm.put(nums[i], i);
        }
        return ans;
    }
}
