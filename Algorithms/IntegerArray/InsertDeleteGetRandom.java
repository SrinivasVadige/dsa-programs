package Algorithms.IntegerArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 16 June 2025
 * @link 380. Insert Delete GetRandom O(1) - <a href="https://leetcode.com/problems/insert-delete-getrandom-o1/">LeetCode link</a>
 * @topics Array, Hash Table, Math, Design, Randomized
 */
public class InsertDeleteGetRandom {
    public static void main(String[] args) {

        RandomizedSet obj = new RandomizedSet();
        System.out.println(obj.insert(1));
        System.out.println(obj.remove(2));
        System.out.println(obj.insert(2));
        System.out.println(obj.getRandom());
        System.out.println(obj.remove(1));
        System.out.println(obj.insert(2));
        System.out.println(obj.getRandom());
    }




    static class RandomizedSet{
        private HashMap<Integer, Integer> map;
        private ArrayList<Integer> list;
        private Random random;

        public RandomizedSet() {
            map = new HashMap<>();
            list = new ArrayList<>();
            random = new Random();
        }

        public boolean insert(int val) {
            if (map.containsKey(val)) return false;
            map.put(val, list.size());
            list.add(val);
            return true;
        }

        public boolean remove(int val) {
            if (!map.containsKey(val)) return false;

            Integer index = map.get(val);
            Integer lastElement = list.get(list.size() - 1);

            //swap i.e Move last element to 'index' of val
            list.set(index, lastElement);
            map.put(lastElement, index);

            // Remove last
            list.remove(list.size() - 1);
            map.remove(val);

            return true;
        }

        public int getRandom() {
            return list.get(random.nextInt(list.size()));
        }
    }











    static class RandomizedSetUsingOnlySet {
        Set<Integer> set;
        public RandomizedSetUsingOnlySet() {
            set = new HashSet<>();
        }

        public boolean insert(int val) {
            return set.add(val);
        }

        public boolean remove(int val) {
            return set.remove(val);
        }

        public int getRandom() {
            int num = set.iterator().next(); // NOT RANDOM
            num = new ArrayList<Integer>(set).get(new Random().nextInt(0, set.size())); // O(1)
            return num;
        }
    }
}
