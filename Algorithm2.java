public class Algorithm2 {

    // Function to fill the array with zeros
    public static int[] resetArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
        return arr;
    }

    // Function to find the maximum length of strings in the array
    public static int findMaxLength(String[] array) {
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            String str = array[i];
            if (str.length() > max) {
                max = str.length();
            }
        }
        return max;
    }

    // Function to perform counting sort based on character positions
    private static String[] countingSort(String[] array, int exp) {

        // The variable to store the result of sorted array
        String[] output = new String[array.length];

        // Initialize 2 count arrays with size 27 (0 for missing char, 1-26 for 'a' to 'z')
        int[] countArr1 = new int[27];
        int[] countArr2 = new int[27];
        int[] count;

        // The word "ape" has the position of 3. Each position corresponds to each character - 0: 'a', 1:'p', 2:'e'.
        // When the exp is even, we use countArr1, and when it's odd, we use countArr2.
        // So, first pass and third pass are passed to countArr1 and second pass is passed to countArr2
        if (exp % 2 == 0) {
            countArr1 = resetArray(countArr1);
            count = countArr1;
        } else {
            countArr2 = resetArray(countArr2);
            count = countArr2;
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
        for (int i = 0; i < array.length; i++) {
            int index;
            if (exp < array[i].length()) {
                char ch = array[i].charAt(exp);
                index = ch - 'a' + 1;
            } else {
                index = 0; // "missing character"
            }
            count[index]++;
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
        for (int i = 1; i < 27; i++) {
            count[i] += count[i - 1];
        }

        // Build output array (iterate in reverse for stability)
        for (int i = array.length - 1; i >= 0; i--) {
            int index;
            if (exp < array[i].length()) {
                char ch = array[i].charAt(exp);
                index = ch - 'a' + 1;
            } else {
                index = 0;
            }
            output[count[index] - 1] = array[i];
            count[index]--;
        }

        return output;
    }

    // Main function to implement the algorithm
    public static void main(String[] args) {

        // Example of an array to be sorted
        String[] words = {"dog", "ti", "bat", "bad", "hat", "tin", "ape", "zoo"};

        // Display the original array
        System.out.println("=== Original array ===");
        System.out.print("Original array: ");
        for (int i = 0; i < words.length; i++) {
            System.out.print(words[i] + " ");
        }
        System.out.println("\n");

        // Find the max word length to determine number of passes
        int maxLen = findMaxLength(words);

        // Sort from last character to first
        for (int exp = maxLen - 1; exp >= 0; exp--) {
            words = countingSort(words, exp);

            System.out.println("=== After sorting on character position " + (exp + 1) + " ===");

            // Buckets: 0 for missing, 1-26 for 'a' to 'z'
            String[] buckets = new String[27];
            buckets[0] = "(empty): ";
            for (int i = 1; i < 27; i++) {
                buckets[i] = (char) ('a' + i - 1) + ": ";
            }

            for (int i = 0; i < words.length; i++) {
                int index;
                if (exp < words[i].length()) {
                    char ch = words[i].charAt(exp);
                    index = ch - 'a' + 1;
                } else {
                    index = 0; 
                }
                buckets[index] += words[i] + " ";
            }

            for (int i = 0; i < buckets.length; i++) {
                System.out.println(buckets[i]);
            }

            System.out.println();
        }

        // Display final sorted array
        System.out.println("=== Final sorted array ===");
        System.out.print("Reordered array: ");
        for (String word : words) {
            System.out.print(word + " ");
        }
    }
}
