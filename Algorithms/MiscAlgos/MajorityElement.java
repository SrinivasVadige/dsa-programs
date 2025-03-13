package Algorithms.MiscAlgos;

import java.util.HashMap;
import java.util.Map;

/**
 * Most common element is different from majority element
 * In most common element, we need to find the element which occurs the maximum number of times --> highest frequency
 * but in majority element, we need to find the element which occurs more than n/2 times
 * i.e when nums={1, 2, 2, 3, 3, 4, 5, 5, 2};
 * the most common element is 2
 * but we get different result for majority element --- cause, there is no majority element
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 12 March 2025
 */
public class MajorityElement {
    public static void main(String[] args) {
        int[] nums =  {1, 2, 2, 3, 3, 4, 5, 5, 2};
        System.out.println(majorityElement(nums));
    }

    /**
     * Boyer-Moore Voting Algorithm
     * @TimeComplexity O(N)
     * @SpaceComplexity O(1)
     */
    public static int majorityElement(int[] nums) {
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
     * @TimeComplexity O(N)
     * @SpaceComplexity O(N)
     */
    public int majorityElementUsingHashMap(int[] nums) {
        // Map<Integer, Integer> map = Arrays.stream(nums).boxed().collect(Collectors.groupingBy(i->i, Collectors.summingInt(e->1)));
        Map<Integer, Integer> map = new HashMap<>();
        for (int i: nums) map.merge(i, 1, Integer::sum);
        int maxK=0, maxV=Integer.MIN_VALUE;
        for(Map.Entry<Integer, Integer> e: map.entrySet()) {
            if(e.getValue() > maxV) {
                maxV=e.getValue();
                maxK=e.getKey();
            }
        }
        return maxK;
    }
}
