import java.util.Arrays;

public class Main {
    
    public static void main(String[] args) {
        int[] numbers = {275, 87, 426, 61, 409, 170, 677, 503};
        
        System.out.println("Original array: " + Arrays.toString(numbers));
        
        // Find the maximum number to know the number of digits
        int max = Arrays.stream(numbers).max().getAsInt();
        
        // Do counting sort for every digit
        for (int exp = 1; max/exp > 0; exp *= 10) {
            numbers = countingSort(numbers, exp);
            System.out.println("After sorting on digit with place value " + exp + ": " + Arrays.toString(numbers));
        }
        
        System.out.println("Sorted array: " + Arrays.toString(numbers));
    }
    
    private static int[] countingSort(int[] array, int exp) {
        int[] output = new int[array.length];
        int[] count = new int[10];
        
        // Initialize count array
        Arrays.fill(count, 0);
        
        // Store count of occurrences in count[]
        for (int num : array) {
            int digit = (num / exp) % 10;
            count[digit]++;
        }
        
        // Change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        // Build the output array
        for (int i = array.length - 1; i >= 0; i--) {
            int digit = (array[i] / exp) % 10;
            output[count[digit] - 1] = array[i];
            count[digit]--;
        }
        
        return output;
    }
}