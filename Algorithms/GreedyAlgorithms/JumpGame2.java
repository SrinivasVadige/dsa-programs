package Algorithms.GreedyAlgorithms;

import java.util.Arrays;

public class JumpGame2 {
    public static void main(String[] args) {
        int[] nums = {2,3,1,1,4};
        System.out.println("jump(nums) => " + jump(nums));
    }

    public static int jump(int[] nums) {
        int jumps = 0, maxJump = 0, end = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            maxJump = Math.max(maxJump, i + nums[i]);
            if (i == end) {
                jumps++;
                end = maxJump;
            }
        }
        return jumps;
    }



    // NOT WORKING
    public int canJumpMyApproach(int[] nums) {
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
