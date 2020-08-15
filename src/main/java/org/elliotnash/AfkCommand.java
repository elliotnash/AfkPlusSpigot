package org.elliotnash;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AfkCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        afkList(sender);

        return true;
    }

    void afkList(CommandSender sender){
        StringBuilder playersBuilder = new StringBuilder(Bukkit.getOnlinePlayers().size()*16);
        for (Player player: Bukkit.getOnlinePlayers()){
            if (BukkitTasks.playerCache.containsKey(player.getUniqueId())) {
                if (BukkitTasks.repeatCounter<=BukkitTasks.playerCache.get(player.getUniqueId()).getValue()){
                    playersBuilder.append(player.getName()+", ");
                }
            }
        }
        //remove final ", "
        if (playersBuilder.charAt(playersBuilder.length()-2)==','){
            playersBuilder.delete(playersBuilder.length()-2, playersBuilder.length()-1);
            sender.sendMessage(ChatColor.RED+"The following players are afk:");
            sender.sendMessage(ChatColor.GOLD+playersBuilder.toString());
        } else {
            sender.sendMessage(ChatColor.RED+"There are currently no players afk");
        }

    }


}
