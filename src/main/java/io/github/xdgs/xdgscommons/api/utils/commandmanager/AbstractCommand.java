package io.github.xdgs.xdgscommons.api.utils.commandmanager;

import org.apache.commons.lang.Validate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.Executor;

public abstract class AbstractCommand extends Command implements TabCompleter {
    private final JavaPlugin plugin;
    private final String id;
    private Executor executor;
    private TabCompleter completer;

    protected AbstractCommand(JavaPlugin plugin, String id, String name) {
        super(name);
        this.plugin = plugin;
        this.id = id;
        this.setTabCompleter(this);
    }

    protected AbstractCommand(JavaPlugin plugin, String id, String name, String description, String usageMessage, List<String> aliases) {
        super(name);
        this.setDescription(description);
        this.setUsage(usageMessage);
        this.setAliases(aliases);
        this.plugin = plugin;
        this.id = id;
        this.setTabCompleter(this);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) {
        return Execute(commandSender, command, args);
    }

    public abstract boolean Execute(CommandSender commandSender, String command, String[] args);

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public void setTabCompleter(@Nullable TabCompleter completer) {
        this.completer = completer;
    }

    @Nullable
    public TabCompleter getTabCompleter() {
        return completer;
    }

    public abstract List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args);

    @NotNull @Override
    public java.util.List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws CommandException, IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");

        List<String> completions = null;

        try {
            if (completer != null) completions = completer.onTabComplete(sender, this, alias, args);
            if (completions == null && executor instanceof TabCompleter) completions = ((TabCompleter) executor).onTabComplete(sender, this, alias, args);
        } catch (Throwable ex) {
            StringBuilder message = new StringBuilder();
            message.append("Unhandled exception during tab completion for command '/").append(alias).append(' ');
            for (String arg : args) message.append(arg).append(' ');
            message.deleteCharAt(message.length() - 1).append("' in plugin ").append(plugin.getDescription().getFullName());
            throw new CommandException(message.toString(), ex);
        }

        if (completions == null) {
            return super.tabComplete(sender, alias, args);
        }

        return completions;
    }

    public String getID() {
        return id;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
