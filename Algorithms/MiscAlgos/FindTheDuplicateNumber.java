package Algorithms.MiscAlgos;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 14 March 2025
 */
public class FindTheDuplicateNumber {
    public static void main(String[] args) {
        int[] nums = {1, 3, 4, 2, 2};
        System.out.println("findDuplicate(nums) => " + findDuplicate(nums));
    }

    public static int findDuplicate(int[] nums) {
        int slow = nums[0], fast = nums[0];
        // Phase 1: Find the intersection point of the two runners
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        // Phase 2: Find the entrance to the cycle
        slow = nums[0];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    public static int findDuplicate2(int[] nums) {
        int tortoise = nums[0];
        int hare = nums[0];

        // Phase 1: Find the intersection point of the two runners
        while(true){
            tortoise = nums[tortoise];
            hare = nums[nums[hare]];
            if(tortoise == hare)
                break;
        }
        // Phase 2: Find the entrance to the cycle
        tortoise = nums[0];
        while(tortoise != hare){
            tortoise = nums[tortoise];
            hare = nums[hare];
        }
        return hare;
    }



    // FYI: 1 <= n <= 105 & 1 number is repeated more than once
    public int findDuplicateUsingArr(int[] nums) {
        int[] arr = new int[nums.length]; // or int[] arr = new int[100001];
        for (int n: nums) {
            if(arr[n] == 0) arr[n]=n;
            else return n;
        }
        return 0;
    }
    // FYI: 1 <= n <= 105 & 1 number is repeated more than once
    public int findDuplicateUsingArr2(int[] nums) {
        boolean[] arr = new boolean[nums.length]; // or boolean[] arr = new boolean[100001];
        for (int n: nums) {
            if(arr[n]) return n;
            else arr[n] = true;
        }
        return 0;
    }



    public int findDuplicateUsingSet(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int n: nums) {
            if(!set.add(n)) return n;
        }
        return 0;
    }
}
