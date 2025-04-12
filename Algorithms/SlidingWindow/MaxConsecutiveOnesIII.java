package Algorithms.SlidingWindow;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 12 April 2025
 */
public class MaxConsecutiveOnesIII {
    public static void main(String[] args) {
        int[] nums = {1,1,0,0,1,1,1,0,1,1};
        int k = 2;
        System.out.println("maxConsecutiveOnes : " + longestOnes(nums, k));
    }

    public static int longestOnes(int[] nums, int k) {
        int left = 0, right = 0, max = 0, count = 0;

        while (right < nums.length) {
            if (nums[right] == 0) count++;
            right++;
            while (count > k) {
                if (nums[left] == 0) count--;
                left++;
            }
            max = Math.max(max, right - left);
        }
        return max;
    }











    /**
     * NOT WORKING AS EXPECTED. NEED TO RESEARCH MORE ON THIS.
     *
     * Here I calculated the window that has max 1s
     * And then expand the window to left and right until k becomes 0
     *
     * but
     *
     * [1,1,1,0,1,1,1,0,0,1,1,1,1] and k=1
     *
     * [1,1,1,0,1,1,1,0,0,1,1,1,1] ---> 5
     *                  ^       ^
     * this method will give wrong above output
     *
     * But the right ans is
     * [1,1,1,0,1,1,1,0,0,1,1,1,1] ---> 7
     *  ^           ^
     *
     *
     *
     * And similarly,
     * [1,1,0,0,1,1,1,0,1,1] and k=1
     *
     * if you fill left sides first then you'll get 4
     * but if you fill right side first then you'll get 6
     *
     * We need to check which side is better to fill first
     */
    public int longestOnesMyApproachOld(int[] nums, int k) {
        int n = nums.length;
        if(n==1) return (nums[0]==1)? 1 : (k>0? 1: 0);
        int maxL=0, maxR=0;

        int l=0, r=1;
        while(r<n) {
            System.out.printf("l:%s, r:%s, maxL:%s, maxR:%s\n", l, r, maxL, maxR);
            if(nums[r]==1){
                if(maxR-maxL < r-l) {
                    maxR=r;
                    maxL=l;
                }
                r++;
            }
            else {
                r++;
                l = r;
            }
        }



        while(maxL-1>0 && nums[maxL-1]==0 && k>0) {
            maxL--;
            k--;
            System.out.printf("maxL:%s, maxR:%s\n", maxL, maxR);
        }
        while(maxL-1>0 && nums[maxL-1]==1){
             maxL--;
            System.out.printf("maxL:%s, maxR:%s\n", maxL, maxR);
        }


        while(maxR+1<n && nums[maxR+1]==0 && k>0) {
            maxR++;
            k--;
            System.out.printf("maxL:%s, maxR:%s\n", maxL, maxR);
        }
        while(maxR+1<n && nums[maxR+1]==1) {
            maxR++;
            System.out.printf("maxL:%s, maxR:%s\n", maxL, maxR);
        }

        return maxR-maxL+1;
    }
}
