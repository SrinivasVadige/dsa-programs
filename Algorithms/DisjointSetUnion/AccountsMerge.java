package Algorithms.DisjointSetUnion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 24 March 2025
 *
 * PATTERNS:
 * ---------
 * 1. A person can have multiple emails and multiple accounts
 * 2. We can have multiple accounts with same name
 * 3. At last merge only the accounts with same name && common emails
 * 4. Different named persons don't have common emails, that's for sure
 */
public class AccountsMerge {

    public static void main(String[] args) {
        List<List<String>> accounts = new ArrayList<>();
        accounts.add(Arrays.asList("John", "John1@gmail.com", "John2@gmail.com"));
        accounts.add(Arrays.asList("Mary", "Mary1@gmail.com"));
        accounts.add(Arrays.asList("John", "John2@gmail.com"));
        accounts.add(Arrays.asList("John", "John3@gmail.com"));

        System.out.println("Accounts Merge -> \n" + accountsMerge(accounts));
        System.out.println("Accounts Merge My Approach -> \n" + accountsMergeMyApproach(accounts));
        System.out.println("Accounts Merge but TLE for long N -> \n" + accountsMergeTLE(accounts));
    }


    public static List<List<String>> accountsMerge(List<List<String>> accounts) {
        List<List<String>> result = new ArrayList<>();
        UnionFind unionFind = new UnionFind(accounts.size());

        // emailToAccIdx map => email -> account index
        // No need to group accounts with same name and iterate that group like accountsMergeMyApproach()
        Map<String, Integer> emailToAccIdx = new HashMap<>(); // <email, accountIndex> with union()
        for (int i = 0; i < accounts.size(); i++) {
            List<String> account = accounts.get(i);
            for (int j = 1; j < account.size(); j++) { // cause 0th index is name
                String email = account.get(j);
                if (!emailToAccIdx.containsKey(email)) {
                    emailToAccIdx.put(email, i);
                } else { // email already exists i.e an edge
                    unionFind.union(i, emailToAccIdx.get(email)); // currI, first occurrence of email
                }
            }
        }


        // leaderGrp map => leader account index -> emails
        Map<Integer, Set<String>> leaderGrp = new HashMap<>(); // <leaderAccIdx, emails> with find()
        for (Map.Entry<String, Integer> entry: emailToAccIdx.entrySet()) {
            int accIdx = entry.getValue();
            String email = entry.getKey();
            int leaderAccIdx = unionFind.find(accIdx);
            leaderGrp.putIfAbsent(leaderAccIdx, new HashSet<>());
            leaderGrp.get(leaderAccIdx).add(email);
        }


        // Prepare result
        for (Map.Entry<Integer, Set<String>> entry: leaderGrp.entrySet()) {
            int i = entry.getKey();
            String accName = accounts.get(i).get(0);
            List<String> emails = new ArrayList<>(entry.getValue());
            Collections.sort(emails);
            emails.add(0, accName);
            result.add(emails);
        }
        return result;
    }

    static class UnionFind {
        int[] parent;
        int[] rank;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }

        public int find(int x) {
            while (parent[x] != x) {
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return x;
        }
        public int find2(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x1, int x2) {
            int p1 = find(x1);
            int p2 = find(x2);
            if (p1 == p2) return;
            if (rank[p1] < rank[p2]) parent[p1] = p2;
            else if (rank[p1] > rank[p2]) parent[p2] = p1;
            else {
                parent[p2] = p1;
                rank[p1]++;
            }
        }
    }










    static int[] par, rank;
    static Map<Integer, Set<Integer>> comps = new HashMap<>();
    /**
        PATTERNS:
        ---------
        1. Merge only the accounts with same name
        2. Now do Union-Find for that accName
     */
    public static List<List<String>> accountsMergeMyApproach(List<List<String>> accounts) {
        // prepare map --> map each mail with unique accName (accounts that have same name)
        Map<String, List<List<String>>> map = new HashMap<>();
        for (List<String> acc: accounts) {
            String accName = acc.get(0);
            List<String> currMails = new ArrayList<>();
            for (int j=0; j<acc.size(); j++) {
                if (j==0) map.putIfAbsent(accName, new ArrayList<>());
                else currMails.add(acc.get(j));
            }
            map.get(accName).add(currMails);
        }

        // EACH ACCOUNT (SAME NAME)
        // k=accName, v=sameNameMails1, sameNameMails2, ....
        List<List<String>> lst = new ArrayList<>();
        for (Map.Entry<String, List<List<String>>> e: map.entrySet()){
            String accName = e.getKey();
            List<List<String>> mailsGroups = e.getValue();
            int n = mailsGroups.size();

            // prepare edges
            Map<String, Integer> mailIndexMap = new HashMap<>(); // map mail to it's first index occurrence
            List<List<Integer>> edges = new ArrayList<>(); // add if you find the same mail again
            for (int i=0; i<n; i++) {
                for(String mail: mailsGroups.get(i)) {
                    if (mailIndexMap.containsKey(mail)) {
                        int u = mailIndexMap.get(mail); // keep first occurrence as root in disjoint set
                        edges.add(Arrays.asList(u,i)); // same mail found in diff account --> so, those two accounts are connected
                    } else {
                        mailIndexMap.put(mail, i);
                    }
                }
            }
            // union-find
            par=new int[n];
            rank=new int[n];
            comps.clear();
            for(int i=0; i<n; i++) par[i]=i;
            for (List<Integer> edge: edges) union(edge.get(0), edge.get(1));

            // add to lst
            for (int i=0; i<par.length; i++) {
                // get all disjoint set mails
                Set<String> subSet = new TreeSet<>();
                // comps that are not in edges[]
                if (!comps.containsKey(i)) {
                    if (i == par[i]) subSet.addAll(mailsGroups.get(i));
                } else {
                    for (int c: comps.get(i)) {
                        subSet.addAll(mailsGroups.get(c));
                    }
                }

                if (subSet.size() > 0) {
                    // List<String> subLst = new ArrayList<>(subSet);
                    // Collections.sort(subLst); --> cause of TreeSet
                    // subLst.add(0, accName);
                    // lst.add(subLst);
                    lst.add(new ArrayList<>());
                    lst.get(lst.size()-1).add(accName);
                    lst.get(lst.size()-1).addAll(subSet);
                }
            }
        }
        return lst;
    }
    private static void union(int a, int b){
        int pa = find(a);
        int pb = find(b);
        if (pa==pb) return;

        if (!comps.containsKey(pa)) {
            Set<Integer> aSet = new HashSet<>();
            aSet.add(a);
            aSet.add(pa);
            comps.put(pa, aSet);
        }
        if (!comps.containsKey(pb)) {
            Set<Integer> bSet = new HashSet<>();
            bSet.add(b);
            bSet.add(pb);
            comps.put(pb, bSet);
        }

        if (rank[pa]<rank[pb]) {
            par[pa]=pb;
            comps.get(pb).addAll(comps.get(pa));
            comps.remove(pa);
        }
        else if (rank[pb]<rank[pa]) {
            par[pb]=pa;
            comps.get(pa).addAll(comps.get(pb));
            comps.remove(pb);
        }
        else {
            par[pb]=pa;
            rank[pa]++;
            comps.get(pa).addAll(comps.get(pb));
            comps.remove(pb);
        }
    }
    private static int find(int i){
        while(i != par[i]) i = par[i];
        return i;
    }













    // WORKING BUT TLE
    public static List<List<String>> accountsMergeTLE(List<List<String>> accounts) {
        // prepare map
        Map<String, List<List<String>>> map = new HashMap<>();
        for (int i=0; i<accounts.size(); i++) {
            List<String> acc = accounts.get(i);
            String accName = acc.get(0);
            List<String> currMails = new ArrayList<>();
            for (int j=0; j<acc.size(); j++) {
                if (j==0) map.putIfAbsent(accName, new ArrayList<>());
                else currMails.add(accounts.get(i).get(j));
            }
            map.get(accName).add(currMails);
        }

        // merge groups for all accounts
        List<List<String>> lst = new ArrayList<>();
        for (Map.Entry<String, List<List<String>>> e: map.entrySet()){
            // k=name, v=sameNameMails1, sameNameMails2, ....
            String accName = e.getKey();
            List<List<String>> mailsGroups = e.getValue();

            // merge mails for this accName
            int mgl=mailsGroups.size();
            for (int i=0; i<mgl; i++) {
                boolean isMerged = false;
                for (int j=i+1; j!=i && j!=mgl && i<mgl; j=(j+1+mgl)%mgl) { //all
                    if (!Collections.disjoint(mailsGroups.get(i),mailsGroups.get(j))){
                        mailsGroups.get(i).addAll(mailsGroups.get(j));
                        mailsGroups.remove(j);
                        isMerged=true;
                    }
                    mgl=mailsGroups.size(); // dynamic groups length
                }
                if (isMerged) i--;
                mgl=mailsGroups.size(); // dynamic groups length
            }


            for(List<String> mails: mailsGroups) {
                mails = mails.stream().distinct().sorted().collect(Collectors.toList());
                mails.add(0, accName);
                lst.add(mails);
            }
        }
        return lst;
    }
}
