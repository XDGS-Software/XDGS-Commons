package io.github.xdgs.xdgscommons.api.utils.commandmanager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CommandUtils {
    public static List<Entity> getEntity(Player executor, String arg) {
        List<Entity> entities = new ArrayList<>(); // initialize entities return list
        switch (arg) {
            case "@p":
                // gets the player who executed the command
                entities.add(executor);
                break;
            case "@a":
                // get all players online
                entities.addAll(Bukkit.getOnlinePlayers());
                break;
            case "@r":
                // get a random player online
                entities.add(Bukkit.getOnlinePlayers().toArray(Entity[]::new)[(int) (Math.random() * Bukkit.getOnlinePlayers().size())]);
                break;
            case "@e":
                // loops through all the worlds and adds the entities in that world to the entities list
                for (World world : Bukkit.getWorlds()) entities.addAll(world.getEntities());
                break;
            default:
                Entity entity = Bukkit.getServer().getPlayer(arg);

                try {
                    // tries to get entity by arg (assuming arg is uuid) only if entity is null
                    if (entity == null) entity = Bukkit.getEntity(UUID.fromString(arg));
                } catch (IllegalArgumentException _ignored) { break; }
                if (entity == null) break; // if the entity is still null by then just break out the catch statement

                entities.add(entity); // add entity to list
                break;
        }
        return entities; // return entities list
    }
}
