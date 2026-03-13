package com.promiliad.gadfli.helpers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public final class Log {

    private static PrintWriter out;

    private Log() {}

    public static synchronized void init(String logFilePath) throws IOException {
        if (out != null) {
            close();
        }

        out = new PrintWriter(new FileWriter(logFilePath, false), true);
        info("==== GADFLI RUN LOG ====");
    }

    public static void info(String message) {
        write("INFO", message);
    }

    public static void warn(String message) {
        write("WARN", message);
    }

    public static void error(String message) {
        write("ERROR", message);
    }

    private static synchronized void write(String level, String message) {
        String line = LocalDateTime.now()
                + " [" + level + "] "
                + message;

        System.out.println(line);

        if (out != null) {
            out.println(line);
        }
    }

    public static synchronized void close() {
        if (out != null) {
            out.flush();
            out.close();
            out = null;
        }
    }
}
