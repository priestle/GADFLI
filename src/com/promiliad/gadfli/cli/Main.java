package com.promiliad.gadfli.cli;

import com.promiliad.gadfli.helpers.Log;
import com.promiliad.gadfli.config.Config;
import com.promiliad.gadfli.config.ConfigLoader;

import java.io.File;

public final class Main {

    private static final int CMD_INDEX    = 0;
    private static final int CONFIG_INDEX = 1;
    private static final int LOG_INDEX    = 2;

    private Main() {}

    public static void main(String[] args) {
        int exitCode = 1;

        try {
            exitCode = dispatch(args);
        }
        catch (Throwable t) {
            Log.error("Fatal error: " + t.getMessage());
        }

        finally {
            Log.close();   // safe even if never initialized
        }

        System.exit(exitCode);
    }

    private static int dispatch(String[] args) throws Exception {

        if (args == null || args.length == 0) {
            printHelp();
            return 1;
        }

        switch (args[CMD_INDEX].toLowerCase()) {

            case "run":
                return run(args);

            case "help":
            case "-h":
            case "--help":
                printHelp();
                return 0;

            default:
                System.err.println("Unknown command: " + args[CMD_INDEX]);
                printHelp();
                return 1;
        }
    }

    private static int run(String[] args) throws Exception {

        if (args.length < 3) {
            System.err.println("Usage: gadfli run <configFile> <logFile>");
            return 1;
        }

        String configFile = args[CONFIG_INDEX];
        String logFile    = args[LOG_INDEX];

        File file = new File(configFile);
        if (!file.exists()) {
            System.err.println("Config file not found: " + configFile);
            return 1;
        }

        Log.init(logFile);
        Log.info("Config file: " + configFile);

        Config  config = ConfigLoader.load(configFile);

        // TODO: start engine
        // TODO: score molecules

        Log.info("Run completed.");
        return 0;
    }

    private static void printHelp() {
        System.out.println("GADFLI");
        System.out.println("Usage:");
        System.out.println("  gadfli run <configFile> <logFile>");
        System.out.println("  gadfli help");
    }
}
