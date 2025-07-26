package Algorithms.StackAlgos;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 18 April 2025
 * @link 735. Asteroid Collision <a href="https://leetcode.com/problems/asteroid-collision/">LeetCoode Link</a>
 * @topics Array, Stack, Simulation, Weekly Contest 60
 * @companies Amazon, Meta, Microsoft, Oracle, Dream11, Nuro, DoorDash, Qualtrics, Google, Goldman Sachs, Bloomberg, Accolite, Nvidia, PayPal, Salesforce, OpenAI, TikTok, Apple, Flipkart, Adobe, IMC, SoFi, Sprinklr, Myntra, Deutsche Bank, Zoho
 */
public class AsteroidCollision {
    public static void main(String[] args) {
        int[] asteroids = new int[]{5, 10, -5}; // => [5, 10]
        System.out.println("asteroidCollision using +ve Stack and -ve Stack: " + Arrays.toString(asteroidCollisionUsingPositiveAndNegativeStacks(asteroids)));
        System.out.println("asteroidCollision using Single Stack: " + Arrays.toString(asteroidCollisionUsingSingleStack(asteroids)));
        System.out.println("asteroidCollision using int[]: " + Arrays.toString(asteroidCollisionUsingIntArray(asteroids)));
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int[] asteroidCollisionUsingPositiveAndNegativeStacks(int[] asteroids) { // asteroidCollisionUsingPositiveAndNegativeStack --> My Approach - 26 July 2025
        int n = asteroids.length;
        Stack<Integer> posStack = new Stack<>();
        Stack<Integer> negStack = new Stack<>();
        for(int curr: asteroids) {
            if (curr > 0) {
                posStack.add(curr);
            } else {
                curr *= -1;
                negStack.push(curr);
                while(!posStack.isEmpty()) {
                    if(posStack.peek() == curr) { // both destroyed
                        posStack.pop();
                        negStack.pop();
                        break;
                    } else if (posStack.peek() < curr) { // smaller +ve destroyed
                        posStack.pop();
                    } else { // smaller -ve destroyed
                        negStack.pop();
                        break;
                    }
                }
            }
        }

        int[] remaining = new int[negStack.size() + posStack.size()];
        int i=remaining.length-1;
        while(!posStack.isEmpty()) {
            remaining[i--] = posStack.pop();
        }
        while(!negStack.isEmpty()) {
            remaining[i--] = negStack.pop()*-1;
        }
        return remaining;
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int[] asteroidCollisionUsingSingleStack(int[] asteroids) { // asteroidCollisionUsingSingleStack
        Stack<Integer> stack = new Stack<Integer>();

        for (int asteroid : asteroids) {
            boolean isDestroyed = false;
            while (!stack.isEmpty() && stack.peek() > 0 && asteroid < 0) { // while(p>0 && a<0)
                if (stack.peek() == Math.abs(asteroid)) { // both +p -a destroyed
                    stack.pop();
                }
                else if (stack.peek() < Math.abs(asteroid)) { // small +p destroyed
                    stack.pop();
                    continue;
                }

                isDestroyed = true;
                break;
            }

            if (!isDestroyed) { // add all +ves by default && -a bigger ones
                stack.push(asteroid);
            }
        }

        int[] remainingAsteroids = new int[stack.size()];
        for (int i = remainingAsteroids.length - 1; i >= 0; i--) {
            remainingAsteroids[i] = stack.peek();
            stack.pop();
        }

        return remainingAsteroids;
    }







    public static int[] asteroidCollisionUsingSingleStack2(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();

        for (int a : asteroids) {
            // -a, +p
            while (!stack.isEmpty() && a < 0 && stack.peek() > 0) {
                int diff = a + stack.peek();
                if (diff < 0) { // 'p' destroyed
                    stack.pop();
                } else if (diff > 0) { // Current asteroid 'a' is destroyed
                    a=0; // or break;
                } else {
                    stack.pop(); // Both asteroids destroy each other
                    a=0; // or break;
                }
            }

            // +a or while loop scenario -> add the current asteroid if not destroyed
            if (a != 0) stack.push(a);
            /*
            // or
             if (stack.isEmpty() || a > 0) {
                 stack.push(a); // Add the current asteroid if not destroyed
             } else if (a < 0 && stack.peek() < 0) {
                 stack.push(a); // Add the current asteroid if it's negative and the top of the stack is also negative
             }
             */
        }

        int[] result = new int[stack.size()];
        for (int i = stack.size() - 1; i >= 0; i--) result[i] = stack.pop();
        return result;
    }






    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int[] asteroidCollisionUsingIntArray(int[] asteroids) {
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
      * Working great but the code complexity is very high

    // move -ves to left side
    // asteroidCollisionUsingPositiveAndNegativeStack --> My Approach - 20 April 2025

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
    public static int[] asteroidCollisionMyApproachOld(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();

        for(int a: asteroids) {
            if(a>0) stack.push(a); // +a
            else { // -a
                if(stack.isEmpty()) {
                    stack.push(a);
                } else {
                    if(stack.peek() < 0) stack.push(a);
                    else { // stack is +ve and -a. so, collide all left +ves with is -a
                        while(!stack.isEmpty() && stack.peek()>0) {
                            int p = stack.pop();
                            if(a+p == 0) break; // -a == +p --> both destroyed
                            else if (a+p > 0) { // +p big --> -a destroyed
                                stack.push(p);
                                break;
                            }
                            else { // +p small, -a big
                                if(stack.isEmpty() || stack.peek()<0) { // empty/-ve stack
                                    stack.push(a);
                                    break;
                                }
                            }

                            // or
                        //     else if(stack.isEmpty() || stack.peek()<0) { // +p small, -a big. so, collide all left +ves with is -a && empty/-ve stack
                        //        stack.push(a);
                        //        break;
                        //    }

                        }
                    }
                }
            }
        }
        int[] res = new int[stack.size()];
        for(int i=res.length-1; i>=0; i--) res[i]=stack.pop();
        return res;
    }
}
