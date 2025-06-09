package Algorithms.IntegerArray;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 16 March 2025
 * @link 189. Rotate Array https://leetcode.com/problems/rotate-array/
 */
public class RotateArray {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6};
        int k = 2;
        rotate(nums, k);
        System.out.println("nums after k rotations => " + Arrays.toString(nums));
        nums = new int[]{1,2,3,4,5,6};
        k = 2;
        rotateMyApproach(nums, k);
        System.out.println("nums after k rotations => " + Arrays.toString(nums));
    }

    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     *
     * Approach:
     * 1. Reverse the whole array
     * 2. Reverse first k elements
     * 3. Reverse remaining elements
     *
     * Example:
     * Input: nums = [1,2,3,4,5,6], k = 2
     * Output: [5,6,1,2,3,4]
     *
     * step1: [6,5,4,3,2,1]
     * step2: [5,6,4,3,2,1]
     * step3: [5,6,1,2,3,4]
     */
    public static void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        reverse(nums, 0, n - 1); // reverse whole array
        reverse(nums, 0, k - 1); // reverse first k
        reverse(nums, k, n - 1); // reverse remaining
    }

    public static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            // swap start & end
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            // decrease the width
            start++;
            end--;
        }
    }







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     *
     * same like {@link #rotateMyApproachNew(int[], int)}, but here use do-while loop instead of recursion

        k=3
        currNum = nums[0] = 1
        currI = 0
        count = 0


        [1, 2, 3, 4, 5, 6, 7] - given
        [1, 2, 3, 1, 5, 6, 7] - 4 - here we popped 4 and pushed 1
        [1, 2, 3, 1, 5, 6, 4] - 7
        [1, 2, 7, 1, 5, 6, 4] - 3
        [1, 2, 7, 1, 5, 3, 4] - 6
        [1, 6, 7, 1, 5, 3, 4] - 2
        [1, 6, 7, 1, 2, 3, 4] - 5
        [5, 6, 7, 1, 2, 3, 4] - 1

        if(count==n) stop rotating;

        and we need to take care of circular loop, so if(start==currI) then we see a circular loop, then stop this do-while and proceed with next startI
     */
    public void rotate2(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        if (k == 0) return;

        int count = 0; // total elements moved
        for (int startI = 0; count < n; startI++) { // it's "count<n" but not "start<n"
            int currI = startI;
            int currNum = nums[startI];

            do {
                int nextI = (currI + k) % n;
                int nextNum = nums[nextI];
                nums[nextI] = currNum;
                currNum = nextNum;
                currI = nextI;
                count++;
            } while (startI != currI); // one cycle done and if(start==currI) then we see a circular loop, then stop this do-while and proceed with next startI till count==n
        }
    }







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     *
     * same like {@link #rotateMyApproachNew(int[], int)} and {@link #rotate2(int[], int)}, but here use recursion instead of do-while to track the circular loop and count
     */
    public void rotate3(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        if (k == 0 || n <= 1) return;

        int count = 0;
        int start = 0;

        while (count < n) {
            count = placeExitNum(nums, k, nums[start], start, start, count);
            start++; // start next cycle from the next index
        }
    }

    private int placeExitNum(int[] nums, int k, int exitNum, int exitI, int startI, int count) {
        int n = nums.length;
        int nextI = (exitI + k) % n;
        int nextNum = nums[nextI];
        nums[nextI] = exitNum;
        count++;

        if (nextI == startI) {
            // cycle completed
            return count;
        }

        return placeExitNum(nums, k, nextNum, nextI, startI, count);
    }



    /**
    * @TimeComplexity O(n^2)
    * @SpaceComplexity O(1)
     */
    public void rotateBruteForce(int[] nums, int k) {
        int n=nums.length;
        k=k%n;
        for(; k>0; k--) {
            int lastEle = nums[n-1];
            for (int i=n-1; i>0; i--){
                // not swap, just assign i-1 -> i
                nums[i]=nums[i-1];
            }
            nums[0]=lastEle;
        }
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public void rotateUsingNSpace(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        int[] rotated = new int[n];

        for (int i = 0; i < n; i++) {
            rotated[(i + k) % n] = nums[i];
        }

        for (int i = 0; i < n; i++) {
            nums[i] = rotated[i];
        }
    }






    /**
    * @TimeComplexity O(n+k)
    * @SpaceComplexity O(k)
     */
    public void rotateUsingKSpace(int[] nums, int k) {
        int n = nums.length;
        k=k>n?k%n:k;
        int[] kEles = new int[k];
        // collect kEles
        for (int i=n-1, ki=k; i>=n-k && i>=0; i--) {
            kEles[--ki]=nums[i];
        }
        // shift starting n-k eles to right
        for (int i=n-1; i>k-1 && i-k>=0; i--) {
            nums[i]=nums[i-k];
        }
        // fill the kEles in starting
        for (int i=0; i<k; i++){
            nums[i]=kEles[i];
        }
    }




    /**
     * It's failing when "n == 2*k" or n % k == 0 or gcd(n, k) > 1
     * k=2 and n=4
     * k=2 and n=6
     * so for these cases, we will reach the same element again before we trav all elements ---> CIRCULAR LOOP, so check {@link #rotateMyApproach(int[], int)}

        k=3
        currNum = nums[0] = 1
        currI = 0
        count = 0


        [1, 2, 3, 4, 5, 6, 7] - given
        [1, 2, 3, 1, 5, 6, 7] - 4 - here we popped 4 and pushed 1
        [1, 2, 3, 1, 5, 6, 4] - 7
        [1, 2, 7, 1, 5, 6, 4] - 3
        [1, 2, 7, 1, 5, 3, 4] - 6
        [1, 6, 7, 1, 5, 3, 4] - 2
        [1, 6, 7, 1, 2, 3, 4] - 5
        [5, 6, 7, 1, 2, 3, 4] - 1

        if(count==n+1) return;

        if we already trav all the elements then stop rotating
     */
    public void rotateMyApproachOld(int[] nums, int k) {
        int currNum = nums[0];
        int currI = 0;
        k = k % nums.length;
        moveCurrKTimes(nums, k, currNum, currI, 0);
    }
    private void moveCurrKTimes(int[] nums, int k, int currNum, int currI, int count) {
        int n = nums.length;
        count++;
        if(count == n+1) return; // EDGE CASE
        int nextI = (currI + k)%n;

        int nextNum = nums[nextI];
        nums[nextI] = currNum;
        moveCurrKTimes(nums, k, nextNum, nextI, count);
    }

    /**
     * this is also failing for gcd(n, k) > 1
     */
    public void rotateMyApproachOld2(int[] nums, int k) {
        int n = nums.length;
        int exitNum = nums[0];
        int exitI = 0;
        k = k % n;
        placeExitNum2(nums, k, exitNum, exitI, 0);
    }
    private void placeExitNum2(int[] nums, int k, int exitNum, int exitI, int count) {
        int n = nums.length;
        count++;
        if(count == n+1) return;
        int nextI = (exitI + k)%n;
        int nextNum = nums[nextI];
        nums[nextI] = exitNum;
        if(n == 2*k && (count)%k==0) {
            nextI = (nextI+1) % n;
            nextNum = nums[nextI];
            System.out.printf("exitI:%s, nextI:%s, count:%s --------- \n", exitI, nextI, count);
            System.out.println(Arrays.toString(nums));
            placeExitNum2(nums, k, exitNum, nextI, count);
        } else {
            System.out.printf("exitI:%s, nextI:%s, count:%s\n", exitI, nextI, count);
            System.out.println(Arrays.toString(nums));

            placeExitNum2(nums, k, nextNum, nextI, count);
        }
    }





    /**
     * Check for infinite circular loop
     *
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)



        [1, 2, 3, 4, 5, 6, 7]
        [1, 2, 3, 1, 5, 6, 7] - 4 exit
        [1, 2, 3, 1, 5, 6, 4] - 7
        [1, 2, 7, 1, 5, 6, 4] - 3
        [1, 2, 7, 1, 5, 3, 4] - 6
        [1, 6, 7, 1, 5, 3, 4] - 2
        [1, 6, 7, 1, 2, 3, 4] - 5
        [5, 6, 7, 1, 2, 3, 4] - 1

        same like above {@link #rotateMyApproachOld} but here we handled the infinite circular loop issue when gcd(n, k) > 1

        when k=3;
        shift every ele to +3 position
        and t is tempHold
        [1,2,3,4,5,6,7] t=
        [1,2,7,4,5,6,7] t=3
        [1,2,7,4,5,3,7] t=6
        [1,6,7,4,5,3,7] t=2
        [1,6,7,4,2,3,7] t=5
        [5,6,7,4,2,3,7] t=1
        [5,6,7,1,2,3,7] t=4
        [5,6,7,1,2,3,4] t=4


        when k=2 & n=4 ---> n=2*k ???
        [-1,-100,3,99] t=
        [-1,99,3,99] t=-100
        [-1,99,3,-100] t=99
        [-1,99,3,-100] t=99

        when k=2 & n=6 ---> n%k==0 ???
        [1,2,3,4,5,6] t= , count=0
        [1,6,3,4,5,6] t=2, count=1
        [1,6,3,2,5,6] t=4, count=2 --> for every (n%k==0 && count%k==0), we can stop
        [1,6,3,2,5,4] t=6
        [1,6,3,2,5,4] t=6
        [1,6,3,6,5,4] t=2 -----> INFINITE loop

        when k=2 & n=6 ---> n%k==0 ???
        [1,2,3,4,5,6] t= , count=0
        [1,6,3,4,5,6] t=2, count=1
        [1,6,3,2,5,6] t=4, count=2 --> for every (n%k==0 && count%k==0), we can stop
        [1,6,3,2,5,4] t= --- currI==> 5; count=3, t=, (n%k==0 && k-1!=0 && count%(k-1)==0)
        -------------
        [1,6,3,2,5,4] --- currI-1==> currI=4; count=3, temp=1
        -------------
        [5,6,3,2,5,4] t=1, count=4
        [5,6,1,2,5,4] t=3, count=5
        [5,6,1,2,3,4] t= --- currI==> 4, count=6
        -------------
        count==n==6 ---> stop

        SIMILARLY
        when k=3 & n=6 ---> n%k==0 ???
        [1,2,3,4,5,6] t= , count=0
        [1,2,6,4,5,6] t=3, count=1
        [1,2,6,4,5,3] t=, count=2 --> for every (n%k==0 && count%k==0), we can stop
        -------------
        [1,2,6,4,5,3] t= --- currI==> 5; count=2, t=, (n%k==0 && k-1!=0 && count%(k-1)==0)
        [1,5,6,4,5,3] --- currI-1==> currI=4; count=3, t=2
        -------------
        [1,5,6,4,2,3] t=1, count=4
        -------------
        [1,5,6,4,2,3] t= --- currI==> 4; count=4,
        [4,5,6,4,2,3] --- currI-1==> currI=3; count=5, t=1
        [4,5,6,1,2,3] t=, count=6
        -------------
        count==n==6 ---> stop


        so, use dummy=n-1 variable to track if the nextI == dummy
        because, if there is a circular loop, then the loop must again come to n-1, n-2 ... so on

        even k=4 & n=6 will be looped


        so, instead of (n%k==0 && count != n && dummy==nextI) just use (count != n && dummy==nextI)
     */
    static int startI; // i For tracking the CircularLoop; or just pass this as param to the moveCurrNumKTimes() method
    public static void rotateMyApproachNew(int[] nums, int k) {
        int n=nums.length;
        k=k%n;
        if(n==1 || k==0 || k==n) return;
        startI=0;

        int currNum = nums[0];
        int currI = 0;
        moveCurrNumKTimes(nums, k, currI, currNum, 0); // or while(count<n){} loop
    }
    private static void moveCurrNumKTimes(int[] nums, int k, int currI, int currNum, int count) {
        int n = nums.length;
        if (count == n) return;

        int nextI = (currI + k) % n; // or (currI+k > n-1)? Math.abs(currI+k-n):currI+k;
        int nextNum = nums[nextI];
        nums[nextI]=currNum;
        count++;

        if(count!=n && startI==nextI) { // only if there is a circular loop
            currI = (nextI+1) % k;
            currNum = nums[currI];
            nextI = (currI+k) % n; // or (currI+k > n-1)? Math.abs(currI+k-n):currI+k;
            nextNum = nums[nextI];
            nums[nextI]=currNum;
            count++;
            startI=currI; // definitely next circular loop will happen at startI-1 as n-1,n-2 ...
        }
        moveCurrNumKTimes(nums, k, nextI, nextNum, count);
    }






    /**
     * same as above but naming conventions are different like startI==dummy, temp == curr
     */
    static int dummy; // i For tracking the CircularLoop; or just pass this
    public static void rotateMyApproach(int[] nums, int k) {
        int n=nums.length;
        if(n==1 || k==0) return;
        if (k>n) k=k%n;
        dummy=n-1;

        int temp = nums[k-1];
        nums[k-1]=nums[n-1];
        moveKTimes(nums, k, k-1, temp, 1); // or while(count<n){} loop
    }
    private static void moveKTimes(int[] nums, int k, int currI, int temp, int count) {
        int n = nums.length;
        if (count == n ) return;

        int nextI = (currI + k) % n; // or (currI+k > n-1)? Math.abs(currI+k-n):currI+k;
        int nextTemp = nums[nextI];
        nums[nextI]=temp;
        count++;
        if(count!=n && dummy==nextI) { // only if there is a circular loop
            currI=nextI-1;
            temp = nums[currI];
            nextI = (currI + k) % n; // or (currI+k > n-1)? Math.abs(currI+k-n):currI+k;
            nextTemp = nums[nextI];
            nums[nextI]=temp;
            count++;
            dummy--; // definitely next circular loop will happen at dummy-1 as n-1,n-2 ...
        }
        moveKTimes(nums, k, nextI, nextTemp, count);
    }
}
