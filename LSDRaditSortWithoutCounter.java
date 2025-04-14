// Import necessary libraries

import java.util.Arrays;

// The algorithm is based on the Least Significant Digit (LSD) Radix Sort method.
public class LSDRaditSortWithoutCounter {

    // Function to do counting sort based on the digit represented by exp (1s, 10s, 100s)
    // The parameter array[] is the array to be sorted
    // The parameter exp is 1, 10 and 100 for 1s, 10s and 100s place respectively
    private static int[] countingSort(int[] array, int exp) {

        int[] output = new int[array.length];

        // Initialize 2 count arrays with size 10 (0-9 digits)
        int[] countArr1 = new int[10];
        int[] countArr2 = new int[10];

        // Decide which array to use for counting
        int[] count;

        // First pass and third pass are passed to countArr1 and second pass is passed to countArr2
        if (exp == 1 || exp == 100) {
            Arrays.fill(countArr1, 0);
            count = countArr1;
        } else {
            Arrays.fill(countArr2, 0);
            count = countArr2;
        }

        // Store count of occurrences in count[]
        for (int num : array) {
            int digit = (num / exp) % 10;
            count[digit]++;
        }

        // Update count[i] so it contains the cumulative count of each digit
        // This can be used to place the numbers in the output array so that the order is maintained
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Build the output array
        // Start the for loop with the last element of array to maintain stability
        // If two numbers have the same digit, the one that appeared first in the original array will appear first in the sorted array
        for (int i = array.length - 1; i >= 0; i--) {
            int digit = (array[i] / exp) % 10;
            output[count[digit] - 1] = array[i];
            count[digit]--;
        }

        // Return the output array
        return output;
    }

    // Main function to implement radix sort
    // It uses "countingSort()" function to sort the numbers based on each digit
    public static void main(String[] args) {
        
        // Counter for the number of passes
        int counterPass = 1;

        // Example number to be sorted
        int[] numbers = {275, 87, 426, 61, 409, 170, 677, 503};

        // Display the original array
        System.out.println("Original array: " + Arrays.toString(numbers));

        // Find the maximum number to know the number of digits
        int max = Arrays.stream(numbers).max().getAsInt();

        // Call "countingSort" function for each digit place
        // Starting from the least significant digit to the most significant digit (1s, 10s and 100s as there are only 3-digit numbers in this example)
        for (int exp = 1; max / exp > 0; exp *= 10) {
            numbers = countingSort(numbers, exp);
            if (counterPass == 1)
                System.out.println("First Pass" + ": " + Arrays.toString(numbers)); 
            else if (counterPass == 2)
                System.out.println("Second Pass" + ": " + Arrays.toString(numbers));
            else 
                System.out.println("Third pass" + ": " + Arrays.toString(numbers));
            counterPass++;
        }

        // Display the sorted array in ascending order
        System.out.println("Reordered array: " + Arrays.toString(numbers));
    }
}
