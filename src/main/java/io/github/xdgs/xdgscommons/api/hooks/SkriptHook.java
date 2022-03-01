package io.github.xdgs.xdgscommons.api.hooks;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SkriptHook implements Hook {
    private final JavaPlugin plugin;

    private Plugin SkriptPlugin;
    private String versionRequired = "2.5";
    private boolean HasSkript;

    public SkriptHook(JavaPlugin plugin) {
        this.plugin = plugin;
        HasSkript();
    }

    public boolean HasSkript() {
        if (SkriptPlugin == null)
            SkriptPlugin = Bukkit.getPluginManager().getPlugin("Skript");
        HasSkript = SkriptPlugin != null;
        return HasSkript;
    }
    public boolean HasSkript(String version) {
        if (version.equals("none")) return false;
        boolean hasSkript = SkriptPlugin != null;
        if (SkriptPlugin == null)
            hasSkript = HasSkript();
        if (hasSkript) {
            String v = SkriptPlugin.getDescription().getVersion();
            if (version.split("\\.").length - 1 <= 1) {
                String[] vv = v.split("\\.");
                String[] vvv = version.split("\\.");
                return Integer.parseInt(vv[0]) == Integer.parseInt(vvv[0]) &&
                        Integer.parseInt(vv[1]) == Integer.parseInt(vvv[1]);
            }
            return v.startsWith(version);
        }
        return false;
    }

    @Override
    public void init(Object... args) {
        String version = args.length > 0 ? (String) args[0] : null;
        if ((version == null && HasSkript) ||
                (version != null && HasSkript(version))) {
            this.versionRequired = version;
            Skript.registerAddon(plugin);
        } else {  this.versionRequired = "none"; }
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
    public <E extends SkriptEvent> void registerEvent(String name,
                                                      Class<E> c,
                                                      Class<? extends Event> event,
                                                      String... patterns) {
        Skript.registerEvent(name, c, event, patterns);
    }
    public <E extends SkriptEvent> void registerEvent(String name,
                                                      Class<E> c,
                                                      Class<? extends Event>[] events,
                                                      String... patterns) {
        Skript.registerEvent(name, c, events, patterns);
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
}
