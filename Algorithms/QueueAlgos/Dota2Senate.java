package Algorithms.QueueAlgos;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 20 April 2025
 *
 * when senate = "DDRRR", output = "Dire"
 *
 * DDRRR - (DDRR -> DRRD)
 * DRRD - (DRD -> RDD)
 * RDD - (RD -> DR)
 * DR - (D)
 * D
 */
public class Dota2Senate {
    public static void main(String[] args) {
        String senate = "DDRRR";
        System.out.println("predictPartyVictoryMyApproach(senate) => " + predictPartyVictoryMyApproach(senate));
        System.out.println( "predictPartyVictory(senate) => " + predictPartyVictory(senate));
    }

    public static String predictPartyVictoryMyApproach(String senate) {
        int r=0, d=0;
        Queue<Character> q = new LinkedList<>();
        for(char c: senate.toCharArray()) {
            q.offer(c);
            if(c=='R') r++;
            else d++;
        }

        int banR = 0, banD = 0;
        while(r>0 && d>0) {
            char c = q.poll();
            if(c=='R') {
                if(banR > 0) {
                    banR--;
                    r--;
                    continue;
                }
                banD++;
            }
            else {
                if (banD > 0) {
                    banD--;
                    d--;
                    continue;
                }
                banR++;
            }
            q.offer(c);
        }
        return r>0? "Radiant" : "Dire";
    }


    public static String predictPartyVictoryMyApproach2(String senate) {
        int r = 0, d = 0; // Count of Radiant and Dire senators
        Queue<Character> queue = new LinkedList<>();
        for (char c : senate.toCharArray()) {
            queue.offer(c);
            if (c == 'R') r++;
            else d++;
        }

        int banR = 0, banD = 0; // Track bans for Radiant and Dire
        while (r > 0 && d > 0) {
            char c = queue.poll();
            if (c == 'R') {
                if (banR > 0) {
                    banR--; // Radiant senator is banned
                    r--;
                } else {
                    banD++; // Ban a Dire senator in the next round
                    queue.offer(c);
                }
            } else {
                if (banD > 0) {
                    banD--; // Dire senator is banned
                    d--;
                } else {
                    banR++; // Ban a Radiant senator in the next round
                    queue.offer(c);
                }
            }
        }

        return r > 0 ? "Radiant" : "Dire";
    }

    /**
     * same as above but using index of senate string instead of char
     */
    public static String predictPartyVictory(String senate) {
        Queue<Integer> rQueue = new LinkedList<>();
        Queue<Integer> dQueue = new LinkedList<>();

        for (int i = 0; i < senate.length(); i++) {
            if (senate.charAt(i) == 'R') {
                rQueue.offer(i);
            } else {
                dQueue.offer(i);
            }
        }

        while (!rQueue.isEmpty() && !dQueue.isEmpty()) {
            int rIndex = rQueue.poll();
            int dIndex = dQueue.poll();
            if (rIndex < dIndex) {
                rQueue.offer(rIndex + senate.length());
            } else {
                dQueue.offer(dIndex + senate.length());
            }
        }

        return rQueue.isEmpty() ? "Dire" : "Radiant";
    }
}
