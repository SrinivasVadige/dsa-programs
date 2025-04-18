package Algorithms.StackAlgos;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 18 April 2025
 */
public class AsteroidCollision {
    public static void main(String[] args) {
        int[] asteroids = new int[]{5, 10, -5}; // => [5, 10]
        System.out.println("asteroidCollision: " + asteroidCollision(asteroids));
    }

    public static int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();

        for (int a : asteroids) {
            boolean destroyed = false;
            while (!stack.isEmpty() && a < 0 && stack.peek() > 0) {
                int diff = a + stack.peek();
                if (diff < 0) {
                    stack.pop(); // Top asteroid is destroyed
                } else if (diff > 0) {
                    destroyed = true; // Current asteroid is destroyed
                    break;
                } else {
                    stack.pop(); // Both asteroids destroy each other
                    destroyed = true;
                    break;
                }
            }

            if (!destroyed) {
                stack.push(a); // Add the current asteroid if not destroyed
            }
        }

        int[] result = new int[stack.size()];
        for (int i = stack.size() - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }
        return result;
    }





    public static int[] asteroidCollision2(int[] asteroids) {
        int[] stack = new int[asteroids.length];
        int top = 0;

        for (int a : asteroids) {
            boolean destroyed = false;
            while (top > 0 && stack[top - 1] > 0 && a < 0) {
                if (stack[top - 1] == -a) {
                    top--; // Both asteroids destroy each other
                    destroyed = true;
                    break;
                } else if (stack[top - 1] > -a) {
                    destroyed = true; // Current a is destroyed
                    break;
                } else {
                    top--; // Stack a is destroyed
                }
            }

            if (!destroyed) {
                stack[top++] = a;
            }
        }

        int[] result = new int[top];
        System.arraycopy(stack, 0, result, 0, top);
        return result;
    }

    /**

        GIVEN:
        ------
        1) i == position
        2) arr[i] == size, +ve --> right, -ve --> left
        3) same speed
        4) smaller will explode when collided
        5) RETURN ONLY NON-EXPLODED ONES


        PATTERNS:
        ---------
        1) Only opposite signs will collide
        2) [-5, 2, 10] --> here -5 will never collide with anything
        3) So, trav from r to l.
        4) [-1, 2, -3, 4] --> only 2 will collide with -3. And -1, and 4 will never collide
        5) Focus only on -ves in r to l traversal
        6) [-1,-2,3,4] --> nothing collides
        7) [1,2,-3,-4] --> first -3 with 2. And then -3 with 1
        8) [4,3,-2,-1] --> -2 with 3 --> [4,3,-1] --> -1 with 3 --> [4,3]
        9) Carry -ves in one in
        10) Stack for arr?
        11) if 1st pop is -ve then collide and 2nd pop is +ve then collide and save the res in list
        12) Save -ve in stack

     */
    public int[] asteroidCollisionMyApproachNotWorking(int[] asteroids) {
        List<Integer> lst = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        int n = asteroids.length;
        for(int i=n-1; i>=0; i--) {

            int curr = asteroids[i];
            if(curr < 0) stack.push(curr);
            else if (curr > 0 && !stack.isEmpty()){ // collide -ve with +ve
                int peek = stack.peek();
                if (curr == Math.abs(peek)) {
                    stack.pop();
                    continue;
                }
                if(curr>Math.abs(peek)) {
                    lst.add(curr);
                    stack.pop();
                }

            } else if (curr > 0) {
                lst.add(curr);
            }
            // System.out.println("curr: " + curr);
            // System.out.println("lst: " + lst);
            // System.out.println("stack: " + stack);
        }
        reverse(lst, 0, lst.size()-1);
        // collide all -ves
        int i = -1;
        while(!stack.isEmpty()){
            lst.add(0, stack.pop());
            i++;
        }
        reverse(lst, 0, i);

        return lst.stream().mapToInt(x->x).toArray();
    }

    private void reverse(List<Integer> lst, int l, int r) {
        while(l<r){
            //swap(lst, l, r);
            int temp = lst.get(l);
            lst.set(l, lst.get(r));
            lst.set(r, temp);
            l++;
            r--;
        }
    }
}
