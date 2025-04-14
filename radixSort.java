public class radixSort {
    // Make counter static so that it is accessible to all static methods.
    static int counter = 0;

    public static void main(String[] args) {
        // We assume that the array contains positive integers with the same number of digits.
        int[] data = {275, 87, 426, 61, 409, 170, 677, 503}; 
        System.out.println("Initial Array: ");
        printArray(data);

        // Find the maximum value in the array to determine the number of digits (i.e., the number of passes)
        int max = getMax(data);

        // The sorting is performed digit by digit using countingSort.
        // passCount keeps track of which pass (iteration) is running.
        int place = 1; // Start with the least significant digit (ones place)
        int passCount = 0;
        while (max / place > 0) {
            passCount++;
            System.out.println("\nIteration " + passCount + " (Sorting by digit place " + place + "): ");
            // Sorting the array based on the current digit represented by 'place'
            countingSort(data, place);
            // Print the array after this iteration/pass
            printArray(data);
            // Move to next digit place (ones -> tens -> hundreds, etc.)
            place *= 10;
        }
        
        // After all passes, the array has been fully sorted. This is the final reorder step.
        System.out.println("\nFinal Sorted Array:");
        printArray(data);
        
        // Output the total counter.
        System.out.println("\nOperation Counter: " + counter);
    }

    // This function sorts the input array according to the digit represented by 'place'
    public static void countingSort(int[] array, int place) {
        int size = array.length;
        counter += 1; // 1 assign
        // array1 serves as the temporary array to store sorted results for the current pass.
        int[] array1 = new int[size];
        counter += 1; // 1 assign
        // array2 counts the occurrences of each digit (0-9).
        int[] array2 = new int[10];
        counter += 1; // 1 assign
        
        // Step 1: Count the occurrences of each digit at the current place.
        for (int i = 0; i < size; i++) {
            counter += 3; // 1 assign, 1 compare, 1 op (loop control)
            int digit = (array[i] / place) % 10;
            counter += 3; // 1 lookup, 2 arithmetic
            array2[digit]++;
            counter += 2; // 1 lookup, 1 op (increment)
        }
        
        // Step 2: Transform array2 to store cumulative counts.
        for (int i = 1; i < 10; i++) {
            counter += 3; // loop overhead: 1 assign, 1 compare, 1 op
            array2[i] += array2[i - 1];
            counter += 3; // 2 lookups and 1 assignment
        }
        
        // Step 3: Build array1, the output array, by placing elements at their correct positions.
        // Looping backwards ensures the algorithm remains stable.
        for (int i = size - 1; i >= 0; i--) {
            counter += 3; // loop overhead: 1 assign, 1 compare, 1 op
            int digit = (array[i] / place) % 10;
            counter += 3; // 1 lookup, 2 arithmetic
            array1[array2[digit] - 1] = array[i];
            counter += 3; // 2 lookups, 1 assignment
            array2[digit]--;
            counter += 2; // 1 lookup, 1 op (decrement)
        }
        
        // Step 4: Copy the sorted values from array1 back into the original array.
        for (int i = 0; i < size; i++) {
            counter += 3; // loop overhead: 1 assign, 1 compare, 1 op
            array[i] = array1[i];
            counter += 3; // 2 lookups, 1 assignment
        }
    }

    // Determines the maximum number in the array. This value is used to decide how many iterations are needed.
    public static int getMax(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }
    
    // Prints the contents of the array to the console.
    public static void printArray(int[] array) {
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}







