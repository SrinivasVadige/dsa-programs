package DataStructures;

/**
<pre>
    Base 2, also known as the binary number system, is a way of representing numbers using only two digits: 0 and 1.
    While the standard decimal system (base 10) uses ten digits (0 – 9),
    So, base 2 uses powers of two to determine the value of each position

    * base2 = binary = n₂
    * base10 = decimal = n₁₀
    * base16 = hexadecimal = n₁₆ = 0–9, A–F => A=10, B=11, C=12, D=13, E=14, F=15 → think in base 256 chunks, not base 16 digits
        int x = 0x1A; => 1A = (1 × 16¹) + (A × 16⁰) = (1 × 16) + (10 × 1) = 16 + 10 = 26
        int x = 0x01020304; => = (0 × 16⁷) 0 + (1 × 16⁶) 16777216 + (0 × 16⁵) 0 + (2 × 16⁴) 2 × 65536 + (0 × 16³) 0 + (3 × 16²) 3 × 256 + (0 × 16¹) 0 + (4 × 16⁰) 4 × 1 = 16909060
        int x = 0x12345678


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

    We used 2^x cause ->
        - In decimal system each place represents 10^x = powers of 10 - like 10s, 100s, 1000s, 10000s...
        - In binary system each place represents 2^x   = powers of 2 - like 2, 4, 8, 16...
        - Bits are indexed from right to left starting at 0
        - Think of 1 as ON and consider the bit weight (2^index), and 0 as OFF so skip it.
        - Bit index (or position) → x
        - Bit weight (or place value or position value) → 2^x
        - LSB (Least Significant Bit) → rightMost → index 0 → bit 0 → first bit
        - MSB (Most Significant Bit) → leftMost → index 7 → bit 7 → highest index
        - So, using just 2^x, 2^(x-1), 2^(x-2),... numbers we can get all possible numbers like 3, 6, 74645, 4564234 ....





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
    Act on operands at the binary bit level, such as bitwise AND (&), OR (|), and XOR (^)

    In | OR operator:
        true  (1) - if at least one of the input is true
        false (0) - if both inputs are the false
    In & AND operator:
        true  (1) - if both inputs are true
        false (0) - if at least one of the input is false
    In ^ XOR (Extended OR) operator:
        false (0) - if both inputs are the same and
        true  (1) - if both inputs are different




    -----------------------
    BITWISE SHIFT OPERATORS
    -----------------------
    Shifts bits of a number
        - left shift (<<)
        - right shift or signed right shift (>>)
        - unsigned/zero-fill right shift (>>>) - and there is no unsigned left shift







    --------------
    BITS USE CASES
    --------------
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

    3. boolean bool = n & (n - 1)) == 0; ---> to check if the number has a power of 2

        when n=2^x,
        binary = "1000..." --- 1 1s + x 0s
        n-1 = 2^x-1 = "1111..." --- x 1s
        then n & (n-1)

        n   = 100
        &
        n-1 = 011
           == 000


    4. Instead of traditional “==”, Use Bitwise unary xor operator “^”

        int a = 123, b= 123;
        boolean bool = (a^b)==0;

        a^b returns 0 if same & bitwise comparison if different.
        So, 0^a = a and a^a = 0
        And 5^3 == 6 not 8
        Note: For chars xor ^ returns the +ve ascii difference









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
