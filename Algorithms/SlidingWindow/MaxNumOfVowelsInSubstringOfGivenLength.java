package Algorithms.SlidingWindow;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 April 2025
 */
public class MaxNumOfVowelsInSubstringOfGivenLength {
    public static void main(String[] args) {
        String s = "abciiidef";
        int k = 3;
        System.out.println(maxVowels(s, k)); // Output: 3
    }

    /**
        GIVEN:
        -----
        1) k=window
        2) calculate vowels in currWindow
        3) return the max vowels we found in all of the windows
     */
    public static int maxVowels(String s, int k) {
        int max = 0;
        int count = 0;

        for(int i=0; i<k; i++) {
            if(isVowel(s.charAt(i))) count++;
        }
        max=count;

        for (int i=k; i<s.length(); i++) {
            char currChar = s.charAt(i);
            char prevChar = s.charAt(i-k);

            if(isVowel(prevChar)) count--;
            if(isVowel(currChar)) count++;

            max=Math.max(max, count);
        }
        return max;
    }

    public static boolean isVowel(char ch) {
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }
}
