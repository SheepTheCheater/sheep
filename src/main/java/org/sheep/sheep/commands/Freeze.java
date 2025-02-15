package org.sheep.sheep.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.sheep.sheep.messageutils;

import java.util.HashSet;
import java.util.Set;

public class Freeze implements CommandExecutor {

    private final Set<Player> frozenPlayers = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messageutils.formatMessage(ChatColor.RED + "This command can only be executed in-game as a player."));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("sheep.freeze")) {
            player.sendMessage(messageutils.formatMessage(ChatColor.RED + "You do not have permission to execute this command."));
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(messageutils.formatMessage(ChatColor.RED + "use /" + label + " (playername)"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            player.sendMessage(messageutils.formatMessage(ChatColor.RED + "this player was not found."));
            return true;
        }

        if (label.equalsIgnoreCase("freeze")) {
            if (frozenPlayers.contains(target)) {
                player.sendMessage(messageutils.formatMessage(ChatColor.YELLOW + target.getName() + ChatColor.RED + " is already freezed."));
                return true;
            }
            frozenPlayers.add(target);
            player.sendMessage(messageutils.formatMessage(ChatColor.GREEN + "you have freezed " + ChatColor.RED + target.getName()));
            target.sendMessage(messageutils.formatMessage(ChatColor.RED + "you have been freezed from " + ChatColor.RED + player.getName()));
        } else if (label.equalsIgnoreCase("unfreeze")) {
            if (!frozenPlayers.contains(target)) {
                player.sendMessage(messageutils.formatMessage(ChatColor.RED + target.getName() + " is not freezed."));
                return true;
            }
            frozenPlayers.remove(target);
            player.sendMessage(messageutils.formatMessage(ChatColor.GREEN + "you have unfreezed " + ChatColor.AQUA + target.getName()));
            target.sendMessage(messageutils.formatMessage(ChatColor.GREEN + "you have been unfreezed from " + ChatColor.AQUA + player.getName()));
        }

        return true;
    }

    public boolean isFrozen(Player player) {
        return frozenPlayers.contains(player);
    }
}
