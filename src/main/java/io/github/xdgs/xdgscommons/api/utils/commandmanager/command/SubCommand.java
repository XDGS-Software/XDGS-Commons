package io.github.xdgs.xdgscommons.api.utils.commandmanager.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {
    boolean run(CommandSender commandSender, String command, List<String> args);
}
