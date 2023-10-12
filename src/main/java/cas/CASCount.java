package cas;

import java.util.concurrent.atomic.AtomicInteger;

public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int temp = count.get();
        int rsl;
        do {
            rsl = temp++;
        } while (!count.compareAndSet(rsl, temp));

    }

    public int get() {
        return count.get();
    }
}
