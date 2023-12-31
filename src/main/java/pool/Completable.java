package pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Completable {

    private static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("Вы: Я работаю");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    public static CompletableFuture<Void> goToTrash() {
        return CompletableFuture.runAsync(() -> {
            System.out.println("Сын: Мам/Пам, я пошел выносить мусор");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Сын: Мам/Пап, я вернулся!");
        });
    }

    public static void runAsyncExample() throws InterruptedException {
        CompletableFuture<Void> completableFuture = goToTrash();
        completableFuture.thenRun(() -> {
            int count = 0;
            while (count < 3) {
                System.out.println("Я Мою руки");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }
        });
        iWork();
    }

    public static CompletableFuture<String> buyProduct(String product) {
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Сын: Мам/Пам, я пошел в магазин");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Мам/Пап, я купил " + product);
                    return product;
                }
        );
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<String> bp = buyProduct("Молоко");
        bp.thenAccept(product -> System.out.println("Я убираю молоко"));
        runAsyncExample();
    }
}
