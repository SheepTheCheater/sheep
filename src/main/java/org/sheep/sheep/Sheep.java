package org.sheep.sheep;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;
import org.sheep.sheep.commands.*;
import org.sheep.sheep.listeners.FreezeListener;
import org.sheep.sheep.listeners.SettingsGUI;
import org.sheep.sheep.listeners.InventoryListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Sheep extends JavaPlugin implements Listener, CommandExecutor {

    private static Sheep instance; // Singleton-Instance

    private Freeze freezeCommand;
    private SettingsGUI settingsGUI;

    private Inventory customInventory;
    private final Map<Player, Boolean> deleteMode = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        freezeCommand = new Freeze();
        settingsGUI = new SettingsGUI();

        getCommand("freeze").setExecutor(freezeCommand);
        getCommand("unfreeze").setExecutor(freezeCommand);
        getCommand("gm").setExecutor(new gamemode());
        getCommand("gm").setTabCompleter(new gamemode());
        getCommand("ckit").setExecutor(new customkit());
        getCommand("sheep").setExecutor(new help());
        getCommand("sheep").setTabCompleter(new help());
        getCommand("heal").setExecutor(new heal());
        getCommand("setspawn").setExecutor(new spawn(this));
        getCommand("spawn").setExecutor(new spawn(this));
        getCommand("ench").setExecutor(new ench());
        getCommand("ench").setTabCompleter(new ench());
        getCommand("settings").setExecutor(this);

        getServer().getPluginManager().registerEvents(new FreezeListener(freezeCommand), this);
        getServer().getPluginManager().registerEvents(settingsGUI, this); // Registriere die Events der SettingsGUI
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        getServer().getPluginManager().registerEvents(this, this); // Registriere die Events der Main-Klasse

        customInventory = Bukkit.createInventory(null, 54, "Customs Inventory");


        getLogger().info("Sheep Plugin erfolgreich aktiviert!"); // HAHAHAHAHFDZWDBWA
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("settings"))
            if (sender instanceof Player) {
                Player player = (Player) sender;
                settingsGUI.openSettingsGUI(player);
            }
        return true;
    };


    @Override
    public void onDisable() {
        getLogger().info("Sheep Plugin deaktiviert.");
    }

    public static Sheep getInstance() {
        return instance;
    }

    public Inventory getCustomInventory() {
        return customInventory;
    }

    public boolean isInDeleteMode(Player player) {
        return deleteMode.getOrDefault(player, false);
    }

    public void setDeleteMode(Player player, boolean mode) {
        deleteMode.put(player, mode);
    }

    @EventHandler
    public void onPlayerHitByProjectile(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player target = (Player) event.getEntity();
            if (event.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile) event.getDamager();
                ProjectileSource shooter = projectile.getShooter();
                if (shooter instanceof Player) {
                    Player attacker = (Player) shooter;
                    double health = target.getHealth() - event.getFinalDamage();
                    if (health < 0) health = 0;

                    attacker.sendMessage(ChatColor.RED + "🏹 " + target.getName() + ChatColor.YELLOW +
                            " is now on " + ChatColor.RED + String.format("%.1f", health) + " ❤!");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);

        Player deadPlayer = event.getEntity();
        Player killer = deadPlayer.getKiller();

        if (killer != null) {
            Bukkit.broadcastMessage("\uD83D\uDDE1 " + ChatColor.RED + deadPlayer.getName() + ChatColor.WHITE +
                    " has been killed by " + ChatColor.RED + killer.getName() + ChatColor.WHITE + "!");

            killer.sendTitle(ChatColor.WHITE + "\uD83D\uDDE1 " + ChatColor.RED + deadPlayer.getName(), "", 10, 40, 10);

            Sound selectedSound = settingsGUI.getSelectedSound(killer);
            killer.playSound(killer.getLocation(), selectedSound, 1.0f, 1.0f);

            double killerHealth = killer.getHealth();
            String healthDisplay = formatHealth(killerHealth, killer.getMaxHealth());
            deadPlayer.sendMessage(ChatColor.YELLOW + "Leben von " + ChatColor.RED + killer.getName() + ChatColor.WHITE + " ↓ " + healthDisplay);

        }
    }

    private String formatHealth(double health, double maxHealth) {
        int fullHearts = (int) health / 2;
        boolean hasHalfHeart = health % 2 != 0;
        int missingHearts = (int) (maxHealth / 2) - fullHearts - (hasHalfHeart ? 1 : 0);

        StringBuilder hearts = new StringBuilder();
        for (int i = 0; i < fullHearts; i++) {
            hearts.append(ChatColor.RED).append("❤");
        }
        if (hasHalfHeart) {
            hearts.append(ChatColor.RED).append("❥");
        }
        for (int i = 0; i < missingHearts; i++) {
            hearts.append(ChatColor.WHITE).append("❤");
        }

        return hearts.toString();
    }
}