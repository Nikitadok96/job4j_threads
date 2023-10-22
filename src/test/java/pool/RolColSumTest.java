package pool;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    public void whenSyncTestRow() {
        int[][] matrix = new int[][] {
                {1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2},
                {3, 3, 3, 3, 3},
                {4, 4, 4, 4, 4},
                {5, 5, 5, 5, 5}
        };
        Sums[] array = RolColSum.sum(matrix);
        int[] expectedRow = new int[5];
        for (int i = 0; i < expectedRow.length; i++) {
            expectedRow[i] = array[i].getRowSum();
        }
        assertThat(expectedRow).containsSequence(5, 10, 15, 20, 25);
    }

    @Test
    public void whenSyncTestColumn() {
        int[][] matrix = new int[][] {
                {1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2},
                {3, 3, 3, 3, 3},
                {4, 4, 4, 4, 4},
                {5, 5, 5, 5, 5}
        };
        Sums[] array = RolColSum.sum(matrix);
        int[] expectedColumn = new int[5];
        for (int i = 0; i < expectedColumn.length; i++) {
            expectedColumn[i] = array[i].getColSum();
        }
        assertThat(expectedColumn).containsSequence(15, 15, 15, 15, 15);
    }


    @Test
    public void whenAsyncTest() {
        int[][] matrix = new int[][] {
                {1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2},
                {3, 3, 3, 3, 3},
                {4, 4, 4, 4, 4},
                {5, 5, 5, 5, 5}
        };
        Sums[] rsl = RolColSum.asyncSum(matrix);
        Sums[] expected = new Sums[] {
                new Sums(5, 15),
                new Sums(10, 15),
                new Sums(15, 15),
                new Sums(20, 15),
                new Sums(25, 15),
        };
        assertThat(rsl).isEqualTo(expected);
    }
}