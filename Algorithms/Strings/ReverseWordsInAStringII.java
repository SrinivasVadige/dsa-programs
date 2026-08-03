package Algorithms.Strings;

/**
 * @author Srinivas Vadige srinivas.vadige@gmail.com
 * @since 02 Aug 2026
 * @link 186. Reverse Words in a String II <a href="https://leetcode.com/problems/reverse-words-in-a-string-ii/">LeetCode Link</a>
 * @topics String, Two Pointers
 * @companies ServiceNow(3), Microsoft(2), Amazon(2)
 * @description 186. Reverse Words in a String II
 * You receive a character array containing one or more words separated by single spaces. Rearrange the array so the words appear in reverse order, while keeping each individual word spelled correctly.
 * Do this directly within the original array—do not create another array for storage.
 * No spaces at the beginning or end
 * Each word is separated by exactly one space
 * Characters can be letters or digits
 * Array length: 1 to 100,000
 * @see Algorithms.Strings.ReverseWordsInAString
 */
public class ReverseWordsInAStringII {
    static void main() {
        char[] s = "the sky is blue".toCharArray();
        reverseWords(s);
        System.out.println(new String(s));
    }

    /**

    s = ["t","h","e"," ","s","k","y"," ","i","s"," ","b","l","u","e"]
Output: ["b","l","u","e"," ","i","s"," ","s","k","y"," ","t","h","e"]

    "t","h","e" word order is still same


step 1 : reverse all
["e","u","l","b"," ","s","i"," ","y","k","s"," ","e","h","t"]

step 2 : reverse each word
["b","l","u","e"," ","i","s"," ","s","k","y"," ","t","h","e"]

     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static void reverseWords(char[] s) {
        int n = s.length;
        reverse(s, 0, n-1); // reverse whole s[]

        int l = 0;
        for (int r=0; r<=n; r++) { // reverse each word
            if (r == n || s[r] == ' ') {
                reverse(s, l, r-1);
                l = r+1;
            }
        }
    }
    private static void reverse(char[] s, int l, int r) {
        for (; l<r; l++, r--) {
            char temp = s[l];
            s[l] = s[r];
            s[r] = temp;
        }
    }
}
