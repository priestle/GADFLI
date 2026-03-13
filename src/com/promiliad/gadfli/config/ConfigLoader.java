package com.promiliad.gadfli.config;

public final class ConfigLoader {

    private ConfigLoader() {}

    public static Config load(String path) {
        // TODO: real file parsing later
        return new Config(path);
    }
}
