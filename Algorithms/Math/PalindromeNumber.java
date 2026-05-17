package Algorithms.Math;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 21 April 2025
 * @link 9. Palindrome Number <a href="https://leetcode.com/problems/palindrome-number/description/"> LeetCode link</a>
 * @topics Math
 * @companies Google(26), Amazon(15), Microsoft(4), Meta(3), Bloomberg(3), Cognizant(2), Qualcomm(2), TCS(4), IBM(2), Morgan Stanley(2), EXL(2), Infosys(13), BCG(13), Accenture(8), Oracle(5), Capital One(5), Zoho(4), Deloitte(3), Intel(3), Capgemini(3), SAP(3), EPAM Systems(1)
 */
public class PalindromeNumber {
    public static void main(String[] args) {
        int x = 532435;
        System.out.println(isPalindrome(x));
        System.out.println(isPalindrome2(x));
        System.out.println(isPalindrome3(x));
        System.out.println(isPalindromeUsingLeftAndRightDigits2(x));
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




    /**

        Not working for 1000110001 and 1000021

     */
    public static boolean isPalindromeUsingLeftAndRightDigits1NotWorking(int x){
        if (x < 0 ) return false;

        while (x != 0) {
            int digits = (int) Math.pow(10, (int) Math.log10(Math.abs(x)));
            int leftDigit = x / digits;
            int rightDigit = x % 10;
            if (leftDigit != rightDigit) return false;

            x -= leftDigit * digits;
            x /= 10;
        }
        return true;
    }

    public static boolean isPalindromeUsingLeftAndRightDigits2(int x) {
        if (x < 0) return false;

        int div = 1;

        while (x / div >= 10) {
            div *= 10;
        }

        while (x != 0) {
            int leftD = x / div;
            int rightD = x % 10;

            if (leftD != rightD) return false;

            x = (x % div) / 10;
            div /= 100;
        }

        return true;
    }
}
