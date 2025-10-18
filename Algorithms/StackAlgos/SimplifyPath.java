package Algorithms.StackAlgos;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 17 Oct 2025
 * @link 71. Simplify Path <a href="https://leetcode.com/problems/simplify-path/">LeetCode link</a>
 * @topics String, Stack
 * Description -> Convert "Absolute Path" to "Canonical Path"
 */
public class SimplifyPath {
    public static void main(String[] args) {
        String path = "/home/////user/Documents/../Pictures";
        System.out.println("simplifyPath using stack 1 -> " + simplifyPathUsingStack1(path));
        System.out.println("simplifyPath using stack 2 -> " + simplifyPathUsingStack2(path));
        System.out.println("simplifyPath using stack 3 -> " + simplifyPathUsingStack3(path));
        System.out.println("simplifyPath using deque -> " + simplifyPathUsingDeque(path));
    }

    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static String simplifyPathUsingStack1(String path) {
        Stack<String> stack = new Stack<>();
        String[] paths = path.split("/+"); // [, home, user, Documents, .., Pictures]
        for (int i=1; i<paths.length; i++) {
            String p = paths[i];
            if (p.equals("..")) {
                if (!stack.isEmpty()) stack.pop();
            } else if (!p.equals(".")) {
                stack.push(p);
            }
        }
        return "/" + String.join("/", stack);
    }



    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static String simplifyPathUsingStack2(String path) {

        Stack<String> stack = new Stack<>();

        for (String dir : path.split("/")) { // [, home, , , , , user, Documents, .., Pictures]
            if (dir.isEmpty() || dir.equals(".")) {
                continue;
            } else if (dir.equals("..")) {
                if(!stack.isEmpty()) stack.pop();
            } else {
                stack.push(dir);
            }
        }

        if (stack.isEmpty()) return "/";

        StringBuilder sb = new StringBuilder();
        for (String dir : stack) {
            sb.append("/").append(dir);
        }

        /*
        // or
        while (!st.isEmpty()) {
            sb.insert(0, "/" + st.pop());
        }
         */

        return sb.toString();
    }




    public static String simplifyPathUsingStack3(String path) {
        Stack<String> stack = new Stack<>();
        StringBuilder cur = new StringBuilder();

        // Append '/' at the end to make sure the last component is processed
        path = path + "/";

        for (char c : path.toCharArray()) {
            if (c == '/') {
                if (!cur.isEmpty()) {
                    String dir = cur.toString();
                    if (dir.equals("..")) {
                        if (!stack.isEmpty()) stack.pop();
                    } else if (!dir.equals(".")) {
                        stack.push(dir);
                    }
                    cur.setLength(0); // reset current
                }
            } else {
                cur.append(c);
            }
        }

        return "/" + String.join("/", stack);
    }








    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static String simplifyPathUsingDeque(String path) {
        Deque<String> deque = new ArrayDeque<>();
        String[] segments = path.split("/");

        for (String segment : segments) {
            if (segment.equals("..")) {
                if (!deque.isEmpty()) {
                    deque.removeLast(); // pop from stack
                }
            } else if (segment.isEmpty() || segment.equals(".")) { // skip empty or current directory
                continue;
            } else {
                deque.addLast(segment); // push to stack
            }
        }

        return "/" + String.join("/", deque);
    }
}
