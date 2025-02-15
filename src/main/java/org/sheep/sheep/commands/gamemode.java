package org.sheep.sheep.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.sheep.sheep.messageutils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class gamemode implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Ensure the sender is a player or has console permissions
        if (!(sender instanceof Player) && args.length < 2) {
            sender.sendMessage(messageutils.formatMessage(ChatColor.RED + "Usage: /gm <mode> <player> when using from console."));
            return false;
        }

        Player executor = sender instanceof Player ? (Player) sender : null;

        if (executor != null && !executor.hasPermission("sheep.gm")) {
            executor.sendMessage(messageutils.formatMessage(ChatColor.RED + "You do not have permission to execute this command."));
            return true;
        }

        // Ensure at least one argument is provided
        if (args.length < 1) {
            sender.sendMessage(messageutils.formatMessage(ChatColor.RED + "use /gm (0,1,2,3) (player)"));
            return false;
        }

        String mode = args[0];
        Player targetPlayer;

        // Determine the target player (if specified)
        if (args.length > 1) {
            targetPlayer = Bukkit.getPlayer(args[1]);
            if (targetPlayer == null) {
                sender.sendMessage(messageutils.formatMessage(ChatColor.RED + "Player not found."));
                return false;
            }
        } else if (executor != null) {
            targetPlayer = executor;
        } else {
            sender.sendMessage(messageutils.formatMessage(ChatColor.RED + "Please specify a player when using this command from the console."));
            return false;
        }

        // Set the GameMode based on the mode argument
        switch (mode) {
            case "1":
                targetPlayer.setGameMode(GameMode.CREATIVE);
                sender.sendMessage(messageutils.formatMessage(ChatColor.WHITE + targetPlayer.getName() + " is now in Creative mode."));
                if (targetPlayer != executor) {
                    targetPlayer.sendMessage(messageutils.formatMessage(ChatColor.WHITE + "Your game mode has been changed to Creative by " + sender.getName() + "."));
                }
                break;
            case "0":
                targetPlayer.setGameMode(GameMode.SURVIVAL);
                sender.sendMessage(messageutils.formatMessage(ChatColor.WHITE + targetPlayer.getName() + " is now in Survival mode."));
                if (targetPlayer != executor) {
                    targetPlayer.sendMessage(messageutils.formatMessage(ChatColor.WHITE + "Your game mode has been changed to Survival by " + sender.getName() + "."));
                }
                break;
            case "2":
                targetPlayer.setGameMode(GameMode.ADVENTURE);
                sender.sendMessage(messageutils.formatMessage(ChatColor.WHITE + targetPlayer.getName() + " is now in Adventure mode."));
                if (targetPlayer != executor) {
                    targetPlayer.sendMessage(messageutils.formatMessage(ChatColor.WHITE + "Your game mode has been changed to Adventure by " + sender.getName() + "."));
                }
                break;
            case "3":
                targetPlayer.setGameMode(GameMode.SPECTATOR);
                sender.sendMessage(messageutils.formatMessage(ChatColor.WHITE + targetPlayer.getName() + " is now in Spectator mode."));
                if (targetPlayer != executor) {
                    targetPlayer.sendMessage(messageutils.formatMessage(ChatColor.WHITE + "Your game mode has been changed to Spectator by " + sender.getName() + "."));
                }
                break;
            default:
                sender.sendMessage(messageutils.formatMessage(ChatColor.RED + "Invalid mode. Use /gm 0, 1, 2, or 3."));
                return false;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("gm")) {
            if (args.length == 1) {
                return Arrays.asList("0", "1", "2", "3");
            } else if (args.length == 2) {
                return null; // Let Bukkit auto-complete player names
            }
        }
        return Collections.emptyList();
    }
}
