package DataStructures;

/**
<pre>
    ------------------
    NUMBER BASE SYSTEM
    ------------------
    A number base system is a, methodical way of representing numbers using a specific set of digits or symbols, based on grouping or "bundles."

    Common Base Systems:
    * base2 = binary = n₂ = 0–1
    * base8 = octal = n₈ = 0=7
    * base10 = decimal = n₁₀ = 0-9
    * base16 = hexadecimal = n₁₆ = 0–9, A–F => A=10, B=11, C=12, D=13, E=14, F=15 → think in base 256 chunks, not base 16 digits
        int x = 0x1A; => 1A = (1 × 16¹) + (A × 16⁰) = (1 × 16) + (10 × 1) = 16 + 10 = 26
        int x = 0x01020304; => = (0 × 16⁷) 0 + (1 × 16⁶) 16777216 + (0 × 16⁵) 0 + (2 × 16⁴) 2 × 65536 + (0 × 16³) 0 + (3 × 16²) 3 × 256 + (0 × 16¹) 0 + (4 × 16⁰) 4 × 1 = 16909060
        int x = 0x12345678
        F (hex) = 15 (decimal) = 1111 (binary)

    Base 2, also known as the binary number system, is a way of representing numbers using only two digits: 0 and 1.
    While the standard decimal system (base 10) uses ten digits (0 – 9),
    So, base 2 uses powers of two to determine the value of each position



    -------
    BASE 10
    -------
    Base10 Example ---

        int n = 583;

        2   1   0
        5   8   3
        10² 10¹ 10⁰

        => 5*10² + 8*10¹ + 3*10⁰ = 500 + 80 + 3 = 583

        so, base10 is 10s, 100s, 1000s... and log10 returns the the power of n in 10s. Eg: log10(4321) = 3 ---> because 10³ ---> number of 0s




    ------
    BASE 2
    ------
    0 → OFF  (not set / unset / cleared)
    1 → ON   (set)

    “set” here has nothing to do with collections. It’s just overloaded terminology
    “set the bit” → turn it ON → make it 1
    “clear the bit” → turn it OFF → make it 0

    x = 13 = "1 1 0 1"
              ↑ ↑ ↑ ↑
              3 2 1 0

    bit 0 → set (1)
    bit 1 → not set (0)
    bit 2 → set (1)
    bit 3 → set (1)

    NOTE:
    - In base10 decimal system each place represents 10^x = powers of 10 - like 10s, 100s, 1000s, 10000s...
    - In base2 binary system each place represents 2^x   = powers of 2 - like 2, 4, 8, 16...
    - Bits are indexed from right to left starting at 0
    - Think of 1 as ON and consider the bit weight (2^index), and 0 as OFF so skip it.
    - Bit index (or position) → x
    - Bit weight (or place value or position value) → 2^x
    - LSB (Least Significant Bit) → rightMost → index 0 → bit 0 → first bit
    - MSB (Most Significant Bit) → leftMost → index 7 → bit 7 → highest index
    - So, using just 2^x, 2^(x-1), 2^(x-2),... numbers we can get all possible numbers like 3, 6, 74645, 4564234 ....


    Key Characteristics of Base2:
        - Two Digits Only: It exclusively uses 0 and 1
        - Positional Value: Each place value represents a power of 2, increasing as you move from right to left:
            1st position (rightmost): 2^0 = 1
            2nd position: 2^1 = 2
            3rd position: 2^2 = 4
            4th position: 2^3 = 8
        - Bits: Each digit in a base 2 number is called a bit (short for binary digit)
        - Notation: To distinguish a base 2 number from a decimal one, it is often written with a subscript, such as 1101₂
        - Same like log10, log2 returns the the power of n in 2s. Eg: log2(8) = 3 ---> because 2³ ---> number of 2s

    Why Base2 Is Used:
        Base 2 is the fundamental language of modern computers.
        Digital electronic circuits are made of transistors that act as switches,
        which are naturally suited for two states: on (represented by 1) or off (represented by 0).


    Base2 Example Conversion:
        To understand the value of a base 2 number like 1101₂ in our standard decimal system,
        you sum the powers of two for every position that has a "1"

    Base2 +ve -ve Sign representation:
        - if leftMost bit is 0, then it is +ve
        - if leftMost bit is 1, then it is -ve
        00000101 = +5 ✅
        10000101 ≠ -5 ❌
        To convert +ve num to -ve num, follow these steps
            1. Invert/flip all bits => 00000101 → 11111010
            2. Add 1 => 11111010 + 1 = 11111011
            3. Convert to base10 decimal => 11111011 = -5
                * Positive numbers → normal binary
                * Negative numbers → encoded using wrap-around arithmetic
                    to convert to -ve decimal num value = BinaryNumber - 2^n
                    For 8-bit:
                    10000101 = 133
                    133 - 256 = -123
        NOTE:
            - We have left zeros based on the bit size that provided like 8-bit, 16-bit, 32-bit, 64-bit... add that many zeros in the left
            - To flip the bits use ~ operator or XOR ^ 0xFFFFFFFF (as, F  F  F  F  F  F  F  F = 1111 1111 1111 1111 1111 1111 1111 1111)




    Base2 Example 1 ---

        "01010101"

    0   1  0  1  0  1  0  1
    2⁷  2⁶ 2⁵ 2⁴ 2³ 2² 2¹ 2⁰
    128 64 32 16 8  4  2  1
    0   64 0  16 0  4  0  1 = 85

    Bit index:   7   6   5   4   3   2   1   0
    Value:       2⁷  2⁶  2⁵  2⁴  2³  2²  2¹  2⁰
    Bits:        0   1   0   1   0   1   0   1



    Base2 Example 2 ---

     3 2 1 0  - indexes
    "1 1 0 1" - binary

    Index i                     3           2               1           0
    Binary Digit                1           1               0           1
    Binary Position Weight 2^i  2^3 (8)     2^2 (4)         2^1 (2)     2^0 (1)
    Calculation (digit * wt)    1*8         1*4             0*2         1*1
    Value                       8           4               0           1

    Result                  8+4+0+1 = 13




        n = 13

      2 | 13
        |____
      2 | 6     1
        |____
      2 | 3     0
        |____
        | 1     1
        |

    The binary string is formed from bottom to top i.e., "1101"



    --------------------------
    PATTERNS IN BINARY NUMBERS
    --------------------------
    1. the binary string is reverse of the 2 divisions - check above division structure
    2. All odd numbers ends with 1. Eg: "1 1 0 1" means its a odd number as this last bit "1" is the first division remainder of 2
    3. All even numbers ends with 0
    4. 2^x has x+1 bits set = first bit is 1 and rest are x number of 0s    (2⁰="1"=1,   2¹="10"=2,  2²="100"=4,  2³="1000"=8,  2⁴="10000"=16...)
    5. 2^x-1 has x bits set = all 1s with x number of 1s - except 2^0-1=0   (2⁰-1="0"=0, 2¹-1="1"=1, 2²-1="11"=3, 2³-1="111"=7, 2⁴-1="1111"=15...)
    6.



    ----------------
    BIT MANIPULATION
    ----------------
    Here we work on manipulating bits of a number like binary string with 0s and 1s rather than decimal
    And it's used in error correction, compression, encryption, and optimizing algorithms

        Integer class bit methods
        1. int Integer.bitCount(int i) - returns the number of 1s in the binary representation of the integer
        2. int Integer.highestOneBit(int i) - returns the value of the highest bit set (leftMost 2^x) to 1 in the binary representation of the integer
        3. int Integer.lowestOneBit(int i) - returns the value of the lowest bit set (rightMost 2^x) to 1 in the binary representation of the integer
        5. int Integer.numberOfLeadingZeros(int i) - returns the number of leading 0s (starting zeros - if we have 100100110 return 2) in the binary representation of the integer
        6. int Integer.numberOfTrailingZeros(int i) - returns the number of trailing 0s (ending zeros - if we have 10010011 return 0) in the binary representation of the integer
        7. int Integer.reverse(int i) - returns the value of the integer with all the bits reversed in 32 bits representation - Reverses all 32 bits - Bit at position k → moves to position 31 - k
        8. int Integer.reverseBytes(int i) - returns the value of the integer with all the bytes reversed
            byte-level reversal ===> 1 byte = 8 bits
            Treats int as 4 bytes (8 bits each)
            Only reverses the order of bytes, not bits inside them
            Structure: [byte1][byte2][byte3][byte4]
            Becomes: [byte4][byte3][byte2][byte1]
            int x = 0x01020304; 0x...  → hexadecimal number
            Binary: 00000001 00000010 00000011 00000100
                       01       02       03       04
            After reverseBytes: 00000100 00000011 00000010 00000001
            So: Integer.reverseBytes(0x01020304) = 0x04030201
        9. String Integer.toBinaryString(int i) - returns the binary string representation of the integer
        10. int Integer.parseInt(String s, int 2) - returns the integer value of the specified string in the specified radix
        11. int Integer.parseUnsignedInt(String s) - returns the 32 bits unsigned integer value of the specified string number
        12. Integer.decode(String hexDecimalString) - decodes the hexadecimal string into an integer Eg: "0x12345678"
        13. Integer.toHexString(int i) - returns the hexadecimal string representation of the integer
        14. int Integer.rotateLeft(int i, int distance) or << d - returns the value of the integer with all the bits rotated left by the specified distance
        15. int Integer.rotateRight(int i, int distance) or >> d - returns the value of the integer with all the bits rotated right by the specified distance
        16. int Integer.signum(int i) - returns the sign of the integer like -1, 0, 1
        17. int Float.floatToIntBits(float f) - returns the 32 bits integer value of the specified float




    -----------------
    BITWISE OPERATORS
    -----------------
    Act on operands at the binary bit level, such as bitwise
        * AND (&)   ---> a & 0 = 0, a & 1 = a
        * OR (|)    ---> a | 0 = a, a | 1 = 1
        * XOR (^)   ---> a ^ 0 = a, a ^ 1 = 1, a ^ a = 0
        * NOT (~)   ---> ~0 = -1, ~1 = 0, ~a = -(a + 1)

    In | OR operator:
        true  (1) - if at least one of the input is true
        false (0) - if both inputs are the false
    In & AND operator:
        true  (1) - if both inputs are true
        false (0) - if at least one of the input is false
    In ^ XOR (Extended OR) operator:
        false (0) - if both inputs are the same and
        true  (1) - if both inputs are different
    In ~ NOT operator:
        true  (1) - if input is false 0
        false (0) - if input is true 1


    Instead of + addition, we can do the binary addition with carry like this:
          1 1 1 1 1 1 1   --- carry
        +   1 1 1 0 1 1   --- 123 binary
        +   1 0 0 1 1 1   --- 39 binary
        ----------------
        1 0 1 0 0 0 1 0   --- 162 binary

        Binary reference:
        2 = 10
        3 = 11

    so, if the sum is 3 then use 11 instead of 3 and if the sum is 2 then use 10 instead of 2







    -----------------------
    BITWISE SHIFT OPERATORS
    -----------------------
    Shifts bits of a number
        * left shift (<<) - unsigned shift
        * right shift or signed right shift (>>) - preserves sign (fills with MSB) - ARITHMETIC RIGHT SHIFT
        * unsigned/zero-fill right shift (>>>) - fills with 0 (ignores sign) - LOGICAL RIGHT SHIFT


                    LEFT SHIFT (<<)               RIGHT SHIFT (>>)
                    multiply by 2                 divide by 2

                            23                        23
                    ┌─────────────────┐       ┌─────────────────┐
                    │ 0 0 0 1 0 1 1 1 │       │ 0 0 0 1 0 1 1 1 │
                    └─│───────────────┘       └───────────────│─┘
               (drop)🗑️ ↙ ↙ ↙ ↙ ↙ ↙ ↙           ↘ ↘ ↘ ↘ ↘ ↘ ↘🗑️ (drop)
                    ┌─────────────────┐       ┌─────────────────┐
                    │ 0 0 1 0 1 1 1 0 │       │ 0 0 0 0 1 0 1 1 │
                    └─────────────────┘       └─────────────────┘
                           46       ↑           ↑     11
                                    │           │
                                    │           │
                          + 0 added right    + 0 added left


    So, LEFT SHIFT already behaves like unsigned shift - that's why we don't have <<< unsigned left shift






    ---------------------------------
    ARITHMETIC OPERATIONS + - ON BITS
    ---------------------------------

    * a + b = (a ^ b) + ((a & b) << 1)
    * a + b = (a ^ b) + 2 (a ^ b)
    * a + b = (a | b) + (a & b)
    * a - b = a + (~b + 1)

    a ^ b	sum without carry
    a & b	carry bits
    << 1	shift carry to next position

    Even on bits we can use arithmetic operations using + and -

    binaryOf(13)  = 1101
    binaryOf(7)   = 0111


    ADDITION (+)
    ---------

            1 1 1   (carry)
            1 1 0 1
          + 0 1 1 1
          ──────────
          1 0 1 0 0     ->  decimalOf(10100) = 20

        we know that binaryOf(2) = 10, binaryOf(3) = 11

        Binary uses only digits 0 and 1, but results of addition can be multi-bit (like 2 val = 10 binary, 3 val = 11 binary)
        so, we use sum + carry just like decimal.

        In decimal 1 + 1 = 2
        but in binary 1 + 1 != 2 ---> 1 + 1 = 2 (in value) but 2 is represented as 10 in binary
        so, 1+1 = binaryOf(2) = 10
        similarly
        In decimal 1+1+1 = 3
        but in binary 1+1+1 = binaryOf(3) = 11
        so, use right number in "10" binary i.e "0" in the current place and carry forward the "1" --- just like addition



    SUBTRACTION (-)
    -----------

        ... 2^3  2^2  2^1  2^0
             8    4    2    1

        So when you borrow from the next position (2¹ place) --- left of current position
        you borrow a "1" → which equals 2 (decimal) = 10 (binary)
        it's similar to base10 approach, if we borrow from next position we get 10 value but here in base2 we get 2 value

        10 (binary) ≠ ten value
        10 (binary) = 2 (decimal)
        Now do the subtraction: 10₂ - 1₂
        Convert mentally: 2 - 1 = 1
        Back to binary: 1 = 1₂

        Now lets solve this bit by bit
         1 1 0 1
       - 0 1 1 1
       ──────────
         0 1 1 0


    BIT0
        3 2 1 0     (bit positions/indexes)
        1 1 0 1
      - 0 1 1 1
      ──────────
              0     ---> Bit0: 1-1 = 0

    BIT1
        1 1 0 1
      - 0 1 1 1
      ──────────
               0    ---> Bit1: 0-1 → need borrow
                    Borrow from next bit (bit 2), but that is 1
                    so: bit2 becomes 0, current bit1 becomes 10
        1 0 10 1
      - 0 1 1  1
      ───────────
            1  0    ---> Bit1: 10₂ - 1₂   => 2 - 1 = 1    => 1 = 1₂


    BIT2
        1 0 10 1
      - 0 1 1  1
      ───────────
            1  0    ---> Bit2: 0-1 → need borrow
                    Borrow from next bit (bit 3), but that is 1
                    so: bit3 becomes 0, current bit2 becomes 10
        0 10 10 1
      - 0 1  1  1
      ───────────
          1  1  0    ---> Bit2: 10₂ - 1₂   => 2 - 1 = 1    => 1 = 1₂


    BIT3
        0 10 10 1
      - 0 1  1  1
      ───────────
        0 1  1  0     ---> Bit3: 0-0 = 0


    NEVER write 10 inline inside the number, the above steps are just for understanding







    -----------------------
    BITS USE CASES / TRICKS
    -----------------------
    1. (num & 1)==0 is even and (num & 1)==1 is odd

        if num is odd then "num&1" returns '1'
        n=10101
        &
        1=00001
        ==00001

        Similarly, if num is even "num&1" return '0'
        n=10110
        &
        1=00001
        ==00000

    2. Instead of int div = num/2; int mul = num*2; use int div = num >> 1; and int mul = num << 1;

        // DIVISION num >> n == num/2ⁿ
        int div = num >> 1; // num/2¹ = num/2
        div = num >> 2; // num/2² = num/4
        div = nums >> 3; // num/2³ = num/8

        // MULTIPLICATION num << n == num*2ⁿ
        int mul = num << 1; // num*2¹ = num*2
        mul = num << 2; //num*2² = num*4
        mul = nums << 3; // num*2³ = num*8

    3. boolean bool = (n & (n - 1)) == 0; ---> to check if the number has a power of 2

        when n=2^x,
        binary = "1000..." --- 1 1s + x 0s
        n-1 = 2^x-1 = "111..." --- x 1s
        then n & (n-1)

        n   = 100
        &
        n-1 = 011
           == 000

        and note that if n=0 then we'll get some error so use n!=0 && (n & (n - 1)) == 0


    4. Instead of traditional “==”, Use Bitwise unary xor operator “^”

        int a = 123, b= 123;
        boolean bool = (a^b)==0;

        a^b returns 0 if same & bitwise comparison if different.
        So, 0^a = a and a^a = 0
        And 5^3 == 6 not 8
        Note: For chars xor ^ returns the +ve ascii difference


    5. Playing with the kth bit

        We know that (1 >> k) = 2^k ---> here we left shift "1" binary to k times
        Example: (1 >> 3) => converts "1" to "1000" in binary representation - k zeros = 3 zeros

        kth bit = kth LSB (Least Significant Bit) - kth bit from the right
        And we know that set = "1" and clear/unset = "0"

        - check if kth bit is set =>    x & (1 << k) != 0   or  (x << k) & 1 != 0
        - toggle the kth bit =>         x ^ (1 << k)
        - set the kth bit =>            x | (1 << k)
        - unset the kth bit =>          x & ~(1 << k)


        when LSB k = 3 = 3rd bit from the right (3 is index)
              543210 --- indexes
        100100111000 --- x's binary
        000000001000 --- 1 << k
        111111110111 --- ~(1 << k)


    6. Find x % 2^k

        x & ((1 << k) - 1)

        we know that (1 << k) = 2^k =  1000... k zeros = [0 to k] bits
        and we use (1 << k) - 1 = 1000-0001 = 0111.. k ones = [0 to k-1] bits
        so, we get (x & 0111..) ---> so finally we get the last 1&1=1 ---> remainder after %

        when x=10, k=3
        (1 << k) = (1 << 3) =   "1000"
        (1 << k)-1 = (1 << 3)-1 = "1000" - "0001" = "0111"
        x & ((1 << k) - 1) = "1010" & "0111" = "0010" = 2 (decimal)

        as 10 % 8 = 2 remainder

        and we can also think 1 << 3 as 2^3 right
        so
        x & ((1 << k) - 1)
        = 10 & (2^3 - 1)
        = 10 & (8 - 1)
        = 10 & 7
        = "1010" & "0111"
        = "0010"
        = 2




    7. Swap 2 variables x,y
        x = x ^ y
        y = x ^ y
        x = x ^ y



    8.


</pre>
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 03 May 2026
 */
public class BitManipulation {
    public static void main(String[] args) {
        int n = 13;

        // n to binary
        String nBinary = Integer.toBinaryString(n);
        String n32BitsBinary = String.format("%32s", Integer.toBinaryString(n)).replace(' ', '0');
        System.out.printf("%s's binary string => %s\n", n, nBinary);
        System.out.printf("%s's 32 bit binary string => %s\n", n, n32BitsBinary);

        // binary to n
        int nFromBinary = Integer.parseInt(nBinary, 2);
        int nFrom32BitsBinary = Integer.parseInt(n32BitsBinary, 2);
        System.out.printf("%s binary string to decimal n => %s\n", nBinary, nFromBinary);
        System.out.printf("%s 32 bit binary string to decimal n => %s\n", n32BitsBinary, nFrom32BitsBinary);

        /*<pre>

        --- manual way to convert n to binary ---

            n = 13

          2 | 13
            |____
          2 | 6     1
            |____
          2 | 3     0
            |____
            | 1     1
            |

        The binary string is formed from bottom to top i.e., "1101"


        </pre>*/
        n = 13;
        String manualBinary = "";
        while (n>0) {
            manualBinary += (n%2);
            n = n/2;
        }
        manualBinary = new StringBuilder(manualBinary).reverse().toString();
        System.out.printf("%s's manual binary string => %s\n", n, manualBinary);



        /*<pre>

        --- manual way to convert n to binary ---

        two ways:
        1. Manual multiplication => n*2 + r
        2. Formula => Summation ∑ 2^i * r



            n = 13

          2 | 13
            |____
          2 | 6     1
            |____
          2 | 3     0
            |____
            | 1     1
            |

         3 2 1 0  - indexes
        "1 1 0 1" - binary

         </pre>*/
        n = 0;
        String binary = Integer.toBinaryString(13);
        for (int i=0; i<binary.length(); i++){
            int r = binary.charAt(i)-'0'; // remainder
            n = n * 2 + r;
        }
        System.out.printf("%s's binary string to decimal n using Manual multiplication => %s\n", binary, n);

        n = 0;
        binary = Integer.toBinaryString(13);
        for (int i=0; i<binary.length(); i++){
            int reversedI = binary.length() - 1 - i;
            int remainder = binary.charAt(i)-'0';
            int val = (int) Math.pow(2, reversedI) * remainder;
            n += val;
        }
        System.out.printf("%s's binary string to decimal n using Formula ∑ 2^i * r => %s\n", binary, n);

    }
}
