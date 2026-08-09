package Algorithms.Hashing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 04 Sept 2025
 * @link 205. Isomorphic Strings <a href="https://leetcode.com/problems/isomorphic-strings/">Leetcode link</a>
 * @topics Hash Table, String
 * @see Algorithms.Hashing.WordPattern

        Examples for easy understanding

        s = "paper"
        t = "title"
        TRUE
        here 'e' is present in both s and t

        s = "badc"
        t = "baba"
        FALSE
        here b-b, a-a, d-b, c-a ---> 'b' value and 'a' value are repeated in HashMap values multiple times
        but as per isomorphic, the values must be unique too

 */
public class IsomorphicStrings {
    public static void main(String[] args) {
        String s = "egg", t = "add";
        System.out.println("isIsomorphic 1 => " + isIsomorphic(s, t));
        System.out.println("isIsomorphic 2 => " + isIsomorphic2(s, t));
        System.out.println("isIsomorphic 3 => " + isIsomorphic3(s, t));
    }



    public static boolean isIsomorphic(String s, String t) {
        Map<Character, Character> sToT = new HashMap<>();

        for(int i=0; i<s.length(); i++) {
            char sChar = s.charAt(i);
            char tChar = t.charAt(i);

            if(sToT.containsKey(sChar)) {
                if(sToT.get(sChar) != tChar) return false;
            } else {
                sToT.put(sChar, tChar);
            }

            /*
            // or
            if(sToT.containsKey(sChar) && sToT.get(sChar) != tChar) return false;
            sToT.put(sChar, tChar);
            */
        }

        return sToT.size() == new HashSet<>(sToT.values()).size(); // or (int) sToT.values().stream().distinct().count()
    }





    public static boolean isIsomorphic2(String s, String t) {
        Map<Character, Character> sToT = new HashMap<>();
        Map<Character, Character> tToS = new HashMap<>();

        for(int i=0; i<s.length(); i++) {
            char sChar = s.charAt(i);
            char tChar = t.charAt(i);

            if(sToT.containsKey(sChar) && sToT.get(sChar) != tChar) return false;
            if(tToS.containsKey(tChar) && tToS.get(tChar) != sChar) return false;

            sToT.put(sChar, tChar);
            tToS.put(tChar, sChar);
        }
        return true;
    }






    public static boolean isIsomorphic3(String s, String t) {
        Map<Character, Character> sToT = new HashMap<>();
        Map<Character, Character> tToS = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char charS = s.charAt(i);
            char charT = t.charAt(i);

            if (sToT.containsKey(charS)) {
                if (sToT.get(charS) != charT) {
                    return false;
                }
            } else if (tToS.containsKey(charT)) {
                return false;
            } else {
                sToT.put(charS, charT);
                tToS.put(charT, charS);
            }
        }
        return true;
    }




    /**
        FAILED FOR

        s = "bbbaaaba"
        t = "aaabbbba"

        because, we need to track order as well
     */
    public static boolean isIsomorphicNotWorking(String s, String t) {
        Map<String, Integer> sMap = Arrays.stream(s.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e->1)));
        Map<Integer, Integer> sFreq = sMap.values().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e->1)));
        Map<String, Integer> tMap = Arrays.stream(t.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e->1)));
        Map<Integer, Integer> tFreq = tMap.values().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e->1)));


        // System.out.println("sMap : " + sMap);
        // System.out.println("sFreq : " + sFreq);
        // System.out.println("tMap : " + tMap);
        // System.out.println("tFreq : " + tFreq);

        for(int count: sFreq.keySet()) {
            if(!sFreq.get(count).equals(tFreq.getOrDefault(count, 0))) {
                return false;
            }
        }
        return true;

    }
}
