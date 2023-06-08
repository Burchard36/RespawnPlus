package com.burchard36.git;

import com.burchard36.git.config.PluginMessages;
import com.burchard36.git.config.PluginSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static com.burchard36.git.RespawnPlus.convert;

public class InvincibilityHandler {

    protected final RespawnPlus pluginInstance;
    protected final HashMap<UUID, Long> playerCooldowns;
    protected final BukkitTask asyncTimerTask;

    public InvincibilityHandler(final RespawnPlus pluginInstance) {
        this.pluginInstance = pluginInstance;
        this.playerCooldowns = new HashMap<>();

        this.asyncTimerTask = new BukkitRunnable() {
            @Override
            public void run() {
                final long currentTime = new Date().getTime();
                playerCooldowns.forEach((uuid, expAtMillis) -> {
                    if (expAtMillis <= currentTime) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                removeFromMap(uuid);
                            }
                        } .runTask(pluginInstance);
                    }
                });
            }
        }.runTaskTimerAsynchronously(this.pluginInstance, PluginSettings.getTimerUpdateRate(), PluginSettings.getTimerUpdateRate());
    }

    public void removeFromMap(final UUID uuid) {
        if (this.playerCooldowns.containsKey(uuid)) {
            final Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                player.sendMessage(convert(PluginMessages.getInvincibilityExpired()));
                player.setInvulnerable(false); // We handle QuitEvent in case this leaks through
            }
        }
        this.playerCooldowns.remove(uuid);
    }

    public void processFor(final Player player) {
        this.playerCooldowns.remove(player.getUniqueId()); // We are going to replace the players timer if he has one already

        long expireAt = new Date().getTime() + (PluginSettings.getInvincibleTimer() * 50L);
        this.playerCooldowns.putIfAbsent(player.getUniqueId(), expireAt);
        player.sendMessage(convert(PluginMessages.getInvincibilityApplied().formatted(this.getSeconds())));
        player.setInvulnerable(true);
    }

    public boolean isInvincible(UUID uuid) {
        return this.playerCooldowns.containsKey(uuid);
    }

    protected int getSeconds() {
        return PluginSettings.getInvincibleTimer() / 20;
    }

}
