package io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    private synchronized String getContent(Predicate<Character> filter) {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) != -1) {
                if (filter.test((char) data)) {
                    stringBuilder.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    public String getAllContent() {
        return getContent(p -> true);
    }

    public String getContentWithoutUnicode() {
        return getContent(p -> p < 0x80);
    }
}
