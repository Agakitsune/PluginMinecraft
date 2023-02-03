package com.agakitsune.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class HolyPlugin extends JavaPlugin {

    @Override
    public void onDisable() {
        System.out.println("HolyPlugin disabled");
    }

    @Override
    public void onEnable() {
        System.out.println("HolyPlugin enabled");
    }
}
