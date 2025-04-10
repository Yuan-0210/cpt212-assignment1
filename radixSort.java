import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class radixSort {

    public static void main(String[] args) {
        // Input: array of strings representing numbers with equal length (padded with leading zeros if needed)
        // Example input: "275,087,426,061,409,170,677,503"
        String[] inputArray = {"275", "087", "426", "061", "409", "170", "677", "503"};
        List<String> numbers = new ArrayList<>(Arrays.asList(inputArray));
        int numDigits = numbers.get(0).length(); // Get the number of digits in the numbers (assumed equal length)

        // Initialization: Create two bucket arrays, each with 10 buckets for digits 0-9
        List<List<String>> array1 = new ArrayList<>(10);
        List<List<String>> array2 = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            array1.add(new ArrayList<>()); // For storing numbers in each digit bucket
            array2.add(new ArrayList<>());
        }

        // Iterate over each digit from the least significant (rightmost) to the most significant (leftmost)
        for (int digitIndex = 0; digitIndex < numDigits; digitIndex++) {
            List<List<String>> source; // Source buckets for the current pass
            List<List<String>> dest;   // Destination buckets for the current pass

            // Set source and destination arrays depending on the current digit index
            if (digitIndex == 0) {
                // First pass: source is the input list, destination is array1
                source = null; // Not needed for the first pass
                dest = array1;
            } else if (digitIndex % 2 == 1) {
                // Odd index pass: array1 → array2
                source = array1;
                dest = array2;
            } else {
                // Even index pass (after first): array2 → array1
                source = array2;
                dest = array1;
            }

            // Clear the destination buckets to prepare for new distribution
            for (int i = 0; i < 10; i++) {
                dest.get(i).clear();
            }

            // Distribute numbers into appropriate buckets based on the current digit
            if (digitIndex == 0) {
                // First pass: use input list directly
                for (String number : numbers) {
                    // Get the digit at the current position (from right to left)
                    int currentDigit = Character.getNumericValue(number.charAt(numDigits - 1 - digitIndex));
                    dest.get(currentDigit).add(number); // Add to the corresponding bucket
                }
            } else {
                // Subsequent passes: read from source buckets and distribute into dest buckets
                for (int bucket = 0; bucket < 10; bucket++) {
                    for (String number : source.get(bucket)) {
                        int currentDigit = Character.getNumericValue(number.charAt(numDigits - 1 - digitIndex));
                        dest.get(currentDigit).add(number);
                    }
                }
            }
        }

        // Collect sorted numbers from the final array after all digit passes
        List<String> sorted = new ArrayList<>();
        // Choose the final array based on the number of passes (even → array2, odd → array1)
        List<List<String>> finalArray = (numDigits % 2 == 0) ? array2 : array1;
        for (int bucket = 0; bucket < 10; bucket++) {
            sorted.addAll(finalArray.get(bucket)); // Flatten the bucketed list into a single sorted list
        }

        // Output the final sorted list of numbers
        System.out.println(String.join(" ", sorted));
    }
}

