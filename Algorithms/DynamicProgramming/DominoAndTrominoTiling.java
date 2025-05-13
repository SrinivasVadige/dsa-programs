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
        return (int)dominoes(0, n, false);
    }
    private static long dominoes(int i, int n, boolean possible) {
        if (i == n) return possible ? 0 : 1;
        if (i > n) return 0;

        if (possible) return (dominoes(i + 1, n, false) + dominoes(i + 1, n, true)) % mod;

        return (dominoes(i + 1, n, false) + dominoes(i + 2, n, false) + 2 * dominoes(i + 2, n, true)) % mod;
    }




    public static int numTilingsTopDownMemoDp2(int n) {
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
    private static int makeState(boolean t1, boolean t2) {
        if (!t1 && !t2) return 0;
        if (t1 && !t2) return 1;
        if (!t1 && t2) return 2;
        return 3;
    }
}
