package org.sheep.sheep.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.sheep.sheep.messageutils;

public class spawn implements CommandExecutor {

    private final JavaPlugin plugin;
    private Location spawnLocation;

    public spawn(JavaPlugin plugin) {
        this.plugin = plugin;
        this.spawnLocation = null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messageutils.formatMessage(ChatColor.RED + "Only players can use this command."));
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("setspawn")) {
            // Check for permission
            if (!player.hasPermission("sheep.setspawn")) {
                player.sendMessage(messageutils.formatMessage(ChatColor.RED + "You do not have permission to use this command."));
                return true;
            }

            spawnLocation = player.getLocation();
            plugin.getConfig().set("spawn", serializeLocation(spawnLocation));
            plugin.saveConfig();
            player.sendMessage(messageutils.formatMessage(ChatColor.GREEN + "Spawn location set successfully!"));
            return true;
        }

        if (command.getName().equalsIgnoreCase("spawn")) {
            if (spawnLocation == null) {
                spawnLocation = deserializeLocation(plugin.getConfig().getString("spawn"));
            }

            if (spawnLocation != null) {
                player.teleport(spawnLocation);
                player.sendMessage(messageutils.formatMessage(ChatColor.YELLOW + "Teleported to spawn!"));
            } else {
                player.sendMessage(messageutils.formatMessage(ChatColor.RED + "Spawn location has not been set yet!"));
            }
            return true;
        }

        return false;
    }

    private String serializeLocation(Location location) {
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
    }

    private Location deserializeLocation(String locationString) {
        if (locationString == null || locationString.isEmpty()) {
            return null;
        }

        String[] parts = locationString.split(",");
        if (parts.length != 6) {
            return null;
        }

        try {
            return new Location(
                    plugin.getServer().getWorld(parts[0]),
                    Double.parseDouble(parts[1]),
                    Double.parseDouble(parts[2]),
                    Double.parseDouble(parts[3]),
                    Float.parseFloat(parts[4]),
                    Float.parseFloat(parts[5])
            );
        } catch (Exception e) {
            return null;
        }
    }
}
