package io.github.xdgs.xdgscommons.api.utils.configmanager;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    private YamlConfiguration config;
    private File file;

    public Config(String path) {
        File directory = new File(path.substring(0, path.lastIndexOf("/")));
        if (!directory.exists()) directory.mkdirs();

        this.file = new File(path);
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        loadConfiguration();
    }

    public void loadConfiguration() {
        this.config = YamlConfiguration.loadConfiguration(file);
    }
    public void loadFromString(String contents) {
        try {
            this.config.loadFromString(contents);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    public String saveToString() {
        return this.config.saveToString();
    }
    public void save() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public YamlConfiguration getConfig() {
        return config;
    }
    public void setFile(File file) {
        this.file = file;
    }
    public File getFile() {
        return file;
    }

    public void dispose() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.file = null;
        this.config = null;
    }

    public void defaultV(String id, Object value) {
        if (this.getConfig() == null) return;
        if (id == null) return;
        if (value == null) return;
        if (!config.contains(id)) {
            config.set(id, value);
        }
    }
}
