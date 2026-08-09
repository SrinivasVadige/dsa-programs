package Algorithms.StackAlgos;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 03 March 2025
 */
public class DecodeString {
    public static void main(String[] args) {
        // String s = "3[a]2[bc]";
        String s = "10[a]2[bc]";
        System.out.println("decodeString: " + decodeString(s));
        System.out.println("decodeString2: " + decodeString2(s));
        System.out.println("decodeStringMyApproach: " + decodeStringMyApproach(s));
        System.out.println("decodeString3: " + decodeString3(s));
        System.out.println("decodeString4: " + decodeString4(s));
    }

    public static String decodeString(String s) {
        Stack<String> stack = new Stack<>();

        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) != ']') stack.push(s.charAt(i) + ""); // Push each character to the stack until first ']'
            else {
                // find subStr
                String subStr = "";
                while(stack.size() > 0) {
                    String c = stack.pop();
                    if (c.equals("[")) break;
                    subStr = c + subStr;
                }

                // find repeat
                int repeat = 0;
                String repeatStr = "";
                while(stack.size() > 0) {
                    String c = stack.pop();
                    if (Character.isDigit(c.charAt(0))) repeatStr = c + repeatStr;
                    else {
                        stack.push(c);
                        break;
                    }
                }
                repeat = repeatStr.length() > 0 ? Integer.parseInt(repeatStr) : 0;

                // repeat subStr repeat times
                String extendedString = "";
                for (; repeat>0; repeat--) extendedString += subStr;

                // push extendedString
                stack.push(extendedString);
            }
        }

        s = "";
        while(stack.size() > 0) s = stack.pop() + s;
        return s;
    }



    public static String decodeString2(String s) {
        Stack<String> stack = new Stack<>(); // Stack to store repeat counts

        int i=0;
        while (i<s.length()) {

            boolean travDone = true;
            for (; i < s.length(); i++) {
                if (s.charAt(i) == ']') {
                    i++; // go to next subStr
                    travDone = false;
                    break;
                }
                stack.push(s.charAt(i) + ""); // Push each character to the stack until first ']'
            }
            // trav done --- to check for cases like "2[abc]3[cd]ef"
            if (i == s.length() && travDone) break;

            String subStr = "";
            while(stack.size() > 0) {
                String c = stack.pop();
                if (c.equals("[")) break;
                subStr = c + subStr;
            }

            int repeat = 0;
            String repeatStr = "";
            while(stack.size() > 0) {
                String c = stack.pop();
                if (Character.isDigit(c.charAt(0)))
                    repeatStr = c + repeatStr;
                else {
                    stack.push(c);
                    break;
                }
            }
            repeat = repeatStr.length() > 0 ? Integer.parseInt(repeatStr) : 0;

            String expandedStr = "";
            while (repeat > 0) {
                expandedStr += subStr;
                repeat--;
            }
            stack.push(expandedStr);
        }

        s = "";
        while(stack.size() > 0) {
            s = stack.pop() + s;
        }
        return s;
    }

    static String res = "";
    public static String decodeStringMyApproach(String s) {
        res = s;
        while(res.contains("[")) expand();
        return res;
    }

    // find last [] window and replace d[s] with expandedStr
    private static void expand() {

        // left pointer
        int l;
        for (l=res.length()-1; l>=0; l--) {
            if(res.charAt(l) == '[') break; // definitely "[" exists and need to expand that right most window first
        }

        // find digit
        String ds = ""; // need to remove "ds i + r i"
        for(int i=l-1; i>=0; i--) {
            if (Character.isDigit(res.charAt(i))) ds = res.charAt(i) + ds;
            else break;
        }
        int d = ds.length()>0?Integer.parseInt(ds):0;

        // subStr
        String subStr = "";
        int r;
        for(r=l+1; r<res.length(); r++) { // l+1, cause l is '['
            if (res.charAt(r) == ']') break;
            subStr += res.charAt(r);
        }

        // expand subStr d times
        String expandStr = "";
        while(d > 0) {
            expandStr += subStr;
            d--;
        }

        // replace d[subStr] with expandStr
        l -= ds.length();
        res = res.substring(0, l) + expandStr + (r+1 >= res.length()? "": res.substring(r+1));
    }





    public static String decodeString3(String s) {
        Stack<Integer> countStack = new Stack<>(); // Stack to store repeat counts
        Stack<StringBuilder> stringStack = new Stack<>(); // Stack to store previous strings
        StringBuilder currentStr = new StringBuilder(); // Stores the current decoded string
        int k = 0; // To store the current repeat count

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                // Build the number for k (handle multi-digit numbers)
                k = k * 10 + (c - '0');
            } else if (c == '[') {
                // Push the current count and string to stacks
                countStack.push(k);
                stringStack.push(currentStr);
                // Reset for next substring
                k = 0;
                currentStr = new StringBuilder();
            } else if (c == ']') {
                // Pop values from stacks
                int repeat = countStack.pop();
                StringBuilder decodedString = stringStack.pop();
                // Repeat the current string and append to previous
                for (int i = 0; i < repeat; i++) {
                    decodedString.append(currentStr);
                }
                // Update current string
                currentStr = decodedString;
            } else {
                // Normal character, add to current string
                currentStr.append(c);
            }
        }

        return currentStr.toString();
    }


    public static String decodeString4(String s) {
        Deque<Integer> countStack = new LinkedList<>();
        Deque<StringBuilder> stringStack = new LinkedList<>();
        StringBuilder currentStr = new StringBuilder();
        int k = 0;

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                k = k * 10 + (c - '0');
            } else if (c == '[') {
                countStack.push(k);
                stringStack.push(currentStr);
                k = 0;
                currentStr = new StringBuilder();
            } else if (c == ']') {
                int repeat = countStack.pop();
                StringBuilder decodedString = stringStack.pop();
                for (int i = 0; i < repeat; i++) {
                    decodedString.append(currentStr);
                }
                currentStr = decodedString;
            } else {
                currentStr.append(c);
            }
        }

        return currentStr.toString();
    }
}
