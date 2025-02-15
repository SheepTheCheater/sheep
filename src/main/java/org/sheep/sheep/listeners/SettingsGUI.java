package org.sheep.sheep.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SettingsGUI implements Listener {

    private final Map<Player, Sound> playerSelectedSound = new HashMap<>();

    // Öffnet die erste Seite des Killsound-GUIs
    public void openSettingsGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.GREEN + "Killsounds");

        // Seite 1: Items hinzufügen
        gui.setItem(10, createSoundItem(Material.BONE, "Wolf Howl", Sound.ENTITY_WOLF_HOWL, player));
        gui.setItem(13, createSoundItem(Material.EXPERIENCE_BOTTLE, "Experience", Sound.ENTITY_EXPERIENCE_ORB_PICKUP, player));
        gui.setItem(16, createSoundItem(Material.STRING, "Cat Meow", Sound.ENTITY_CAT_AMBIENT, player));

        // Button für nächste Seite
        ItemStack nextPageItem = new ItemStack(Material.BARRIER);
        ItemMeta nextPageMeta = nextPageItem.getItemMeta();
        nextPageMeta.setDisplayName(ChatColor.RED + "Next Page");
        nextPageItem.setItemMeta(nextPageMeta);
        gui.setItem(26, nextPageItem);

        player.openInventory(gui);
    }

    // Öffnet die zweite Seite des Killsound-GUIs
    public void openSettingsPageTwo(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.GREEN + "Killsounds Page 2");

        // Seite 2: Items hinzufügen
        gui.setItem(10, createSoundItem(Material.RECOVERY_COMPASS, "Sonic Boom", Sound.ENTITY_WARDEN_SONIC_BOOM, player));
        gui.setItem(13, createSoundItem(Material.GOLDEN_HOE, "Lightning", Sound.ENTITY_LIGHTNING_BOLT_THUNDER, player));

        // Button für vorherige Seite
        ItemStack backPageItem = new ItemStack(Material.BARRIER);
        ItemMeta backPageMeta = backPageItem.getItemMeta();
        backPageMeta.setDisplayName(ChatColor.RED + "Previous Page");
        backPageItem.setItemMeta(backPageMeta);
        gui.setItem(18, backPageItem);

        player.openInventory(gui);
    }

    // Erstellt ein Item für den Sound mit den gewünschten Eigenschaften
    private ItemStack createSoundItem(Material material, String name, Sound sound, Player player) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + name);

        // Überprüfen, ob der Sound ausgewählt ist
        boolean isSelected = playerSelectedSound.getOrDefault(player, Sound.ENTITY_WOLF_HOWL) == sound;
        String selectedText = isSelected ? ChatColor.GREEN + "Selected ✔" : ChatColor.RED + "Not Selected";

        // Lore hinzufügen
        meta.setLore(Arrays.asList(
                selectedText,
                ChatColor.GRAY + "for a preview click right or left click."
        ));

        item.setItemMeta(meta);
        return item;
    }

    // EventHandler für Klicks in den GUIs
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        String title = event.getView().getTitle();

        // Logik für die erste Seite
        if (title.equals(ChatColor.GREEN + "Killsounds")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || !clickedItem.hasItemMeta()) return;

            String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());

            // Sound auswählen oder Vorschau abspielen
            if (event.getSlot() == 10) { // Wolf Howl
                handleSoundSelection(player, Sound.ENTITY_WOLF_HOWL, "Wolf Howl", clickedItem, event);
            } else if (event.getSlot() == 13) { // Experience Orb
                handleSoundSelection(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, "Experience", clickedItem, event);
            } else if (event.getSlot() == 16) { // Cat Ambient
                handleSoundSelection(player, Sound.ENTITY_CAT_AMBIENT, "Cat Meow", clickedItem, event);
            } else if (event.getSlot() == 26) { // Nächste Seite
                openSettingsPageTwo(player);
            }
        }
        // Logik für die zweite Seite
        else if (title.equals(ChatColor.GREEN + "Killsounds Page 2")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || !clickedItem.hasItemMeta()) return;

            String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());

            // Sound auswählen oder Vorschau abspielen
            if (event.getSlot() == 10) { // Sonic Boom
                handleSoundSelection(player, Sound.ENTITY_WARDEN_SONIC_BOOM, "Sonic Boom", clickedItem, event);
            } else if (event.getSlot() == 13) { // Lightning
                handleSoundSelection(player, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, "Lightning", clickedItem, event);
            } else if (event.getSlot() == 18) { // Zurück zur ersten Seite
                openSettingsGUI(player);
            }
        }
    }

    // Verarbeitet die Soundauswahl oder spielt eine Vorschau ab
    private void handleSoundSelection(Player player, Sound sound, String soundName, ItemStack clickedItem, InventoryClickEvent event) {
        if (event.isRightClick()) {
            // Vorschau abspielen
            player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
        } else {
            // Sound auswählen
            playerSelectedSound.put(player, sound);
            player.sendMessage(ChatColor.GREEN + "You selected: " + ChatColor.AQUA + soundName + ChatColor.GREEN + " ✔");

            // GUI aktualisieren
            if (event.getView().getTitle().equals(ChatColor.GREEN + "Killsounds")) {
                openSettingsGUI(player);
            } else {
                openSettingsPageTwo(player);
            }
        }
    }

    // Gibt den ausgewählten Killsound eines Spielers zurück (Standard: Wolf Howl)
    public Sound getSelectedSound(Player player) {
        return playerSelectedSound.getOrDefault(player, Sound.ENTITY_WOLF_HOWL);
    }
}