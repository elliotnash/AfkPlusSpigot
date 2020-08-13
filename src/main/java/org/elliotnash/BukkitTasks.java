package org.elliotnash;

import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
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

    public HashMap<UUID, Pair<Block, Boolean>> playerCache = new HashMap<>();

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
            boolean isAfk=false;
            if (playerCache.containsKey(player.getUniqueId())) {
                if (player.getLocation().getBlock().toString().equals( playerCache.get(player.getUniqueId()).getKey().toString()) ) {
                    //runs if player has stayed in same location
                    isAfk=true;
                    if (!playerCache.get(player.getUniqueId()).getValue()) {
                        //player is not afk, afk them
                        Bukkit.broadcastMessage(ChatColor.RED + player.getName() + ChatColor.GOLD + " is now afk");
                    }
                } else {
                    if (playerCache.get(player.getUniqueId()).getValue()) {
                        //player is afk but just moved
                        Bukkit.broadcastMessage(ChatColor.RED + player.getName() + ChatColor.GOLD + " is no longer afk");
                    }
                }
            }
            playerCache.put(player.getUniqueId(), new Pair<Block, Boolean>(player.getLocation().getBlock(), isAfk));
        }
    }

}
