package org.sheep.sheep.commands;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.sheep.sheep.messageutils;

import java.sql.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.bukkit.ChatColor.AQUA;

public class help implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed in-game as a player.");
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("sheep.help")) {
            player.sendMessage(messageutils.formatMessage(ChatColor.RED + "you have no permission to view the help-list."));
            return true;

        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                player.sendMessage(ChatColor.YELLOW + "Command List:");
                player.sendMessage(AQUA + "\n" + "- " + ChatColor.WHITE + "/gm " + ChatColor.GRAY + "changes your gamemode or others.");
                player.sendMessage(AQUA + "\n" + "- " + ChatColor.WHITE + "/heal " + ChatColor.GRAY + "heal you or other players.");
                player.sendMessage(AQUA + "\n" + "- " + ChatColor.WHITE + "/freeze " + ChatColor.GRAY + "freeze you or other players.");
                player.sendMessage(AQUA + "\n" + "- " + ChatColor.WHITE + "/unfreeze " + ChatColor.GRAY + "unfreeze you or other players.");
            } else {
                sender.sendMessage(messageutils.formatMessage(ChatColor.RED + "use (/sheep help) for help for the commands."));
            }
        } else {
            sender.sendMessage(ChatColor.RED + "use /sheep help for help");
        }
        return false;
    }
    @Override
    public List <String> onTabComplete(CommandSender sender, Command command, String alias, String[]args){
        if (command.getName().equalsIgnoreCase("sheep")){
            if (args.length == 1) {
                return Arrays.asList("help", "test");}
        }
        return Collections.emptyList();
    }
}
