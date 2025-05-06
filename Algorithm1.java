public class Algorithm1 {

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
    private static Integer[][] array1;
    private static Integer[][] array2;

    // 
    private static int[] SortedList;

    // Parallel array that keep track the number of elements in each digit row (0-9)
    private static int[] digitRowCounts = new int[10];

    // Store the largest number's digit length
    private static int maxDigitLength;

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
        array1 = new Integer[10][size]; // one assignment
        array2 = new Integer[10][size]; // one assignment
        operationCount += 2; // operation count is 1 + 1
    }

    /**
     *  Reset the count of elements in each digit row to zero before the next sorting pass
    */
    private static void resetDigitRowCounts() {
        // one assignment (int i = 0)
        operationCount += 1;
        
        for (int i = 0; i < 10; i++) {
            // one addition, one assignment (i++ also equivalent to i = i + 1), one comparison (i < 10)
            operationCount += 3;
            digitRowCounts[i] = 0;

            // one array indexing, one assignment
            operationCount += 2;
        }
        // one extra comparison (10 < 10)
        operationCount += 1;
    }

    /**
     * Clear all numbers in the given 2D array by setting them to NULL.
     * 
     * This ensures the digit row is empty and ready for the next sorting pass.
     * @param array the 2D array to clear, (Setting all elements in the digit row from 0-9 to null)
    */
    private static void clearArray(Integer[][] array) {
        
        // one assignmnet (int i = 0)
        operationCount += 1;
        for (int i = 0; i < array2.length; i++) { // outer loop, loop through 10 digit row (0-9)
            /** 
             * one addition, one assignment (i++ also equivalent to i = i + 1) 
             * one accessing member variable (bucket.length)
             * one comparison (i < bucket.length)
            */
            operationCount += 4;

            // one assignment (int j = 0)
            operationCount += 1;

            for (int j = 0; j < array[i].length; j++) { // inner loop, loop through elements in each digit row
                /**
                 * one addition, one assignment (j++ also equivalent to j = j + 1)
                 * one accessing member variable, one array indexing (bucket[i].length)
                 * one comparison (j < bucket[i].length)
                 */
                operationCount += 5;
                array[i][j] = null; // setting the elemtents to null
                
                // one array indexing, one assignment
                operationCount += 2;
            }
            /**
             * one extra comparison (j < bucket[i].length where j = bucket[i].length)
             * one array indexing when comparing
             * one member variable accesing when comparing
             */
            operationCount += 3;
        }
        /**
         * one extra comparison (i < bucket.length where i = bucket.length)
         * one member variable accessing when comparing
        */
        operationCount += 2;
    }

    /**
     * Finds and returns the maximum value in the given input array
     *  
     * @param array given input array that is needed to be sort
     * @return the maximum value found in the array
     */
    public static int findLargestValue(int[] array) {

        int max = array[0]; // declare integer max that keep track the maximum value inside the input array
        
        // one array indexing (array[0]), one assignment (max = array[0]) 
        operationCount += 2;

        // one assignment (int i = 0)
        operationCount += 1;
        for (int i = 0; i < array.length; i++) { // loop through the input array and find the largest value
            /**
             * one addition, one assignment (i++ also equivalent to i = i + 1)
             * one accessing member variable (array.length)
             * one comparison (i < array.length)
            */
            operationCount += 4;

            // one array indexing (array[i]), one comparison (array[i] > max)
            operationCount += 2;
            if (array[i] > max) { // if the current number is bigger than the maximum value
                max = array[i]; // set the maximum value to the current number

                // one array indexing, one assignment
                operationCount += 2;
            }
        }
        /**
         * one extra comparison (i < array.length), where i = array.length
         * one accessing member variable
        */
        operationCount += 2;
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
    private static void sortingPass(int[] array, int placeValue) {
        
        /**
         * Stores the current digit of a number at the given place value.
         * For example: if placeValue == 10, we're sorting by the tens place.
         * If the number is 30, then digit = 3.
        */
        int digit;

        /**
         * First if statement
         * On the first pass where placeValue == 1
         * Sort based on the current place value & moves numbers from input array to array1
         */
        operationCount += 1; // one comparison (placeValue == 1)
        if (placeValue == 1) {

            // one assignment (int i = 0)
            operationCount += 1;
            for (int i = 0; i < array.length; i++) { // Loop through the input number array
                /**
                 * one addition, one assignment (i ++ also equivalent to i = i + 1)
                 * one accessing member variable (array.length)
                 * one comparison (i < array.length) 
                */
                operationCount += 4;

                int currentNumber = array[i];

                /**
                 * one array indexing (array[i])
                 * one assignment (to currentNumber)
                */
                operationCount += 2;
                digit = (currentNumber / placeValue) % 10; // get the current numbers digit based on the placeValue

                /**
                 * one division (/)
                 * one modulo (%)
                 * one assignment (to digit)
                 */
                operationCount += 3;
                array1[digit][digitRowCounts[digit]] = currentNumber; // assign the current number to the correct digit row

                /**
                 * three array indexing:
                 * - array1[digit]
                 * - array1[digit][digitRowCounts[digit]]
                 * - digitRowCounts[digit]
                 * one assignment (store number into digit row)
                 */
                operationCount += 4;
                digitRowCounts[digit]++; // increment the counts of element in that row

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
            resetDigitRowCounts();

            // one method call
            operationCount += 1;
        } 

        /**
         * Executes during odd passes (when placeValue is 10, 1000, 100000, etc.)
         * Sort based on the current placeValue
         * Moves numbers from array1 to array2
         */
        else if (Math.log10(placeValue) % 2 == 1) {
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

                    Integer currentNumber = array1[i][j];
                    /**
                     * two array indexing (array1[i][j])
                     * one assignment (to currentNumber)
                     */
                    operationCount += 3;
                    if (currentNumber == null) break; // if the current number is null, means there's no other more numbers left in this digit rows, we break the inner loop
                    
                    // one comparison
                    operationCount += 1;
                    digit = (currentNumber / placeValue) % 10; // get the current numbers digit based on the placeValue
                    /**
                     * one division (/)
                     * one modulo (%)
                     * one assignment (to digit)
                    */
                    operationCount += 3;
                    array2[digit][digitRowCounts[digit]] = currentNumber; // assign the current number to the correct digit row

                    /**
                     * three array indexing:
                     * - array2[digit]
                     * - array2[digit][digitRowCounts[digit]]
                     * - digitRowCounts[digit]
                     * one assignment (store number into digit row)
                     */
                    operationCount += 4;
                    digitRowCounts[digit]++; // increment the counts of element in that row

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
            resetDigitRowCounts();
            
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

                    Integer currentNumber = array2[i][j];
                    /**
                     * two array indexing (array2[i][j])
                     * one assignment (to currentNumber)
                     */
                    operationCount += 3;
                    if (currentNumber == null) break; // if the current number is null, means there's no other more numbers left in this digit rows, we break the inner loop
                    
                    // one comparison
                    operationCount += 1;
                    digit = (currentNumber / placeValue) % 10; // get the current numbers digit based on the placeValue
                    /**
                     * one division (/)
                     * one modulo (%)
                     * one assignment (to digit)
                    */
                    operationCount += 3;
                    array1[digit][digitRowCounts[digit]] = currentNumber; // assign the current number to the correct digit row

                    /**
                     * three array indexing:
                     * - array1[digit]
                     * - array1[digit][digitRowCounts[digit]]
                     * - digitRowCounts[digit]
                     * one assignment (store number into digit row)
                     */
                    operationCount += 4;
                    digitRowCounts[digit]++; // increment the counts of element in that row

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
            resetDigitRowCounts();

            // two method calls
            operationCount += 2;
        }
    }

    /**
     * Displays the current state of the digit rows during the sorting pass based on the given placeValue.
     * The method determines which bucket array (array1 or array2) to display based on whether the place value 
     * corresponds to an odd or even digit pass.
     *
     * @param placeValue the current place value (1, 10, 100, etc.) based on which sorting pass is happening
     * 
     * - Chooses the active 2D array (either array1 or array2) depending on whether the current place value is odd or even.
     * - Iterates over each digit row (0-9) and prints the numbers stored in in, stopping at null (empty spots, means there is no more numbers left behind).
    */
    public static void displayArray(int placeValue) {
        Integer[][] activeArray;

        // if it is odd pass, the active array is array2, else it is array1
        if (Math.log10(placeValue) % 2 == 1) {
            activeArray = array2;
        } else {
            activeArray = array1;
        }

        for (int i = 0; i < activeArray.length; i++) { // Outer loop, loop through 10 digit row of active array
            System.out.print(i + ": ");
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
     * It scans the final active sorting array (array1 or array2 depending on maxDigitLength)
     * and transfers all non-null values into a one-dimensional SortedList array.
    */
    public static void Reorder() {
        // Declare LastArray to point to the final sorted array based on maxDigitLength's parity
        Integer[][] LastArray;
        int totalElements = 0;

        // Choose between array1 and array2, which one is the last sorting pass array depending on whether maxDigitLength is even or odd
        if (maxDigitLength % 2 == 0) {
            LastArray = array1;
        } else {
            LastArray = array2;
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
        SortedList = new int[totalElements];
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
    public static void Sort(int [] numbers) {
        
        // initialize two 2D array instances with numbers.length that is use for sorting
        initializeArray(numbers.length);
        operationCount += 1; // one method call

        // Find the largest number in the unsorted number array to determine the maximum digit length needed for sorting
        int largestValue = findLargestValue(numbers);
        operationCount += 2; // one method call, one assignment

        // Calculate how many digit places (maxDigitLength) the sorting needs to process
        calculateMaxDigitLength(largestValue);
        operationCount += 1; // one method call

        // Loop through each digit place (1s, 10s, 100s, etc.) until no higher place exists
        operationCount += 1; // one assignment (placeValue = 1)
        for (int placeValue = 1; largestValue / placeValue > 0; placeValue *= 10) {
            /** 
             * one addition, one assignment (placeValue *= 10) 
             * one arithmetic operation (division /)
             * one comparison (largestValue / placeValue)
            */
            operationCount += 4;

            // perform sorting pass based on current placed value
            sortingPass(numbers, placeValue);
            operationCount += 1; // one method call

            // print the array state after sorted by this digit place
            System.out.println("=== After sorting on digit place " + placeValue + " ===");
            operationCount += 2;
            displayArray(placeValue);
        }
        // one extra comparison (largestValue / placeValue > 0), one arithmetic operation (division /)
        operationCount += 2;
    }

    /**
     * Methods to determine the length of largest digit given the largest value in the input numbers array
     * (e.g.: 3141 digit length is 4)
     * @param largestValue the largest value in the input numbers array
     */
    private static void calculateMaxDigitLength(int largestValue) {
        if (largestValue == 0) {
            maxDigitLength = 1;
        } else {
            maxDigitLength = (int) Math.log10(Math.abs(largestValue) + 1);
        }
    }

    public static void main(String[] args) {

        // number array to sort
        int[] numbers = {275, 87, 426, 61, 409, 170, 677, 8910, 2182, 45, 180, 222, 500, 720, 30, 90};

        // display the initial array of numbers
        System.out.println("=== Initial array ===");
        System.out.print("Initial array: ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println("\n");

        // pass the array to the Sorting algorithm methods
        Sort(numbers);

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
