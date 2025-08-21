package BasicPrograms;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 21 April 2025
 * @link 9. Palindrome Number <a href="https://leetcode.com/problems/palindrome-number/description/"> LeetCode link</a>
 * @topics Math
 */
public class PalindromeNumber {
    public static void main(String[] args) {
        int x = 532435;
        System.out.println(isPalindrome(x));
        System.out.println(isPalindrome2(x));
        System.out.println(isPalindrome3(x));
    }

    public static boolean isPalindrome(int x) {
        if(x<0) {
            return false;
        }

        int originalX = x;
        int reverseX = 0;
        while(x>0) {
            int lastDigit = x%10;
            x /= 10;
            reverseX = reverseX*10 + lastDigit;
        }
        return originalX == reverseX;
    }

    public static boolean isPalindrome2(int x) {
        if (x < 0)
            return false;
        int y = 0; // reverse of x
        int temp = x;
        while (temp != 0) {
            y = y * 10 + temp % 10; // 0 + 0 // 0 + 1
            temp = temp / 10; // 1
        }
        return x == y;
    }

    public static boolean isPalindrome3(int x) {
        if(x<0) {
            return false;
        }

        String s = String.valueOf(x);
        return new StringBuilder(s).reverse().toString().equals(s);
    }
}
