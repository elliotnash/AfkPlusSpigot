package org.elliotnash;

import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BukkitTasks extends BukkitRunnable {

    private final JavaPlugin plugin;

    private int counter;

    public HashMap<UUID, Pair<Location, Boolean>> playerCache;

    public BukkitTasks(JavaPlugin plugin, int counter) {
        this.plugin = plugin;
        if (counter <= 0) {
            throw new IllegalArgumentException("counter must be greater than 0");
        } else {
            this.counter = counter;
        }
    }

    @Override
    public void run() {
        for (Player player: Bukkit.getOnlinePlayers()){
            if (player.getLocation()==playerCache.get(player.getUniqueId()).getKey()){
                //runs if player has stayed in same location
                if (!playerCache.get(player.getUniqueId()).getValue()){
                    playerCache.put(player.getUniqueId(), new Pair<Location, Boolean>(player.getLocation(), true));
                    //player is not afk, afk them
                    Bukkit.broadcastMessage(ChatColor.RED+player.getName()+ChatColor.GOLD+" is now afk");
                }
            } else {
                if (playerCache.get(player.getUniqueId()).getValue()){
                    //player is afk but just moved
                    playerCache.put(player.getUniqueId(), new Pair<Location, Boolean>(player.getLocation(), false));
                    Bukkit.broadcastMessage(ChatColor.RED+player.getName()+ChatColor.GOLD+" is now afk");
                }
            }
        }
    }

}
