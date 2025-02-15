package org.sheep.sheep.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;
import org.sheep.sheep.commands.Freeze;

public class FreezeListener implements Listener {

    private final Freeze freezeCommand;

    public FreezeListener(Freeze freezeCommand) {
        this.freezeCommand = freezeCommand;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (freezeCommand.isFrozen(player)) {
            event.setTo(event.getFrom());
        }
    }
}
