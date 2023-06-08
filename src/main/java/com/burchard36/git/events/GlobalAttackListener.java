package com.burchard36.git.events;

import com.burchard36.git.RespawnPlus;
import com.burchard36.git.config.PluginMessages;
import com.burchard36.git.config.PluginSettings;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static com.burchard36.git.RespawnPlus.convert;

public class GlobalAttackListener implements Listener {

    protected final RespawnPlus pluginInstance;

    public GlobalAttackListener(final RespawnPlus pluginInstance) {
        this.pluginInstance = pluginInstance;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerAttack(EntityDamageByEntityEvent damageEvent) {
        final Entity target = damageEvent.getEntity();
        final Entity assault = damageEvent.getDamager();

        if (!(assault instanceof Player)) return;

        final Player assaultingPlayer = (Player) assault;
        if (!this.pluginInstance.getInvincibilityHandler().isInvincible(assaultingPlayer.getUniqueId())) return;

        if (target instanceof Player) {

            if (PluginSettings.isCancelInvincibilityOnAttack()) {
                this.pluginInstance.getInvincibilityHandler().removeFromMap(assaultingPlayer.getUniqueId());
            } else {
                assaultingPlayer.sendMessage(convert(PluginMessages.getUnableToAttackPlayer()));
                damageEvent.setCancelled(true);
            }

        } else {

            if (PluginSettings.isPreventPvEDuringInvincibility()) {
                assaultingPlayer.sendMessage(convert(PluginMessages.getUnableToAttackEntity()));
                damageEvent.setCancelled(true);
            }

        }

    }
}
