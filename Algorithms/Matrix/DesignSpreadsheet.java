package Algorithms.Matrix;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 15 March 2025
 */
public class DesignSpreadsheet {
    public static void main(String[] args) {
        Spreadsheet s = new Spreadsheet(3);
        s.setCell("A1", 1);
        s.setCell("A2", 2);
        s.setCell("A3", 3);
        System.out.println("s.getValue(\"A1\") => " + s.getValue("A1+8"));
        s.resetCell("A1");
    }


}

// https://leetcode.com/problems/design-spreadsheet/
class  Spreadsheet {

    int[][] sheet;
    Spreadsheet(int rows) {
        sheet = new int[rows][26];
    }

    public void setCell(String cell, int value) {
        int row = Integer.parseInt(cell.substring(1)) - 1;
        int col = cell.charAt(0) - 'A';
        sheet[row][col] = value;
    }

    public void resetCell(String cell) {
        int row = Integer.parseInt(cell.substring(1)) - 1;
        int col = cell.charAt(0) - 'A';
        sheet[row][col] = 0;
    }

    public int getValue(String formula) {
        // formula can be "=A1+B2" or "5+B33"
        // return the result
        String ls="", rs="";
        String s[] = formula.split("\\+");
        ls = s[0].substring(1);
        rs = s[1];

        int lv = 0;
        if ( Character.isDigit(ls.charAt(0)) ) lv = Integer.parseInt(ls);
        else {
            int col = ls.charAt(0) - 'A';
            int row = Integer.parseInt(ls.substring(1));
            lv=sheet[row][col];
        }

        int rv = 0;
        if ( Character.isDigit(rs.charAt(0)) ) rv = Integer.parseInt(rs);
        else {
            int col = rs.charAt(0) - 'A';
            int row = Integer.parseInt(rs.substring(1));
            rv=sheet[row][col];
        }

        return lv+rv;
    }


    class SpreadsheetUsingMap {

        Map<String, Integer> map;

        public SpreadsheetUsingMap(int rows) {
            map = new HashMap<>();
        }

        public void setCell(String cell, int value) {
            map.put(cell, value);
        }

        public void resetCell(String cell) {
            map.put(cell, 0);
        }

        public int getValue(String f) {
            int index = -1;
            for(int i = 1; i < f.length(); i++) {
                if(f.charAt(i) == '+') {
                    index = i;
                    break;
                }
            }

            int ans = 0;
            char ch = f.charAt(1);
            if(ch >= 'A' && ch <= 'Z') {
                String sub = f.substring(1, index);
                if(map.containsKey(sub)) ans += map.get(sub);
            } else {
                String sub = f.substring(1, index);
                ans += Integer.parseInt(sub);
            }

            ch = f.charAt(index+1);
            if(ch >= 'A' && ch <= 'Z') {
                String sub = f.substring(index+1);
                if(map.containsKey(sub)) ans += map.get(sub);
            } else {
                String sub = f.substring(index+1);
                ans += Integer.parseInt(sub);
            }
            return ans;
        }
    }
}
