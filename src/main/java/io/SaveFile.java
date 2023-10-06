package io;

import java.io.*;

public class SaveFile {
    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        synchronized (file) {
            try (OutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
                for (int i = 0; i < content.length(); i += 1) {
                    o.write(content.charAt(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
