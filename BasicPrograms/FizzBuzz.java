package BasicPrograms;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 10 Oct 2024
 */
public class FizzBuzz {
    public static void main(String[] args) {
        System.out.println(fizzBuzz(3));
    }

    public static List<String> fizzBuzz(int n) {
        List<String> list = new ArrayList<>();
        for(int i=1; i<=n; i++) {
            String s = "";
            if (i % 3 == 0)
                s+="Fizz";
            if (i % 5 == 0)
                s+="Buzz";

            if(s.isEmpty())
                s+=i;

            list.add(s);
        }
        return list;
    }
}