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
                knownCommandsField = commandMap.getClass().getDeclaredField("knownCommands");
            } catch (NoSuchFieldException e) {
                knownCommandsField = commandMap.getClass().getSuperclass().getDeclaredField("knownCommands");
            }
            knownCommandsField.setAccessible(true);
            knownCommands = (HashMap<String, Command>) knownCommandsField.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static boolean registerCommand(AbstractCommand command) {
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
