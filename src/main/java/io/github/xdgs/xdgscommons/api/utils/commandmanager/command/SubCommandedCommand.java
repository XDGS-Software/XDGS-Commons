package io.github.xdgs.xdgscommons.api.utils.commandmanager.command;

import io.github.xdgs.xdgscommons.api.utils.commandmanager.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public abstract class SubCommandedCommand extends AbstractCommand {
    public HashMap<String, SubCommand> subCommandHashMap = new HashMap<>();

    public SubCommandedCommand(JavaPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    public boolean Execute(CommandSender commandSender, String command, String[] args) {
        if (args.length > 0) {
            if (subCommandHashMap.containsKey(args[0])) {
                return subCommandHashMap.get(args[0]).run(commandSender, command, List.of(args).subList(1, args.length));
            }
        }
        return true;
    }

    public void putSubCommand(String subCommandName, SubCommand subCommand) {
        subCommandHashMap.put(subCommandName, subCommand);
    }

    public SubCommand removeSubCommand(String subCommandName) {
        return subCommandHashMap.remove(subCommandName);
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command command, @NotNull String alias, @NotNull String[] args) {
        if (subCommandHashMap.keySet().size() > 0) return List.of(subCommandHashMap.keySet().toArray(String[]::new));
        return null;
    }
}
