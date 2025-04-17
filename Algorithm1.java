public class Algorithm1 {

    // Function to fill the count array with zeros
    public static int[] resetArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
        return arr;
    }

    // Function to find the maximum number in the array
    public static int findMax(int[] array) {
        int max = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    // The "countingSort()" function to sort the numbers based on the digit
    // The parameter array[] is the array to be sorted
    private static int[] countingSort(int[] array, int exp) {

        // The variable to store the result of sorted array
        int[] output = new int[array.length];

        // Initialize 2 count arrays with size 10 (0-9 digits)
        int[] countArr1 = new int[10];
        int[] countArr2 = new int[10];
        int[] count;

        // Decide which array to use for counting
        int temp = exp;
        int zeroCount = 0;

        // Count the number of zeros in exp
        while (temp > 0) {
            if (temp % 10 == 0) {
                zeroCount++;
            }
            temp /= 10;
        }

        // Use countArr1 if zero count is even, else use countArr2
        // So, first pass and third pass are passed to countArr1 and second pass is passed to countArr2
        if (zeroCount % 2 == 0) {
            countArr1 = resetArray(countArr1);
            count = countArr1;
        } else {
            countArr2 = resetArray(countArr2);
            count = countArr2;
        }

        // Store count of occurrences in count[]
        /*
         * For example, if the input array is {275, 87, 426, 61, 409, 170, 677, 503}
         * and we are sorting based on the 1s place (exp = 1), the count array will be: 
         * count[0] = 1, count[1] = 1, count[2] = 0, count[3] = 1, count[4] = 0,
         * count[5] = 1, count[6] = 1, count[7] = 2, count[8] = 0, count[9] = 1
         */
        for (int i = 0; i < array.length; i++) {
            int num = array[i];
            int digit = (num / exp) % 10;
            count[digit]++;
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
            count[i] += count[i - 1];
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
            int digit = (array[i] / exp) % 10;
            output[count[digit] - 1] = array[i];
            count[digit]--;
        }

        // Return the output array
        return output;
    }

    // Main function to implement the algorithm
    public static void main(String[] args) {

        // Example of an array to be sorted
        int[] numbers = {275, 87, 426, 61, 409, 170, 677, 503 };

        // Display the original array
        System.out.println("=== Original array ===");
        System.out.print("Original array: ");
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + " ");
        }
        System.out.println("\n");

        // Find the maximum number to know the number of digits with the "findMax()" function
        int max = findMax(numbers);

        // Call "countingSort()" function for each digit place
        for (int exp = 1; max / exp > 0; exp *= 10) {
            numbers = countingSort(numbers, exp);

            System.out.println("=== After sorting on digit place " + exp + " ===");

            // Create buckets for digits 0–9
            String[] buckets = new String[10];
            for (int i = 0; i < 10; i++) {
                buckets[i] = i + ": ";
            }

            // Assign numbers to corresponding digit buckets
            for (int i = 0; i < numbers.length; i++) {
                int digit = (numbers[i] / exp) % 10;
                buckets[digit] += numbers[i] + " ";
            }

            // Print each bucket
            for (int i = 0; i < buckets.length; i++) {
                System.out.println(buckets[i]);
            }

            System.out.println();
        }

        // Display the sorted array in ascending order
        System.out.println("=== Final sorted array ===");
        System.out.print("Reordered array: ");
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + " ");
        }
    }

}
