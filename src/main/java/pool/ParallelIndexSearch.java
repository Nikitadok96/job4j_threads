package pool;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T searchObject;
    private final int from;
    private final int to;

    public ParallelIndexSearch(T[] array, T searchObject, int from, int to) {
        this.array = array;
        this.searchObject = searchObject;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return findCycle();
        }
        int mid = (from + to) / 2;
        ParallelIndexSearch<T> leftArray =
                new ParallelIndexSearch<>(array, searchObject, from, mid);
        ParallelIndexSearch<T> rightArray =
                new ParallelIndexSearch<>(array, searchObject, mid + 1, to);
        leftArray.fork();
        rightArray.fork();
        int left = leftArray.join();
        int right = rightArray.join();
        return Math.max(left, right);
    }

    private int findCycle() {
        int rsl = -1;
        for (int i = this.from; i < this.to; i++) {
            if (array[i].equals(searchObject)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    public static int findIndexInteger(Integer[] array, Integer object) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearch<>(array, object, 0, array.length));
    }
}
