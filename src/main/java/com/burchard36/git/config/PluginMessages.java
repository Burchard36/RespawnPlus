package com.burchard36.git.config;

import com.burchard36.git.RespawnPlus;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public class PluginMessages implements Configable {
    protected final RespawnPlus pluginInstance;

    @Getter
    protected static String invincibilityApplied;
    @Getter
    protected static String invincibilityExpired;
    @Getter
    protected static String unableToAttackPlayer;
    @Getter
    protected static String unableToAttackEntity;

    public PluginMessages(final RespawnPlus pluginInstance) {
        this.pluginInstance = pluginInstance;
        this.loadConfig();
    }

    @Override
    public void loadConfig() {
        final FileConfiguration config = this.pluginInstance.getConfig();
        invincibilityApplied = config.getString("Messages.InvincibilityApplied");
        invincibilityExpired = config.getString("Messages.InvincibilityExpired");
        unableToAttackPlayer = config.getString("Messages.UnableToAttackPlayer");
        unableToAttackEntity = config.getString("Messages.UnableToAttackEntity");
    }

    @Override
    public void reloadConfig() {
        this.pluginInstance.reloadConfig();
        this.loadConfig();
    }

}
