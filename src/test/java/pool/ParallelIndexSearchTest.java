package pool;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.ForkJoinPool;

import static org.assertj.core.api.Assertions.*;

class ParallelIndexSearchTest {

    @Test
    public void whenIndex12AndLengthMore10() {
        Integer[] array = new Integer[] {11, 3, 14, 15, 16, 17, 18, 19, 20, 11, 1, 10, 13, 4, 5, 6, 7, 8, 9, 12};
        ParallelIndexSearch<Integer> parallelIndexSearch = new ParallelIndexSearch<>(array, 13,
                0, array.length);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int rsl = forkJoinPool.invoke(parallelIndexSearch);
        int expected = 12;
        assertThat(rsl).isEqualTo(expected);
    }

    @Test
    public void whenIndex19AndLengthMore10() {
        Integer[] array = new Integer[] {11, 3, 14, 15, 16, 17, 18, 19, 20, 11, 1, 10, 12, 4, 5, 6, 7, 8, 9, 13};
        ParallelIndexSearch<Integer> parallelIndexSearch = new ParallelIndexSearch<>(array, 13,
                0, array.length);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int rsl = forkJoinPool.invoke(parallelIndexSearch);
        int expected = 19;
        assertThat(rsl).isEqualTo(expected);
    }


    @Test
    public void whenIndexIs1AndLengthLess10() {
        Integer[] array = new Integer[] {10, 13, 4, 5, 6, 7, 8, 9, 12};
        ParallelIndexSearch<Integer> parallelIndexSearch = new ParallelIndexSearch<>(array, 13,
                0, array.length);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int rsl = forkJoinPool.invoke(parallelIndexSearch);
        int expected = 1;
        assertThat(rsl).isEqualTo(expected);
    }

    @Test
    public void whenIndexIs1AndLengthMore10() {
        Integer[] array = new Integer[] {11, 13, 14, 15, 16, 17, 18, 19, 20, 11, 1, 10, 3, 4, 5, 6, 7, 8, 9, 12};
        ParallelIndexSearch<Integer> parallelIndexSearch = new ParallelIndexSearch<>(array, 13,
                0, array.length);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int rsl = forkJoinPool.invoke(parallelIndexSearch);
        int expected = 1;
        assertThat(rsl).isEqualTo(expected);
    }

    @Test
    public void whenSearchStringIndexIs3AndLengthLess10() {
        String[] strings = new String[] {"Name", "Nikita", "Oleg", "Semen", "Vlad"};
        ParallelIndexSearch<String> parallelIndexSearch = new ParallelIndexSearch<>(strings, "Semen",
                0, strings.length);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int rsl = forkJoinPool.invoke(parallelIndexSearch);
        int expected = 3;
        assertThat(rsl).isEqualTo(expected);
    }

    @Test
    public void whenSearchStringIndexIs10AndLengthMore10() {
        String[] strings = new String[] {"Name", "Nikita", "Oleg", "Dmitry", "Vlad", "Ivan", "Viktor",
        "Igor", "Stas", "Egor", "Semen"};
        ParallelIndexSearch<String> parallelIndexSearch = new ParallelIndexSearch<>(strings, "Semen",
                0, strings.length);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int rsl = forkJoinPool.invoke(parallelIndexSearch);
        int expected = 10;
        assertThat(rsl).isEqualTo(expected);
    }

    @Test
    public void whenIndexIs0() {
        Integer[] array = new Integer[] {10, 13, 4, 5, 6, 7, 8, 9, 12};
        ParallelIndexSearch<Integer> parallelIndexSearch = new ParallelIndexSearch<>(array, 10,
                0, array.length);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int rsl = forkJoinPool.invoke(parallelIndexSearch);
        int expected = 0;
        assertThat(rsl).isEqualTo(expected);
    }

    @Test
    public void whenNotFoundIndex() {
        Integer[] array = new Integer[] {10, 13, 4, 5, 6, 7, 8, 9, 12};
        ParallelIndexSearch<Integer> parallelIndexSearch = new ParallelIndexSearch<>(array, 22,
                0, array.length);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int rsl = forkJoinPool.invoke(parallelIndexSearch);
        int expected = -1;
        assertThat(rsl).isEqualTo(expected);
    }

    @Test
    public void whenIndexIs22AndLength40() {
        Integer[] array = new Integer[] {
                11, 3, 14, 15, 16, 17, 18, 19, 20, 11, 1, 10, 13, 4, 5, 6, 7, 8, 9, 12,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40
        };
        ParallelIndexSearch<Integer> parallelIndexSearch = new ParallelIndexSearch<>(array, 23,
                0, array.length);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int rsl = forkJoinPool.invoke(parallelIndexSearch);
        int expected = 22;
        assertThat(rsl).isEqualTo(expected);
    }

    @Test
    public void whenIndexIs2AndLength40() {
        Integer[] array = new Integer[] {
                11, 3, 14, 15, 16, 17, 18, 19, 20, 11, 1, 10, 13, 4, 5, 6, 7, 8, 9, 12,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40
        };
        ParallelIndexSearch<Integer> parallelIndexSearch = new ParallelIndexSearch<>(array, 14,
                0, array.length);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int rsl = forkJoinPool.invoke(parallelIndexSearch);
        int expected = 2;
        assertThat(rsl).isEqualTo(expected);
    }

    @Test
    public void whenIndexIs11AndLength40() {
        Integer[] array = new Integer[] {
                11, 3, 14, 15, 16, 17, 18, 19, 20, 11, 1, 10, 13, 4, 5, 6, 7, 8, 9, 12,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40
        };
        ParallelIndexSearch<Integer> parallelIndexSearch = new ParallelIndexSearch<>(array, 10,
                0, array.length);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int rsl = forkJoinPool.invoke(parallelIndexSearch);
        int expected = 11;
        assertThat(rsl).isEqualTo(expected);
    }
}