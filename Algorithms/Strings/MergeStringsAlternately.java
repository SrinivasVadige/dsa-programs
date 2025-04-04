package Algorithms.Strings;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 04 April 2025
 */
public class MergeStringsAlternately {
    public static void main(String[] args) {
        String word1 = "ace";
        String word2 = "bdf";
        System.out.println(mergeAlternately(word1, word2)); // Output: "abcdef"
    }

    public static String mergeAlternately(String word1, String word2) {
        StringBuilder word = new StringBuilder();
        int n1 = word1.length(), n2 = word2.length();
        int n = Math.max(n1, n2);
        for(int i=0; i<n; i++) {
            if(i<n1) word.append(word1.charAt(i));
            if(i<n2) word.append(word2.charAt(i));
        }
        return word.toString();
    }

    public static String mergeAlternately2(String word1, String word2) {
        String word = "";
        int n1 = word1.length(), n2 = word2.length();
        int n = Math.max(n1, n2);
        for(int i=0; i<n; i++) {
            String s1 = "", s2 = "";
            if(i<n1) s1 += word1.charAt(i);
            if(i<n2) s2 += word2.charAt(i);
            word += s1+s2;
        }
        return word;
    }

    public static String mergeAlternately3(String word1, String word2) {
        StringBuilder merged = new StringBuilder();
        int i = 0, j = 0;
        while (i < word1.length() || j < word2.length()) {
            if (i < word1.length()) {
                merged.append(word1.charAt(i++));
            }
            if (j < word2.length()) {
                merged.append(word2.charAt(j++));
            }
        }
        return merged.toString();
    }




    public String mergeAlternately4(String word1, String word2) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        while(index < word1.length() && index<word2.length()){
            sb.append(word1.charAt(index));
            sb.append(word2.charAt(index));
            index++;
        }
        sb.append(word1.substring(index));
        sb.append(word2.substring(index));

        return sb.toString();

    }
}
