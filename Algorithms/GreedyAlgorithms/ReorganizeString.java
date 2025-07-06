package Algorithms.GreedyAlgorithms;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 06 July 2025
 * @link 767. Reorganize String <a href="https://leetcode.com/problems/reorganize-string/">Leetcode link</a>
 * @description reorganize a string such that no two adjacent characters are the same
 * @topics Array, Greedy, Heap(Priority Queue), String, Hash Table, Sorting
 * @companies amazon, roblox, google, microsoft, zoho, goldman, facebook, tiktok, pinterest, oracle, bloomberg, adobe, apple, uber, paypal, tesla
 */
public class ReorganizeString {
    public static void main(String[] args) {
        String s = "aabbcc";
        System.out.println("reorganizeString using Counting and Priority Queue => " + reorganizeStringUsingCountingAndPriorityQueue(s));
        System.out.println("reorganizeString using Counting OddEven => " + reorganizeStringUsingCountingOddEven(s));

    }

    /**
     * @TimeComplexity O(N⋅logk), where N is the length of s and k is the number of distinct characters in s.
     * @SpaceComplexity O(N)

        INTUITION:
        We fill the chars as per frequency and sort it
        even we consumed a char then we do "frequency--" then append back the "char-frequency" again
        this will re-sort the maxHeap and maintains the bigger frequencies at start

        So, use pq to always maintain bigger frequency at the start
     */
    public static String reorganizeStringUsingCountingAndPriorityQueue(String s) {
        int[] charCounts = new int[26]; // or HashMap --> but inserting "a-z" in int[26] like a bucket sort -> but here we store frequencyCount
        for (char c : s.toCharArray()) {
            charCounts[c - 'a']++;
        }

        // Max heap ordered by character counts
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> Integer.compare(b[1], a[1])); // or (a, b) -> b[1] != a[1] ? b[1] - a[1] : a[0] - b[0]
        for (int i = 0; i < 26; i++) {
            if (charCounts[i] > 0) {
                pq.offer(new int[] {i + 'a', charCounts[i]});
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            int[] first = pq.poll(); // bigger frequency
            if (sb.isEmpty() || first[0] != sb.charAt(sb.length() - 1)) { // if same char then hold till then the next char is inserted
                sb.append((char) first[0]);
                if (--first[1] > 0) {
                    pq.offer(first);
                }
            } else {
                if (pq.isEmpty()) {
                    return "";
                }

                int[] second = pq.poll(); // second bigger frequency
                sb.append((char) second[0]);
                if (--second[1] > 0) {
                    pq.offer(second);
                }

                pq.offer(first);
            }
        }

        return sb.toString();
    }










    /**
        ---> This is a greedy + heap combo. Greedy picks highest freq at every step, heap helps you always get that dynamically.
        Why this works:
        1. At each step, grab the most frequent char not equal to the previous one.
        2. Keep track of previous char (delayed re-insertion logic).
        3, Priority dynamically changes as you reduce frequencies.
        4. Guarantees max separation between same characters.
        5. We use "prev" --> cause it ensures that the same character is not used twice in a row.
        6. Even if current[1] > 0, it won't go back into the heap immediately.
        7. It only re-enters the heap after one turn of delay via prev.
    */
    public static String reorganizeStringUsingCountingAndPriorityQueue2(String s) {
        Map<Character, Integer> charFreq = new HashMap<>();
        for (char c : s.toCharArray()) {
            charFreq.put(c, charFreq.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[1] - a[1]); // or (a, b) -> b[1] != a[1] ? b[1] - a[1] : a[0] - b[0]
        for (Map.Entry<Character, Integer> entry : charFreq.entrySet()) {
            maxHeap.offer(new int[]{entry.getKey(), entry.getValue()});
        }

        StringBuilder res = new StringBuilder();
        int[] prev = new int[]{'#', 0};

        while (!maxHeap.isEmpty()) {
            int[] current = maxHeap.poll();
            res.append((char) current[0]);

            if (prev[1] > 0) {
                maxHeap.offer(prev);
            }

            current[1]--;
            prev = current;
        }


        if (res.length() != s.length()) {
            return "";
        }

        return res.toString();
    }


    /**
     * @TimeComplexity O(N)
     * @SpaceComplexity O(N)
     */
    public static String reorganizeStringUsingCountingOddEven(String s) {
        int[] charCounter = new int[26]; // inserting "a-z" in int[26] is a bucket sort -> but here we store frequencyCount
        for (char c : s.toCharArray()) {
            charCounter[c - 'a']++;
        }
        
        int maxCharCount = 0, maxChar = 0;
        for (int i = 0; i < charCounter.length; i++) {
            if (maxCharCount < charCounter[i]) {
                maxCharCount = charCounter[i];
                maxChar = i;
            }
        }

        if (maxCharCount > (s.length() + 1) / 2) { // more than half? --> then it's impossible to have a reorganized string
            return "";
        }

        char[] ans = new char[s.length()];
        int index = 0;

        // Place the most frequent maxChar int every alternate position ---> all odd positions
        while (charCounter[maxChar] != 0) {
            ans[index] = (char) (maxChar + 'a');
            index += 2;
            charCounter[maxChar]--;
        }

        // Place rest of the letters in any order --> alternate positions ---> continue from odd positions till  (index >= s.length()), then continue from even positions i.e from index = 1;
        // here don't start form index=1, cause to avoid edge case like "cbcbcgci▯k▯l▯m▯▯▯▯▯▯" when "ccccmmmbboostwgxizkl"
        for (int i = 0; i < charCounter.length; i++) {
            while (charCounter[i] > 0) {
                if (index >= s.length()) {
                    index = 1;
                }
                ans[index] = (char) (i + 'a');
                index += 2;
                charCounter[i]--;
            }
        }

        return String.valueOf(ans);
    }








    public String reorganizeStringUsingCountingAndPriorityQueue3(String s) {
        int n = s.length();
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        for (int f : freq) {
            if (f > (n + 1) / 2) return "";
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);

        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0) {
                pq.offer(new int[]{i, freq[i]}); // [char index, freq]
            }
        }

        StringBuilder sb = new StringBuilder();

        while (pq.size() >= 2) {
            int[] a = pq.poll();
            int[] b = pq.poll();

            sb.append((char)(a[0] + 'a'));
            sb.append((char)(b[0] + 'a'));

            if (--a[1] > 0) pq.offer(a);
            if (--b[1] > 0) pq.offer(b);
        }

        if (!pq.isEmpty()) {
            int[] last = pq.poll();
            if (last[1] > 1) return "";
            sb.append((char)(last[0] + 'a'));
        }

        return sb.toString();
    }











    /**
     * same as {@link #reorganizeStringUsingCountingAndPriorityQueue} but we didn't insert back the char to array
        INTUITION:
        Sort all chars by frequency
        loop till l=1st frequency char, r=2nd frequency char --> one of char finishes
        and repeat the loop

        failing for "aabbcc" --> this logic is preparing "ababcc" but expected "abacbc"
     */
    public String reorganizeStringNotWorking1(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int n = s.length();
        for(int i=0; i<n; i++) {
            map.merge(s.charAt(i), 1, Integer::sum);
        }
        if(map.size() < 2) { // only one unique char present
            return "";
        }

        int[][] arr = new int[map.size()][2]; // use pq to solve the issue --> insert back the char with new freq to maintain frequency order
        int i=0;
        for(char c: map.keySet()) {
            arr[i][0] = c - 'a';
            arr[i++][1] = map.get(c);
        }
        Arrays.sort(arr, Comparator.comparingInt(a -> -a[1]));

        /*
            vvvllo
            vl
            vlvl
            vlvlvo
        */
        int l=0, r=1;
        n=arr.length;
        StringBuilder sb = new StringBuilder();

        while(l<n && r<n) {
            char c1 = (char)('a' + arr[l][0]);
            char c2 = (char)('a' + arr[r][0]);

            while(arr[l][1]>0 && arr[r][1]>0) {
                sb
                .append((sb.length()==0 || c1!=sb.charAt(sb.length()-1))?c1:c2)
                .append(c2!=sb.charAt(sb.length()-1)?c2:c1);
                arr[l][1]--;
                arr[r][1]--;
            }
            if(arr[l][1]==0 && arr[r][1]==0) {
                l=r+1;
                r=l+1;
            } else if(arr[l][1]==0 && arr[r][1]>0) {
                l = r;
                r = l+1;
            } else if(arr[l][1]>0 && arr[r][1]==0) {
                r++;
            }
        }

        if(l<n) {
            if(arr[l][1]>1) {
                return "";
            } else {
                sb.append((char)('a' + arr[l][0]));
            }
        } else if(r<n) {
            if(arr[r][1]>1) {
                return "";
            } else {
                sb.append((char)('a' + arr[r][0]));
            }
        }

        return sb.toString();
    }









    /**
        INTUITION: Sort chars by frequency,
                    now add l = 0th char and r = (n-1)th char
                    now add l = 1st char and r = (n-2)th char
                    now add l = 2nd char and r = (n-3)th char
                    ... till l==r



        not working for "ogccckcwmbmxtsbmozli" cause this logic returns
        "clckczcimxmgmwbtbsoo" -> ""
        but we can form "cmcmcmcbobostwgxizkl" or "cocgcickmlmsmtbwbxoz"
    */
    public String reorganizeStringNotWorking2(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int n = s.length();
        for(int i=0; i<n; i++) {
            map.merge(s.charAt(i), 1, Integer::sum);
        }

        int[][] arr = new int[map.size()][2];
        int i=0;
        for(char c: map.keySet()) {
            arr[i][0] = c - 'a';
            arr[i++][1] = map.get(c);
        }
        Arrays.sort(arr, Comparator.comparingInt(a -> -a[1]));

        StringBuilder ogs = new StringBuilder();

        for(i=0; i<arr.length; i++) {
            ogs.append( String.valueOf((char)(arr[i][0]+'a')).repeat(arr[i][1]) );
        }

        Arrays.stream(arr).forEach(x->System.out.println(Arrays.toString(x)));
        System.out.println(ogs);

        StringBuilder sb = new StringBuilder();
        int l=0, r=ogs.length()-1;
        while(l<=r) {
            if(l==r){
                sb.append(ogs.charAt(l));
                break;
            }
            char c1 = ogs.charAt(l);
            char c2 = ogs.charAt(r);
            if(c1 == c2){

            }
            sb.append(ogs.charAt(l)).append(ogs.charAt(r));
            l++;
            r--;
        }
        return sb.toString();
    }











    /**
        INTUITION: A loop to append all unique chars to a new String
                    and then repeat the loop process till no chars left
        "vvvlo" -> should return "vlvov" not "vlovv"
     */
    public String reorganizeStringNotWorking3(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int n = s.length();
        for(int i=0; i<n; i++) {
            map.merge(s.charAt(i), 1, Integer::sum);
        }

        StringBuilder sb = new StringBuilder();
        while(!map.isEmpty()) {
            char c1 = map.keySet().iterator().next();
            char c2 = sb.isEmpty() ? '\u0000' : sb.charAt(sb.length()-1);
            // System.out.printf(arr: %s, c2: %s, map: %s, sb: %s\n", c1, c2, map, sb);
            // System.out.printf("c1: %s, c2: %s, map: %s, sb: %s\n", c1, c2, map, sb);
            if(c1 == c2) {
                return "";
            }
            Iterator<Character> it = map.keySet().iterator(); // to avoid ConcurrentModificationException
            while(it.hasNext()) {
                char c = it.next();
                sb.append(c);
                map.merge(c, -1, Integer::sum);
                if(map.get(c) == 0) {
                    it.remove();  // safe removal via iterator. map.remove(c); will throw ConcurrentModificationException
                }
            }
        }

        return sb.toString();
    }


}
