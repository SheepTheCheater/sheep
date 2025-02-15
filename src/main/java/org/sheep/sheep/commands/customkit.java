package org.sheep.sheep.commands;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.sheep.sheep.messageutils;

public class customkit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();

            if (!player.hasPermission("sheep.customkit")) {
                player.sendMessage(messageutils.formatMessage(ChatColor.RED + "You do not have permission to execute this command, this is a test command so you dont have acess to it."));
                return true;
            }

            ItemStack helmet = new ItemStack(Material.NETHERITE_HELMET);
            ItemStack chestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
            ItemStack leggings = new ItemStack(Material.NETHERITE_LEGGINGS);
            ItemStack boots = new ItemStack(Material.NETHERITE_BOOTS);
            player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 2));
            player.getInventory().addItem(new ItemStack(Material.NETHERITE_SWORD, 1));
            ItemStack nethsword = new ItemStack(Material.NETHERITE_SWORD);

            ItemStack potionStack = new ItemStack(Material.SPLASH_POTION, 5);
            PotionMeta potionMeta = (PotionMeta) potionStack.getItemMeta();
            potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH, 1, 1, false), true);
            potionStack.setItemMeta(potionMeta);
            player.getInventory().addItem(potionStack);

            nethsword.addEnchantment(Enchantment.SHARPNESS, 1);
            helmet.addEnchantment(Enchantment.PROTECTION, 2);
            chestplate.addEnchantment(Enchantment.PROTECTION, 2);
            leggings.addEnchantment(Enchantment.PROTECTION, 2);
            boots.addEnchantment(Enchantment.PROTECTION, 2);

            player.getInventory().setHelmet(helmet);
            player.getInventory().setChestplate(chestplate);
            player.getInventory().setLeggings(leggings);
            player.getInventory().setBoots(boots);

            player.sendMessage(messageutils.formatMessage(ChatColor.WHITE + "you have received the " + ChatColor.AQUA + "CustomKit" + ChatColor.WHITE + " kit."));

        }
        return true;
    }
}
