package Algorithms.DisjointSetUnion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 21 March 2025
 */
public class FindIndexOfFirstOccurrenceInString {
    public static void main(String[] args) {
        String haystack="hello", needle="ll";
        System.out.println(strStr(haystack, needle));
    }

    public static int strStr(String haystack, String needle) {
        for (int i = 0; i + needle.length() <= haystack.length(); i++) {
            if (haystack.substring(i, i + needle.length()).equals(needle)) return i;
        }
        return -1;
    }


    public static int strStr2(String haystack, String needle) {
        return haystack.indexOf(needle);
    }

    public int strStrMyApproach(String haystack, String needle) {
        for(int i=0; i<haystack.length(); i++) {
            if (haystack.substring(i).startsWith(needle)) return i;
        }
        return -1;
    }

    public int strStrMyApproach2(String haystack, String needle) {
        for(int i=0; i<haystack.length(); i++) {
            while(i<haystack.length() && haystack.charAt(i) != needle.charAt(0)) i++;
            if (haystack.substring(i).startsWith(needle)) return i;
        }
        return -1;
    }


    public static int strStr3(String haystack, String needle) {
        Pattern pattern = Pattern.compile(needle);
        Matcher matcher = pattern.matcher(haystack);
        if(matcher.find()){
            return matcher.start();
        }
        return -1;
    }



    public int strStr4(String haystack, String needle) {
        int n = haystack.length();
        System.out.println(n);
        int m = needle.length();
        System.out.println(m);

        int i = 0;
        int j = 0;
        while (i <= n - m) {
            while (haystack.charAt(i + j) == needle.charAt(j)) {
                j++;
                if (j == m) {
                    return i;
                }
            }
            i++;
            j = 0;
        }
        return -1;
    }
}
