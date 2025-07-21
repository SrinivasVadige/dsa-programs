package Algorithms.TwoPointers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 01 July 2025
 * @link 167. Two Sum II - Input Array Is Sorted <a href="https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/">LeetCode link</a>
 * @topics Array, Two Pointers, Binary Search
 * @companies Google, Amazon, Meta, Bloomberg, Microsoft, Apple, Adobe, Infosys, Oracle, Yandex, EPAM Systems, eBay, Qualcomm, Zomato
 */
public class TwoSum2InputArrayIsSorted {
    public static void main(String[] args) {
        int[] numbers = {2, 7, 11, 15};
        int target = 9;
        System.out.println(Arrays.toString(twoSum(numbers, target)));
    }

    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)

        [1, 3, 4, 5, 7, 10, 11] and target = 9

        [1, 3, 4, 5, 7, 10, 11]
         i                  j  ---> currSum = 1+11 = 12 > target = 9
         i               j     ---> currSum = 1 + 10 = 11 > target = 9
         i           j         ---> currSum = 1 + 7 = 8 < target = 9
            i        j         ---> currSum = 3 + 7 = 10 > target = 9
            i    j             ---> currSum = 3 + 5 = 8 < target = 9
              i  j             ---> currSum = 4 + 5 = 9 == target = 9

     */
    public static int[] twoSum(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            int currSum = numbers[left] + numbers[right];
            if (currSum == target) {
                return new int[] {left + 1, right + 1};
            } else if (currSum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[] {-1, -1};
    }


    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(1)
     */
    public static int[] twoSumUsingBinarySearch(int[] numbers, int target) {
        int n = numbers.length;
        for(int i=0; i<n; i++) {
            int need = target - numbers[i];
            int j = binarySearch(numbers, i+1, n-1, need); // Arrays.binarySearch(arr, startInclusive, endInclusive, target);
            if(j>i && j<n && numbers[j] == need) {
                return new int[]{i+1, j+1};
            }
        }
        return new int[0];
    }
    private static int binarySearch(int[] numbers, int l, int r, int need) {
        while(l<=r) {
            int m = l + (r-l)/2;
            if(numbers[m]<need) {
                l=m+1;
            } else {
                r=m-1;
            }
        }
        return l;
    }






    /**
     Same as traditional two sum
     */
    public int[] twoSumUsingMap(int[] numbers, int target) {
        int n = numbers.length;
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0; i<n; i++) {
            int need = target - numbers[i];
            if(map.containsKey(need)) {
                return new int[]{map.get(need)+1, i+1};
            }
            map.put(numbers[i], i);
        }

        return new int[2];
    }





    /**
     [2, 7, 11, 15]
      i

     */
    public int[] twoSumBruteForce(int[] numbers, int target) {
        int n = numbers.length;
        for(int i=0; i<n; i++) {
            int need = target - numbers[i];
            for(int j=i+1, k=n-1; numbers[j]<=need && numbers[k]>=need; j++, k--) {
                int secI = -1;
                if(numbers[j]==need) {
                    secI = j;
                } else if (numbers[k]==need) {
                    secI = k;
                }

                if(secI > -1) {
                    return new int[]{i+1, secI+1};
                }
            }
        }
        return new int[2];
    }
}
