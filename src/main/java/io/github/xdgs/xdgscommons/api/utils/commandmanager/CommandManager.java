package io.github.xdgs.xdgscommons.api.utils.commandmanager;

import java.util.HashMap;

public class CommandManager {
    private HashMap<String, AbstractCommand> commands = new HashMap<>();
    
    public boolean registerCommand(AbstractCommand command) {
        commands.put(command.getName(), command);
        return CommandMapUtils.registerCommand(command);
    }

    public boolean unregisterCommand(String command) {
        commands.remove(command);
        return CommandMapUtils.unregisterCommand(command);
    }

    public boolean unregisterCommand(AbstractCommand command) {
        commands.remove(command.getName());
        return CommandMapUtils.unregisterCommand(command);
    }
    
    public void dispose() {
        this.commands = null;
    }

    public HashMap<String, AbstractCommand> getCommands() {
        return commands;
    }
}
