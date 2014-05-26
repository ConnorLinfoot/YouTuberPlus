package com.connorlinfoot.youtuberplus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 * Created by Connor Linfoot on 06/04/2014.
 */

public class YouTuberPlus extends JavaPlugin {
    private static Plugin instance;
    public ArrayList recordingPlayers = new ArrayList();


    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        getCommand("yt").setExecutor(new YouTuberPlusCommands());
        getCommand("link").setExecutor(new YouTuberPlusCommands());
        getCommand("record").setExecutor(new YouTuberPlusCommands());
        getCommand("rankup").setExecutor(new YouTuberPlusCommands());
        getCommand("subs").setExecutor(new YouTuberPlusCommands());
        getCommand("followers").setExecutor(new YouTuberPlusCommands());
        getConfig().set("recordingPlayers", recordingPlayers);
        saveConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        if(getConfig().getString("LocalMode").equals("true")){
            getLogger().info(getDescription().getName() + " has been enabled, running in local mode!");
            ArrayList YouTubers = (ArrayList) getConfig().getList("YouTubers");
            getLogger().info(String.valueOf(YouTubers));
        } else {
            getLogger().info(getDescription().getName() + " has been enabled, running in non local mode!");
        }
    }

    public void onDisable() {
        getLogger().info(getDescription().getName() + " has been disabled!");
    }

    public static Plugin getInstance() {
        return instance;
    }

}