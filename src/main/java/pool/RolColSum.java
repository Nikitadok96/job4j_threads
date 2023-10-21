package pool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            Sums sums = new Sums();
            for (int j = 0; j < matrix[i].length; j++) {
                sums.rowSum += matrix[i][j];
            }
            for (int[] ints : matrix) {
                sums.colSum += ints[i];
            }
            rsl[i] = sums;
        }
        return rsl;
    }
    
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] rsl = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Integer>> row = new HashMap<>();
        Map<Integer, CompletableFuture<Integer>> column = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            Sums sums = new Sums();
            row.put(i, getRow(matrix, i, matrix[i].length));
            column.put(i, getColumn(matrix, i, matrix.length));
            rsl[i] = sums;
        }
        for (Integer key : row.keySet()) {
            rsl[key].setRowSum(row.get(key).get());
        }
        for (Integer key : column.keySet()) {
            rsl[key].setColSum(column.get(key).get());
        }
        return rsl;
    }

    private static CompletableFuture<Integer> getRow(int[][] matrix, int row, int length) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int i = 0; i < length; i++) {
                sum += matrix[row][i];
            }
            return sum;
        });
    }

    private static CompletableFuture<Integer> getColumn(int[][] matrix, int column, int length) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int i = 0; i < length; i++) {
                sum += matrix[i][column];
            }
            return sum;
        });
    }



    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][] {
                {1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2},
                {3, 3, 3, 3, 3},
                {4, 4, 4, 4, 4},
                {5, 5, 5, 5, 5}
        };
        Sums[] array = sum(matrix);
        Arrays.stream(array).forEach(s -> {
            System.out.println(s.rowSum + " " + s.colSum);
        });
        System.out.println("ASYNC");
        Sums[] asyncArray = asyncSum(matrix);
        Arrays.stream(asyncArray).forEach(s -> {
            System.out.println(s.rowSum + " " + s.colSum);
        });
    }
}
