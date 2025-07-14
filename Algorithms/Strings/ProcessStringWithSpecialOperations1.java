package Algorithms.Strings;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 14 July 2025
 * @link 3612. Process String with Special Operations I <a href="https://leetcode.com/problems/process-string-with-special-operations-i/">LeetCode link</a>
 * @topics String
 * @companies amazon

 Example 1:
    Input: s = "a#b%*"
    Output: "ba"
    Explanation:
    i	s[i]	Operation	                Current result
    0	'a'	    Append 'a'	                "a"
    1	'#'	    Duplicate result            "aa"
    2	'b'	    Append 'b'	                "aab"
    3	'%'	    Reverse result	            "baa"
    4	'*'	    Remove the last character	"ba"
    Thus, the final result is "ba".
 */
public class ProcessStringWithSpecialOperations1 {
    public static void main(String[] args) {
        String s = "a#b%*";
        System.out.println("processStr => " + processStr(s));
    }


    public static String processStr(String s) {
        StringBuilder sb = new StringBuilder();

        for(char c : s.toCharArray()) {
            if(c == '#') {
                sb.append(sb);
            } else if(c == '%') {
                sb.reverse();
            } else if(c == '*') {
                if (sb.length()>0) sb.deleteCharAt(sb.length()-1);
                // sb.isEmpty() is introduced in Java 15
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
