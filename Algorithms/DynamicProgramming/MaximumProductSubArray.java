package Algorithms.DynamicProgramming;

import java.util.*;

/**
 * find the contiguous sub-array within an array (containing at least one number) which has the largest product.
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 29 Oct 2024
 */
public class MaximumProductSubArray {

    public static void main(String[] args) {

        int[] nums = {2, 3, -2, -2, 1};
        System.out.println(new ArrayList<>(List.of(1,1)));
        System.out.println(maxProduct(nums));
    }

    /**
     * Bottom Up - No memory with currMin and currMax
     * @TimeComplexity: O(n)
     * @SpaceComplexity: O(1)
     */
    public static int maxProduct(int[] nums) {
        int max = nums[0], min = nums[0];
        int res = nums[0];  // max product

        for (int i = 1; i < nums.length; i++) {

            if (nums[i] < 0) {
                int temp = max;
                max = min;
                min = temp; // swap to flip it
            }

            max = Math.max(nums[i], max * nums[i]);
            min = Math.min(nums[i], min * nums[i]);

            res = Math.max(res, max);
        }
        return res;
    }

    public int maxProduct2(int[] nums) {
        int max = nums[0], min = nums[0], ans = nums[0];

        for (int i = 1; i < nums.length; i++) {

            int maxBackup = max;  // store the max because before updating min your max will already be updated

            max = Math.max(Math.max(max * nums[i], min * nums[i]), nums[i]);
            min = Math.min(Math.min(maxBackup * nums[i], min * nums[i]), nums[i]);

            if (max > ans) {
                ans = max;
            }
        }

        return ans;

    }

    public static int maxProduct3(int[] nums) {
        int max=Integer.MIN_VALUE;

        int pro=1; // product
        for(int i=0;i<nums.length;i++){
         pro*=nums[i];
         max = Math.max(max,pro);
         if(pro==0)
             pro=1;
        }

         pro=1;
         for(int i=nums.length-1;i>=0;i--){
             pro*=nums[i];
             max = Math.max(max, pro);
            if(pro==0)
                pro=1;
         }

         return max;
     }

     public int maxProductTwoPointer(int[] nums) {

        int n = nums.length;
        int l=1,r=1;
        int ans=nums[0];

        for(int i=0;i<n;i++){

			//if any of l or r become 0 then update it to 1
            l = l==0 ? 1 : l;
            r = r==0 ? 1 : r;

            l *= nums[i];   //prefix product
            r *= nums[n-1-i];    //suffix product

            ans = Math.max(ans,Math.max(l,r));

        }

        return ans;
    }

     public static int maxProductBruteForce(int[] nums) {

        int maxProd = nums[0];
        int tempProd;

        for(int i=0; i<nums.length; i++){
            tempProd = nums[i];
            maxProd = Math.max(maxProd, tempProd);
            for(int j=i+1; j<nums.length; j++){
                tempProd = tempProd * nums[j];
                maxProd = Math.max(maxProd, tempProd);
            }
        }

        return maxProd;
    }


    /**
     *
     * <pre>
     * NOT WORKING for [-2,3,-4] -> 24
     * calculate each and every possibility
     * 1 max var
     * 1 dp[]
     * how to get max product at specific index?? -- as sub-array must be consecutive ints
     * store max in dp i.e start from l and set each max at each index
     *
     * is -ve +ve logic needed??
     *
     * as [-2,3,-4] -> 24 is not working, can we use prefix sum approach??
     * </pre>
    */
    public static int maxProductMyApproach1(int[] nums) {

        //int[] dp = new int[nums.length];

        int tempMax = nums[0], max = nums[0];

        for (int i=1; i<nums.length; i++){
            System.out.println(max + " " + tempMax);
            // + -
            if ((tempMax > 0 && nums[i] < 0) || max >= tempMax*nums[i] )
                tempMax = nums[i];
            else
                tempMax *= nums[i];

            if(tempMax > max) max = tempMax;
        }

        return max;
    }

}