package io.github.xdgs.xdgscommons.api.utils.commandmanager.command;

import io.github.xdgs.xdgscommons.api.utils.commandmanager.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public abstract class SubCommandedCommand extends AbstractCommand {
    private String noSubCommandUsage;
    private final HashMap<String, SubCommand> subCommandHashMap = new HashMap<>();

    public SubCommandedCommand(JavaPlugin plugin, String name) {
        super(plugin, name);
        noSubCommandUsage = "/" + name + " <subCommands>";
    }

    @Override
    public boolean Execute(CommandSender commandSender, String command, String[] args) {
        if (args.length > 0) {
            if (subCommandHashMap.containsKey(args[0])) {
                return subCommandHashMap.get(args[0]).run(commandSender, command, List.of(args).subList(1, args.length));
            }
        } else {
            commandSender.sendMessage(noSubCommandUsage.replaceAll("<subCommands>",
                    "(" + String.join("|", List.of(subCommandHashMap.keySet().toArray(String[]::new)) + ")"
                    )));
        }
        return true;
    }

    public void addSubCommand(String subCommandName, SubCommand subCommand) {
        subCommandHashMap.put(subCommandName, subCommand);
    }

    public SubCommand removeSubCommand(String subCommandName) {
        return subCommandHashMap.remove(subCommandName);
    }

    public abstract List<String> subCommandTabComplete(CommandSender sender, String subcommand, String[] args);

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length > 1) {
            return subCommandTabComplete(sender, args[0], List.of(args).subList(1, args.length).toArray(String[]::new));
        } else {
            return List.of(subCommandHashMap.keySet().toArray(String[]::new));
        }
    }

    public HashMap<String, SubCommand> getSubCommandHashMap() {
        return subCommandHashMap;
    }

    public String getNoSubCommandUsage() {
        return noSubCommandUsage;
    }

    public void setNoSubCommandUsage(String noSubCommandUsage) {
        this.noSubCommandUsage = noSubCommandUsage;
    }
}
