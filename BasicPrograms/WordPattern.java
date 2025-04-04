package BasicPrograms;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * Here one letter from pattern word is mapped to one word from s sentence
 * Pattern = "abba"
 * s = "dog cat cat dog"
 * here a is mapped to dog and b is mapped to cat
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 22 Sept 2024
*/
public class WordPattern {

    	public static void main(String[] args) {
		String pattern = "abba"; // abba
		String s = "dog dog dog dog"; // dog cat cat dog"
		System.out.println(wordPattern(pattern, s));
	}

    public static boolean wordPattern(String pattern, String s) {

        String[] letters = pattern.split("");
        String[] words = s.split(" ");

        if (letters.length != words.length) return false;

        Map<String, String> map = new HashMap<>();
        for(int i = 0; i< letters.length; i++){
			var key = letters[i];
			var val = words[i];
            if(!map.containsKey(key)) {
                if (map.containsValue(val))
                    return false;
                map.put(key, val);
            }
            else{
                if (!map.get(key).equals(val))
                    return false;
            }
		}
        return true;
	}

    public static <E> void st(E s){ System.out.println(s);}

}
