package wait;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    public void whenAdd1AndGet() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(1);
        Thread producer = new Thread(() -> simpleBlockingQueue.offer(1));
        producer.start();
        producer.join();
        Thread consumer = new Thread(() -> {
            try {
                 assertThat(simpleBlockingQueue.poll()).isEqualTo(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        });
        consumer.start();
    }

    @Test
    public void whenConsistent()  {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(2);
        Thread firstConsumer = new Thread(() -> {
            try {
                assertThat(simpleBlockingQueue.poll()).isEqualTo(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread secondConsumer = new Thread(() -> {
            try {
                firstConsumer.join();
                assertThat(simpleBlockingQueue.poll()).isEqualTo(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread producer = new Thread(() -> {
            try {
                simpleBlockingQueue.offer(1);
                firstConsumer.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            simpleBlockingQueue.offer(2);
        });
        firstConsumer.start();
        secondConsumer.start();
        producer.start();
    }
}