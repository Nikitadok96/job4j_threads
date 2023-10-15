package pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private static final int SIZE = Runtime.getRuntime().availableProcessors();
    ExecutorService pool = Executors.newFixedThreadPool(SIZE);

    public void emailTo(User user) {
        pool.submit(() -> {
            String subject = String.format("Notification %s to email %s", user.getUserName(), user.getEmail());
            String body = String.format("Add a new event to %s", user.getUserName());
            send(subject, body, user.getEmail());
        });
    }

    public void close() {
        pool.shutdown();
    }

    public void send(String subject, String body, String email) {

    }
}
