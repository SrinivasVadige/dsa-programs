package Algorithms.StackAlgos;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 16 April 2025
 */
public class RemovingStarsFromString {
    public static void main(String[] args) {
        String s = "leet**cod*e";
        System.out.println(removeStars(s));
    }

    public static String removeStars(String s) {
        Stack<Character> stack = new Stack<>();

        for (char c: s.toCharArray()) {
            if(c=='*') {
                if(!stack.isEmpty()) stack.pop();
            }
            else stack.push(c);
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) sb.append(stack.pop());
        return sb.reverse().toString();
    }






    public static String removeStars1(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == '*') {
                if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }






    public static String removeStarsUsingDeque(String s) {
       Deque<Character> st = new ArrayDeque<>();
        int len = s.length();

        for(int i = 0 ; i < len ; i++){
            char ch = s.charAt(i);
            if(ch == '*'){
                st.pop();
                continue;
            }
            st.push(ch);
        }

        StringBuffer str = new StringBuffer();
        while(!st.isEmpty()){
            char el = st.pollLast();
            str.append(el);
        }

        return str.toString();
    }







    public String removeStarsMyApproach(String s) {
        Stack<Character> stack = new Stack<>();
        for(char c: s.toCharArray()) stack.push(c);
        StringBuilder sb = new StringBuilder();
        char c = stack.pop();
        int rm=0;
        while(!stack.isEmpty()) {
            // count *s
            while(!stack.isEmpty() && c=='*') {
                rm++;
                c=stack.pop();
            }

            // remove *s chars
            while(rm>0 && c!='*'){
                c= stack.isEmpty()? '0' : stack.pop();
                rm--;
            }

            // fill next non *s
            while(!stack.isEmpty() && c!='*') {
                sb.append(c);
                c=stack.pop();
            }
        }
        if(c!='*' && c !='0') sb.append(c);
        return sb.reverse().toString();
    }
}
