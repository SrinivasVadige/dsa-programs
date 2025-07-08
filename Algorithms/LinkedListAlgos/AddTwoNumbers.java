package Algorithms.LinkedListAlgos;

import java.math.BigDecimal;

/**
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.
 * l1 and l2 are already in reverse order just like we need to add two numbers and carry forward the digit if necessary
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 Nov 2024
 * @link 2. Add Two Numbers <a href="https://leetcode.com/problems/add-two-numbers/">LeetCode link</a>
 * @topics Linked List, Math, Recursion
 * @companies Amazon, Meta, Bloomberg, Microsoft, Nvidia, Goldman, Apple, Oracle, ByteDance, Yandex, Capgemini, Adobe, Uber, Yahoo, Avito, TikTok, tcs, Accenture, josh, Infosys, Cisco
 */
public class AddTwoNumbers {
    public static class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static void main(String[] args) {

        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));

        // ListNode l1 = new ListNode(9);
        // ListNode l2 = new ListNode(1, new ListNode(9, new ListNode(9)));

        ListNode l3 = addTwoNumbers(l1, l2);

        for(ListNode trav=l3; trav!=null; trav=trav.next){
            System.out.print(trav.val + " ");
        }
    }



    /**
        1
        999
      + 223
       ----
       1222
       ----

     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1), trav = dummy;

        int carryForward = 0;
        while(l1 != null || l2 != null) {
            int val1 = 0, val2 = 0;
            if(l1 != null) {
                val1 = l1.val;
                l1 = l1.next;
            }
            if(l2 != null) {
                val2 = l2.val;
                l2 = l2.next;
            }

            int sum = carryForward + val1 + val2;
            carryForward = sum/10;
            int currNum = sum % 10;

            trav.next = new ListNode(currNum);
            trav = trav.next;
        }
        if(carryForward > 0) {
            trav.next = new ListNode(carryForward);
        }
        return dummy.next;
    }






    public static ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0), curr = dummy;
        int carry = 0;
        while(l1!=null || l2!=null || carry==1){
            int sum = 0;
            if(l1!=null){
                sum+=l1.val;
                l1=l1.next;
            }
            if(l2!=null){
                sum+=l2.val;
                l2=l2.next;
            }

            sum+=carry;
            carry = sum/10;
            ListNode node = new ListNode(sum%10);
            curr.next = node;
            curr = node;
        }
        return dummy.next;
    }






    /**
     *
    */
    public static ListNode addTwoNumbersMyApproachOld(ListNode l1, ListNode l2) {
        ListNode l3 = new ListNode();
        ListNode trav = l3;
        int carryForward = 0;

        while(l1!=null || l2!=null) {

            int n1 = 0;
            int n2 = 0;

            if(l1 != null) {
                System.out.println("l1: " + l1.val);
                n1 = l1.val;
                l1 = l1.next;
            }

            if(l2 != null) {
                System.out.println("l2: "+l2.val);
                n2 = l2.val;
                l2 = l2.next;
            }

            int n3 = n1+n2+carryForward;

            // if (n3 > 9) { ---- OPTIONAL as n3 = 5%10 = 5 and carryForward =5/10 = 0 -- int not double
            //     carryForward = n3/10;
            //     n3 = n3 % 10;
            // } else {
            //     carryForward = 0;
            // }

            carryForward = n3/10;
            n3 = n3 % 10;

            trav.val = n3;
            trav.next = new ListNode(); // ----> if we use "ListNode dummy = new ListNode(-1), trav = dummy;" --> no need to worry about --> trav.next = new ListNode(); trav.next.val = carryForward; trav.next = null;

            if (l1 == null && l2 == null){ // to carryForward the last digit sum if > 9
                if(carryForward !=0)
                    trav.next.val = carryForward;
                else
                    trav.next = null; // because of above trav.next = new ListNode(); condition
            } else {
                trav = trav.next;
            }
        }

        return l3;
    }






    public ListNode addTwoNumbersUsingBigDecimal(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1), trav = dummy;
        ListNode trav1 = l1, trav2 = l2;

        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        while(trav1 != null) {
            sb1.append(trav1.val);
            trav1 = trav1.next;
        }

        while(trav2 != null) {
            sb2.append(trav2.val);
            trav2 = trav2.next;
        }

        BigDecimal sum = new BigDecimal(sb1.reverse().toString()).add(new BigDecimal(sb2.reverse().toString()));
        String reverseSum = new StringBuilder().append(sum).reverse().toString();

        for(String s: reverseSum.split("")){
            trav.next = new ListNode(Integer.parseInt(s));
            trav = trav.next;
        }

        return dummy.next;
    }









    /**
     * Working but failing with java.lang or java.lang.NumberFormatException when
     * l1=[1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1] and l2=[5,6,4]
     * i.e when total sequence is greater than Integer.MAX_VALUE and Long.MAX_VALUE
     * So, use strings and solve it like an elementary school addition i.e carry forward the digit to left side
     */
    public static ListNode addTwoNumbersUsingLongNotWorking(ListNode l1, ListNode l2) {
        int n1 = 0;
        int n2 = 0;
        int n3 = 0;
        ListNode l3 = new ListNode();

        String temp = "";
        for(ListNode trav=l1; trav!=null; trav=trav.next){
            temp = trav.val + temp; // reverse
        }
        n1 = Integer.parseInt(temp);
        System.out.println(n1);

        temp = "";
        for(ListNode trav=l2; trav!=null; trav=trav.next){
            temp = trav.val + temp; // reverse
        }
        n2 = Integer.parseInt(temp);
        System.out.println(n2);

        n3 = n1 + n2;

        temp = "" + n3;
        temp = new StringBuilder(temp).reverse().toString();
        System.out.println(temp);
        temp = temp.replaceAll("0", "");

        String[] l3Arr = temp.split("");
        ListNode trav=l3;
        for (int i=0; i<l3Arr.length; i++, trav=trav.next){
            trav.val =Integer.parseInt(l3Arr[i]);
            if(i<l3Arr.length-1) trav.next = new ListNode();
        }

        return l3;
    }
}
