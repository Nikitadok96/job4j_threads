package concurrent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.regex.Pattern;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File("tmp.xml");
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                out.write(dataBuffer, 0, bytesRead);
                double nanoTime = System.nanoTime() - downloadAt;
                System.out.println("Time: " + nanoTime);
                System.out.println("Read 512 bytes : " + nanoTime + " nano.");
                int currentTime = (int) ((512.0 / nanoTime) * 1000);
                if (currentTime * 1000 > speed) {
                    Thread.sleep(currentTime);
                }
            }
            System.out.println(Files.size(file.toPath()) + " bytes");
        }  catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }

    private static void validate(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Arguments not passed to program");
        }
        if (!Pattern.compile("^https://\\w+|.xml$").matcher(args[0]).find()) {
            throw new IllegalArgumentException(String.format(
                    "Error: This argument '%s' is not a link", args[0]
            ));
        }
        if (Integer.parseInt(args[1]) < 0) {
            throw new IllegalArgumentException("The speed must be greater than zero");
        }
    }
}
