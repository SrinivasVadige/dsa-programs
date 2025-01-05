package Algorithms.LinkedListAlgos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * consider both put and get means recent used
 * evict LRU with using order or time
 *
 * so how to maintain the order?
 * ---> ArrayList by removing and adding the recent key at end -- TLE
 * ---> LinkedList by removing and adding the recent key at head
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 04 Jan 2025
 */
public class LRUCache {

    class Node {int key;int val;Node prev;Node next;public Node(int key, int val) {this.key = key;this.val = val;}}

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
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

    private int cap;
    private Map<Integer, Node> cache; // key & it's node address... oldest is the head of that node and latest is the tail
    private Node head; // less used LRU ---> HEAD
    private Node tail; // most used MRU ---> TAIL

    public LRUCache(int capacity) {
        this.cap = capacity;
        cache = new HashMap<>();

        head = new Node(0,0);
        tail = new Node(0,0);

        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            removeNode(node);
            insertNode(node);

            return node.val;
        }
        return -1;
    }

    public void put(int key, int val) {
        if (cache.containsKey(key)) {
            Node currentNode = cache.get(key);
            removeNode(currentNode);
        }

        // as we removed the currentNode -- treat every Node as newNode
        Node newNode = new Node(key, val);
        insertNode(newNode);
        cache.put(key, newNode);

        if (cap < cache.size()) {
            Node lruNode = head.next;
            removeNode(lruNode);
            cache.remove(lruNode.key);
        }
    }

    private void removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;

        prev.next = next;
        next.prev = prev;
    }

    private void insertNode(Node node) { // newNode doesn't contains prev and next
        Node prev = tail.prev;
        prev.next = node;
        node.prev = prev;

        node.next = tail;
        tail.prev = node;
    }





    private Map<Integer, Integer> map = new HashMap<>();
    private List<Integer> lst = new ArrayList<>();
    // Working but TLE
    public int get2(int key) {
        if (map.containsKey(key)) {
            lst.remove(Integer.valueOf(key));
            lst.add(key);
        }
        return map.getOrDefault(key, -1);
    }

    // Working but TLE
    public void put2(int key, int value) {
        if (cap==map.size() && !map.containsKey(key)) {
            map.remove(lst.get(0));
            lst.remove(0);
        }

        if(map.containsKey(key)) { // or use this in else case with above if case
            lst.remove(Integer.valueOf(key));
        }

        lst.add(key);
        map.put(key, value);

        System.out.println("map: " + map);
        System.out.println( "lst: " + lst);
    }
}
