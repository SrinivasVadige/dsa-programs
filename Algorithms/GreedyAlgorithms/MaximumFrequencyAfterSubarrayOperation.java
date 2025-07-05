package Algorithms.GreedyAlgorithms;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 05 July 2025
 * @link 3434. Maximum Frequency After Subarray Operation <a href="https://leetcode.com/problems/maximum-frequency-after-subarray-operation">Leetcode link</a>
 * @topics Array, Greedy, Dp, HashTable
 * @companies amazon, google, microsoft
 */
public class MaximumFrequencyAfterSubarrayOperation {
    public static void main(String[] args) {
        int[] nums = {10, 2, 3, 10, 3, 3, 10, 3, 3};
        int k = 10;
        System.out.println("maxFrequency using Kadane's Algorithm: " + maxFrequencyUsingKadanesAlgorithm(nums, k));
        System.out.println("maxFrequency 2: " + maxFrequency2(nums, k));
    }




    /**
     * @TimeComplexity O(N*U), where N is the length of nums and U is the number of unique numbers
     * @SpaceComplexity O(U)
     * Using Kadane's Algorithm
     * --> see {@link Algorithms.DivideAndConquer.MaximumSubArray#maxSubArrayUsingKadanesAlgorithm}
     * and {@link Algorithms.DivideAndConquer.MaximumSubArray#maxSubArrayUsingKadanesAlgorithm2}

        We know that if the numbers are different like 2,2,3,4
        then even if we add x=2 to that numbers 4,4,5,6 the numbers will be still different

        So, assume that we need max frequency of any number in the subArray and if we add x=k-num,
        then we'll get the max frequency

        nums=[10, 2, 3, 10, 3, 3, 10, 3, 3], target=10
        set=[10, 2, 3]

        when target = 10
        [10, 2, 3, 10, 3, 3, 10, 3, 3]
        skip as target == k == 10

        when target = 2
        [10, 2, 3, 10, 3, 3, 10, 3, 3]
          i                            ---> count = -1 => 0, maxCount = 0
             i                         ---> count =  1, maxCount = 1
                i                      ---> count =  1, maxCount = 1
                    i                  ---> count =  0, maxCount = 1   ---> 1 target and 1 k ---> are canceled out
                       i               ---> count =  0, maxCount = 1
                          i            ---> count =  0, maxCount = 1
                              i        ---> count =  -1 => 0, maxCount = 1   ---> 1 target and 1 k ---> are canceled out
                                  i    ---> count =  0, maxCount = 1
                                     i ---> count =  0, maxCount = 1
        maxCount = 1

        when target = 3
        [10, 2, 3, 10, 3, 3, 10, 3, 3]
          i                            ---> count = -1 => 0, maxCount = 0
             i                         ---> count =  0, maxCount = 0
                i                      ---> count =  1, maxCount = 1
                    i                  ---> count =  0, maxCount = 1  ---> 1 target and 1 k ---> are canceled out
                       i               ---> count =  1, maxCount = 1
                          i            ---> count =  2, maxCount = 2
                              i        ---> count =  1, maxCount = 2  ---> 1 target and 1 k ---> are canceled out
                                  i    ---> count =  2, maxCount = 2
                                     i ---> count =  3, maxCount = 3
        maxCount = 3

     */
    public static int maxFrequencyUsingKadanesAlgorithm(int[] nums, int k) {
        final int n = nums.length;
        int kCount  = 0;
        int res = 0;
        Set<Integer> set = new HashSet<>();

        // Count the number of k's and collect unique numbers
        for (int num : nums) {
            if(num == k) {
                kCount++;
            }
            set.add(num);
        }

        if(kCount == n) {
            return n;
        }

        // Try every number ≠ k as a conversion target
        for (int target : set) {
            if(target == k) {
                continue;
            }

            int count = 0;
            int maxCount = 0;
            // Kadane's Algorithm
            for(int num: nums) {
                if(num == target) {
                    count++;
                } else if(num == k) { // 1 target and 1 k ---> are canceled out
                    count--;
                }

                if (count < 0) {
                    count = 0;
                }
                maxCount =  Math.max(maxCount, count);
            }
            res = Math.max(res, maxCount);
        }
        return res+kCount;
    }










    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     * int[] count = new int[51]; // as the problem stated "1 <= nums[i] <= 50" and skip count[0] 0th index ele

        when target = 10
        [10, 2, 3, 10, 3, 3, 10, 3, 3]
          i                            ---> count[10] = max(count[10], count[10])+1 => max(0, 0)+1 => 1,   count[10] = 1, res=max(0, 1-1)=0
             i                         ---> count[2]  = max(count[2], count[10])+1  => max(0, 1)+1 => 2,   count[10] = 1, res=max(0, 2-1)=1
                i                      ---> count[3]  = max(count[3], count[10])+1  => max(0, 1)+1 => 2,   count[10] = 1, res=max(0, 1-1)=0
                    i                  ---> count[10] = max(count[10], count[10])+1 => max(1, 1)+1 => 2,   count[10] = 2, res=max(0, 1-1)=0
                       i               ---> count[3]  = max(count[3], count[10])+1  => max(2, 2)+1 => 3,   count[10] = 2, res=max(0, 1-1)=0
                          i            ---> count[3]  = max(count[3], count[10])+1  => max(3, 2)+1 => 4,   count[10] = 2, res=max(0, 1-1)=0
                              i        ---> count[10] = max(count[10], count[10])+1 => max(2, 2)+1 => 3,   count[10] = 3, res=max(0, 1-1)=0
                                  i    ---> count[3]  = max(count[3], count[10])+1  => max(4, 3)+1 => 5,   count[10] = 3, res=max(0, 1-1)=0
                                     i ---> count[3]  = max(count[3], count[10])+1  => max(5, 3)+1 => 6,   count[10] = 3, res=max(0, 1-1)=0
     */
    public static int maxFrequency2(int[] nums, int k) {
        int[] count = new int[51]; // maxFrequency we achieve upto now if target in count[num]
        int maxFreq = 0; // max frequency of num i.e., not including k
        for (int num : nums) {
            count[num] = Math.max(count[num], count[k]) + 1; // +1 for current num
            maxFreq = Math.max(maxFreq, count[num] - count[k]);
        }
        return count[k] + maxFreq;
    }













    public static int maxFrequencyUsingKadanesAlgorithm2(int[] nums, int k) {
        int n = nums.length;
        int kCount = 0;
        Set<Integer> set = new HashSet<>();

        // Count the number of k's and collect unique numbers
        for (int num : nums) {
            if(num == k) {
                kCount++;
            }
            set.add(num);
        }

        if(kCount == n) {
            return n;
        }

        int res = 0;
        for (int target : set) {
            if(target == k) {continue;} // optional because in kadane() we 1st check (num==k) and then (num==target)
            res = Math.max(res, kadane(nums, k, target));
        }

        return res + kCount;
    }

    private static int kadane(int[] nums, int k, int target) {
        int maxCount = 0, count = 0;
        for (int num : nums) {
            if (num == k) {
                count--;
            }
            else if (num == target) {
                count++;
            }

            if (count < 0) {
                count = 0;
            }
            maxCount = Math.max(maxCount, count);
        }
        return maxCount;
    }
}
