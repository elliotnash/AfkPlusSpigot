package org.elliotnash;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("afk").setExecutor(new AfkCommand());

        //schedule task to be run every x ticks
        BukkitTask task = new BukkitTasks(this, 5).runTaskTimer(this, (20*2), (20*2));

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
