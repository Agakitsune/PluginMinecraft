package com.agakitsune.plugin;

import com.agakitsune.plugin.command.EpitechCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class HolyPlugin extends JavaPlugin {

    @Override
    public void onDisable() {
        System.out.println("HolyPlugin disabled");
    }

    @Override
    public void onEnable() {
        System.out.println("HolyPlugin enabled");
        //---------------------------
        getServer().getPluginManager().registerEvents(new Events(), this);
        //---------------------------
        this.getCommand("epitech").setExecutor(new EpitechCommand());
        //this.getCommand("help").setExecutor(new EpitechCommand());
    }
}
