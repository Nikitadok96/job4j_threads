package pool;

import java.util.concurrent.CompletableFuture;


public class RolColSum {

    private static void count(int[][] array, int index, Sums[] sums) {
        int sumRow = 0;
        int sumColumn = 0;
        for (int i = 0; i < array.length; i++) {
            sumRow += array[index][i];
            sumColumn += array[i][index];
        }
        sums[index] = new Sums(sumRow, sumColumn);
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            count(matrix, i, rsl);
        }
        return rsl;
    }
    
    public static Sums[] asyncSum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int index = i;
            CompletableFuture.runAsync(() -> count(matrix, index, rsl));
        }
        return rsl;
    }
}
