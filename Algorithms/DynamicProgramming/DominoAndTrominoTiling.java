package Algorithms.DynamicProgramming;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 12 May 2025

    PREREQUISITES for easy understanding:
    -------------------------------------
    1) Polyomino shapes -- Greek / Latin words -> POLY == MANY, OMINO == SQUARES
    2) Monomino-1square, Domino-2squares, Tromino-3, Tetromino-4, Pentomino-5, Hexo6, Hepto7, Octo8, Nano9, Deci10
    3) These shapes can be arranged in any shape like l , --, L, Z, N, O
    4) Domino effect
    5) Tetris game
    6)



    OBS:
    ----
    1) 2*1 Domino means two units tile(it can be 2*1 or 1*2. Here 1,2 are rows & cols)
    2) And tromino means three units tile
    3) D can be rotated Horizontally and vertically -- two ways
        __    |

        or
                       [a]
        [a][a]         [a]

    4) T can be rotated in 4 directions -- turn 90 degrees to see -- four ways (right-angled 3 squares)
         __             __
        |    |__  __|     |

        or

        [1][1]      [1]            [1]       [1][1]
        [1]         [1][1]      [1][1]          [1]

    5) Example

        alphabets == DOMINO
        numbers == TROMINO


        [1][1][2][a][b][b][d][3][3][4][4][f]    ↑
        [1][2][2][a][c][c][d][3][e][e][4][f]    2
                                                ↓
        <-------------- n ------------------>

        or

        [1][1][2] a  b-b  d [3][3][4][4] f
        [1][2][2] a  c-c  d [3] e--e [4] f



    PROBLEM ANALYSIS:
    -----------------
    1) No gaps anywhere in the 2*n grid
    2) Draw combinations when n=1, 2, 3, 4 and try to find a pattern
    3) Our dp calculation might miss some squares while tiling

    3) Note that on average all the combinations remains same but the new one and last but not one

 */
public class DominoAndTrominoTiling {
    public static void main(String[] args) {
        int n=3;
        System.out.println("numTilingsBottomUpTabulationDp =>" + numTilingsBottomUpTabulationDp(n));
        System.out.println("numTilingsTopDownMemoDp =>" + numTilingsTopDownMemoDp(n));
    }



    /**
     *      0    1    2          n-3  n-2  n-1   n
     *     [  ] [  ] [  ]        [  ] [  ] [  ] [  ]
     *     [  ] [  ] [  ] ...... [  ] [  ] [  ] [  ]
     *
     *  Here we have 2*n gird -- 2 rows and n cols
     *
     * to calculate 2*n grid, we have to calculate 2*(n-1) grid. And to calculate 2*(n-1) grid, we have to calculate 2*(n-2) grid .... so on
     *
     *
     * Here calculate the number of ways for for n=0, 1, 2, 3, 4 & 5
     *
     * n=0 then ways = 1
     * n=1 then ways = 1
     * n=2 then ways = 2
     * n=3 then ways = 5
     * n=4 then ways = 11
     * n=5 then ways = 24
     *
     * now if you find some pattern you will get
     *
     * dp[3] = 2*2 + 1 = 5 ----> 2 * dp[2] + dp[0]
     * dp[4] = 2*5 + 1 = 11 ----> 2 * dp[3] + dp[1]
     * dp[5] = 2*11 + 1 = 24 ----> 2 * dp[4] + dp[2]
     *
     * so, finally we will get
     * dp[n] = 2*dp[n-1] + dp[n-3]
     * or
     * dp[n] = dp[n-1] + dp[n-2] + 2 * sum(dp[0 to n-3])
     *
     * THIS TYPE OF CREATING PATTERN FROM DATA IS CALLED "RECURRENCE RELATION" 🔥🔥🔥
     *
     * In interview, if you get this type of problem, ask interviewer like this
     * "I'd like to compute the first few values manually to try to find a pattern.
     * I already have:
     * dp[0] = 1,
     * dp[1] = 1,
     * dp[2] = 2,
     * dp[3] = 5
     * It might help me to see dp[4] or dp[5] — would it be okay if we computed those together?"
     * Possibly ask you to compute dp[4] yourself manually — and that's fair.
     *
     * i.e Important: Don’t treat it like “asking for the answer.”
     * Instead, frame it like: "I'd like to build an intuition for the recurrence by calculating the values for small n. I think that will help me generalize the approach"
     * This shows maturity and problem-solving.
     */
    public static int numTilingsBottomUpTabulationDp(int n) {
        int MOD = 1_000_000_007;
        if (n <= 1) return 1;
        if (n == 2) return 2;
        if (n == 3) return 5;

        long[] dp = new long[n + 1];
        dp[0] = 1; dp[1] = 1; dp[2] = 2; dp[3] = 5;

        for (int i = 4; i <= n; i++) {
            dp[i] = (2 * dp[i - 1] % MOD + dp[i - 3]) % MOD;
        }

        return (int) dp[n];
    }



    public static int numTilingsBottomUpNoMemoryDp(int n) {
        int MOD = 1_000_000_007;
        int a = 1;
        int b = 1;
        int c = 2;
        if (n <= 1) return 1;
        if (n == 2) return 2;
        if (n == 3) return 5;
        for (int i = 3; i <= n; i++) {
            int d = (2 * c % MOD + a) % MOD;
            a = b;
            b = c;
            c = d;
        }
        return c;
    }




    public static int numTilingsBottomUpTabulationDp2(int n) {
        long[] dp = new long[Math.max(4, n + 1)];
        int MOD = 1_000_000_007;
        dp[1] = 1; dp[2] = 2; dp[3] = 5;
        for (int i = 4; i <= n; i++) {
            dp[i] = (2 * dp[i - 1] + dp[i - 3]) % MOD;
        }
        return (int) dp[n];
    }




    public static int numTilingsBottomUpTabulationDp3(int n) {
        int MOD = 1_000_000_007;

        // Step 1: Handle base cases
        if (n == 1) return 1;
        if (n == 2) return 2;

        // Step 2: Create a dp array
        long[] dp = new long[n + 1];
        dp[0] = 1;  // Empty board
        dp[1] = 1;  // One vertical domino
        dp[2] = 2;  // Two verticals or two horizontals

        // Step 3: Fill dp array using recurrence
        for (int i = 3; i <= n; i++) {
            dp[i] = (2 * dp[i - 1] + dp[i - 3]) % MOD;
        }

        // Step 4: Return final result
        return (int) dp[n];
    }





    static final int mod = 1_000_000_007;

    public static int numTilingsTopDownMemoDp(int n) {
        int[][] dp = new int[n + 1][4]; // 4 states: 0, 1, 2, 3
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < 4; j++) {
                dp[i][j] = -1;
            }
        }
        return f(0, true, true, n, dp);
    }
    private static int f(int i, boolean t1, boolean t2, int n, int[][] dp) {
        if(i == n) return 1;
        int state = makeState(t1, t2);
        if(dp[i][state] != -1) return dp[i][state];
        boolean t3 = (i+1) < n, t4 = (i+1) < n;
        int count = 0;

        /**
         * [t1][t3]
         * [t2][t4]
         *
         * true == empty, false == filled
         * in (i+1) index, t3  & t4 will become t1 and t2
         */
        if(t1 && t2 && t3) count = (count + f(i+1, false, true, n, dp)) % mod; // t1, t2, t3 are empty in i and in i+1, now filled with tromino
        if(t1 && t2 && t4) count = (count + f(i+1, true, false, n, dp)) % mod; // t1, t2, t4 - tromino
        if(t1 && !t2 && t3 && t4) count = (count + f(i+1, false, false, n, dp)) % mod;
        if(!t1 && t2 && t3 && t4) count = (count + f(i+1, false, false, n, dp)) % mod;
        if(t1 && t2) count = (count + f(i+1, true, true, n, dp)) % mod;
        if(t1 && t2 && t3 && t4) count = (count + f(i+1, false, false, n, dp)) % mod;
        if(t1 && !t2 && t3) count = (count + f(i+1, false, true, n, dp)) % mod;
        if(!t1 && t2 && t4) count = (count + f(i+1, true, false, n, dp)) % mod;
        if(!t1 && !t2) count = (count + f(i+1, true, true, n, dp)) % mod;

        return dp[i][state] = count;
    }
    /**
        t1 (top) | t2 (bottom) | State | Interpretation
        -------- | ----------- | ----- | ----------------
        false    | false       | 0     | Both filled  [X]
                                                      [X]

        true     | false       | 1     | Top empty    [ ]
                                                      [X]

        false    | true        | 2     | Bottom empty [X]
                                                     '[ ]

        true     | true        | 3     | Both empty   [ ]
                                                     '[ ]
     */
    private static int makeState(boolean t1, boolean t2) {
        if (!t1 && !t2) return 0;
        if (t1 && !t2) return 1;
        if (!t1 && t2) return 2;
        return 3;
    }






    public static int numTilingsTopDownMemoDp2(int n) {
        return (int)dominoes(0, n, false);
    }
    private static long dominoes(int i, int n, boolean possible) {
        if (i == n) return possible ? 0 : 1;
        if (i > n) return 0;

        if (possible) return (dominoes(i + 1, n, false) + dominoes(i + 1, n, true)) % mod;

        return (dominoes(i + 1, n, false) + dominoes(i + 2, n, false) + 2 * dominoes(i + 2, n, true)) % mod;
    }
}
