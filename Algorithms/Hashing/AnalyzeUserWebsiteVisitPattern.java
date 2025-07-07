package Algorithms.Hashing;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 06 July 2025
 * @link 1152. Analyze User Website Visit Pattern <a href="https://leetcode.com/problems/analyze-user-website-visit-pattern/">Leetcode link</a>
 * @description Return the same pattern of maximum users
 * @topics Array, Hash Table, String, Sorting
 * @companies Amazon, Google, Uber, Spotify, DoorDash
 */
public class AnalyzeUserWebsiteVisitPattern {
    public static void main(String[] args) {
        String[] username = { "joe", "joe", "joe", "james", "james", "james", "james", "mary", "mary", "mary" };
        int[] timestamp = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        String[] website = { "home", "about", "career", "home", "cart", "maps", "home", "home", "about", "home" };
        System.out.println("mostVisitedPattern => " + mostVisitedPattern(username, timestamp, website));
        System.out.println("mostVisitedPatternMyApproach => " + mostVisitedPatternMyApproach(username, timestamp, website));
    }


    /**
     * INTUITION:
     * 1) sort the websites by time order -> logs[time, website]
     * 2) Assign the websites of each user -> Map<String, List<String>> userVisits
     * 2) Use "Map<String, Set<String>> patternUsers" global variable to store all unique patterns of all users as keys
     * 2) -> and assign the key's value as set of usernames who are eligible under a specific pattern
     * 3) Before doing that,
     */
    public static List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        int n = username.length;
        List<int[]> logs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            logs.add(new int[]{timestamp[i], i}); // i is the index to refer username and website
        }

        // Sort "logs[time, website]" by timestamp
        logs.sort(Comparator.comparingInt(a -> a[0]));

        // Map of user -> list of websites in time order
        Map<String, List<String>> userVisits = new HashMap<>();
        for (int[] log : logs) {
            String user = username[log[1]];
            String site = website[log[1]];
            userVisits.computeIfAbsent(user, k -> new ArrayList<>()).add(site);
        }

        // Pattern -> Set of users who visited this pattern
        Map<String, Set<String>> patternUsers = new HashMap<>();

        // same like backtracking() --> and note that we don't need patternCounter for each user, we just need unique patterns of each user
        for (String user : userVisits.keySet()) {
            List<String> visits = userVisits.get(user);
            int size = visits.size();

            // we need only the websites of size 3 - possible patterns
            for (int i = 0; i < size; i++) {
                for (int j = i + 1; j < size; j++) {
                    for (int k = j + 1; k < size; k++) {
                        String pattern = visits.get(i) + "," + visits.get(j) + "," + visits.get(k);
                        patternUsers.computeIfAbsent(pattern, p -> new HashSet<>()).add(user);
                    }
                }
            }
        }

        // Find the pattern with max users, break ties with lex order
        String result = "";
        int maxCount = 0;
        for (String pattern : patternUsers.keySet()) {
            int count = patternUsers.get(pattern).size();
            if (maxCount < count || (count == maxCount && pattern.compareTo(result) < 0)) {
                /*
                s1.compareTo(s2) gives the lexicographical (dictionary) order comparison in Java
                String a = "a,b,c";
                String b = "a,b,d";
                System.out.println(a.compareTo(b));  // Output: -1 ===> a is smaller
                System.out.println(b.compareTo(a));  // Output: 1
                So, no need for PriorityQueue or separate sort
                */
                maxCount = count;
                result = pattern;
            }
        }

        return Arrays.asList(result.split(","));
    }










    /**
     * My custom Brute force approach
     * WORKING -> all test cases are passing, but slow


        username = ["joe",  "joe",  "joe",    |  "james",    "james",    "james",    "james",  |  "mary", "mary", "mary"]
        website =  ["home", "about","career", |  "home",     "cart",     "maps",     "home",   |  "home", "about","career"]
        timestamp = [1,         2,      3,    |      4,          5,          6,          7,    |      8,      9,      10]


        INTUTION:
        1) RETURN: maxFrequency of comman pattern in all users / max users
        2) First group the list by username
        3) Sort the each userName group by time
        4) Backtracking each users website patterns --> (n^2)
        5) store each users pattern in a specific map and all patterns for all users in set for easy traversal
        6) Now iterate each pattern from set
        7) Check that set pattern is present in all users --> get users count
        8) store all maxCount patterns in a temporary list
        9) Now as these patterns have same maxFrequency for multiple patterns then return the lexicographically smallest such pattern --> if we have apple and banana -> then return apple. ---> using PriorityQueue sort


     */
    public static List<String> mostVisitedPatternMyApproach(String[] username, int[] timestamp, String[] website) {
        int n = username.length;
        Map<String, List<Object[]> > userWebsites = new HashMap<>(); // username : [[time1, website1], [time2, website2], .....]
        Map<String, Map<String, Integer>> userPatterns = new HashMap<>();
        Set<String> allPatterns = new HashSet<>();
        for(int i=0; i<n; i++) {
            userWebsites.computeIfAbsent(username[i], k->new ArrayList<>()).add(new Object[]{timestamp[i], website[i]});
        }

        for(String user: userWebsites.keySet()){
            List<Object[]> list = userWebsites.get(user);
            list.sort(Comparator.comparingInt(a -> (int) a[0]));

            // calculate all unique patterns with size "3"

            Map<String, Integer> patternCounter = new HashMap<>();
            backtrack(list, patternCounter, 0, new ArrayList<>(), allPatterns);
            userPatterns.put(user, patternCounter);
        }

        int maxCount = 0;
        List<String> maxFreqPatterns = new ArrayList<>();
        for(String pattern: allPatterns) {
            int count = 0;
            for(String user: userPatterns.keySet()) {
                if(userPatterns.get(user).keySet().stream().anyMatch(x-> x.equals(pattern))) {
                    count++;
                }
            }
            if(maxCount < count) {
                maxFreqPatterns = new ArrayList<>();
                maxFreqPatterns.add(pattern);
                maxCount = count;
            } else if (maxCount == count) {
                maxFreqPatterns.add(pattern);
            }
        }

        PriorityQueue<String> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        for(String pattern: maxFreqPatterns) {
            maxHeap.offer(pattern);
            if(maxHeap.size()>1) {
                maxHeap.poll();
            }
        }
        return Arrays.asList(maxHeap.poll().split(","));
    }

    private static void backtrack(List<Object[]> list,  Map<String, Integer> patternCounter, int i, List<String> subList, Set<String> allPatterns) {
        if(subList.size() == 3) {
            String pattern = String.join(",", subList);
            patternCounter.merge(pattern, 1, Integer::sum);
            allPatterns.add(pattern);
            return;
        }
        if(i>=list.size()) {
            return;
        }

        for(int start = i; start<list.size(); start++) {
            subList.add( String.valueOf( list.get(start)[1] ) );
            backtrack(list, patternCounter, start+1, subList, allPatterns);
            subList.remove(subList.size()-1);
        }
    }


}
