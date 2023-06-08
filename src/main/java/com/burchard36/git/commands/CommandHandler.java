package com.burchard36.git.commands;

import com.burchard36.git.RespawnPlus;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static com.burchard36.git.RespawnPlus.convert;

public class CommandHandler implements CommandExecutor {

    @Getter
    protected RespawnPlus pluginInstance;

    public CommandHandler(final RespawnPlus pluginInstance) {
        this.pluginInstance = pluginInstance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!RespawnPlus.getVaultPermissions().has(sender, "respawnplus.admin")) {
            sender.sendMessage(convert("&cYou do not have &brespawnplus.admin&c permission to use this!"));
            return false;
        }

        if (args.length != 1) {
            sender.sendMessage(convert("&cThe only command is &b/respawnplus reload&c!"));
            return false;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(convert("&aReloading configs..."));

            this.pluginInstance.getPluginSettings().reloadConfig();
            this.pluginInstance.getPluginMessages().reloadConfig();

            sender.sendMessage(convert("&aConfigs reloaded!"));
        } else {
            sender.sendMessage(convert("&cThe only command is &b/respawnplus reload&c!"));
            return false;
        }

        return true;
    }
}
