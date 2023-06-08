package com.burchard36.git;

import com.burchard36.git.commands.CommandHandler;
import com.burchard36.git.config.PluginMessages;
import com.burchard36.git.config.PluginSettings;
import com.burchard36.git.events.GlobalAttackListener;
import com.burchard36.git.events.GlobalInteractListener;
import com.burchard36.git.events.GlobalQuitListener;
import com.burchard36.git.events.GlobalRespawnListener;
import lombok.Getter;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class RespawnPlus extends JavaPlugin {

    @Getter
    private PluginSettings pluginSettings;
    @Getter
    private PluginMessages pluginMessages;
    @Getter
    private InvincibilityHandler invincibilityHandler;
    @Getter
    private static Permission vaultPermissions;
    @Getter
    private Logger pluginLogger;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.pluginSettings = new PluginSettings(this);
        this.pluginMessages = new PluginMessages(this);
        this.pluginLogger = Bukkit.getServer().getLogger();
        this.invincibilityHandler = new InvincibilityHandler(this);

        if (!this.setupPermissions()) {
            this.getPluginLogger().log(Level.SEVERE, "Unable to register vault permissions! Shutting plugin down. (Do you have Vault installed?)");
            this.getServer().getPluginManager().disablePlugin(this);
        }

        this.getServer().getPluginManager().registerEvents(new GlobalAttackListener(this), this);
        this.getServer().getPluginManager().registerEvents(new GlobalQuitListener(this), this);
        this.getServer().getPluginManager().registerEvents(new GlobalRespawnListener(this), this);
        this.getServer().getPluginManager().registerEvents(new GlobalInteractListener(this), this);

        getCommand("respawnplus").setExecutor(new CommandHandler(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Registers the VaultAPI for permission checking
     * @return true if the setup was successfull
     */
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) return false;
        vaultPermissions = rsp.getProvider();
        return true;
    }

    /**
     * Converts a basic string to basic minecraft color codes
     * @param toConvert String to convert
     * @return Colored string
     */
    public static String convert(final String toConvert) {
        return ChatColor.translateAlternateColorCodes('&', toConvert);
    }
}
