package Algorithms.BackTracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 21 Feb 2025
 */
public class Subsets {
    public static void main(String[] args) {
        int[] nums = {1,2,3}; // => [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
        System.out.println("subsets(nums) => " + subsets(nums));
    }

    /**
        THOUGHTS:
        ---------
        1) Construct a tree
                                        []
                   _____________________|______________________
                   |                    |                     |
                  [1]                  [2]                   [3]
              _____|_____               |                     |
              |         |               |                   [***]
            [1,2]      [1,3]          [2,3]                  ❌
              |         |               |
              |         |               |
            [1,2,3]  [1,3,2]          [2,3,1]
                        ❌             ❌

        2) As per this tree, the child is >=0
        3) Stop child if you reached i==nums.length or nums[nums.length-1] i.e don't need [3,1]
     */
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>(); // List of Lists
        List<Integer> curr = new ArrayList<>(); // List of Integers
        backtrack(nums, 0, res, curr);
        return res;
    }

    public static void backtrack(int[] nums, int i, List<List<Integer>> res, List<Integer> curr) {
        res.add(new ArrayList<>(curr)); // add the current combination to the results list
        for (; i < nums.length; i++) {
            curr.add(nums[i]); // add the current number to the current combination
            backtrack(nums, i + 1, res, curr); // backtrack with the next number
            curr.remove(curr.size() - 1); // remove the current number from the current combination
        }
    }
}
