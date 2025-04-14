import java.util.Arrays;

// The algorithm is based on the Least Significant Digit (LSD) Radix Sort method adapted for 3-character lowercase strings.
public class LSDRadixSortStrings {

    /*
    * Function to do counting sort based on the character at position charPos (0, 1, or 2)
    * The parameter array[] is the array to be sorted
    * The parameter charPos represents the index of the character in the string (0 for leftmost, 2 for rightmost)
    */
    private static String[] countingSort(String[] array, int charPos) {

        // Output array to store sorted strings
        String[] output = new String[array.length];

        // Initialize 2 count arrays with size 26 (for each lowercase letter a-z)
        int[] countArr1 = new int[26];
        int[] countArr2 = new int[26];

        // Decide which array to use for counting
        int[] count;

        // First and third passes use countArr1, second pass uses countArr2
        if (charPos == 0 || charPos == 2) {
            Arrays.fill(countArr1, 0); // Reset counts
            count = countArr1;
        } else {
            Arrays.fill(countArr2, 0); // Reset counts
            count = countArr2;
        }

        // Store count of occurrences of each character at charPos
        /*
         * For example, if the input array is {"cat", "dog", "bat"} and we are sorting based on position 2,
         * the characters are: 't', 'g', 't' → count['g'-'a']++, count['t'-'a']++
         */
        for (String word : array) {
            int index = word.charAt(charPos) - 'a';
            count[index]++;
        }

        // Update count[i] to contain cumulative count
        /*
         * For instance, if count['a'] = 1, count['b'] = 2, after cumulative count:
         * count['b'] = count['a'] + count['b'] = 3 → helps to position elements correctly
         */
        for (int i = 1; i < 26; i++) {
            count[i] += count[i - 1];
        }

        // Build the output array
        // Start from the last element to maintain stability
        /*
         * For example, if the input array is {"aba", "aca", "aaa"}
         * Sorting by char at index 2 (last character): 'a', 'a', 'a' → stable sort keeps original order
         */
        for (int i = array.length - 1; i >= 0; i--) {
            int index = array[i].charAt(charPos) - 'a';
            output[count[index] - 1] = array[i];
            count[index]--;
        }

        // Return the sorted array based on current character position
        return output;
    }

    // Main function to implement LSD radix sort for strings
    // It uses "countingSort()" function to sort the strings based on each character from right to left
    public static void main(String[] args) {

        // Example array of lowercase 3-character strings to be sorted
        String[] words = {"cat", "dog", "bat", "ant", "car", "bee", "ape", "dig"};

        // Display the original array
        System.out.println("Original array: " + Arrays.toString(words));

        // Call "countingSort" for each character position from rightmost (2) to leftmost (0)
        // LSD radix sort processes characters from least significant to most significant
        for (int pos = 2; pos >= 0; pos--) {
            words = countingSort(words, pos);
            System.out.println("After sorting by character at position " + pos + ": " + Arrays.toString(words));
        }

        // Display the sorted array in ascending lexicographical order
        System.out.println("Sorted array: " + Arrays.toString(words));
    }
}
