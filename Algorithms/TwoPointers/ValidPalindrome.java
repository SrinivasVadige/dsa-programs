package Algorithms.TwoPointers;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 29 June 2025
 * @link 125. Valid Palindrome <a href="https://leetcode.com/problems/valid-palindrome/">LeetCode link</a>
 * @topics String, Two Pointers
 */
public class ValidPalindrome {
    public static void main(String[] args) {
        String s = "abba";
        System.out.println(isPalindromeMyApproach(s));
    }

    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public boolean isPalindrome(String s) {
        int l = 0, r = s.length()-1;
        while (l<r) {
            while (!Character.isLetterOrDigit(s.charAt(l))) l++; // or s.charAt(l) < 'a' || s.charAt(l) > 'z' || s.charAt(l) < 'A' || s.charAt(l) > 'Z'
            while (!Character.isLetterOrDigit(s.charAt(r))) r--; // or s.charAt(r) < 'a' || s.charAt(r) > 'z' || s.charAt(r) < 'A' || s.charAt(r) > 'Z'
            if (Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r))) return false;
            l++; r--;
        }
        return true;
    }



    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     * But this method is slower than {@link #isPalindrome}
     */
    public static boolean isPalindromeMyApproach(String s) {
        s = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase(); // s.replaceAll("\\s+", "").replaceAll("[\\W_]","").toLowerCase();
        int l=0;
        int r=s.length()-1;

        while(l<=r) {
            if (s.charAt(l) != s.charAt(r)) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }

    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n) - for StringBuilder
     */
    public static boolean isPalindromeMyApproach2(String s) {
        s = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return new StringBuilder(s).reverse().toString().equals(s); // or Objects.equals(new StringBuilder(s).reverse().toString(), s);
    }

    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public boolean isPalindromeMyApproach3(String s) {
        int l = 0, r = s.length()-1;
        while (l<=r) {
            char leftChar = s.charAt(l); // or
            char rightChar = s.charAt(r);
            boolean canCompare = true;
            if(!Character.isLetterOrDigit(leftChar)) {
                l++;
                canCompare = false;
            }

            if (!Character.isLetterOrDigit(rightChar)) {
                r--;
                canCompare = false;
            }

            if (canCompare) {
                if (Character.toLowerCase(leftChar) != Character.toLowerCase(rightChar)) {
                    return false;
                }
                l++;
                r--;
            }
        }
        return true;
    }





    public boolean isPalindrome2(String s) {
        int left =0, right = s.length() -1;

        while(left < right){
            char c1 = Character.toLowerCase(s.charAt(left));
            char c2 = Character.toLowerCase(s.charAt(right));

//            if(c1 >= 'A' && c1 <= 'Z') c1= (char)(c1 + 32); // ASCII conversion of lowercase -- 32 cause we have A to Z and [\]^_` and then a to z i.e 26+6
//            if(c2 >= 'A' && c2 <= 'Z') c2= (char)(c2 + 32); // '0'=48, 'A'=65, 'a'=97

            if(!((c1 >= 'a' && c1 <= 'z') || (c1 >= '0' && c1 <= '9'))){
                left++;
                continue;
            }
            if(!((c2 >= 'a' && c2 <= 'z') || (c2 >= '0' && c2 <= '9'))){
                right--;
                continue;
            }
            if(c1 != c2){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
