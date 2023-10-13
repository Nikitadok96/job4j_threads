package cas;

import java.util.concurrent.atomic.AtomicInteger;

public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int temp;
        int rsl;
        do {
            temp = count.get();
            rsl = temp + 1;
        } while (!count.compareAndSet(temp, rsl));

    }

    public int get() {
        return count.get();
    }

    public static void main(String[] args) {
        CASCount casCount = new CASCount();
        for (int i = 0; i < 10; i++) {
            casCount.increment();
        }
    }
}
