package Algorithms.GreedyAlgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 23 Nov 2024
 */
public class MinimumNumberOfSwapsToSort {
    public static void main(String[] args) {
        int[] arr = new int[]{2, 3, 88, 3, 4, 8, 5}, nonContiguousArr = new int[]{6, 3, 1, 2, 4, 5};
        int[] temp = nonContiguousArr.clone();
        System.out.println("Contiguous numbers minSwaps of " + Arrays.toString(temp));
        System.out.println("minSwaps using brute force: " + minSwapsBruteForce(temp));
        temp = nonContiguousArr.clone();
        System.out.println("minSwaps using HashMap: " + minSwapsUsingHashMap(temp));
        temp = nonContiguousArr.clone();
        System.out.println("minSwaps using List<Map.Entry>: " + minSwapsUsingMapEntryList(temp));

        temp = arr.clone();
        System.out.println("\nNon-contiguous numbers minSwaps of " + Arrays.toString(temp) );
        System.out.println("minSwaps4: " + minSwaps4(temp));
        temp = arr.clone();
        System.out.println("minSwaps5: " + minSwaps5(temp));
        temp = arr.clone();
        System.out.println("minSwaps6: " + minSwaps6(temp));
        temp = arr.clone();
        System.out.println("minSwaps7: " + minSwaps7(temp));
    }

/**
 * Contiguous Numbers ------------------------------------------------------------------------------
 *
 * Must start from 1
 * No duplicates
 *
*/

    //
    public static int minSwapsBruteForce(int[] arr) {
        int minSwaps = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != i + 1) {
                for (int j = i; j < arr.length; j++) {
                    if (arr[j] == i + 1) {
                        swap(arr, i, j);
                        minSwaps++;
                        break;
                    }
                }
            }
        }
        return minSwaps;
    }

    public static int minSwapsUsingHashMap(int[] arr) {
        int swaps = 0;
        HashMap<Integer, Integer> map = new HashMap<>(); // {expected num, curr num} ==> num=curr
        boolean[] visited = new boolean[arr.length + 1];

        for (int i = 1; i <= arr.length; i++)
            map.put(i, arr[i-1]); // [1, 4, 3, 2] --> {1=1, 2=4, 3=3, 4=2}, we know that hashMap sorts the keys by default

        for (int num = 1; num <= arr.length; num++) { // expected num
            if (!visited[num]) {
                visited[num] = true;
                int curr = map.get(num);

                if (num == curr) // is num at correct position?
                    continue;
                else {
                    // [1, 4, 3, 2]
                    // {1=1, 2=4, 3=3, 4=2} ---> exp num = curr
                    // num=2, curr=4, next=2
                    while (!visited[curr]) {
                        visited[curr] = true;
                        int next = map.get(curr); // if curr != expected num, then check what's in that curr's position current number, i.e while loop until we visited all next nums
                        curr = next;
                        swaps++;
                    }
                }
            }
        }
        return swaps;
    }

    public static int minSwapsUsingMapEntryList(int[] arr) {
        int swaps = 0;
        List<Map.Entry<Integer, Integer>> lst = new ArrayList<>(); // List<{num, index}>
        //---> so we have 3 properties here - list index or sorted index, expected num and given index

        for (int i = 0; i < arr.length; i++)
            lst.add(Map.entry(arr[i], i)); // by default the keys i.e nums are sorted
        lst.sort(Comparator.comparing(Map.Entry::getKey)); // or lst.sort((o1, o2) -> o1.getKey() - o2.getKey()); sort by needed nums

        /*
             0, 1, 2, 3, 4, 5   ----> indices
            [6, 3, 1, 2, 4, 5]  ----> nums

              0    1    2    3    4    5   ----> indices of lst
            [1=2, 2=3, 3=1, 4=4, 5=5, 6=0]  ----> lst swap 0
            [3=1*, 2=3, 1=2*, 4=4, 5=5, 6=0]  ----> lst swap 1
            [2=3*, 3=1*, 1=2, 4=4, 5=5, 6=0]  ----> lst swap 2
            [4=4*, 3=1, 1=2, 2=3*, 5=5, 6=0]  ----> lst swap 3
            [5=5*, 3=1, 1=2, 2=3, 4=4*, 6=0]  ----> lst swap 4
            [6=0*, 3=1, 1=2, 2=3, 4=4, 5=5*]  ----> lst swap 5

        */

        // check how many swaps needed to convert the lst to before sort
        for (int i = 0; i < lst.size(); i++) {
            Map.Entry<Integer, Integer> entry = lst.get(i);
            if (entry.getValue() != i ) { // lst sorted - given index != lst index
                System.out.println(lst);
                // swap(arr[], i, j)
                int givenIndex = entry.getValue();
                lst.set(i, lst.get(givenIndex));
                lst.set(givenIndex, entry);

                swaps++;
                i--; // maintain same lst index until we obtain the exact swap i.e (lst num == arr num) i.e back to original form before sorting the lst
            }
        }
        System.out.println(lst);
        return swaps;
    }

    public static int minSwapsForContiguousOrder2(int[] arr) {
        int minimumSwaps = 0;
        for (int i = 0; i < arr.length; i++) {
            int current = arr[i];
            while(current != i + 1){
                int temp = arr[current - 1];
                arr[current - 1] = current;
                arr[i] = temp;
                minimumSwaps++;
                current = temp;
            }
        }
        return minimumSwaps;
    }

    public static int minSwapsForContiguousOrder3(int[] arr) {
        int i = 0;
        int swap = 0;
        while( i < arr.length ) {
            if (arr[i] == i+1)
            i += 1;
            else{
                int index = arr[i] -1;
                swap(arr, i, index);
                swap += 1;

            }
        }
        return swap;
    }

    public static int minSwapsForContiguousOrder4(int[] arr) {
        boolean isSorted = false;
        int curr = 0; // current pointer index
        int swaps = 0;

        while (!isSorted) {
            if (curr == arr.length) {
                isSorted = true;
                break;
            }

            if (curr+1 == arr[curr])
                curr += 1;
            else {
                int ind = arr[curr] - 1;
                swap(arr, curr, ind);
                swaps += 1;
            }
        }

        return swaps;
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static class Pair { // like 'cpp STL <utility> header - pair container' or Map.entry() or AbstractMap.SimpleEntry
        int first, second;

        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    private static int indexOf(int[] arr, int ele) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == ele) {
                return i;
            }
        }
        return -1;
    }

/**
 * Non-Contiguous numbers ------------------------------------------------------------------------------
 *
 * No duplicates
 * No contiguity
 */

    /**
     * @TimeComplexity O(nlogn)
     */
    public static int minSwaps4(int[] arr) {
        int n = arr.length;

        // Create two arrays and use as pairs where first array is element and second array is position of first element
        ArrayList<Pair> arrPos = new ArrayList<Pair>();
        for (int i = 0; i < n; i++)
            arrPos.add(new Pair(arr[i], i));

        arrPos.sort( new Comparator<Pair>() { // or arrPos.sort((o1, o2) -> o1.first - o2.first);
            @Override
            public int compare( Pair o1, Pair o2) {
                if (o1.first < o2.first)
                    return -1;
                else if (o1.first == o2.first)
                    return 0;
                else
                    return 1;
            }
        });

        // To keep track of visited elements. Initialize all elements as not visited or false.
        Boolean[] vis = new Boolean[n];
        Arrays.fill(vis, false);

        // Initialize result
        int ans = 0;

        // Traverse array elements
        for (int i = 0; i < n; i++) {
            // already swapped and corrected or already present at correct pos
            if (vis[i] || arrPos.get(i).second == i)
                continue;

            // find out the number of  node in this cycle and add in ans
            int cycle_size = 0;
            int j = i;
            while (!vis[j]) {
                vis[j] = true;

                // move to next node
                j = arrPos.get(j).second;
                cycle_size++;
            }

            // Update answer by adding current cycle.
            if (cycle_size > 0) {
                ans += (cycle_size - 1);
            }
        }

        // Return result
        return ans;
    }

    /**
     * @TimeComplexity O(nlogn)
     */
    public static int minSwaps5(int[] nums) {
        int len = nums.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < len; i++)
            map.put(nums[i], i);

        Arrays.sort(nums);

        // To keep track of visited elements. Initialize all elements as not visited or false.
        boolean[] visited = new boolean[len];
        Arrays.fill(visited, false);

        // Initialize result
        int ans = 0;
        for (int i = 0; i < len; i++) {

            // already swapped and corrected or already present at correct pos
            if (visited[i] || map.get(nums[i]) == i)
                continue;

            int j = i, cycle_size = 0;
            while (!visited[j]) {
                visited[j] = true;

                // move to next node
                j = map.get(nums[j]);
                cycle_size++;
            }

            // Update answer by adding current cycle.
            if (cycle_size > 0) {
                ans += (cycle_size - 1);
            }
        }
        return ans;
    }

    /**
     * @TimeComplexity: O(N^2)
     */
    public static int minSwaps6(int[] arr) {
        int ans = 0;
        int N = arr.length;
        int[] temp = Arrays.copyOfRange(arr, 0, N);
        Arrays.sort(temp);
        for (int i = 0; i < N; i++) {

            // This is checking whether the current element is at the right place or not
            if (arr[i] != temp[i]) {
                ans++;

                // Swap the current element with the right index so that arr[0] to arr[i] is sorted
                swap(arr, i, indexOf(arr, temp[i]));
            }
        }
        return ans;
    }

    /**
     * @TimeComplexity O(nlogn)
    */
    public static int minSwaps7(int[] arr) {
        int ans = 0;
        int N = arr.length;
        int[] temp = Arrays.copyOfRange(arr, 0, N);

        // Hashmap which stores the indexes of the input array
        HashMap<Integer, Integer> h = new HashMap<Integer, Integer>();

        Arrays.sort(temp);
        for (int i = 0; i < N; i++) {
            h.put(arr[i], i);
        }
        for (int i = 0; i < N; i++) {

            // This is checking whether the current element is at the right place or not
            if (arr[i] != temp[i]) {
                ans++;
                int init = arr[i];

                // If not, swap this element with the index of the element which should come here
                swap(arr, i, h.get(temp[i]));

                // Update the indexes in the hashmap accordingly
                h.put(init, h.get(temp[i]));
                h.put(temp[i], i);
            }
        }
        return ans;
    }
}
