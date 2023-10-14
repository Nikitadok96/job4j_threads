package pool;

import wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final int countAvailableProcessors = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks =
            new SimpleBlockingQueue<>(countAvailableProcessors);

    public ThreadPool() {
        for (int i = 0; i < countAvailableProcessors; i++) {
            Thread thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            thread.start();
            threads.add(thread);
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        System.out.println("Start");
        threadPool.work(() -> System.out.println("Hello 1"));
        threadPool.work(() -> System.out.println("Hello 2"));
        threadPool.work(() -> System.out.println("Hello 3"));
        threadPool.work(() -> System.out.println("Hello 4"));
        threadPool.work(() -> System.out.println("Hello 5"));
        threadPool.work(() -> System.out.println("Hello 6"));
        threadPool.work(() -> System.out.println("Hello 7"));
        threadPool.work(() -> System.out.println("Hello 8"));
        threadPool.work(() -> System.out.println("Hello 9"));
        threadPool.work(() -> System.out.println("Hello 10"));
        threadPool.work(() -> System.out.println("Hello 11"));
        threadPool.work(() -> System.out.println("Hello 12"));
        threadPool.work(() -> System.out.println("Hello 13"));
        threadPool.work(() -> System.out.println("Hello 14"));
        threadPool.work(() -> System.out.println("Hello 15"));
        threadPool.work(() -> System.out.println("Hello 16"));
        threadPool.work(() -> System.out.println("Hello 17"));
        threadPool.work(() -> System.out.println("Hello 18"));
        threadPool.work(() -> System.out.println("Hello 19"));
        threadPool.work(() -> System.out.println("Hello 20"));
        System.out.println("Stop");
        threadPool.shutdown();
    }
}
