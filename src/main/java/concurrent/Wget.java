package concurrent;

public class Wget {
    public static void main(String[] args) {
        for (int i = 0; i <= 100; i++) {
            try {
                Thread.sleep(1000);
                System.out.print("\rLoading : " + i  + "%");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
