package io.github.xdgs.xdgscommons.api.hooks;

import io.github.xdgs.xdgscommons.api.utils.Hook;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class VaultHook implements Hook {
    private JavaPlugin plugin;
    private Economy econ = null;
    private Permission perms = null;
    private Chat chat = null;

    public VaultHook(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init(Object... args) {
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

    @Override
    public void dispose() {
        plugin = null;
        econ = null;
        perms = null;
        chat = null;
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

    public Economy getEcon() {
        return econ;
    }

    public Permission getPerms() {
        return perms;
    }

    public Chat getChat() {
        return chat;
    }
}
