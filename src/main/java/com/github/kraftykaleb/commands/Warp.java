package com.github.kraftykaleb.commands;

import com.github.kraftykaleb.Main;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Kraft on 4/18/2017.
 */
public class Warp implements CommandExecutor {

    private Main plugin;

    public Warp(Main instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (sender.hasPermission("soontmcore.warp")) {
            if (args.length == 2) {
                if (Arrays.asList(plugin.serverList).contains(args[1])) {
                    if (Bukkit.getServer().getPlayer(args[0]) != null) {
                        try {
                            ByteArrayOutputStream b = new ByteArrayOutputStream();
                            DataOutputStream out = new DataOutputStream(b);
                            try {
                                out.writeUTF("Connect");
                                out.writeUTF(args[1].toLowerCase());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            //out.writeUTF(e.getPlayer().getName());


                            Bukkit.getServer().getPlayer(args[0]).sendPluginMessage(Main.get(), "BungeeCord", b.toByteArray());
                            Bukkit.getServer().getPlayer(args[0]).sendMessage(ChatColor.GREEN + "Sending you to " + args[1].toLowerCase() + "...");

                            sender.sendMessage(ChatColor.GREEN + "Successfully sent " + Bukkit.getServer().getPlayer(args[0]).getCustomName() + ChatColor.GREEN + " to " + args[1].toLowerCase());
                            return true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        sender.sendMessage(ChatColor.RED + "That player is not online!");
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "That server was not found!");
                    sender.sendMessage("DEBUG:" + plugin.serverList);
                    return true;
                }

            } else {
                sender.sendMessage(ChatColor.RED + "Invalid arguments! Use /send <player> <server>");
                return true;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to run that command!");
            return true;
        }
        return true;
    }
}
