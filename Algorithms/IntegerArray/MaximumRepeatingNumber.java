package Algorithms.IntegerArray;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Frequent element in an array or maximum repeating number in an array
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 13 Oct 2024
 */
class MaximumRepeatingNumber{

    public static void main(String args[]){
        int[] arr = new int[]{3,1,2,3,2,3,3,4,5};
        System.out.println("maxRepeatingBruteForce: " + maxRepeatingBruteForce(arr) );
        System.out.println("maxRepeatingUsingSort: " + maxRepeatingUsingSort(arr) );
        System.out.println("maxRepeatingUsingHashMap: " + maxRepeatingUsingHashMap(arr) );
    }

    /**
     * Brute Force
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(1)
     */
    public static int maxRepeatingBruteForce(int[] nums) {
        int maxRep = 0;
        int maxCount = 0;        
        for (int i = 0; i < nums.length; i++) {
            int tempCount = 0;
            for (int j = 0; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    tempCount++;
                }
            }
            if (maxCount < tempCount) {
                maxCount = tempCount;
                maxRep = nums[i];
            }
        }
        return maxRep;
    }


    /**
     * Using Sorting
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(1)
     */
    public static int maxRepeatingUsingSort(int[] nums) {
        Arrays.sort(nums);
        int maxRep = nums[0];
        int maxCount = 1;
        int tempCount = 1;

        for (int i = 1; i < nums.length - 1; i++) {
            if (nums[i-1] == nums[i]) {
                tempCount++;
            }
            if(maxCount < tempCount) {
                maxCount = tempCount;
                maxRep = nums[i];
                tempCount = 1;
            }
        }        
        return maxRep;
    }


    /**
     * Using HashMap
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    @SuppressWarnings("unused")
    public static int maxRepeatingUsingHashMap(int[] nums) {
        int maxRep = 0;
        int maxCount = 0;
        Map<Integer, Integer> map = Arrays.stream(nums).boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e->1)));
        for(Map.Entry<Integer, Integer> e: map.entrySet()) {
            if(maxCount < e.getValue()) {
                maxCount = e.getValue();
                maxRep = e.getKey();
            }
        }
        return maxRep;
    }

}