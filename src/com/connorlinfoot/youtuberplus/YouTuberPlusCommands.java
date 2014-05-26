package com.connorlinfoot.youtuberplus;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Connor Linfoot on 08/04/2014.
 */

public class YouTuberPlusCommands implements CommandExecutor {
    private Plugin instance = YouTuberPlus.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Plugin instance = YouTuberPlus.getInstance();
        if (label.equalsIgnoreCase("yt") || label.equalsIgnoreCase("youtube") || label.equalsIgnoreCase("youtuber")) {
            Player player = (Player) sender;
            if( args.length == 0) {
                player.sendMessage("Use /yt help");
            } else if(!args[0].equals("")) {
                if(args[0].equalsIgnoreCase("help")){
                    player.sendMessage(ChatColor.RED + "=== YouTuber Plus Beta ===");
                    player.sendMessage("/record - Show your recording!");
                    player.sendMessage("/subs <YT User> - Get amount of subscribers for a channel!");
                    player.sendMessage("/followers <Twitch User> - Get amount of followers for a channel!");
                    player.sendMessage("/rankup <YouTube/Twitch> - Attempt to rank up as a YouTuber or Streamer!");
                    player.sendMessage("/link <YouTube/Twitch> <Username> - Link to a YouTube or Twitch account!");
                    player.sendMessage("/yt help - Shows this screen!");
                    player.sendMessage("/yt reload - Reloads the config!");
                    player.sendMessage("/yt about - About the plugin!");
                    player.sendMessage("/yt whatsnew - Shows whats new in the latest update!");
                } else if(args[0].equalsIgnoreCase("about")) {
                    player.sendMessage(ChatColor.RED + "=== YouTuber Plus Beta ===");
                    //player.sendMessage("=== Version: " + getDescription().getDescription() + " ===");
                    player.sendMessage("=== Version: " + instance.getDescription().getVersion() + " ===");
                } else if(args[0].equalsIgnoreCase("reload")) {
                    if(player.hasPermission("ytplus.admin.reload")) {
                        instance.reloadConfig();
                        player.sendMessage("Configuration Reloaded");
                    } else {
                        player.sendMessage(ChatColor.RED + "Access to this command was denied!");
                    }
                } else if(args[0].equalsIgnoreCase("whatsnew")) {
                    player.sendMessage(ChatColor.RED + "=== YouTuber Plus Beta ===");
                    player.sendMessage(ChatColor.RED + "===== What's New 0.2 =====");
                    player.sendMessage("- Full Twitch Integration");
                    player.sendMessage("- Permissions");
                    player.sendMessage("- New Command: /record");
                    player.sendMessage("- New Commands: /yt");
                    player.sendMessage("- Bug Fixes");
                    player.sendMessage("+ More!");
                }
            }
        }

        if (label.equalsIgnoreCase("record")) {
            Player player = (Player) sender;
            if(player.hasPermission("ytplus.record")) {
                if (args.length == 0) {
                    World world = player.getWorld();
                    ArrayList recordingPlayers = (ArrayList) instance.getConfig().getList("recordingPlayers");
                    if (recordingPlayers.contains(player.getName())) {
                        world.playEffect(player.getLocation(), Effect.CLICK1,0);
                        recordingPlayers.remove(player.getName());
                        Bukkit.getServer().broadcastMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " " + player.getName() + " has now stopped recording!");
                    } else {
                        world.playEffect(player.getLocation(), Effect.CLICK2,0);
                        Bukkit.getServer().broadcastMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " " + player.getName() + " is now recording!");
                        recordingPlayers.add(player.getName());
                    }
                    instance.getConfig().set("recordingPlayers", recordingPlayers);
                    instance.saveConfig();
                    //player.sendMessage(ChatColor.RED + getConfig().getString("Prefix") + ChatColor.WHITE + " " + recordingPlayers);
                }
            } else {
                player.sendMessage(ChatColor.RED + "Access to this command was denied!");
            }
        }

        if (label.equalsIgnoreCase("subs")) {
            Player player = (Player) sender;
            if(player.hasPermission("ytplus.subs")) {
                //http://theminecraftapi.com/youtube/subs/?user=ConnorLinfoot
                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " Please use the command by doing /subs <YouTube User>");
                } else if (!args[0].equals("")) {
                    String subscribers = null;
                    try {
                        subscribers = new Scanner(new URL("http://theminecraftapi.com/youtube/subs/?user=" + args[0]).openStream(), "UTF-8").useDelimiter("\\A").next();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " " + args[0] + " has " + subscribers + " subscribers.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Access to this command was denied!");
            }
        }

        if (label.equalsIgnoreCase("followers")) {
            Player player = (Player) sender;
            if(player.hasPermission("ytplus.followers")) {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " Please use the command by doing /followers <Twitch User>");
                } else if (!args[0].equals("")) {
                    String followers = null;
                    try {
                        followers = new Scanner(new URL("http://theminecraftapi.com/twitch/followers/?user=" + args[0]).openStream(), "UTF-8").useDelimiter("\\A").next();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(followers.equals("NF")){
                        player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " " + args[0] + " was not found on Twitch. Or an error occurred with Twitch.");
                    } else {
                        player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " " + args[0] + " has " + followers + " followers on Twitch.");
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "Access to this command was denied!");
            }
        }



















        // Rank up command!

        if (label.equalsIgnoreCase("rankup")) {
            Player player = (Player) sender;
            if(player.hasPermission("ytplus.rankup")) {
                if( args.length == 0 ){
                    player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " Please use the command by doing /rankup <Twitch/YouTube>");
                } else if(args[0].equalsIgnoreCase("YouTube")) {
                    String ytuser = null;
                    try {
                        ytuser = new Scanner(new URL("http://theminecraftapi.com/youtube/getuser/?uuid=" + player.getUniqueId()).openStream(), "UTF-8").useDelimiter("\\A").next();
                    } catch (IOException e) {
                        ytuser = "";
                    }

                    String subscribers = null;
                    try {
                        if (!ytuser.equals("NF")) {
                            subscribers = new Scanner(new URL("http://theminecraftapi.com/youtube/subs/?user=" + ytuser).openStream(), "UTF-8").useDelimiter("\\A").next();
                        }
                    } catch (IOException e) {
                        subscribers = "";
                    }
                    int subscribers2 = 0;
                    if (subscribers == null || subscribers.equals("")) {
                        subscribers2 = 0;
                    } else {
                        subscribers2 = Integer.parseInt(subscribers);
                    }


                    if (ytuser.equals("NF")) {
                        player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " You have not linked up a YouTube account! Do /link!");
                    } else {
                        if (subscribers2 < instance.getConfig().getInt("Subs")) {
                            player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " " + ytuser + " is your YouTube account. You have " + subscribers + " subscribers. Not enough for rank, requirement is " + instance.getConfig().getInt("Subs") + "!");
                        } else {
                            if(instance.getConfig().getString("RunCommand").equals("true")) {
                                String thecmd = instance.getConfig().getString("Command").replaceAll("<playername>", player.getName());
                                instance.getServer().dispatchCommand(instance.getServer().getConsoleSender(), thecmd);
                            }
                            if(instance.getConfig().getString("LocalMode").equals("true")) {
                                ArrayList YouTubers = (ArrayList) instance.getConfig().getList("YouTubers");
                                String playerid = String.valueOf(player.getUniqueId());
                                if (YouTubers.contains(playerid)) {
                                    player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " You already have YouTuber status!");
                                } else {
                                    YouTubers.add(playerid);
                                    player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " " + ytuser + " is your YouTube account. You have " + subscribers + " subscribers. You have been ranked up!");
                                }
                                instance.getConfig().set("YouTubers", YouTubers);
                                instance.saveConfig();
                            }
                        }
                    }
                } else if(args[0].equalsIgnoreCase("twitch")) {
                    String twitchuser = "";
                    try {
                        twitchuser = new Scanner(new URL("http://theminecraftapi.com/twitch/getuser/?uuid=" + player.getUniqueId()).openStream(), "UTF-8").useDelimiter("\\A").next();
                    } catch (IOException e) {
                        twitchuser = "";
                    }

                    String followers = null;
                    try {
                        if (!twitchuser.equals("NF")) {
                            followers = new Scanner(new URL("http://theminecraftapi.com/twitch/followers/?user=" + twitchuser).openStream(), "UTF-8").useDelimiter("\\A").next();
                        }
                    } catch (IOException e) {
                        followers = "";
                    }
                    int followers2 = 0;
                    if (followers == null || followers.equals("")) {
                        followers2 = 0;
                    } else {
                        followers2 = Integer.parseInt(followers);
                    }

                    if (twitchuser.equals("NF")) {
                        player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " You have not linked up a Twitch account! Do /link!");
                    } else {
                        if (followers2 < instance.getConfig().getInt("Followers")) {
                            player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " " + twitchuser + " is your Twitch account. You have " + followers + " followers. Not enough for rank, requirement is " + instance.getConfig().getInt("Followers") + "!");
                        } else {
                            if(instance.getConfig().getString("RunCommand").equals("true")) {
                                String thecmd = instance.getConfig().getString("TwitchCommand").replaceAll("<playername>", player.getName());
                                instance.getServer().dispatchCommand(instance.getServer().getConsoleSender(), thecmd);
                            }
                            player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " " + twitchuser + " is your Twitch account. You have " + followers + " followers. You have been ranked up!");
                        }
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "Access to this command was denied!");
            }

        }

        if (label.equalsIgnoreCase("link")) {
            Player player = (Player) sender;
            if(player.hasPermission("ytplus.link")) {
                if( args.length == 0 ) {
                    player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " Please use the command by doing /link <Twitch/YouTube> <Username>");
                } else if( args.length == 1 ){
                    player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " Please use the command by doing /link <Twitch/YouTube> <Username>");
                } else if( args.length == 2 && args[0].equalsIgnoreCase("youtube")){
                    String ytuser = null;
                    try {
                        ytuser = new Scanner(new URL("http://theminecraftapi.com/youtube/getuser/?uuid=" + player.getUniqueId()).openStream(), "UTF-8").useDelimiter("\\A").next();
                    } catch (IOException e) {
                        //e.printStackTrace();
                        ytuser = "";
                    }
                    if (ytuser.equals("NF")) {
                        player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " Please click the following link");
                        player.sendMessage("http://theminecraftapi.com/youtube/login/?uuid=" + player.getUniqueId() + "&ytuser=" + args[1] + "&user=" + player.getName());
                    } else {
                        player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " You are already linked to " + ytuser);
                    }
                } else if( args.length == 2 && args[0].equalsIgnoreCase("twitch")){
                    String tuser = null;
                    try {
                        tuser = new Scanner(new URL("http://theminecraftapi.com/twitch/getuser/?uuid=" + player.getUniqueId()).openStream(), "UTF-8").useDelimiter("\\A").next();
                    } catch (IOException e) {
                        //e.printStackTrace();
                        tuser = "";
                    }
                    if (tuser.equals("NF")) {
                        player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " Please click the following link");
                        player.sendMessage("http://theminecraftapi.com/twitch/verify/?uuid=" + player.getUniqueId() + "&tuser=" + args[1]);
                    } else {
                        player.sendMessage(ChatColor.RED + instance.getConfig().getString("Prefix") + ChatColor.WHITE + " You are already linked to " + tuser);
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "Access to this command was denied!");
            }
        }

        return false;
    }
}
