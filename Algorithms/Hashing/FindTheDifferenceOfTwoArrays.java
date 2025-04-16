package Algorithms.Hashing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 15 April 2025
 */
public class FindTheDifferenceOfTwoArrays {
    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3};
        int[] nums2 = {2, 4, 6};
        System.out.println("findDifference(nums1, nums2) => " + findDifference(nums1, nums2));
    }

    public static List<List<Integer>> findDifference(int[] nums1, int[] nums2) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        result.add(new ArrayList<Integer>());
        result.add(new ArrayList<Integer>());
        Set<Integer> set1 = new HashSet<Integer>();
        Set<Integer> set2 = new HashSet<Integer>();

        for (int x: nums1) set1.add(x);
        for (int x: nums2) set2.add(x);
        for (int x: set1) if (!set2.contains(x)) result.get(0).add(x);
        for (int x: set2) if (!set1.contains(x)) result.get(1).add(x);
        return result; // or Arrays.asList(list1, list2);
    }




    public List<List<Integer>> findDifferenceMyApproach(int[] nums1, int[] nums2) {
        List<List<Integer>> lst = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        Set<Integer> subLst = new HashSet<>(); // or use List, just by trav "set" instead of nums1, nums2

        for(int x: nums2) set.add(x);
        for(int x:nums1) {
            if(!set.contains(x)) subLst.add(x);
        }
        lst.add(new ArrayList<>(subLst));



        subLst = new HashSet<>();
        set.clear();
        for(int x: nums1) set.add(x);
        for(int x:nums2) {
            if(!set.contains(x)) subLst.add(x);
        }
        lst.add(new ArrayList<>(subLst));

        return lst;
    }

}
