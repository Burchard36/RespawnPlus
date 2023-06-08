package com.burchard36.git.events;

import com.burchard36.git.RespawnPlus;
import com.burchard36.git.config.PluginSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GlobalRespawnListener implements Listener {

    protected final RespawnPlus pluginInstance;
    protected final List<UUID> playersMarkedForInvincibility;

    public GlobalRespawnListener(final RespawnPlus pluginInstance) {
        this.pluginInstance = pluginInstance;
        this.playersMarkedForInvincibility = new ArrayList<>();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerRespawn(final PlayerRespawnEvent respawnEvent) {
        final Player respawningPlayer = respawnEvent.getPlayer();

        if (!this.playersMarkedForInvincibility.contains(respawningPlayer.getUniqueId())) return;

        this.playersMarkedForInvincibility.remove(respawningPlayer.getUniqueId());
        this.pluginInstance.getInvincibilityHandler().processFor(respawningPlayer);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(final PlayerDeathEvent deathEvent) {
        final Player deadPlayer = deathEvent.getEntity();
        if (this.wasKilledByPlayer(deadPlayer)
                && PluginSettings.isOnlyApplyInvincibilityIfKilledByPlayer()
                && !this.playersMarkedForInvincibility.contains(deadPlayer.getUniqueId())) {
            this.playersMarkedForInvincibility.add(deadPlayer.getUniqueId());
        }
    }

    /**
     * Checks if the player's killer was null
     * @param player Player to check
     * @return true if player has a killer
     */
    protected final boolean wasKilledByPlayer(final Player player ) {
        return player.getKiller() != null;
    }

}
