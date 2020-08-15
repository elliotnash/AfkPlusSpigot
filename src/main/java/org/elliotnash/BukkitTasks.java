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

    public static final int repeatCounter = 8;

    public static HashMap<UUID, Pair<Block, Integer>> playerCache = new HashMap<>();

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
            int afkAmount=0;
            if (playerCache.containsKey(player.getUniqueId())) {
                if (player.getLocation().getBlock().toString().equals( playerCache.get(player.getUniqueId()).getKey().toString()) ) {
                    //runs if player has stayed in same location
                    if (repeatCounter>playerCache.get(player.getUniqueId()).getValue()) {
                        afkAmount = playerCache.get(player.getUniqueId()).getValue() + 1;
                        //player hasn't moved, but not for as long as necessary
                    } else if (repeatCounter==playerCache.get(player.getUniqueId()).getValue()){
                        //player is not afk, afk them
                        Bukkit.broadcastMessage(ChatColor.RED + player.getName() + ChatColor.GOLD + " is now afk");
                        afkAmount=repeatCounter+1;
                    } else{
                        afkAmount=repeatCounter+1;
                    }
                } else {
                    if (repeatCounter<=playerCache.get(player.getUniqueId()).getValue()) {
                        //player is afk but just moved
                        Bukkit.broadcastMessage(ChatColor.RED + player.getName() + ChatColor.GOLD + " is no longer afk");
                    }
                }
            }
            playerCache.put(player.getUniqueId(), new Pair<Block, Integer>(player.getLocation().getBlock(), afkAmount));
        }
    }

}
