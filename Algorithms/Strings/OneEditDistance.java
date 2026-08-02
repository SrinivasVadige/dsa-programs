package Algorithms.Strings;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 02 Aug 2026
 * @link 161. One Edit Distance <a href="https://leetcode.com/problems/one-edit-distance/">LeetCode link</a>
 * @topics Two Pointers, String
 * @companies Yandex(23), Meta(3), Google(2), Stripe(2)
 * @description One Edit Distance
 * Determine whether two strings differ by exactly one edit.
 * A valid edit is one of the following operations performed once:
 * Insert one character
 * Remove one character
 * Replace one character with a different character
 * Return true only when exactly one operation can transform one string into the other. Identical strings are not valid because they require zero edits.
 * Key observations:
 * If the lengths differ by more than one, return false.
 * If the strings are equal, return false.
 * Scan both strings with two pointers.
 * At the first different character:If lengths are equal, treat it as a replacement and advance both pointers.
 * If one string is longer, advance only the pointer of the longer string, representing an insertion/deletion.
 * A second mismatch means more than one edit is required.
 * @see Algorithms.DynamicProgramming.EditDistance
 */
public class OneEditDistance {
    public static void main(String[] args) {
        String s = "abc";
        String t = "ac";
        System.out.println("isOneEditDistance Using TwoPointers 1" + isOneEditDistanceUsingTwoPointers1(s, t));
        System.out.println("isOneEditDistance Using TwoPointers 2" + isOneEditDistanceUsingTwoPointers2(s, t));
        System.out.println("isOneEditDistance Using TwoPointers 3" + isOneEditDistanceUsingTwoPointers3(s, t));
        System.out.println("isOneEditDistance Using TwoPointers 4" + isOneEditDistanceUsingTwoPointers4(s, t));
    }

        /**


        ab
         l
        acb
         r

        if(lc ==  rc) l++, r++

        if (lc != rc) then

        1. lc == r+1 c -> r++ --- insert in l
        2. l+1 c == rc -> l++ --- delete in l
        3. if (lc != rc && lc != r+1 c && l+1 c != rc) -> l++, r++ --- replace in l

     * @TimeComplexity O(m+n)
     * @SpaceComplexity O(1)
     */
    public static boolean isOneEditDistanceUsingTwoPointers1(String s, String t) {
        int edits = 0;

        int l = 0, r = 0, m = s.length(), n = t.length();

        while ((l < m || r < n) && edits <= 1) {
            char lc = 0, rc = 0;
            if (l<m) lc = s.charAt(l);
            if (r<n) rc = t.charAt(r);

            if (lc != rc) {
                edits++;
                if (r+1 < n && lc == t.charAt(r+1) && m+1 == n) r++;        // insert
                else if (l+1 < m && s.charAt(l+1) == rc && m-1 == n) l++;   // delete
                else {                                          // replace
                    l++;
                    r++;
                }
            } else {
                l++;
                r++;
            }
        }

        return edits == 1;
    }


    /**
     * @TimeComplexity O(m+n)
     * @SpaceComplexity O(1)
     */
    public static boolean isOneEditDistanceUsingTwoPointers2(String s, String t) {
        int edits = 0;

        int l = 0, r = 0, m = s.length(), n = t.length();

        while ((l < m || r < n) && edits <= 1) {
            char lc = 0, rc = 0;
            if (l<m) lc = s.charAt(l);
            if (r<n) rc = t.charAt(r);

            if (lc != rc) {
                edits++;
                if (r+1 < n && lc == t.charAt(r+1) && m+1 == n) r++;        // insert
                else if (l+1 < m && s.charAt(l+1) == rc && m-1 == n) l++;   // delete
            }

            l++;
            r++;
        }

        return edits == 1;
    }






    /**
     * @TimeComplexity O(Max(m,n))
     * @SpaceComplexity O(1)
     */
    public static boolean isOneEditDistanceUsingTwoPointers3(String s, String t) {
        int m = s.length();
        int n = t.length();

        if (m > n) return isOneEditDistanceUsingTwoPointers3(t, s); // set smaller string as s
        else if (n - m > 1) return false;

        for (int i = 0; i < m; i++) {
            if (s.charAt(i) != t.charAt(i)) {
                if (m == n) {
                    return s.substring(i + 1).equals(t.substring(i + 1)); // is rest of strings equal
                } else {
                    return s.substring(i).equals(t.substring(i + 1)); // is rest of strings equal from i in s
                }
            }
        }

        return (m + 1 == n);
    }






    /**
     * @TimeComplexity O(Max(m,n))
     * @SpaceComplexity O(1)
     */
    public static boolean isOneEditDistanceUsingTwoPointers4(String s, String t) {
        int m = s.length(), n = t.length();

        if (Math.abs(m - n) > 1 || s.equals(t)) return false;

        boolean foundInequality = false;
        int l = 0, r = 0;

        while (l < m && r < n) {

            if (s.charAt(l) != t.charAt(r)) {
                if (foundInequality) return false;
                foundInequality = true;

                if (m < n) {
                    l--; // net effect after l++ below: keep l, advance r
                } else if (m > n) {
                    r--; // net effect after r++ below: advance l, keep r
                }
            }

            l++;
            r++;
        }

        return true;
    }
}
