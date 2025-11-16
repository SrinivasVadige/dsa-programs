package Algorithms.LinkedListAlgos;

import java.util.*;

/**
 * consider both put and get means recent used
 * evict LRU with using order or time

 so how to maintain the order?
 ---> ArrayList by removing from the middle and adding at end -- TLE
 ---> LinkedHashMap with accessOrder=true --> maintain the order or access
 ---> Custom Doubly LinkedList by removing from the middle and adding the recent key at tail or end --> internal implementation of LinkedHashMap with accessOrder=true
 ---> LinkedHashMap with accessOrder=false --> remove from the middle and add to the end
 ---> HashMap with LinkedHashSet ---> same as above LinkedHashMap with accessOrder=false
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 04 Jan 2025
 * @link 146. LRU Cache <a href="https://leetcode.com/problems/lru-cache/">LeetCode link</a>
 * @topics Hash Table, Linked List, Design, Doubly-Linked List
 * @see DataStructures.HashMapExample JavaDoc -> to understand "HOW HASHMAP WORKS"
 * @companies amazon, facebook, apple, google, tiktok, oracle, palo, microsoft, goldman, bloomberg, walmart, uber, aurora, confluent, visa, paypal, snapchat, salesforce, bytedance, cloudflare, adobe, nvidia, yandex, samsung, linkedin, citadel, shopify, intuit, servicenow, rubrik
 */
public class LRUCache {

    public static void main(String[] args) {
        LRUCacheUsingDoublyLinkedList cache = new LRUCacheUsingDoublyLinkedList(2);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // returns 1
        cache.put(3, 3);    // evicts key 2
        System.out.println( "cache.get(1): " + cache.get(2));       // returns -1 (not found)
        cache.put(4, 4);    // evicts key 1
        System.out.println( "cache.get(1): " + cache.get(1));       // returns -1 (not found)
        System.out.println( "cache.get(3): " + cache.get(3));       // returns 3
        System.out.println( "cache.get(4): " + cache.get(4));       // returns 4
    }


    /**
     * see {@link DataStructures.HashMapExample} javaDoc
     * accessOrder==true --> orderByAccess not by insertion
     */
    static class LRUCacheUsingLinkedHashMapAccessOrderAsParent extends LinkedHashMap<Integer, Integer> {
        int capacity;
        public LRUCacheUsingLinkedHashMapAccessOrderAsParent(int capacity) {
            super(capacity+1, 1f, true); // --> as capacity is not changing or use default super(capacity, 0.75f, true);
            this.capacity = capacity;
        }

        @Override  // it's automatically called after every put() in LinkedHashMap
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > capacity; // Automatically remove LRU
        }

        public int get(int key) {
            return getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            super.put(key, value);
        }
    }




    static class LRUCacheUsingLinkedHashMapAccessOrder {
        Map<Integer, Integer> cache;

        public LRUCacheUsingLinkedHashMapAccessOrder(int capacity) {
            cache = new LinkedHashMap<>(capacity, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {return size() > capacity;}
            };
        }

        public int get(int key) {return cache.getOrDefault(key, -1);}

        public void put(int key, int value) {cache.put(key, value);}
    }







    /**
     * It's just the internal implementation of LinkedHashMap with accessOrder instead of insertionOrder
     */
    static class LRUCacheUsingDoublyLinkedList {
        int capacity;
        static class Node { int key; int val; Node prev; Node next; Node(){} Node(int key, int val) {this.key=key; this.val=val;}}
        Node dummyHead = new Node(0,0), dummyTail = new Node(0,0);
        Map<Integer, Node> map = new HashMap<>();

        public LRUCacheUsingDoublyLinkedList(int capacity) {
            this.capacity = capacity;
            dummyHead.next = dummyTail;
            dummyTail.prev = dummyHead;
        }

        public int get(int key) {
            if (map.containsKey(key)) {
                Node node = map.get(key);
                updateCache(node);
                return node.val;
            }
            return -1;
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                Node node = map.get(key);
                updateCache(node);
                node.val = value;
            } else {
                Node node = new Node(key, value);
                Node tail = dummyTail.prev;
                tail.next = node;
                node.prev = tail;
                node.next = dummyTail;
                dummyTail.prev = node;

                map.put(key, node);
            }

            if (map.size() > capacity) {
                Node lru = dummyHead.next; // actual head
                dummyHead.next = lru.next;
                lru.next.prev = dummyHead;
                map.remove(lru.key);
            }
        }

        private void updateCache(Node node) {
            if (node == dummyTail.prev) return;

            Node prevNode = node.prev;
            Node nextNode = node.next;

            prevNode.next = node.next;
            nextNode.prev = node.prev;

            Node tail = dummyTail.prev;
            tail.next = node;
            node.prev = tail;
            node.next = dummyTail;
            dummyTail.prev = node;
        }
    }










    static class LRUCacheUsingDoublyLinkedList2 {

        static class Node {int key, value; Node prev, next; Node(int k, int v) { key = k; value = v; }}

        private final Map<Integer, Node> map = new HashMap<>();
        private final int capacity;
        private final Node dummyHead = new Node(0, 0);
        private final Node dummyTail = new Node(0, 0);

        public LRUCacheUsingDoublyLinkedList2(int capacity) {
            this.capacity = capacity;
            dummyHead.next = dummyTail;
            dummyTail.prev = dummyHead;
        }

        public int get(int key) {
            if (!map.containsKey(key)) return -1;
            Node node = map.get(key);
            remove(node);
            insert(node);
            return node.value;
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                Node node = map.get(key);
                node.value = value;
                remove(node);
                insert(node);
            } else {
                if (map.size() == capacity) {
                    Node lru = dummyHead.next; // actual head
                    remove(lru);
                    map.remove(lru.key);
                }
                Node newNode = new Node(key, value);
                map.put(key, newNode);
                insert(newNode);
            }
        }

        private void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void insert(Node node) {
            Node tail = dummyTail.prev;
            tail.next = node;
            node.prev = tail;
            node.next = dummyTail;
            dummyTail.prev = node;
        }
    }









    static class LRUCacheUsingLinkedHashMap {
        Map<Integer, Integer> cache = new LinkedHashMap<>();
        int capacity;

        public LRUCacheUsingLinkedHashMap(int capacity) {
            this.capacity = capacity;
        }

        public int get(int key) {
            if (cache.containsKey(key)) {
                int val = cache.get(key);
                cache.remove(key);
                cache.put(key, val);
                return val;
            }
            return -1;
        }

        public void put(int key, int value) {
            cache.remove(key);
            cache.put(key, value);

            if(cache.size()>capacity) {
                int removeKey = cache.keySet().iterator().next();
                cache.remove(removeKey);
            }
        }
    }





    /**
     * Use (LinkedHashSet with HashMap) or (LinkedHashMap with insertionOrder)
     * ---> and then remove from the middle and add to the end
     */
    static class LRUCacheUsingLinkedHashSetAndHashMap {
        Map<Integer, Integer> map = new HashMap<>();
        Set<Integer> set = new LinkedHashSet<>();
        int size = 0;
        public LRUCacheUsingLinkedHashSetAndHashMap(int capacity) {
            size = capacity;
        }

        public int get(int key) {
            if(set.contains(key)) {
                set.remove(key);
                set.add(key);
            }
            return map.getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            map.put(key, value);

            set.remove(key);
            set.add(key);

            if(map.size() > size) {
                int lru = set.iterator().next(); // set.stream().findFirst().orElse(null);
                set.remove(lru);
                map.remove(lru);
            }
        }
    }










    /**
     * Remove from the middle and add to the end
     * Brute force
     */
    static class LRUCacheUsingHashMapAndArrayList {
        private final int capcity;
        private Map<Integer, Integer> map = new HashMap<>();
        private List<Integer> lst = new ArrayList<>();

        public LRUCacheUsingHashMapAndArrayList(int capacity) {
            this.capcity = capacity;
        }

        // Working but TLE
        public int get(int key) {
            if (map.containsKey(key)) {
                lst.remove(Integer.valueOf(key)); // --> remove from the middle
                lst.add(key);                     // --> add to the end
            }
            return map.getOrDefault(key, -1);
        }

        // Working but TLE
        public void put(int key, int value) {
            if (capcity == map.size() && !map.containsKey(key)) {
                map.remove(lst.get(0));
                lst.remove(0);
            }

            if (map.containsKey(key)) { // or use this in else case with above if case
                lst.remove(Integer.valueOf(key));
            }

            lst.add(key);
            map.put(key, value);

            System.out.println("map: " + map);
            System.out.println("lst: " + lst);
        }
    }






}
