package wait;

public class CountBarrier {
    private final Object monitor = this;
    private final int total;
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(3);
        Thread master = new Thread(() -> {
            System.out.println("Master is started");
            countBarrier.count();
        });
        Thread firstSlave = new Thread(() -> {
            System.out.println("First slave is started");
            countBarrier.count();
            countBarrier.await();
            System.out.println("First slave is done");
        });
        Thread secondSlave = new Thread(() -> {
            System.out.println("Second slave is started");
            countBarrier.count();
            countBarrier.await();
            System.out.println("Second slave is done");
        });
        master.start();
        Thread.sleep(2000);
        firstSlave.start();
        Thread.sleep(2000);
        secondSlave.start();
    }
}
