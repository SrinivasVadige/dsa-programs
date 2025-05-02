package Algorithms.Graphs;

import java.util.HashSet;
import java.util.List;
import java.util.Stack;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 01 May 2025
 */
public class KeysAndRooms {
    public static void main(String[] args) {
        int[][] rooms = {{1}, {2}, {3}, {}};
        System.out.println(canVisitAllRooms(rooms)); // Output: true
    }
    /**
        APPROACH:
        ---------
        1) Given AdjacentList Graph
        2) Check if all nodes are connected
        3) So, we can use adjLst dfs or unionFind
        4) In adjLst, entryPoint = 0

        NOTE:
        -----
        UnionFind is failing for [[1],[],[0,3],[1]] because we can't go after 1
        and [[2],[],[1]] cause we only get room1 key from room0 and room1 key from room2
     */
    public static boolean canVisitAllRooms(int[][] rooms) {
        boolean[] visited = new boolean[rooms.length];
        visited[0] = true;
        dfs(rooms, 0, visited);
        for (boolean room : visited) if (!room) return false;
        return true;
        // Arrays.stream(visited).allMatch(v -> v); ---- won't work for boolean[]
    }
    public static void dfs(int[][] rooms, int room, boolean[] visited) {
        for (int key : rooms[room]) {
            if (!visited[key]) {
                visited[key] = true;
                dfs(rooms, key, visited);
            }
        }
    }



    public boolean canVisitAllRooms2(List<List<Integer>> rooms) {
        HashSet<Integer> visited = new HashSet<>();
        dfs(rooms, 0 , visited);
        return visited.size() == rooms.size();
    }
    public void dfs(List<List<Integer>> rooms, int currentRoom , HashSet<Integer> visited){
       visited.add(currentRoom);
        for( int key : rooms.get(currentRoom)){
            if(!visited.contains(key)){
            dfs(rooms, key , visited);
            }
        }
    }




    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        boolean[] seen = new boolean[rooms.size()];
        seen[0] = true;
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        int currentCount = 1;
        while (!stack.isEmpty()) {
            int node = stack.pop();
            for (int nei: rooms.get(node)) {
                if (!seen[nei]) {
                    seen[nei] = true;
                    currentCount++;
                    if(currentCount==rooms.size()) return true;
                    stack.push(nei);
                }
            }
        }
        return false; // or currentCount == rooms.size()
    }
}
