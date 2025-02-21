package Algorithms.BackTracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Let's say we have [1,2,3] list    of numbers
 * Twe need to find all possible permutations of [1,2,3] --> [x, y, z]
 * Now in 1st position, we have 3 choices [1 or 2 or 3] and in 2nd position we have 2 choices [2 or 3] -- as one number is already selected and then in third position we have 1 choice [3]
 * Permutations of [1,2,3] => 3*2*1 => 3! factorial => 6 combinations => [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1]
 * So, permutations of nums[] is nums.length!
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 19 Feb 2025
 */
public class Permutations {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        System.out.println("permute(nums) => " + permute(nums));
        System.out.println("permute2(nums) => " + permute2(nums));
        System.out.println("permute3(nums) => " + permute3(nums));
    }

    /**
     * <pre>

        THOUGHTS:
        ---------
        1) Recursion
        2) When [1, 2, 3, 4] and no duplicates allowed, then we have 4! permutations => 4*3*2*1 => 24 combinations

                                       []
                     __________________|___________________________
                     |                 |             |            |
                    [1]               [2]           [3]          [4]
                _____|___________________________________________________________
                |                           |                                   |
               [1,2]                      [1,3]                               [1,4]
          ______|______          ___________|_________                 _________|_________
         |            |          |                   |                |                  |
    [1,2,3]       [1,2,4]       [1,3,4]            [1,3,2]          [1,4,2]           [1,4,3]
   ____|____      ____|____   _____|_____       _____|_____      ______|______    _______|_______
   |       |      |       |   |         |      |          |      |           |    |             |
[1,2,3,4]  ❌   [1,2,4,3] ❌ [1,3,4,2] ❌  [1,3,2,4]    ❌  [1,4,2,3]     ❌  [1,4,3,2]      ❌


       3) As per above tree, the children are getting decreased and we use dfs to fill the tree
       4) Check if (!subList.contains(currNum)) i.e 1st child is +1 and 2nd child is +2 index numbers. And if nums.length is done then just come to beginning and start filling the remaining child.
       5) In [1,2,4,3] scenario after all the children are finished then add the remaining child
       6) Don't need to image the tree as below binary tree scenario

                                       []
                              __________|________
                              |                 |
                             [1]               []
                         _____|_______________________
                         |                           |
                        [1,2]                       [1]
                   ______|_________          ___________|_________
                  |                |          |                   |
             [1,2,3]             [1,2]       [1,3,4]            [1,3,2]
            ____|____          ____|____   _____|_____       _____|_____
            |       |          |       |   |         |      |          |
         [1,2,3,4] [1,2,3]    [1,2,4]      [1,2] ❌ [1,3,4,2] ❌  [1,3,2,4]    ❌


        7) But instead of starting from [] to [1,2,3,4] combinations, we start with [1,2,3,4] and end up with single number probability [1] [2] [3] [4] like below
        8) Here just remove possible one number from list & calculate the probability for the rest like:

                                                  [1,2,3,4]
               _______________________________________|________________________________________________
               |                          |                                |                           |
            [2,3,4]                    [1,3,4]                         [1,2,4]                      [1,2,3]
     __________|__________       __________|__________           __________|__________        __________|__________
     |         |         |       |         |         |           |         |         |        |         |         |
   [3,4]     [2,4]     [2,3]    [3,4]     [1,4]     [1,3]       [2,4]     [1,4]     [2,4]    [2,3]     [1,3]     [1,2]
  ___|___    ___|___  ___|___  ___|___   ___|___   ___|____    __|___    ___|___  ___|___  ___|___    ___|___   ___|___
 |       |   |      | |     |  |      |  |     |   |      |    |    |    |     |  |     |  |     |    |     |   |     |
[4]     [3] [4]    [2][3]  [3][4]    [3] [4]  [1] [3]    [1]  [4]  [2]  [4]   [1][4]   [2][3]    [2] [3]   [1] [2]   [1]


        9) Now add from bottom to top, if we skipped 3 in [3,4] then 3 to [4] to get [4,3]
        10) We got [4,3] from [2,3,4] by skipping 2. So, add 2 in [4,3] to get [4,3,2]
        11) We got [4,3,2] from [1,2,3,4] by skipping 1. So, add 1 in [4,3,2] to get [4,3,2,1]
        12) So, it'll return [[4, 3, 2, 1], [3, 4, 2, 1], [4, 2, 3, 1], [2, 4, 3, 1], [3, 2, 4, 1], [2, 3, 4, 1], [4, 3, 1, 2], [3, 4, 1, 2], [4, 1, 3, 2], [1, 4, 3, 2], [3, 1, 4, 2], [1, 3, 4, 2], [4, 2, 1, 3], [2, 4, 1, 3], [4, 1, 2, 3], [1, 4, 2, 3], [2, 1, 4, 3], [1, 2, 4, 3], [3, 2, 1, 4], [2, 3, 1, 4], [3, 1, 2, 4], [1, 3, 2, 4], [2, 1, 3, 4], [1, 2, 3, 4]]
        instead of [[1, 2, 3, 4], [1, 2, 4, 3], [1, 3, 2, 4], [1, 3, 4, 2], [1, 4, 2, 3], [1, 4, 3, 2], [2, 1, 3, 4], [2, 1, 4, 3], [2, 3, 1, 4], [2, 3, 4, 1], [2, 4, 1, 3], [2, 4, 3, 1], [3, 1, 2, 4], [3, 1, 4, 2], [3, 2, 1, 4], [3, 2, 4, 1], [3, 4, 1, 2], [3, 4, 2, 1], [4, 1, 2, 3], [4, 1, 3, 2], [4, 2, 1, 3], [4, 2, 3, 1], [4, 3, 1, 2], [4, 3, 2, 1]]

    * </pre>
     */

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> lst = new ArrayList<>();
        if (nums.length == 1) { // base case for single numbers
            lst.add(new ArrayList<>(Arrays.asList(nums[0])));
            return lst;
        }

        for (int i = 0; i < nums.length; i++) {
            int n = nums[i]; // removing the current number i.e to get [2,3,4], [1,3,4], [1,2,4], [1,2,3] from [1,2,3,4]
            int[] remainingNums = new int[nums.length - 1];
            int index = 0; // skip index++ in j!=i scenario
            for (int j = 0; j < nums.length; j++) {
                if (j != i) {
                    remainingNums[index] = nums[j];
                    index++;
                }
            }
            List<List<Integer>> perms = permute(remainingNums); // divide into smaller problems until base case single number
            for (List<Integer> perm : perms) { // add the current number and return the list and this list will be added to it's parent list & so on
                perm.add(n);
            }

            lst.addAll(perms);
        }
        return lst;
    }

    /**
    In same code in python looks like:

    def permute(nums):
        lst = []

        #base case
        if len(nums) == 1:
            return [nums.copy()]

        for i in range(len(nums)):
            n = nums[i]
            remainingNums = nums[:i] + nums[i+1:]
            perms = self.permute(remainingNums)
            for perm in perms:
                perm.append(n)

            lst.extend(perms)

        return lst

    or

    def permute(nums):
        lst = []

        #base case
        if len(nums) == 1:
            return [nums.copy()]

        for i in range(len(nums)):
            n = nums.pop(0)
            perms = self.permute(nums)
            for perm in perms:
                perm.append(n)

            lst.extend(perms)
            nums.append(n)

        return lst
     */




    // this tree is default one i.e converting from [] to [1,2,3,4], not like above #permute()
    public static List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> lst = new ArrayList<>();
        dfs(nums, new ArrayList<>(), lst); // empty path
        return lst;
    }

    private static void dfs(int[] nums, List<Integer> path, List<List<Integer>> lst) {
        if (path.size() == nums.length) { // base case: path is a permutation of nums
            lst.add(new ArrayList<>(path));
            return;
        }

        /**
         * add every possible number but duplicates are not allowed.
         * So, use LinkedHashSet - Set<Integer> set = new LinkedHashSet<>(); lst.add(new ArrayList<>(set)); but to get last element from set is O(n)
         * or path.contains(nums[i])
         */
        for (int i = 0; i < nums.length; i++) {
            if (path.contains(nums[i])) continue; // skip duplicates
            path.add(nums[i]); // add the current number to the path
            dfs(nums, path, lst); // recurse
            path.remove(path.size() - 1); // after calculating [1,2], we need calculate [1,3], [1,4]. So, remove the last element
        }
    }




    // it's same as #permute()
    public static List<List<Integer>> permute3(int[] nums) {
        LinkedList<List<Integer>> result = new LinkedList<List<Integer>>();
        int rSize;
        result.add(new ArrayList<Integer>());
        for (int num: nums) {
            rSize = result. size();
            while (rSize > 0) {
                List<Integer> permutation = result.pollFirst();
                for (int i = 0; i <= permutation.size(); i++) {
                    List<Integer> newPermutation = new ArrayList<Integer>(permutation);
                    newPermutation.add(i, num) ;
                    result.add(newPermutation) ;
                }
            rSize--;
            }
        }
        return result;
    }

}
