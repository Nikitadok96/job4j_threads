package wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int total;

    public SimpleBlockingQueue(int total) {
        this.total = total;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= total) {
            this.wait();
        }
        queue.offer(value);
        this.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        T value;
        while ((value = queue.poll()) == null) {
            this.wait();
        }
        this.notifyAll();
        return value;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(2);
        Thread firstConsumer = new Thread(() -> {
            Integer value = null;
            try {
                System.out.println("First consumer poll T");
                value = simpleBlockingQueue.poll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.printf("First consumer get {%d}\n", value);
        });
        Thread secondConsumer = new Thread(() -> {
            Integer value = null;
            try {
                firstConsumer.join();
                System.out.println("Second consumer poll T");
                value = simpleBlockingQueue.poll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.printf("Second consumer get {%d}\n", value);
        });
        Thread producer = new Thread(() -> {
            System.out.println("Producer give first value");
            try {
                simpleBlockingQueue.offer(1);
                firstConsumer.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Producer give second value");
            try {
                simpleBlockingQueue.offer(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        });
        firstConsumer.start();
        secondConsumer.start();
        producer.start();
    }
}
