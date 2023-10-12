package cas;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {
    @Test
    public void whenGetDefault() {
        CASCount casCount = new CASCount();
        assertThat(casCount.get()).isEqualTo(0);
    }

    @Test
    public void whenAdd10Numbers() {
        CASCount casCount = new CASCount();
        for (int i = 0; i < 10; i++) {
            casCount.increment();
        }
        assertThat(casCount.get()).isEqualTo(10);
    }

    @Test
    public void whenAdd50InTwoThreads() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread firstThread = new Thread(() -> {
            for (int i = 0; i < 25; i++) {
                casCount.increment();
            }
        });
        Thread secondThread = new Thread(() -> {
            for (int i = 0; i < 25; i++) {
                casCount.increment();
            }
        });
        firstThread.start();
        secondThread.start();
        firstThread.join();
        secondThread.join();
        assertThat(casCount.get()).isEqualTo(50);
    }
}