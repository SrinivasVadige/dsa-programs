package Algorithms.Strings;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 05 April 2025
 */
public class ReverseVowelsOfString {
    public static void main(String[] args) {
        String s = "hello";
        System.out.println(reverseVowels(s));
    }

    public static String reverseVowels(String s) {
        char[] chars = s.toCharArray();
        int i = 0, j = chars.length - 1;
        while (i < j) {
            while (i < j && !isVowel(chars[i])) i++;
            while (i < j && !isVowel(chars[j])) j--;;
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
            i++; j--;
        }
        return new String(chars);
    }




    public String reverseVowels2(String s) {
        // Create an array to flag vowels. The ASCII value of the character will serve as the index.
        boolean[] isVowel = new boolean[128];
        for (char c : "aeiouAEIOU".toCharArray()) isVowel[c] = true;
        char[] chars = s.toCharArray();
        int i = 0, j = chars.length - 1;
        while (i < j) {
            while (i < j && !isVowel[chars[i]]) ++i;
            while (i < j && !isVowel[chars[j]]) --j;
            if (i < j) {
                char temp = chars[i];
                chars[i] = chars[j];
                chars[j] = temp;
                ++i;
                --j;
            }
        }
        return String.valueOf(chars);
    }


    public String reverseVowelsMyApproach(String s) {
        StringBuilder sb = new StringBuilder(s);
        int n = s.length();
        int l=0,r=n-1;

        while(l<r && l<n) {
            while(l<r && !isVowel(sb.charAt(l))) l++;
            while(l<r && !isVowel(sb.charAt(r))) r--;

            // reverse
            char lChar = sb.charAt(l);
            char rChar = sb.charAt(r);
            sb.setCharAt(l, rChar);
            sb.setCharAt(r, lChar);
            l++;
            r--;
        }
        return sb.toString();
    }



    public static boolean isVowel(char c) {
            // or if ("aeiouAEIOU".chars().anyMatch(i-> i==c)) return true;
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'
                || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }
}
