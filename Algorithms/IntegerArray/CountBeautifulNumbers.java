package Algorithms.IntegerArray;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 15 March 2025
 * @link https://leetcode.com/problems/count-beautiful-numbers/description/
 */
public class CountBeautifulNumbers {
    public static void main(String[] args) {
        System.out.println("beautifulNumbers() => " + beautifulNumbers(10, 20));
    }

    public static int beautifulNumbers(int l, int r) {
        return countBeautiful(r) - countBeautiful(l - 1);
    }

    public static int countBeautiful(int x) {
        if (x < 0) return 0;
        char[] digits = getCharArray(x);
        HashMap<String, Integer> dp = new HashMap<>();
        return solve(0, 1, 0, 1, digits, dp);
    }

    public static char[] getCharArray(int x) {
        String str = String.valueOf(x);
        return str.toCharArray();
    }

    public static int solve(int i, int tight, int sum, int prod, char[] digits, HashMap<String, Integer> dp) {
        if (i == digits.length) {
            if (sum > 0 && prod % sum == 0) {
                return 1;
            } else {
                return 0;
            }
        }

        String str = i + " - " + tight + " - " + sum + " - " + prod;
        if (dp.containsKey(str)) return dp.get(str);

        int limit = 0;
        if (tight == 1) {
            limit = digits[i] - '0';
        } else {
            limit = 9;
        }

        int count = 0, j = 0;
        while (j <= limit) {
            int newTight = 0;
            if (tight == 1 && j == limit) {
                newTight = 1;
            } else {
                newTight = 0;
            }

            int newSum = sum + j;
            int newProd;
            if (j == 0 && sum == 0) {
                newProd = 1;
            } else {
                newProd = prod * j;
            }

            count += solve(i + 1, newTight, newSum, newProd, digits, dp);
            j++;
        }

        dp.put(str, count);
        return count;
    }





    public int beautifulNumbers2(int l, int r) {
        long countR = count(r);
        long countL = count(l - 1);
        return (int)(countR - countL);
    }

    private long count(int x) {
        if (x < 1) return 0;
        String s = Integer.toString(x);
        int n = s.length();
        int[] digits = new int[n];
        for (int i = 0; i < n; i++) {
            digits[i] = s.charAt(i) - '0';
        }
        Map<String, Long> memo = new HashMap<>();
        return dp(0, true, false, false, 0, 1, digits, n, memo);
    }

    private long dp(int pos, boolean tight, boolean started, boolean hasZero, int sum, int prod, int[] digits, int n, Map<String, Long> memo) {
        if (pos == n) {
            if (!started) return 0;
            if (hasZero) return 1;
            return (prod % sum == 0) ? 1 : 0;
        }

        String key = pos + "_" + (tight ? 1 : 0) + "_" + (started ? 1 : 0) + "_" + (hasZero ? 1 : 0) + "_" + sum + "_" + prod;
        if (memo.containsKey(key)) return memo.get(key);

        long ans = 0;
        int limit = tight ? digits[pos] : 9;

        if (started && hasZero && !tight) {
            long ways = (long) Math.pow(10, n - pos);
            memo.put(key, ways);
            return ways;
        }

        for (int d = 0; d <= limit; d++) {
            boolean newTight = tight && (d == limit);
            if (!started) {
                if (d == 0) {
                    ans += dp(pos + 1, newTight, false, false, 0, 1, digits, n, memo);
                } else {
                    ans += dp(pos + 1, newTight, true, false, d, d, digits, n, memo);
                }
            } else {
                if (hasZero) {
                    ans += dp(pos + 1, newTight, true, true, sum + d, 0, digits, n, memo);
                } else {
                    if (d == 0) {
                        ans += dp(pos + 1, newTight, true, true, sum, 0, digits, n, memo);
                    } else {
                        ans += dp(pos + 1, newTight, true, false, sum + d, prod * d, digits, n, memo);
                    }
                }
            }
        }

        memo.put(key, ans);
        return ans;
    }





    public int beautifulNumbers3(int left, int right) {
        long highCount = compute(right);
        long lowCount = compute(left - 1);
        return (int)(highCount - lowCount);
    }

    private long compute(int num) {
        if (num < 1) return 0;
        String numStr = Integer.toString(num);
        int length = numStr.length();
        int[] numArray = new int[length];

        for (int i = 0; i < length; i++) {
            numArray[i] = numStr.charAt(i) - '0';
        }

        Map<String, Long> memoStore = new HashMap<>();
        return findPatterns(0, true, false, false, 0, 1, numArray, length, memoStore);
    }

    private long findPatterns(int idx, boolean isBounded, boolean hasStarted, boolean containsZero, int sumDigits, int productDigits, int[] numArray, int totalSize, Map<String, Long> memoStore) {
        if (idx == totalSize) {
            if (!hasStarted) return 0;
            if (containsZero) return 1;
            return (productDigits % sumDigits == 0) ? 1 : 0;
        }

        String key = idx + "_" + (isBounded ? 1 : 0) + "_" + (hasStarted ? 1 : 0) + "_" + (containsZero ? 1 : 0) + "_" + sumDigits + "_" + productDigits;
        if (memoStore.containsKey(key)) return memoStore.get(key);

        long countWays = 0;
        int maxDigit = isBounded ? numArray[idx] : 9;

        if (hasStarted && containsZero && !isBounded) {
            long possibleWays = (long) Math.pow(10, totalSize - idx);
            memoStore.put(key, possibleWays);
            return possibleWays;
        }

        for (int d = 0; d <= maxDigit; d++) {
            boolean nextBounded = isBounded && (d == maxDigit);
            if (!hasStarted) {
                if (d == 0) {
                    countWays += findPatterns(idx + 1, nextBounded, false, false, 0, 1, numArray, totalSize, memoStore);
                } else {
                    countWays += findPatterns(idx + 1, nextBounded, true, false, d, d, numArray, totalSize, memoStore);
                }
            } else {
                if (containsZero) {
                    countWays += findPatterns(idx + 1, nextBounded, true, true, sumDigits + d, 0, numArray, totalSize, memoStore);
                } else {
                    if (d == 0) {
                        countWays += findPatterns(idx + 1, nextBounded, true, true, sumDigits, 0, numArray, totalSize, memoStore);
                    } else {
                        countWays += findPatterns(idx + 1, nextBounded, true, false, sumDigits + d, productDigits * d, numArray, totalSize, memoStore);
                    }
                }
            }
        }

        memoStore.put(key, countWays);
        return countWays;
    }
}
