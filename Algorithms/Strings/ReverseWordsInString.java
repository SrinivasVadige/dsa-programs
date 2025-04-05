package Algorithms.Strings;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 05 April 2025
 */
public class ReverseWordsInString {
    public static void main(String[] args) {
        String s = "the sky is blue";
        System.out.println("reverseWords(s) => " + reverseWords(s));
        System.out.println("reverseWordsMyApproach(s) => " + reverseWordsMyApproach(s));
        System.out.println("reverseWords2(s) => " + reverseWords2(s));

    }

    public static String reverseWords(String s) {
        s = s.trim();
        String[] words = s.split("\\s+");
        StringBuilder reversed = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            reversed.append(words[i]);
            if (i != 0) reversed.append(" "); // add space between words except after the last word
        }
        return reversed.toString();
    }

    public static String reverseWords2(String s) {
        char[] ca = s.toCharArray();
        int n = ca.length;
        char result[] = new char[n];
        int result_index = 0;
        int end = n - 1;

        while (end >= 0) {
            while (end >= 0 && ca[end] == ' ') end--; // skip spaces
            int start = end;
            while (start >= 0 && ca[start] != ' ')  start--; // frame word
            if (result_index > 0) result[result_index++] = ' '; // add space between words
            for (int i = start + 1; i <= end; i++)  // put that word into result
                result[result_index++] = ca[i];
            end = start - 1;
        }
        return new String(result).trim(); // or new String(result, 0, result_index).trim();
    }




    public static String reverseWordsMyApproach(String s) {
        String[] arr = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for(int i=arr.length-1; i>=0; i--) {
            if(!arr[i].isBlank()) sb.append(arr[i] + " ");
        }
        return sb.toString().trim();
    }



    public static String reverseWords3(String s) {
        StringBuilder sb = new StringBuilder();
        int n = s.length();
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) != ' ') {
                while (i >= 0 && s.charAt(i) != ' ') {
                    sb.append(s.charAt(i));
                    i--;
                }
                sb.append(' ');
            }
        }
        return sb.toString().trim();
    }
}
