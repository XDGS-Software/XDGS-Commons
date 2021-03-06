package io.github.xdgs.xdgscommons.api.utils.commandmanager;

import org.apache.commons.lang.Validate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public abstract class AbstractCommand extends Command implements TabCompleter {
    private final JavaPlugin plugin;
    private Executor executor;
    private TabCompleter completer;

    protected AbstractCommand(JavaPlugin plugin, String name) {
        super(name);
        this.plugin = plugin;
        this.setTabCompleter(this);
    }

    protected AbstractCommand(JavaPlugin plugin, String name, String description, String usageMessage, List<String> aliases) {
        super(name);
        this.setDescription(description);
        this.setUsage(usageMessage);
        this.setAliases(aliases);
        this.plugin = plugin;
        this.setTabCompleter(this);
    }

    public Player getPlayer(CommandSender sender) {
        if (sender instanceof Player) return (Player) sender;
        return null;
    }

    public String getTabArgSafe(String[] args, int index) {
        if (index < 0) return null;
        if (index >= args.length) return null;
        return args[index];
    }

    public List<String> getTabArgsSafe(String[] args, int... index) {
        List<String> a = new ArrayList<>();
        for (int j : index) a.add(getTabArgSafe(args, j));
        return a;
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

    public abstract List<String> onTabComplete(@NotNull CommandSender sender, Command command, @NotNull String alias, @NotNull String[] args);

    @NotNull
    @Override
    public java.util.List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws CommandException, IllegalArgumentException {
        Validate.notNull(sender, "CommandSender cannot be null");
        Validate.notNull(args, "Command arguments cannot be null");
        Validate.notNull(alias, "Command alias cannot be null");
        List<String> completions = null;
        try {
            if (completer != null)
                completions = completer.onTabComplete(sender, this, alias, args);
            if (completions == null && executor instanceof TabCompleter)
                completions = ((TabCompleter)executor).onTabComplete(sender, this, alias, args);
        } catch (Exception ex) {
            throw new CommandException("Unhandled exception during tab completion for command '/" + alias, ex);
        }
        if (completions == null)
            return super.tabComplete(sender, alias, args);
        return completions;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
