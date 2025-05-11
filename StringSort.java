public class StringSort {

    // Counter to track the primitive operations.
    private static int operationCount = 0;

    /**
     * Two 2D arrays used for sorting
     *  - Each row represents a alphabet and one more extra row for null values.
     *  - Each column holds string assigned to that alphabet during sorting.
     *
     * We use String as we are sorting string values.
    */
    private static String[][] array1;
    private static String[][] array2;

    // Parallel array that stores the reordered sorted strings.
    private static String[] SortedList;

    // Parallel array that keep track the number of elements in each character row (a-z and one null row)).
    private static int[] charRowCounts;

    // Store the maximum string length in the input array.
    private static int maxStringLength;

    /**
     * Initializes two 2D arrays (array1 and array2) with 27 rows 
     * for alphabet a-z and one null row and 'size' columns (based on the number of elements to sort).
     *
     * Worst case: all strings could go into the same character row , 
     * so each row needs 'size' slots to avoid overflow.
     *
     * Example: for input {"apple", "bird", "cat", "elephant", "dog"} (5 strings)
     * → creates array1 and array2 of size [27][5].
     *
     * @param size the number of elements in the input array.
    */
    public static void initializeArray(int size) {
        array1 = new String[27][size]; 
        array2 = new String[27][size]; 
        charRowCounts = new int[27];
    }

    /**
     *  Reset the count of elements in each character row to zero before the next sorting pass.
    */
    private static void resetCharRowCounts() {
        for (int i = 0; i < 27; i++) {
            charRowCounts[i] = 0;
        }
    }

    /**
     * Clear all strings in the given 2D array by setting them to NULL.
     * 
     * This ensures the character row is empty and ready for the next sorting pass.
     * @param array the 2D array to be cleared, (Setting all elements in the character row to null).
    */
    private static void clearArray(String[][] array) {
        for (int i = 0; i < array.length; i++) { // Outer loop, loop through 26 alphabet (a-z) and one null row.
            for (int j = 0; j < array[i].length; j++) { // Inner loop, loop through elements in each character row.
                array[i][j] = null; // Setting the elemtents to null.
            }
        }
    }

    /**
     * Finds and returns the longest string in the given input array.
     *  
     * @param words given input array that is needed to be sort.
     * @return the longest string found in the array.
     */
    public static String findLongestString(String[] words) {
        String max = words[0];
        for (int i = 0; i < words.length; i++) { // Loop through the input array and find the longest string.
            if (words[i].length() > max.length()) { // If the current string is longger than the longest string.
                max = words[i]; // Set the current string as the longest string.
            }
        }
        return max;
    }

    /**
     * Distributes strings into the appropriate character row based on the current alphabet place value.
     * 
     * This method handles the core step of radix sort, where strings are sorted by 
     * their alphabet values (a-z) at a character position (length).
     * 
     * Depending on whether we're sorting on the first pass (length == maxStringLength - 1),
     * or alternating between array1 and array2 on subsequent passes, strings are
     * distributed into the correct character row inside a 2D array
     *
     * On the first pass (length == maxStringLength - 1), strings from the input array are placed in array1.
     * When the maximum length of string and the current character position is odd or even 
     * ((maxStringLength % 2 == 0) && (length % 2 == 0)) || ((maxStringLength % 2 == 1) && (length % 2 == 1)), 
     * strings from array1 are moved to array2.
     * When the maximum length of string is odd and the current character position is even, or vice versa
     * ((maxStringLength % 2 == 0) && (length % 2 == 1)) || ((maxStringLength % 2 == 1) && (length % 2 == 0)),
     * strings from array2 are moved to array1. 
     * and so on
     * 
     * The purpose of using this method is to reduce the replication of array, more memory efficient
     * 
     * After distributing, parallel character row counts & previous array is reset, 
     * so it can be used for the next sorting pass.
     * 
     * @param array input array contains strings to be sorted.
     * @param charPosition current character position. For example: 0 for the first character, 1 for the second character, etc.
     */    
    private static void sortingPass(String[] array, int charPosition) {
        
        /**
         * Stores the current character of a string at the given character position.
         * For example: if charPosition == 0, we're sorting by the first character of the string.
         * If the string is 'apple', then character = 'a'.
        */
        char ch;
        int index;

        /**
         * First if statement.
         * On the first pass where charPosition == maxStringLength -1.
         * Sort based on the current character position & moves strings from input array to array1.
         */
        operationCount += 2; // One comparison (charPosition == maxStringLength -1), one minus (maxStringLength -1).
        if (charPosition == maxStringLength -1) { 
            // One assignment (int i = 0).
            operationCount += 1;
            for (int i = 0; i < array.length; i++) { // Loop through the input words array.
                /**
                 * One addition, one assignment (i++ also equivalent to i = i + 1).
                 * One accessing member variable (array.length).
                 * One comparison (i < array.length).
                */
                operationCount += 4;
                String currentWord = array[i]; // Get the current word.

                /**
                 * One array indexing (array[i]).
                 * One assignment (to currentWord).
                */
                operationCount += 2;

                /**
                 * One comparison (charPosition < currentWord.length()).
                 * One accessing member variable (currentWord.length()).
                 */
                operationCount += 2;
                if (charPosition < currentWord.length()) {
                    ch = currentWord.charAt(charPosition); // Get the current character of current word based on the charPosition.
                    /**
                     * One accessing member variable (currentWord.charAt(length)).
                     * One assignment (ch = currentWord.charAt(length)).
                    */
                    operationCount += 2;
                    index = ch - 'a' + 1;
                    /**
                     * One addtion and one minus (ch - 'a' + 1).
                     * One assignment (index = ch - 'a' + 1).
                     */
                    operationCount += 3;
                } else {
                    index = 0;
                    operationCount += 1; // One assignment (index = 0).
                }
                    
                array1[index][charRowCounts[index]] = currentWord; // Assign the current word to the correct char row.
                 /**
                 * Three array indexing:
                 * - array1[index]
                 * - array1[index][charRowCounts[index]]
                 * - charRowCounts[index].
                 * One assignment (store string into character row).
                 */
                operationCount += 4;

                charRowCounts[index]++; 
                /**
                 * Equivalent to (charRowCounts[index] = charRowCounts[index] + 1).
                 * Two array indexing (charRowCounts[index]).
                 * One addition (increment by 1).
                 * One assignment (store the incremented value back).
                 */
                operationCount += 4;
            }

            /**
             * One extra comparison (i < array.length) where i = array.length.
             * One accessing member variable (array.length).

             */
            operationCount += 2;
            resetCharRowCounts();

            // One method call.
            operationCount += 1;
        } 

        /**
         * Executes when the maximum length of string and the current character position is odd or even.
         * Sort based on the current character position.
         * Moves words from array1 to array2.
         */
        else if (((maxStringLength % 2 == 0) && (charPosition % 2 == 0)) || ((maxStringLength % 2 == 1) && (charPosition % 2 == 1))) {
            /**
             * 7 comparisons:
             * - maxStringLength % 2 == 0
             * - length % 2 == 0
             * - maxStringLength % 2 == 1
             * - length % 2 == 1
             * - (maxStringLength % 2 == 0) && (length % 2 == 0)
             * - (maxStringLength % 2 == 1) && (length % 2 == 1)
             * - ((maxStringLength % 2 == 0) && (length % 2 == 0)) || ((maxStringLength % 2 == 1) && (length % 2 == 1))
             * four modulo (%).
             */
            operationCount += 11;

            // One assignment (int i = 0).
            operationCount += 1;
            for (int i = 0; i < array1.length; i++) { // Outer loop, loop through 27 character rows of array1.
                /**
                 * One addition, one assignment (i++ also equivalent to i = i + 1).
                 * One accessing member variable (array1.length).
                 * One comparison (i < array1.length).
                */
                operationCount += 4;

                // One assignment (int j = 0).
                operationCount += 1;
                for (int j = 0; j < array1[i].length; j++) { // Inner loop, loop through word contains in the particular char row.
                    /**
                     * One addition, one assignment (j++ also equivalent to j = j + 1).
                     * One accessing member variable (array1[i].length).
                     * One array indexing (array1[i]).
                     * One comparison (j < array.length).
                    */
                    operationCount += 5;

                    String currentWord = array1[i][j]; // Get the current word.
                    /**
                     * Two array indexing (array1[i][j]).
                     * One assignment (to currentWord).
                     */
                    operationCount += 3;

                    // One comparison (currentWord == null).
                    operationCount += 1;
                    if (currentWord == null) break; // If the current word is null, means there's no other more words left in this char rows, we break the inner loop.
                    
                    /**
                     * One comparison (length < currentWord.length()).
                     * One accessing member variable (currentWord.length()).
                     */
                    operationCount += 2;
                    if (charPosition < currentWord.length()) {
                        ch = currentWord.charAt(charPosition); // get the current word character based on the charPosition.
                        /**
                         * One accessing member variable (currentWord.charAt(length)).
                         * One assignment (ch = currentWord.charAt(length)).
                         */
                        operationCount += 2;

                        index = ch - 'a' + 1;
                        /**
                         * One addtion and one minus (ch - 'a' + 1).
                         * One assignment (index = ch - 'a' + 1).
                         */
                        operationCount += 3;
                    } else {
                        index = 0;
                        // One assignment (index = 0).
                        operationCount += 1; 
                    }
                    array2[index][charRowCounts[index]] = currentWord; // Assign the current word to the correct char row.
                    /**
                     * Three array indexing:
                     * - array2[index]
                     * - array2[index][charRowCounts[index]]
                     * - charRowCounts[index].
                     * One assignment (store string into character row).
                     */
                    operationCount += 4;

                    charRowCounts[index]++; // increment the counts of element in that row
                    /**
                     * Equivalent to (charRowCounts[index] = charRowCounts[index] + 1).
                     * Two array indexing (charRowCounts[index]).
                     * One addition (increment by 1).
                     * One assignment (store the incremented value back).
                     */
                    operationCount += 4;
                }
                /**
                * One extra comparison (j < array1[i].length) where j = array1[i].length, reached end of char rows.
                * One accessing member variable (array1[i].length).
                * One array indexing (array1[i]).
                */
                operationCount += 3;
            }
            /**
             * One extra comparison (i < array1.length) where i = array1.length.
             * One accessing member variable (array1.length).
             */
            operationCount += 2;
            clearArray(array1);
            resetCharRowCounts();
            
            // Two method calls.
            operationCount += 2;
        } 
        /**
         * Executes when the maximum length of string is odd and the current character position is even, or vice versa.
         * Sort based on the current character position.
         * Moves words from array2 to array1.
        */  
        else {
            /**
             * 7 comparisons:
             * - maxStringLength % 2 == 0
             * - length % 2 == 0
             * - maxStringLength % 2 == 1
             * - length % 2 == 1
             * - (maxStringLength % 2 == 0) && (length % 2 == 0)
             * - (maxStringLength % 2 == 1) && (length % 2 == 1)
             * - ((maxStringLength % 2 == 0) && (length % 2 == 0)) || ((maxStringLength % 2 == 1) && (length % 2 == 1))
             * four modulo (%)
             */
            operationCount += 11;

            // One assignement (int i = 0).
            operationCount += 1;
            for (int i = 0; i < array2.length; i++) { // Outer loop, loop through 27 characters rows of array2.
                /**
                 * One addition, one assignment (i++ also equivalent to i = i + 1).
                 * One accessing member variable (array2.length).
                 * One comparison (i < array2.length).
                 */
                operationCount += 4;

                // One assignment (int j = 0).
                operationCount += 1;
                for (int j = 0; j < array2[i].length; j++) { // Inner loop, loop through words contains in the particular char row.
                    /**
                     * One addition, one assignment (j++ also equivalent to j = j + 1).
                     * One accessing member variable (array2[i].length).
                     * One array indexing (array2[i]).
                     * One comparison (j < array.length).
                    */
                    operationCount += 5;

                    String currentWord = array2[i][j]; // get the current word
                    /**
                     * Two array indexing (array2[i][j]).
                     * One assignment (to currentWord).
                     */
                    operationCount += 3;

                    // One comparison (currentWord == null).
                    operationCount += 1;
                    if (currentWord == null) break; // If the current word is null, means there's no other more words left in this char rows, we break the inner loop.
                    
                    /**
                     * One comparison (length < currentWord.length()).
                     * One accessing member variable (currentWord.length()).
                     */
                    operationCount += 2;
                    if (charPosition < currentWord.length()) {
                        ch = currentWord.charAt(charPosition); // Get the current word char based on the charPosition.
                        /**
                         * One accessing member variable (currentWord.charAt(length)).
                         * One assignment (ch = currentWord.charAt(length)).
                         */
                        operationCount += 2;

                        index = ch - 'a' + 1;
                        /**
                         * One addtion and one minus (ch - 'a' + 1).
                         * One assignment (index = ch - 'a' + 1).
                         */
                        operationCount += 3;
                    } else {
                        index = 0;
                        // One assignment (index = 0).
                        operationCount += 1;
                    }

                    array1[index][charRowCounts[index]] = currentWord; // Assign the current word to the correct char row.
                    /**
                     * Three array indexing:
                     * - array1[index]
                     * - array1[index][charRowCounts[index]]
                     * - charRowCounts[index].
                     * One assignment (store string into character row).
                     */
                    operationCount += 4;

                    charRowCounts[index]++; // Increment the counts of element in that row.
                    /**
                     * Equivalent to (charRowCounts[index] = charRowCounts[index] + 1).
                     * Two array indexing (charRowCounts[index]).
                     * One addition (increment by 1).
                     * One assignment (store the incremented value back).
                    */
                    operationCount += 4;
                }
                /**
                 * One extra comparison (j < array2[i].length) where j = array2[i].length, reached end of character rows.
                 * One accessing member variable (array1[i].length).
                 * One array indexing (array1[i]).
                */
                operationCount += 3;
            }
            /**
             * One extra comparison (i < array2.length) where i = array2.length.
             * One accessing member variable (array2.length).
             */
            operationCount += 2;
            clearArray(array2);
            resetCharRowCounts();

            // Two method calls.
            operationCount += 2;
        }
    }

    /**
     * Displays the current state of the character rows during the sorting pass based on the given character position.
     * The method determines which array (array1 or array2) to display based on the two conditions:
     * - If the maximum string length is even and the current character position is odd or vice versa, it displays array1.
     * - Else, it displays array2.
     *
     * @param charPosition the current character position based on which sorting pass is happening.
     * 
     * - Chooses the active 2D array (either array1 or array2) depending on whether the maximum string length is even and the current character position is odd or vice versa.
     * - Iterates over each character row (a-z and one null row) and prints the strings stored in it, stopping at null (empty spots, means there is no more strings left behind).
    */
    public static void displayArray(int charPosition) {
        String[][] activeArray;

        // If the maximum string length is even and the current character position is odd or vice versa, use array1; otherwise, use array2.
        if (((maxStringLength % 2 == 0) && (charPosition % 2 == 1)) || ((maxStringLength % 2 == 1) && (charPosition % 2 == 0))) {
            activeArray = array1;
        } else {
            activeArray = array2;
        }

        for (int i = 0; i < activeArray.length; i++) { // Outer loop, loop through 27 character row of active array.
            int ch;
            if (i == 0) ch = 32;
            else ch = i + 64;
            System.out.print(((char) ch) + ": ");
            for (int j = 0; j < activeArray[i].length; j++) { // Inner loop, loop through every strings contain in that character row.
                if (activeArray[i][j] == null) break; // If the current string is null, means there's no other more strings left in this character rows, we break the inner loop.
                System.out.print(activeArray[i][j] + " "); // Display the strings in the active array.
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Method used to reorder strings from the 2D array into a 1D array after the final sorting pass.
     * It scans the final active sorting array (array1 or array2 depending on maxStringLength)
     * and transfers all non-null values into a one-dimensional SortedList array.
    */
    public static void Reorder() {
        // Declare LastArray to point to the final sorted array based on maxStringLength's parity.
        String[][] LastArray;
        int totalElements = 0;

        // Choose between array1 and array2, which one is the last sorting pass array depending on whether maxStringLength is even or odd.
        if (maxStringLength % 2 == 0) {
            LastArray = array2;
        } else {
            LastArray = array1;
        }
        
        // Count how many words elements are in LastArray to determine the size of SortedList.
        for (int i = 0; i < LastArray.length; i++) {
            for (int j = 0; j < LastArray[i].length; j++) {
                if (LastArray[i][j] != null) {
                    totalElements++;
                }
            }
        }

        // Initialize the SortedList array with the exact number of non-null elements.
        SortedList = new String[totalElements];
        int index = 0;

        // Transfer all the words from LastArray into SortedList sequentially.
        for (int i = 0; i < LastArray.length; i++) {
            for (int j = 0; j < LastArray[i].length; j++) {
                if (LastArray[i][j] != null) {
                    SortedList[index++] = LastArray[i][j];
                }
            }
        }
    }

    /**
     * Methods that display the final sorted list after the sorting algorithm.
     */
    public static void displaySortedList() {
        for (int i = 0; i < SortedList.length; i++) {
            System.out.print(SortedList[i] + " ");
        }
    }

    /** 
     * Methods use to sort an array of unsorted words using radix sort.
     * @param words Array of words to be sorted.
     */
    public static void Sort(String[] words) {
        
        // Call this method to initialize the 2D array and charRowCounts array with the size of the input array.
        initializeArray(words.length);
        operationCount += 1; // One method call (initializeArray(words.length)).

        // Find the longest string in the unsorted word array to determine the maximum string length needed for sorting.
        String longestString = findLongestString(words);
        /**
         * One method call (findLongestString(words)).
         * One assignment (longestString = findLongestString(words)).
         */
        operationCount += 2; 

        // Get the maximum string length of the longest string found in the array.
        maxStringLength = longestString.length();
        /**
         * One assignment (maxStringLength = longestString.length()).
         * One accessing member variable (longestString.length()).
        */
        operationCount += 2;

        int passCount = 1;
        // One assignment (passCount = 1).
        operationCount += 1;

         // Loop through each character position until charcter position is less than 0.
         // One assignment (int length = maxStringLength - 1).
         operationCount += 1;
        for (int charPosition = maxStringLength - 1; charPosition >= 0; charPosition--) {
            /**
             * One comparison (length >= 0).
             * One minus and one assignment (length--).
             */
            operationCount += 3;

            // Perform sorting pass based on current character position.
            sortingPass(words, charPosition);
            operationCount += 1; // one method call

            // Print the array state after sorted by the current character position.
            System.out.println("=== After sorting  " + passCount + " ===");
            displayArray(charPosition);
            passCount++;
        }
        // One extra comparison (length >= 0) where length = -1, reached the end of the loop.
        operationCount += 2;
    }

    public static void main(String[] args) {

        // string array to sort
        String[] words = {"kfd", "tuz", "qmh", "bvn", "azp", "wle", "jxo", "rcy", "mqa", "znb", "plv", "gdr", "hxt", "syu", "vcm", "ebk", "djy", "fpo", "icr", "bzu", "nwl", "oyk", "qtb", "jcl", "xkp", "lrw", "qoa", "vbs", "azx", "tmn", "rps", "lyd", "pwh", "gsl", "mnb", "cfj", "jwd", "qex", "ytr", "vkp", "ufz", "kqx", "hjy", "wvm", "pxn", "oer", "sdf", "lqi", "zxy", "vfw", "mek", "pju", "rkt", "sgb", "wqz", "yhn", "oxv", "fkn", "dwp", "zmg", "bqt", "lvs", "ycp", "rjh", "pym", "kzw", "vtd", "hqf", "xlg", "srn", "cby", "ojd", "wfg", "mpx", "zrk", "tjc", "vhy", "bnq", "sdv", "kyu"}       ;

        // Display the initial array of strings before sorting
        System.out.println("=== Initial array ===");
        System.out.print("Initial array: ");
        for (String word : words) {
            System.out.print(word + " ");
        }
        System.out.println("\n");

        // Pass the array to the Sorting algorithm methods.
        Sort(words);

        // Reorder after sorting.
        Reorder();

        // Display the final array after sort.
        System.out.println("=== Final sorted array ===");
        System.out.print("Sorted array: "); 
        displaySortedList();

        // Display how many operation count is in the sorting algorithm.
        System.out.println("\nTotal primitive operations: " + operationCount);
    }
}
