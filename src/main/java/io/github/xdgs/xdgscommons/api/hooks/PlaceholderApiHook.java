package io.github.xdgs.xdgscommons.api.hooks;

import io.github.xdgs.xdgscommons.api.hooks.placeholderapi.PlaceholderMethod;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class PlaceholderApiHook extends PlaceholderExpansion implements Hook {
    private final JavaPlugin plugin;
    private final HashMap<String, PlaceholderMethod> placeholders = new HashMap<>();

    public PlaceholderApiHook(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        for (String key: placeholders.keySet()) {
            if (params.equalsIgnoreCase(key)) {
                return placeholders.get(key).action(player, params);
            }
        }
        return null;
    }

    @Override
    public void initHook(Object... args) {
        this.register();
    }

    public String placeholders(OfflinePlayer player, String text) {
        return PlaceholderAPI.setPlaceholders(player, text);
    }

    public void add(String key, PlaceholderMethod method) {
        placeholders.put(key, method);
    }

    public PlaceholderMethod remove(String key) {
        return placeholders.remove(key);
    }

    @Override
    public String getRequiredPlugin() {
        return plugin.getName();
    }

    @Override
    public boolean canRegister() {
        return Bukkit.getPluginManager().getPlugin(getRequiredPlugin()) != null;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return plugin.getName();
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().get(0);
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }
}
