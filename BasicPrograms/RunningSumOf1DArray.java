package BasicPrograms;

import java.util.Arrays;

/**
 * @author Firstname Lastname, srinivas.vadige@gmail.com
 * @since 10 Oct 2024
 */
public class RunningSumOf1DArray {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4};
        System.out.println(Arrays.toString(runningSum(nums)));
    }

    public static int[] runningSum(int[] nums) {
        for(int i=1; i<nums.length; i++) {
            nums[i]+=nums[i-1];
        }
        return nums;
    }
}
