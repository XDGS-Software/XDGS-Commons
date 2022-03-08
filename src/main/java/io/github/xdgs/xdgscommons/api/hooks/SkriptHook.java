package io.github.xdgs.xdgscommons.api.hooks;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.lang.*;
import io.github.xdgs.xdgscommons.api.utils.Hook;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SkriptHook implements Hook {
    private JavaPlugin plugin;

    private Plugin SkriptPlugin;
    private SkriptAddon skriptAddon;
    private String versionRequired = "2.5";
    private boolean HasSkript;

    public SkriptHook(JavaPlugin plugin) {
        this.plugin = plugin;
        HasSkript();
    }

    public boolean checkVersion(String version, int v) {
        boolean ret = false;
        if (SkriptPlugin != null) {
            String[] skriptV = SkriptPlugin.getDescription().getVersion().split("\\.");
            String[] checkV = version.split("\\.");
            for (int i = 0; i < v; i++) {
                int sv = (skriptV.length > i ? Integer.parseInt(skriptV[i]) : 0);
                int cv = (checkV.length > i ? Integer.parseInt(checkV[i]) : 0);
                ret = (sv >= cv);
            }
        }
        return ret;
    }

    public boolean HasSkript() {
        if (SkriptPlugin == null) SkriptPlugin = Bukkit.getPluginManager().getPlugin("Skript");
        HasSkript = SkriptPlugin != null;
        return HasSkript;
    }

    public boolean HasSkript(String version) {
        if (version.equals("none")) return false;
        boolean hasSkript = SkriptPlugin != null;
        if (SkriptPlugin == null)
            hasSkript = HasSkript();
        if (hasSkript) {
            return checkVersion(version, 3);
        }
        return false;
    }

    @Override
    public void init(Object... args) {
        String version = args.length > 0 ? (String) args[0] : null;
        if ((version == null && HasSkript) ||
                (version != null && HasSkript(version))) {
            this.versionRequired = version;
            skriptAddon = Skript.registerAddon(plugin);
        } else {  this.versionRequired = "none"; }
    }

    @Override
    public void dispose() {
        plugin = null;
        SkriptPlugin = null;
        skriptAddon = null;
        versionRequired = null;
    }

    public <E extends Effect> void registerEffect(Class<E> c, String... patterns) {
        Skript.registerEffect(c, patterns);
    }
    public <E extends Expression<T>, T> void registerExpression(Class<E> c,
                         Class<T> returnType,
                         ExpressionType type,
                         String... patterns) {
        Skript.registerExpression(c, returnType, type, patterns);
    }
    public <E extends Condition> void registerCondition(Class<E> c, String... patterns) {
        Skript.registerCondition(c, patterns);
    }
    public <E extends SkriptEvent> SkriptEventInfo<E> registerEvent(String name,
                                                                    Class<E> c,
                                                                    Class<? extends Event> event,
                                                                    String... patterns) {
        return Skript.registerEvent(name, c, event, patterns);
    }
    public <E extends SkriptEvent> SkriptEventInfo<E> registerEvent(String name,
                                                                    Class<E> c,
                                                                    Class<? extends Event>[] events,
                                                                    String... patterns) {
        return Skript.registerEvent(name, c, events, patterns);
    }
    public void registerSection(Class<Section> section, String... patterns) {
        if (HasSkript("2.6"))
            Skript.registerSection(section, patterns);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public String getVersionRequired() {
        return versionRequired;
    }

    public SkriptAddon getSkriptAddon() {
        return skriptAddon;
    }
}
