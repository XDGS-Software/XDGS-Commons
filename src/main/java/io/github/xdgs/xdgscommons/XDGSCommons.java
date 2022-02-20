package io.github.xdgs.xdgscommons;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class XDGSCommons extends JavaPlugin {
    private static XDGSCommons instance;

    private Logger logger;

    @Override
    public void onEnable() {
        double started = System.currentTimeMillis();

        instance = this;
        logger = getLogger();

        logger.info(getName() + " has loaded in " + (System.currentTimeMillis() - started) + "ms.");
    }

    @Override
    public void onDisable() {
        logger = null;
        instance = null;
    }

    public static XDGSCommons getInstance() {
        return instance;
    }
}
