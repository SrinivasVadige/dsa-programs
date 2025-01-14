package Algorithms.DynamicProgramming;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;
/**
 * Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.
 *
 * Examples: "()())", "(())", "()(()", "()(())"
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 12 Oct 2024
 */
class  LongestValidParenthesis{

    public static void main(String[] args){
        String s = ")()())";
        // String s = "()";
        System.out.println("longestValidParenthesesUsingTwoLoops: " + longestValidParenthesesUsingTwoLoops(s));
        System.out.println("longestValidParenthesesUsingStack: " + longestValidParenthesesUsingStack(s));
        System.out.println("longestValidParenthesesUsingDp: " + longestValidParenthesesUsingDp(s));
    }

    /**
     * Open params count and close params count approach
     * @TimeComplexity - O(n) as O(n)+O(n) = 2*O(n) = O(n)
     * @SpaceComplexity - O(1)
     */
    public static int longestValidParenthesesUsingTwoLoops(String s) {
        if(s.isEmpty() || s.isBlank()) return 0;
        int maxL=0;
        int open=0, close=0;

        for (char c: s.toCharArray()){
            if(c == '(')
                open++;
            else
                close++;

            if (open == close)
                maxL = Math.max(maxL, 2*close);
            else if(open < close) { // i.e for first right orphan for "())" -- to maintain even width
                open = 0;
                close = 0;
            }
            // else if (l > r) { // l > r lot of left orphans -- need it for "(()", "(()()" but not needed for "()(()" conditions as well --> do we need another for loop after this for loop to move from right to left ??
            //     maxL = Math.max(maxL, 2*r);
            // }
        }

        open=close=0;

        for (int i=s.length()-1; i>-1; i--) {
            if(s.charAt(i) == '(')
                open++;
            else
                close++;

            if (open == close)
                maxL = Math.max(maxL, 2*close);
            else if(open > close) { // i.e for first right orphan for "())", "()(()" -- to maintain even width
                open = 0;
                close = 0;
            }
        }

        return maxL;
    }

    /**
     * <pre>
     * Using Stack with (valid Parenthesis start index - 1) or -1 or 'prev index of validWith' index on the top-most when calculating the maxL
     * Eg1: "()" => initially stack=[-1]; when i=0, then char='(' then we insert 0 in stack in 1st iteration and pop stack in 2nd iteration as char is ')'. So, stack.peek() => -1 then i-(-1) => 1+1 = 2 as maxL 
     * Eg2: "(())" => initial, stack=[-1]; i=0, s=[-1,0]; i=1, [-1,0,1]; i=2, [-1,0]; i=3 [-1] => 3-(-1)=4 as maxL
     * Eg3: ")()" => [-1]; i=0, [0]; i=1, [0,1]; i=2, [0] => 2-0=2 as maxL
     * Eg4: "()((())" => s=[-1] after "()" and s=[-1,2] [-1,2,3] [-1,2,3,4] [-1,2,3] [-1,2] at last
     * From the above Eg4, 2 is the prev index of validWith
     * So, stack top-most element (is valid Parenthesis start index - 1)
     * Note that stack size >= 1 only to store '(' open parentheses indices and pop them when we see counter ')' close parentheses
     * As we want the stack size >0 and "prev index of validWith" store ')' close parentheses indices too only if size == 0
     * And calculate the maxL when current char is ')' and stack size > 0
     * </pre>
     *
     * @TimeComplexity - O(n)
     * @SpaceComplexity - O(n) -- extra space for stack
     */
    public static int longestValidParenthesesUsingStack(String s) {
        if(s.isEmpty() || s.isBlank()) return 0;
        int maxL=0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1); // "()" => 0th i is char '(' then we insert 0 in stack in 1st iteration stack and pop it in 2nd iteration as char is ')'. So, stack.peek() => -1 then i-(-1) => 1+1 = 2 as maxL  
        for (int i=0; i<s.length(); i++) {
            if(s.charAt(i) == '(')
                stack.push(i);
            else { // ')'
                stack.pop();
                if(stack.isEmpty()) // when orphan close Parenthesis i.e after completing width like "())" as 1st ')' removes -1 from [-1,0] and 2nd ')' removes 0 from [0] then stack is empty
                    stack.push(i);
                else // ')' && stack.size() > 0 i.e we have start index of the valid Parenthesis at top-most index
                    maxL = Math.max(maxL, i - stack.peek()); // stack.peek() gives top element i.e last index element
            }
        }
        return maxL;
    }

    /**
     * @Approach Bottom-Up Tabulation DP as we use dp = new int[s.length()]
     *
     * @TimeComplexity - O(n)
     * @SpaceComplexity - O(n)
     */
    public static int longestValidParenthesesUsingDp(String s) {
        if(s.isEmpty() || s.isBlank()) return 0;
        int maxL=0;
        int[] dp = new int[s.length()]; // valid parenthesis length at each char in s if available or 0
        for (int i=1; i<s.length(); i++) { // start from 1 as we check (i-1) and when "()", i=2 dp = [0, 2], note that we can hardcode dp[0]=0 and dp[1]=2 before loop but we calculate maxL in i=1 too. So, it's better to start loop from 1
            // if *)
            if(s.charAt(i) == ')') {
                //if *()
                if(s.charAt(i-1) == '(') {
                    dp[i] = (i>=2 ? dp[i-2] : 0) + 2; // so "*()" i.e i-2 is "*" is previous valid parenthesis start index and current "()" length = 2 and so current length = prevLength + 2. Note that prevLength can be 0 because of invalid parenthesisWidth or i=2 index.
                }
                // by default else will be *))
                // we can easily check if *) is valid Parenthesis or not as we insert in dp array only if that index is a valid Parenthesis. So, dp[i-1] > 0 means valid Parenthesis and dp[i-1] == 0 means not valid Parenthesis
                // i == dp[i-1] means i-1 is end of valid Parenthesis with start index 0
                // i > dp[i-1] > 0 means i-1 is end of valid Parenthesis with start index > 0
                // dp[i-1] == 0 means i-1 has no valid Parenthesis end
                // i != dp[i-1] i.e iDistanceFrom0 != prevIndexLength means dp[i-1] is a valid Parenthesis with start index != 0 (or) not a valid Parenthesis
                // from *)), check if *) is a valid Parenthesis then check if (*)) is valid? i.e prevChar of *)) is '(' or not?
                // i.e to check for (*)) i.e is current start char ')'? => check if prevLength starts from 0 or not?
                // iDistanceFrom0 != prevIndexLength means prevIndexLength doesn't start from 0
                // finally => (*)) ?
                //  *)) && !isPrevIndexWidthStartsWith0 && currWidthStartChar == '('. isPrevIndexWidthStartsWith0 means iDistanceFrom0 != prevIndexLength. And s.charAt(i-dp[i-1]-1) becomes s.charAt(i-1) when dp[i-1] ==0
                else if(i != dp[i-1] && s.charAt(i-dp[i-1]-1) == '(') { // i-dp[i-1] > 0 or i-dp[i-1] != 0 or i != dp[i-1] // So, check if *)) && iDistanceFrom0 != prevIndexLength && char at iDistanceFrom0-prevIndexLength-1 index i.e current Width start char is open?       
                    dp[i] = dp[i-1] + 2 + ((i-dp[i-1])>=2 ? dp[i-dp[i-1]-2] : 0); // extra for "()"(()) --  if(iDistanceFrom0 - prevIndexLength >= 2) dp[prevIndexWidthStartIndex-1]....... prevIndexWidthStartIndex-2 = (i-dp[i-1]-2) we did -2 for ()'(' *) ')' 
                     // currentLength '(*))' = prevLength ('*)') + 2 for '(' *) ')' + if any prev lengths before i-1 ')*' start index scenarios like ()(()) i.e for '()'(()) 
                }
                maxL = Math.max(maxL, dp[i]);
            }
        }
        return maxL;
    }




    // brute force
    public static int longestValidParentheses(String s){
        if(s.isEmpty() || s.isBlank()) return 0;
        int maxLength = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i+1; j <= s.length(); j++) {
                if(s.charAt(i) == '(' && s.charAt(j) == ')' && isValid(s.substring(i, j))) {
                    maxLength = Math.max(maxLength, j-i);
                }
            }
        }
        return maxLength;
    }

    private static boolean isValid(String substring) {
        Map<Character, Long> map = substring.chars().mapToObj(i-> (char)i).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return map.getOrDefault('(', 0l).equals(map.getOrDefault(')', 0l));    
    }













    // -1 +1 Binary Approach, prefixsum and store in array or hashmap
    // not working
    public static int longestValidParentheses2(String s){
        if(s.isEmpty() || s.isBlank()) return 0;
        int maxLength = 0;
        int prefixSum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            int n = s.charAt(i) == '(' ? -1 : 1;

            if(n == 1 && map.size() == 0) {
                continue;
            }

            prefixSum += n;

            if(prefixSum == 0 && n == 1) maxLength = Math.max(maxLength, i+1);

            if (prefixSum != 0 && map.containsKey((0-prefixSum))) {
                maxLength = Math.max(maxLength, i - map.get(-(prefixSum))-1);
            }

            map.putIfAbsent(prefixSum, i); // as we need max length
        }

        return maxLength;
    }

    // this logic only works for ()()()().. but it should also work for (())()()()
    public static int longestValidParenthesesBinaryApproach(String s) {
        if(s.isEmpty() || s.isBlank()) return 0;
        int maxLen = 0;
        int l = -1;
        int prefixSum = 0;
        for(int r=0;  r<s.length(); r++ ){
            int i = ( s.charAt(r)=='(' ) ? -1 : 1 ;
            if(l<0 && i == -1){
                l = r;
                prefixSum = 0;
            }
            if(r>0 && s.charAt(r) == s.charAt(r-1) ){
                prefixSum = 0;
                if(i == -1){
                    l = r;
                    prefixSum += i;
                }
                else
                    l = -1;
                continue;
            }
            System.out.println(l);
            prefixSum += i;
            if (prefixSum == 0 && l > -1 && maxLen< (r-l+1) ){
                maxLen = r-l+1;
            }
        }
        return maxLen;
    }

}