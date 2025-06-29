package Algorithms.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 28 June 2025
 * @link 68. Text Justification <a href="https://leetcode.com/problems/text-justification/">LeetCode link</a>
 * @topics String, Array, Simulation
 */
public class TextJustification {
    public static void main(String[] args) {
        String[] words = {"This", "is", "an", "example", "of", "text", "justification."};
        int maxWidth = 16;
        System.out.printf("fullJustifyMyApproach => %s\n", fullJustifyMyApproach(words, maxWidth));
        System.out.printf("fullJustify           => %s\n", fullJustify(words, maxWidth));
    }

    /**
            i = nextWordI
            lineStats = new int[]{words, len}
            spaceBetween = (maxWidth-len)/(words-1) ----> word-1 cause we need space between words

            maxWidth = 16
            ["What","must","be","acknowledgment","shall","be"]
    1st line= 1234 | 5678 | 90 | 123 --- if we consider last word then len > 16, So skip it
    1st line = new int[]{3, 10}
    spaceBetween = (maxWidth-len)/(words-1) = (16-10)/2 = 3
    1st line = "What   must   be" ---- done

            i = 3
            ["What","must","be","acknowledgment","shall","be"]
    2nd line=                    12345678901234 | 56789 --- if we consider last word then len > 16, So skip it
    2nd line = new int[]{1, 14}
    if (i==n-1 || stats[0]==1) {
        // attach space only on right --- not in b/w
    }
    2nd line = "acknowledgment  " ----- done

            i = 4
            ["What","must","be","acknowledgment","shall","be"]
    3rd line=                                     12345 | 67 --- if we consider last word then len > 16, So skip it
    3rd line = new int[]{2, 7}
    if (i==n-1 || stats[0]==1) {
        // attach space only on right --- not in b/w
    }
    3rd line = "shall be        " ----- done



    HANDLE:
    If the number of spaces on a line does not divide evenly between words


    */
    public static List<String> fullJustifyMyApproach(String[] words, int maxWidth) {
        int i = 0;
        int n = words.length;
        List<String> para = new ArrayList<>();

        while (i<n) {
            // calculate lineStats[] ---
            int[] lineStats =  new int[2]; // [numOfWords, wordsLen]
            for(int start=i; start<n; start++) {
                int len = lineStats[1] + words[start].length();
                int numOfWords = lineStats[0] + 1;
                if((len + numOfWords-1) > maxWidth) { // (len + numOfWords-1) -- EDGE CASE FOR SPACE
                    break;
                }

                lineStats[1] = len;
                lineStats[0]++;
            }

            // ADD startI to endI WORDS IN PARAGRAPH ---
            int startI = i;
            int endI = i + lineStats[0]-1;
            int spaceBetween;
            int numOfUnevens;
            if(endI == n-1 || lineStats[0]==1) {
                spaceBetween = 1;
                numOfUnevens = 0;
            } else {
                int totalSpaces = maxWidth - lineStats[1]; // space between words --> combination of both even or uneven spaces between words
                spaceBetween = totalSpaces / (lineStats[0] - 1);
                numOfUnevens = totalSpaces % (lineStats[0] - 1);
                // or
                // spaceBetween = (maxWidth-lineStats[1])/(lineStats[0]-1);
                // numOfUnevens = maxWidth - (lineStats[1]+ spaceBetween*(lineStats[0]-1));
            }
            StringBuilder space = new StringBuilder().repeat(" ", spaceBetween);
            StringBuilder sb = new StringBuilder();
            for(; startI<=endI; startI++) {
                sb.append(words[startI]);
                if(startI != endI) {
                    sb.append(space);
                    if(numOfUnevens-- > 0) {
                        sb.append(" ");
                    }
                }
            }

            // TRAILING SPACES ---
            if(endI == n-1 || lineStats[0]==1) {
                int diff = maxWidth - sb.length();
                sb.repeat(" ", diff); // or sb.append(new StringBuilder().repeat(" ", diff)) Java 21; or sb.append(" ".repeat(diff)) Java 11; or String.join(" ", Collections.nCopies(diff)) Java 8;
            }

            para.add(sb.toString());
            i = endI + 1;
        }

        return para;
    }


    public List<String> fullJustifyMyApproachOld(String[] words, int maxWidth) {
        int i = 0;
        int n = words.length;
        List<String> para = new ArrayList<>();

        while (i<n) {
            // calculate lineStats[]
            int[] lineStats =  new int[2]; // [numOfWords, wordsLen]
            for(int start=i; start<n; start++) {
                int len = lineStats[1] + words[start].length();
                int numOfWords = lineStats[0] + 1;
                if((len + numOfWords-1) > maxWidth) { // (len + numOfWords-1) -- EDGE CASE FOR SPACE
                    break;
                }

                lineStats[1] = len;
                lineStats[0]++;
            }

            // i   to  i+lineStats[0]
            int startI = i;
            int endI = i + lineStats[0]-1;
            if(endI == n-1 || lineStats[0]==1) {
                String str = IntStream.rangeClosed(startI, endI).mapToObj(idx-> words[idx]).collect(Collectors.joining(" "));
                int diff = maxWidth - str.length();
                para.add(str + " ".repeat(diff));
            } else {
                int spaceBetweenCounnt = (maxWidth-lineStats[1])/(lineStats[0]-1);
                StringBuilder space = new StringBuilder().repeat(" ", spaceBetweenCounnt); // or String.join("", Collections.nCopies(n, " ")); or IntStream.rang(0, n).mapToObj(i-> " ").collect(Collectors.joining(""))🔥
                int numOfUnevens = maxWidth - (lineStats[1 ]+ spaceBetweenCounnt*(lineStats[0]-1));
                StringBuilder sb = new StringBuilder();
                for(; startI<=endI; startI++) {
                    sb.append(words[startI]);
                    if(startI != endI) {
                        sb.append(space);
                        if(numOfUnevens-- > 0) {
                            sb.append(" ");
                        }
                    }
                }
                para.add(sb.toString());
                // para.add(IntStream.rangeClosed(startI, endI).mapToObj(idx-> words[idx]).collect(Collectors.joining(" ".repeat(spaceBetween))));
            }
            i = endI + 1;
        }

        return para;
    }





    public static List<String> fullJustify(String[] words, int maxWidth) {
         List<String> result = new ArrayList<>();
         List<String> line = new ArrayList<>(); // or StringBuilder
         int length = 0;
         int i = 0;
         int n = words.length;

         while (i < n) {
             // can we add word[i] to line?
             if(length + line.size() + words[i].length() > maxWidth) { // line.size() == number of words, length == length of all words in line.
                 // line complete
                 int extraSpaces = maxWidth - length; // extra spaces - combination of both even or uneven spaces
                 int spaceEach = extraSpaces / Math.max(1, (line.size()-1)); // Math.max to avoid divide by zero
                 int unevenSpaces = extraSpaces % Math.max(1, (line.size()-1)); // Math.max to avoid divide by zero

                 for (int j = 0; j < Math.max(1, line.size()-1); j++) { // Math.max to avoid zero
                     line.set(j, line.get(j) + " ".repeat(spaceEach));
                     if (unevenSpaces-- > 0) {
                         line.set(j, line.get(j) + " ");
                     }
                 }

                 result.add(String.join("", line));
                 line = new ArrayList<>();
                 length = 0;
             }

             line.add(words[i]);
             length += words[i].length();
             i++;
         }

        // Handle the last line ---
        String lastLine = String.join(" ", line);
        int trailingSpaces = maxWidth - lastLine.length();
        result.add(lastLine + " ".repeat(trailingSpaces));

        return result;
    }










    public static List<String> fullJustify3(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        int i = 0;
        while (i < words.length) {
            int j = i, len = 0;
            while (j < words.length && len + words[j].length() + (j - i) <= maxWidth) {
                len += words[j].length();
                j++;
            }
            int gaps = j - i - 1;
            int spaces = maxWidth - len;
            StringBuilder line = new StringBuilder();

            if (j == words.length || gaps == 0) {
                for (int k = i; k < j; k++) {
                    line.append(words[k]);
                    if (k != j - 1) line.append(" ");
                }
                while (line.length() < maxWidth) line.append(" ");
            } else {
                int spaceEach = spaces / gaps, extra = spaces % gaps;
                for (int k = i; k < j; k++) {
                    line.append(words[k]);
                    if (k != j - 1) {
                        int toAdd = spaceEach + (extra-- > 0 ? 1 : 0);
                        line.append(" ".repeat(toAdd));
                    }
                }
            }
            result.add(line.toString());
            i = j;
        }
        return result;
    }


}
