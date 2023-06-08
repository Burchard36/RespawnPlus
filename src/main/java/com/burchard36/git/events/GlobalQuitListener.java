package com.burchard36.git.events;

import com.burchard36.git.RespawnPlus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class GlobalQuitListener implements Listener {

    protected final RespawnPlus pluginInstance;

    public GlobalQuitListener(RespawnPlus pluginInstance) {
        this.pluginInstance = pluginInstance;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent quitEvent) {
        quitEvent.getPlayer().setInvulnerable(false);
        this.pluginInstance.getInvincibilityHandler().removeFromMap(quitEvent.getPlayer().getUniqueId());
    }

}
