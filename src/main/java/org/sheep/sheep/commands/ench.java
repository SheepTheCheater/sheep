package org.sheep.sheep.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.sheep.sheep.messageutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ench implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(ChatColor.RED + "Verwendung: /ench <Spielername> <Enchantment> <Level>");
            return true;
        }

        // Spieler überprüfen
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(messageutils.formatMessage(ChatColor.RED + "Spieler nicht gefunden."));
            return true;
        }

        // Item in der Hand überprüfen
        ItemStack item = target.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            sender.sendMessage(messageutils.formatMessage(ChatColor.RED + "Der Spieler hält kein Item in der Hand."));
            return true;
        }

        // Enchantment überprüfen
        Enchantment enchantment = getEnchantmentByName(args[1]);
        if (enchantment == null) {
            sender.sendMessage(messageutils.formatMessage(ChatColor.RED + "Ungültiges Enchantment."));
            return true;
        }

        // Level überprüfen
        int level;
        try {
            level = Integer.parseInt(args[2]);
            if (level < 1 || level > 255) {
                sender.sendMessage(messageutils.formatMessage(ChatColor.RED + "Das Level muss zwischen 1 und 255 liegen."));
                return true;
            }
        } catch (NumberFormatException e) {
            sender.sendMessage(messageutils.formatMessage(ChatColor.RED + "Ungültiges Level. Bitte gib eine Zahl ein."));
            return true;
        }

        // Enchantment anwenden
        item.addUnsafeEnchantment(enchantment, level);
        sender.sendMessage(messageutils.formatMessage(ChatColor.GREEN + "Enchantment erfolgreich angewendet!"));
        target.sendMessage(messageutils.formatMessage(ChatColor.GOLD + "Dein Item wurde mit " + ChatColor.YELLOW + "\uD83D\uDDE1\uFE0F " + args[1] + " " + level + ChatColor.GOLD + " enchantet!"));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 2) {
            // Vervollständige Enchantment-Namen
            return Arrays.asList("Sharpness", "Protection", "Knockback", "Unbreaking", "Efficiency", "Fortune", "Power");
        } else if (args.length == 3) {
            // Vervollständige Level (1-255)
            return Arrays.asList("1", "10", "50", "100", "255");
        }
        return new ArrayList<>();
    }

    private Enchantment getEnchantmentByName(String name) {
        switch (name.toLowerCase(Locale.ROOT)) {
            case "sharpness":
                return Enchantment.SHARPNESS;
            case "protection":
                return Enchantment.PROTECTION;
            case "knockback":
                return Enchantment.KNOCKBACK;
            case "unbreaking":
                return Enchantment.UNBREAKING;
            case "efficiency":
                return Enchantment.EFFICIENCY;
            case "fortune":
                return Enchantment.FORTUNE;
            case "power":
                return Enchantment.POWER;
            default:
                return null;
        }
    }
}