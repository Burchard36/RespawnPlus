package com.burchard36.git.config;

import com.burchard36.git.RespawnPlus;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public class PluginSettings implements Configable {

    protected final RespawnPlus pluginInstance;

    @Getter
    protected static boolean cancelInvincibilityOnAttack;
    @Getter
    protected static boolean preventInteractionDuringInvincibility;
    @Getter
    protected static boolean allowItemPickupWhileInvincible;
    @Getter
    protected static boolean preventPvEDuringInvincibility;
    @Getter
    protected static boolean onlyApplyInvincibilityIfKilledByPlayer;
    @Getter
    protected static int invincibleTimer;
    @Getter
    protected static int timerUpdateRate;


    public PluginSettings(final RespawnPlus pluginInstance) {
        this.pluginInstance = pluginInstance;
        this.loadConfig();
    }

    @Override
    public void loadConfig() {
        final FileConfiguration config = this.pluginInstance.getConfig();
        cancelInvincibilityOnAttack = config.getBoolean("RespawnSettings.CancelInvincibilityOnAttack");
        preventInteractionDuringInvincibility = config.getBoolean("RespawnSettings.PreventInteractionDuringInvincibility");
        allowItemPickupWhileInvincible = config.getBoolean("RespawnSettings.AllowItemPickupWhileInvincible");
        onlyApplyInvincibilityIfKilledByPlayer = config.getBoolean("RespawnSettings.OnlyApplyInvincibilityIfKilledByPlayer");
        preventPvEDuringInvincibility = config.getBoolean("RespawnSettings.PreventPvEDuringInvincibility");
        invincibleTimer = config.getInt("RespawnSettings.InvincibleTimer");
        timerUpdateRate = config.getInt("RespawnSettings.TimerUpdateRate");
    }

    @Override
    public void reloadConfig() {
        this.pluginInstance.reloadConfig();
        this.loadConfig();
    }

}
