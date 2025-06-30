package Algorithms.TwoPointers;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 08 April 2025
 * @link 392. Is Subsequence <a href="https://leetcode.com/problems/is-subsequence/">LeetCode link</a>
 * @topics String, Two Pointers
 */
public class IsSubsequence {

    public static void main(String[] args) {

        String s = "abc", t = "ahbgdc";
        System.out.println("isSubsequence: " + isSubsequence(s, t));
        System.out.println("isSubsequence2: " + isSubsequence2(s, t));
        System.out.println("isSubsequence3: " + isSubsequence3(s, t));
        System.out.println("isSubsequenceMyApproach: " + isSubsequenceMyApproachOld(s, t));
    }


    public static boolean isSubsequence(String s, String t) {
        int i = 0, j = 0;
        while (s.length() > i && t.length() > j) {
            if(s.charAt(i) == t.charAt(j)) {
                i++;
                j++;
            } else {
                j++;
            }
        }
        return i == s.length();
    }

    public static boolean isSubsequence2(String s, String t) {
        int i = 0, j = 0;
        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)){
                i++;
            }
            j++;
        }
        return i == s.length();
    }

    public static boolean isSubsequence3(String s, String t) {
        int i = 0, j = 0;
        while (i < s.length() && j < t.length()) {
            char currS = s.charAt(i);
            while (j < t.length() && t.charAt(j) != currS) j++;
            if (j == t.length()) return false;
            i++;
            j++;
        }
        return i == s.length();
    }






    public static boolean isSubsequenceMyApproachOld(String s, String t) {
        // prepare tStringBuilder
        int[] sArr = new int[26]; // or use set.add(c-'a');
        for(char c: s.toCharArray()) sArr[c-'a']++;
        StringBuilder tsb = new StringBuilder();
        for(char tChar: t.toCharArray()) {
            if(sArr[tChar-'a'] > 0) tsb.append(tChar);
        }

        if(tsb.length() < s.length()) return false;

        StringBuilder ssb = new StringBuilder(s);
        while (ssb.length() > 0) {
            // 'c'
            char c = ssb.charAt(0);

            // sCount
            int sCount = 0;
            while (ssb.length() > 0 && ssb.charAt(0)==c){
                sCount++;
                ssb.deleteCharAt(0);
            }

            // trav until we found the 'c' in tStringBuilder
            while(tsb.length() > 0 && tsb.charAt(0) != c) {
                tsb.deleteCharAt(0);
            }

            // tCount
            int tCount = 0;
            while(tsb.length() > 0 && tsb.charAt(0) == c) {
                tCount++;
                tsb.deleteCharAt(0);
            }

            if(tCount < sCount) return false;
        }
        return true;
    }




    /** same as {@link #isSubsequenceMyApproachOld(String, String)} */
    public boolean isSubsequenceMyApproachOld2(String s, String t) {
        int[] sArr = new int[26]; // or use set.add(c-'a');
        for(char c: s.toCharArray()) sArr[c-'a']++;
        StringBuilder tsb = new StringBuilder(t);
        for(int i=0; i<tsb.length(); i++) {
            if(sArr[tsb.charAt(i)-'a'] > 0) continue; // s char found in t
            tsb.deleteCharAt(i);
            i--;
        }

        if(tsb.length() < s.length()) return false;

        StringBuilder ssb = new StringBuilder(s);
        while (ssb.length() > 0) {
            int sCount = 0;
            char c = ssb.charAt(0);
            while (ssb.length() > 0 && ssb.charAt(0)==c){
                sCount++;
                ssb.deleteCharAt(0);
            }

            while(tsb.length() > 0 && tsb.charAt(0) != c) {
                tsb.deleteCharAt(0);
            }

            int tCount = 0;
            while(tsb.length() > 0 && tsb.charAt(0) == c) {
                tCount++;
                tsb.deleteCharAt(0);
            }

            if(tCount < sCount) return false;
        }
        return true;
    }







    public boolean isSubsequence4(String s, String t) {
        int n=s.length();
        int l=t.length();
        int k=-1;
        for(int i=0;i<n;i++) {
            char ch=s.charAt(i);
            boolean b=false;
            for(int j=k+1;j<l;j++) {
                char a=t.charAt(j);
                if(ch==a) {
                    b=true;
                    k=j;
                    break;
                }
            }
            if(b==false) return false;
        }
        return true;
    }
}