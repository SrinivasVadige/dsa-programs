package Algorithms.Strings;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeSet;

/**
<pre>
At Amazon, a user owns a unique tool called the
"Parentheses Perfection Kit." This kit contains different types of parentheses, each with a specific efficiency rating. The goal is to create a balanced sequence of parentheses by adding zero or more parentheses from the kit to maximize the sequences total EfficiencyScore. The EfficiencyScore of a sequence is the sum of the efficiency ratings of the parentheses used from the kit.


A sequence is considered balanced if it has an equal number of opening '(' and closing )' parentheses, with each opening parentheses properly matched with a closing one in the correct order (i.e., circular balance). For instance, sequences like (()), (), and (()()) are balanced, while sequences like ), ((), and (()(())) are not.


You are given an initial parentheses sequence represented by the string s, along with a Parentheses Perfection Kit containing different types of parentheses in the form of the string kitParentheses and their respective efficiency ratings in the efficiencyRatings array (both of size m). The EfficiencyScore of the original strings is initially 0. You can use any number of unused parentheses from the kit to create the final sequence, as long as the final sequence remains balanced.


The task is to determine the maximum possible EfficiencyScore that can be achieved for the resulting balanced sequence


Note: It is guaranteed that the sequence can be made balanced by adding zero or more parentheses from the kit.


Sample Case 0
Sample Input 0
STDIN Function
() → s = "() "
(()) → kitParentheses = "(())"
4 → efficiencyRatings[] size m=4
4 → efficiencyRatings = [4, 2, -3, -3]
2
-3
-3

# Example 1
s = "()"
kit = "(())"
efficiency_rating = [4, 2, -3, -3]
print(find_max_to_balance(s, kit, efficiency_rating))  # Output: 1

# Example 2
s = ")(("
kit = ")(()))"
efficiency_rating = [3,4,2,-4,-1,-3]
print(find_max_to_balance(s, kit, efficiency_rating))  # Output: 6


Explanation
If the user used the 0th indexed and 2nd indexed parentheses from the bag and add them to the start and end of the
string respectively, then the final balanced sequence will be "(0)" with a total EfficiencyScore of 4 + (-3) = 1. There are
no other combinations of adding parentheses that can yield a balanced sequence with total EfficiencyScore greater than 1, Hence return 1 as answer.
</pre>


 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 Jan 2025
 * @see {@link Algorithms.DynamicProgramming.LongestValidParenthesis}
 */
public class ParenthesesPerfectionKit {
    public static void main(String[] args) {
        String s = "())()()";
        String kit = "()()()";
        int[] efficiencyRating = { 5,5,5,5,5,5 };
        // String s = "))(()";
        // String kit = "(())";
        // int[] efficiencyRating = { 4, 2, -3, -1 };
        System.out.println("findMaxToBalance using PriorityQueue My Approach : " + findMaxToBalanceUsingPriorityQueueMyApproach(s, kit, efficiencyRating));
        // System.out.println("findMaxToBalance using PriorityQueue : " + findMaxToBalanceUsingPriorityQueue(s, kit, efficiencyRating));
        // System.out.println("findMaxToBalance using TreeSet : " + findMaxToBalanceUsingTreeSet(s, kit, efficiencyRating));
    }

    /**
        s = ")(("
        kit = ")(()))"
        efficiency_rating = [3,4,2,-4,-1,-3]
        print(find_max_to_balance(s, kit, efficiency_rating))  # Output: 6

        ")(", ")((", ")(()))" scenarios

		the map of kit and efficiency_rating looks like:
        )    (    (    )    )    )
        3    4    2   -4   -1   -3

	   which is same as
        (    (
		4    2
		*

		)    )    )    )
        3   -1   -3   -4
        *    *

	   i.e we need 4 + 3 + (-1) => 6
     */
    // TODO: validate open & close count with different test cases
    public static int findMaxToBalanceUsingPriorityQueueMyApproach(String s, String kit, int[] efficiencyRating) {
        int open, close; open=close=0; // '(' open orphan count and ')' close orphan count
        // check if s is balanced or not AND // get orphanOpenCount & orphanCloseCount
        for (String s1 : s.split("")){
            if (s1.equals("(")) {
                open++;
            }
            else if (s1.equals(")")) {
                if (open == 0) close++;
                else open--; // decrease '(' open orphan count when it becomes balanced
            }
        }
        if (open == 0 && close == 0) return 1;

        // Prepare PQs
        PriorityQueue<Integer> openPQ = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> closePQ = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 0; i < kit.length(); i++) {
            if(kit.charAt(i)=='(')
            openPQ.add(efficiencyRating[i]);
            else closePQ.add(efficiencyRating[i]);
        }

        int max = 0;
        while(open>0) {
            max += openPQ.isEmpty()?0:openPQ.poll();
            open--;
        }
        for (;close>0;close--)
            max += closePQ.isEmpty()?0:closePQ.poll();

        return max;
    }


    public static int findMaxToBalanceUsingPriorityQueue(String s, String kit, int[] efficiencyRating) {

        Stack<Character> tmpStack = new Stack<>();
        PriorityQueue<Integer> openPQ = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> closePQ = new PriorityQueue<>(Comparator.comparingInt(a -> -a));

        for (int i = 0; i < kit.length(); i++) {
            if (kit.charAt(i) == '(')
                openPQ.add(efficiencyRating[i]);
            else
                closePQ.add(efficiencyRating[i]);
        }

        int max = 0;
        // Loop over String to find all needed to balance the string
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ')' && tmpStack.isEmpty())
                if (!openPQ.isEmpty()) max += openPQ.poll(); // We need opening parenthesis
            else if (s.charAt(i) == ')' && tmpStack.peek() == '(')
                tmpStack.pop();
            else tmpStack.push(s.charAt(i));
        }

        while (!tmpStack.isEmpty()) {
            if (tmpStack.pop() == '(')
                if (!closePQ.isEmpty()) max += closePQ.poll();
            else
                if (!openPQ.isEmpty()) max += openPQ.poll();
        }

        while (!openPQ.isEmpty() && !closePQ.isEmpty())
            max = Math.max(max, openPQ.poll() + closePQ.poll() + max);

        return max;
    }










    /*
        TreeSet can't handle duplicates
     */
    @SuppressWarnings("unused")
    public static int findMaxToBalanceUsingTreeSet(String s, String kit, int[] efficiencyRating) {
        int open, close; open=close=0;
        // check if s is balanced or not
        for (String s1 : s.split("")){
            if (s1.equals("(")) {
                open++;
            }
            else if (s1.equals(")")) {
                if (open == 0)
                    close++;
                else
                    open--; // sub parenthesis balanced
            }
        }
        if (open == close) return 1;

        int max = 0;
        String needed = (open>close)? "(" : ")";

        // Prepare TreeSet
        Map<String, TreeSet<Integer>> map = new HashMap<>(); // or we can use "new PriorityQueue<>(Comparator.reverseOrder());"
        for (int i = 0; i < kit.length(); i++) {
            String key = kit.charAt(i)+"";
            if(map.containsKey(key)){
                map.get(key).add(efficiencyRating[i]);
            } else {
                TreeSet<Integer> set = new TreeSet<>(Comparator.reverseOrder());
                set.add(efficiencyRating[i]);
                map.put(key, set);
            }
        }

        while(open>0) {
            max += (map.containsKey("(")&&!map.get("(").isEmpty()) ? map.get("(").pollFirst():0;
            open--;
        }
        while(close>0) {
            max += (map.containsKey(")")&&!map.get(")").isEmpty()) ? map.get(")").pollFirst():0;
            close--;
        }

        return max;
    }
}
