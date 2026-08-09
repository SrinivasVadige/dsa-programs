package Algorithms.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 30 March 2025
 */
public class LongestPalindromeAfterSubstringConcatenationI {
    public static void main(String[] args) {
        String s = "abcde";
        String t = "eba";
        System.out.println("longestPalindrome(s, t): => " + longestPalindrome(s, t));
        System.out.println( "longestPalindrome2(s, t): => " + longestPalindrome2(s, t));
    }

    public static int longestPalindrome(String s, String t) {
        int maxLen = 0;

        List<String> sSubs = generateSubstrings(s);
        List<String> tSubs = generateSubstrings(t);

        for (String sSub : sSubs) {
            for (String tSub : tSubs) {
                String combined = sSub + tSub;
                if (isPalindrome(combined)) {
                    maxLen = Math.max(maxLen, combined.length());
                }
            }
        }

        return maxLen;
    }

    private static List<String> generateSubstrings(String str) {
        List<String> subs = new ArrayList<>();
        int n = str.length();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                subs.add(str.substring(i, j));
            }
        }
        subs.add(""); // include empty substring to cover edge case - palindromes only in s or t
        return subs;
    }

    private static boolean isPalindrome(String s){
        int l = 0, r = s.length()-1;
        while(l <= r) {
            if(s.charAt(l) != s.charAt(r)) return false;
            l++;
            r--;
        }
        return true;
    }










    public static int longestPalindrome2(String s, String t) {
        int ans=0;
        int n=s.length();
        int m=t.length();
        for(int i=0;i<=n;i++){
            for(int j=i;j<=n;j++){
                String subS=s.substring(i,j);
                for(int k=0;k<=m;k++){
                    for(int l=k;l<=m;l++){
                        String subT=t.substring(k,l);
                        String c=subS+subT;
                        if(isPalindrome(c)){
                            ans=Math.max(ans,c.length());
                        }
                    }
                }
            }
        }
        return ans;
    }















    /**
     * NOT WORKING

        PATTERNS:
        ---------
        1) For "s", use two pointers l & r, l++ check, r-- check
        2) If l==r then check the palindrome in t
    */
    public int longestPalindromeMyApproach(String s, String t) {
        int n = s.length();
        t = new StringBuilder(t).reverse().toString();
        System.out.println("t:" + t);
        int res = 0;
        boolean isFound = false;
        int ti = -1;

        int l=0, ml=0;
        int r=n-1, mr=-1;
        for (l=0; l<n; l++) {
            for (r=n-1; r>=l; r--) {
                String tempS = s.substring(l,r+1);
                System.out.println(tempS);
                if (isPalindrome(tempS))
                    res = Math.max(res, tempS.length());
                int tIndex = t.indexOf(tempS);
                if (tIndex > -1) {
                    System.out.printf("s in t --->  l:%s, r:%s\n", l, r);
                    isFound = true;
                    if (mr-ml < r-l){
                        ti = tIndex;
                        ml = l;
                        mr = r;
                    }
                    // if (ti==-1) ti=tIndex;
                    // break main;
                }
            }
        }
        if (!isFound) {
            l--;
            r++;
        } else {
            l = ml;
            r = mr;
        }

        System.out.printf("l:%s, r:%s\n", l, r);

        // no chars in "s" matched in "t"
        n=t.length();
        int i =0, j=n-1;
        main: for (i=0; i<n; i++) {
            for (j=n-1; j>=i; j--) {
                if (isPalindrome(t.substring(i, j+1))) {
                    res = Math.max(res, j+1-i);
                    break main;
                }
            }
        }
        if (i > j) {
            i--;
            j++;
        }
        System.out.printf("i:%s, j:%s\n", i, j);


        System.out.println("isFound:"+isFound);
        System.out.println("ti:"+ti);
        if (isFound) {
            n=s.length();
            if (r<n-1)
                res = Math.max((r-l+1)*2+1, res);
            else
                res = Math.max((r-l+1)*2, res);
        }
        return res;
    }
}
