package Algorithms.IntegerArray;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 15 March 2025
 * @link 238. Product of Array Except Self <a href="https://leetcode.com/problems/product-of-array-except-self/">LeetCode link</a>
 * @topics Array, Prefix Sum
 *
 * Note: In problem description, it asked us not to use division operator, but we can still use multiplication operator
 */
public class ProductOfArrayExceptSelf {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4};
        System.out.printf("productExceptSelf => %s\n", Arrays.toString(productExceptSelf(nums)));
        System.out.printf("productExceptSelf using prefix[] and suffix[] => %s\n", Arrays.toString(productExceptSelfUsingPrefixAndSuffixArrays(nums)));
        System.out.printf("productExceptSelf using suffix[] and prevPrefixNum => %s\n", Arrays.toString(productExceptSelfUsingSuffixArrayAndPrevPrefixNum(nums)));



    }

    public int[] productExceptSelfBruteForce(int[] nums) {
        int[] prods = new int[nums.length];
        for(int i=0; i<prods.length; i++){
            prods[i] = 1;
            for(int j=0; j<nums.length; j++){
                if(i==j) continue;
                prods[i] *= nums[j];
            }
        }
        return prods;
    }

    public int[] productExceptSelfUsingDivision(int[] nums) {
        int product = 1;
        int zeros = 0;

        for (int n: nums) {
            if (n==0) {
                zeros++;
                continue;
            }
            product *= n;
        }

        int[] res = new int[nums.length];

        for (int i=0; i<nums.length; i++) {
            if (nums[i]==0) {
                res[i]= zeros>1? 0: product;
            } else {
                if(zeros>0) res[i]=0;
                else res[i] = product/nums[i];
            }
        }
        return res;
    }



        /**
     [1, 1, 2, 6, 24]   ---> prefix
        [1, 2, 3, 4]
        [24,24,12,4, 1] ---> suffix

        Extra Spaces => prefix[], suffix[]
     */
    public static int[] productExceptSelfUsingPrefixAndSuffixArrays(int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n+1];
        int[] suffix = new int[n+1];
        prefix[0] = suffix[n] = 1;

        for(int i=0; i<n; i++) prefix[i+1] = prefix[i]*nums[i];
        for(int i=n-1; i>=0; i--) suffix[i] = suffix[i+1]*nums[i];

        int[] answer = new int[n];
        for(int i=0; i<n; i++) answer[i] = prefix[i]*suffix[i+1];

        return answer;
    }




    /**
         1, 2, 6, 24   ---> prevPrefixNum
        [1, 2, 3, 4]
        [24,24,12,4, 1] ---> suffix

        Extra Spaces => suffix[]
     */
    public static int[] productExceptSelfUsingSuffixArrayAndPrevPrefixNum(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];
        int[] suffix = new int[n+1];
        suffix[n] = 1;

        for(int i=n-1; i>=0; i--) suffix[i] = suffix[i+1]*nums[i];

        int prevPrefixNum = 1;
        for(int i=0; i<n; i++) {
            answer[i] = prevPrefixNum*suffix[i+1];
            prevPrefixNum *= nums[i];
        }
        return answer;
    }



    /**
        Input:  [2,3,4,5]

        prefix: [2,  6, 24,120]
        suffix: [120,60,20,5  ]

        Output: [60,40,30,24]

        output[i]=prefix[i-1] * suffix[i+1]

        Similarly
        Input:  [2,3,0 ,5]
        prefix: [2,6,0 ,0]
        suffix: [0,0,0 ,5]
        Output: [0,0,30,0]

        NOTE:
        1) if "i" is out of bound then use 1
        2) No need to handle the 0's separately
     */
    public static int[] productExceptSelfUsingPrefixAndSuffixArrays2(int[] nums) {
        int n=nums.length;
        int[] prefix = new int[n];
        int[] suffix = new int[n];

        prefix[0]=nums[0];
        for (int i=1; i<n; i++){
            prefix[i] = prefix[i-1] * nums[i];
        }
        suffix[n-1]=nums[n-1];
        for (int i=n-2; i>=0; i--){
            suffix[i] = suffix[i+1] * nums[i];
        }

        int[] res = new int[n];
        for(int i=0; i<n; i++){
            int p = (i==0)? 1:prefix[i-1];
            int s = (i==n-1)?1:suffix[i+1];
            res[i]=p*s;
        }
        return res;
    }





    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] suffix = new int[n];

        for(int i=n-1; i>=0; i--) suffix[i] = (i==n-1? 1 : suffix[i+1])*nums[i];

        int prevPrefixNum = 1;
        for(int i=0; i<n; i++) {
            suffix[i] = prevPrefixNum*(i==n-1? 1 : suffix[i+1]);
            prevPrefixNum *= nums[i];
        }
        return suffix;
    }



    // same as above productExceptSelfUsingPrefixAndSuffixArrays
    public static int[] productExceptSelf2(int[] nums) {
        int n=nums.length;
        int[] prefix = new int[n];
        prefix[0]=nums[0];
        for (int i=1; i<n; i++){
            prefix[i] = prefix[i-1] * nums[i];
        }

        int suffix = 1;
        for(int i=n-1; i>=0; i--){
            int p = (i==0)? 1:prefix[i-1]; // prefix[0]==suffix[1]
            prefix[i]=p*suffix;
            suffix*=nums[i];
        }
        return prefix;
    }



    public static int[] productExceptSelf3(int[] nums) {
        int[] result = new int[nums.length];
        result[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }
        int right = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            result[i] = result[i] * right;
            right = right * nums[i];
        }
        return result;
    }
}
