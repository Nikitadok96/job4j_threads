package concurrent;

public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();
        first.start();
        first.join();
        second.join();
        System.out.println("работа завершена");
    }
}
