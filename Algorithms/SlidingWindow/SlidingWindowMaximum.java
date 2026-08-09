package Algorithms.SlidingWindow;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.stream.IntStream;
/**
    <p>Example 1:

    <p>Input:
    <blockquote><pre>
    int [] nums = [1,3,-1,-3,5,3,6,7]; int k = 3;
    </pre></blockquote>
    Output:<blockquote><pre> [3,3,5,5,6,7] </pre></blockquote>
    <pre>Explanation:
    "Window position"                 "Max"
    ---------------                   -----
    [1  3  -1] -3  -4  5  3  6  7       3
    1  [3  -1  -3] -4  5  3  6  7       3
    1   3 [-1  -3  -4] 5  3  6  7      -1
    1   3  -1 [-3  -4  5] 3  6  7       5
    1   3  -1  -3 [-4  5  3] 6  7       5
    1   3  -1  -3  -4 [5  3  6] 7       6
    1   3  -1  -3  -4  5 [3  6  7]      7
    <pre>
    <table class="striped">
 *      <caption>Methods</caption>
 *      <tbody>
 *        <tr><td>{@link #maxSlidingWindow(int[] nums, int k) 📦maxSlidingWindow(int[] nums, int k)}
 *        <tr><td>{@link #maxSlidingWindowDequeBruteForce(int[] nums, int k) 📦maxSlidingWindowDequeBruteForce(int[] nums, int k)}
 *        <tr><td>{@link #maxSlidingWindowBruteForce(int[] nums, int k) 📦addLast(int[] nums, int k)}
 *      </tbody>
 * </table>

 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 28 Sept 2024,
 *
*/
public class SlidingWindowMaximum {
    public static void main(String[] args) {
        int[] arr = new int[]{1,3,-1,-3,-4,5,3,6,7};
        int k = 3;
        System.out.println("maxSlidingWindow: " + Arrays.toString(maxSlidingWindow(arr, k)));
        System.out.println("maxSlidingWindowDequeBruteForce: " + Arrays.toString(maxSlidingWindowDequeBruteForce(arr, k)));
        System.out.println("maxSlidingWindowBruteForce: " + Arrays.toString(maxSlidingWindowBruteForce(arr, k)));
    }

    /**
     * <p>Use {@code Deque} & pronounce it as <i>DECK</i>
     * <p>Monotonically Decresing Queue -- that is why it is called Deque i.e nums in decreasing/descending order
     * <p>So, place the big numers at the left by removing that small number
     * <p>It is an double ended queue or doble ended queue
     * <p>So, it can be used as Stack or Queue
     *
     *
     * <p>Here {@code [1,3,-1,-3,5,3,6,7] k=4 }
     *
     * <p> If you want to add a bigger int to the deque then poll all the small ints in deque
     * <p> If you want to add a smaller int then add at the last
     * <p> If you want to add the middle int then don't add it
     *
     * <p>in 1st loop {@code [1,3,-1,-3]} ... here we know that {@code 1,-1,-3} are smaller than {@code 3} and they will be small in next window
     * in 2nd loop {@code [3,-1,-3,5]} so, looks like we do not need {@code 1,-1,-3} from the Deque
     * @implNote deq we will have the biggest number at head and do not care about the right side nums until we checking whether the current num with all the dq ints
     * @implNote here deq size is always k. i.e extra space complexity is O(k)
     * @implNote By observing the sliding window pattern, we can see that output arr size is nums.length-k+1
     *
    */
    public static int[] maxSlidingWindow(int[] nums, int k) {

        List<Integer> list = new ArrayList<>();
        Deque<Integer> deq = new ArrayDeque<>();
        int right = 0, left=0;

        while (right < nums.length) { // or for loop as it a constant increment

            // to add a bigger int to the deque then poll all the small ints from right in deque untill it reaches it's descending order position
            while (!deq.isEmpty() && deq.peekLast()<nums[right]) {
                deq.pollLast();
            }
            deq.offerLast(nums[right]); // --- Queue apprach to add bigger number but if while loop removes the small ints. So, basically we offer every right int to deq

            // after we got the required k window, move the left pointer (i.e use pointers distance instad of deq size() )
            if(right - left +1 == k){

                // after k window, add the bigger number to list
                list.add(deq.peekFirst());

                // poll deq if the current left is the biggest deq
                if(deq.peekFirst() == nums[left])
                    deq.pollFirst();

                left++;
            }

            right++;
        }

        return list.stream().mapToInt(i->i).toArray();
    }


    /**
     * Deque Brute force--- pronounce it as DECK
     * This logic is just for understanding how dq works, using this logic we will get TLE
    */
    public static int[] maxSlidingWindowDequeBruteForce(int[] nums, int k) {
        int[] maxInts = new int[nums.length-k+1];
        Deque<Integer> dq = new ArrayDeque<>(); // or LinkedList
        IntStream.range(0, k).forEach(e-> dq.addLast(nums[e]));
        maxInts[0] = Collections.max(dq);
        for(int i=k, index=1; i<nums.length; i++, index++){
            dq.remove(); // deq.pollLast() -- will remove and return the removes element
            dq.add(nums[i]); // or deq.offerLast() -- don't throw error
            maxInts[index] = Collections.max(dq);
        }
        return maxInts;
    }

    /**
     * using this logic we will get TLE
    */
    public static int[] maxSlidingWindowBruteForce(int[] nums, int k) {
        int[] maxNums = new int[nums.length -k + 1];
        for(int i=0; i<nums.length-k+1; i++){
            int[] subArr = new int[k];
            subArr = Arrays.copyOfRange(nums, i, i+k);
            maxNums[i] = Arrays.stream(subArr).max().getAsInt();
        }
        return maxNums;
    }


}
