package Algorithms.Strings;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 04 April 2025
 * @see {@link BasicPrograms.GCD_HCF#euclideanAlgorithmWhileLoop}
 * @see {@link BasicPrograms.GCD_HCF#euclideanAlgorithmRecursion}
 */
public class GreatestCommonDivisorOfStrings {
    public static void main(String[] args) {
        String str1 = "ABCABC", str2 = "ABC";
        System.out.println("gcdOfStrings(str1, str2) => " + gcdOfStrings(str1, str2));
        System.out.println("gcdOfStringsMyApproach(str1, str2) => " + gcdOfStringsMyApproach(str1, str2));
    }

    public static String gcdOfStrings(String str1, String str2) {
        if (!str1.concat(str2).equals(str2.concat(str1))) return "";// If strings are not concatenable
        // if the above condition is met for 1="ABCDABC", 2="ABC" and 1="LEET", 2="CODE" scenarios
        return str1.substring(0, gcd(str1.length(), str2.length()));
    }

    private static int gcd(int a, int b) {
        if (b == 0) return a; // or return (b == 0) ? a : gcd(b, a % b);
        return gcd(b, a % b);
    }

    @SuppressWarnings("unused")
    private int gcd2(int len1, int len2) {
        while (len2 != 0) {
            int temp = len1 % len2;
            len1 = len2;
            len2 = temp;
        }
        return len1;
    }






    public static String gcdOfStringsMyApproach(String str1, String str2) {
        if (!new StringBuilder(str1).append(str2).toString().equals(new StringBuilder(str2).append(str1).toString())) return ""; // if(!(str1+str2).equals(str2+str1)) return "";  --- OPTIONAL 

        int n1 = str1.length();
        int n2 = str2.length();
        String maxStr = (n1>=n2?str1:str2);
        String minStr = (n1<n2?str1:str2);
        n1=maxStr.length();
        n2=minStr.length();

        StringBuilder s = new StringBuilder();
        for (int i=n2-1; i>=0; i--) {
            s = new StringBuilder(minStr.substring(0, i+1));
            if(checkReps(minStr, maxStr, n1, n2, s)) {
                return s.toString();
            }
        }
        return "";
    }

    private static boolean checkReps(String minStr, String maxStr, int n1, int n2, StringBuilder s) {
        int n = s.length();
        if (!maxStr.startsWith(s.toString()) || n1%n != 0 || n2%n != 0) return false;

        StringBuilder tempS = new StringBuilder(s).append(s);
        while(n2 >= tempS.length()) {
            if (!minStr.startsWith(tempS.toString()) || !maxStr.startsWith(tempS.toString()))
                return false;
            tempS.append(s);
        }
        while(n1 >= tempS.length()) {
            if(!maxStr.startsWith(tempS.toString())) return false;
            tempS.append(s);
        }

        return true;
    }
}