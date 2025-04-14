// Import necessary libraries
import java.util.Arrays;

// The algorithm is based on the Least Significant Digit (LSD) Radix Sort method.
public class LSDRadixSort {

    /*
    *Counter for primitive operations
    *According to lecture notes, the primitive operations are:
    *1. Assignment
    *2. Comparison
    *3. Arithmetic operations (addition, subtraction, multiplication, division)
    *4. Calling a method
    *5. Return from a method
     */
    static int primitiveOps = 0;

    // Function to do counting sort based on the digit represented by exp (1s, 10s, 100s)
    // The parameter array[] is the array to be sorted
    // The parameter exp is 1, 10 and 100 for 1s, 10s and 100s place respectively
    private static int[] countingSort(int[] array, int exp) {
        primitiveOps++; // method call

        int[] output = new int[array.length];
        primitiveOps++; // assignment

        // Initialize 2 count arrays with size 10 (0-9 digits)
        int[] countArr1 = new int[10];
        primitiveOps++; // assignment

        int[] countArr2 = new int[10];
        primitiveOps++; // assignment

        // Decide which array to use for counting
        int[] count;
        primitiveOps++; // assignment

        // First pass and third pass are passed to countArr1 and second pass is passed to countArr2
        if (exp == 1 || exp == 100) {
            primitiveOps += 2; // two comparisons
            Arrays.fill(countArr1, 0);
            primitiveOps++; // method call
            count = countArr1;
            primitiveOps++; // assignment
        } else {
            Arrays.fill(countArr2, 0);
            primitiveOps++; // method call
            count = countArr2;
            primitiveOps++; // assignment
        }

        // Store count of occurrences in count[]
        /*
         * For example, if the input array is {275, 87, 426, 61, 409, 170, 677, 503}
         * and we are sorting based on the 1s place (exp = 1), the count array will be: 
         * count[0] = 1, count[1] = 1, count[2] = 0, count[3] = 1, count[4] = 0,
         * count[5] = 1, count[6] = 1, count[7] = 2, count[8] = 0, count[9] = 1
         */
        for (int num : array) {
            primitiveOps++; // assignment for the loop
            int digit = (num / exp) % 10;
            primitiveOps += 3; // division, modulo, assignment
            count[digit]++;
            primitiveOps += 2; // indexing + assignment
        }

        // Update count[i] so it contains the cumulative count of each digit
        // This can be used to place the numbers in the output array so that the order is maintained
        /*
         * Based on the previous example, the updated count array will be:
         * digit: 0,1,2,3,4,5,6,7,8,9
         * count: 1,2,2,3,3,4,5,7,7,8
         * Therefore, count[0] = 1, count[1] = 2, count[2] = 2, count[3] = 3, count[4] = 3,
         * count[5] = 4, count[6] = 5, count[7] = 7, count[8] = 7, count[9] = 8
         */
        for (int i = 1; i < 10; i++) {
            primitiveOps += 2; // assignment, comparison for the loop
            count[i] += count[i - 1];
            primitiveOps += 3; // substraction, addition, assignment
            primitiveOps++; // substraction for the loop
        }

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
        for (int i = array.length - 1; i >= 0; i--) {
            primitiveOps += 2; // assignment, subtraction, comparison for loop
            int digit = (array[i] / exp) % 10;
            primitiveOps += 3; // division, modulo, assignment
            output[count[digit] - 1] = array[i];
            primitiveOps += 2; // subtraction, assignment
            count[digit]--;
            primitiveOps++; // assignment
            primitiveOps++; // subtraction for the loop
        }

        // Display the reordered array after sorting on the current digit(1s, 10s or 100s)
        primitiveOps++; // return
        return output;
    }

    // Main function to implement radix sort
    // It uses "countingSort()" function to sort the numbers based on each digit
    public static void main(String[] args) {
        primitiveOps++; // method call

        // Example number to be sorted
        int[] numbers = {275, 87, 426, 61, 409, 170, 677, 503};
        primitiveOps++; // assignment

        // Display the original array
        System.out.println("Original array: " + Arrays.toString(numbers));

        // Find the maximum number to know the number of digits
        // In this example, the maximum number is 677
        int max = Arrays.stream(numbers).max().getAsInt();
        primitiveOps += 4; // method calls: stream(), max(), getAsInt(), assignment

        // Call "countingSort" function for each digit place
        // Starting from the least significant digit to the most significant digit(1s, 10s and 100s as there are only 3 digits numbers in this example)
        for (int exp = 1; max / exp > 0; exp *= 10) {
            primitiveOps += 3; // assignment, division (comparison) for loop
            numbers = countingSort(numbers, exp);
            primitiveOps += 2; // assignment, mehtod call
            System.out.println("After sorting on digit place " + exp + ": " + Arrays.toString(numbers));
            primitiveOps++; // multiplication
        }

        // Display the reordered array in ascending order
        System.out.println("Reordered array: " + Arrays.toString(numbers));

        // Display the total number of primitive operations
        System.out.println("Total primitive operations: " + primitiveOps);
    }
}
