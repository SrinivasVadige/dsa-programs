package Algorithms.MonotonicStack;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 14 October 2026
 * @link GFG Next Greater Element <a href="https://www.geeksforgeeks.org/problems/next-larger-element-1587115620/1">GFG link</a>
 * @link GFG Next Greater Element Article <a href="https://www.geeksforgeeks.org/next-greater-element/">GFG Article</a>
 * @topics Stack
 * @companies Flipkart, Amazon, Microsoft, MakeMyTrip, Adobe
 * @description Given an array of integers, find the next greater element for each element.

Example 1:
Input: arr = [1, 2, 1]
Output: [2, -1, -1]
Output: [4,3]


Example 2:
Input: arr = [1, 3, 2, 4]
Output: [3, 4, 4, -1]




Monotonic Stack maintains elements in either strictly increasing or strictly decreasing order -> i.e no duplicates
Monotonic means permanently sorted in a single direction
So, to keep this monotonic property we might need to pop the top ele in order to insert the current ele

Two types
1. Monotonically Increasing stack, Eg: [2, 4, 7]
2. Monotonically Decreasing stack, Eg: [9, 6, 3]

How this monotonic stack works:
It works in 3 steps => 1. compare top, 2. pop if needed, 3. push(newEle)

    Step 1: COMPARE TOP:
        Before .push(newEle) compare the newEle with top .peek() ele

    Step 2: POP IF NEEDED: (to maintain the incre / decre monotonic order)
        For increasing stack, pop() till the top .peek() ele is < newEle
        For decreasing stack, pop() till the top .peek() ele is > newEle

    Step 3: PUSH NEW_ELE
        now .push(newEle)

USE CASES:

    🔥Monotonic stack is used to get the next greater/smaller element in the original sequence, not in a sorted sequence.

    Next / Previous GREATER Element -> Monotonic DECREASING Stack
    Next / Previous SMALLER Element -> Monotonic INCREASING Stack

    1. Next (or Previous) Greater (or Smaller) Element from the original sequence: Find the next (or previous) element greater (or smaller) than the current element in the array efficiently, in O(n) time  (in just one pass) - NGE(NextGreater), NSE(NextSmaller), PGE(PreviousGreater), PSE(PreviousSmaller)
    2. Daily Temperatures: Given temperatures & return an array answer such that answer[i] is the number of days you have to wait after the ith day to get a warmer temperature. temperatures = [73,74,75,71,69,72,76,73] output: [1,1,4,2,1,1,0,0]
    4. Largest Rectangle in Histogram: Calculate the area of the largest rectangle in a histogram using decreasing monotonic stack
    5. Trapping Rain Water: (Histogram Related Problems left and right boundaries for each bar) Compute how much water can be trapped between bars representing an elevation map
    6. Lexicographically Smallest/Greatest


    // Values-based stack (Right to Left) <-
    for (int i = n - 1; i >= 0; i--) {
        while (!stack.isEmpty() && stack.peek() <= arr[i]) { stack.pop(); }
        result[i] = stack.isEmpty() ? -1 : stack.peek(); // arr[i] is biggest if stack.isEmpty()
        stack.push(arr[i]);
    }

    // Index-based stack (Left to Right) ->
    for (int i = 0; i < n; i++) {
        while (!indexStack.isEmpty() && arr[i] > arr[indexStack.peek()]) {
            int prevIdx = indexStack.pop();
            result[prevIdx] = arr[i]; // or (i - prevIdx) for distance-based problems
        }
        indexStack.push(i);
    }

  It looks like minHeap and maxHeap, but it is not a heap. And we don't maintain the all elements
  in the stack, we only maintain the indices of the elements that are only required i.e increasing or decreasing order.

  NOTE:
  1. It's recommended to keep the indices of given array in the stack instead of the actual values.
  2. Using Monotonic Stack, we can solve the problem in O(n) time complexity instead of O(n^2) time complexity Brute Force approach.




 */
public class NextGreaterElement {
    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 4};

        System.out.printf("nextLargerElement Using BruteForce QuadraticTime_TLE: %s%n", nextLargerElementUsingBruteForceQuadraticTime_TLE(arr));

        System.out.printf("nextLargerElement Using MonotonicallyDecreasingStack RTL ByValues: %s%n", nextLargerElementUsingMonotonicallyDecreasingStack_RTL_ByValues(arr));
        System.out.printf("nextLargerElement Using MonotonicallyDecreasingStack RTL ByIndexes: %s%n", nextLargerElementUsingMonotonicallyDecreasingStack_RTL_ByIndexes(arr));
        System.out.printf("nextLargerElement Using MonotonicallyDecreasingStack LTR ByIndexes: %s%n", nextLargerElementUsingMonotonicallyDecreasingStack_LTR_ByIndexes(arr));

        System.out.printf("nextLargerElement Using Dp: %s%n", nextLargerElementUsingDp(arr));

        System.out.printf("nextLargerElement Using SelfBalancingBST: %s%n", nextGreaterUsingSelfBalancingBST(arr));

        System.out.printf("nextLargerElement Using SegmentTree: %s%n", nextGreaterUsingSegmentTree(arr));

        System.out.printf("nextLargerElement Using DivideAndConquer_TLE: %s%n", nextGreaterUsingDivideAndConquer_TLE(arr));
        System.out.printf("nextLargerElement Using DivideAndConquer: %s%n", nextGreaterUsingDivideAndConquer(arr));

        System.out.printf("nextLargerElement UsingUnionFindDSU: %s%n", nextLargerElementUsingUnionFindDSU(arr));

        System.out.printf("nextLargerElement UsingMinHeap: %s%n", nextGreaterUsingMinHeap(arr));

        System.out.printf("nextLargerElement Using SparseTable: %s%n", nextGreaterUsingSparseTable(arr));

        System.out.printf("nextLargerElement Using FenwickTreeOrBinaryIndexedTree: %s%n", nextGreaterUsingFenwickTreeOrBinaryIndexedTree(arr));
    }






	/**
	* Brute Force (Nested Loops)
	*
	* @TimeComplexity O(N^2)
	* @SpaceComplexity O(1)
	*/
	public static ArrayList<Integer> nextLargerElementUsingBruteForceQuadraticTime_TLE(int[] arr) {
		int n = arr.length;
		ArrayList<Integer> res = new ArrayList<>(); ;

		for (int i = 0; i<n; i++) {
			int j = i + 1;
			while (j<n && arr[i] >= arr[j])
				j++;
			if (j == n)
				res.add(-1);
			else
				res.add(arr[j]);
		}

		return res;
	}

	/**
	0 1 2 3
	[1, 3, 2, 4]

	i = 3, num = 4, s = [] => res = [-1, -1, -1, -1], s = [4]
	i = 2, num = 2, s = [4] => res = [-1, -1, 4, -1], s = [4, 2]
	i = 1, num = 3, s = [4, 2] => s = [4] => res = [-1, 4, 4, -1], s = [4, 3]
	i = 0, num = 1, s = [4, 3] => res = [3, 4, 4, -1], s = [4, 3]

	* Monotonic Stack (Right - to - Left)
	*
	* @TimeComplexity O(N)
	* @SpaceComplexity O(N)
	*/
	public static ArrayList<Integer> nextLargerElementUsingMonotonicallyDecreasingStack_RTL_ByValues(int[] arr) {
		int n = arr.length;
		ArrayList<Integer> res = new ArrayList<>();
		for (int i = 0; i<n; i++)
			res.add(-1);

		Stack<Integer> stack = new Stack<>();

		for (int i = n - 1; i >= 0; i--) {
			while (!stack.isEmpty() && stack.peek() <= arr[i]) stack.pop(); // remove all smaller and dups

			if (stack.isEmpty()) res.set(i, -1); // OPTIONAL CHECK: current arr[i] is the biggest & we already set all eles to -1 by defatult
			else
				res.set(i, stack.peek());

			stack.push(arr[i]);
		}

		return res;
	}

	/**
	0 1 2 3
	[1, 3, 2, 4]

	i = 3, num = 4, s = [] => res = [-1, -1, -1, -1], s = [3:4]
	i = 3, num = 2, s = [3:4] => res = [-1, -1, 4, -1], s = [3:4, 2:2]
	i = 2, num = 3, s = [3:4, 2:2] => s = [3:4] res = [-1, 4, 4, -1], s = [3:4, 1:3]
	i = 0, num = 1, s = [3:4, 1:3] => res = [3, 4, 4, -1], s = [4, 3]

	* Monotonic Stack (Right - to - Left)
	*
	* @TimeComplexity O(N)
	* @SpaceComplexity O(N)
	*/
	public static ArrayList<Integer> nextLargerElementUsingMonotonicallyDecreasingStack_RTL_ByIndexes(int[] arr) {
		int n = arr.length;
		ArrayList<Integer> res = new ArrayList<>();
		for (int i = 0; i<n; i++)
			res.add(-1);

		Stack<Integer> indexStack = new Stack<>();

		for (int i = n - 1; i >= 0; i--) {
			while (!indexStack.isEmpty() && arr[indexStack.peek()] <= arr[i])
				indexStack.pop();

			if (indexStack.isEmpty()) res.set(i, -1); // OPTIONAL CHECK: current arr[i] is the biggest & we already set all eles to -1 by defatult
			else
				res.set(i, arr[indexStack.peek()]);

			indexStack.push(i);
		}

		return res;
	}

	/**
	0 1 2 3
	[1, 3, 2, 4]

	i = 0, num = 1, s = [] => res = [-1, -1, -1, -1], s = [0:1]
	i = 1, num = 3, s = [0:1] => s = [] - res = [3, -1, -1, -1] res = [3, -1, -1, -1], s = [1:3]
	i = 2, num = 2, s = [1:3] => res = [3, -1, -1, -1], s = [1:3, 2:2]
	i = 3, num = 4, s = [1:3, 2:2] => s = [1:3] - res = [3, -1, 4, -1] => s = [] - res = [3, 4, 4, -1] res = [3, 4, 4, -1], s = [4, 3]

	* Monotonic Stack (Right - to - Left)
	*
	* @TimeComplexity O(N)
	* @SpaceComplexity O(N)
	*/
	public static ArrayList<Integer> nextLargerElementUsingMonotonicallyDecreasingStack_LTR_ByIndexes(int[] arr) {
		int n = arr.length;
		ArrayList<Integer> res = new ArrayList<>();
		for (int i = 0; i<n; i++)
			res.add(-1);

		Stack<Integer> indexStack = new Stack<>();

		for (int i = 0; i<n; i++) {
			while (!indexStack.isEmpty() && arr[indexStack.peek()] < arr[i]) { // set the same arr[i] value in multiple prevIndexes
				int prevI = indexStack.pop();
				res.set(prevI, arr[i]);
			}

			indexStack.push(i);
		}

		return res;
	}













	/**
	* Dynamic Programming / Jump Pointers
	*
	* @TimeComplexity O(N)
	* @SpaceComplexity O(1)
	*/
	public static ArrayList<Integer> nextLargerElementUsingDp(int[] arr) {
		int n = arr.length;
		int[] res = new int[n];
		Arrays.fill(res, -1);

		// Rightmost element has no next greater element
		res[n - 1] = -1;

		for (int i = n - 2; i >= 0; i--) {
			int nextIdx = i + 1;

			// Jump using previously computed answers
			while (nextIdx != -1 && arr[nextIdx] <= arr[i]) {
				// Find where arr[nextIdx]'s next greater element was stored
				// (Assuming we stored indices in res, or map values back)
				nextIdx = findNextGreaterIndex(arr, res, nextIdx);
			}

			res[i] = (nextIdx == -1) ? -1 : arr[nextIdx];
		}
		return new ArrayList<>(Arrays.stream(res).boxed().collect(java.util.stream.Collectors.toList()));
	}

	private static int findNextGreaterIndex(int[] arr, int[] res, int currIdx) {
		int targetVal = res[currIdx];
		if (targetVal == -1)
			return - 1;

		// Linearly find the index corresponding to res[currIdx] starting from currIdx + 1
		for (int j = currIdx + 1; j < arr.length; j++) {
			if (arr[j] == targetVal)
				return j;
		}
		return - 1;
	}












	/**
	* Self - Balancing Binary Search Tree (TreeSet)
	*
	* @TimeComplexity O(N log N)
	* @SpaceComplexity O(N)
	*/
	public static ArrayList<Integer> nextGreaterUsingSelfBalancingBST(int[] arr) {
		int n = arr.length;
		ArrayList<Integer> res = new ArrayList<>();
		for (int i = 0; i < n; i++)
			res.add(-1);

		// Store pairs: {value, index}, sorted strictly by index ascending
		TreeSet<int[]> set = new TreeSet<>((a, b) -> Integer.compare(a[1], b[1]));

		for (int i = n - 1; i >= 0; i--) {
			// Remove all elements in the set that are <= current element arr[i]
			// This ensures the set only contains strictly greater elements to the right
			final int currentValue = arr[i];
			set.removeIf(pair -> pair[0] <= currentValue);

			// Find the element with the smallest index strictly greater than 'i'
			int[] candidate = set.higher(new int[] {Integer.MIN_VALUE, i});

			if (candidate != null) {
				res.set(i, candidate[0]);
			}

			set.add(new int[] {arr[i], i});
		}

		return res;
	}













	private static int[] tree;
	private static int n;
	/**
	* Segment Tree (Range Maximum Query + Binary Search)
	*
	* @TimeComplexity O(N log N)
	* @SpaceComplexity O(N)
	*/
	public static ArrayList<Integer> nextGreaterUsingSegmentTree(int[] arr) {
		n = arr.length;
		if (n == 0)
			return null;

		tree = new int[4 * n];
		build(arr, 0, 0, n - 1);

		int[] res = new int[n];
		for (int i = 0; i < n; i++) {
			int targetIndex = queryFirstGreater(0, 0, n - 1, i + 1, n - 1, arr[i]);
			res[i] = (targetIndex == -1) ? -1 : arr[targetIndex];
		}
		return new ArrayList<>(Arrays.stream(res).boxed().collect(java.util.stream.Collectors.toList()));
	}

	private static void build(int[] arr, int node, int start, int end) {
		if (start == end) {
			tree[node] = arr[start];
			return;
		}
		int mid = start + (end - start) / 2;
		build(arr, 2 * node + 1, start, mid);
		build(arr, 2 * node + 2, mid + 1, end);
		tree[node] = Math.max(tree[2 * node + 1], tree[2 * node + 2]);
	}

	private static int queryFirstGreater(int node, int start, int end, int ql, int qr, int val) {
		// Out of range or range max is not strictly greater
		if (ql > end || qr < start || tree[node] <= val)
			return - 1;

		if (start == end)
			return start;

		int mid = start + (end - start) / 2;
		int leftRes = queryFirstGreater(2 * node + 1, start, mid, ql, qr, val);
		if (leftRes != -1) return leftRes; // Found in left child range

		return queryFirstGreater(2 * node + 2, mid + 1, end, ql, qr, val);
	}














	/**
	* Divide & Conquer (with Stack Merge Step)
	*
	* @TimeComplexity O(N^2)
	* @SpaceComplexity O(N)
	*/
	public static ArrayList<Integer> nextGreaterUsingDivideAndConquer_TLE(int[] arr) {
		int n = arr.length;
		ArrayList<Integer> res = new ArrayList<>();
		for (int i = 0; i < n; i++)
			res.add(-1);

		solveDC(arr, 0, n - 1, res);
		return res;
	}

	private static void solveDC(int[] arr, int left, int right, ArrayList<Integer> res) {
		if (left >= right)
			return;

		int mid = left + (right - left) / 2;

		// 1. Solve left and right halves recursively
		solveDC(arr, left, mid, res);
		solveDC(arr, mid + 1, right, res);

		// 2. Cross-boundary resolution: resolve elements in [left...mid] using candidates in [mid+1...right]
		for (int i = left; i <= mid; i++) {
			if (res.get(i) == -1) {
				for (int j = mid + 1; j <= right; j++) {
					if (arr[j] > arr[i]) {
						res.set(i, arr[j]);
						break;
					}
				}
			}
		}
	}












	/**
	* Divide & Conquer (with Stack Merge Step)
	*
	* @TimeComplexity O(N log N)
	* @SpaceComplexity O(N)
	*/
	public static ArrayList<Integer> nextGreaterUsingDivideAndConquer(int[] arr) {
		int n = arr.length;
		ArrayList<Integer> res = new ArrayList<>();
		for (int i = 0; i < n; i++)
			res.add(-1);

		solveDC(arr, res, 0, n - 1);
		return res;
	}

	private static void solveDC(int[] arr, ArrayList<Integer> res, int left, int right) {
		if (left >= right)
			return;

		int mid = left + (right - left) / 2;

		// 1. Solve left and right halves recursively
		solveDC(arr, res, left, mid);
		solveDC(arr, res, mid + 1, right);

		// 2. Optimized Cross-Boundary Step using a local Right-to-Left Stack
		// Build candidate stack from [mid + 1 ... right]
		Deque<Integer> stack = new ArrayDeque<>();
		for (int j = right; j >= mid + 1; j--) {
			while (!stack.isEmpty() && stack.peek() <= arr[j]) {
				stack.pop();
			}
			stack.push(arr[j]);
		}

		// Resolve unresolved elements in [left ... mid]
		for (int i = left; i <= mid; i++) {
			if (res.get(i) == -1) {
				// Find first element in candidate stack > arr[i]
				for (int val : stack) {
					if (val > arr[i]) {
						res.set(i, val);
						break;
					}
				}
			}
		}
	}











	/**
	* Union - Find / Disjoint Set Union (DSU)
	*
	* @TimeComplexity O(N log N)
	* @SpaceComplexity O(N)
	*/
	public static ArrayList<Integer> nextLargerElementUsingUnionFindDSU(int[] arr) {
		int n = arr.length;
		ArrayList<Integer> res = new ArrayList<>();
		for (int i = 0; i < n; i++)
			res.add(-1);

		// Store pairs: {value, originalIndex}
		int[][] elements = new int[n][2];
		for (int i = 0; i < n; i++) {
			elements[i] = new int[] {arr[i], i};
		}

		// Sort ASCENDING by value.
		// If values are equal, sort DESCENDING by index so rightmost duplicates are processed first.
		Arrays.sort(elements, (a, b) -> {
			if (a[0] != b[0])
				return Integer.compare(a[0], b[0]);
			return Integer.compare(b[1], a[1]);
		});

		// parent[i] stores the next available index to the right
		int[] parent = new int[n + 1];
		for (int i = 0; i <= n; i++)
			parent[i] = i;

		// Process from SMALLEST value to LARGEST value
		for (int[] elem : elements) {
			int idx = elem[1];

			// Query DSU for the first available position to the right
			int nextAvailableIdx = dsuFind(parent, idx + 1);

			if (nextAvailableIdx < n) {
				res.set(idx, arr[nextAvailableIdx]);
			}

			// Union index with index + 1 so smaller elements skip past this position
			parent[idx] = dsuFind(parent, idx + 1);
		}

		return res;
	}

	private static int dsuFind(int[] parent, int i) {
		if (parent[i] == i)
			return i;
		return parent[i] = dsuFind(parent, parent[i]); // Path compression
	}











    /**
     * Priority Queue / Min-Heap
     *
     * @TimeComplexity O(N log N)
     * @SpaceComplexity O(N)
     */
    public static ArrayList<Integer> nextGreaterUsingMinHeap(int[] arr) {
        int n = arr.length;
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) res.add(-1);

        // Min-heap ordered by element value ascending
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));

        for (int i = 0; i < n; i++) {
            // Resolve answers for all waiting elements in heap smaller than arr[i]
            while (!minHeap.isEmpty() && minHeap.peek()[0] < arr[i]) {
                int[] pair = minHeap.poll();
                res.set(pair[1], arr[i]);
            }
            minHeap.offer(new int[]{arr[i], i});
        }

        return res;
    }








    /**
     * Sparse Table (Range Maximum Query) + Binary Search
     *
     * @TimeComplexity O(N log N)
     * @SpaceComplexity O(N log N)
     */
    public static ArrayList<Integer> nextGreaterUsingSparseTable(int[] arr) {
        int n = arr.length;
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) res.add(-1);

        if (n == 0) return res;

        int logN = (int) (Math.log(n) / Math.log(2)) + 1;
        int[][] st = new int[n][logN];

        // Build Sparse Table for Range Maximum Query
        for (int i = 0; i < n; i++) st[i][0] = arr[i];

        for (int j = 1; (1 << j) <= n; j++) {
            for (int i = 0; i + (1 << j) - 1 < n; i++) {
                st[i][j] = Math.max(st[i][j - 1], st[i + (1 << (j - 1))][j - 1]);
            }
        }

        // Binary search for the first index > i with value > arr[i]
        for (int i = 0; i < n - 1; i++) {
            int low = i + 1, high = n - 1, targetIdx = -1;

            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (queryMax(st, i + 1, mid) > arr[i]) {
                    targetIdx = mid;
                    high = mid - 1; // Search left for first occurrence
                } else {
                    low = mid + 1;
                }
            }

            if (targetIdx != -1) {
                res.set(i, arr[targetIdx]);
            }
        }

        return res;
    }

    private static int queryMax(int[][] st, int L, int R) {
        int length = R - L + 1;
        int k = (int) (Math.log(length) / Math.log(2));
        return Math.max(st[L][k], st[R - (1 << k) + 1][k]);
    }








    /**
     * Binary Indexed Tree (Fenwick Tree) + Coordinate Compression
     *
     * @TimeComplexity O(N log N)
     * @SpaceComplexity O(N)
     */
    public static ArrayList<Integer> nextGreaterUsingFenwickTreeOrBinaryIndexedTree(int[] arr) {
        int n = arr.length;
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) res.add(-1);

        if (n == 0) return res;

        // 1. Coordinate Compression: sorted unique values
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        int uniqueCount = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0 || sorted[i] != sorted[i - 1]) {
                sorted[uniqueCount++] = sorted[i];
            }
        }

        // 2. BIT stores the MINIMUM original index for each rank
        int[] bit = new int[uniqueCount + 2];
        Arrays.fill(bit, Integer.MAX_VALUE);

        // 3. Process Right-to-Left
        for (int i = n - 1; i >= 0; i--) {
            // Rank 1 is the LARGEST value, so larger values have SMALLER ranks
            int rank = uniqueCount - getZeroBasedIndex(sorted, uniqueCount, arr[i]);

            // Query BIT prefix [1 ... rank - 1] for minimum index
            int minIdx = queryBitPrefixMin(bit, rank - 1);

            if (minIdx != Integer.MAX_VALUE) {
                res.set(i, arr[minIdx]);
            }

            // Update BIT at 'rank' with current index 'i'
            updateBit(bit, rank, i);
        }

        return res;
    }

    // Binary search for 0-based index in sorted unique array
    private static int getZeroBasedIndex(int[] sorted, int uniqueCount, int val) {
        int low = 0, high = uniqueCount - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (sorted[mid] == val) return mid;
            if (sorted[mid] < val) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }

    // Standard BIT update: propagates minimum index upward
    private static void updateBit(int[] bit, int idx, int val) {
        for (; idx < bit.length; idx += idx & -idx) {
            bit[idx] = Math.min(bit[idx], val);
        }
    }

    // Standard BIT prefix query: checks [1 ... idx] for minimum index
    private static int queryBitPrefixMin(int[] bit, int idx) {
        int minIdx = Integer.MAX_VALUE;
        for (; idx > 0; idx -= idx & -idx) {
            minIdx = Math.min(minIdx, bit[idx]);
        }
        return minIdx;
    }
}
