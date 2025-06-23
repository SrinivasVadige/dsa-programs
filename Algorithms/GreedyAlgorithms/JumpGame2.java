package Algorithms.GreedyAlgorithms;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 26 Feb 2025
 * @link 45. Jump Game II <a href="https://leetcode.com/problems/jump-game-ii/">Leetcode link</a>
 * @topics Array, Greedy, Dynamic Programming
 */
public class JumpGame2 {
    public static void main(String[] args) {
        int[] nums = {2,3,1,1,4};
        System.out.printf("jump => %d%n", jump(nums));
        System.out.printf("jumpUsingBfs => %d%n", jumpUsingBfs(nums));
        System.out.printf("jumpUsingDfs => %d%n", jumpUsingDfs(nums));
    }

    public static int jump(int[] nums) {
        int jumps = 0, maxJump = 0, endI = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            maxJump = Math.max(maxJump, i + nums[i]);
            if (i == endI) {
                jumps++;
                endI = maxJump;
            }
        }
        return jumps;
    }

    /**
     * Just imagine as sections or levels --> calculate after how many levels we reach the end
     * <pre>
            0  1  2  3  4
          [ 2, 3, 1, 1, 4 ]
           |_| |__|  |___|
            1   2      3

        Here, 0th index 2 is 1st level, and we can jump to 1,2 indices, then consider this 1,2 indices as 2nd level
        Now check the 3rd level we can jump from from 2nd level, if the 3rd level contains n-1 then we can return the jumps
     * </pre>
     */
    public static int jumpUsingBfs(int[] nums) {
        int l=0, r=0, jumps=0, n=nums.length;
        while(r<n-1) {
            int farthestJump=0; // nextR
            // calculate next level boundaries --> next l, r
            // --> we already know that nextL=currR+1.
            // So basically we calculate the nextR
            for(int i=l; i<=r; i++) {
                farthestJump = Math.max(farthestJump, i+nums[i]);
            }
            l=r+1; // nextL=currR+1
            r=farthestJump; // nextR
            jumps++;
        }
        return jumps;
    }



    /**
         [-1,-1,-1,-1, 0]
         [2, 3, 0, 1, 4]
                      i
         [-1,-1,-1, 1, 0]
         [2, 3, 0, 1, 4]
                   i
         [-1,-1, n, 1, 0]
         [2, 3, 0, 1, 4]
                i
         [-1, 1, n, 1, 0]
         [2, 3, 0, 1, 4]
             i
         [2, 1, n, 1, 0]
         [2, 3, 0, 1, 4]
          i



         [-1,-1,-1, 0]
         [1, 1, 1, 1]
                   i
         [-1,-1, 1, 0]
         [1, 1, 1, 1]
                i
         [-1, 2, 1, 0]
         [1, 1, 1, 1]
             i
         [3, 2, 1, 0]
         [1, 1, 1, 1]
          i

     */
    public static int jumpUsingDfs(int[] nums) {
        int n = nums.length;
        if(n==1) return 0;
        int[] dp = new int[n];
        Arrays.fill(dp, -1); // to cover the edge case in dfs recursion or use Integer[] dp
        dp[n-1]=0;
        for(int i=n-2; i>=0; i--) {
            dfs(nums, i, dp);
        }
        return dp[0];
    }
    private static int dfs(int[] nums, int i, int[] dp) {
        int n = nums.length;

        if(dp[i] != -1) return dp[i]; // Arrays.fill(dp, -1); edge case or use Integer[] dp instead of int[]
        else if(nums[i] >= n-1-i) return dp[i] = 1;
        else if (nums[i]==0) return dp[i] = n;

        int minJump = n;
        for(int j=i+1; j-i <= nums[i]; j++) { // && j<n is not needed as we have "nums[i] >= n-1-i" condition check at top
            minJump = Math.min(minJump, dfs(nums, j, dp)+1 );
        }

        return dp[i] = minJump;
    }



    /**
     * Top-Down DP with no memo, just recursion i.e., backtracking
     * TLE
     * */
    public int jumpBacktracking(int[] nums) {
        if(nums.length==1) return 0;
        return dfs(nums, 0, 1);
    }
    private int dfs(int[] nums, int i, int currJump) {
        int n = nums.length;
        if (nums[i]+i+1 >= n) return currJump; // BASE CASE
        else if (nums[i]==0) return Integer.MAX_VALUE;

        // we need to jump again i.e +1
        int minJump=Integer.MAX_VALUE;
        for(int j=i+1; j<n && j-i <= nums[i]; j++) {
            minJump = Math.min(minJump, dfs(nums, j, currJump+1));
        }
        return minJump;
    }








    public int jumpTopDownMemoDp(int[] nums) {
        int n = nums.length;
        if (n == 1) return 0;

        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        dp[n - 1] = 0;

        return dfs1(nums, 0, dp);
    }

    private int dfs1(int[] nums, int i, int[] dp) {
        if (dp[i] != -1) return dp[i];

        int n = nums.length;
        int minJump = Integer.MAX_VALUE;

        for (int j = i + 1; j <= Math.min(i + nums[i], n - 1); j++) {
            int jumps = dfs1(nums, j, dp);
            if (jumps != Integer.MAX_VALUE) {
                minJump = Math.min(minJump, 1 + jumps);
            }
        }

        return dp[i] = minJump;
    }






    public int jumpTopDownMemoDp2(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return dfs2(nums, 0, dp);
    }

    private int dfs2(int[] nums, int i, int[] dp) {
        int n = nums.length;

        if (i >= n - 1) return 0; // already at or beyond the end
        if (dp[i] != -1) return dp[i];
        if (nums[i] == 0) return dp[i] = n; // can't move

        int minJump = n;

        for (int j = i + 1; j <= Math.min(i + nums[i], n - 1); j++) {
            int jumps = dfs2(nums, j, dp);
            if (jumps != n) {
                minJump = Math.min(minJump, 1 + jumps);
            }
        }

        return dp[i] = minJump;
    }









    // NOT WORKING
    public int canJumpMyApproachOld(int[] nums) {
        int min = 0;
        if (nums.length==1) return min;

        for (int i=nums.length-1; i>=0; i--){
            if ((nums[i] + i) >= (nums.length-1)) {
                nums[i] = -1;
                min = 1;
                continue;
            }

            if (isJumpFound(nums, i)) {
                nums[i] = -1; // "flag" that index as --> can jump
                min++;
            }
            System.out.println(Arrays.toString(nums));
            System.out.println(min);
        }
        if (nums[0]==-1) {
            return min;
        }
        return -1;
    }

    private static boolean isJumpFound(int[] nums, int start) {
        int end = (start+nums[start])>=nums.length? nums.length-1: start+nums[start];
        int i = end;
        for (;(end-i)<nums[start] && i>-1; i--) {
            if (nums[i]==-1) return true;
        }
        // return end-1 ---> and subtract the same from min??
        return false;
    }
}
