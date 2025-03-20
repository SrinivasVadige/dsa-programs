package Algorithms.MiscAlgos;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 20 March 2025
 *
 * 1 <= nums.length <= 105
 * -2^31 <= nums[i] <= 2^31 - 1
 *
 * But we can calculate up to n-1 positive numbers because ideal +ve nums must start from 1.
 * And even if we have -ve nums or +ve nums the consecutive nums cannot form up to Integer.MAX_VALUE
 * Definitely at some point this consecutive series will break
 */
public class FirstMissingPositive {
    public static void main(String[] args) {
        int[] nums = {3,4,-1,1};
        System.out.println("firstMissingPositiveUsingBuckets(nums) => " + firstMissingPositiveUsingBuckets(nums));
        System.out.println("firstMissingPositive(nums) => " + firstMissingPositive(nums));
    }

    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int firstMissingPositiveUsingBuckets(int[] nums) { // -- array buckets or hashMap buckets
        int n=nums.length;
        boolean[] isPresent = new boolean[n+1]; // the consecutive series will break before n-1
        for(int e: nums) {
            if(e>0 && e<=n) isPresent[e]=true;
        }
        int max=Integer.MIN_VALUE;
        for(int i=1; i<isPresent.length;i++){
            if(!isPresent[i]) return i;
            max=Math.max(max, i);
        }
        return max+1; // after the isPresent loop
    }

    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int firstMissingPositiveUsingHashSet(int[] nums) {
        int n = nums.length;
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (num > 0 && num <= n) set.add(num);
        }
        for (int i = 1; i <= n; i++) {
            if (!set.contains(i)) return i;
        }
        return n + 1;
    }


    /**
     *
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     *
     * Here just use the given nums array as HashSet
     *
     * i.e just focus only on (1 to n) positive numbers --> (0 to n-1) indexes in given arr
     *
     * place this if(num > 0 && num <= n) then place this num in nums[num-1] index
     *
     */
    public static int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            while (x > 0 && x <= n && nums[x - 1] != x) { // x in range and nextX != x --> isDuplicate? otherwise infinite loop will occur
                swap(nums, i, x-1);
                x = nums[i];
            }
            // or while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) swap(nums, i, nums[i]-1);
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) return i + 1;
        }
        return n + 1;
    }
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     *
     * Here the -ves in nums are useless, so mark them as 0 in first step
     * Consider 1 to n-1 as range just like above firstMissingPositive() method
     *
     * if you find a +ve num in range, then mark (num-1) index as -ve but don't change (i) & (num-1) indexes values
     * that means num is present in nums array with O(1) time complexity
     */
    public int firstMissingPositive2(int[] nums) {
        int n = nums.length;
        for(int i=0; i<n; i++) if(nums[i]<0) nums[i]=0; // prepare the array for marking visited

        for(int i=0; i<n; i++) {
            int val = Math.abs(nums[i]);
            if(val>=1 && val<=n) { // in range
                int currValPositionVal = nums[val-1];
                // here -ve number in ith index means it is confirmed that the (i+1)th number is present in nums arr
                if(currValPositionVal>0) nums[val-1] *= -1; // if +ve, then mark that position -ve --> val number is present in arr
                else if(currValPositionVal==0) nums[val-1] = -1*(n+1); // mark -ve & out-of-range i.e val present & no need to check this index val for loop
                // no else as it is marked & confirmed that index number is present in nums arr
            }
        }
        // FINALLY: 0 & +ve nums means, that num not found in arr

        for(int i=1; i<=n; i++) { // within range
            if(nums[i-1]>=0) return i; // i-1 index means i number & it has to be -ve
        }
        return n+1;
    }



    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(1)
     */
    public int firstMissingPositiveUsingSort(int[] nums) {
        nums = Arrays.stream(nums).boxed().collect(Collectors.toSet()).stream().mapToInt(Integer::intValue).toArray(); // remove duplicates
        Arrays.sort(nums);
        int res=0;
        for(int e:nums){
            if(e>0) {
                if(++res == e) continue; // consecutive increments
                else return res; // break of consecutive increment series
            }
        }
        return res+1; // after the loop completion, the next missing small +ve num will be res+1 or sortedNums[n-1]+1
    }




    public int firstMissingPositiveTLE(int[] nums) {
        int n = nums.length;
        for (int num: nums) {
            int futureNum = nums[num-1];
            while (num > 0 && num <= n) {
                nums[num-1]=num;
                num=futureNum;
            }
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) return i + 1;
        }
        return n + 1;
    }

    public int firstMissingPositiveNotWorking(int[] nums) {
        int n = nums.length;
        for (int num: nums) {
            while (num > 0 && num <= n) {
                nums[num-1]=num;
                if(num-1>=0 && nums[num-1]>0) {
                    int futureNum = nums[num-1];
                    num=futureNum;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) return i + 1;
        }
        return n + 1;
    }
}