package io.github.xdgs.xdgscommons.api.hooks;

import org.bukkit.plugin.java.JavaPlugin;

// not finished
public class SkriptHook implements Hook {
    private final JavaPlugin plugin;

    public SkriptHook(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void initHook(Object... args) {

    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
