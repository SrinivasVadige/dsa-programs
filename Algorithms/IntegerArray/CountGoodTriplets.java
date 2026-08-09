package Algorithms.IntegerArray;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 06 April 2025
 */
public class CountGoodTriplets {
    public static void main(String[] args) {
        int[] nums = {3,0,1,1,9,7};
        int a = 3, b = 4, c = 4;
        System.out.println(countGoodTriplets(nums, a, b, c)); // Output: 10
    }

    public static int countGoodTriplets(int[] nums, int a, int b, int c) {
        int count = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                if (Math.abs(nums[i] - nums[j]) <= a) {
                    for (int k = j + 1; k < nums.length; k++) {
                        if (Math.abs(nums[j] - nums[k]) <= b && Math.abs(nums[i] - nums[k]) <= c) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }




    public int countGoodTriplets2(int[] arr, int a, int b, int c) {
        int ans = 0, n = arr.length;
        int[] sum = new int[1001];
        for (int j = 0; j < n; ++j) {
            for (int k = j + 1; k < n; ++k) {
                if (Math.abs(arr[j] - arr[k]) <= b) {
                    int lj = arr[j] - a, rj = arr[j] + a;
                    int lk = arr[k] - c, rk = arr[k] + c;
                    int l = Math.max(0, Math.max(lj, lk)),
                    r = Math.min(1000,Math.min(rj, rk));
                    if (l <= r) {
                        if (l == 0) {
                            ans += sum[r];
                        } else {
                            ans += sum[r] - sum[l - 1];
                        }
                    }
                }
            }
            for (int k = arr[j]; k <= 1000; ++k) {
                ++sum[k];
            }
        }
        return ans;
    }
}
