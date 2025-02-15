package org.sheep.sheep.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.projectiles.ProjectileSource;

public class PlayerEventListener implements Listener {

    @EventHandler
    public void onPlayerHitByProjectile(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player target) {
            if (event.getDamager() instanceof Projectile projectile) {
                ProjectileSource shooter = projectile.getShooter();
                if (shooter instanceof Player attacker) {
                    double health = target.getHealth() - event.getFinalDamage();
                    if (health < 0) health = 0;

                    attacker.sendMessage(ChatColor.RED + target.getName() + ChatColor.YELLOW +
                            " is now on " + ChatColor.RED + String.format(" ", health) + "");
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
            Bukkit.broadcastMessage("🗡️ " + ChatColor.RED + deadPlayer.getName() + ChatColor.WHITE +
                    " has been killed by " + ChatColor.RED + killer.getName() + ChatColor.WHITE + "!");
            killer.sendTitle("🗡️ " + deadPlayer.getName(), "", 10, 40, 10);
        }
    }
}
