package Algorithms.DivideAndConquer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Most common element is different from majority element
 * In most common element, we need to find the element which occurs the maximum number of times --> highest frequency
 * but in majority element, we need to find the element which occurs more than n/2 times
 * i.e when nums={1, 2, 2, 3, 3, 4, 5, 5, 2};
 * the most common element is 2
 * but we get different result for majority element --- cause, there is no majority element
 *
 * It can be solved using
 *      1) Boyer-Moore Voting Algorithm
 *      2) Divide and Conquer
 *      3) HashMap
 *      4) Sorting
 *      5) Brute Force O(n^2)
 *      6) Brute Force with HashSet
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 12 March 2025
 * @link 169. Majority Element https://leetcode.com/problems/majority-element/
 */
public class MajorityElement {
    public static void main(String[] args) {
        int[] nums =  {1, 2, 2, 3, 3, 4, 5, 5, 2};
        System.out.println("majorityElement using Boyer-Moore Voting Algorithm => " + majorityElement(nums));
        System.out.println("majorityElement using Divide and Conquer => " + majorityElementUsingDivideAndConquer(nums));
        System.out.println("majorityElement using HashMap => " + majorityElementUsingHashMap(nums));
    }

    /**
     * Boyer-Moore Voting Algorithm
     *
     * NOTE: Only one majority element in the array i.e majority element occurs more than n/2. So rest of the nums occurrence will be less than n/2
     *
     * Increase the count if we see majority element or decrease count if we don't see
     * and if count==0 then change the majority element
     *
     * use if(count==0) condition before calculating count. Eg: [3,2,3]
     *
     * i=0
     * [3, 2, 3]
     * majority = 3, count = 1
     *
     * i=1
     * [3, 2, 3]
     * majority = 3, count = 0
     * ---> here we don't change the majority element to 2 even if the count is 0.
     * So, change the majority element value in next iteration if we didn't see 3 again
     *
     * i=2
     * [3, 2, 3]
     * majority = 3, count = 1
     *
     * @TimeComplexity O(N)
     * @SpaceComplexity O(1)
     */
    public static int majorityElement(int[] nums) {
        int majority = nums[0], count = 0;

        for(int num: nums) {
            if(count == 0) majority = num;
            count += num==majority? 1 : -1;
        }

        return majority;
    }



    public static int majorityElement2(int[] nums) {
        int major = nums[0], count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == major) count++;
            else if (count == 0) {
                major = nums[i];
                count = 1;
            }
            else count--;
        }
        return major;
    }




    /**
     * @TimeComplexity O(NlogN)
     * @SpaceComplexity O(N)
     */
    public static int majorityElementUsingDivideAndConquer(int[] nums) {
        return majority(nums, 0, nums.length - 1);
    }

    private static int majority(int[] nums, int left, int right) {
        if (left == right) return nums[left]; // base case

        int mid = left + (right - left) / 2;
        int leftMajor = majority(nums, left, mid);
        int rightMajor = majority(nums, mid + 1, right);

        if (leftMajor == rightMajor) {
            return leftMajor;
        }

        // count the number of times leftMajor occurs in left side and rightMajor in right side
        int leftCount = countInRange(nums, leftMajor, left, right);
        int rightCount = countInRange(nums, rightMajor, left, right);

        return leftCount > rightCount ? leftMajor : rightMajor;
    }

    private static int countInRange(int[] nums, int num, int left, int right) {
        int count = 0;
        for (int i = left; i <= right; i++) {
            if (nums[i] == num) count++;
        }
        return count;
    }





    /**
     * @TimeComplexity O(N)
     * @SpaceComplexity O(N)
     *
     * Here we can use HashMap or Bucket Sort int[] with given range
     */
    @SuppressWarnings("unused")
    public static int majorityElementUsingHashMap(int[] nums) {
        // Map<Integer, Integer> map = Arrays.stream(nums).boxed().collect(Collectors.groupingBy(i->i, Collectors.summingInt(e->1)));
        Map<Integer, Integer> map = new HashMap<>(); // <num, numOccurrences>
        for (int i: nums) map.merge(i, 1, Integer::sum);
        int maxK=0, maxV=Integer.MIN_VALUE; // or java.util.Map.Entry<> majorityEntry = new java.util.AbstractMap.SimpleEntry<>(0, 0); and return majorityEntry.getKey();
        for(Map.Entry<Integer, Integer> e: map.entrySet()) {
            if (e.getValue() > nums.length/2) {
                return e.getKey();
            }
            /**
            or
            if(e.getValue() > maxV) {
                maxV=e.getValue();
                maxK=e.getKey();
            }
            */
        }
        return maxK;
    }





    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(1)
     */
    public static int majorityElementUsingSort(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length/2];
    }

    public static int majorityElementUsingSort2(int[] nums) {
        Arrays.sort(nums);
        int majority = nums[0], count = 0;
        for(int num: nums) {
            if(majority==num) count++;
            else {
                majority = num;
                count = 1;
            }
            if(count > nums.length/2) return majority;
        }
        return majority;
    }



    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(1)
     */
    public static int majorityElementUsingBruteForce(int[] nums) {
        int n = nums.length;
        int majority = nums[0], count = 0;
        for(int num: nums) {
            int currNum = num, currCount = 1;
            for(int i=0; i<n; i++) {
                if(nums[i]==num) currCount++;
            }
            if(currCount > count) {
                count = currCount;
                majority = currNum;
            }
        }
        return majority;
    }


    /**
     * Same as above brute force but use HashSet for O(1) lookup which we already calculated and skip it
     */
    public static int majorityElementUsingBruteForce2(int[] nums) {
        int n = nums.length;
        int majority = nums[0], count = 0;
        Set<Integer> seen = new HashSet<>();
        for(int num: nums) {
            int currNum = num, currCount = 1;

            if(seen.contains(num)) continue;
            seen.add(num);

            for(int i=0; i<n; i++) {
                if(nums[i]==num) currCount++;
            }

            if(currCount > count) {
                count = currCount;
                majority = currNum;
            }
        }
        return majority;
    }
}
