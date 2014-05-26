package com.connorlinfoot.youtuberplus;

import me.confuser.barapi.BarAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

import java.util.ArrayList;

/**
 * Created by Connor Linfoot on 08/04/2014.
 */

public class PlayerListener implements Listener {
    /*@EventHandler
    public void onNameTag(AsyncPlayerReceiveNameTagEvent event) {
       YouTuberPlus plugin = null;
        plugin = YouTuberPlus;
        if (plugin != null && plugin.recordingPlayers.contains("MoogleMan")) {
            event.setTag(ChatColor.RED + "MoogleMan");
        }
    }*/

    @EventHandler
    public void onPlayerJoin( PlayerJoinEvent event){
        Player player = event.getPlayer();
        Plugin instance = YouTuberPlus.getInstance();
        ArrayList YouTubers = (ArrayList) instance.getConfig().getList("YouTubers");
        String playerid = String.valueOf(player.getUniqueId());
        if (YouTubers.contains(playerid)) {
            BarAPI.setMessage(player, "Welcome YouTuber!", 5);
            World world = player.getWorld();
            world.playEffect(player.getLocation(), Effect.ENDER_SIGNAL,0);
        }
    }


    @EventHandler
    public void onPlayerQuit( PlayerQuitEvent event){
        Player player = event.getPlayer();
        Plugin instance = YouTuberPlus.getInstance();
        ArrayList recordingPlayers = (ArrayList) instance.getConfig().getList("recordingPlayers");
        if (recordingPlayers.contains(player.getName())) {
            recordingPlayers.remove(player.getName());
            Bukkit.getServer().broadcastMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " " + player.getName() + " has now stopped recording!");
        } else {
            Bukkit.getServer().broadcastMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " " + player.getName() + " is now recording!");
            recordingPlayers.add(player.getName());
        }
        instance.getConfig().set("recordingPlayers", recordingPlayers);
        instance.saveConfig();
    }
}