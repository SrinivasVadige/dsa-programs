package Algorithms.BackTracking;

import java.util.*;

/**
 * Let's say we have [1,2,3] list    of numbers
 * Twe need to find all possible permutations of [1,2,3] --> [x, y, z]
 * Now in 1st position, we have 3 choices [1 or 2 or 3] and in 2nd position we have 2 choices [2 or 3] -- as one number is already selected and then in third position we have 1 choice [3]
 * Permutations of [1,2,3] => 3*2*1 => 3! factorial => 6 combinations => [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1]
 * So, permutations of nums[] is nums.length!
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 19 Feb 2025
 * @link 46. Permutations <a href="https://leetcode.com/problems/permutations/">LeetCode link</a>
 * @topics Array, Backtracking
 * @companies Google(10), Amazon(5), Meta(4), Microsoft(2), LinkedIn(5), Bloomberg(3), Goldman Sachs(2), TikTok(7), Apple(6), Oracle(4), Booking.com(4), Adobe(2), Epic Systems(2), Uber(2), Cisco(2), Arista Networks(2), American Express(2)
 */
public class Permutations {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        System.out.println("permute1(nums) => " + permute1(nums));
        System.out.println("permute2(nums) => " + permute2(nums));
        System.out.println("permute3(nums) => " + permute3(nums));
    }




    /**
     * @TimeComplexity O(n * n!), where left side n is for cloning the subSet and right side n! is for permutations
     * @SpaceComplexity O(n), where n is the subSet size but the actual space complexity is O(n * n!) for storing all permutations
     <pre>
                                                                [ ]
                            _____________________________________|_____________________________________
                            [1]                                 [2]                                   [3]
                     ________|________
                    [1,2]          [1,3]
                      |              |
                   [1,2,3]        [1,3,2]
     </pre>
     */
    static List<List<Integer>> result = new ArrayList<>();
    public static List<List<Integer>> permute1(int[] nums) {
        backtrack(nums, new LinkedHashSet<>());
        return result;
    }

    private static void backtrack(int[] nums, Set<Integer> subSet) {
        if (subSet.size() == nums.length) {
            result.add(new ArrayList<>(subSet));
            return;
        }

        for (int num : nums) {
            if (subSet.add(num)) {
                backtrack(nums, subSet);
                subSet.remove(num);
            }
        }
    }
    
    
    
    
    
    static List<List<Integer>> res = new ArrayList<>();
    public static List<List<Integer>> permute2(int[] nums) {
        backtrack(nums, 0);
        return res;
    }
    private static void backtrack(int[] nums, int startI) {
        // base case: all positions fixed
        if (startI == nums.length) {
            List<Integer> perm = new ArrayList<>();
            for (int num : nums) perm.add(num);
            res.add(perm);
            return;
        }

        for (int i = startI; i < nums.length; i++) {
            swap(nums, startI, i);      // choose
            backtrack(nums, startI + 1);
            swap(nums, startI, i);      // undo
        }
    }
    private static void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
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

    public static List<List<Integer>> permute3(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        if (nums.length == 1) { // base case for single numbers
            list.add(new ArrayList<>(Arrays.asList(nums[0])));
            return list;
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
            List<List<Integer>> perms = permute3(remainingNums); // divide into smaller problems until base case single number
            for (List<Integer> perm : perms) { // add the current number and return the list and this list will be added to it's parent list & so on
                perm.add(n);
            }

            list.addAll(perms);
        }

        return list;
    }

    /**
    In same code in python looks like:

    def permute(nums):
        list = []

        #base case
        if len(nums) == 1:
            return [nums.copy()]

        for i in range(len(nums)):
            n = nums[i]
            remainingNums = nums[:i] + nums[i+1:]
            perms = self.permute(remainingNums)
            for perm in perms:
                perm.append(n)

            list.extend(perms)

        return list

    or

    def permute(nums):
        list = []

        #base case
        if len(nums) == 1:
            return [nums.copy()]

        for i in range(len(nums)):
            n = nums.pop(0)
            perms = self.permute(nums)
            for perm in perms:
                perm.append(n)

            list.extend(perms)
            nums.append(n)

        return list
     */




    // this tree is default one i.e converting from [] to [1,2,3,4], not like above #permute()
    public static List<List<Integer>> permute4(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        dfs(nums, new ArrayList<>(), list); // empty path
        return list;
    }

    private static void dfs(int[] nums, List<Integer> path, List<List<Integer>> list) {
        if (path.size() == nums.length) { // base case: path is a permutation of nums
            list.add(new ArrayList<>(path));
            return;
        }

        /**
         * add every possible number but duplicates are not allowed.
         * So, use LinkedHashSet - Set<Integer> set = new LinkedHashSet<>(); list.add(new ArrayList<>(set)); but to get last element from set is O(n)
         * or path.contains(nums[i])
         */
        for (int i = 0; i < nums.length; i++) {
            if (path.contains(nums[i])) continue; // skip duplicates
            path.add(nums[i]); // add the current number to the path
            dfs(nums, path, list); // recurse
            path.remove(path.size() - 1); // after calculating [1,2], we need calculate [1,3], [1,4]. So, remove the last element
        }
    }




    // it's same as #permute()
    public static List<List<Integer>> permute5(int[] nums) {
        LinkedList<List<Integer>> result = new LinkedList<List<Integer>>();
        int rSize;
        result.add(new ArrayList<>());
        for (int num: nums) {
            rSize = result. size();
            while (rSize > 0) {
                List<Integer> permutation = result.pollFirst();
                for (int i = 0; i <= permutation.size(); i++) {
                    List<Integer> newPermutation = new ArrayList<>(permutation);
                    newPermutation.add(i, num) ;
                    result.add(newPermutation) ;
                }
            rSize--;
            }
        }
        return result;
    }

}
