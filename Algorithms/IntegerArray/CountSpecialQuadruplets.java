package Algorithms.IntegerArray;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 06 April 2025
 */
public class CountSpecialQuadruplets {
    public static void main(String[] args) {
        int[] nums = {28,8,49,85,37,90,20,8};
        System.out.println(countQuadruplets(nums)); // Output: 1
    }

    public static int countQuadruplets(int[] nums) {
        int count = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        if (nums[i] + nums[j] + nums[k] == nums[l]) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }


    public static int countQuadruplets2(int[] nums) {
        int cnt = 0;
        int[] diff = new int[201];
        int n = nums.length;
        for (int b = n - 3; b >= 1; b--) {
            int c = b + 1;
            for (int d = c + 1; d < n; d++) {
                if (nums[d] >= nums[c]) diff[nums[d] - nums[c]]++;
            }
            for (int a = 0; a < b; a++) {
                cnt += diff[nums[a] + nums[b]];
            }
        }
        return cnt;
    }
}
