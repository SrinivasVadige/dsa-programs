package Algorithms.Hashing;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 15 April 2025
 */
public class EqualRowAndColumnPairs {
    public static void main(String[] args) {
        // int[][] grid = {{18,18,18,18,18},
        //                 {18,18,18,18,17},
        //                 {18,18,18,18,18},
        //                 {18,18,18,18,18},
        //                 {18,18,18,18,18}};

        int[][] grid = {{11,1},
                        {1,11}};
        System.out.println("equalPairsMyApproach(grid) => " + equalPairsMyApproach(grid));
    }



    public static int equalPairsMyApproach(int[][] grid) {
        int n = grid.length;
        StringBuilder[] rows = new StringBuilder[n], cols = new StringBuilder[n];
        Map<String, Integer> rowMap = new HashMap<>(), colMap = new HashMap<>();
        for(int i=0; i<n; i++) {
            rows[i] = new StringBuilder();
            cols[i] = new StringBuilder();
        }

        // rows
        for(int r=0; r<n; r++) {
            for(int c=0; c<n; c++) {
                rows[r].append(grid[r][c]).append(",");
            }
            rowMap.merge(rows[r].toString(), 1, Integer::sum);
        }

        // cols
        for(int c=0; c<n; c++) {
            for(int r=0; r<n; r++) {
                cols[c].append(grid[r][c]).append(",");
            }
            colMap.merge(cols[c].toString(), 1, Integer::sum);
        }

        int count = 0;
        for(String sb1: rowMap.keySet()) {
            if(colMap.containsKey(sb1)) {
                count += rowMap.get(sb1) * colMap.get(sb1);
            }
        }

        return count;
    }







    public static int equalPairs(int[][] grid) {
        int n = grid.length;
        Map<String, Integer> rowMap = new HashMap<>();

        // rows
        for (int r = 0; r < n; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < n; c++) {
                sb.append(grid[r][c]).append(",");
            }
            String rowStr = sb.toString();
            rowMap.merge(rowStr, 1, Integer::sum);
        }

        // cols
        int count = 0;
        for (int c = 0; c < n; c++) {
            StringBuilder sb = new StringBuilder();
            for (int r = 0; r < n; r++) {
                sb.append(grid[r][c]).append(",");
            }
            String colStr = sb.toString();
            count += rowMap.getOrDefault(colStr, 0);
        }

        return count;
    }







    public static int equalPairs2(int[][] grid) {
        int n = grid.length;
        int count = 0;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                boolean isSame = true;
                for (int i = 0; i < n; i++) {
                    if (grid[r][i] != grid[i][c]) {
                        isSame = false;
                        break;
                    }
                }
                if (isSame) count++;
            }
        }

        return count;
    }






    /**
     * Even though {@link #equalPairsMyApproach2()} looks cleaner with fewer loops than {@link #equalPairsMyApproach()}
     * it does: Inefficient memory access (grid[c][r])
     * More work per iteration (building both row and col together)
     * Higher GC impact from object allocation in loop
     * And that’s why equalPairs() ends up being faster in practice
     */
    public static int equalPairsMyApproach2(int[][] grid) {
        int n = grid.length;
        StringBuilder[] rows = new StringBuilder[n], cols = new StringBuilder[n];
        Map<String, Integer> rowMap = new HashMap<>(), colMap = new HashMap<>();

        // rows & cols
        for(int r=0; r<n; r++) {
            rows[r] = new StringBuilder();
            cols[r] = new StringBuilder();
            for(int c=0; c<n; c++) {
                rows[r].append(rows[r].length() == 0 ? "" : " ").append(grid[r][c]);
                cols[r].append(cols[r].length() == 0 ? "" : " ").append(grid[c][r]);
            }
            rowMap.merge(rows[r].toString(), 1, Integer::sum);
            colMap.merge(cols[r].toString(), 1, Integer::sum);
        }

        int count = 0;
        for(String sb1: rowMap.keySet()) {
            if(colMap.containsKey(sb1)) {
                count += rowMap.get(sb1) * colMap.get(sb1);
            }
        }

        return count;
    }




    public static int equalPairsMyApproachSlow(int[][] grid) {
        int n = grid.length;
        StringBuilder[] rows = new StringBuilder[n], cols = new StringBuilder[n];
        for(int i=0; i<n; i++) {
            rows[i] = new StringBuilder();
            cols[i] = new StringBuilder();
        }

        // rows
        for(int r=0; r<n; r++) {
            for(int c=0; c<n; c++) {
                rows[r].append(rows[r].length() == 0 ? "" : " ").append(grid[r][c]);
            }
        }

        // cols
        for(int c=0; c<n; c++) {
            for(int r=0; r<n; r++) {
                cols[c].append(cols[c].length() == 0 ? "" : " ").append(grid[r][c]);
            }
        }

        int count = 0;
        for(StringBuilder sb1: rows) {
            for(StringBuilder sb2: cols) {
                if (sb1.toString().equals(sb2.toString())) count++;
            }
        }

        return count;
    }









    @SuppressWarnings("unchecked")
    public static int equalPairsMyApproachOldNotWorking(int[][] grid) {
        int n = grid.length;
        Map<Integer, Integer>[] rows = new Map[n], cols = new Map[n];
        for(int i=0; i<n; i++) {
            rows[i] = new LinkedHashMap<>();
            cols[i] = new LinkedHashMap<>();
        }

        // rows
        for(int r=0; r<n; r++) {
            for(int c=0; c<n; c++) {
                rows[r].merge(grid[r][c], 1, Integer::sum);
            }
        }

        // cols
        for(int c=0; c<n; c++) {
            for(int r=0; r<n; r++) {
                cols[c].merge(grid[r][c], 1, Integer::sum);
            }
        }

        int count = 0;
        for(Map<Integer, Integer> map1: rows) {
            for(Map<Integer, Integer> map2: cols) {


                if (map1.size() != map2.size()) continue;

                Iterator<Map.Entry<Integer, Integer>> it1 = map1.entrySet().iterator();
                Iterator<Map.Entry<Integer, Integer>> it2 = map2.entrySet().iterator();

                boolean isSame = true;
                while (it1.hasNext() && it2.hasNext()) {
                    Map.Entry<Integer, Integer> e1 = it1.next();
                    Map.Entry<Integer, Integer> e2 = it2.next();

                    if (!e1.getKey().equals(e2.getKey()) || !e1.getValue().equals(e2.getValue())) { // or Objects.equals(e1.getValue(), e2.getValue())
                        isSame = false;
                        break;
                    }
                }


                if(isSame) {
                    System.out.println(map1);
                    count++;
                }


            }
        }

        return count;
    }
}
