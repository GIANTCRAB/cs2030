package lab3;

import java.util.stream.IntStream;

public class ArrayUtils {
    public static int[] reverseIntArray(int[] intArray) {
        return IntStream.rangeClosed(1, intArray.length).map(i -> intArray[intArray.length - i]).toArray();
    }

    public static int[][] deepCopy(int[][] arrayToCopy) {
        final int ROWS = arrayToCopy.length;
        final int COLS = arrayToCopy[0].length;
        final int[][] intClone = new int[ROWS][COLS];
        for (int row = 0; row < ROWS; row++) {
            System.arraycopy(arrayToCopy[row], 0, intClone[row], 0, COLS);
        }

        return intClone;
    }
}
