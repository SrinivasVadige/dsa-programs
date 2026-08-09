package Algorithms.Hashing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 *
 * Here one letter from pattern word is mapped to one word from s sentence
 * Pattern = "abba"
 * s = "dog cat cat dog"
 * here a is mapped to dog and b is mapped to cat
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 22 Sept 2024
 * @link 290. Word Pattern <a href="https://leetcode.com/problems/word-pattern/">Leetcode link</a>
 * @topics Hash Table, String
 * @see Algorithms.Hashing.IsomorphicStrings

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
public class WordPattern {

    	public static void main(String[] args) {
		String pattern = "abba"; // abba
		String s = "dog dog dog dog"; // dog cat cat dog"
		sout("wordPattern 1 => " + wordPattern(pattern, s));
        sout("wordPattern 2 => " + wordPattern2(pattern, s));
        sout("wordPattern 3 => " + wordPattern3(pattern, s));
        sout("wordPattern 4 => " + wordPattern4(pattern, s));
	}

    public static boolean wordPattern(String pattern, String s) {
        String[] strs = s.split(" ");
        if(pattern.length() != strs.length) {
            return false;
        }

        Map<Character, String> pToS = new HashMap<>();

        for(int i=0; i<pattern.length(); i++) {
            char c = pattern.charAt(i);

            if(pToS.containsKey(c) && !pToS.get(c).equals(strs[i])) {
                return false;
            }

            pToS.put(c, strs[i]);
        }

        return pToS.size() == new HashSet<>(pToS.values()).size();
    }




    public static boolean wordPattern2(String pattern, String s) {
        String[] strs = s.split(" ");
        if(pattern.length() != strs.length) {
            return false;
        }

        Map<Character, String> pToS = new HashMap<>();
        Map<String, Character> sToP = new HashMap<>();

        for(int i=0; i<pattern.length(); i++) {
            char c = pattern.charAt(i);

            if(pToS.containsKey(c) && !pToS.get(c).equals(strs[i])) return false;
            if(sToP.containsKey(strs[i]) && !sToP.get(strs[i]).equals(c)) return false;

            pToS.put(c, strs[i]);
            sToP.put(strs[i], c);
        }

        return true;
    }





    public static boolean wordPattern3(String pattern, String s) {
        String[] strs = s.split(" ");
        if(pattern.length() != strs.length) {
            return false;
        }

        Map<Character, String> pToS = new HashMap<>();
        Set<String> seen = new HashSet<>();

        for(int i=0; i<pattern.length(); i++) {
            char c = pattern.charAt(i);

            if(pToS.containsKey(c) && !pToS.get(c).equals(strs[i])) return false;
            if(!pToS.containsKey(c) && seen.contains(strs[i])) return false;

            pToS.put(c, strs[i]);
            seen.add(strs[i]);
        }

        return true;
    }




    public static boolean wordPattern4(String pattern, String s) {

        String[] letters = pattern.split("");
        String[] words = s.split(" ");

        if (letters.length != words.length) return false;

        Map<String, String> map = new HashMap<>();


        for(int i = 0; i< letters.length; i++){
			var key = letters[i];
			var val = words[i];
            if(!map.containsKey(key)) {
                if (map.containsValue(val)) return false;
                map.put(key, val);
            }
            else{
                if (!map.get(key).equals(val)) return false;
            }
		}


        return true;

	}

    public static <E> void sout(E s){ System.out.println(s);}

}
