package BasicPrograms;

/**
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 21 Sept 2024
 * @see Algorithms.MiscAlgos.ReverseInteger
*/
public class ReverseNumber {

    public static void main(String[] args) {
        int n = 543212345;
        System.out.println("Given number is: " + n);
        System.out.println("Reverse using DIV /10 AND MODULS %10 APPROACH => " + reverse(n));
        System.out.println("Reverse using STRING REVERSE => " + stringReverse(n));
        System.out.println("Reverse using STRING BUILDER REVERSE => " + stringBuilderReverse(n));
        System.out.println("isPalindrome: " + isPalindrome(n));
        System.out.println("isPalindromeStringApproach: " + isPalindromeStringApproach(n));


        System.out.println("ListNode approach --------------------------------------- ");
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));

        int n1 = 0;
        for(ListNode trav=l1; trav!=null; trav=trav.next){ // ----- normal sequence
            n1 = n1*10 + trav.val;
        }
        System.out.println(n1); // 243

        n1 = 0;
        for(ListNode trav=l1; trav!=null; trav=trav.next){ // ----- reverse sequence
            int zeros = (int) Math.log10(n1==0?1:n1)+1; // Math.log10(0) is -Infinity and (int)Math.log10(0) is -2147483648
            // or zeros = String.valueOf(n1).length();
            int mul = (int) Math.pow(10, zeros);
            if(n1 == 0)
                mul = 1;
            n1 = trav.val*mul + n1;
        }
        System.out.println(n1); // 342
    }

    // reversing using %10 and /10 concept
    static int reverse(int n) {

        // reversed number
        int rev = 0;

        while (n > 0) {
        int lastDigit = n % 10; // remainder
            rev = (rev * 10) + lastDigit;
            n = n / 10; // quotient
        }

        return rev;
    }


    static int stringReverse(int n){
        String oStr = String.valueOf(n);
        String nStr = "";

        for (char i : oStr.toCharArray()) { // or for index loop
            nStr = i+nStr; // it is different from nStr += i
        }

        // or
        // while (!oStr.equals("")) {
        //     nStr += oStr.charAt(oStr.length()-1);
        //     oStr = oStr.substring(0, oStr.length()-1);
        // }

        return Integer.parseInt(nStr);
    }

    static int stringBuilderReverse(int n){
        return Integer.parseInt(new StringBuffer(String.valueOf(n)).reverse().toString());
    }

    public static boolean isPalindrome(int x) {
        if (x < 0)
            return false;
        int temp = x;
        int y = 0; // reverse of x

        while (temp!=0) {
            y = y * 10 + temp % 10; // 0 + 0 // 0 + 1
            temp = temp / 10; // 1
        }
        return x == y;
    }

    public static boolean isPalindromeStringApproach(int x) {
        return new StringBuilder(String.valueOf(x)).reverse().toString().equals(String.valueOf(x));
    }

    @SuppressWarnings("unused")
    private static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
   }

}
