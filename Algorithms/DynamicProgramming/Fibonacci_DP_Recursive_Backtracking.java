package Algorithms.DynamicProgramming;

/**
 * 
 * <pre>
 * Bigger number to smaller number 
 * 
 * i.e similar to Top Down Memoization but without using extra space fo an array for memoization
 * that is why this approach have repitive calls
 * 
 * Let us imagine DP solution using directed acyclic graph or binary tree
 * 
 * BINARY TREE REPRESENTATION OF RECURCIVE CALLS AS BELOW:
 * 
 *                                   -------------- for fib(8) ---------------
 *                     
 *                                                       8
 *                                                 ______|_______                         
 *                                                /              \
 *                                               7                6_________________
 *                                   ____________|______                      ______|______ 
 *                                  /                   \                   /              \         
 *                                 6                     5                  5                4                                                                                                                                                                          
 *                           ______|_____             ___|___            ___|___            / \
 *                          /            \           /       \          /       \          3   2
 *                         5              4         4         3        4         3        /\  /\
 *                      ___|___          / \       / \       / \      / \       /\       2  1 1 0  
 *                     /       \        3   2     3   2     2   1    3   2     2  1     /\
 *                    4         3      /\  /\    /\   /\   /\       /\  /\    /\       1  0
 *                   / \       /\     2  1 1 0  2  1  1 0  1 0     2 1 1 0   1  0
 *                  3   2     2  1   /\         /\                /\
 *                 /\  /\    /\    1  0        1  0              1  0                                                                                                                                                
 *                2  1 1 0  1  0                                
 *               /\
 *              1  0
 *
 *
 *                
 * NOTE:
 * -> Above, 8 is the root node and root node value is 21. 
 * -> fib(2) is the minimum recursion call cause we return n when n = 0 or n = 1 in base case
 * -> Node is differenent and sum is different
 * -> for leaf nodes(i.e n = 0 or n = 1), sum is the same as node value
*                                               
*                     --------- for fib(6) with sum -----------       
*                                                        
*                                       6 (sum = 8)
*                              _________|________
*                             /                  \                     
*                            5 (sum = 5)          4 (sum = 3)  
*                    ________|____________ 
*                   /                     \   
*                  4(s=3)                  3(s=2) 
*                 / \                     /\ 
*           (s=2)3   2(s=1)         (s=1)2  1 --> leaf
*               /\   /\                 /\   
*         (s=1)2  1  1 0--> leaves     1  0 -----> leaves
*             /\  |-------> leaf  
*            1  0 --------> leaves                
*                                                                                                                                   
 *                                                                                                  
 * <code>" 
 * in simple terms, first all the left values are calculated => i.e 
 * Once the last left node value (eg: 2) is obtained from method base case or previous call
 * calculate right sibling node vlaue (eg: 1) if we have one, and then add both these siblings in nearest parent node (eg: 3)
 * Here, this nearest parent node is nothing but a left node sibling of another right node, 
 * then calculate it's right sibling node and then sum up them in it's nearest parent node and so on ...
 * "</code>
 * </pre>
 * 
 * That's we use recursive approach in graphs and trees
 * <p>
 * <code> 
 * <p>SUGGESUTIONS FOR FINDING DP PATTERNS:
 * <p>1. Visualize few examples in mind & play around with them
 * <p>2. We see repitive sub-problem i.e already calulated expression => So, try to implement a sub-problem which can be used to solve the whole problem using loop or recursion
 * <p>3. Find the relationship between sub-problems i.e fib(5) sub-problem should work for fib(4) and all other sub-problems
 * <p>4. Related hard cases to simpler cases i.e fib(4) value should be used for fib(5) and so on
 * <p>5. Tracking & storing the previuos sub-problems values in each recursive / loop approach to use them & skip if it is already calculated
 * <p>6. Simplest possible input(s) are the base cases. Eg: fib(1), fib(0)
 * <p>7. Topological sort order - Dependency order of sub-problems -- using sort -- if needed
 * <p>8. Try to construct a directed acyclic graph or binary tree to solve the problem.
 * <p>9. Single directionl graph with lot of nodes but not a bi-directional.
 * <p>10. Practice and experience is the key
 * <p>11. First, write the recursive solution and then refactor it to the Top-Down recursive memo or Bottom-Up for loop
 * <p>Egs: 
 * <p>* Fibonacci series or nth finonacci number, 
 * <p>* Longest increasing subsequence
 * <p>* Longest common subsequence
 * <p>* Longest palindromic subsequence
 * <p>* Maze problem
 * <p>* 0/1 Knapsack problem
 * <p>* Matrix chain multiplication
 * <p>* subset sum problem
 * <p>* Number of ways to make coins change, so that the coins will be the given Sum
 * <p>* Minimum coins for sum m
 * <p>* Stacking boxes l,w,h -- all avilable options to stack the boxes like a-box can stack on b-box, c-box can stack on a-box etc i.e it can be a loop too i.e now focus the biggest box as base / root node in each option repeat all created options to find the answer or just keep track of the answer using an extra var like maxLen
 * </code>
 * 
 * 
 * @TimeComplexity: O(2^n) due to repitative calls
 * @see <a href="/DataStructures/BinaryTree.java"> /DataStructures/BinaryTree.java </a>
 * @author Srinivas Vadige
 * @since 06 Oct 2024
 */
public class Fibonacci_DP_Recursive_Backtracking {
    public static void main(String[] args) {
        int n = 6;
        fib(n);
        // for root node or n = 6 then every recursive node sum and leaves will be printed as =>
        // 1 0 1 1 2 1 0 1 3 1 0 1 1 2 5 1 0 1 1 2 1 0 1 3 8 
        // --> where (1 0 1 1 2 1 0 1 3) 1 0 1 1 2 5 are left sub-tree of parent node i.e 6. and (1 0 1 1 2 1 0 1 3) upto n=4 node

        // and for n=8 then every recursive sum (only temp not leaves) will be printed as =>
        // 1 2 1 3 1 2 5 1 2 1 3 8 1 2 1 3 1 2 5 13 1 2 1 3 1 2 5 1 2 1 3 8 21
    }

    public static int fib(int n) {
        if (n == 0 || n == 1) { // base case for leaf nodes
            System.out.println( "leaf node: " + n + " ");
            return n;
        }
        int temp = fib(n - 1) + fib(n - 2); // or int temp = fib(n - 2) + fib(n - 1); 
        System.out.println( "node: " + n + ", " + "sum of " + (n-1) + " and " + (n-2) + " nodes: " + temp); // prints repeatative calls as well
        return temp;
    }
}
