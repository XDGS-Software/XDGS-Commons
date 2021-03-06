package io.github.xdgs.xdgscommons.api.utils.configmanager;

import java.util.HashMap;

public class ConfigManager {
    private HashMap<String, Config> configMap = new HashMap<>();

    public ConfigManager() {}

    public Config set(String id, Config config) {
        return this.configMap.put(id, config);
    }
    public Config remove(String id) {
        return this.configMap.remove(id);
    }
    public Config create(String id, String filePath) {
        Config config = new Config(filePath);
        set(id, config);
        return config;
    }
    public Config get(String id) {
        return configMap.get(id);
    }

    public void dispose() {
        for (Config config: configMap.values()) config.dispose();

        configMap.clear();
        configMap = null;
    }
}
