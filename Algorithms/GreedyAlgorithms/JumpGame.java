package Algorithms.GreedyAlgorithms;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 24 Feb 2025
 * @link 55. Jump Game <a href="https://leetcode.com/problems/jump-game/">leetcode link</a>
 * @topics Array, Dp, Greedy
 * @companies Google, Amazon, Microsoft, Bloomberg, Meta, DoorDash, Zoho, TomTom, TikTok, Turing, Adobe, tcs, Apple, Goldman Sachs, Yahoo, Oracle, Uber, Media.net, Salesforce, Shopee
 */
public class JumpGame {
    public static void main(String[] args) {
        int[] nums = {2,3,1,1,4};
        System.out.println("canJump using Greedy GoalShift => " + canJumpUsingGreedyGoalShift(nums));
        System.out.println("canJump using Greedy MaxJump => " + canJumpUsingGreedyMaxJump(nums));
        System.out.println("canJump using BottomUpTabulationDp => " + canJumpUsingBottomUpTabulationDp(nums));
        System.out.println("canJump using TopDownMemoDp => " + canJumpUsingTopDownMemoDp(nums));
        System.out.println("canJump using Backtracking TLE => " + canJumpUsingBacktracking(nums));
    }


    /**
     * Greedy using l-flag and r-flag or goal

        nums = [2,3,1,1,4]

        [2,3,1,1,4]
               l r  -> true then r--

        [2,3,1,1,4]
             l r   -> true then r--

        [2,3,1,1,4]
           l r     -> true then r--

        [2,3,1,1,4]
         l r       -> true then r--

    */
    public static boolean canJumpUsingGreedyGoalShift(int[] nums) {
        int n = nums.length, goal = n - 1;
        for (int i = n - 2; i >= 0; i--) {
            if (i + nums[i] >= goal) goal = i;
        }
        return goal == 0;
    }







    /**
         maxJump = 3;
         [3, 2, 1, 0, 4]
         i

         maxJump = 2; max(3--, 2) = max(2,2)
         [3, 2, 1, 0, 4]
         i

         maxJump = 1; max(2--, 1) = max(1,1)
         [3, 2, 1, 0, 4]
         i

         maxJump = 0; max(1--, 0) = max(0,0)
         [3, 2, 1, 0, 4]
         i

         if maxJump<=0 break; false;
     */
    public static boolean canJumpUsingGreedyMaxJump(int[] nums) {
        if(nums.length == 1) return true;

        int maxJump = nums[0];
        for(int num: nums) {
            if(maxJump <= 0) return false;
            maxJump = Math.max(maxJump-1, num);
        }
        return true;
    }






    public static boolean canJumpUsingGreedyMaxJump2(int[] nums) {
        int n = nums.length, max = 0;
        for (int i = 0; i < n - 1; i++) {
            max = Math.max(max, i + nums[i]); // max at each index
            if (max <= i) return false;
        }
        return true;
    }









    /**
     * Bottom Up Tabulation DP

            [3, 2, 1, 0, 4] [f, f, f, f, t]
                      i
            [3, 2, 1, 0, 4] [f, f, f, f, t]
                   i
            [3, 2, 1, 0, 4] [f, f, f, f, t]
                i
            [3, 2, 1, 0, 4] [f, f, f, f, t]
             i


     */
    public static boolean canJumpUsingBottomUpTabulationDp(int[] nums) {
        int n = nums.length;
        boolean[] isJump = new boolean[n];
        isJump[n-1]=true;
        for(int i=n-2; i>=0; i--) {
            isJump[i] = nums[i] >= n-1-i || canJump(isJump, i+1, Math.min(n-1, i+nums[i]));
            /*
            or Arrays.stream(isJump).skip(i+1).limit(nums[i]).anyMatch(b->b); ---> O(n) ---> TLE
            we don't have BooleanStream so Arrays.stream(boolean[]) won't work
            and Stream<Boolean> Arrays.stream(Boolean[]) creation is O(n) not O(1) like Arrays.stream(int[])
            */
        }
        return isJump[0];
    }
    private static boolean canJump(boolean[] isJump, int l, int r) {
        while(l<=r) {
            if (isJump[l++] || isJump[r--]) {
                return true;
            }
        }
        return false;
        /*
        // or
        int range = nums[start++];
        for(int j=0; j<range && start<nums.length; j++) {
        */
    }










    public static boolean canJumpUsingBottomUpTabulationDp2(int[] nums) {
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
            /*
            or
            int furthestJump = Math.min(i + nums[i], n - 1);
            for (int j = i + 1; j <= furthestJump; j++) {
                        if (dp[j]) {
                    dp[i] = true;
                    break;
                }
            }
             */
        }
        return dp[0];
    }






    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n)

     THOUGHTS:
     ---------
     1) Num of Children at i == nums[i]

                                           [2*,3,1,1,4]
                               ____________________|____________
                             |                                 |
                     [2,3*,1,1,4]                     [2,3,1*,1,4]
         ___________________|____________________              |
        |                   |                   |              |
        [2,3,1*,1,4]       [2,3,1,1*,4]     [2,3,1,1,4*]  [2,3,1,1*,4]

     2) Instead of calculating every possibility using backtracking, just use need=nums.length variable to easily reach the end.
     3) nums[n-1] is "end goal"
     4) So, start from nums[n-1] and shift the "end goal" to the right side if we can reach it.
     5) Finally we reach nums[0] and check if it can reach to near by "end flag"
     6) That means, we nums[0] can reach nums[n-1]
     */
    public static boolean canJumpUsingTopDownMemoDp(int[] nums) {
        Boolean[] memo = new Boolean[nums.length];
        return dfs(0, nums, memo);
    }

    private static boolean dfs(int position, int[] nums, Boolean[] memo) {
        if (memo[position] != null) return memo[position];
        if (position >= nums.length - 1) return true;

        int furthestJump = Math.min(position + nums[position], nums.length - 1);
        for (int nextPosition = position + 1; nextPosition <= furthestJump; nextPosition++) {
            if (dfs(nextPosition, nums, memo)) {
                return memo[position] = true;
            }
        }
        return memo[position] = false;
    }








    public static boolean canJumpUsingTopDownMemoDp2(int[] nums) {
        int n = nums.length;
        if (n==1) return true;
        Boolean[] dp = new Boolean[nums.length]; // initially, dp[i]=null. So. will mark T/F as per dfs result
        dp[n-1] = true; // last index is "end goal" and it's always true
        dp[0]=dfs(nums, 0, dp);
        return dp[0];
    }
    private static boolean dfs(int[] nums, int i, Boolean[] dp) {
        int n = nums.length;
        if (i==n-1) return true;
        else if (i>n-1) return false;
        else if (dp[i]!=null) return dp[i];

        // if currNum=3, then we can jump to i+1, i+2, i+3 places
        for (int j=1; j<=nums[i] && i+j<n; j++) {
            if (dfs(nums, i + j, dp)) { // no need to assign "dp[i+j]=dfs(nums, i + j, dp)" as we return "dp[i] = false" at last
                return dp[i] = true;
            }
        }
        return dp[i] = false;  // only now store the result for i
    }









    public static boolean canJumpUsingBacktracking(int[] nums) {
        return rec(nums, 0, nums.length-1);
    }
    private static boolean rec(int[] nums, int start, int end) {
        if (start==end) return true;
        for (int i=1; i<=nums[start] && i<=end-start; i++) {
            if (rec(nums, start+i, end)) return true;
        }
        return false;
    }
}
