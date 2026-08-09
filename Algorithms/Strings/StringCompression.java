package Algorithms.Strings;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 06 April 2025
 */
public class StringCompression {
    public static void main(String[] args) {
        char[] chars = {'a', 'a', 'b', 'b', 'c', 'c', 'c'};
        System.out.println("Original String: " + new String(chars));
        System.out.println("Compressed String: " + compressMyApproach(chars));
        chars = new char[]{'a', 'a', 'b', 'b', 'c', 'c', 'c'};
        System.out.println("Compressed String: " + compress(chars));
    }

    public static int compressMyApproach(char[] chars) {
        StringBuilder sb = new StringBuilder();
        char prevChar = chars[0];
        int count = 0;
        for(char c: chars) {
            if(prevChar == c) count++;
            else {
                sb.append(prevChar);
                if(count > 1) sb.append(count);
                count = 1;
                prevChar = c;
            }
        }
        // for last group
        sb.append(prevChar);
        if(count > 1) sb.append(count);

        // update chars arr
        for(int i=0; i<sb.length(); i++) chars[i]=sb.charAt(i);
        for(int i=sb.length(); i<chars.length; i++) chars[i]='0';

        System.out.println(sb);
        return sb.length();
    }

    public static int compress(char[] chars) {
        int n = chars.length, writeIndex = 0, count = 1;
        for (int i = 1; i < n; i++) {
            if (chars[i] == chars[i - 1]) count++;
            else {
                chars[writeIndex++] = chars[i - 1];
                if (count > 1) {
                    String countStr = String.valueOf(count);
                    for (char c : countStr.toCharArray()) chars[writeIndex++] = c;
                }
                count = 1;
            }
        }
        chars[writeIndex++] = chars[n - 1];
        if (count > 1) {
            String countStr = String.valueOf(count);
            for (char c : countStr.toCharArray()) chars[writeIndex++] = c;
        }
        return writeIndex;
    }

}
