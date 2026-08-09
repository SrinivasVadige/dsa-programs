package Algorithms.DynamicProgramming;

import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;

/**
 * <pre>
 * sum of all elements = 2 *( 1 sub array sum )
 *
 * loop up to (sum/2) calculate
 *
 * same like coin change --- but limited coins
 *
 * can't repeat same index
 * don't care about subArrays lens
 *
 * [1,5,11,5]
 *
 * sum = 22
 * target = 11
 * Contiguous SubsSequence array i.e don't take prev indices in the subArray. So, last ele in suArray has to has child nodes with next index elements
 * So, child nodes of current node will always be less than parent
 *
 *                                                                                      [] sum=0 need=11
 *                                            ___________________________________________|___________________________________________
 *                                            |                                          |                          |                |
 *                                           [1] s=1 n=10                         [5] s=5 n=6                     [11]              [5]
 *                   _________________________|______________________________            |                          |                |
 *                  |                         |                            |
 *                 [1,5] s=6 n=5             [1,11] s=16 n=5             [1,5] s=6 n=5
 *      ____________|                         |
 *     |            |                        [1,11,5]
 *    [1,5,11]     [1,5,5]
 *    s=17 n=-6    s=11 n=0
 *     ❌           ✅
 *
 * If we got one targetSum from one subArray sum, We already know that the other subArray sum must be 11. because the total sum of the given array is 22.
 *
 * So, if we can have a best solution for Sum of Non Contiguous SubArray then we can return true (i.e "sub-array sum equals to given array sum/2")
 * </pre>
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 05 Nov 2024
 */
public class Knapsack_01_DP_PartitionEqualSubsetSum {

    public static void main(String[] args) {
        int[] nums = {1, 5, 11, 5};
        System.out.println(canPartitionDP2(nums));
    }

    /**
     * <pre>
     * Using Set
     * Instead of above n^n graph we can convert it into 2^n graph as below
     * Keep the same subArr on the right side and on the left side add the next index num for each child in the width of the graph
     *
     *
     *
     *  Dummy  ---->                                               [0] sum=0 need=11
     *                                                ______________________|________________________________________________________________________________________
     *                                                |                                                                                                             |
     *  i=0  ---->                                [0,1]s=1,n=10                                                                                                  [0]s=0,n=11
     *                           _____________________|_____________________                                                   ______________________________________|_____________________
     *                           |                                         |                                                   |                                                           |
     *  i=1  ---->             [0,1,5]s=6,n=5                           [0,1]s=1,n=10                                         [0,5]s=5,n=6                                             [0]s=0,n=11
     *                  __________|_________                    __________|_________________                        _______________|______________                           _______________|______________
     *                 |                    |                   |                           |                       |                            |                           |                             |
     *  i=2  ----> [0,1,5,11]s=17,n=-6   [0,1,5] s=6 n=5        [0,1,11]s=16,n=-5        [0,1]s=1,n=10             [0,5,11]s=16,n=-5          [0,5]s=5,n=6               [0,11]s=11,n=0               [0]s=0,n=11
     *                  ❌    ______________|_________________      ❌        ______________|______________         ❌            ______________|_____________             ✅            _______________|______________
     *                        |                               |               |                             |                      |                           |                           |                            |
     *  i=3  ---->      [0,1,5,5]s=11,n=0          [0,1,5]s=6,n=5          [0,1,5]s=6,n=5          [0,1]s=1,n=10                  [0,5,5]s=10,n=1          [0,5]s=5,n=6                   [0,5]s=5,n=6                [0]s=0,n=11
     *                         ✅
     *
     *
     * Here, if I said i=x means in that total graph we have to add that index num to the left child of parent for the whole graph width
     * So, simply maintain all these sums in a Set or HashMap(with counter) and just check the if the target sum is present or not
     *
     * </pre>
     */
    public static boolean canPartitionDpUsingSet(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        if(sum % 2 != 0) return false; else sum/=2;

        Set<Integer> set = new HashSet<>();
        set.add(0);
        for (int num : nums) {
            Set<Integer> newSet = new HashSet<>(); // In below loop, if we add the nums to the existing "set" variable then we'll iterate over the just added nums. To avoid this just create a new set and consume it later
            for (int s : set) {
                newSet.add(s + num); // left child
                newSet.add(s); // right child
            }
            set = newSet; // or set.addAll(newSet);
        }

        return set.contains(sum);
    }

    public static boolean canPartitionDP2(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        if(sum % 2 != 0) return false; else sum/=2;

        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;
        for (int num : nums) {
            for (int i = sum; i >= num; i--) {
                dp[i] = dp[i] || dp[i - num];
            }
        }
        return dp[sum];
    }

    public static boolean canPartitionDP3(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        if(sum % 2 != 0) return false; else sum/=2;

        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;
        for (int num : nums) {
            for(int i=sum; i>0 && num<=i; i--){
                if(!dp[i]) dp[i]=dp[i-num];
            }
        }
        return dp[sum];
    }





    // Working but TLE. So, use below HashMap memo approach
    public static boolean canPartitionTopDown2(int[] nums) {
        int target = Arrays.stream(nums).sum();
        if(target % 2 != 0) return false; else target/=2;
        return rec2(nums, 0, 0, target);
    }

    private static boolean rec2(int[] nums, int index, int sum, int total) {
        if (sum == total) return true;
        if (sum > total || index >= nums.length) return false;

        return rec2(nums, index + 1, sum+nums[index], total) // left child
                || rec2(nums, index + 1, sum, total); // right child
    }






    public static boolean canPartitionTopDownHashMap(int[] nums) {
        int target = Arrays.stream(nums).sum();
        if(target % 2 != 0) return false; else target/=2;
        return recMap(nums, 0, 0, target, new HashMap<>());
    }

    private static boolean recMap(int[] nums, int index, int sum, int total, HashMap<String, Boolean> memo) {
        String current = index + "," + sum;
        if (memo.containsKey(current)) return memo.get(current);
        if (sum == total) return true;
        if (sum > total || index >= nums.length) return false;

        boolean foundPartition =
        recMap(nums, index + 1, sum+nums[index], total, memo) // left child
        || recMap(nums, index + 1, sum, total, memo); // right child

        memo.put(current, foundPartition);
        return memo.get(current);
    }






    // same like above approach, instead of using the sum just decrease the target value and use dp[][]
    public static boolean canPartitionTopDown(int[] nums) {
        int target = Arrays.stream(nums).sum();
        if(target % 2 != 0) return false; else target/=2;
        return rec(nums, 0, target, new Boolean[nums.length + 1][target + 1]);
    }

    private static boolean rec(int[] nums, int index, int target, Boolean[][] dp) {
        if (target == 0) return true;
        if (target < 0 || index >= nums.length - 1) return false;
        if (dp[index][target] != null) return dp[index][target];

        return dp[index][target] =
        rec(nums, index + 1, target - nums[index + 1], dp) // left child
        || rec(nums, index + 1, target, dp); // right child
    }






    /**
     * 🔥
     */
    public static boolean canPartition01KnapsackSolution(int[] nums) {
        int sum=0;
        for(int i=0;i<nums.length;i++) sum=sum+nums[i];
        if(sum%2!=0) return false;

        int n=nums.length;
        int k=sum/2;
        boolean dp[][]=new boolean[n+1][k+1];
        for (int i=0; i<n+1; i++) {
            for (int j=0; j<k+1; j++) {
                if (i==0) dp[i][j]=false;
                else if (j==0) dp[i][j]=true;
            }
        }
        for (int i=1; i<n+1; i++) {
            for (int j=1; j<k+1; j++) {
                if (nums[i-1]<=j) dp[i][j]=dp[i-1][j-nums[i-1]] || dp[i-1][j];
                else dp[i][j]=dp[i-1][j];
            }
        }
        return dp[n][k];
    }






    public static boolean canPartitionDivideAndConquer(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        if(sum % 2 != 0) return false; else sum/=2;
        int[][] dp =new int[nums.length][sum + 1];
        for(int i = 0;i<nums.length;i++){
            for(int j = 0;j<=sum;j++){
                dp[i][j] = -1;
            }
        }
        return c(nums,0,sum,dp);
    }
    private static Boolean c(int[] arr, int i, int sum, int[][] dp){
        if(sum == 0)
            return true;
        if(i == arr.length-1) {
            if(sum == 0 || sum-arr[i] == 0){
                return true;
            }
            return false;
        }
        if(sum<0) return false;
        if(dp[i][sum]!=-1) {
            if(dp[i][sum] == 1) return true;
            return false;
        }

        boolean b =c(arr,i+1,sum-arr[i],dp) || c(arr,i+1,sum,dp);
        if(b){
            dp[i][sum] = 1;
            return b;
        }
        dp[i][sum] = 0;
        return b;
    }














    // Working but TLE
    public static boolean canPartitionBacktracking(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        if(sum % 2 != 0) return false; else sum/=2;

        return backtrack(nums, sum, 0, 0, new boolean[nums.length]);
    }

    /**
     * This backtracking approach is TLE because it explores all possible subsets recursively,
     * and the boolean[] "memo" does not actually memoize (index, sum) pairs, so it repeats sub-problems.
     * Time complexity is O(2^n).
     *
     * In contrast, canPartitionDpUsingSet() uses a Set to store all possible subset sums,
     * building up solutions iteratively and avoiding redundant calculations.
     * This reduces the time complexity to O(n*sum/2) and is much faster.
     */
    private static boolean backtrack(int[] nums, int sum, int index, int currentSum, boolean[] memo) {
        if(currentSum == sum) return true;
        if(index >= nums.length || currentSum > sum) return false;

        if(memo[index]) return memo[index];

        boolean include = backtrack(nums, sum, index + 1, currentSum + nums[index], memo);
        boolean exclude = backtrack(nums, sum, index + 1, currentSum, memo);
        return memo[index] = include || exclude;
    }


}
