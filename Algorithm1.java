
public class Algorithm1 {

    private static int opCount = 0;
    private static Integer[][] array1;
    private static Integer[][] array2;
    private static final int[] countArray = new int[10];
    private static int[] reorderedArray;
    private static int maxDigit;

    public static void arrayInitialization(int n) {
        array1 = new Integer[10][n];
        array2 = new Integer[10][n];
        reorderedArray = new int[n];
    }

    private static void countArrayReset() {
        for (int i = 0; i < 10; i++) {
            countArray[i] = 0;
        }
    }

    private static void resetArray(Integer[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = null;
            }
        }
    }

    public static int findMax(int[] array) {
        int max = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    private static void sortingAlgorithm(int[] array, int exp) {
        int radix;
        if (exp == 1) {
            opCount += 1; // 1 comparison
            opCount += 1; // 1 assignment (for loop)
            for (int i = 0; i < array.length; i++) {
                opCount += 3; // 1 comparison, 1 post-increment (for loop)
                radix = (array[i] / exp) % 10;
                opCount += 4; // 1 assignment, 1 array lookup, 1 division, 1 modulus
                array1[radix][countArray[radix]] = array[i];
                opCount += 4; // 3 array lookups, 1 assignment
                countArray[radix]++;
                opCount += 3; // 1 array lookup, 1 post-increment
            }
            countArrayReset();
            opCount += 1; // 1 call a function
        } else if (Math.log10(exp) % 2 == 1) {
            opCount += 3; // 1 call Math function, 1 modulus, 1 comparison
            opCount += 1; // 1 assignment (outer for loop)
            for (int i = 0; i < array1.length; i++) {
                opCount += 3; // 1 comparison, 1 post-increment (outer for loop) 
                opCount += 1; // 1 assignment (inner for loop)
                for (int j = 0; j < array1[i].length; j++) {
                    opCount += 3; // 1 comparison, 1 post-increment (inner for loop)
                    if (array1[i][j] == null) {
                        opCount += 2; // 1 array lookup, 1 comparison
                        break;
                    }
                    radix = (array1[i][j] / exp) % 10;
                    opCount += 4; // 1 assignment, 1 array lookup, 1 division, 1 modulus
                    array2[radix][countArray[radix]] = array1[i][j];
                    opCount += 4; // 3 array lookups, 1 assignment
                    countArray[radix]++;
                    opCount += 3; // 1 array lookup, 1 post-increment
                }
            }
            if (Math.log10(exp) != maxDigit - 1) {
                opCount +=2; // 1 call a function, 1 comparison
                resetArray(array1);
                opCount += 1; // 1 call a function
            }
            countArrayReset();
            opCount += 1; // 1 call a function
        } else {
            opCount += 1; // 1 assignment (outer for loop)
            for (int i = 0; i < array2.length; i++) {
                opCount += 3; // 1 comparison, 1 post-increment (outer for loop)
                opCount += 1; // 1 assignment (inner for loop)
                for (int j = 0; j < array2[i].length; j++) {
                    opCount += 3; // 1 comparison, 1 post-increment (inner for loop)
                    if (array2[i][j] == null) {
                        opCount += 2; // 1 array lookup, 1 comparison
                        break;
                    }
                    radix = (array2[i][j] / exp) % 10;
                    opCount += 4; // 1 assignment, 1 array lookup, 1 division, 1 modulus
                    array1[radix][countArray[radix]] = array2[i][j];
                    opCount += 4; // 3 array lookups, 1 assignment
                    countArray[radix]++;
                    opCount += 3; // 1 array lookup, 1 post-increment
                }
            }
            if (Math.log10(exp) != maxDigit - 1) {
                opCount += 2; // 1 call a function, 1 comparison, 1 minus
                resetArray(array2);
                opCount += 1; // 1 call a function
            }
            countArrayReset();
            opCount += 1; // 1 call a function
        }
    }

    public static void display(int exp) {
        if (Math.log10(exp) % 2 == 1) {
            for (int i = 0; i < array2.length; i++) {
                System.out.print(i + ": ");
                for (Integer x : array2[i]) {
                    if (x == null) {
                        break;
                    }
                    System.out.print(x + " ");
                }
                System.out.println();
            }
        } else {
            for (int i = 0; i < array1.length; i++) {
                System.out.print(i + ": ");
                for (Integer x : array1[i]) {
                    if (x == null) {
                        break;
                    }
                    System.out.print(x + " ");
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    public static void reorder(int maxNumbers) {
        int counter = 0;
        if (maxDigit % 2 == 0) {
            for (Integer[] row : array2) {
                for (Integer element : row) {
                    if (element == null) {
                        break;
                    }
                    reorderedArray[counter++] = element;
                }
            }
        } else {
            for (Integer[] row : array1) {
                for (Integer element : row) {
                    if (element == null) {
                        break;
                    }
                    reorderedArray[counter++] = element;
                }
            }
        }

        for (int i = 0; i < reorderedArray.length; i++) {
            System.out.print(reorderedArray[i] + " ");
        }

    }

    public static void findMaxDigit(int maxNumbers) {
        if (maxNumbers == 0) {
            maxDigit = 1;
        } else {
            maxDigit = (int) Math.log10(Math.abs(maxNumbers)) + 1;
        }
    }

    public static void main(String[] args) {

        int[] numbers = {275, 87, 426, 61, 409, 170, 677, 503, 1, 45, 180, 222, 500, 720, 30, 90};
        arrayInitialization(numbers.length);

        System.out.println("=== Original array ===");
        System.out.print("Original array: ");
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + " ");
        }
        System.out.println("\n");

        int max = findMax(numbers);
        findMaxDigit(max);

        for (int exp = 1; max / exp > 0; exp *= 10) {
            sortingAlgorithm(numbers, exp);

            System.out.println("=== After sorting on digit place " + exp + " ===");
            display(exp);

            System.out.println();
        }

        System.out.println("=== Final sorted array ===");
        System.out.print("Reordered array: ");
        reorder(max);
        System.out.println("\nTotal primitive operations: " + opCount);
    }

}
