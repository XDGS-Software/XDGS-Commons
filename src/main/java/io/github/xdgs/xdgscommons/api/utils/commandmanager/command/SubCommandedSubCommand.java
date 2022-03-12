package io.github.xdgs.xdgscommons.api.utils.commandmanager.command;

import io.github.xdgs.xdgscommons.api.utils.commandmanager.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.HashMap;
import java.util.List;

public abstract class SubCommandedSubCommand implements SubCommand, TabCompleter {
    private String noSubCommandUsage;
    private AbstractCommand parent;
    private TabCompleter completer;
    private final HashMap<String, SubCommand> subCommandHashMap = new HashMap<>();

    public SubCommandedSubCommand(String name) {
        noSubCommandUsage = "/" + name + " ...";
    }

    @Override
    public boolean run(CommandSender commandSender, String command, List<String> args) {
        if (args.size() <= 0) {
            commandSender.sendMessage(noSubCommandUsage.replaceAll("<subCommands>",
                    String.join("|", List.of(subCommandHashMap.keySet().toArray(String[]::new))
                    )));
            return true;
        }

        if (subCommandHashMap.containsKey(args.get(0))) {
            return runSub(commandSender, args.get(0), args);
        }

        return true;
    }

    public abstract boolean runSub(CommandSender commandSender, String subCommand, List<String> args);

    public void addSubCommand(String subCommandName, SubCommand subCommand) {
        subCommandHashMap.put(subCommandName, subCommand);
    }

    public SubCommand removeSubCommand(String subCommandName) {
        return subCommandHashMap.remove(subCommandName);
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

    public AbstractCommand getParent() {
        return parent;
    }

    public void setParent(AbstractCommand parent) {
        this.parent = parent;
        completer = parent.getTabCompleter();
        parent.setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length > 0 && this.subCommandHashMap.containsKey(args[0]))
            return onTabCompleteSubCommand(sender, args[0], List.of(args).subList(1, args.length).toArray(String[]::new));
        return completer.onTabComplete(sender, command, alias, args);
    }

    protected abstract List<String> onTabCompleteSubCommand(CommandSender sender, String subCommand, String[] args);
}
