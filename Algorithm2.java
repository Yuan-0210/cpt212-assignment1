public class Algorithm2 {
    private static int counter =0;
    // Function to fill the array with zeros
    public static int[] resetArray(int[] arr) {
        counter+=1; // 1 assign
        for (int i = 0; i < arr.length; i++) {
            counter+=3; // 1 assign, 1 compare, 1 op
            arr[i] = 0;
            counter+=2; // 1 lookup, 1 assign
        }
        counter+=1; // 1 return
        return arr;
    }

    // Function to find the maximum length of strings in the array
    public static int findMaxLength(String[] array) {
        int max = 0;
        counter+=1; // 1 assign
        counter+=1; // 1 assign
        for (int i = 0; i < array.length; i++) {
            counter+=3; // 1 assign, 1 compare, 1 op
            String str = array[i];
            counter+=2; // 1 assign, 1 lookup
            counter+=2; // 1 compare, 1 call
            if (str.length() > max) {
                max = str.length();
                counter+=2; // 1 assign, 1 call
            }
        }
        counter+=1; // 1 return
        return max;
    }

    // Function to perform counting sort based on character positions
    private static String[] countingSort(String[] array, int exp) {

        // The variable to store the result of sorted array
        String[] output = new String[array.length];
        counter+=1; // 1 assign

        // Initialize 2 count arrays with size 27 (0 for missing char, 1-26 for 'a' to 'z')
        int[] countArr1 = new int[27];
        counter+=1; // 1 assign
        int[] countArr2 = new int[27];
        counter+=1; // 1 assign
        int[] count;
        counter+=1; // 1 assign

        // The word "ape" has the position of 3. Each position corresponds to each character - 0: 'a', 1:'p', 2:'e'.
        // When the exp is even, we use countArr1, and when it's odd, we use countArr2.
        // So, first pass and third pass are passed to countArr1 and second pass is passed to countArr2
        counter+=2; // 1 arithmetic, 1 compare
        if (exp % 2 == 0) {
            countArr1 = resetArray(countArr1);
            counter+=2; // 1 assign, 1 call
            count = countArr1;
            counter+=1; // 1 assign
        } else {
            countArr2 = resetArray(countArr2);
            counter+=2; // 1 assign, 1 call
            count = countArr2;
            counter+=1; // 1 assign
        }

        // Count frequency of each character (or missing char)
        /*
        *For example, if the input array is {"dog", "ti", "bat", "bad", "hat", "tin", "ape", "zoo"}
        * and we are sorting based on the 1s place (exp = 1), the count array will be:
        * count[0] - 'missing' = 0, count[1] - 'a' = 3, count[2] - 'b' = 0, count[3] - 'c' = 0,
        * count[4] - 'd' = 0,       count[5] - 'e' = 0, count[6] - 'f' = 0, count[7] - 'g' = 0,
        * count[8] - 'h' = 0,       count[9] - 'i' = 2, count[10] - 'j' = 0, count[11] - 'k' = 0,
        * count[12] - 'l' = 0,      count[13] - 'm' = 0, count[14] - 'n' = 0, count[15] - 'o' = 2,
        * count[16] - 'p' = 1,      count[17] - 'q' = 0, count[18] - 'r' = 0, count[19] - 's' = 0,
        * count[20] - 't' = 0,      count[21] - 'u' = 0, count[22] - 'v' = 0, count[23] - 'w' = 0,
        * count[24] - 'x' = 0,      count[25] - 'y' = 0, count[26] - 'z' = 0
        */
        counter+=1; // 1 assign
        for (int i = 0; i < array.length; i++) {
            counter+=3; // 1 assign, 1 compare, 1 op
            int index;
            counter+=3; // 1 compare, 1 lookup, 1 call
            if (exp < array[i].length()) {
                char ch = array[i].charAt(exp);
                counter+=3; // 1 assign, 1 lookup, 1 call
                index = ch - 'a' + 1;
                counter+=3; // 1 assign, 2 arithmetic
            } else {
                index = 0; // "missing character"
                counter+=1; // 1 assign
            }
            count[index]++;
            counter+=3; // 1 lookup, 1 op, 1 assign
        }

        // Cumulative count
        // This can be used to place the strings in the output array so that the order is maintained
        /*
        * Based on the previous example, the updated count array will be:
        * index: 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26
        * count: 0,3,3,3,3,3,3,3,3,5,5, 5, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
        * count[0] = 0, count[1] = 3, count[2] = 3, count[3] = 3, count[4] = 3,
        * count[5] = 3, count[6] = 3, count[7] = 3, count[8] = 3, count[9] = 5, 
        * count[10] = 5, count[11] = 5, count[12] = 5, count[13] = 5, count[14] = 5,
        * count[15] = 7, count[16] = 8, count[17] = 8, count[18] = 8, count[19] = 8,
        * count[20] = 8, count[21] = 8, count[22] = 8, count[23] = 8, count[24] = 8,
        * count[25] = 8, count[26] = 8
        */
        counter+=1; // 1 assign
        for (int i = 1; i < 27; i++) {
            counter+=3; // 1 assign, 1 compare, 1 op
            count[i] += count[i - 1];
            counter+=5; // 2 lookup, 2 arithmetic, 1 assign
        }

        // Build output array (iterate in reverse for stability)
        counter+=1; // 1 assign
        for (int i = array.length - 1; i >= 0; i--) {
            counter+=4; // 1 assign, 1 arithmetic, 1 compare, 1 op
            int index;
            counter+=3; // 1 compare, 1 lookup, 1 call
            if (exp < array[i].length()){
                char ch = array[i].charAt(exp);
                counter+=3; // 1 assign, 1 lookup, 1 call
                index = ch - 'a' + 1;
                counter+=3; // 1 assign, 2 arithmetic
            } else {
                index = 0;
                counter+=1; // 1 assign
            }
            output[count[index] - 1] = array[i];
            counter+=5; // 1 arithmetic, 3 lookup, 1 assign
            count[index]--;
            counter+=3; // 1 lookup, 1 op, 1 assign
        }
        counter+=1; // 1 return
        return output;
    }

    // Main function to implement the algorithm
    public static void main(String[] args) {

        // Example of an array to be sorted
        String[] words = {
            "dog", "ti", "bat", "bad", "hat", "tin", "ape", "zoo",
            "x", "rv", "ubj", "ft", "q", "hz", "klm", "sj",
            "j", "sv", "rm", "qaz", "zt", "n", "ghk", "pl",
            "mo", "yp", "em", "rq", "t", "nd", "cvb", "ak",
            "p", "xy", "zq", "om", "hd", "wt", "efa", "gn",
            "uv", "qr", "jm", "ko", "ev", "bt", "sn", "wx",
            "y", "ac", "zb", "lo", "ir", "ps", "fv", "cw",
            "du", "vc", "na", "lg", "kp", "jy", "fz", "wb",
            "bd", "it", "ja", "mx", "pu", "qe", "rh", "sz",
            "lx", "zv", "fy", "sb", "ca", "tw", "kh", "ae"
          }
          
          
          
          
       
       ;
        counter+=1; // 1 assign

        // Display the original array
        System.out.println("=== Original array ===");
        System.out.print("Original array: ");
        counter+=1; // 1 assign
        for (int i = 0; i < words.length; i++) {
            counter+=3; // 1 assign, 1 compare, 1 op
            System.out.print(words[i] + " ");
            counter+=2; // 1 lookup, 1 arithmetic
        }
        System.out.println("\n");

        // Find the max word length to determine number of passes
        int maxLen = findMaxLength(words);
        counter+=2; // 1 assign, 1 call

        // Sort from last character to first
        counter+=1; // 1 assign
        for (int exp = maxLen - 1; exp >= 0; exp--) {
            counter+=4; // 1 assign, 1 arithmetic, 1 compare, 1 op
            words = countingSort(words, exp);
            counter+=2; // 1 assign, 1 call

            System.out.println("=== After sorting on character position " + (exp + 1) + " ===");
            counter+=3; // 3 arihmetic

            // Buckets: 0 for missing, 1-26 for 'a' to 'z'
            String[] buckets = new String[27];
            counter+=1; // 1 assign
            buckets[0] = "(empty): ";
            counter+=2; // 1 assign, 1 lookup
            counter+=1; // 1 assign
            for (int i = 1; i < 27; i++) {
                counter+=3; // 1 assign, 1 compare, 1 op
                buckets[i] = (char) ('a' + i - 1) + ": ";
                counter+=5; // 1 lookup, 1 assign , 3 arithmetic
            }
            counter+=1; // 1 assign
            for (int i = 0; i < words.length; i++) {
                counter+=3; // 1 assign, 1 compare, 1 op
                int index;
                counter+=3; // 1 compare, 1 lookup, 1 call
                if (exp < words[i].length()) {
                    char ch = words[i].charAt(exp);
                    counter+=3; // 1 assign, 1 lookup, 1 call
                    index = ch - 'a' + 1;
                    counter+=3; // 2 arithmetic, 1 assign
                } else {
                    index = 0;
                    counter+=1; // 1 assign 
                }
                buckets[index] += words[i] + " ";
                counter+=5; // 2 lookup, 2 arithmetic, 1 assign
            }
            counter+=1; // 1 assign
            for (int i = 0; i < buckets.length; i++) {
                counter+=3; // 1 assign, 1 compare, 1 op
                System.out.println(buckets[i]);
                counter+=1; // 1 lookup
            }

            System.out.println();
        }

        // Display final sorted array
        System.out.println("=== Final sorted array ===");
        System.out.print("Reordered array: ");
        for (String word : words) {
            counter+=2; // 1 op, 1 assign
            System.out.print(word + " ");
            counter+=1; // 1 arithmetic
        }
        System.out.println("\nTotal primitive operations: " + counter);
    }
}
