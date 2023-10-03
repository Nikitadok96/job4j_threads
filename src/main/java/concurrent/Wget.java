package concurrent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Time;
import java.util.regex.Pattern;

public class Wget implements Runnable {
    private final String url;
    private final String fileTemp;
    private final int speed;

    public Wget(String url, String fileTemp, int speed) {
        this.url = url;
        this.fileTemp = fileTemp;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File(fileTemp);
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512];
            int bytesRead;
            var downloadAt = System.nanoTime();
            int size = 0;
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                size += dataBuffer.length;
                if (size >= speed) {
                    var nanoTime = System.nanoTime() - downloadAt;
                    System.out.println("Read: " + size + " byte : " + nanoTime + " nanoSec");
                    long byteSec = (long) (((double) size / nanoTime) * 1000000);
                    System.out.println("ByteSec: " + byteSec);
                    if (byteSec  >= speed) {
                        long needSleep = byteSec / 1000;
                        System.out.println("Need sleep: " + needSleep);
                        Thread.sleep(needSleep);
                    }
                    size = 0;
                    downloadAt = System.nanoTime();
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
        Thread wget = new Thread(new Wget(url, args[2], speed));
        wget.start();
        wget.join();
    }

    private static void validate(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Arguments not passed to program");
        }
        if (!Pattern.compile("^https://\\w+|.\\w+$").matcher(args[0]).find()) {
            throw new IllegalArgumentException(String.format(
                    "Error: This argument '%s' is not a link", args[0]
            ));
        }
        if (Integer.parseInt(args[1]) < 0) {
            throw new IllegalArgumentException("The speed must be greater than zero");
        }
        if (!Pattern.compile("\\w.\\w+$").matcher(args[2]).find()) {
            throw new IllegalArgumentException("No data storage file specified");
        }
    }
}
