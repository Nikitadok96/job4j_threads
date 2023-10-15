package pool;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T searchObject;

    public ParallelIndexSearch(T[] array, T searchObject) {
        this.array = array;
        this.searchObject = searchObject;
    }

    @Override
    protected Integer compute() {
        int rsl = -1;
        if (array.length <= 10) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(searchObject)) {
                    rsl = i;
                    break;
                }
            }
            return rsl;
        }
        int mid = array.length / 2;
        ParallelIndexSearch<T> leftArray =
                new ParallelIndexSearch<>(Arrays.copyOfRange(array, 0, mid), searchObject);
        ParallelIndexSearch<T> rightArray =
                new ParallelIndexSearch<>(Arrays.copyOfRange(array, mid, array.length), searchObject);
        leftArray.fork();
        rightArray.fork();
        int left = leftArray.join();
        int right = rightArray.join();
        return merge(left, right);
    }

    private int merge(int left, int right) {
        int length = array.length / 2;
        int rsl = 0;
        if (left > 0) {
            rsl += left;
        } else if (right > 0) {
            if (array.length >= 10) {
                rsl += length;
            }
            rsl += right;
        }
        return rsl;
    }
}
