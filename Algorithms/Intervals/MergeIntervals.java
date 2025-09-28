package Algorithms.Intervals;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 18 March 2025
 * @link 56. Merge Intervals <a href="https://leetcode.com/problems/merge-intervals/">LeetCode Link</a>
 * @topics Array, Sorting, Trees
 * @companies Meta, Amazon, Google, Microsoft, Apple, Bloomberg, Zoho, Grammarly, Oracle, Darwinbox, Tesla, Hubspot, Walmart Labs, Citadel, IBM, Yandex, TikTok, Visa, Netflix, IXL, Tesco, Salesforce, Adobe, Roblox, LinkedIn, Yahoo, J.P. Morgan, Goldman Sachs, Atlassian, Cisco
 */
public class MergeIntervals {
    public static void main(String[] args) {
        int[][] intervals = {{1, 4}, {5, 6}};
        System.out.println("merge Using Sort & List => ") ;
        intervals = mergeUsingSortAndList(intervals); for (int[] i : intervals) System.out.print(Arrays.toString(i));

        System.out.println("merge Using Sort & Array => ") ;
        intervals = mergeUsingSortAndArray(intervals); for (int[] i : intervals) System.out.print(Arrays.toString(i));

        System.out.println("\n\nmerge Using Min-Offset Greedy Range Mapping => ") ;
        intervals = mergeUsingMinOffsetGreedyRangeMapping(intervals); for (int[] i : intervals) System.out.print(Arrays.toString(i));

        System.out.println("\n\nmerge Using Greedy Linear Interval Extension via Start Mapping => ") ;
        intervals = mergeUsingGreedyLinearIntervalExtensionViaStartMapping(intervals); for (int[] i : intervals) System.out.print(Arrays.toString(i));

        System.out.println("\n\nmerge using custom binary interval partitioning tree => "); ;
        intervals = new int[][]{{1, 4}, {5, 6}}; for (int[] interval : mergeUsingCustomBinaryIntervalPartitioningTree(intervals)) System.out.print(Arrays.toString(interval));
    }


    public static int[][] mergeUsingSortAndList(int[][] intervals) { // mergeUsingSortAndList
        Arrays.sort(intervals, Comparator.comparingInt(a->a[0]));

        List<int[]> res = new ArrayList<>();

        for(int[] interval: intervals) {

            if(res.isEmpty()) {
                res.add(interval);
                continue;
            }

            int[] prev = res.get(res.size()-1);

            if(prev[1] < interval[0]) {
                res.add(interval);
            } else {
                // prev[0] = Math.min(prev[0], interval[0]); // not required, as the prev[0] is always <= interval[0]
                prev[1] = Math.max(prev[1], interval[1]);
            }
        }

        return res.toArray(new int[res.size()][]); // or res.toArray(int[][]::new)
    }





    public static int[][] mergeUsingSortAndList2(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        List<int[]> merged = new ArrayList<>();

        int[] current = intervals[0];

        for (int i = 1; i < intervals.length; i++) {
            int[] next = intervals[i];

            if (current[1] >= next[0]) { // overlap
                current[1] = Math.max(current[1], next[1]);
            } else {
                merged.add(current);
                current = next;
            }
        }

        merged.add(current); // add the last merged interval
        return merged.toArray(new int[merged.size()][]);
    }






    public static int[][] mergeUsingSortAndArray(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        int[][] res = new int[intervals.length][2];
        res[0] = intervals[0]; // set first arr
        int idx = 0; // res index
        for (int[] arr: intervals) { // or start from i=1
            int start = arr[0], end = arr[1]; // current start and end
            int lastEnd = res[idx][1]; // recent end in res

            if (start <= lastEnd) {  // overlapping [1,5], [3,8]
                res[idx][1] = Math.max(lastEnd, end);
            }
            else { // non-overlapping [1,5], [8,10]
                res[++idx] = arr;
            }

        }
        return Arrays.copyOfRange(res, 0, idx+1);
    }





    public static int[][] mergeUsingSortAndDeque(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        LinkedList<int[]> merged = new LinkedList<>(); // Deque
        for (int[] interval : intervals) {
            if (merged.isEmpty() || merged.getLast()[1] < interval[0]) {
                merged.add(interval);
            }
            else {
                merged.getLast()[1] = Math.max(merged.getLast()[1], interval[1]);
            }
        }
        return merged.toArray(int[][]::new); // or .toArray(new int[merged.size()][]); or .toArray(new int[merged.size()][2]); or .toArray(new int[0][]); or .toArray(new int[0][2]);
    }





    public static int[][] mergeUsingSortAndStack(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        Stack<int[]> stack = new Stack<>();
        stack.push(intervals[0]);

        for(int[] interval: intervals) {
            if(stack.peek()[1] < interval[0]){
                stack.push(interval);
            } else {
                stack.peek()[1] = Math.max(stack.peek()[1], interval[1]); // [[1,4],[2,3]]
            }
        }

        return stack.toArray(int[][]::new);

        /*
        // or
        int[][] mergedIntervals = new int[stack.size()][2];
        int i=0;
        while(!stack.isEmpty()) {
            mergedIntervals[i++] = stack.pop();
        }
        return mergedIntervals;
         */
    }





    public int[][] mergeUsingSortAndMinHeapPQ(int[][] intervals) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        int[] prev = {-1,-1};
        pq.addAll(Arrays.asList(intervals));
        List<int[]> lst = new ArrayList<>();

        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            if(curr[0]>prev[1]) {
                lst.add(curr);
                prev = curr;
            } else {
                prev[1] = Math.max(prev[1], curr[1]);
            }
        }

        return lst.toArray(new int[lst.size()][]);
    }









    /**
     * @TimeComplexity O(N + R), where R = Range = maxStart - minStart + 1
     * @SpaceComplexity O(R)
     * Offset-Based Range Compression using Min-Shifted Mapping
     * or Shifted Bucket Merge
     * or Interval Compression via Min-Normalized Start Indexing
     */
    public static int[][] mergeUsingMinOffsetGreedyRangeMapping(int[][] intervals) {
        int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;

        for (int[] interval : intervals) {
            min = Math.min(min, interval[0]);
            max = Math.max(max, interval[0]);
        }

		int[] range = new int[max - min + 1];
        for (int[] interval : intervals) {
            range[interval[0]-min] = Math.max(range[interval[0]-min], interval[1]-min);
        }

		int start = 0, end = 0;
		LinkedList<int[]> result = new LinkedList<>();
		for (int i = 0; i < range.length; i++) {
			if (range[i] == 0) {
				continue;
			}
			if (i <= end) {
				end = Math.max(range[i], end);
			} else {
				result.add(new int[] {start + min, end + min});
				start = i;
				end = range[i];
			}
		}
		result.add(new int[] {start + min, end + min});
		return result.toArray(new int[result.size()][]);
    }







    /**
     * @TimeComplexity O(N+K), where K is max start
     * @SpaceComplexity O(K)
     * Greedy Linear Interval Extension via Start Mapping
     * or Start-Mapped Timeline Sweep
     * or Sparse Bucketed Interval Merge
     */
    public static int[][] mergeUsingGreedyLinearIntervalExtensionViaStartMapping(int[][] intervals) {
        int max = 0;
        for (int[] interval : intervals) {
            max = Math.max(interval[0], max);
        }

        int[] mp = new int[max + 1];
        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];
            mp[start] = Math.max(end + 1, mp[start]);
        }

        int r = 0;
        int have = -1;
        int intervalStart = -1;
        for (int i = 0; i < mp.length; i++) {
            if (mp[i] != 0) {
                if (intervalStart == -1) intervalStart = i;
                have = Math.max(mp[i] - 1, have);
            }
            if (have == i) {
                intervals[r++] = new int[] {intervalStart, have};
                have = -1;
                intervalStart = -1;
            }
        }

        if (intervalStart != -1) {
            intervals[r++] = new int[] {intervalStart, have};
        }
        if (intervals.length == r) {
            return intervals;
        }

        int[][] res = new int[r][];
        System.arraycopy(intervals, 0, res, 0, r);
        return res;
    }


    /**
     * @TimeComplexity O(NlogN) best case and O(N^2) worst case
     * @SpaceComplexity O(N^2)
     * custom binary interval partitioning tree
        Phase	        Best / Avg	    Worst
        Build Tree	    O(N log N)	    O(N²)
        Query Tree	    O(N)	        O(N)
        Total	        O(N log N)	    O(N²)
     */
    static class TreeNode {
        int start, end, middle; TreeNode left, right; // class vars are null by default
        TreeNode(int start, int end) {this.start = start; this.end = end; middle = start+(end-start)/2;}
    }
    public static int[][] mergeUsingCustomBinaryIntervalPartitioningTree(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return new int[0][];

        TreeNode root = null;

        // Step 1: Build a binary interval partitioning tree --- using add()
        for (int[] interval : intervals) {
            int start = interval[0], end = interval[1];
            if (root == null) {
                root = new TreeNode(start, end);
            } else {
                add(root, start, end);
            }
        }

        // Step 2: Query the binary interval partitioning tree --- using query()
        List<int[]> merged = query(root);

        return merged.toArray(new int[merged.size()][]);
    }

    private static void add(TreeNode node, int start, int end) {
        if (end < node.middle) {                            // <- left
            if (node.left != null) {
                add(node.left, start, end);
            } else {
                node.left = new TreeNode(start, end);
            }
        } else if (start > node.middle) {                    // -> right
            if (node.right != null) {
                add(node.right, start, end);
            } else {
                node.right = new TreeNode(start, end);
            }
        } else {  // (end >= node.middle && start <= node.middle) - Merge into current node -- If start & end are overlapping, expand the curr node's interval
            node.start = Math.min(node.start, start);
            node.end = Math.max(node.end, end);
        }
    }

    private static List<int[]> query(TreeNode node) {
        if (node == null) return new ArrayList<>();

        List<int[]> leftIntervals = query(node.left);
        List<int[]> rightIntervals = query(node.right);
        List<int[]> res = new ArrayList<>();

        boolean inserted = false;

        for (int[] lRes : leftIntervals) {
            if (lRes[1] < node.start) {
                res.add(lRes);
            } else {
                res.add(new int[]{Math.min(lRes[0], node.start), node.end});
                inserted = true;
                break;
            }
        }

        if (!inserted) {
            res.add(new int[]{node.start, node.end});
        }

        for (int[] rRes : rightIntervals) {
            int[] last = res.get(res.size() - 1);
            if (rRes[0] <= last[1]) {
                last[1] = Math.max(last[1], rRes[1]);
            } else {
                res.add(rRes);
            }
        }

        return res;
    }












    // Connected components graph TLE
    private Map<int[], List<int[]>> graph;
    private Map<Integer, List<int[]>> nodesInComp;
    private Set<int[]> visited;
    public int[][] mergeUsingConnectedComponentsGraphTLE(int[][] intervals) {
        buildGraph(intervals);
        buildComponents(intervals);

        // for each component, merge all intervals into one interval.
        List<int[]> merged = new LinkedList<>();
        for (int comp = 0; comp < nodesInComp.size(); comp++) {
            merged.add(mergeNodes(nodesInComp.get(comp)));
        }

        return merged.toArray(new int[merged.size()][]);
    }

    // return whether two intervals overlap (inclusive)
    private boolean overlap(int[] a, int[] b) {
        return a[0] <= b[1] && b[0] <= a[1];
    }

    // build a graph where an undirected edge between intervals u and v exists
    // iff u and v overlap.
    private void buildGraph(int[][] intervals) {
        graph = new HashMap<>();
        for (int[] interval : intervals) {
            graph.put(interval, new LinkedList<>());
        }

        for (int[] interval1 : intervals) {
            for (int[] interval2 : intervals) {
                if (overlap(interval1, interval2)) {
                    graph.get(interval1).add(interval2);
                    graph.get(interval2).add(interval1);
                }
            }
        }
    }

    // merges all of the nodes in this connected component into one interval.
    private int[] mergeNodes(List<int[]> nodes) {
        int minStart = nodes.get(0)[0];
        for (int[] node : nodes) {
            minStart = Math.min(minStart, node[0]);
        }

        int maxEnd = nodes.get(0)[1];
        for (int[] node : nodes) {
            maxEnd = Math.max(maxEnd, node[1]);
        }

        return new int[] { minStart, maxEnd };
    }

    // use depth-first search to mark all nodes in the same connected component
    // with the same integer.
    private void markComponentDFS(int[] start, int compNumber) {
        Stack<int[]> stack = new Stack<>();
        stack.add(start);

        while (!stack.isEmpty()) {
            int[] node = stack.pop();
            if (!visited.contains(node)) {
                visited.add(node);

                nodesInComp.computeIfAbsent(compNumber, k -> new LinkedList<>());
                nodesInComp.get(compNumber).add(node);

                stack.addAll(graph.get(node));
            }
        }
    }

    // gets the connected components of the interval overlap graph.
    private void buildComponents(int[][] intervals) {
        nodesInComp = new HashMap<>();
        visited = new HashSet<>();
        int compNumber = 0;

        for (int[] interval : intervals) {
            if (!visited.contains(interval)) {
                markComponentDFS(interval, compNumber);
                compNumber++;
            }
        }
    }









    public static int[][] mergeMyApproachOld(int[][] intervals) {
        int n=intervals.length;
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        System.out.println();
        int startI = 0;
        List<List<Integer>> lst = new ArrayList<>();
        while(startI<n) {
            int l = intervals[startI][0], r=intervals[startI][1];
            int rCopy=r;
            if (startI==0) startI++;
            int tempI = startI;
            for(int i=startI; i<n; i++) {
                if(l>=intervals[i][0] || intervals[i][0]<=r) {
                    r=Math.max(r,intervals[i][1]);
                    tempI=i+1;
                } else break;
            }
            lst.add(Arrays.asList(l,r));
            startI=(tempI==startI && r!=rCopy)? startI+1 : tempI;
        }

        int min = Integer.MAX_VALUE, max= Integer.MIN_VALUE;
        for(int[] x: intervals) {
            min = Math.min(min, x[0]);
            max = Math.max(max, x[1]);
        }
        if(lst.isEmpty() || lst.get(0).get(0)!=min || lst.get(lst.size()-1).get(1)!=max) return intervals;

        int[][] res=new int[lst.size()][2];
        for(int i=0; i<res.length; i++)
            res[i]=new int[]{lst.get(i).get(0), lst.get(i).get(1)};
        return res;
    }
}
