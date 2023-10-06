package io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) {
        String output = "";
        synchronized (file) {
            try (InputStream i = new BufferedInputStream(new FileInputStream(file))) {
                int data;
                while ((data = i.read()) > 0) {
                    if (filter.test((char) data)) {
                        output += (char) data;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return output;
    }
}
