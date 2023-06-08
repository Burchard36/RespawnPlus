package com.burchard36.git.events;

import com.burchard36.git.RespawnPlus;
import com.burchard36.git.config.PluginMessages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import static com.burchard36.git.RespawnPlus.convert;

public class GlobalInteractListener implements Listener {

    protected final RespawnPlus pluginInstance;

    public GlobalInteractListener(final RespawnPlus pluginInstance) {
        this.pluginInstance = pluginInstance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent interactEvent) {
        if (!this.pluginInstance.getInvincibilityHandler().isInvincible(interactEvent.getPlayer().getUniqueId())) return;

        interactEvent.getPlayer().sendMessage(convert(PluginMessages.getUnableToInteract()));
        interactEvent.setCancelled(true);
    }
}
