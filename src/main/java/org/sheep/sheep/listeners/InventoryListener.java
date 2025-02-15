package org.sheep.sheep.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.sheep.sheep.Sheep;

public class InventoryListener implements Listener {

    private final Sheep plugin;

    public InventoryListener(Sheep plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        Inventory customInventory = plugin.getCustomInventory();

        if (clickedInventory != null && clickedInventory.equals(customInventory)) {
            Player player = (Player) event.getWhoClicked();

            if (plugin.isInDeleteMode(player)) {
                // Player is in delete mode, allow removing items
                event.setCancelled(false);
            } else {
                // Player is not in delete mode, prevent taking items
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Inventory draggedInventory = event.getInventory();
        Inventory customInventory = plugin.getCustomInventory();

        if (draggedInventory.equals(customInventory)) {
            Player player = (Player) event.getWhoClicked();

            if (!plugin.isInDeleteMode(player)) {
                event.setCancelled(true);
            }
        }
    }
}