package Algorithms.Graphs;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 03 Jan 2026
 * @link 433. Minimum Genetic Mutation <a href="https://leetcode.com/problems/minimum-genetic-mutation/">LeetCode Link</a>
 * @topics Graph, HashTable, String, DFS, BFS
 * @companies Microsoft(2), Google(2), Amazon(2), Bloomberg(5), Meta(2), Oracle(2), X(2)




    rootNode = startGene
    now create a graph
    where rootNode child is one of the endGene or node from bank[] where difference = 1
    and then this child node's child will be endGene or another node from bank[] if there diff is 1
    and finally we have to reach from startGene node to endGene node?

    Here, 0,3,7 indices are different from startGene to endGene
    startGene of i7 changed gene is present in bank[]
    and i7 && i0 changed gene is present in bank[]
    note that ---> (i0) and (i0 && i3) and then (i0 && i3 && i7) respective changes are not neccesarly present in bank[]
    the order can be different

    startGene ="AACCGGTT"
    endGene = "AAACGGTA"
    bank = ["AACCGGTA","AACCGCTA","AAACGGTA"]

                                               AACCGGTT

                                             | AACCGGTA | ✔️
                                             | AACCGCTA | ❌
                                             | AAACGGTA | ❌
                    _______________________________|________________________
                    |                              |                       |
                AACCGGTA                       AACCGCTA                 AAACGGTA
                                                  ❌                      ❌
              | AACCGCTA | ✔️
              | AAACGGTA | ✔️
         ___________|_____________
         |                       |
      AACCGCTA                AAACGGTA
         ✔️                   ✔️ ✅ ------> DONE

    Let N = bank.length, L = gene length = 8 (constant), alphabet size = 4.

 */
public class MinimumGeneticMutation {
    public static void main(String[] args) {
        String startGene = "AACCGGTT", endGene = "AAACGGTA", bank[] = {"AACCGGTA","AACCGCTA","AAACGGTA"};
        System.out.println("minMutation Using Bfs1 -> " + minMutationUsingBfs1(startGene, endGene, bank));
        System.out.println("minMutation Using Bfs2 -> " + minMutationUsingBfs2(startGene, endGene, bank));
        System.out.println("minMutation Using Bfs3 -> " + minMutationUsingBfs3(startGene, endGene, bank));
        System.out.println("minMutation Using Bfs4 🔥 -> " + minMutationUsingBfs4(startGene, endGene, bank));
        System.out.println("minMutation Using Bfs5 -> " + minMutationUsingBfs5(startGene, endGene, bank));

    }



    static class Node {
        String gene;
        List<Node> children=new ArrayList<>();
        Node(){}
        Node(String gene){this.gene=gene;}
    }

    /**
     * @TimeComplexity O(N²·L) → O(N!)
     * @SpaceComplexity O(N²)
     */
    public static int minMutationUsingBfs1(String startGene, String endGene, String[] bank) {
        Set<String> genes = new HashSet<>();
        for (String b: bank) genes.add(b);
        if (genes.add(endGene)) return -1; // endGame must be in given bank[], if not return -1

        Node root = new Node(startGene);
        prepareGraphUsingDfs(root, genes, endGene);

        Queue<Node> q = new LinkedList<>();
        q.add(root);
        int mutations = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            while(size-- > 0){
                Node node =  q.poll();
                if (node.children.size()==0 && node.gene.equals(endGene)) return mutations;

                for(Node child: node.children) {
                    q.add(child);
                }

            }
            mutations++;
        }
        return -1;
    }
    private static void prepareGraphUsingDfs(Node node, Set<String> genes, String endGene) { // here, we prepared tree instead of graph
        for (String currGene: genes) {
            int diff = 0;
            for (int i=0; i<8; i++) {
                if (node.gene.charAt(i) != currGene.charAt(i)) diff++;
            }
            if (diff ==  1) {
                Node currNode = new Node(currGene);
                node.children.add(currNode);
                if (currGene.equals(endGene)) return; // if endGene is child then stop DFS, no need to prepare graph

                Set<String> nextGenes = new HashSet<>(genes);
                nextGenes.remove(currGene);
                prepareGraphUsingDfs(currNode, nextGenes, endGene);
            }
        }
    }








    /**
     * @TimeComplexity O(N²·L)
     * @SpaceComplexity O(N)
     */
    public static int minMutationUsingBfs2(String start, String end, String[] bank) {
        Set<String> bankSet = new HashSet<>(Arrays.asList(bank));
        if (!bankSet.contains(end)) return -1;

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(start);
        visited.add(start);

        int mutations = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                if (curr.equals(end)) return mutations;

                for (String gene : bankSet) {
                    if (!visited.contains(gene) && isDiffOne(curr, gene)) {
                        visited.add(gene);
                        queue.offer(gene);
                    }
                }
            }
            mutations++;
        }
        return -1;
    }

    private static boolean isDiffOne(String a, String b) {
        int diff = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) diff++;
        }
        return diff == 1;
    }







    /**
     * @TimeComplexity O(N²)
     * @SpaceComplexity O(N²)
     */
    public static int minMutationUsingBfs3(String start, String end, String[] bank) {
        Queue<String> queue = new LinkedList<>();
        Set<String> seen = new HashSet<>();

        int steps = 0;
        queue.add(start);
        seen.add(start);

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                String node = queue.remove();
                if (node.equals(end)) {
                    return steps;
                }

                for (char c: new char[] {'A', 'C', 'G', 'T'}) {
                    for (int i = 0; i < node.length(); i++) {
                        String neighbor = node.substring(0, i) + c + node.substring(i + 1);
                        if (!seen.contains(neighbor) && Arrays.asList(bank).contains(neighbor)) {
                            queue.add(neighbor);
                            seen.add(neighbor);
                        }
                    }
                }
            }

            steps++;
        }

        return -1;
    }






    /**
     * @TimeComplexity O(N)
     * @SpaceComplexity O(N)
     * 🔥
     */
    public static int minMutationUsingBfs4(String startGene, String endGene, String[] bank) {
        Set<String> bankSet = new HashSet<>(Arrays.asList(bank));
        if (!bankSet.contains(endGene)) return -1;

        char[] chars = {'A', 'C', 'G', 'T'};
        Queue<String> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        q.offer(startGene);
        visited.add(startGene);

        int mutations = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                String gene = q.poll();
                if (gene.equals(endGene)) return mutations;

                char[] geneArr = gene.toCharArray();
                for (int i = 0; i < 8; i++) {
                    char oldC = geneArr[i];
                    for (char c : chars) { // new char[]{'A', 'C', 'G', 'T'}
                        if (c == oldC) continue;
                        geneArr[i] = c;
                        String nextGene = new String(geneArr); // or nextGene = gene.substring(0,i) + c + gene.substring(i+1);

                        if (bankSet.contains(nextGene) && !visited.contains(nextGene)) {
                            visited.add(nextGene);
                            q.offer(nextGene);
                        }
                    }
                    geneArr[i] = oldC; // reset back to original
                }
            }
            mutations++;
        }
        return -1;
    }






    // static class Node {
    //     String gene;
    //     List<Node> children=new ArrayList<>();
    //     Node(){}
    //     Node(String gene){this.gene=gene;}
    // }
    /**
     * @TimeComplexity O(N²·L)
     * @SpaceComplexity O(N²)
     */
    public static int minMutationUsingBfs5(String startGene, String endGene, String[] bank) {
        Set<String> genes = new HashSet<>();
        for (String b: bank) genes.add(b);
        if (genes.add(endGene)) return -1; // endGame must be in given bank[], if not return -1
        Map<String, Node> geneToNode = new HashMap<>();

        Node root = new Node(startGene);
        geneToNode.put(startGene, root);
        prepareGraphUsingDfs(root, genes, endGene, geneToNode);

        Queue<Node> q = new LinkedList<>();
        q.add(root);
        int mutations = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            while(size-- > 0){
                Node node =  q.poll();
                if (node.children.size()==0 && node.gene.equals(endGene)) return mutations;

                for(Node child: node.children) {
                    q.add(child);
                }

            }
            mutations++;
        }
        return -1;
    }


    private static void prepareGraphUsingDfs(Node node, Set<String> genes, String endGene, Map<String, Node> geneToNode) { // here, we prepared tree instead of graph
        for (String currGene: genes) {
            int diff = 0;
            for (int i=0; i<8; i++) {
                if (node.gene.charAt(i) != currGene.charAt(i)) diff++;
            }
            if (diff == 1) {
                Node currNode;
                if (geneToNode.containsKey(currGene)) {
                    currNode = geneToNode.get(currGene);
                } else {
                    currNode = new Node(currGene);
                    geneToNode.put(currGene, currNode);
                }

                node.children.add(currNode);
                // if (currGene.equals(endGene)) return;

                Set<String> nextGenes = new HashSet<>(genes);
                nextGenes.remove(currGene);
                prepareGraphUsingDfs(currNode, nextGenes, endGene, geneToNode);
            }
        }
    }









    public int minMutationUsingStringManipulationNotWorking(String startGene, String endGene, String[] bank) {

        int mutations = 0;
        for (int i=0; i<8; i++) {
            System.out.println(i);
            char s = startGene.charAt(i);
            char e = endGene.charAt(i);

            if (s==e) continue;

            String preGene = startGene.substring(0, i);
            boolean isFound = false;
            for(String b: bank) {
                if (b.startsWith(preGene)) {
                    isFound = true;
                    break;
                }
            }
            System.out.printf("startGene: %s, endGene: %s, preGene: %s, isFound: %s\n", startGene, endGene, preGene, isFound);
            if (isFound) mutations++;
            else return -1;
        }
        return mutations;
    }
}
