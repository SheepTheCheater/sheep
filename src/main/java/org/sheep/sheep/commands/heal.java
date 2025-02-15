package org.sheep.sheep.commands;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.sheep.sheep.messageutils;

public class heal implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Wenn der Sender kein Spieler ist
        if (!(sender instanceof Player)) {
            sender.sendMessage(messageutils.formatMessage(ChatColor.RED + "This command can only be executed in-game as a player."));
            return false;
        }

        Player player = (Player) sender;

        // Überprüfe, ob der Spieler die nötige Berechtigung hat
        if (!player.hasPermission("sheep.heal")) {
            player.sendMessage(messageutils.formatMessage(ChatColor.RED + "You do not have permission to execute this command."));
            return true;
        }

        // Wenn der Befehl ohne Spielername ausgeführt wird, heile den Spieler, der den Befehl ausführt
        if (args.length == 0) {
            player.setHealth(20); // Setze die Gesundheit des Spielers auf maximal
            player.sendMessage(messageutils.formatMessage(ChatColor.GREEN + "you have been healed, you are now on" + " 10" + ChatColor.RED + "❤"));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        } else if (args.length == 1) { // Wenn ein Spielername angegeben wurde
            String targetName = args[0];
            Player targetPlayer = Bukkit.getPlayer(targetName);

            // Überprüfe, ob der Zielspieler online ist
            if (targetPlayer != null) {
                targetPlayer.setHealth(20); // Heile den Zielspieler
        targetPlayer.sendMessage(messageutils.formatMessage(ChatColor.GREEN + "you have been healed from " + ChatColor.RED + player.getName() + " " + ChatColor.GREEN + "you are now on " + ChatColor.WHITE + "10" + ChatColor.RED + "❤"));
                targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                player.sendMessage(messageutils.formatMessage(ChatColor.GREEN + "you have been healed " + ChatColor.RED + targetName));
            } else {
                player.sendMessage(messageutils.formatMessage(ChatColor.RED + "the player " + ChatColor.YELLOW + targetName + ChatColor.RED + " was not found."));
            }
        } else {
            player.sendMessage(messageutils.formatMessage(ChatColor.RED + "use /heal (playername)"));
        }

        return true;
    }
}
