package Algorithms.BackTracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 07 March 2026
 * @link 77. Combinations <a href="https://leetcode.com/problems/combinations/">LeetCode link</a>
 * @topics Backtracking
 * @companies Meta(4), Google(2), Amazon(16), Microsoft(6), Bloomberg(5), Tiktok(5)

<pre>


                                                [ ]
                                 ________________|______________
                                 [1]         [2]          [3]        [4]
                      ____________|____________
                     [1,2]     [1,3]       [1,4]


    NOTE:
    For backtracking with frequent add/remove at the end, ArrayList almost always outperforms LinkedList in Java
    because CPU cache and memory allocation dominate runtime, not the theoretical O(1)/O(n) complexity.

| Problem Type                           | Choices per step                       | Total possibilities | Complexity         |
| -------------------------------------- | -------------------------------------- | ------------------- | ------------------ |
| Permutations                           | n, n-1, n-2... 1                       | n!                  | O(n * n!)          |
| Subsets / Combinations / DP (all sizes)| 2 per element (include/exclude)        | 2^n                 | O(n * 2^n)         |
| Combinations n choose k like this prob | 1... n-i at step i, only pick k eles   | C(n,k)              | O(k * C(n,k))      |
| Constraint problems (Sudoku, N-Queens) | variable                               | up to exponential   | depends on pruning |

Here in the above table, left side "n *" is for cloning the subLst and right side is for generating the combinations
and C(n,k)) maths formula is n! / k!(n−k)!

</pre>
 */
public class Combinations {
    public static void main(String[] args) {
        int n = 4, k = 2;
        System.out.println("combine(n, k) => " + combine(n, k));
    }

    /**
     * We know that backtracking time complexity is O(n!)
     * @TimeComplexity O(k * n^k) for the worst case and O(k * C(n, k)) for best case where left side k is for cloning the subLst
     * and right side C is the number of combinations generated C(n,k) = (n k) = n! / k!(n−k)!
     * @SpaceComplexity O(k)
     */
    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> lst = new ArrayList<>();
        backtrack(n, k, 1, new ArrayList<>(), lst); // or new LinkedList<>() or new Stack<>() or new ArrayDeque<>()
        return lst;
    }

    private static void backtrack(int n, int k, int start, List<Integer> subLst, List<List<Integer>> lst) {
        if (subLst.size() == k) {
            lst.add(new ArrayList<>(subLst));
            return;
        }
        for (int i = start; i <= n; i++) {
            subLst.add(i);
            backtrack(n, k, i + 1, subLst, lst);
            subLst.remove(subLst.size() - 1); // or subList.removeLast();
        }
    }
}
