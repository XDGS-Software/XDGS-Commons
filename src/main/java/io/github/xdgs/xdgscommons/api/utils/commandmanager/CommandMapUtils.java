package io.github.xdgs.xdgscommons.api.utils.commandmanager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class CommandMapUtils {
    private static SimpleCommandMap commandMap;
    private static HashMap<String, Command> knownCommands;

    static {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer());

            Field knownCommandsField;

            try {
                knownCommandsField = commandMap.getClass().getDeclaredField("knownCommands"); // below 1.13
            } catch (NoSuchFieldException e) {
                knownCommandsField = commandMap.getClass().getSuperclass().getDeclaredField("knownCommands"); // 1.13+
            }

            knownCommandsField.setAccessible(true);
            knownCommands = (HashMap<String, Command>) knownCommandsField.get(commandMap);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static boolean registerCommand(AbstractCommand command) {
        return commandMap.register(command.getPlugin().getDescription().getName(), command);
    }

    public static boolean unregisterCommand(String command) {
        return knownCommands.remove(command) != null;
    }

    public static boolean unregisterCommand(AbstractCommand command) {
        return unregisterCommand(command.getName());
    }

    public static CommandMap getCommandMap() {
        return commandMap;
    }
}
