package Algorithms.SlidingWindow;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 13 April 2025
 */
public class LongestSubArrayOf1sAfterDeletingOneElement {
    public static void main(String[] args) {
        int[] nums = {1, 1, 0, 1, 1, 0, 1, 1, 1};
        System.out.println("longestSubArray(nums) => " + longestSubArray(nums)); // Output: 5
        System.out.println("longestSubArray2(nums) => " + longestSubArray2(nums)); // Output: 5
        System.out.println("longestSubArrayMyApproach(nums) => " + longestSubArrayMyApproach(nums)); // Output: 5
        System.out.println("longestSubArrayMyApproach2(nums) => " + longestSubArrayMyApproach2(nums)); // Output: 5

    }
    public static int longestSubArray(int[] nums) {
        int max =0, start=0, zeroCounter=0;
       for(int end=0; end < nums.length; end++){
           if(nums[end] == 0){
               zeroCounter++;
           }
           while(zeroCounter > 1){
               if(nums[start]==0) {
                   zeroCounter--;
               }
               start++;
           }
           max = Math.max(max, end-start);
       }
       return max;
   }




    public static int longestSubArray2(int[] nums) {
        int countZero = 1;
        int l = 0, r = 0;

        for(; r<nums.length; r++) {
            countZero -= (nums[r] == 0) ? 1 : 0;
            if(countZero < 0){
                countZero += nums[l] == 0 ? 1 : 0;
                l++;
            }
        }
        return r - l; // r-l+1-1 --> cause delete one ele
    }






    public static int longestSubArray3(int[] nums) {
        int countZero = 0;
        int cur = 0;
        int max = 0;

        for(int i = 0; i < nums.length; i++){
            countZero += nums[i] == 0 ? 1 : 0;

            if(countZero > 1){
                countZero -= nums[cur++] == 0 ? 1 : 0;
            }
            max = Math.max(i - cur , max);
        }
        return max;
    }




    public static int longestSubArray4(int[] nums) {
        int curr = 0, prev = 0, ans = 0, zeros = 0;
        for (int num : nums) {
            if (num == 0) {
                zeros++;
                ans = Math.max(ans, curr + prev);
                prev = curr;
                curr = 0;
            } else {
                curr++;
            }
        }
        ans = Math.max(ans, curr + prev);
        return (zeros == 0) ? ans - 1 : ans;
    }






    /**
        GIVEN:
        ------
        1) Remove one ele for sure --> cause [1,1,1] outputs 2 not 3
        2) In best case: Remove one '0' and return the longest 1's sequence


        PATTERNS:
        ---------
        1) Ignore 0 in first and last index position
        2) One series -> maintain two windows l1,r1 and l2,r2
        3) Consider two windows if only one '0' in between those two

     */
    public static int longestSubArrayMyApproach(int[] nums) {
        int l1,r1,l2,r2,n=nums.length, max;
        l1=r1=l2=r2=max=0;

        // FIRST WINDOW l1
        while(l1<n && nums[l1]==0) l1++;
        if(l1==n) return 0; // no 1s found
        r1=l1;

        while(r1<n && r2<n) {
            // FIRST WINDOW r1
            while(r1<n && nums[r1]==1) r1++;
            r1--; // get back to 1 or n-1 position
            max = Math.max((l1>0||r1<n-1)?r1-l1+1:r1-l1, max); // --> delete one ele


            // NUM OF 0s
            int zeros = 0;
            int i=r1+1;
            while(i<n && nums[i]==0) {
                i++;
                zeros++;
            }
            i--;
            if(r1==n-1 || i==n-1) break; // reached the end
            if(zeros != 1) { // need only one zero, if not, skip this window and trav next series
                l1=r1=i+1;
                continue;
            }


            // SECOND WINDOW
            l2=r2=i+1;
            while(r2<n && nums[r2]==1) r2++;
            r2--; // get back to 1 or n-1 position
            max = Math.max(r2-l2+r1-l1+2, max);

            // CONVERT THIS SECOND WINDOW TO FIRST WINDOW FOR NEXT SERIES
            l1=l2;
            r1=r2;
        }

        return max;
    }







    public static int longestSubArrayMyApproach2(int[] nums) {
        int l1,r1,l2,r2,n=nums.length, max, rep;
        l1=r1=l2=r2=max=rep=0;

        while(r1<n && r2<n) {
            rep++;
            // FIRST WINDOW
            if (rep==1) {
                while(l1<n && nums[l1]==0) l1++;
                r1=l1;
                while(r1<n && nums[r1]==1) r1++;
                r1--; // get back to 1 or n-1 position
            }
            max = Math.max(l1>0?r1-l1+1:r1-l1, max); // not (r1-l1+1) --> cause delete one ele

            // System.out.printf("l1:%s, r1:%s, l2:%s, r2:%s, max:%s\n", l1, r1, l2, r2, max);

            if(l1==r1 && l1==n-1 && rep==1) return 0; // no 1s found
            if(r1==n-1) break;

            // NUM OF 0s
            // here even if I used if instead of while, it works -->
            // as the above if(rep==1) condition skips to calculate l1 & r1 again and comes to this if condition
            // and skips zeros until nums[i]==1
            //
            int zeros = 0;
            int i=r1+1;
            if(i<n-1 && nums[i]==0) {
                i++;
                zeros++;
            }
            i--;

            if(zeros != 1) {
                l1=r1=i+1;
                continue;
            }

            // SECOND WINDOW
            l2=r2=i+1;
            while(r2<n && nums[r2]==1) r2++;
            r2--; // get back to 1 or n-1 position
            max = Math.max(r2-l2+r1-l1+2, max);
            // System.out.printf("l1:%s, r1:%s, l2:%s, r2:%s\n", l1, r1, l2, r2);

            l1=l2;
            r1=r2;
        }

        return max;
    }

















   public int longestSubArray5(int[] nums) {
    boolean deleted = false;
    boolean wasDeleted = false;
    int subArr = 0;
    int maxArr = 0;
    int left = 0;

    for (int right = 0; right < nums.length; right++) {
        if (nums[right] == 1) {
            subArr++;
        } else {
            if (!deleted) {
                wasDeleted = true;
                deleted = true;
                left = right;
            } else {
                if (subArr > maxArr) {
                    maxArr = subArr;
                }
                subArr = 0;
                right = left;
                deleted = false;
            }
        }
    }
    maxArr = Math.max(maxArr, subArr);
    if (!wasDeleted) {
        maxArr--;
    }
    return maxArr;
}
}
