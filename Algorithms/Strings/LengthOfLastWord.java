package Algorithms.Strings;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 06 April 2025
 * @link 58. Length of Last Word <a href="https://leetcode.com/problems/length-of-last-word/">LeetCode link</a>
 * @topics String, String methods

    split(" ") vs split("\\s+") vs split("\\W+")

    split(" ") -> splits on whitespace
    split("\\s+") -> splits on one or more spaces
    split("\\W+") -> splits on one or more non-word characters

    Example 1:
    Input: s = "    Hello World, ok ok    "
    s.split(" ")    =>  String[8] { "", "", "", "", "Hello", "World,", "ok", "ok" }
    s.trim().split(" ") String[4] { "Hello", "World,", "ok", "ok" }
    s.split("\\s")  =>  String[8] { "", "", "", "", "Hello", "World,", "ok", "ok" }
    s.split("\\s+") =>  String[5] { "", "Hello", "World,", "ok", "ok" }
    s.split("\\W")  =>  String[9] { "", "", "", "", "Hello", "World", "", "ok", "ok" }
    s.split("\\W+") =>  String[5] { "", "Hello", "World", "ok", "ok"
    s.trim()        => "Hello World, ok ok"
    s.strip()       => "Hello World, ok ok" ---> Java 11

    NOTE: "a_b c".split("\\W") => String[2]{"a_b", "c"} --> cause _ is a word character. so, a_b is a word

    JAVA REGEX META CHARACTERS:
    ----------------------------
    1. Characters with Special Meaning:
    . (Dot) Matches Any Character
    \ (Backslash) Escape Character
    ^	Start of line/input
    $	End of line/input
    *	0 or more repetitions
    +	1 or more repetitions
    ?	0 or 1 repetition, or makes quantifier lazy
    []	Character class
    ()	Capturing group
    {}	Quantifier: exact, min, or min,max

    2. Predefined Character Classes:
    \s -> whitespace [\t\n\x0B\f\r]
    \S -> non-whitespace
    \w -> word character
    \W -> non-word character
    \d -> digit character [0-9]
    \D -> non-digit character [^0-9]
    \b -> word boundary
    \B -> non-word boundary

 */
public class LengthOfLastWord {
    public static void main(String[] args) {
        String s = "Hello World";
        System.out.printf("lengthOfLastWord => %d", lengthOfLastWord(s));
    }

    public static int lengthOfLastWord(String s) {
        String[] words = s.split(" ");  // or s.trim().split(" "); or s.split("\\s+"); or s.split("\\W");
        return words[words.length - 1].length();
    }




    public int lengthOfLastWordMyApproach(String s) {
        int res = 0;
        for(int i=s.length()-1; i>=0; i--) {
            if(s.charAt(i) == ' ') {
                if(res == 0) continue;
                else break;
            }
            res++;
        }
        return res;
    }





    public static int lengthOfLastWord2(String s) {
        int length = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ' ') {
                if (length != 0) return length;
            } else {
                length++;
            }
        }
        return length;
    }



    public static int lengthOfLastWord3(String s) {
        int length = 0;
        int end = s.length() - 1;
        while(end >= 0 && s.charAt(end) == ' ') end--;
        for(int i=0; i<=end; i++) {
            if(s.charAt(i) == ' ') {
                length = 0;
            } else {
                length++;
            }
        }
        return length;
    }




    public static int lengthOfLastWord4(String s) {
        return s.trim().length() - s.trim().lastIndexOf(' ') - 1;
    }




    public static int lengthOfLastWord5(String s) {
        int i = s.length() - 1;
        while (i >= 0 && s.charAt(i) == ' ') i--; // skip trailing spaces
        int j = i;
        while (j >= 0 && s.charAt(j) != ' ') j--; // find beginning of last word
        return i - j;
    }




    public static int lengthOfLastWord6(String s) {
        int i = s.length() - 1;
        while (i >= 0 && s.charAt(i) == ' ') i--; // skip trailing spaces
        return s.substring(i + 1).length();
    }

}
