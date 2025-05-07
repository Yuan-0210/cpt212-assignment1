public class StringSort {

    // Counter to track the primitive operations
    private static int operationCount = 0;

    /**
     * Two 2D arrays used for sorting
     *  - Each row represents a digit (0-9)
     *  - Each column holds numbers assigned to that digit during sorting.
     *
     * We use Integer (object) instead of int (primitive) so we can set unused elements to null.
     * This avoids confusion in cases where the input array might contain actual zero values that is needed to sort.
    */
    private static String[][] array1;
    private static String[][] array2;

    // 
    private static String[] SortedList;

    // Parallel array that keep track the number of elements in each digit row (0-9)
    private static int[] charRowCounts;

    // Store the largest number's digit length
    private static int maxStringLength;

    /**
     * Initializes two 2D arrays (array1 and array2) with 10 rows 
     * for digits 0–9 and 'size' columns (based on the number of elements to sort).
     *
     * Worst case: all numbers could go into the same digit row , 
     * so each row needs 'size' slots to avoid overflow.
     *
     * Example: for input {23, 45, 12, 99, 5} (5 numbers)
     * → creates array1 and array2 of size [10][5].
     *
     * @param size the number of elements in the input array
    */
    public static void initializeArray(int size) {
        array1 = new String[27][size]; 
        array2 = new String[27][size]; 
        charRowCounts = new int[27];
    }

    /**
     *  Reset the count of elements in each digit row to zero before the next sorting pass
    */
    private static void resetCharRowCounts() {
        for (int i = 0; i < 27; i++) {
            charRowCounts[i] = 0;
        }
    }

    /**
     * Clear all numbers in the given 2D array by setting them to NULL.
     * 
     * This ensures the digit row is empty and ready for the next sorting pass.
     * @param array the 2D array to clear, (Setting all elements in the digit row from 0-9 to null)
    */
    private static void clearArray(String[][] array) {
        for (int i = 0; i < array.length; i++) { // outer loop, loop through 10 digit row (0-9)
            for (int j = 0; j < array[i].length; j++) { // inner loop, loop through elements in each digit row
                array[i][j] = null; // setting the elemtents to null
            }
        }
    }

    /**
     * Finds and returns the maximum value in the given input array
     *  
     * @param array given input array that is needed to be sort
     * @return the maximum value found in the array
     */
    public static String findLongestString(String[] words) {
        String max = words[0];
        for (int i = 0; i < words.length; i++) { // loop through the input array and find the largest value
            if (words[i].length() > max.length()) { // if the current number is bigger than the maximum value
                max = words[i]; // set the maximum value to the current number
            }
        }
        return max;
    }

    /**
     * Distributes numbers into the appropriate digit row based on the current digit place value.
     * 
     * This method handles the core step of radix sort, where numbers are sort by 
     * their digit values (0-9) at a given place value (1, 10, 100, 1000 etc.)
     * 
     * Depending on whether we're sorting on the first pass (placeValue == 1),
     * or alternating between array1 and array2 on subsequent passes, numbers are
     * distributed into the correct digit row inside a 2D array
     *
     * On the first pass (placeValue == 1), numbers from the input array are placed in array1.
     * On odd passes (log10(placeValue) % 2 == 1), numbers from array1 are moved to array2.
     * On even passes (log10(placeValue) % 2 == 0), numbers from array2 are moved to array1. 
     * and so on
     * 
     * The purpose of using this method is to reduce the replication of array, more memory efficient
     * 
     * After distributing, parallel digit row counts & previous array is reset, 
     * so it can be used for the next sorting pass.
     * 
     * @param array input array contains numbers to be sort, only used for the first sorting pass
     * @param placeValue current place value (1, 10, 100, 1000 etc.)
     */    
    private static void sortingPass(String[] array, int length) {
        
        /**
         * Stores the current digit of a number at the given place value.
         * For example: if placeValue == 10, we're sorting by the tens place.
         * If the number is 30, then digit = 3.
        */
        char ch;
        int index;

        /**
         * First if statement
         * On the first pass where placeValue == 1
         * Sort based on the current place value & moves numbers from input array to array1
         */
        operationCount += 1; // one comparison (placeValue == 1)
        if (length == maxStringLength -1) {
            // one assignment (int i = 0)
            operationCount += 1;
            for (int i = 0; i < array.length; i++) { // Loop through the input number array
                /**
                 * one addition, one assignment (i ++ also equivalent to i = i + 1)
                 * one accessing member variable (array.length)
                 * one comparison (i < array.length) 
                */
                operationCount += 4;
                String currentWord = array[i];

                /**
                 * one array indexing (array[i])
                 * one assignment (to currentNumber)
                */
                operationCount += 2;
                if (length < currentWord.length()) {
                    ch = currentWord.charAt(length); // get the current numbers digit based on the placeValue
                    index = ch - 'a' + 1;
                } else 
                    index = 0;
                array1[index][charRowCounts[index]] = currentWord;


                /**
                 * three array indexing:
                 * - array1[digit]
                 * - array1[digit][digitRowCounts[digit]]
                 * - digitRowCounts[digit]
                 * one assignment (store number into digit row)
                 */
                operationCount += 4;
                charRowCounts[index]++; // increment the counts of element in that row

                /**
                 * equivalent to (digitRowCounts[digit] = digitRowCounts[digit] + 1)
                 * two array indexing (digitRowCounts[digit])
                 * one addition (increment by 1)
                 * one assignment (store the incremented value back)
                */
                operationCount += 4;
            }

            /**
             * one extra comparison (i < array.length) where i = array.length
             * one accessing member variable (array.length)
             */
            operationCount += 2;
            resetCharRowCounts();

            // one method call
            operationCount += 1;
        } 

        /**
         * Executes during odd passes (when placeValue is 10, 1000, 100000, etc.)
         * Sort based on the current placeValue
         * Moves numbers from array1 to array2
         */
        else if (((maxStringLength % 2 == 0) && (length % 2 == 0)) || ((maxStringLength % 2 == 1) && (length % 2 == 1))) {
            /**
             * one method call (log10())
             * one modulo (%)
             * one comparison
             */
            operationCount += 3;

            // one assignment (int i = 0)
            operationCount += 1;
            for (int i = 0; i < array1.length; i++) { // Outer loop, loop through 10 digit rows of array1
                /**
                 * one addition, one assignment (i ++ also equivalent to i = i + 1)
                 * one accessing member variable (array1.length)
                 * one comparison (i < array1.length) 
                */
                operationCount += 4;

                // one assignment (int j = 0)
                operationCount += 1;
                for (int j = 0; j < array1[i].length; j++) { // Inner loop, loop through numbers contains in the particular digit row
                    /**
                     * one addition, one assignment (j ++ also equivalent to j = j + 1)
                     * one accessing member variable (array1[i].length)
                     * one array indexing (array1[i])
                     * one comparison (j < array.length) 
                    */
                    operationCount += 5;

                    String currentWord = array1[i][j];
                    /**
                     * two array indexing (array1[i][j])
                     * one assignment (to currentNumber)
                     */
                    operationCount += 3;
                    if (currentWord == null) break; // if the current number is null, means there's no other more numbers left in this digit rows, we break the inner loop
                    
                    // one comparison
                    operationCount += 1;
                    if (length < currentWord.length()) {
                        ch = currentWord.charAt(length); // get the current numbers digit based on the placeValue
                        index = ch - 'a' + 1;
                    } else 
                        index = 0;
                    array2[index][charRowCounts[index]] = currentWord;
                    /**
                     * three array indexing:
                     * - array2[digit]
                     * - array2[digit][digitRowCounts[digit]]
                     * - digitRowCounts[digit]
                     * one assignment (store number into digit row)
                     */
                    operationCount += 4;
                    charRowCounts[index]++; // increment the counts of element in that row

                    /**
                     * equivalent to (digitRowCounts[digit] = digitRowCounts[digit] + 1)
                     * two array indexing (digitRowCounts[digit])
                     * one addition (increment by 1)
                     * one assignment (store the incremented value back)
                    */
                    operationCount += 4;
                }
                /**
                 * one extra comparison (j < array1[i].length) where j = array1[i].length, reached end of digit rows
                 * one accessing member variable (array1[i].length)
                 * one array indexing (array1[i])
                */
                operationCount += 3;
            }
            /**
             * one extra comparison (i < array1.length) where i = array1.length
             * one accessing member variable (array1.length)
             */
            operationCount += 2;
            clearArray(array1);
            resetCharRowCounts();
            
            // two method calls
            operationCount += 2;
        } 
        /**
         * Executes during even passes (when placeValue is 100, 10000, 1000000, etc.)
         * Sort based on the current placeValue
         * Moves numbers from array2 to array1
        */  
        else {
            /**
             * If the code reaches here, it means the condition in the previous 'else if' was false.
             * Increase operations involved in the evaluation of the 'else if' condition:
             * 
             * one method call (log10())
             * one modulo (%)
             * one comparison
            */
            operationCount += 3;

            // one assignement (int i = 0)
            operationCount += 1;
            for (int i = 0; i < array2.length; i++) { // Outer loop, loop through 10 digit rows of array2
                /**
                 * one addition, one assignment (i ++ also equivalent to i = i + 1)
                 * one accessing member variable (array2.length)
                 * one comparison (i < array2.length) 
                */
                operationCount += 4;

                // one assignment (int j = 0)
                operationCount += 1;
                for (int j = 0; j < array2[i].length; j++) { // Inner loop, loop through numbers contains in the particular digit row
                    /**
                     * one addition, one assignment (j++ also equivalent to j = j + 1)
                     * one accessing member variable (array2[i].length)
                     * one array indexing (array2[i])
                     * one comparison (j < array.length) 
                    */
                    operationCount += 5;

                    String currentWord = array2[i][j];
                    /**
                     * two array indexing (array2[i][j])
                     * one assignment (to currentNumber)
                     */
                    operationCount += 3;
                    if (currentWord == null) break; // if the current number is null, means there's no other more numbers left in this digit rows, we break the inner loop
                    
                    // one comparison
                    operationCount += 1;
                    if (length < currentWord.length()) {
                        ch = currentWord.charAt(length); // get the current numbers digit based on the placeValue
                        index = ch - 'a' + 1;
                    } else 
                        index = 0;
                    array1[index][charRowCounts[index]] = currentWord;// get the current numbers digit based on the placeValue


                    /**
                     * three array indexing:
                     * - array1[digit]
                     * - array1[digit][digitRowCounts[digit]]
                     * - digitRowCounts[digit]
                     * one assignment (store number into digit row)
                     */
                    operationCount += 4;
                    charRowCounts[index]++; // increment the counts of element in that row

                    /**
                     * equivalent to (digitRowCounts[digit] = digitRowCounts[digit] + 1)
                     * two array indexing (digitRowCounts[digit])
                     * one addition (increment by 1)
                     * one assignment (store the incremented value back)
                    */
                    operationCount += 4;
                }
                /**
                 * one extra comparison (j < array2[i].length) where j = array2[i].length, reached end of digit rows
                 * one accessing member variable (array1[i].length)
                 * one array indexing (array1[i])
                */
                operationCount += 3;
            }
            /**
             * one extra comparison (i < array2.length) where i = array2.length
             * one accessing member variable (array2.length)
             */
            operationCount += 2;
            clearArray(array2);
            resetCharRowCounts();

            // two method calls
            operationCount += 2;
        }
    }

    /**
     * Displays the current state of the digit rows during the sorting pass based on the given placeValue.
     * The method determines which bucket array (array1 or array2) to display based on whether the place value 
     * corresponds to an odd or even digit pass.
     *
     * @param length the current place value (1, 10, 100, etc.) based on which sorting pass is happening
     * 
     * - Chooses the active 2D array (either array1 or array2) depending on whether the current place value is odd or even.
     * - Iterates over each digit row (0-9) and prints the numbers stored in in, stopping at null (empty spots, means there is no more numbers left behind).
    */
    public static void displayArray(int length) {
        String[][] activeArray;

        // if it is odd pass, the active array is array2, else it is array1
        if (((maxStringLength % 2 == 0) && (length % 2 == 1)) || ((maxStringLength % 2 == 1) && (length % 2 == 0))) {
            activeArray = array1;
        } else {
            activeArray = array2;
        }

        for (int i = 0; i < activeArray.length; i++) { // Outer loop, loop through 10 digit row of active array
            int ch;
            if (i == 0) ch = 32;
            else ch = i + 64;
            System.out.print(((char) ch) + ": ");
            for (int j = 0; j < activeArray[i].length; j++) { // Inner loop, loop through every numbers contain in that digit row
                if (activeArray[i][j] == null) break; // if the current number is null, means there's no other more numbers left in this digit rows, we break the inner loop
                System.out.print(activeArray[i][j] + " "); // display the number in the digit row
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Method used to reorder numbers from the 2D array into a 1D array after the final sorting pass.
     * It scans the final active sorting array (array1 or array2 depending on maxStringLength)
     * and transfers all non-null values into a one-dimensional SortedList array.
    */
    public static void Reorder() {
        // Declare LastArray to point to the final sorted array based on maxStringLength's parity
        String[][] LastArray;
        int totalElements = 0;

        // Choose between array1 and array2, which one is the last sorting pass array depending on whether maxStringLength is even or odd
        if (maxStringLength % 2 == 0) {
            LastArray = array2;
        } else {
            LastArray = array1;
        }
        
        // Count how many number elements are in LastArray to determine the size of SortedList
        for (int i = 0; i < LastArray.length; i++) {
            for (int j = 0; j < LastArray[i].length; j++) {
                if (LastArray[i][j] != null) {
                    totalElements++;
                }
            }
        }

        // Initialize the SortedList array with the exact number of non-null elements
        SortedList = new String[totalElements];
        int index = 0;

        // Transfer all the numbers from LastArray into SortedList sequentially
        for (int i = 0; i < LastArray.length; i++) {
            for (int j = 0; j < LastArray[i].length; j++) {
                if (LastArray[i][j] != null) {
                    SortedList[index++] = LastArray[i][j];
                }
            }
        }
    }

    /**
     * Methods that display the final sorted list after the sorting algorithm
     */
    public static void displaySortedList() {
        for (int i = 0; i < SortedList.length; i++) {
            System.out.print(SortedList[i] + " ");
        }
    }

    /** 
     * Methods use to sort an array of unsorted numbers using radix sort
     * @param numbers Array of integers to be sorted.
     */
    public static void Sort(String[] words) {
        
        // initialize two 2D array instances with numbers.length that is use for sorting
        initializeArray(words.length);
        operationCount += 1; // one method call

        // Find the largest number in the unsorted number array to determine the maximum digit length needed for sorting
        String largestValue = findLongestString(words);
        operationCount += 2; // one method call, one assignment

        // Calculate how many digit places (maxStringLength) the sorting needs to process
        maxStringLength = largestValue.length();
        operationCount += 1; // one method call

        int passCount = 1;
        // Loop through each digit place (1s, 10s, 100s, etc.) until no higher place exists
        operationCount += 1; // one assignment (placeValue = 1)
        for (int length = maxStringLength - 1; length >= 0; length--) {
            /** 
             * one addition, one assignment (placeValue *= 10) 
             * one arithmetic operation (division /)
             * one comparison (largestValue / placeValue)
            */
            operationCount += 4;

            // perform sorting pass based on current placed value
            sortingPass(words, length);
            operationCount += 1; // one method call

            // print the array state after sorted by this digit place
            System.out.println("=== After sorting  " + passCount + " ===");
            displayArray(length);
            passCount++;
        }
        // one extra comparison (largestValue / placeValue > 0), one arithmetic operation (division /)
        operationCount += 2;
    }

    public static void main(String[] args) {

        // number array to sort
        String[] words = {"apple", "aeroplan", "bat", "baby", "cherry", "zebra", "ape", "lizard", "bird", 
                            "eagle", "elephant", "giraffe", "deer", "lion", "tiger", "kill", "first",
                            "dragon", "hat", "whale", "beef", "house", "yatch", "xylem", "blood",
                            "pop", "push", "oil", "man", "red", "quartz", "dance", "juice", "orange"};

        // display the initial array of numbers
        System.out.println("=== Initial array ===");
        System.out.print("Initial array: ");
        for (String word : words) {
            System.out.print(word + " ");
        }
        System.out.println("\n");

        // pass the array to the Sorting algorithm methods
        Sort(words);

        // Reorder after sorting
        Reorder();

        // display the final array after sort
        System.out.println("=== Final sorted array ===");
        System.out.print("Sorted array: "); 
        displaySortedList();

        // display how many operation count is in the sorting algorithm
        System.out.println("\nTotal primitive operations: " + operationCount);
    }
}
