public class Algorithm1 {
    private static int operationCounts = 0;
    private static int[] array1;
    private static int[] array2;
    private static int[] digitCounts = new int[10];
    private static int[] cumulativeCounts = new int[10];
    private static String[] displayString = new String[10];

    // Function to fill the count array with zeros
    public static int[] resetArray(int[] arr) {
        operationCounts+=1; // 1 assign
        for (int i = 0; i < arr.length; i++) {
            operationCounts+=3; // 1 assign, 1 compare, 1 op
            arr[i] = 0;
            operationCounts+=2; // 1 lookup, 1 assign
        }
        operationCounts+=1; // 1 return
        return arr;
    }

    // Function to find the maximum number in the array
    public static int findLargestDigit(int[] array) {
        int largest = array[0];
        operationCounts+=2; // 1 assign, 1 lookup
        operationCounts+=1; // 1 assign
        for (int i = 0; i < array.length; i++) {
            operationCounts+=3; // 1 assign, 1 compare, 1 op
            operationCounts+=2; // 1 lookup, 1 compare
            if (array[i] > largest) {
                largest = array[i];
                operationCounts+=2; // 1 assign, 1 lookup
            }
        }
        operationCounts+=1; // 1 return
        return largest;
    }

    // Main sorting function
    private static void Sort(int[] numbers) {
        array1 = new int[numbers.length];
        array2 = new int[numbers.length];
        // Find the maximum number to know the number of digits with the "findLargestDigit()" function
        int largest = findLargestDigit(numbers);

        for (int placeValue = 1; largest / placeValue > 0; placeValue*=10) {
            sortingPass(numbers, placeValue);
            System.out.println("=== After sorting on digit place " + placeValue + " ===");
            displayDigit(placeValue);
        }
    }

    private static void displayDigit(int placeValue) {
        int [] activeArray;

        if (Math.log10(placeValue) % 2 == 1) {
            activeArray = array2;
        } else {
            activeArray = array1;
        }

        for (int i = 0; i < 10; i++) {
            operationCounts+=3; // 1 assign, 1 compare, 1 op
            displayString[i] = i + ": ";
            operationCounts+=3; // 1 lookup, 1 assign, 1 arithmetic
        }
           
        // Assign numbers to corresponding digit buckets
        operationCounts+=1; // 1 assign
        for (int i = 0; i < activeArray.length; i++) {
            operationCounts+=3; // 1 assign, 1 compare, 1 op
            int digit = (activeArray[i] / placeValue) % 10;
            operationCounts+=4; // 1 assign, 2 arithmetic, 1 lookup
            displayString[digit] += activeArray[i] + " ";
            operationCounts+=4; // 2 lookup, 2 arithmetic
        }
           
        // Print each bucket
        operationCounts+=1; // 1 assign
        for (int i = 0; i < 10; i++) {
            operationCounts+=3; // 1 assign, 1 compare, 1 op
            System.out.println(displayString[i]);
            operationCounts+=1; // 1 lookup
        }
        System.out.println();
    }
    

    // Store count of occurrences in digitCount
    /*
    * For example, if the input array is {275, 87, 426, 61, 409, 170, 677, 503}
    * and we are sorting based on the 1s place (placeValue = 1), the count array will be: 
    * digitCounts[0] = 1, digitCounts[1] = 1, digitCounts[2] = 0, digitCounts[3] = 1, digitCoutns[4] = 0,
    * digitCounts[5] = 1, digitCounts[6] = 1, digitCounts[7] = 2, digitCounts[8] = 0, digitCounts[9] = 1
    */
    private static void countDigit(int[] numbers, int placeValue) {
        for (int i = 0; i < numbers.length; i++){
            int digit = (numbers[i] / placeValue) % 10;
            digitCounts[digit]++;
        }
    }

    // Update cumulativeCounts array so it contains the cumulative count of each digit based on the digitCounts[]
    // This can be used to place the numbers in the to the subsequent array so that the order is maintained
    /*
    * Based on the previous example, the updated digitCounts array will be:
    * digit: 0,1,2,3,4,5,6,7,8,9
    * count: 1,2,2,3,3,4,5,7,7,8
    * Therefore, cumulativeCounts[0] = 1, cumulativeCounts[1] = 2, cumulativeCounts[2] = 2, cumulatvieCounts[3] = 3, cumulativeCounts[4] = 3,
    * cumulativeCounts[5] = 4, cumulativeCounts[6] = 5, cumulativeCounts[7] = 7, cumulativeCounts[8] = 7, cumulativeCounts[9] = 8
    */
    private static void countCumulative() {
        cumulativeCounts[0] = digitCounts[0];
        for (int i = 1; i < 10; i++) {
            cumulativeCounts[i] = cumulativeCounts[i-1] + digitCounts[i];
        }
    }

    // The "sort()" function to sort the numbers based on the digit
    // The parameter array[] is the array to be sorted
    private static void sortingPass(int[] numbers, int placeValue) {

        resetArray(digitCounts);
        resetArray(cumulativeCounts);
        if (placeValue == 1) {
            countDigit(numbers, placeValue);
            countCumulative();

            // Build the output array
            // Start the for loop with the last element of array to maintain stability
            // If two numbers have the same digit, the one that appeared first in the original array will appear first in the sorted array
            /*
            * For example, the input array is {112, 121, 122}
            * When sorting by the least significant digit (1s place), the sorted order based on the digit is:
            * - 112 has 2 in the 1s place
            * - 121 has 1 in the 1s place
            * - 122 has 2 in the 1s place
            *
            * In the unstable sort (start from the first element of the array), 121 would come before 122, and the final sorted array would look like:
            * {121, 122, 112}, which doesn't preserve the original order of equal elements.
            *
            * However, in the stable sort (start from the last element of the array), since 112 appeared first in the original array, it will appear first in the sorted array.
            * The final sorted array will be:
            * {112, 121, 122}
            * This preserves the original relative order of equal elements.
            */
            for (int i = numbers.length -1; i >= 0; i--) {
                int digit = (numbers[i] / placeValue) % 10;
                array1[cumulativeCounts[digit] - 1] = numbers[i];
                cumulativeCounts[digit]--;
            }
        } 

        else if (Math.log10(placeValue) % 2 == 1) {
            countDigit(array1, placeValue);
            countCumulative();
            for (int i = array1.length -1; i >= 0; i--) {
                int digit = (array1[i] / placeValue) % 10;
                array2[cumulativeCounts[digit] -1] = array1[i];
                cumulativeCounts[digit] --;
            }
            resetArray(array1);
        }

        else {
            countDigit(array2, placeValue);
            countCumulative();
            for (int i = array2.length -1; i >= 0; i--) {
                int digit = (array2[i] / placeValue) % 10;
                array1[cumulativeCounts[digit] -1] = array2[i];
                cumulativeCounts[digit]--;
            }
            resetArray(array2);
        }
    }

    public static void printSortedArray(int largestValue) {
        int maxDigitLength = (largestValue == 0) ? 1 : (int) Math.log10(Math.abs(largestValue)) +1;
        int [] finalArray = (maxDigitLength % 2 == 0) ? array2 : array1;

        for (int i = 0; i < finalArray.length; i++) {
            System.out.print(finalArray[i] +" ");
        }
    }

    // Main function to implement the algorithm
    public static void main(String[] args) {

        // Example of an array to be sorted
        int[] numbers = {275, 87, 426, 61, 409, 170, 677, 503, 1, 45, 180, 222, 500, 720, 30, 90};
        operationCounts+=1; // 1 assign

        // Display the original array
        System.out.println("=== Original array ===");
        System.out.print("Original array: ");
        operationCounts+=1; // 1 assign
        for (int i = 0; i < numbers.length; i++) {
            operationCounts+=3; // 1 assign, 1 compare, 1 op
            System.out.print(numbers[i] + " ");
            operationCounts+=2; // 1 lookup, 1 arithmetic
        }
        System.out.println("\n");
        Sort(numbers);
        int LargestDigit = findLargestDigit(numbers);

        // Display the sorted array in ascending order
        System.out.println("=== Final sorted array ===");
        System.out.print("Reordered array: ");
        operationCounts+=1; // 1 assign
        printSortedArray(LargestDigit);
        System.out.println("\nTotal primitive operations: " + operationCounts);
    }

}