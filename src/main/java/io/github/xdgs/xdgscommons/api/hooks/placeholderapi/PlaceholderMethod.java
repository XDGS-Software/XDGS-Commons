package io.github.xdgs.xdgscommons.api.hooks.placeholderapi;

import org.bukkit.OfflinePlayer;

public interface PlaceholderMethod {
    String action(OfflinePlayer player, String params);
}
