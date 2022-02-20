package io.github.xdgs.xdgscommons.hooks;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class VaultHook implements Hook {
    private final JavaPlugin plugin;
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    public VaultHook(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void initHook(Object... args) {
        boolean enforceVault = false;
        if (args.length > 0 && args[0] instanceof Boolean) {
            enforceVault = (boolean)args[0];
        }

        if (!setupEconomy()) {
            if (enforceVault) {
                Bukkit.getLogger().severe(
                        String.format("[%s] - Disabled due to no Vault dependency found!",
                                plugin.getDescription().getName()));
                Bukkit.getServer().getPluginManager().disablePlugin(plugin);
            }
            return;
        }

        setupPermissions();
        setupChat();
    }

    private boolean setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) return false;
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = plugin.getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = plugin.getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public static Economy getEcon() {
        return econ;
    }

    public static Permission getPerms() {
        return perms;
    }

    public static Chat getChat() {
        return chat;
    }
}
