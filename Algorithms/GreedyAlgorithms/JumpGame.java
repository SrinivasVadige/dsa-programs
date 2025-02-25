package Algorithms.GreedyAlgorithms;

/**
 * <pre>
 *
    THOUGHTS:
    ---------
    1) Num of Children at i == nums[i]

                                            [2*,3,1,1,4]
                             ____________________|____________
                            |                                 |
                       [2,3*,1,1,4]                     [2,3,1*,1,4]
         ___________________|___________________              |
        |                   |                   |             |
    [2,3,1*,1,4]       [2,3,1,1*,4]       [2,3,1,1,4*]  [2,3,1,1*,4]

    2) Instead of calculating every possibility using backtracking, just use need=nums.length variable to easily reach the end.
    3) nums[n-1] is "end goal"
    4) So, start from nums[n-1] and shift the "end goal" to the right side if we can reach it.
    5) Finally we reach nums[0] and check if it can reach to near by "end flag"
    6) That means, we nums[0] can reach nums[n-1]
 * </pre>
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 24 Feb 2025
 */
public class JumpGame {
    public static void main(String[] args) {
        int[] nums = {2,3,1,1,4};
        System.out.println("canJump(nums) => " + canJump(nums));
        System.out.println("canJumpUsingGoalShift(nums) => " + canJumpUsingGoalShift(nums));
        System.out.println("canJumpMyApproach(nums) => " + canJumpMyApproach(nums));
    }

    public static boolean canJump(int[] nums) {
        int n = nums.length, max = 0;
        for (int i = 0; i < n - 1; i++) {
            max = Math.max(max, i + nums[i]); // max at each index
            if (max <= i) return false;
        }
        return true;
    }




    public static boolean canJumpUsingGoalShift(int[] nums) {
        int n = nums.length, goal = n - 1;
        for (int i = n - 2; i >= 0; i--) {
            if (i + nums[i] >= goal) goal = i;
        }
        return goal == 0;
    }





    public static boolean canJumpMyApproach(int[] nums) {

        for (int i=nums.length-1; i>=0; i--){
            if (
                (nums[i] + i) >= (nums.length-1) ||
                isJumpFound(nums, i)
                ) nums[i] = -1; // "flag" that index as --> can jump

        }
        return nums[0]==-1;
    }

    private static boolean isJumpFound(int[] nums, int start) {
        for (int i=1; i<=nums[start] && i<nums.length; i++) {
            if (nums[i+start]==-1) return true;
        }
        return false;
    }





    public static boolean canJumpDp(int[] nums) {
        if (nums.length==1) return true;
        boolean[] dp = new boolean[nums.length];
        dp[nums.length-1] = true;
        for (int i=nums.length-2; i>=0; i--) {
            for (int j=1; j<=nums[i] && i+j<nums.length; j++) {
                if (dp[i+j]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[0];
    }






    public static boolean canJumpBacktracking(int[] nums) {
        return rec(nums, 0, nums.length-1);
    }
    private static boolean rec(int[] nums, int start, int end) {
        if (start==end) return true;
        for (int i=1; i<=nums[start] && i<=end-start; i++) {
            if (rec(nums, start+i, end)) return true;
        }
        return false;
    }





    public static boolean canJumpBacktrackingMyApproach(int[] nums) {
        if (nums.length==1) return true;
        return jump(nums, 0);
    }

    private static boolean jump(int[] nums, int i) {
        if (i==nums.length-1) return true;
        if (i>nums.length) return false;

        for (int c=1; c<=nums[i]; c++) {
            if (jump(nums, i+c)) return true;
        }

        return false;
    }

}
