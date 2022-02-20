package io.github.xdgs.xdgscommons.hooks.placeholderapi;

import org.bukkit.OfflinePlayer;

public interface PlaceholderMethod {
    String action(OfflinePlayer player, String params);
}
