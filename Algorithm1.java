public class Algorithm1 {
    private static int counter =0;
    private static Integer [][] array1;
    private static Integer [][] array2;
    private static int [] countArray = new int[10];
    private static int maxDigit;

    public static void arrayInitialization (int n){
        array1 = new Integer [10][n];
        array2 = new Integer [10][n];
    }

    private static void countArrayReset () {
        for (int i = 0; i < 10; i++) {
            countArray[i] = 0;
        }
    }

    private static void resetArray(Integer[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = null; // Set each element to null
            }
        }
    }

    // Function to find the maximum number in the array
    public static int findMax(int[] array) {
        int max = array[0];
        counter+=2; // 1 assign, 1 lookup
        counter+=1; // 1 assign
        for (int i = 0; i < array.length; i++) {
            counter+=3; // 1 assign, 1 compare, 1 op
            counter+=2; // 1 lookup, 1 compare
            if (array[i] > max) {
                max = array[i];
                counter+=2; // 1 assign, 1 lookup
            }
        }
        counter+=1; // 1 return
        return max;
    }

    // The "countingSort()" function to sort the numbers based on the digit
    // The parameter array[] is the array to be sorted
    private static void sortingAlgorithm(int[] array, int exp) {
        int radix;
        if (exp == 1) {
            for (int i = 0; i < array.length; i++) {
                radix = (array[i] / exp) % 10;
                array1[radix][countArray[radix]] = array[i];
                countArray[radix]++;
            }
            countArrayReset();
        } else if (Math.log10(exp) % 2 == 1) {
            for (Integer[] row: array1) {
                for (Integer element: row){
                    if(element == null)
                        break;
                    radix = (element / exp) % 10;
                    array2[radix][countArray[radix]] = element;
                    countArray[radix]++;
                }
            }
            if (Math.log10(exp) != maxDigit -1)
                resetArray(array1);
            countArrayReset();
        } else {
            for (Integer [] row: array2) {
                for (Integer element: row) {
                    if (element == null)
                        break;
                    radix = (element / exp) % 10;
                    array1[radix][countArray[radix]] = element;
                    countArray[radix]++;
                }
            }
            if (Math.log10(exp) != maxDigit - 1)
                resetArray(array2);
            countArrayReset();
        }
    }

    public static void display(int exp) {
        if (Math.log10(exp) % 2 == 1) {
            for (int i = 0; i < array2.length; i++){
                System.out.print(i + ": ");
                for (Integer x: array2[i]) {
                    if (x == null)
                        break;
                    System.out.print(x +" ");
                }
                System.out.println();
            }
        } else {
            for (int i = 0; i < array1.length; i++){
                System.out.print(i + ": ");
                for (Integer x: array1[i]) {
                    if (x == null)
                        break;
                    System.out.print(x +" ");
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    public static void reorder(int maxNumbers) {
        if (maxDigit % 2 == 0) {
            for (Integer[] row : array2) {
                for (Integer x : row) {
                    if (x == null)
                        break;
                    System.out.print(x + " ");                 
                }
            }
        } else {
            for (Integer[] row : array1) {
                for (Integer x : row) {
                    if (x == null)
                        break;
                    System.out.print(x + " ");                
                }
            }
        }
    }

    public static void findMaxDigit(int maxNumbers) {
        if (maxNumbers == 0)
            maxDigit = 1; 
        else
            maxDigit = (int) Math.log10(Math.abs(maxNumbers)) + 1; 
    }

    // Main function to implement the algorithm
    public static void main(String[] args) {

        // Example of an array to be sorted
        int[] numbers = {275, 87, 426, 61, 409, 170, 677, 503, 1, 45, 180, 222, 500, 720, 30, 90};
        counter+=1; // 1 assign
        arrayInitialization(numbers.length);

        // Display the original array
        System.out.println("=== Original array ===");
        System.out.print("Original array: ");
        counter+=1; // 1 assign
        for (int i = 0; i < numbers.length; i++) {
            counter+=3; // 1 assign, 1 compare, 1 op
            System.out.print(numbers[i] + " ");
            counter+=2; // 1 lookup, 1 arithmetic
        }
        System.out.println("\n");

        // Find the maximum number to know the number of digits with the "findMax()" function
        int max = findMax(numbers);
        findMaxDigit(max);

        counter+=2; // 1 assign, 1 call

        // Call "countingSort()" function for each digit place
        counter+=1; // 1 assign
        for (int exp = 1; max / exp > 0; exp *= 10) {
            counter+=4; // 1 assign, 2 arithmetic, 1 compare
            sortingAlgorithm(numbers, exp);
            counter+=2; // 1 assign, 1 call

            System.out.println("=== After sorting on digit place " + exp + " ===");
            counter+=2; // 2 arithmetic
            display(exp);
            

            System.out.println();
        }

        // Display the sorted array in ascending order
        System.out.println("=== Final sorted array ===");
        System.out.print("Reordered array: ");
        reorder(max);
        System.out.println("\nTotal primitive operations: " + counter);
    }

}