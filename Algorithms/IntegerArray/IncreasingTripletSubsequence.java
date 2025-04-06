package Algorithms.IntegerArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 06 April 2025
 */
public class IncreasingTripletSubsequence {
    public static void main(String[] args) {
        int[] nums = {1,5,2,0,3,6};
        System.out.println("increasingTripletBruteForce -> " + increasingTripletBruteForce(nums)); // Output: true
        System.out.println("increasingTriplet -> " + increasingTriplet(nums)); // Output: true
        System.out.println("increasingTriplet2 -> " + increasingTriplet2(nums)); // Output: true
        System.out.println("increasingTriplet3 -> " + increasingTriplet3(nums)); // Output: true
        System.out.println("increasingTripletUsingDFS -> " + increasingTripletUsingDFS(nums)); // Output: true
    }
    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     *
     * PATTERNS:
     * ---------
     * 1) [1,5,2,0,3,6] --> here [1,5,6], [1,2,3] and [0,3,6] are true as i<j<k and nums[i]<nums[j]<nums[k]
     * 2) [20,100,10,12,5,13] --> here [10,12,13] is true
     * 3) Move until you find smallest number
     * 4) [5,6,1,2,7]
     *
     * [5,6,1,2,7]
     *  i j
     * [5,6,1,2,7]
     *    j i
     * [5,6,1,2,7]
     *      i j
     * [5,6,1,2,7]
     *      i j |
     *          |
     *      else case --> i.e true
     *
     * 5) [1,2,7,3,4]
     *
     * [1,2,7,3,4]
     *  i j
     * [1,2,7,3,4]
     *      |
     *      else case --> i.e true
     *
     */
    public static boolean increasingTriplet(int[] nums) {
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num <= first) {
                first = num;
            } else if (num <= second) {
                second = num;
            } else { // num > first && num > second -- num greater than both first and second
                return true;
            }
        }
        return false;
    }
    /** same as above {@link #increasingTriplet(int[])} */
    public static boolean increasingTriplet2(int[] nums) {
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num <= first)
                first = num;
            if (num>first && num <= second)
                second = num;
            if (num>first && num>second) // or just if(num > second)
                return true; // Found a number greater than both first and second
        }
        return false;
    }
    /** same as above {@link #increasingTriplet(int[])} */
    public static boolean increasingTriplet3(int[] nums) {
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num <= first)
                first = num;
            else if (num>first && num <= second)
                second = num;
            else if (num>first && num>second) // or just if(num > second)
                return true; // Found a number greater than both first and second
        }
        return false;
    }



    public static boolean increasingTriplet4(int[] nums) {
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        for (int num : nums) {
            if(num > second) return true; // found a number greater than both first and second
            if(num <= first) first = num; // replace first with nums[i]
            else if(num <= second) second = num; // replace second with nums[i]
        }
        return false;
    }


    public static boolean increasingTriplet5(int[] nums) {
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num > second) return true; // found a number greater than both first and second
            if (num > first) second = Math.min(second, num); // replace second with nums[i] if it's smaller than current second
            first = Math.min(first, num);
        }
        return false;
    }






    /**
     * @TimeComplexity: O(n^2)
     * @SpaceComplexity: O(1)
     */
    public static boolean increasingTripletUsingDFS(int[] nums) {
        int n= nums.length;
        if(n < 3) return false;
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        return dfs(0, nums, first, second);
    }
    public static boolean dfs(int i, int[] nums, int first, int second) {
        if (i == nums.length) return false;
        if (nums[i] <= first) return dfs(i+1, nums, nums[i], second); // replace first with nums[i]
        else if (nums[i] <= second) return dfs(i+1, nums, first, nums[i]); // replace second with nums[i]
        else return true; // found a number greater than both first and second
    }



    /**
     * TLE
     * @TimeComplexity O(n^3)
     * @SpaceComplexity O(1)
     *
     * We can use brute force or DFS or dynamic programming to solve this problem.
     */
    public static boolean increasingTripletBruteForce(int[] nums) {
        int n = nums.length;
        if(n < 3) return false;

        for(int i=0; i<n; i++) {
            int firstNum = nums[i];
            // find secondNum
            for (int j=i+1; j<n; j++) {
                int secondNum = nums[j];
                // find thirdNum
                if(firstNum < secondNum) {
                    for (int k=j+1; k<n; k++) {
                        int thirdNum = nums[k];
                        if(secondNum < thirdNum)
                            return true;
                    }
                }
            }
        }
        return false;
    }



    // TLE
    public static boolean increasingTripletDP(int[] nums) {
        int n = nums.length;
        if(n < 3) return false;

        // dp[i] = length of longest increasing subsequence ending at index i
        int[] dp = new int[n];
        for (int i=0; i<n; i++) {
            dp[i] = 1; // every element is a subsequence of length 1
            for (int j=0; j<i; j++) {
                if(nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j]+1);
                }
            }
            if(dp[i] >= 3) return true;
        }
        return false;
    }









    // NOT WORKING
    public boolean increasingTripletUsingHashMap(int[] nums) {
        int n = nums.length;
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        for (int i=0; i<n; i++) map.computeIfAbsent(i, _->new LinkedHashMap<>()).put(nums[i], 1);
        if(n < 3) return false;
        for(int i=0; i<n; i++) {
            for(int j=i+1; j<n; j++) {
                if (nums[i] <= nums[j]) {
                    map.get(i).merge(nums[j], 1, Integer::sum);
                }
            }
        }
        for(int k: map.keySet()) {
            Map<Integer, Integer> counterMap = map.get(k);
            System.out.println(counterMap);
            if (isIncreasingTriplets( counterMap )) return true;
        }

        return false;
    }


    public boolean isIncreasingTriplets(Map<Integer, Integer> counterMap) {
        int n = counterMap.size();
        if(n < 3) return false;
        List<Integer> lst = new ArrayList<>(counterMap.keySet());

        int c = 0;
        int firstNum = lst.get(0);
        c+=counterMap.get(firstNum);
        if(c==3) return true;

        return false;
    }



    // ONLY WORKS FOR CONSECUTIVE INDICES
    public boolean increasingTripletIfConsecutiveIndices(int[] nums) {
        int n = nums.length;
        if(n < 3) return false;
        for(int i=0; i<n-2; i++) {
            if(nums[i] < nums[i+1] && nums[i+1] < nums[i+2]) return true;
        }
        return false;
    }

}
