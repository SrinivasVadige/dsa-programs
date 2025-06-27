package Algorithms.Strings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 05 April 2025
 * @link 151. Reverse Words in a String <a href="https://leetcode.com/problems/reverse-words-in-a-string/">LeetCode link</a>
 * @topics String, Two Pointers
 */
public class ReverseWordsInString {
    public static void main(String[] args) {
        String s = "the sky is blue";
        System.out.println("reverseWords(s) => " + reverseWords(s));
        System.out.println("reverseWordsMyApproach(s) => " + reverseWordsMyApproach(s));
        System.out.println("reverseWords2(s) => " + reverseWords2(s));

    }

    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
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



    public static String reverseWordsMyApproach(String s) {
        String[] arr = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for(int i=arr.length-1; i>=0; i--) {
            if(!arr[i].isBlank()) sb.append(arr[i]).append(" ");
        }
        return sb.toString().trim();
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(w), where is maxWordLength
     */
    public static String reverseWords2(String s) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder(); // or use new int[2]{start, end} and just use sb.append(s.substring(start, end))
        s=s.trim();
        for(int i=0; i<s.length(); i++) { // or for(char c: s.toCharArray()){
            char c = s.charAt(i);
            if(c != ' ') {
                temp.append(c);
            } else if(!temp.isEmpty()) {
                sb.insert(0, " "+ temp);
                temp.setLength(0);
            }
        }
        return sb.insert(0, temp).toString(); // for the last word we don't see ' ' cause we did s = s.trim();
    }




    public static String reverseWordsUsingTwoPointers(String s) {
        char[] ca = s.toCharArray();
        int n = ca.length;
        char[] result = new char[n];
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





    public String reverseWordsUsingList(String s) {
        List<String> list = Arrays.asList(s.trim().split("\\s+"));
        Collections.reverse(list);
        return String.join(" ", list); // String.join works for char[], String[], List<String> and individual strings
    }


    public String reverseWordsUsingStream1(String s) {
        List<String> list = Arrays.stream(s.trim().split("\\s+")).collect(Collectors.toList());
        Collections.reverse(list);
        return list.stream().collect(Collectors.joining(" ")); // or return String.join(" ", list);
    }



    public String reverseWordsUsingStream2(String s) {
        return String.join(" ", Arrays.stream(s.trim().split("\\s+"))
        .collect(Collectors.collectingAndThen(
            Collectors.toList(),
            (List<String> list) -> { // use "(list) -> {" -- will throw error: reference to join is ambiguous. So use "(List<String> list) -> {"
                Collections.reverse(list);
                return list;
            }
        )));
    }



    public String reverseWordsUsingStream3(String s) {
        return Arrays.stream(s.trim().split("\\s+")).collect(Collectors.collectingAndThen(
            Collectors.toList(),
            (list) -> {
                Collections.reverse(list);
                return list;
            }
        )).stream().collect(Collectors.joining(" "));
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
