package io.github.xdgs.xdgscommons.api.utils.commandmanager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.HashMap;

public class CommandMapUtils {
    private static CommandMap commandMap;
    private static HashMap<String, Command> knownCommands;

    static {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
            knownCommands =
                    (HashMap<String, Command>) commandMap.getClass().getDeclaredField("knownCommands").get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static boolean registerCommand(AbstractCommand command) {
        knownCommands.put(command.getID(), command);
        return commandMap.register(command.getID(), command);
    }

    public static boolean unregisterCommand(String command) {
        return knownCommands.remove(command) != null;
    }

    public static boolean unregisterCommand(AbstractCommand command) {
        return unregisterCommand(command.getID());
    }

    public static CommandMap getCommandMap() {
        return commandMap;
    }
}
